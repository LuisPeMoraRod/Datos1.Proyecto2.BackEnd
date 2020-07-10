package com.Project2.BackEnd.RecipesManagement;

import com.Project2.BackEnd.Trees.Node;

public class DoublyLinkedList <T>{
	Node first;
	Node last;
	
	
	public Node getFirst() {
		return first;
	}
	public void setFirst(Node first) {
		this.first = first;
	}
	public Node getLast() {
		return last;
	}
	public void setLast(Node last) {
		this.last = last;
	}
	

}
