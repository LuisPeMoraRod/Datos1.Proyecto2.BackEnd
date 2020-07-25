package com.Project2.BackEnd.REST;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.StringTokenizer;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.Status;

import org.json.simple.JSONObject;

import com.Project2.BackEnd.CompaniesManagement.Company;
import com.Project2.BackEnd.RecipesManagement.DoublyLinkedList;
import com.Project2.BackEnd.RecipesManagement.Recipe;
import com.Project2.BackEnd.Trees.AVLTree;
import com.Project2.BackEnd.Trees.BinaryTree;
import com.Project2.BackEnd.Trees.SplayTree;
import com.Project2.BackEnd.UsersManagement.MD5;
import com.Project2.BackEnd.UsersManagement.User;

@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompaniesResources implements RestResources,Observer {
	private static BinaryTree<User> bt = BinaryTree.getInstance();
	private static AVLTree<Recipe> avl = AVLTree.getInstance();
	private static SplayTree<Company> splay = SplayTree.getInstance();
	private String key, name = null, email = null, password = null, contact = null, image = null,
			serviceSchedule = null;
	ArrayList<Double> location = null;
	private ArrayList<String> admins = null, usersFollowing = null, followers = null;
	private int punctuation = 0, sortingType = 0;
	private ArrayList<Recipe> recipes = null;
	private DoublyLinkedList myMenu = null;
	private ArrayList<Notification> notifications = null;
	private MD5 md5;
	private boolean sendNotif = false;
	private String observerCompany;
	private Notification notification;
	
	/**
	 * Get specific company
	 */
	@GET
	@Path("/{companyEmail}")
	@Override
	public Response get(@PathParam("companyEmail") String companyEmail) {
		Company company = splay.get(companyEmail);
		if (company != null) {
			return Response.status(Status.OK).entity(company).build();
		}else {
			return Response.status(Status.NOT_FOUND).entity("Company not found for "+companyEmail).build();
		}
	}

	/**
	 * Returns all the registered companies (sorted by alphabetical order) as a
	 * response to the client
	 * 
	 * @return responseList : Response
	 */
	@GET
	@Override
	public Response getAll() {
		ArrayList<Company> responseList = splay.getList();
		return Response.ok(responseList).build();
	}

	
	@SuppressWarnings("rawtypes")
	@POST
	@Override
	public Response register(@Context UriInfo uriInfo) {

		for (Map.Entry entry : uriInfo.getQueryParameters().entrySet()) {
			key = entry.getKey().toString();
			StringTokenizer tokenizer = new StringTokenizer(entry.getValue().toString(), "[ // ]");
			tokenizer = new StringTokenizer(tokenizer.nextToken());
			String encoded = tokenizer.nextToken();
			tokenizer = new StringTokenizer(encoded, "_");
			String value = "";
			while (tokenizer.hasMoreTokens()) {
				value += tokenizer.nextToken() + " ";
			}
			switch (key) {
			case "email":
				email = encoded;
				break;
			case "password":
				password = encoded;
				md5 = new MD5(password);
				try {
					password = md5.getMD5();
				} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			case "name":
				name = value;
				break;
			case "contact":
				contact = value;
				break;
			case "serviceSchedule":
				serviceSchedule = value;
				break;
			case "location":
				//location = value;
				location = new ArrayList<Double>();
				tokenizer = new StringTokenizer(encoded,",");
				while(tokenizer.hasMoreElements()) {
					Double loc = Double.parseDouble(tokenizer.nextToken());
					location.add(loc);
				}
				break;
			default:
				break;
			}
		}
		if (splay.get(email) == null) {
			ArrayList<Recipe> recipes = null;
			Company newCompany = Company.builder().withEmail(email).withPassword(password).withName(name)
					.withContact(contact).withServiceSchedule(serviceSchedule).withLocation(location)
					.withMyMenu(recipes).build();

			splay.insert(newCompany);
			return Response.status(201).entity(newCompany).build();
		} else {
			return Response.status(Status.CONFLICT).entity("Email already in use").build();
		}

	}

	@POST
	@Path("/{companyEmail}")
	public Response addAdmin(@PathParam("companyEmail") String companyEmail, @QueryParam("email") String email) {
		Company company = splay.get(companyEmail);
		if (company != null) {
			User user = bt.getUserByEmail(email);

			if (user != null) {
				if (!company.getAdmins().contains(email)) {
					company.addAdmin(email);
					return Response.status(Status.OK).entity("Admin added successfully.").build();
				}else {
					return Response.status(Status.CONFLICT).entity("Error: admin already exists.").build();
				}
			} else {
				return Response.status(Status.NOT_FOUND).entity("Error: user not found for: " + email).build();
			}
		} else {
			return Response.status(Status.NOT_FOUND).entity("Error: company not found for: " + companyEmail).build();
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
	@Override
	public Response editImage(JSONObject jsonObject) {
		String companyEmail = (String) jsonObject.get("company");
		String picture = (String) jsonObject.get("image");
		Company company = splay.get(companyEmail);
		if (company != null) {
			company.setImage(picture);
			return Response.status(Status.OK).entity("Profile picture updated successfully.").build();
		} else {
			return Response.status(Status.NOT_FOUND).entity("Error: company not found for " + companyEmail).build();
		}

	}

	@SuppressWarnings("rawtypes")
	@PUT
	@Path("/{companyEmail}")
	@Override
	public Response edit(@PathParam("companyEmail") String companyEmail, UriInfo uriInfo) {
		Company company = splay.get(companyEmail);
		if (company != null) {
			for (Map.Entry entry : uriInfo.getQueryParameters().entrySet()) {
				key = entry.getKey().toString();
				StringTokenizer tokenizer = new StringTokenizer(entry.getValue().toString(), "[ // ]");
				tokenizer = new StringTokenizer(tokenizer.nextToken());
				String encoded = tokenizer.nextToken();
				tokenizer = new StringTokenizer(encoded, "_");
				String value = "";
				while (tokenizer.hasMoreTokens()) {
					value += tokenizer.nextToken() + " ";
				}
				switch (key) {
				case "password":
					password = encoded;
					md5 = new MD5(password);
					try {
						password = md5.getMD5();
					} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					company.setPassword(password);
					break;
				case "name":
					name = value;
					company.setName(name);
					break;
				case "contact":
					contact = value;
					company.setContact(contact);
					break;
				case "serviceSchedule":
					serviceSchedule = value;
					company.setServiceSchedule(serviceSchedule);
					break;
				case "location":
					//location = value;
					company.setLocation(location);
					break;
				default:
					break;
				}
			}
			return Response.status(201).entity(company).build();
		} else {
			return Response.status(Status.NOT_FOUND).entity("Error: company not found for " + companyEmail).build();
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
	public Response getNotification(@QueryParam("observerCompany") String observerCompany) throws InterruptedException {
		this.observerCompany = observerCompany;
		Company company = splay.get(observerCompany);
		UsersResources.notifObservable.addObserver(this);
		System.out.println("observer added");
		int cont = 0;
		while (!sendNotif & cont<40000) {
			Thread.sleep(1);
			cont++;
		}
		if (sendNotif) {
			notification = UsersResources.notifObservable.getNotification();
			company.addNotification(notification);
			ArrayList<Notification> notificationsList = company.getNotifications();
			sendNotif = false;
			return Response.status(Status.OK).entity(notificationsList).build();
		}else {
			return Response.status(Status.NO_CONTENT).entity("Timed out.").build();
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (UsersResources.notifObservable.isNewNotif()
				& UsersResources.notifObservable.getNotification().getRecieverUser().equals(observerCompany)) {
			this.sendNotif = true;
			UsersResources.notifObservable.setNewNotif(false);
		}
		
	}

}
