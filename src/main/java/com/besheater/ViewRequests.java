package com.besheater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewRequests
 */
@WebServlet("/ViewRequests")
public class ViewRequests extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// Print all save requests
		ArrayList<String> requests = (ArrayList<String>) getServletContext().getAttribute("allRequests");
		
		PrintWriter writer = response.getWriter();
		writer.println("<table style=\"width:100%\" border=\"1\">");
		writer.println("<tr>");
		writer.println("<td>Content type</td>");
		writer.println("<td>Request body</td>");
		writer.println("<td>Cookies</td>");
		writer.println("</tr>");
		
		for (String req : requests) {
			writer.println(req);	
		}
		writer.println("</table>");
		
		// Print debug strings array
		ArrayList<String> debugStrings = (ArrayList<String>) getServletContext().getAttribute("strings");
		
		writer.println("<h2>Saved debug strings</h2>");
		writer.println("<table border=\"1\">");
		for (String string : debugStrings) {
			writer.println("<tr>");
			writer.println("<td>" + string +"</td>");
			writer.println("</tr>");
		}
		writer.println("</table>");
	}

}
