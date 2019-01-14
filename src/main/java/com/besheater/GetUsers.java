package com.besheater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetUsers
 */
@WebServlet("/GetUsers")
public class GetUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataStorage dataStorage = (DataStorage) getServletContext().getAttribute(DataStorage.DATA_STORAGE);
		
		// Try to convert request to User object
		User user = UserHelper.getUser(request);
		// Try to retrieve uniqId from Cookie
		String uniqId = UserHelper.getUniqIdFromRequest(request);
		
		// Try to add this user to data storage
		if (!dataStorage.addUser(uniqId, user)) {
			// If user is not good to add then
			// make new unique Id
			uniqId = dataStorage.getNewUserUniqId();
		}
		
		// response variables
		Cookie respUniqIdCk = new Cookie("uniqId", uniqId);
		int respUserId = dataStorage.getUserId(uniqId);
		
		// Add unique ID cookie
		response.addCookie(respUniqIdCk);
		
		// Return all saved users and user Id
		response.setContentType("application/json");
		User[] users = dataStorage.getUsers();
		Gson gson = new Gson();
		ResponseWithUsers responseWithUsers = new ResponseWithUsers(respUserId, users);
		
		PrintWriter writer = response.getWriter();
		writer.println(gson.toJson(responseWithUsers));
		
	}

}
