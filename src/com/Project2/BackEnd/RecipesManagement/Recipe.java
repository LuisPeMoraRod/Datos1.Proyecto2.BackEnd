package com.Project2.BackEnd.RecipesManagement;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;

import com.Project2.BackEnd.Trees.Node;
import com.Project2.BackEnd.UsersManagement.User;

public class Recipe implements Comparable<Recipe>{
	/**
	 * Recipe class. Implements a builder pattern design.
	 * @author Luis Pedro Morales Rodriguez
	 * @version 9/7/2020
	 * 
	 */
	private String name;
	private String author;
	private String type;
	private String portions;
	private String cookingSpan;
	private String eatingTime;
	private String tags;
	private String image;
	private String ingredients;
	private String steps;
	private ArrayList<HashMap<String,String>> comments;
	private ArrayList<String> likers;
	private String price;
	private int difficulty;
	private int id;
	private int punctuation;
	private int shares;
	
	public ArrayList<HashMap<String,String>> getComments() {
		return comments;
	}

	public void setComments( ArrayList<HashMap<String,String>> comments) {
		this.comments = comments;
	}
	
	public void addComment(String user, String comment) {
		HashMap<String,String> commentHM = new HashMap<String,String>();
		commentHM.put(user,comment);
		this.comments.add(0,commentHM);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	
	public ArrayList<String> getLikers() {
		return likers;
	}

	public void addLiker(String liker) {
		this.likers.add(liker);
	}
	
	public void removeLiker(String liker) {
		this.likers.remove(liker);
	}

	public int getPunctuation() {
		return punctuation;
	}

	public void setPunctuation(int punctuation) {
		this.punctuation = punctuation;
	}
	
	public void incrementPunctuation() {
		this.punctuation++;
	}
	
	public void decrementPunctuation() {
		this.punctuation--;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPortions() {
		return portions;
	}

	public void setPortions(String portions) {
		this.portions = portions;
	}

	public String getCookingSpan() {
		return cookingSpan;
	}

	public void setCookingSpan(String cookingSpan) {
		this.cookingSpan = cookingSpan;
	}

	public String getEatingTime() {
		return eatingTime;
	}

	public void setEatingTime(String eatingTime) {
		this.eatingTime = eatingTime;
	}

	
	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getIngredients() {
		return ingredients;
	}

	public void setIngredients(String ingredients) {
		this.ingredients = ingredients;
	}

	public String getSteps() {
		return steps;
	}

	public void setSteps(String steps) {
		this.steps = steps;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}
	
	public int getShares() {
		return shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}
	
	public void incrementShares() {
		this.shares++;
	}
	
	public void decrementShares() {
		this.shares--;
	}
	
	public Recipe(Builder builder) {
		this.name = builder.name;
		this.author=builder.author;
		this.type = builder.type;
		this.portions = builder.portions;
		this.cookingSpan = builder.cookingSpan;
		this.eatingTime = builder.eatingTime;
		this.tags = builder.tags;
		this.difficulty = builder.difficulty;
		this.image = builder.image;
		this.ingredients = builder.ingredients;
		this.steps = builder.steps;
		this.price = builder.price;
		this.punctuation = builder.punctuation;
		this.id = builder.id;
		this.shares = builder.shares;
		this.comments = builder.comments;		
		this.likers = builder.likers;
	}
	
	public static Builder builder() {
		return new Builder();
	}

	public static class Builder
	{
		private String name;
		private String author;
		private String type;
		private String portions;
		private String cookingSpan;
		private String eatingTime;
		private String tags;
		private String image;
		private String ingredients;
		private String steps;
		private String price;
		private int punctuation;
		private int difficulty;
		private int id;
		private int shares;
		private  ArrayList<HashMap<String,String>> comments;
		private ArrayList<String> likers;
		
		
		public Recipe build() {
			if (comments == null) {
				comments = new  ArrayList<HashMap<String,String>>();
			}
			if (likers == null) {
				likers = new ArrayList<String>();
			}
			return new Recipe(this);
		}
		
		public Builder withName(String name) {
			this.name = name;
			return this;
		}
		
		public Builder withAuthor(String author) {
			this.author = author;
			return this;
		}
		
		public Builder withType(String type) {
			this.type = type;
			return this;
		}
		
		public Builder withPortions(String portions) {
			this.portions = portions;
			return this;
		}
		
		public Builder withCookingSpan(String cookingSpan) {
			this.cookingSpan = cookingSpan;
			return this;
		}
		
		public Builder withEatingTime(String eatingTime) {
			this.eatingTime = eatingTime;
			return this;
		}
		
		public Builder withDifficulty(int difficulty) {
			this.difficulty = difficulty;
			return this;
		}
		
		public Builder withTags(String tags) {
			this.tags = tags;
			return this;
		}
		
		public Builder withImage(String image) {
			this.image = image;
			return this;
		}
		
		public Builder withIngredients(String ingredients) {
			this.ingredients = ingredients;
			return this;
		}
		
		public Builder withSteps(String steps) {
			this.steps = steps;
			return this;
		}
		
		public Builder withPrice(String price) {
			this.price = price;
			return this;
		}
		
		
		public Builder withPunctuation(int punctuation) {
			this.punctuation = punctuation;
			return this;
		}
		
		public Builder withComments( ArrayList<HashMap<String,String>> comments) {
			this.comments = comments;
			return this;
		}
		
		public Builder withId(int id) {
			this.id = id;
			return this;
		}
		
		
		public Builder withShares(int shares) {
			this.shares = shares;
			return this;
		}
		
		public Builder withLikers(ArrayList<String> likers) {
			this.likers = likers;
			return this;
		}

	}

	@Override
	public int compareTo(Recipe o) {
		if (o == null) {
			return -1;
		}
		return this.name.compareTo(o.name.toString());
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
