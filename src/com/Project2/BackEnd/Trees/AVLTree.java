package com.Project2.BackEnd.Trees;

import com.Project2.BackEnd.RecipesManagement.Recipe;

public class AVLTree<T extends Comparable<T>> extends Tree<T> {
	private static AVLTree<Recipe> avl = null;

    private  AVLTree(){
        this.setTypeTree("avl");
    }
    
    public static synchronized AVLTree<Recipe> getInstance() {
    	if (avl == null) {
    		avl = new AVLTree<Recipe>();
    	}
    	return avl;
    }
}
