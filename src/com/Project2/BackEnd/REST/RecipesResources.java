package com.Project2.BackEnd.REST;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import com.Project2.BackEnd.RecipesManagement.Recipe;
import com.Project2.BackEnd.Trees.AVLTree;
import com.Project2.BackEnd.Trees.BinaryTree;
import com.Project2.BackEnd.UsersManagement.User;

@Path("/recipes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RecipesResources {
	/**
	 * REST resources for handling recipes
	 * 
	 * @author Luis Pedro Morales Rodriguez
	 * @version 13/7/2020
	 */

	private static AVLTree<Recipe> avl = AVLTree.getInstance();
	private static BinaryTree<User> bt = BinaryTree.getInstance();
	private String key, name = null, author = null, type = null, portions = null, cookingSpan = null, eatingTime = null,
			tags = null, price = null, ingredients = null, steps = null;
	private ArrayList<String> comments;
	private int difficulty = 0, punctuation = 0;
	private ArrayList<Recipe> responseList;
	private User authorUser;

	@POST
	@SuppressWarnings("rawtypes")
	public Response registerRecipe(@Context UriInfo uriInfo) {
		Recipe recipe;
		for (Map.Entry entry : uriInfo.getQueryParameters().entrySet()) {
			key = entry.getKey().toString();
			StringTokenizer tokenizer = new StringTokenizer(entry.getValue().toString(), "[ // ]");
			tokenizer = new StringTokenizer(tokenizer.nextToken());

			switch (key) {
			case "name":
				name = tokenizer.nextToken();
				break;
			case "author":
				author = tokenizer.nextToken();
				break;
			case "type":
				type = tokenizer.nextToken();
				break;
			case "portions":
				portions = tokenizer.nextToken();
				break;
			case "eatingTime":
				eatingTime = tokenizer.nextToken();
				break;
			case "cookingSpan":
				cookingSpan = tokenizer.nextToken();
				break;
			case "difficulty":
				String dif = tokenizer.nextToken();
				difficulty = Integer.parseInt(dif);
				break;
			case "tags":
				tags = tokenizer.nextToken();
				break;
			case "price":
				price = tokenizer.nextToken();
				break;
			case "steps":
				steps = tokenizer.nextToken();
				break;
			case "ingredients":
				ingredients = tokenizer.nextToken();
				break;
			case "punctuation":
				String punct = tokenizer.nextToken();
				punctuation = Integer.parseInt(punct);
				break;

			default:
				break;
			}
		}

		if (author != null) {
			Recipe newRecipe = Recipe.builder().withName(name).withAuthor(author).withType(type).withPortions(portions)
					.withEatingTime(eatingTime).withCookingSpan(cookingSpan).withDifficulty(difficulty).withTags(tags)
					.withPrice(price).withSteps(steps).withIngredients(ingredients).withPunctuation(punctuation)
					.build();
			avl.insert(newRecipe);

			authorUser = bt.getUserByEmail(author);
			authorUser.addRecipe(newRecipe);

			return Response.status(201).entity(newRecipe).build();
		}
		else {
			return Response.status(Status.NOT_FOUND).entity("User not found for: " + author).build();
		}
	}

	@GET
	@Path("/{userEmail}")
	@SuppressWarnings("rawtypes")
	public Response getMyMenu(@PathParam("userEmail") String userEmail, @Context UriInfo uriInfo) {
		StringTokenizer tokenizer = null;
		int sortingType;

		for (Map.Entry entry : uriInfo.getQueryParameters().entrySet()) {
			key = entry.getKey().toString();
			tokenizer = new StringTokenizer(entry.getValue().toString(), "[ // ]");
			tokenizer = new StringTokenizer(tokenizer.nextToken());
		}
		String sortType = tokenizer.nextToken();
		sortingType = Integer.parseInt(sortType);
		authorUser = bt.getUserByEmail(userEmail);
		if (authorUser != null) {
			authorUser.setSortingType(sortingType);
			responseList = authorUser.getRecipes();
			return Response.status(Status.OK).entity(responseList).build();
		}

		else {
			return Response.status(Status.NOT_FOUND).entity("User not found for: " + userEmail).build();
		}

	}

	@GET
	public Response getAllRecipes() {
		responseList = avl.getList();
		return Response.ok(responseList).build();
	}

}
