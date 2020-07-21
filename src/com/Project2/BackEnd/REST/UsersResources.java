package com.Project2.BackEnd.REST;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.Project2.BackEnd.RecipesManagement.Recipe;
import com.Project2.BackEnd.Trees.AVLTree;
import com.Project2.BackEnd.Trees.BinaryTree;
import com.Project2.BackEnd.UsersManagement.MD5;
import com.Project2.BackEnd.UsersManagement.User;
import com.Project2.BackEnd.UsersManagement.UsersJSON;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResources implements RestResources, Observer {

	private static BinaryTree<User> bt = BinaryTree.getInstance();
	private static AVLTree<Recipe> avl = AVLTree.getInstance();
	private ArrayList<User> responseList;
	private User responseUser;
	private String key, email = null, name = null, password = null, age = null, profilePic = null;
	private UsersJSON usersJson;
	private MD5 MD5;
	private ArrayList<Recipe> myMenu;
	private static NotifObservable notifObservable;
	private boolean sendNotif = false;
	private Notification notification;
	private String observerUser;
	static {
		notifObservable = new NotifObservable();
	}

	/**
	 * Sets a new notification
	 * 
	 * @param uriInfo : UriInfo
	 * @return Response
	 */
	@SuppressWarnings("rawtypes")
	@POST
	@Path("/new_notif")
	public Response newNotification(@Context UriInfo uriInfo) {
		String emisorUser = null, recieverUser = null, newComment = null, recipeName = null;
		int notifType = 0;
		Recipe recipe = null;
		for (Map.Entry entry : uriInfo.getQueryParameters().entrySet()) {
			key = entry.getKey().toString();
			StringTokenizer tokenizer = new StringTokenizer(entry.getValue().toString(), "[ // ]");
			tokenizer = new StringTokenizer(tokenizer.nextToken());
			switch (key) {
			case "emisorUser":
				emisorUser = tokenizer.nextToken();
				break;
			case "recieverUser":
				recieverUser = tokenizer.nextToken();
				break;
			case "notifType":
				notifType = Integer.parseInt(tokenizer.nextToken());
				break;
			case "newComment":
				String encoded = tokenizer.nextToken();
				tokenizer = new StringTokenizer(encoded, "_");
				newComment = "";
				while (tokenizer.hasMoreTokens()) {
					newComment += tokenizer.nextToken()+" ";
				}

				break;
			case "recipe":
				recipeName = tokenizer.nextToken();
				break;
			default:
				break;
			}
		}
		notification = new Notification(emisorUser, recieverUser, notifType, newComment, recipeName);
		User emisor = bt.getUserByEmail(emisorUser);
		User reciever = bt.getUserByEmail(recieverUser);

		if (recipeName != null) {
			recipe = avl.getRecipeByName(recipeName);
		}

		switch (notification.getNotifType()) {
		case Notification.NEW_COMMENT:
			recipe.addComment(emisorUser, newComment);
			break;
		case Notification.NEW_FOLLOWER:
			emisor.addUserFollowing(recieverUser);
			reciever.addFollower(emisorUser);
			break;
		case Notification.NEW_LIKE:
			recipe.incrementPunctuation();
			break;
		case Notification.NEW_SHARE:
			emisor.addRecipe(recipe);
			recipe.incrementShares();
			break;
		default:
			break;
		}
		notifObservable.setNotification(notification); // change in observable subject
		return Response.status(200).entity("notification setted").build();
	}

	/**
	 * Sends a new notification as a response every time there is an update in the
	 * Notification Observer
	 * 
	 * @param uriInfo
	 * @return Response
	 * @throws InterruptedException {@link NotifObservable}
	 */
	@GET
	@Path("/get_notif")
	public Response getNotification(@QueryParam("observerUser") String observerUser) throws InterruptedException {
		this.observerUser = observerUser;
		notifObservable.addObserver(this);
		System.out.println("observer added");
		while (!sendNotif) {
			Thread.sleep(1);
		}
		notification = notifObservable.getNotification();
		sendNotif = false;
		return Response.status(200).entity(notification).build();
	}

	/**
	 * Inserts every user in the JSONArray to the BT
	 * 
	 * @param incomingData
	 * @return
	 */
	//@POST
	//@Path("/load")
	public Response load(JSONArray incomingData) {
		System.out.println(incomingData.toJSONString());
		usersJson = UsersJSON.getInstance();
		usersJson.addUsersToBT(incomingData);

		return Response.status(201).entity("JSON recieved").build();
	}
	
	@POST
	@Path("/load")
	public Response load(InputStream incomingData) throws ParseException {
		JSONParser parser = new JSONParser();
		StringBuilder stringBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error parsing");
		}
		
		JSONArray json = (JSONArray) parser.parse(stringBuilder.toString());
		usersJson = UsersJSON.getInstance();
		usersJson.addUsersToBT(json);
		return Response.status(201).entity(stringBuilder.toString()).build();
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
		responseUser = bt.getUserByEmail(userEmail);
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
		responseList = bt.getList();
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

			case "profilePic":
				profilePic = tokenizer.nextToken();
				break;

			default:
				break;
			}
		}
		if (email == null || name == null || password == null) {
			return Response.status(Status.CONFLICT).entity("Email, name and password mustn't be empty").build();
		} else if (bt.getUserByEmail(email) == null) {
			ArrayList<Recipe> recipes = null;
			newUser = User.builder().withEmail(email).withName(name).withAge(age).withPassword(password)
					.withProfilePic(profilePic).withMyMenu(recipes).build();
			bt.insert(newUser);
			return Response.status(201).entity(newUser).build();
		} else {
			return Response.status(Status.CONFLICT).entity("Email already in use").build();
		}

	}

	/**
	 * Edits information of the user
	 * 
	 * @param userEmail
	 * @param uriInfo
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@PUT
	@Path("/{userEmail}")
	public Response editUser(@PathParam("userEmail") String userEmail, @Context UriInfo uriInfo) {
		responseUser = bt.getUserByEmail(userEmail);
		if (responseUser != null) {
			for (Map.Entry entry : uriInfo.getQueryParameters().entrySet()) {
				key = entry.getKey().toString();
				StringTokenizer tokenizer = new StringTokenizer(entry.getValue().toString(), "[ // ]");

				switch (key) {
				case "email":
					email = tokenizer.nextToken();
					if (bt.getUserByEmail(email) == null) {
						responseUser.setEmail(email);
					} else {
						return Response.status(Status.CONFLICT).entity("Email not available: " + email).build();
					}
					break;
				case "name":
					name = tokenizer.nextToken();
					responseUser.setName(name);
					break;
				case "age":
					age = tokenizer.nextToken();
					responseUser.setAge(age);
					break;
				case "password":
					password = tokenizer.nextToken();
					MD5 = new MD5(password);
					try {
						password = MD5.getMD5();
						responseUser.setPassword(password);
					} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;

				case "profilePic":
					profilePic = tokenizer.nextToken();
					responseUser.setProfilePic(profilePic);
					break;

				default:
					break;
				}
			}
			return Response.ok(responseUser).build();
		} else {
			return Response.status(Status.NOT_FOUND).entity("User not found for: " + userEmail).build();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (notifObservable.getIsNewNotif() & notifObservable.getNotification().getRecieverUser().equals(observerUser)) {
			this.sendNotif = true;
			notifObservable.setIsNewNotif(false);
		}
	}

}
