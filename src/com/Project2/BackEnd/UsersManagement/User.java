package com.Project2.BackEnd.UsersManagement;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import com.Project2.BackEnd.RecipesManagement.DoublyLinkedList;
import com.Project2.BackEnd.RecipesManagement.Recipe;
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
	private String usersFollowing;
	private String followers;
	private ArrayList<Recipe> responseList;
	private DoublyLinkedList myMenu;

	public User(Builder builder) {
		this.email = builder.email;
		this.name = builder.name;
		this.age = builder.age;
		this.password = builder.password;
		this.profilePic = builder.profilePic;
		this.usersFollowing = builder.usersFollowing;
		this.followers = builder.followers;
		//this.myMenu = new DoublyLinkedList();
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

	public DoublyLinkedList getProfile() {
		return myMenu;
	}

	public void setProfile(DoublyLinkedList myMenu) {
		this.myMenu = myMenu;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getUsersFollowing() {
		return usersFollowing;
	}

	public void setUsersFollowing(String usersFollowing) {
		this.usersFollowing = usersFollowing;
	}

	public String getFollowers() {
		return followers;
	}

	public void setFollowers(String followers) {
		this.followers = followers;
	}

	public static Builder builder() {
		return new Builder();
	}
	
	public void addRecipe(Recipe newRecipe) {
		Node<Recipe> node = new Node<Recipe>(newRecipe);
		int id = myMenu.getSize();
		node.setId(id);
		myMenu.sortedInsert(node, DoublyLinkedList.SORT_BY_DATE);
	}
	

	public static class Builder {
		private String email;
		private String name;
		private String age;
		private String password;
		private String profilePic;
		private String usersFollowing;
		private String followers;

		public User build() {
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

		public Builder withUsersFollowing(String usersFollowing) {
			this.usersFollowing = usersFollowing;
			return this;
		}

		public Builder withFollowers(String followers) {
			this.followers = followers;
			return this;
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

}}