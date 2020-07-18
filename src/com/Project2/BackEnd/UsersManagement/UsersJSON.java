package com.Project2.BackEnd.UsersManagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.parser.ParseException;

import com.Project2.BackEnd.RecipesManagement.Recipe;
import com.Project2.BackEnd.Trees.BinaryTree;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UsersJSON {
	private User user;
	private BinaryTree<User> BT;
	private static UsersJSON usersJson = null;

	private UsersJSON() {
		BT = BinaryTree.getInstance();

	}

	public BinaryTree<User> getBT() {
		return BT;
	}

	public void setBT(BinaryTree<User> bT) {
		BT = bT;
	}

	/**
	 * Singleton pattern design instantiation method
	 * 
	 * @return usersJson : UsersJSON
	 */
	public static synchronized UsersJSON getInstance() {
		if (usersJson == null) {
			usersJson = new UsersJSON();
		}
		return usersJson;
	}

	/**
	 * Public method. Loads to the binary tree all the users objects from the
	 * JSONArray
	 */
	@SuppressWarnings("unchecked")
	public void addUsersToBT(JSONArray usersList) {
		JSONParser parser = new JSONParser();
		ArrayList<Object> array = null;
		ArrayList<Recipe> recipesArray = null;
		String email = null, name = null, password = null, age = null, sortingType = null,
				recipesString = null, profilePic = null;
		ArrayList<String> usersFollowing = null;
		int followers = 0;
		System.out.println(usersList.getClass());
		// usersList.forEach(user -> parseUser((JSONObject) user));
		for (int i = 0; i < usersList.size(); i++) {
			HashMap<String, Object> passedValues = (HashMap<String, Object>) usersList.get(i);
			for (Entry<String, Object> mapTemp : passedValues.entrySet()) {
				switch (mapTemp.getKey()) {
				case "email":
					email = (String) mapTemp.getValue();
					break;
				case "name":
					name = (String) mapTemp.getValue();
					break;
				case "password":
					password = (String) mapTemp.getValue();
					break;
				case "age":
					age = (String) mapTemp.getValue();
					break;
				case "profilePic":
					profilePic = (String) mapTemp.getValue();
					break;
				case "usersFollowing":
					usersFollowing = (ArrayList<String>) mapTemp.getValue();
					break;
				case "followers":
					followers = Integer.parseInt((String)mapTemp.getValue());
					break;
				case "sortingType":
					sortingType = mapTemp.getValue().toString();
					break;
				case "recipes":
					array = (ArrayList<Object>) mapTemp.getValue();
					recipesArray = parseArray(array);
					break;

				default:
					break;
				}
			}
			this.user = User.builder().withEmail(email).withName(name).withPassword(password).withAge(age)
					.withProfilePic(profilePic).withUsersFollowing(usersFollowing).withFollowers(followers)
					.withMyMenu(recipesArray).build();
			this.BT.insert(user);
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Recipe> parseArray(ArrayList<Object> array) {
		ArrayList<Recipe> recipesArray = new ArrayList<Recipe>();
		String name = null, author = null, type = null, portions = null, cookingSpan = null, eatingTime = null,
				tags = null, image = null, ingredients = null, steps = null, price = null;
		int difficulty = 0, id = 0, punctuation = 0;
		HashMap<String, String> comments = null;
		for (int i = 0; i < array.size(); i++) {
			HashMap<String, Object> passedValues = (HashMap<String, Object>) array.get(i);
			for (Entry<String, Object> mapTemp : passedValues.entrySet()) {
				switch (mapTemp.getKey()) {
				case "name":
					name = (String) mapTemp.getValue();
					break;
				case "author":
					author = (String) mapTemp.getValue();
					break;
				case "type":
					type = (String) mapTemp.getValue();
					break;
				case "portions":
					portions = (String) mapTemp.getValue();
					break;
				case "cookingSpan":
					cookingSpan = (String) mapTemp.getValue();
					break;
				case "eatingTime":
					eatingTime = (String) mapTemp.getValue();
					break;
				case "tags":
					tags = (String) mapTemp.getValue();
					break;
				case "image":
					image = (String) mapTemp.getValue();
					break;
				case "ingredients":
					ingredients = (String) mapTemp.getValue();
					break;
				case "steps":
					steps = (String) mapTemp.getValue();
					break;
				case "price":
					price = (String) mapTemp.getValue();
					break;
				case "difficulty":
					difficulty = (int) mapTemp.getValue();
					break;
				case "id":
					id = (int) mapTemp.getValue();
					break;
				case "punctuation":
					punctuation = (int) mapTemp.getValue();
					break;
				case "comments":
					comments = (HashMap<String, String>) mapTemp.getValue();
					break;

				default:
					break;
				}
			}
			Recipe newRecipe = Recipe.builder().withName(name).withAuthor(author).withType(type).withPortions(portions)
					.withCookingSpan(cookingSpan).withEatingTime(eatingTime).withTags(tags).withImage(image)
					.withIngredients(ingredients).withSteps(steps).withPrice(price).withDifficulty(difficulty)
					.withId(id).withComments(comments).build();
			recipesArray.add(newRecipe);
		}

		return recipesArray;

	}

}
