package com.Project2.BackEnd.Trees;

public class Node<T extends Comparable<T>> {

	T element;
	Node<T> left;
	Node<T> right;
	int id;

	/**
	 * Constructs the node with its children
	 * 
	 * @param element
	 * @param left
	 * @param right
	 */

	public Node(T element, Node<T> left, Node<T> right) {
		this.element = element;
		this.left = left;
		this.right = right;
	}

	public Node<T> getLeft() {
		return left;
	}

	public void setLeft(Node<T> left) {
		this.left = left;
	}

	public Node<T> getRight() {
		return right;
	}

	public void setRight(Node<T> right) {
		this.right = right;
	}

	public Node(T element) {
		this(element, null, null);
	}

	public T getElement() {
		return this.element;
	}

	public void setElement(T element) {
		this.element = element;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
