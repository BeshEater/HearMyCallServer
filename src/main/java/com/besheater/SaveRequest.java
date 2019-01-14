package com.besheater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SaveRequest
 */
@WebServlet("/SaveRequest")
public class SaveRequest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ArrayList<String> requests = (ArrayList<String>) getServletContext().getAttribute("allRequests");
		
		StringBuilder buffer = new StringBuilder();
		buffer.append("<tr>");
		buffer.append("<td>"+ request.getContentType() + "</td>");
		buffer.append("<td>"+ getRequestBody(request) + "</td>");
		buffer.append("<td>" + getRequestCookies(request) + "</td>");
		buffer.append("<tr>");
		
		requests.add(buffer.toString());
		
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		writer.println("{\"type\":\"RequestSaved\"}");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	private String getRequestBody(HttpServletRequest request) throws IOException {
		// Read from request
	    StringBuilder buffer = new StringBuilder();
	    BufferedReader reader = request.getReader();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        buffer.append(line);
	    }
	    String data = buffer.toString();
	    return data;
	}
	
	private String getRequestCookies(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		StringBuilder buffer = new StringBuilder();
		buffer.append("");
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				buffer.append(cookie.getName() + " | ");
				buffer.append(cookie.getValue() + " | ");
				buffer.append(cookie.getMaxAge() + " | ");
			}	
		}
		
		return buffer.toString();
	}

}
