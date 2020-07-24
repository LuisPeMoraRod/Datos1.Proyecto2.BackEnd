package com.Project2.BackEnd.UsersManagement;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.Project2.BackEnd.REST.Notification;
import com.Project2.BackEnd.REST.RecipesResources;
import com.Project2.BackEnd.RecipesManagement.DoublyLinkedList;
import com.Project2.BackEnd.RecipesManagement.Recipe;
import com.Project2.BackEnd.Trees.AVLTree;
import com.Project2.BackEnd.Trees.Node;

public class User implements Comparable<User> {
	/**
	 * User objects class
	 * 
	 * @author Luis Pedro Morales Rodriguez
	 * @version 1/7/2020
	 */

	private String email;
	private String name;
	private String age;
	private String password;
	private String profilePic;
	private ArrayList<String> usersFollowing;
	private ArrayList<String> followers;
	private ArrayList<Recipe> recipes;
	private DoublyLinkedList myMenu;
	private int sortingType;
	private boolean chef;
	private boolean admin;
	private boolean company;
	private ArrayList<Notification> notifications;
	private AVLTree<Recipe> avl;

	public User(Builder builder) {
		this.email = builder.email;
		this.name = builder.name;
		this.age = builder.age;
		this.password = builder.password;
		this.profilePic = builder.profilePic;
		this.usersFollowing = builder.usersFollowing;
		this.followers = builder.followers;
		this.myMenu = builder.myMenu;
		this.sortingType = builder.sortingType;
		this.recipes = builder.recipes;
		this.chef = builder.chef;
		this.admin = builder.admin;
		this.company = builder.company;
		this.notifications = builder.notifications;
		this.avl = AVLTree.getInstance();
	}

	// Getters and setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public ArrayList<String> getUsersFollowing() {
		return this.usersFollowing;
	}

	public void addUserFollowing(String newFollowing) {
		this.usersFollowing.add(newFollowing);
	}

	public void removeUserFollowing(String user) {
		this.usersFollowing.remove(user);
	}

	public void setUsersFollowing(ArrayList<String> usersFollowing) {
		this.usersFollowing = usersFollowing;
	}

	public ArrayList<String> getFollowers() {
		return followers;
	}

	public void setFollowers(ArrayList<String> followers) {
		this.followers = followers;
	}

	public void addFollower(String follower) {
		this.followers.add(follower);
	}

	public void removeFollower(String follower) {
		this.followers.remove(follower);
	}

	public int getSortingType() {
		return this.sortingType;
	}

	public void setSortingType(int sortingType) {
		switch (sortingType) {
		case 0:
			myMenu.bubblesort();
			myMenu.printList();
			break;
		case 1:
			myMenu.quickSort();
			myMenu.printList();
			break;
		case 2:
			myMenu.radixSort();
			myMenu.printList();
			break;

		default:
			break;
		}

		this.sortingType = sortingType;
	}

	public boolean isChef() {
		return chef;
	}

	public void setChef(boolean chef) {
		this.chef = chef;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isCompany() {
		return company;
	}

	public void setCompany(boolean company) {
		this.company = company;
	}

	public void addRecipe(Recipe newRecipe) {
		Node<Recipe> node = new Node<Recipe>(newRecipe);
		myMenu.sortedInsert(node, this.sortingType);
	}

	public void removeRecipe(Recipe recipe) {
		myMenu.remove(recipe);
	}

	public void removeRecipes() {
		while (!recipes.isEmpty()) {
			for (int i = 0; i < recipes.size(); i++) {
				recipes.remove(i);
			}
		}
	}

	public void setRecipes() {
		removeRecipes();
		Node<Recipe> pointer = myMenu.getFirst();
		while (pointer != null) {
			Recipe recipe = pointer.getElement();
			if (this.sortingType == DoublyLinkedList.SORT_BY_DATE) {
				recipes.add(0, recipe);
			} else {
				recipes.add(recipe);
			}
			pointer = pointer.getRight();
		}
	}

	public ArrayList<Recipe> getRecipes() {
		setRecipes();
		return recipes;
	}

	public ArrayList<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(ArrayList<Notification> notifications) {
		this.notifications = notifications;
	}

	public void addNotification(Notification notification) {
		this.notifications.add(0, notification);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String email;
		private String name;
		private String age;
		private String password;
		private String profilePic;
		private ArrayList<String> usersFollowing;
		private ArrayList<String> followers;
		private ArrayList<Recipe> recipes;
		private DoublyLinkedList myMenu;
		private boolean admin;
		private boolean chef;
		private boolean company;
		private int sortingType;
		private ArrayList<Notification> notifications;

		public User build() {
			if (recipes == null) {
				recipes = new ArrayList<Recipe>();
			}
			if (usersFollowing == null) {
				usersFollowing = new ArrayList<String>();
			}
			if (followers == null) {
				followers = new ArrayList<String>();
			}

			if (notifications == null) {
				notifications = new ArrayList<Notification>();
			}
			if (myMenu == null) {
				myMenu = new DoublyLinkedList();
			}
			return new User(this);
		}

		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}

		public Builder withName(String name) {
			this.name = name;
			return this;
		}

		public Builder withAge(String age) {
			this.age = age;
			return this;
		}

		public Builder withPassword(String password) {
			this.password = password;
			return this;
		}

		public Builder withProfilePic(String profilePic) {
			this.profilePic = profilePic;
			return this;
		}

		public Builder withUsersFollowing(ArrayList<String> usersFollowing) {
			this.usersFollowing = usersFollowing;
			return this;
		}

		public Builder withFollowers(ArrayList<String> followers) {
			this.followers = followers;
			return this;
		}

		public Builder withSortingType(int sortingType) {
			this.sortingType = sortingType;
			return this;
		}

		public Builder withMyMenu(ArrayList<Recipe> recipes) {
			this.recipes = recipes;
			this.myMenu = parseArrayToLinkedList(recipes);
			return this;

		}

		public Builder withChef(boolean chef) {
			this.chef = chef;
			return this;
		}

		public Builder withAdmin(boolean admin) {
			this.admin = admin;
			return this;
		}
		
		public Builder withCompany(boolean company) {
			this.company = company;
			return this;
		}

		public Builder withNotifications(ArrayList<Notification> notifications) {
			this.notifications = notifications;
			return this;
		}

		private DoublyLinkedList parseArrayToLinkedList(ArrayList<Recipe> recipes) {
			DoublyLinkedList myMenu = new DoublyLinkedList();
			Node<Recipe> node;
			if (recipes != null) {
				for (Recipe recipe : recipes) {
					node = new Node<Recipe>(recipe);
					myMenu.sortedInsert(node, sortingType);
				}
			}
			return myMenu;
		}

	}

	@Override
	public int compareTo(User o) {
		if (o == null) {
			return -1;
		}
		return this.email.compareTo(o.email.toString());
	}

	/**
	 * Overrided toString method so that it returns de user's email
	 */
	@Override
	public String toString() {
		return this.email;

	}
}