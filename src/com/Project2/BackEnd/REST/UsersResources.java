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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.Project2.BackEnd.CompaniesManagement.Company;
import com.Project2.BackEnd.RecipesManagement.Recipe;
import com.Project2.BackEnd.Trees.AVLTree;
import com.Project2.BackEnd.Trees.BinaryTree;
import com.Project2.BackEnd.Trees.SplayTree;
import com.Project2.BackEnd.UsersManagement.MD5;
import com.Project2.BackEnd.UsersManagement.User;
import com.Project2.BackEnd.UsersManagement.UsersJSON;
import com.sun.xml.bind.util.Which;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsersResources implements RestResources, Observer {

	private static BinaryTree<User> bt = BinaryTree.getInstance();
	private static AVLTree<Recipe> avl = AVLTree.getInstance();
	private static SplayTree<Company> splay = SplayTree.getInstance();
	private ArrayList<User> responseList;
	private User responseUser;
	private String key, email = null, name = null, password = null, age = null, profilePic = null;
	private boolean company;
	private UsersJSON usersJson;
	private MD5 md5;
	private ArrayList<Recipe> myMenu;
	public static NotifObservable notifObservable;
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
		String emisorUser = null, recieverUser = null, newComment = null, recipeName = null, entity = null;
		int notifType = 0;
		Recipe recipe = null;
		// get notification parameters
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
					newComment += tokenizer.nextToken() + " ";
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
		Company emisorComp = splay.get(emisorUser);
		if (recipeName != null) {
			recipe = avl.get(recipeName);
			if (recipe == null) {
				return Response.status(Status.CONFLICT).entity("Error: couldn't find recipe: " + recipeName).build();
			}
		}

		Company company = splay.get(recieverUser);
		switch (notification.getNotifType()) {
		case Notification.NEW_COMMENT:
			recipe.addComment(emisorUser, newComment);
			entity = "Comment added.";
			break;

		case Notification.NEW_FOLLOWER:
			if (emisor != null) {
				if (!emisor.getUsersFollowing().contains(recieverUser)) {
					emisor.addUserFollowing(recieverUser);
					follow(recieverUser, emisorUser);
					entity = "Follower added successfully.";
				} else {
					return Response.status(Status.CONFLICT)
							.entity("Error: " + emisorUser + " already follows " + recieverUser).build();
				}
			} else {
				if (!emisorComp.getUsersFollowing().contains(recieverUser)) {
					emisorComp.addUserFollowing(recieverUser);
					follow(recieverUser, emisorUser);
					entity = "Follower added successfully.";
				} else {
					return Response.status(Status.CONFLICT)
							.entity("Error: " + emisorUser + " already follows " + recieverUser).build();
				}
			}
			break;

		case Notification.NEW_UNFOLLLOW:
			if (emisor != null) {
				if (emisor.getUsersFollowing().contains(recieverUser)) {
					emisor.removeUserFollowing(recieverUser);
					unfollow(recieverUser, emisorUser);
					entity = "User unfollowed succesfully.";
				} else {
					return Response.status(Status.CONFLICT)
							.entity("Error: " + emisorUser + " doesn't follow " + recieverUser).build();
				}
			} else {
				if (emisorComp.getUsersFollowing().contains(recieverUser)) {
					emisorComp.removeUserFollowing(recieverUser);
					unfollow(recieverUser, emisorUser);
					entity = "User unfollowed succesfully.";
				} else {
					return Response.status(Status.CONFLICT)
							.entity("Error: " + emisorUser + " doesn't follow " + recieverUser).build();
				}
			}
			break;

		case Notification.NEW_LIKE:
			if (!recipe.getLikers().contains(emisorUser)) {
				recipe.incrementPunctuation();
				recipe.addLiker(emisorUser);
				entity = "Like added successfully.";
			} else {
				return Response.status(Status.CONFLICT).entity("Error: " + emisorUser + " already likes the recipe")
						.build();
			}
			break;

		case Notification.NEW_UNLIKE:
			if (recipe.getLikers().contains(emisorUser)) {
				recipe.decrementPunctuation();
				recipe.removeLiker(emisorUser);
				entity = "Like removed succesfully.";
			} else {
				return Response.status(Status.CONFLICT).entity("Error: " + emisorUser + " doesn't like the recipe")
						.build();
			}
			break;
		case Notification.NEW_SHARE:
			if (!emisor.getRecipes().contains(recipe)) {
				emisor.addRecipe(recipe);
				recipe.incrementShares();
				entity = "Recipe shared successfully.";
			} else {
				return Response.status(Status.CONFLICT)
						.entity("Error: " + emisorUser + " has already shared this recipe.").build();
			}
			break;

		case Notification.NEW_UNSHARE:
			if (emisor.getRecipes().contains(recipe)) {
				emisor.removeRecipe(recipe);
				recipe.decrementShares();
				entity = "Recipe removed from MyMenu successfully";
			} else {
				return Response.status(Status.CONFLICT).entity("Error: " + emisorUser + " hasn't shared this recipe.")
						.build();
			}
			break;
		case Notification.NEW_LIKE_COMPANY:
			if (!company.getLikers().contains(emisorUser)) {
				company.addLiker(emisorUser);
				entity = "New like to company added successfully";
			} else {
				return Response.status(Status.CONFLICT)
						.entity("Error: " + emisorUser + " already likes " + recieverUser).build();
			}
			break;
		case Notification.NEW_UNLIKE_COMPANY:
			if (company.getLikers().contains(emisorUser)) {
				company.removeLiker(emisorUser);
				entity = "Like to company removed successfully";
			} else {
				return Response.status(Status.CONFLICT).entity("Error: " + emisorUser + " doesn't like " + recieverUser)
						.build();
			}
		case Notification.CHEF_ACCEPTED:
			User chef = bt.getUserByEmail(recieverUser);
			chef.setChef(true);
			entity = "Chef request accepted.";
			
			break;
		case Notification.CHEF_DENIED:
			User user = bt.getUserByEmail(recieverUser);
			user.setChef(false);
			entity = "Chef request denied.";
			break;
		default:
			break;
		}

		notifObservable.setNotification(notification); // change in observable subject
		return Response.status(200).entity(entity).build();

	}

	public void follow(String recieverUser, String emisorUser) {
		User reciever = bt.getUserByEmail(recieverUser);
		Company company = splay.get(recieverUser);
		if (reciever != null) {
			reciever.addFollower(emisorUser);
		} else {
			company.addFollower(emisorUser);
		}
	}

	public void unfollow(String recieverUser, String emisorUser) {
		User reciever = bt.getUserByEmail(recieverUser);
		Company company = splay.get(recieverUser);
		if (reciever != null) {
			reciever.removeFollower(emisorUser);
		} else {
			company.removeFollower(emisorUser);
		}
	}

	/**
	 * Sets a new chef request which is going to be sent to the administrators' client
	 * @param request : JSONObject
	 * @return Response
	 */
	@POST
	@Path("/chef_request")
	public Response newChefRequest(JSONObject request) {
		String userEmail = (String) request.get("user");
		User user = bt.getUserByEmail(userEmail);
		if (user != null) {
			if (!user.isChef()) {
				notifObservable.setChefRequest(request);
				return Response.status(Status.OK).entity("Request sent successfully.").build();
			}else {
				return Response.status(Status.CONFLICT).entity("Error: user is a verified chef already.").build();
			}
		}else {
			return Response.status(Status.CONFLICT).entity("Error: couldn't find user for "+userEmail).build();
		}
		
	}
	
	@GET
	@Path("/get_chef_request")
	public Response getChefRequest() throws InterruptedException {
		this.observerUser = "admin";
		notifObservable.addObserver(this);
		System.out.println("observer for chef request added");
		int cont = 0;
		while (!sendNotif & cont < 40000) {
			Thread.sleep(1);
			cont++;
		}
		if (sendNotif) {
			JSONObject chefRequest = notifObservable.getChefRequest();
			sendNotif = false;
			return Response.status(Status.OK).entity(chefRequest).build();
		}else {
			return Response.status(Status.NO_CONTENT).entity("Timed out.").build();
		}
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
		User user = bt.getUserByEmail(observerUser);
		notifObservable.addObserver(this);
		System.out.println("observer added");
		int cont = 0;
		while (!sendNotif & cont < 40000) {
			Thread.sleep(1);
			cont++;
		}
		if (sendNotif) {
			notification = notifObservable.getNotification();
			user.addNotification(notification);
			ArrayList<Notification> notificationsList = user.getNotifications();
			sendNotif = false;
			return Response.status(Status.OK).entity(notificationsList).build();
		} else {
			return Response.status(Status.NO_CONTENT).entity("Timed out.").build();
		}
	}

	/**
	 * Inserts every user in the JSONArray to the BT
	 * 
	 * @param incomingData
	 * @return
	 */

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
				md5 = new MD5(password);
				try {
					password = md5.getMD5();
				} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;

			case "profilePic":
				profilePic = tokenizer.nextToken();
				break;
			case "company":
				company = Boolean.parseBoolean(tokenizer.nextToken());
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
					.withProfilePic(profilePic).withMyMenu(recipes).withCompany(company).build();
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
	public Response edit(@PathParam("userEmail") String userEmail, @Context UriInfo uriInfo) {
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
					md5 = new MD5(password);
					try {
						password = md5.getMD5();
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
			return Response.status(Status.NOT_FOUND).entity("Error: user not found for: " + userEmail).build();
		}
	}

	/**
	 * Edits base64 string for users' profile picture
	 * 
	 * @param jsonObject : JSONObject
	 * @return Response
	 */
	@PUT
	@Path("/profilePic")
	public Response editImage(JSONObject jsonObject) {
		String userName = (String) jsonObject.get("user");
		String picture = (String) jsonObject.get("image");
		User user = bt.getUserByEmail(userName);
		if (user != null) {
			user.setProfilePic(picture);
			return Response.status(Status.OK).entity("Profile picture updated successfully.").build();
		} else {
			return Response.status(Status.CONFLICT).entity("Error: user not found for:" + userName).build();
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		if (notifObservable.isNewChefRequest() & observerUser.equals("admin")) {
			this.sendNotif = true;
			notifObservable.setNewChefRequest(false);
		}
		else if (notifObservable.isNewNotif()
				& notifObservable.getNotification().getRecieverUser().equals(observerUser)) {
			this.sendNotif = true;
			notifObservable.setNewNotif(false);
		}
	}

}
