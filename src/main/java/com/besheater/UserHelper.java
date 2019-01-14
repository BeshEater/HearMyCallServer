package com.besheater;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

public class UserHelper {
	
	public static String getUniqIdFromRequest(HttpServletRequest request) {
		
		Cookie[] cookies = request.getCookies();
		if (cookies == null || cookies.length == 0) {
			return null;
		}
		String uniqId = null;
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("uniqId")) {
				uniqId = cookie.getValue();
			}
		}
		return uniqId;
	}
	
	public static String getRequestBody(HttpServletRequest request) throws IOException {
		// Read from request
	    StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	    }
	    return buffer.toString();
	}
	
	public static User getUser(HttpServletRequest request) throws IOException {
		// Parse body
		String bodyJson = getRequestBody(request);	
		if (bodyJson != null) {
			Gson gson = new Gson();
			User user = gson.fromJson(bodyJson, User.class);
			return user;
		}
		return null;
	}

	public static ChatMessage getMessage(HttpServletRequest request) throws IOException {
		// Parse body
		String bodyJson = getRequestBody(request);	
		if (bodyJson != null) {
			Gson gson = new Gson();
			ChatMessage message = gson.fromJson(bodyJson, ChatMessage.class);
			return message;
		}
		return null;
	}

}
