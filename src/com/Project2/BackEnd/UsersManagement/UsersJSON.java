package com.Project2.BackEnd.UsersManagement;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import org.json.simple.parser.ParseException;

import com.Project2.BackEnd.Trees.BinaryTree;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;



public class UsersJSON {
	private User user;
	private BinaryTree<User> BT;
	private JSONArray usersArray;
	private static UsersJSON usersJson = null;

	private UsersJSON(JSONArray usersArray) {
		BT = BinaryTree.getInstance();
		this.usersArray = usersArray;
		addUsersToBT(usersArray);
	}

	public BinaryTree<User> getBT() {
		return BT;
	}

	public void setBT(BinaryTree<User> bT) {
		BT = bT;
	}

	/**
	 * Singleton pattern design instantiation method
	 * 
	 * @return usersJson : UsersJSON
	 */
	public static synchronized UsersJSON getInstance(JSONArray usersArray) {
		if (usersJson == null) {
			usersJson = new UsersJSON(usersArray);
		}
		return usersJson;
	}

	/**
	 * Public method. Loads to the binary tree all the users objects from the
	 * JSONArray
	 */
	@SuppressWarnings("unchecked")
	public void addUsersToBT(JSONArray usersList) {
		String email = null, name = null, password = null;
		int age = 0;
		System.out.println(usersList.getClass());
		// usersList.forEach(user -> parseUser((JSONObject) user));
		for (int i = 0; i < usersList.size(); i++) {
			HashMap<String, String> passedValues = (HashMap<String, String>) usersList.get(i);
			for (Entry<String, String> mapTemp : passedValues.entrySet()) {
				switch (mapTemp.getKey()) {
				case "email":
					email = mapTemp.getValue();
					System.out.println(email);
					break;
				case "name":
					name = mapTemp.getValue();
					System.out.println(name);
					break;
				case "password":
					password = mapTemp.getValue();
					System.out.println(password);
					break;
				case "age":
					age = Integer.parseInt(mapTemp.getValue());
					System.out.println(age);
					break;
				default:
					break;
				}
			}
			this.user = User.builder().withEmail(email).withName(name).withPassword(password).withAge(age).build();
			this.BT.insert(user);
		}
	}

}
