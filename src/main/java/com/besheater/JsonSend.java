package com.besheater;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Servlet implementation class JsonSend
 */
@WebServlet("/JsonSend")
public class JsonSend extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public JsonSend() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Gson gson = new Gson();
		HttpSession session = request.getSession();
		session.getId();
		
		// Check if request contains JSON
		String contentType = request.getContentType();
		if (contentType != null && contentType.equals("application/json")) {
			// Parse user data from JSON
			
			// Read from request
		    StringBuilder buffer = new StringBuilder();
		    BufferedReader reader = request.getReader();
		    String line;
		    while ((line = reader.readLine()) != null) {
		        buffer.append(line);
		    }
		    String data = buffer.toString();
		    
		    JsonElement jelement = new JsonParser().parse(data);
		    JsonObject  jobject = jelement.getAsJsonObject();
		    jobject = jobject.getAsJsonObject("data");
		    JsonArray jarray = jobject.getAsJsonArray("translations");
		    jobject = jarray.get(0).getAsJsonObject();
		    String result = jobject.get("translatedText").getAsString();
		}
		
		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();
		
		// Create JSON
		DataStorage dataStorage = (DataStorage) getServletContext().getAttribute(DataStorage.DATA_STORAGE);
		User[] users = dataStorage.getUsers();
		String resp = gson.toJson(users);
		writer.println(resp);
		
		// Save debug string 
		ArrayList<String> debugStrings = (ArrayList<String>) getServletContext().getAttribute("strings");
		debugStrings.add(resp);
	}
	
	
}
