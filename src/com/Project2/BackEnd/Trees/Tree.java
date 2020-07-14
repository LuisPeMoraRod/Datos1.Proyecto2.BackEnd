package com.Project2.BackEnd.Trees;

import java.util.ArrayList;

/**
 * Abstract class used to create the shared methods of the Binary Search Tree, AVL Tree and Splay Tree
 * this way, duplicated code will be avoided.
 *
 * @param <T>
 */

public abstract class Tree<T extends Comparable<T>> {

    protected NodeTree<T> root;

    private boolean isBST;
    private boolean isAVL;
    private boolean isSplay;
    
    private ArrayList<T> elementsList = new ArrayList<>();

    public boolean isEmpty(){
        return this.root == null;
    }

    /**
     * This method is used to set the type of tree in the child classes constructor
     * @param type the type of AVLTree
     */
    protected void setTypeTree(String type){
        if(!isSplay & !isAVL & !isBST){
            if(type.equals("splay")){
                this.isSplay = true;
            }
            else if(type.equals("avl")){
                this.isAVL = true;
            }
            else{
                this.isBST = true;
            }
        }
    }

    /**
     * Method that can be accessed by a user to search an element in the tree
     * @param element the element to be searched
     * @return a recursive call to the private contain method
     */

    public boolean contains(T element){
        if(isSplay){
            this.root = this.splay(this.root, element);
        }
        return this.contains(element, this.root);
    }

    /**
     * This method works as an auxiliary for the public contains method
     * and in this way the root can remain not accessed by the user.
     *
     * @param element is the elements to be found
     * @param node the node that the element is being compare to
     * @return a recursive call to the private contain method
     */

    private boolean contains(T element, NodeTree<T> node) {
        if(node == null){
            return false;
        }
        else {
            int compareResult = element.compareTo(node.element);

            if(compareResult < 0){
                return contains(element, node.left);
            }
            else if(compareResult > 0){
                return contains(element, node.right);
            }
            else{
                return true;
            }
        }
    }

    /**
     * Finds the minimum value of the tree
     * @return a recursive call to the private findMin method
     */

    public NodeTree<T> findMin(){
        if(this.isEmpty()){
            return null;
        }
        else{
            return this.findMin(this.root);
        }
    }

    /**
     * This method works as an auxiliary to look for the minimum value of the tree
     *
     * @param node is the node that is currently analyzed
     * @return the node that contains the minimum value
     */

    private NodeTree<T> findMin(NodeTree<T> node) {
        if(node == null){
            return null;
        }
        else if(node.left == null){
            return node;
        }
        else{
            return findMin(node.left);
        }
    }

    /**
     * Finds the maximum value of the tree
     * @return a recursive call to the findMax method
     */

    public NodeTree<T> findMax(){
        if(this.isEmpty()){
            return null;
        }
        else{
            return this.findMax(this.root);
        }
    }

    /**
     * This method works as an auxiliary to look for the minimum value of the tree
     * @param node is the node that is currently analyzed
     * @return the node that contains the maximum value
     */

    private NodeTree<T> findMax(NodeTree<T> node) {
        if(node == null){
            return null;
        }
        else if(node.right == null){
            return node;
        }
        else{
            return findMax(node.right);
        }
    }

    /**
     * This method insert a new element as a leaf
     * @param element the value of the element that is wanted to be added in the tree
     */

    public void insert(T element){
        this.root = this.insert(element, this.root);
        if(isSplay){
            this.root = this.splay(this.root,element);
        }
    }

    /**
     * This is the auxiliary method that has access to the tree root and
     * inserts an element as a leaf
     *
     * @param element the element that is going to be inserted
     * @param node the current node that is being compared
     * @return the node where the element is inserted
     */

    private NodeTree<T> insert(T element, NodeTree<T> node) {

        if(node == null){
            return new NodeTree<>(element);
        }

        int compareResult = element.compareTo(node.element);

        if(compareResult < 0){
            node.left = this.insert(element, node.left);
        }
        else if( compareResult > 0){
            node.right = this.insert(element, node.right);
        }

        // This part will establish the condition to insert an element in a AVL Tree

        if(this.isAVL){

            node.height = 1 + max(getHeight(node.left), getHeight(node.right));

            int balance = getBalance(node);

            if (balance > 1 && element.compareTo(node.left.element) < 0)
                return rightRotate(node);

            if (balance < -1 && element.compareTo(node.right.element) > 0)
                return leftRotate(node);

            // Left Right Case
            if (balance > 1 && element.compareTo(node.left.element) > 0) {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }

            // Right Left Case
            if (balance < -1 && element.compareTo(node.right.element) < 0) {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        return node;
    }

    /**
     * Removes an element from the tree
     * @param element the element that is going to be erased
     */

    public void remove(T element){
        this.root = this.remove(element, this.root);
    }

    /**
     * The auxiliary to the remove method that has access to the tree root and
     *
     * @param element the element that is going to be removed
     * @param node the node that is being compared
     * @return the node in which the element was eliminated
     */

    private NodeTree<T> remove(T element, NodeTree<T> node) {

        if (node == null){
            return null;
        }

        int compareResult = element.compareTo(node.element);

        if(compareResult < 0){
            node.left = remove(element, node.left);
        }

        else if(compareResult > 0){
            node.right = remove(element,node.right);
        }

        else if(node.left != null && node.right != null){
            node.element = findMin(node.right).element;
            node.right = remove(node.element, node.right);
        }

        else{
            node = node.left!=null ? node.left : node.right;
        }

        // After doing the deletion, checks if the tree is AVL or Splay to balance it

        if(isAVL){

            this.root.height = max(getHeight(this.root.left), getHeight(this.root.right)) + 1;

            int balance = getBalance(this.root);


            if (balance > 1 && getBalance(this.root.left) >= 0)
                return rightRotate(this.root);

            if (balance > 1 && getBalance(this.root.left) < 0) {
                this.root.left = leftRotate(this.root.left);
                return rightRotate(this.root);
            }

            if (balance < -1 && getBalance(this.root.right) <= 0)
                return leftRotate(this.root);

            if (balance < -1 && getBalance(this.root.right) > 0) {
                this.root.right = rightRotate(this.root.right);
                return leftRotate(this.root);
            }

        }

        return node;
    }

    public void getRootValue(){
        System.out.println(this.root.element);
    }

    // The next methods are related to the balance of a AVL Tree and a Splay Tree

    /**
     * Gets the height of a specific node
     * @param node the node which height wants to be known
     * @return the height of the node
     */
    private int getHeight(NodeTree<T> node) {
        if (node == null)
            return 0;

        return node.height;
    }

    /**
     * This method returns the maximum of two integers
     *
      * @param a integer 1
     * @param b integer 2
     * @return the integer with a greater value
     */

    private int max(int a, int b) {
        return (a > b) ? a : b;
    }

    /**
     *  A utility function to right rotate subtree rooted with y
     * @param node the node to be rotated
     * @return  new root
     */
    private NodeTree<T> rightRotate(NodeTree<T> node) {
        NodeTree<T> x = node.left;
        NodeTree<T> T2 = x.right;

        x.right = node;
        node.left = T2;

        node.height = max(getHeight(node.left), getHeight(node.right)) + 1;
        x.height = max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    /**
     * A utility function to left rotate subtree rooted with x
     * @param node the node to be rotated
     * @return the new root
     */
    private NodeTree<T> leftRotate(NodeTree<T> node) {
        NodeTree<T> y = node.right;
        NodeTree<T> T2 = y.left;

        y.left = node;
        node.right = T2;

        node.height = max(getHeight(node.left), getHeight(node.right)) + 1;
        y.height = max(getHeight(y.left), getHeight(y.right)) + 1;

        return y;
    }

    /**
     * Method to get the balance factor of a node and check if it is unbalanced
     * @param node the node from which the balance factor is gotten
     * @return the balance factor
     */
    private int getBalance(NodeTree<T> node) {
        if (node == null)
            return 0;

        return getHeight(node.left) - getHeight(node.right);
    }


    /**
     * This method splays the tree each each time an element is searched or a new element is added
     * It is exclusive for Splay Trees
     *
     * @param node the node where it is looking for the element requested
     * @param element the reference element
     * @return the new root
     */
   private NodeTree<T> splay(NodeTree<T> node, T element)
    {

        if (node == null || node.element.compareTo(element) == 0)
            return node;

        if (node.element.compareTo(element) > 0)
        {
            // Key is not in tree, we are done
            if (node.left == null) return node;

            // Zig-Zig (Left Left)
            if (node.left.element.compareTo(element) > 0)
            {
                // First recursively bring the
                // key as root of left-left
                node.left.left = splay(node.left.left, element);

                // Do first rotation for root,
                // second rotation is done after else
                node = rightRotate(node);
            }
            else if (node.left.element.compareTo(element) < 0) // Zig-Zag (Left Right)
            {
                // First recursively bring
                // the key as root of left-right
                node.left.right = splay(node.left.right, element);

                // Do first rotation for root.left
                if (node.left.right != null)
                    node.left = leftRotate(node.left);
            }

            // Do second rotation for root
            return (node.left == null) ?
                    node : rightRotate(node);
        }
        else // Key lies in right subtree
        {
            // Key is not in tree, we are done
            if (node.right == null) return node;

            // Zag-Zig (Right Left)
            if (node.right.element.compareTo(element) > 0)
            {
                // Bring the key as root of right-left
                node.right.left = splay(node.right.left, element);

                // Do first rotation for root.right
                if (node.right.left != null)
                    node.right = rightRotate(node.right);
            }
            else if (node.right.element.compareTo(element) < 0)// Zag-Zag (Right Right)
            {
                // Bring the key as root of
                // right-right and do first rotation
                node.right.right = splay(node.right.right, element);
                node = leftRotate(node);
            }

            // Do second rotation for root
            return (node.right == null) ? node : leftRotate(node);
        }
    }

    
    
    ////////////////////////////////////////////////////////
    public ArrayList<T> getList() {
		return inOrder();
	}

    /**
     * Removes every element of the ListArray
     */
	public void setList() {
		
		while(!elementsList.isEmpty()) {
			for (int i = 0 ; i<elementsList.size(); i++) {
				elementsList.remove(i);
			}
		}
	}
	
	/**
	 * Public method that returns an array with all the elements of the binary tree in order (in terms of the users' email)
	 * @return list : ArraList<T>
	 */
	public ArrayList<T> inOrder() {
		setList();
		inOrder(elementsList, this.root);
		return elementsList;
	}

	/**
	 * Recursive method used by inOrder method
	 * @param list : ArrayList <T>
	 * @param node : Node <T>
	 * {@link #inOrder()}
	 */
	public void inOrder(ArrayList<T> list, NodeTree<T> node) {
		if (node == null) {
			return;
		}
		inOrder(list, node.left);
		list.add(node.getElement());
		inOrder(list, node.right);
		
	}


}