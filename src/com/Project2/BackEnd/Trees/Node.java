package com.Project2.BackEnd.Trees;

public class Node<T extends Comparable<T>> {

    T element;
    Node<T> left;
    Node<T> right;

    public Node(T element){
        this(element, null, null);
    }

    /**
     * Constructs the node with its children
     * @param element
     * @param left
     * @param right
     */

    public Node(T element, Node<T> left, Node<T> right){
        this.element = element;
        this.left = left;
        this.right = right;
    }
    
    public T getElement() {
    	return this.element;
    }

}
