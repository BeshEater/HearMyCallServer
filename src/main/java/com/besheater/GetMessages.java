package com.besheater;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

/**
 * Servlet implementation class GetMessages
 */
@WebServlet("/GetMessages")
public class GetMessages extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataStorage dataStorage = (DataStorage) getServletContext().getAttribute(DataStorage.DATA_STORAGE);
		
		// Try to convert request to User object
		User user = UserHelper.getUser(request);
		// Try to retrieve uniqId from Cookie
		String uniqId = UserHelper.getUniqIdFromRequest(request);
		
		// Check if User is valid
		if (dataStorage.isUserValid(uniqId, user)) {
			// if user is good then we can proceed
			if (user.connectedUsersId != null && user.connectedUsersId.length > 0) {
				int callingUserId = user.connectedUsersId[0];
				Chat chat = null;
				String callingUserUniqId = dataStorage.getUserUniqId(callingUserId);
				if (callingUserUniqId != null && !callingUserUniqId.equals("")) {
					chat = dataStorage.getChat(callingUserUniqId);
				}
				if ( chat != null && chat.size() > 0) {
					// Return all messages from connected user chat
					response.setContentType("application/json");
					ChatMessage[] messages = chat.getMessages();
					Gson gson = new Gson();
					ResponseWithMessages responseWithMessages = new ResponseWithMessages(messages);
					
					PrintWriter writer = response.getWriter();
					writer.println(gson.toJson(responseWithMessages));
				} else {
					// Return that chat is clear
					response.setContentType("application/json");
					PrintWriter writer = response.getWriter();
					writer.println("{\"chat\": \"clean\"}");
				}
			}
			
		} else {
			response.setContentType("application/json");
			PrintWriter writer = response.getWriter();
			writer.println("{\"chat\": \"clean\"}");
		}
		
	}

}
