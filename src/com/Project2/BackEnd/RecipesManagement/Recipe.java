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
	private HashMap<String,String> comments;
	private String price;
	private String picture;
	private int difficulty;
	private int id;
	private int punctuation;
	private int shares;
	
	public HashMap<String,String>  getComments() {
		return comments;
	}

	public void setComments(HashMap<String,String>  comments) {
		this.comments = comments;
	}
	
	public void addComment(String user, String comment) {
		
		this.comments.put(user, comment);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
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
		this.picture = builder.picture;
		this.shares = builder.shares;
		this.comments = new HashMap<String, String>();		
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
		private String picture;
		private int punctuation;
		private int difficulty;
		private int id;
		private int shares;
		private HashMap<String,String> comments;
		
		
		public Recipe build() {
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
		
		public Builder withComments(HashMap<String,String> comments) {
			this.comments = comments;
			return this;
		}
		
		public Builder withId(int id) {
			this.id = id;
			return this;
		}
		
		public Builder withPicture(String picture) {
			this.picture = picture;
			return this;
		}
		
		public Builder withShares(int shares) {
			this.shares = shares;
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
