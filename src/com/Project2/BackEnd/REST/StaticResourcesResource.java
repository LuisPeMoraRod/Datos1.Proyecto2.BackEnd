package com.Project2.BackEnd.REST;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;

import static java.lang.String.format;
import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("")
public class StaticResourcesResource {

	ServletContext context;

	/**
	 * Serving static files form folders:
	 *
	 * /WEB-INF/resources /WEB-INF/static /WEB-INF/public /WEB-INF/assets
	 */

	@GET
	 @Path("/{path: ^resources\\/.*}")
	  public Response staticResources(@PathParam("path") final String path) {
	    // log.debug("handling assets: {}", path);
	    //InputStream resource = context.getResourceAsStream(format("/WEB-INF/%s", path));
		//String resource = getJsonFile(path);
		String resource = path;
	    return null == resource
	        ? Response.status(NOT_FOUND).build()
	        : Response.ok().entity(resource).build();
	}

	public String getJsonFile(String path) {
		JSONParser parser = new JSONParser();
		JSONArray usersArray = null;
		try {
			usersArray = (JSONArray) parser.parse(new FileReader(format("/WEB-INF/resources/%s", path)));

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
}