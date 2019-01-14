package com.besheater;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewAllusers
 */
@WebServlet("/ViewAllusers")
public class ViewAllusers extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter writer = response.getWriter();
		DataStorage dataStorage = (DataStorage) getServletContext().getAttribute(DataStorage.DATA_STORAGE);
		User[] users = dataStorage.getUsers();
		writer.println("<table style=\"width:100%\" border=\"1\">");
		writer.println("<tr>");
		writer.println("<td>uniqID</td>");
		writer.println("<td>id</td>");
		writer.println("<td>name</td>");
		writer.println("<td>latitude</td>");
		writer.println("<td>longitude</td>");
		writer.println("<td>avatarImageNum</td>");
		writer.println("<td>callMessage</td>");
		writer.println("<td>ConnectedUsersId</td>");
		writer.println("<td>time</td>");
		writer.println("</tr>");
		
		for (User user: users) {
			if (user != null) {
				writer.println("<tr>");
				writer.println("<td>"+ dataStorage.getUserUniqId(user) + "</td>");
				writer.println("<td>"+ user.id + "</td>");
				writer.println("<td>"+ user.name + "</td>");
				writer.println("<td>"+ user.latitude + "</td>");
				writer.println("<td>"+ user.longitude + "</td>");
				writer.println("<td>"+ user.avatarImageNum + "</td>");
				writer.println("<td>"+ user.callMessage + "</td>");
				writer.println("<td>"+ printIntArr(user.connectedUsersId) + "</td>");
				writer.println("<td>"+ user.time + "</td>");
				writer.println("</tr>");
			}
		}
		writer.println("</table>");
	}
	
	private String printIntArr(int[] arr) {
		if (arr != null && arr.length > 0) {
			StringBuilder content = new StringBuilder();
			content.append("[");
			for (int num : arr) {
				content.append(num);
				content.append(", ");
			}
			content.append("]");
			return content.toString();	
		}
		return null;
		
	}

}
