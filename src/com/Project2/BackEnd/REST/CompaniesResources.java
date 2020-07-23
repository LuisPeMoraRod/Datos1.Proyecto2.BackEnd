package com.Project2.BackEnd.REST;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONObject;

@Path("/companies")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CompaniesResources implements RestResources {

	@Override
	public Response get(String companyEmail) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@POST
	@Override
	public Response register(@Context UriInfo uriInfo) {
		
		return null;
	}

	@Override
	public Response editImage(JSONObject jsonObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response edit(String companyEmail, UriInfo uriInfo) {
		// TODO Auto-generated method stub
		return null;
	}

}
