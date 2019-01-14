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
 * Servlet implementation class AddMessage
 */
@WebServlet("/AddMessage")
public class AddMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataStorage dataStorage = (DataStorage) getServletContext().getAttribute(DataStorage.DATA_STORAGE);
		
		// Try to convert request to ChatMessage
		User user = null;
		ChatMessage message = UserHelper.getMessage(request);
		if (message != null) {
			user = message.user;
		}
		
		// Try to retrieve uniqId from Cookie
		String uniqId = UserHelper.getUniqIdFromRequest(request);
		
		// Check if User is valid
		if (dataStorage.isUserValid(uniqId, user)) {
			// if user is good then we can proceed
			if (user.connectedUsersId != null && user.connectedUsersId.length > 0) {
				int callingUserId = user.connectedUsersId[0];
				String callingUserUniqId = dataStorage.getUserUniqId(callingUserId);
				Chat chat = dataStorage.getChat(callingUserUniqId);
				if ( chat != null) {
					// Add new message to this chat
					if (message.text != null && !message.text.equals("")) {
						chat.addMessage(message);
						
						response.setContentType("application/json");
						PrintWriter writer = response.getWriter();
						writer.println("{\"result\": \"Message sucsesfully added\"}");
					}
				}
			}
			
		} else {
			// If user is not good to add then
			// return nothing
		}
	}

}
