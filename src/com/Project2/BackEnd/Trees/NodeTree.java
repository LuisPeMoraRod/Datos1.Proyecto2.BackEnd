package com.Project2.BackEnd.Trees;

/**
 * This class abstractize a node from a BST
 *
 * @author moniwaterhouse
 * @since 06/20/2020
 *
 * @param <T> allows the use of generics
 */

public class NodeTree<T extends Comparable<T>> {

    T element;
    NodeTree<T> left;
    NodeTree<T> right;
    int height;

    public NodeTree(T element){
        this(element, null, null);
    }

    /**
     * Constructs the node with its children
     * @param element
     * @param left
     * @param right
     */

    public NodeTree(T element, NodeTree<T> left, NodeTree<T> right){
        this.element = element;
        this.left = left;
        this.right = right;
        this.height = 1;
    }
}

