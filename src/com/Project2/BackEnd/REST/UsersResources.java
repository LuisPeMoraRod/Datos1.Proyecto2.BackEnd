package com.Project2.BackEnd.REST;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import java.util.ArrayList;
import java.util.Map;
import java.util.StringTokenizer;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONArray;

import com.Project2.BackEnd.RecipesManagement.Recipe;
import com.Project2.BackEnd.Trees.BinaryTree;
import com.Project2.BackEnd.UsersManagement.MD5;
import com.Project2.BackEnd.UsersManagement.User;
import com.Project2.BackEnd.UsersManagement.UsersJSON;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResources implements RestResources {

	private static BinaryTree<User> BT = BinaryTree.getInstance();
	private ArrayList<User> responseList;
	private User responseUser;
	private String key, email = null, name = null, password = null, age = null, usersFollowing = null, followers = null,
			profilePic = null;
	private UsersJSON usersJson;
	private MD5 MD5;
	private ArrayList<Recipe> myMenu;

	@GET
	@Path("/notif")
	public Response notifications() {
		try {
			for (int i = 0; i < 60; i++) {
				Thread.sleep(1000);
				System.out.println(i);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return Response.status(200).entity("notification back").build();
	}

	@POST
	@Path("/load")
	public Response load(JSONArray incomingData) {
		System.out.println(incomingData);
		usersJson = UsersJSON.getInstance();
		usersJson.addUsersToBT(incomingData);

		return Response.status(201).entity("JSON recieved").build();
	}

	/**
	 * Searches by email and returns a specific user
	 * 
	 * @return responseUser : Response
	 * @return NOT_FOUND : Response
	 */
	@GET
	@Path("/{userEmail}")
	public Response get(@PathParam("userEmail") String userEmail) {
		responseUser = BT.getUserByEmail(userEmail);
		if (responseUser != null) {
			return Response.ok(responseUser).build();
		} else {
			return Response.status(Status.NOT_FOUND).entity("User not found for: " + userEmail).build();
		}

	}

	/**
	 * Returns all the registered users (sorted by alphabetical order) as a response
	 * to the client
	 * 
	 * @return responseList : Response
	 */
	@GET
	public Response getAll() {
		responseList = BT.getList();
		return Response.ok(responseList).build();
	}

	/**
	 * Registers a new user: authenticates and adds user to the binary tree
	 * 
	 * @param uriInfo : UriInfo
	 * @return newUser : Response
	 */
	@POST
	@SuppressWarnings("rawtypes")
	public Response register(@Context UriInfo uriInfo) {
		User newUser;
		for (Map.Entry entry : uriInfo.getQueryParameters().entrySet()) {
			key = entry.getKey().toString();
			StringTokenizer tokenizer = new StringTokenizer(entry.getValue().toString(), "[ // ]");

			switch (key) {
			case "email":
				email = tokenizer.nextToken();
				break;
			case "name":
				name = tokenizer.nextToken();
				break;
			case "age":
				age = tokenizer.nextToken();
				break;
			case "password":
				password = tokenizer.nextToken();
				MD5 = new MD5(password);
				try {
					password = MD5.getMD5();
				} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "usersFollowing":
				usersFollowing = tokenizer.nextToken();
				break;
			case "followers":
				followers = tokenizer.nextToken();
				break;
			case "profilePic":
				profilePic = tokenizer.nextToken();
				break;

			default:
				break;
			}
		}
		if (email == null || name == null || password == null) {
			return Response.status(Status.CONFLICT).entity("Email, name and password mustn't be empty").build();
		} else if (BT.getUserByEmail(email) == null) {
			newUser = User.builder().withEmail(email).withName(name).withAge(age).withPassword(password)
					.withFollowers(followers).withUsersFollowing(usersFollowing).withProfilePic(profilePic).build();
			BT.insert(newUser);
			return Response.status(201).entity(newUser).build();
		} else {
			return Response.status(Status.CONFLICT).entity("Email already in use").build();
		}

	}

}
