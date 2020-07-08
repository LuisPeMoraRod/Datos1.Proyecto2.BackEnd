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

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class AdminRESTClient {
	/**
	 * Creates a client for rest api services.
	 * Sends JSON 
	 * @autohor Luis Pedro Morales Rodriguez
	 * @version 6/7/2020
	 */
	
	Client client = Client.create();
	String loadUrl = "http://localhost:8080/CookTime.BackEnd/api/users/load";
	String getUrl = "http://localhost:8080/CookTime.BackEnd/api/users";
	
	public void getRequest() {
		WebResource webResource = client.resource(getUrl);
        ClientResponse response = webResource.accept("application/json").get(ClientResponse.class);
        if(response.getStatus()!=200){
            throw new RuntimeException("HTTP Error: "+ response.getStatus());
        }
         
        String result = response.getEntity(String.class);
        System.out.println("Response from the Server: ");
        System.out.println(result);
	}
	
	public void postRequest(){
        WebResource webResource = client.resource(loadUrl);
        String inputData =getJsonFile();
        ClientResponse response = webResource.type("application/json").post(ClientResponse.class,inputData);
        if(response.getStatus()!=201){
            throw new RuntimeException("HTTP Error: "+ response.getStatus());
        }
         
        String result = response.getEntity(String.class);
        System.out.println("Response from the Server: ");
        System.out.println(result);
    }
	
	public String getJsonFile() {
		JSONParser parser = new JSONParser();
		JSONArray usersArray = null;
		try {
			usersArray = (JSONArray) parser
					.parse(new FileReader("src/com/Project2/BackEnd/Admin/users.json"));
			
			System.out.println("Reading JSON file...\n"+usersArray.toJSONString());
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
	
	
	public static void main(String[] args) {
        AdminRESTClient restClient = new AdminRESTClient();
                //fire the post request on the server
        restClient.postRequest();
    }
	
	
	
	public void oldMain() {
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
