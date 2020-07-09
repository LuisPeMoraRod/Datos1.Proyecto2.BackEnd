package com.Project2.BackEnd.REST;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Base64;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Project2.BackEnd.Trees.BinaryTree;
import com.Project2.BackEnd.UsersManagement.MD5;
import com.Project2.BackEnd.UsersManagement.User;


@WebFilter(filterName = "authFilter", urlPatterns = "/*")
public class AuthFilter implements Filter {
	/**
	 * Filter for user authentication
	 * @author Luis Pedro Morales Rodriguez
	 * @version 9/7/2020
	 */
	
	private  BinaryTree<User> BT;
	private User validationUser;
	private MD5 MD5;

	@Override
	/**
	 * Validates if the headers "From" and "Authorization" match with the email and password of a user
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String authorizationHeader = httpRequest.getHeader("Authorization");
		String fromHeader = httpRequest.getHeader("From");
		System.out.println(fromHeader);
		if (authorizationHeader == null & fromHeader == null) {
			chain.doFilter(request, response);
			System.out.println("new user");
		} else if (validateAuthorization(authorizationHeader,fromHeader)) {			
			chain.doFilter(request, response);
			System.out.println("Authorized client");
		} else {
			HttpServletResponse httpResponse = (HttpServletResponse) response;
			httpResponse.setStatus(401);
		}
	}
	
	private boolean validateAuthorization(String password, String email) {
		BT=BinaryTree.getInstance();
		validationUser = BT.getUserByEmail(email);
		System.out.println(email);
		MD5 = new MD5(password);
		try {
			password = MD5.getMD5();
			System.out.println(password);
			System.out.println(validationUser.getPassword());
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			e.printStackTrace();
		}
		return password.equals(validationUser.getPassword());
	}

}
