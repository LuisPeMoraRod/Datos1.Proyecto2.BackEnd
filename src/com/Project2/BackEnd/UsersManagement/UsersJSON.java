package com.Project2.BackEnd.UsersManagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.parser.ParseException;

import com.Project2.BackEnd.REST.Notification;
import com.Project2.BackEnd.RecipesManagement.Recipe;
import com.Project2.BackEnd.Trees.AVLTree;
import com.Project2.BackEnd.Trees.BinaryTree;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class UsersJSON {
	private User user;
	private BinaryTree<User> bt;
	private AVLTree<Recipe> avl;
	private static UsersJSON usersJson = null;

	private UsersJSON() {
		bt = BinaryTree.getInstance();
		avl = AVLTree.getInstance();
	}

	public BinaryTree<User> getBT() {
		return bt;
	}

	public void setBT(BinaryTree<User> bT) {
		bt = bT;
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
		String email = null, name = null, password = null, age = null, profilePic = null;
		ArrayList<String> usersFollowing = null, followers = null;
		int sortingType = 0;
		JSONArray jsonArray;
		boolean chef = false, admin = false;
		ArrayList<Notification> notifications = null;
		System.out.println(usersList);
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
					usersFollowing = new ArrayList<String>();
					jsonArray = (JSONArray) mapTemp.getValue();
					for (int j = 0; j < jsonArray.size(); j++) {
						usersFollowing.add((String) jsonArray.get(j));
					}
					break;
				case "followers":
					followers = new ArrayList<String>();
					jsonArray = (JSONArray) mapTemp.getValue();
					for (int j = 0; j < jsonArray.size(); j++) {
						followers.add((String) jsonArray.get(j));
					}
					break;
				case "sortingType":
					long sort = (long) mapTemp.getValue();
					sortingType = Integer.parseInt(Long.toString(sort));
					break;
				case "recipes":
					array = (ArrayList<Object>) mapTemp.getValue();
					recipesArray = parseRecipesArray(array);
					break;
				case "chef":
					chef = (boolean) mapTemp.getValue();
					break;
				case "admin":
					admin = (boolean) mapTemp.getValue();
					break;
				case "notifications": //parse notifications 
					notifications = new ArrayList<Notification>();

					jsonArray = (JSONArray) mapTemp.getValue();
					for (int j = 0; j < jsonArray.size(); j++) {
						String emisorUser = null, recieverUser = null, recipe = null, newComment = null;
						int notifType = 0;
						HashMap<String, Object> notifObj = (HashMap<String, Object>) usersList.get(j);
						for (Entry<String, Object> mapEntry : notifObj.entrySet()) {
							switch (mapEntry.getKey()) {
							case "emisorUser":
								emisorUser = (String) mapEntry.getValue();
								break;
							case "recieverUser":
								recieverUser = (String) mapEntry.getValue();
								break;
							case "recipe":
								recipe = (String) mapEntry.getValue();
								break;
							case "notifType":
								long type = (long) mapEntry.getValue();
								notifType = Integer.parseInt(Long.toString(type));
								break;
							case "newComment":
								newComment = (String) mapEntry.getValue();
								break;

							default:
								break;
							}
						}
						Notification notif = new Notification(emisorUser, recieverUser, notifType, newComment, recipe);
						notifications.add(0, notif);

					}

					break;
				default:
					break;
				}
			}
			this.user = User.builder().withEmail(email).withName(name).withPassword(password).withAge(age)
					.withProfilePic(profilePic).withUsersFollowing(usersFollowing).withFollowers(followers)
					.withMyMenu(recipesArray).withSortingType(sortingType).withChef(chef).withAdmin(admin)
					.withNotifications(notifications).build();

			this.bt.insert(user);
		}
	}

	@SuppressWarnings("unchecked")
	public ArrayList<Recipe> parseRecipesArray(ArrayList<Object> array) {
		ArrayList<Recipe> recipesArray = new ArrayList<Recipe>();
		ArrayList<String> likers = null;
		String name = null, author = null, type = null, portions = null, cookingSpan = null, eatingTime = null,
				tags = null, image = null, ingredients = null, steps = null, price = null;
		int difficulty = 0, id = 0, punctuation = 0, shares = 0;
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
					long dif = (Long) mapTemp.getValue();
					difficulty = Integer.parseInt(Long.toString(dif));
					break;
				case "id":
					long idl = (Long) mapTemp.getValue();
					id = Integer.parseInt(Long.toString(idl));
					break;
				case "punctuation":
					long punct = (Long) mapTemp.getValue();
					punctuation = Integer.parseInt(Long.toString(punct));
					break;
				case "shares":
					long shr = (long) mapTemp.getValue();
					shares = Integer.parseInt(Long.toString(shr));
					break;
				case "comments":
					comments = (HashMap<String, String>) mapTemp.getValue();
					break;
				case "likers":
					likers = new ArrayList<String>();
					JSONArray jsonArray = (JSONArray) mapTemp.getValue();
					for (int j = 0; j < jsonArray.size(); j++) {
						likers.add((String) jsonArray.get(j));
					}
					break;

				default:
					break;
				}
			}
			Recipe newRecipe = Recipe.builder().withName(name).withAuthor(author).withType(type).withPortions(portions)
					.withCookingSpan(cookingSpan).withEatingTime(eatingTime).withTags(tags).withImage(image)
					.withIngredients(ingredients).withSteps(steps).withPrice(price).withDifficulty(difficulty)
					.withId(id).withComments(comments).withPunctuation(punctuation).withLikers(likers).build();
			recipesArray.add(newRecipe);
			avl.insert(newRecipe);
			avl.insertToNewsfeed(newRecipe);
		}

		return recipesArray;

	}

}
