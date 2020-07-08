package com.Project2.BackEnd.Admin;

/**
 * @author Luis Pedro Morales Rodriguez
 * @version 5/7/2020
 */

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class AdminRESTClient {
	/**
	 * Creates a client for rest api services.
	 * Sends JSON 
	 * @autohor Luis Pedro Morales Rodriguez
	 * @version 6/7/2020
	 */
	
	
	public static void main(String[] args) {
		JSONParser parser = new JSONParser();
		JSONArray usersArray = null;
		try {
			usersArray = (JSONArray) parser
					.parse(new FileReader("src/com/Project2/BackEnd/Admin/users.json"));
			
			System.out.println(usersArray.toJSONString());
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
		
		try {
			URL url = new URL("http://localhost:8080/Project2.BackEnd/api/rest/users/load");
			URLConnection connection = url.openConnection();
			connection.setDoOutput(true);
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);
			OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
			out.write(usersArray.toString());
			out.close();

			//BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			//in.close();
		} catch (Exception e) {
			System.out.println("\nError while calling REST Service");
			System.out.println(e);
		}

		
		}
}
