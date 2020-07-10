package com.Project2.BackEnd.RecipesManagement;

import java.awt.image.BufferedImage;

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
	private BufferedImage image;
	private String[] ingredients;
	private String[] steps;
	private String price;
	private Node<Recipe> pointerToNodeInList;
	private int difficulty;
	private int id;
	private int punctuation;
	
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

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}

	public String[] getIngredients() {
		return ingredients;
	}

	public void setIngredients(String[] ingredients) {
		this.ingredients = ingredients;
	}

	public String[] getSteps() {
		return steps;
	}

	public void setSteps(String[] steps) {
		this.steps = steps;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Node<Recipe> getPointerToNodeInList() {
		return pointerToNodeInList;
	}

	public void setPointerToNodeInList(Node<Recipe>  pointerToNodeInList) {
		this.pointerToNodeInList = pointerToNodeInList;
	}

	public Recipe(Builder builder) {
		this.name = builder.name;
		this.author=builder.author;
		this.type = builder.type;
		this.portions = builder.portions;
		this.cookingSpan = builder.cookingSpan;
		this.eatingTime = builder.eatingTime;
		this.difficulty = builder.difficulty;
		this.image = builder.image;
		this.ingredients = builder.ingredients;
		this.steps = builder.steps;
		this.price = builder.price;
		
		
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
		private int difficulty;
		private BufferedImage image;
		private String[] ingredients;
		private String[] steps;
		private String price;
		private Node<Recipe> pointerToNodeInList;
		private String rate;
		
		
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
		
		public Builder withImage(BufferedImage image) {
			this.image = image;
			return this;
		}
		
		public Builder withIngredients(String[] ingredients) {
			this.ingredients = ingredients;
			return this;
		}
		
		public Builder withSteps(String[] steps) {
			this.steps = steps;
			return this;
		}
		
		public Builder withPrice(String price) {
			this.price = price;
			return this;
		}
		
		public Builder withPointer(Node<Recipe> pointerToNodeInList) {
			this.pointerToNodeInList = pointerToNodeInList;
			return this;
		}

	}

	@Override
	public int compareTo(Recipe o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

}
