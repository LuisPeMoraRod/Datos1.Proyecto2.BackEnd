package com.Project2.BackEnd.RecipesManagement;

import java.util.Arrays;

import com.Project2.BackEnd.Trees.Node;

public class DoublyLinkedList {
	private Node<Recipe> first;
	private Node<Recipe> last;
	private int size;
	private static final int SORT_BY_DATE = 0, SORT_BY_PUNCTUATION = 1, SORT_BY_DIFFICULTY = 2;
	private int date, punctuation, difficulty;

	public DoublyLinkedList() {
		first = null;
		last = null;
	}

	public Node<Recipe> getFirst() {
		return first;
	}

	public void setFirst(Node<Recipe> first) {
		this.first = first;
	}

	public Node<Recipe> getLast() {
		return last;
	}

	public void setLast(Node<Recipe> last) {
		this.last = last;
	}

	public int getSize() {
		return this.size;
	}

	/**
	 * Inserts new node at the end of the list
	 * @param newNode : Node<Recipe>
	 */
	public void insertEnd(Node<Recipe> newNode) {
		if (size == 0) {
			first = newNode;
			last = newNode;
		} else {
			Node<Recipe> temp = last;
			newNode.setLeft(temp);
			temp.setRight(newNode);
			last = newNode;
		}
		size++;

	}

	/**
	 * Inserts node as the head of the list
	 * 
	 * @param newNode : Node<Recipe>
	 */
	public void insertHead(Node<Recipe> newNode) {
		if (size == 0) {
			first = newNode;
			last = newNode;
		} else {
			Node<Recipe> temp = first;
			newNode.setRight(temp);
			temp.setLeft(newNode);
			first = newNode;
		}
		size++;
	}

	/**
	 * Inserts new node in a sorted list
	 * 
	 * @param newNode
	 * @param type
	 */
	public void sortedInsert(Node<Recipe> newNode, int type) {
		if (size == 0) {
			first = newNode;
			last = newNode;
			size++;
		} else if (type == SORT_BY_DATE) {
			int date = newNode.getId();
			if (date < first.getId()) {
				insertHead(newNode);
			} else if (date > last.getId()) {
				insertEnd(newNode);
			} else {
				Node<Recipe> pointer = first;
				while (date > pointer.getId()) {
					pointer = pointer.getRight();
				}
				insert(newNode, pointer);
			}
		}
		
		else if (type == SORT_BY_DIFFICULTY) {
			int difficulty = newNode.getElement().getDifficulty();
			if (difficulty < first.getElement().getDifficulty()) {
				insertHead(newNode);
			} else if (difficulty > last.getElement().getDifficulty()) {
				insertEnd(newNode);
			} else {
				Node<Recipe> pointer = first;
				while (difficulty > pointer.getElement().getDifficulty()) {
					pointer = pointer.getRight();
				}
				insert(newNode, pointer);
			}
		}
		else if (type == SORT_BY_PUNCTUATION) {
			int punctuation = newNode.getElement().getPunctuation();
			if (punctuation < first.getElement().getPunctuation()) {
				insertHead(newNode);
			} else if (punctuation > last.getElement().getPunctuation()) {
				insertEnd(newNode);
			} else {
				Node<Recipe> pointer = first;
				while (punctuation > pointer.getElement().getPunctuation()) {
					pointer = pointer.getRight();
				}
				insert(newNode, pointer);
			}
		}

	}

	/**
	 * Inserts new node previous to pointer node
	 * 
	 * @param newNode : Node<Recipe>
	 * @param pointer : Node<Recipe>
	 */
	public void insert(Node<Recipe> newNode, Node<Recipe> pointer) {
		Node<Recipe> prev = pointer.getLeft();
		prev.setRight(newNode);
		pointer.setLeft(newNode);

		newNode.setLeft(prev);
		newNode.setRight(pointer);
		size++;
	}
	
	/**
	 * Gets a recipe from an specific node
	 * @param index of the node that is being accessed 
	 * @return the recipe located in that index
	 * 
	 */
	
	 public Recipe getRecipe(int index){

	    Node<Recipe> current = this.first;
	        int reference = 0;

	        while(current != null){

	            if(reference == index){

	                return current.getElement();
	            }

	            else if(index>getSize()-1){
	                System.out.println("Index out of limits");
	                return null;
	            }

	            else{
	                reference++;
	                current = current.getRight();
	            }

	        }
	        return null;

	    }
	 
	 public void modifyValue(Recipe r, int index){

	        Node<Recipe> current = this.first;
	        int reference = 0;

	        while(current != null){

	            if(reference == index){

	                current.setElement(r);
	                return;
	            }

	            else if(index>getSize()-1){
	                System.out.println("Index out of limits");
	                return;
	            }

	            else{
	                reference++;
	                current = current.getRight();
	            }

	        }
	        return;

	    }

	/**
	 * Sorts using bubble sort method
	 */
	public void Bubblesort() {
		Node<Recipe> pointer = last.getRight();
		Node<Recipe> temp;
		for (int i = 0; i < size; i++) {
			temp = first;
			while (temp != null) {
				try {
					if (temp.getId() > temp.getRight().getId()) {
						swap(temp, temp.getRight(), pointer);
					} else {
						temp = temp.getRight();
					}
				} catch (Exception e) {
					temp = temp.getRight();
				}
			}
			try {
				pointer = pointer.getLeft();
			} catch (Exception e) {
				pointer = last;
			}
		}
	}

	/**
	 * Swaps the position of two consecutive nodes
	 * @param pointer
	 * @param nextPointer
	 * @param stopReference
	 */
	public void swap(Node<Recipe> pointer, Node<Recipe> nextPointer, Node<Recipe> stopReference) {
		Node<Recipe> leftNode = pointer.getLeft(), rightNode = nextPointer.getRight();
		if (leftNode == null) {
			pointer.setRight(rightNode);
			rightNode.setLeft(pointer);

			pointer.setLeft(nextPointer);

			nextPointer.setLeft(null);
			nextPointer.setRight(pointer);

			first = nextPointer;
		}

		else if (rightNode == null) {
			leftNode.setRight(nextPointer);
			nextPointer.setLeft(leftNode);

			nextPointer.setRight(pointer);

			pointer.setLeft(nextPointer);
			pointer.setRight(null);
			last = pointer;
			stopReference = last;
		} else {
			leftNode.setRight(nextPointer);
			rightNode.setLeft(pointer);

			pointer.setRight(rightNode);
			nextPointer.setLeft(leftNode);

			nextPointer.setRight(pointer);
			pointer.setLeft(nextPointer);
		}

	}
	
	/**
     * Method that returns the maximum value of the elements of the list
     * @return the maximum value found
     */

    public  int getMax(){
        int max = this.getFirst().getElement().getDifficulty();
        
        for (int i = 1; i < this.getSize(); i++) {
        	if(this.getRecipe(i).getDifficulty()>max) {
        		max = this.getRecipe(i).getDifficulty();
        	}
        	
        }

        return max;
    }
    
    /**
     * This is an auxiliary method to implement the Radix sort algorithm
     * It is used to create a counting array according to the digit represented in a certain position.
     * 
     * @param exp is used to select a digit from a specific position of a int value
     */

    private void countSort(int exp){

    	Recipe output[] = new Recipe[this.getSize()];
        int count[] = new int[10];
        Arrays.fill(count,0);

        for (int i = 0; i < this.getSize(); i++)
            count[ (this.getRecipe(i).getDifficulty()/exp)%10 ]++;

        for (int i = 1; i < 10; i++)
            count[i] += count[i - 1];

        for (int i = this.getSize() - 1; i >= 0; i--)
        {
            output[count[ (this.getRecipe(i).getDifficulty()/exp)%10 ] - 1] = this.getRecipe(i);
            count[ (this.getRecipe(i).getDifficulty()/exp)%10 ]--;
        }

        for (int i = 0; i < this.getSize(); i++)
            this.modifyValue(output[i], i);
    }
    
    /**
     * This method is used to arrange the recipes according to its difficulty
     */
    
    public void radixSort() {
        int max = getMax();

        for (int exp = 1; max/exp > 0; exp *= 10)
            countSort(exp);
    }
    
    /**
     * Quick sort algorithm is used to arrange the recipes according to its punctuation
     */
    
    public void quickSort() {
    	
    	if (this.getSize()==0){
    		return;
    	}
    	
    	this.quickSort(0, this.getSize()-1);
    	}
    
	private void quickSort(int low, int high) {
		int i = low, j = high;
		
		int pivot = this.getRecipe(i +(j-i)/2).getPunctuation();
		Recipe leftPivot;
		Recipe rightPivot;
		
		while(i <= j) {
			while(this.getRecipe(i).getPunctuation() < pivot) {
				i++;
			}
			while(this.getRecipe(j).getPunctuation() > pivot) {
				j--;
			}
			
			if(i <= j) {
				leftPivot = this.getRecipe(i);
				rightPivot = this.getRecipe(j);
				
				this.modifyValue(leftPivot, j);
				this.modifyValue(rightPivot, i);
				
				i++;
				j--;
			}
			
		}
		
		if(low < j) {
			this.quickSort(low, j);
		}
		if(i < high) {
			this.quickSort(i,high);
		}
	}

	public void printList() {
		Node<Recipe> temp = first;
		String list = "";
		for (int i = 0; i < size; i++) {
			list += temp.getElement().toString() + " ";
			temp = temp.getRight();
		}
		System.out.println(list);
	}

	

}
