package com.Project2.BackEnd.Admin;

import java.io.Closeable;

/**
 * @author Luis Pedro Morales Rodriguez
 * @version 5/7/2020
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AdminRESTClient {
	/**
	 * Creates a client for rest api services. Sends JSON
	 * 
	 * @autohor Luis Pedro Morales Rodriguez
	 * @version 6/7/2020
	 */

	private Client client;
	private String loadUrl = "http://localhost:8080/CookTime.BackEnd/api/users/load";
	private String getUrl = "http://localhost:8080/CookTime.BackEnd/api/users";
	private String jsonFileLoc = "src/com/Project2/BackEnd/Admin/users.json";
	private String notifUrl= "http://localhost:8080/CookTime.BackEnd/api/users/get_notif?observerUser=lmorales";
	private JSONParser parser;
	
	public AdminRESTClient() {
		client = Client.create();
		parser = new JSONParser();
	}

	public void getRequest() throws ParseException {
		WebResource webResource = client.resource(getUrl);
		ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}

		String result = response.getEntity(String.class);
		
		JSONArray json = (JSONArray) parser.parse(result);
		try (FileWriter file = new FileWriter(jsonFileLoc, false)) {

			file.write(json.toJSONString());
			file.flush();
			System.out.println("Response from the Server: ");
			System.out.println(result);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void postRequest() {
		WebResource webResource = client.resource(loadUrl);
		String inputData = getJsonFile();
		ClientResponse response = webResource.type("application/json").post(ClientResponse.class, inputData);
		if (response.getStatus() != 201) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}

		String result = response.getEntity(String.class);
		System.out.println("Response from the Server: ");
		System.out.println(result);
	}

	public String getJsonFile() {
		JSONParser parser = new JSONParser();
		JSONArray usersArray = null;
		try {
			usersArray = (JSONArray) parser.parse(new FileReader(jsonFileLoc));

			System.out.println("Reading JSON file...\n" + usersArray.toJSONString());
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return usersArray.toJSONString();
	}
	
	public void getNotif() {
		WebResource webResource = client.resource(notifUrl);
		//String inputData = getJsonFile();
		ClientResponse response = webResource.type("application/json").get(ClientResponse.class);
		if (response.getStatus() != 200) {
			throw new RuntimeException("HTTP Error: " + response.getStatus());
		}

		String result = response.getEntity(String.class);
		System.out.println("Response from the Server: ");
		System.out.println(result);
	}

	public static void main(String[] args) throws ParseException {
		AdminRESTClient restClient = new AdminRESTClient();
		// fire the post request on the server
		//restClient.postRequest();
		//restClient.getRequest();
		for (int i = 0; i < 5; i++) {
			restClient.getNotif();
		}
	}

}
