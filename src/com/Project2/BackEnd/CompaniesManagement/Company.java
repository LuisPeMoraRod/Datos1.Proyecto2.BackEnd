package com.Project2.BackEnd.CompaniesManagement;

import java.util.ArrayList;

import com.Project2.BackEnd.REST.Notification;
import com.Project2.BackEnd.RecipesManagement.DoublyLinkedList;
import com.Project2.BackEnd.RecipesManagement.Recipe;
import com.Project2.BackEnd.Trees.Node;

public class Company implements Comparable<Company>{
	private String name;
	private String email;
	private String password;
	private String contact;
	private String image;
	private String serviceSchedule;
	private ArrayList<Double> location;
	private int punctuation;
	private int sortingType;
	private DoublyLinkedList myMenu;
	private ArrayList<String> admins;
	private ArrayList<String> usersFollowing;
	private ArrayList<String>  followers;
	private ArrayList<String> likers;
	private ArrayList<Recipe> recipes;
	private ArrayList<Notification> notifications;
	
	public Company(Builder builder) {
		this.name = builder.name;
		this.email = builder.email;
		this.password = builder.password;
		this.contact = builder.contact;
		this.image = builder.image;
		this.serviceSchedule = builder.serviceSchedule;
		this.location = builder.location;
		this.admins = builder.admins;
		this.punctuation = builder.punctuation;
		this.usersFollowing = builder.usersFollowing;
		this.followers = builder.followers;
		this.likers = builder.likers;
		this.recipes = builder.recipes;
		this.myMenu = builder.myMenu;
		this.sortingType = builder.sortingType;
		this.notifications = builder.notifications;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getServiceSchedule() {
		return serviceSchedule;
	}
	public void setServiceSchedule(String serviceSchedule) {
		this.serviceSchedule = serviceSchedule;
	}
	public ArrayList<Double> getLocation() {
		return location;
	}
	public void setLocation(ArrayList<Double> location) {
		this.location = location;
	}
	public ArrayList<String> getAdmins() {
		return admins;
	}
	public void setAdmins(ArrayList<String> admins) {
		this.admins = admins;
	}
	public void addAdmin(String newAdmin) {
		this.admins.add(newAdmin);
	}
	public int getPunctuation() {
		return punctuation;
	}
	public void setPunctuation(int punctuation) {
		this.punctuation = punctuation;
	}
	
	
	public ArrayList<String> getUsersFollowing() {
		return usersFollowing;
	}

	public void setUsersFollowing(ArrayList<String> usersFollowing) {
		this.usersFollowing = usersFollowing;
	}
	
	public void addUserFollowing(String newFollowing) {
		this.usersFollowing.add(newFollowing);
	}
	
	public void removeUserFollowing(String user) {
		this.usersFollowing.remove(user);
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

	
	public ArrayList<String> getLikers() {
		return likers;
	}

	public void setLikers(ArrayList<String> likers) {
		this.likers = likers;
	}
	
	public void addLiker(String newLiker) {
		this.likers.add(newLiker);
		this.punctuation++;
	}
	
	public void removeLiker(String liker) {
		this.likers.remove(liker);
		this.punctuation--;
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
			recipes.add(recipe);
			pointer = pointer.getRight();
		}
	}
	
	public ArrayList<Recipe> getRecipes() {
		setRecipes();
		return recipes;
	}


	public int getSortingType() {
		return sortingType;
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
	
	public static class Builder{
		private String name;
		private String email;
		private String password;
		private String contact;
		private String image;
		private String serviceSchedule;
		private ArrayList<Double> location;
		private int punctuation;
		private int sortingType;
		private DoublyLinkedList myMenu;
		private ArrayList<String> admins;
		private ArrayList<String> usersFollowing;
		private ArrayList<String>  followers;
		private ArrayList<String> likers;
		private ArrayList<Recipe> recipes;
		private ArrayList<Notification> notifications;
		
		public Company build() {
			if (admins == null) {
				admins = new ArrayList<String>();
			}
			if (recipes == null) {
				recipes = new ArrayList<Recipe>();
			}
			if (usersFollowing == null) {
				usersFollowing = new ArrayList<String>();
			}
			if (followers == null) {
				followers = new ArrayList<String>();
			}
			if (likers == null) {
				likers = new ArrayList<String>();
			}
			
			if (notifications == null) {
				notifications = new ArrayList<Notification>();
			}
			if (myMenu == null) {
				myMenu = new DoublyLinkedList();
			}
			return new Company(this);
		}
		
		public Builder withName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder withEmail(String email) {
			this.email = email;
			return this;
		}
		
		public Builder withPassword(String password) {
			this.password = password;
			return this;
		}
		
		public Builder withContact(String contact) {
			this.contact = contact;
			return this;
		}
		
		public Builder withImage(String image) {
			this.image = image;
			return this;
		}
		
		public Builder withServiceSchedule(String serviceSchedule) {
			this.serviceSchedule = serviceSchedule;
			return this;
		}
		
		public Builder withLocation(ArrayList<Double> location) {
			this.location = location;
			return this;
		}
		
		public Builder withAdmins(ArrayList<String> admins) {
			this.admins = admins;
			return this;
		}
		
		public Builder withPunctuation(int punctuation) {
			this.punctuation = punctuation;
			return this;
		}
		
		public Builder withUsersFollowing(ArrayList<String> usersFollowing) {
			this.usersFollowing = usersFollowing;
			return this;
		}

		public Builder withFollowers(ArrayList<String>  followers) {
			this.followers = followers;
			return this;
		}
		public Builder withLikers(ArrayList<String> likers) {
			this.likers = likers;
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
	public int compareTo(Company o) {
		if (o == null) {
			return -1;
		}
		
		return this.email.compareTo(o.email);
	}
	
	@Override 
	public String toString() {
		return this.email;
	}
}
