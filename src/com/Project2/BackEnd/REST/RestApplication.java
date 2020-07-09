package com.Project2.BackEnd.REST;

import java.security.MessageDigest;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("rest/")
public class RestApplication extends Application{
	
	@Override
	public Set<Class<?>> getClasses(){
		Set<Class<?>> classList = new HashSet<>();
		classList.add(UsersResources.class);
		return classList;
	}
	
}
