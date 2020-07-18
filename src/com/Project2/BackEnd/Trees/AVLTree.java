package com.Project2.BackEnd.Trees;

import java.util.ArrayList;

import com.Project2.BackEnd.RecipesManagement.Recipe;

public class AVLTree<T extends Comparable<T>> extends Tree<T> {
	private static AVLTree<Recipe> avl = null;
	private ArrayList<Recipe> newsfeed;

    private  AVLTree(){
        this.setTypeTree("avl");
        this.newsfeed = new ArrayList<Recipe>();
    }
    
    public static synchronized AVLTree<Recipe> getInstance() {
    	if (avl == null) {
    		avl = new AVLTree<Recipe>();
    	}
    	return avl;
    }
    
    
    public ArrayList<Recipe> getNewsfeed() {
		return newsfeed;
	}

	public void setNewsfeed(ArrayList<Recipe> newsfeed) {
		this.newsfeed = newsfeed;
	}

	public void insertToNewsfeed(Recipe newRecipe) {
    	this.newsfeed.add(0, newRecipe);
    }
   
		

}
