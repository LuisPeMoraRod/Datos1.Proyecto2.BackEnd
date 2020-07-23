package com.Project2.BackEnd.Trees;

import com.Project2.BackEnd.CompaniesManagement.Company;

public class SplayTree<T extends Comparable<T>> extends Tree<T> {
	
	private static SplayTree<Company> splay = null;
	
    private SplayTree(){
        this.setTypeTree("splay");
    }
    
    public static synchronized SplayTree<Company> getInstance() {
    	if (splay == null) {
    		splay = new SplayTree<Company>();
    	}
    	return splay;
    }
}