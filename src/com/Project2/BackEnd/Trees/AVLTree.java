package com.Project2.BackEnd.Trees;

import java.util.ArrayList;

import com.Project2.BackEnd.RecipesManagement.DoublyLinkedList;
import com.Project2.BackEnd.RecipesManagement.Recipe;

public class AVLTree<T extends Comparable<T>> extends Tree<T> {
	private static AVLTree<Recipe> avl = null;
	private ArrayList<Recipe> newsfeed;
	private DoublyLinkedList newsfeedLinkedList;

	private AVLTree() {
		super.size = 0;
		this.setTypeTree("avl");
		this.newsfeed = new ArrayList<Recipe>();
		this.newsfeedLinkedList = new DoublyLinkedList();
	}

	public static synchronized AVLTree<Recipe> getInstance() {
		if (avl == null) {
			avl = new AVLTree<Recipe>();
		}
		return avl;
	}

	public ArrayList<Recipe> getNewsfeed() {
		return this.newsfeed;
	}

	public void setNewsfeed() {
		removeRecipes();
		Node<Recipe> pointer = newsfeedLinkedList.getLast();
		while (pointer != null) {
			Recipe recipe = pointer.getElement();
			newsfeed.add(recipe);
			pointer = pointer.getLeft();
		}
	}

	public void removeRecipes() {
		while (!newsfeed.isEmpty()) {
			for (int i = 0; i < newsfeed.size(); i++) {
				newsfeed.remove(i);
			}
		}
	}

	public void insertToNewsfeed(Recipe newRecipe) {
		Node<Recipe> node = new Node<Recipe>(newRecipe);
		newsfeedLinkedList.sortedInsert(node, DoublyLinkedList.SORT_BY_DATE);
		setNewsfeed();
		// this.newsfeed.add(0, newRecipe);
	}

}
