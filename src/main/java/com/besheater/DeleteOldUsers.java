package com.besheater;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DeleteOldUsers
 */
@WebServlet("/DeleteOldUsers")
public class DeleteOldUsers extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DataStorage dataStorage = (DataStorage) getServletContext().getAttribute(DataStorage.DATA_STORAGE);
		
		int numOfDelUsers = dataStorage.deleteOldUsers();
		PrintWriter writer = response.getWriter();
		writer.println("" + numOfDelUsers +" users deleted.");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
