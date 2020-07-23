package com.Project2.BackEnd.REST;

import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONObject;

public interface RestResources {
	
	public Response get(String email);
	public Response getAll();
	public Response register(@Context UriInfo uriInfo);
	public Response editImage(JSONObject jsonObject);
	public Response edit(String email, @Context UriInfo uriInfo);
}
