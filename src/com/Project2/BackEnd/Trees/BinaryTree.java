package com.Project2.BackEnd.Trees;

import com.Project2.BackEnd.UsersManagement.User;

public class BinaryTree<T extends Comparable<T>> extends Tree<T> {
	
	private static BinaryTree<User> binaryTree = null;

    private BinaryTree(){

        this.setTypeTree("bst");
    }
    
    /**
	 * Public static method that returns the unique instance of the class. Uses
	 * thread safeties
	 */

    
    public static synchronized BinaryTree<User> getInstance() {
		if (binaryTree == null) {
			binaryTree = new BinaryTree<User>();
		}
		return binaryTree;
	}
    
    /**
	 * Returns a user object. Searches the user by its email
	 * @param email : String
	 * @return element : T
	 */
	public T getUserByEmail(String email) {
		return this.getUserByEmail(email, this.root);
	}
	
	private T getUserByEmail(String email, NodeTree<T> node) {
		if (node == null) {
			return null;
		}
		int comparisonResult = email.compareTo((node.getElement().toString()));
		
		if (comparisonResult < 0) {
			return getUserByEmail(email, node.left);
		}
		else if (comparisonResult > 0) {
			return getUserByEmail(email, node.right);
		}else {
			return node.element;
		}
		
		
		
	}



}
