package com.besheater;

import java.util.ArrayList;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

/**
 * Application Lifecycle Listener implementation class DataInitListener
 *
 */
@WebListener
public class DataInitListener implements ServletContextListener {



    public void contextDestroyed(ServletContextEvent sce)  { 
         // nothing
    }


    public void contextInitialized(ServletContextEvent sce)  { 
         
    	ServletContext sc = sce.getServletContext();
    	
    	// Create dataStorage for all app
    	DataStorage dataStorage = new DataStorage();
    	sc.setAttribute(DataStorage.DATA_STORAGE, dataStorage);
    	
    	// Create request storage for debugging
    	ArrayList<String> list = new ArrayList<>();
    	sc.setAttribute("allRequests", list);
    	
    	// Create string storage for debugging
    	ArrayList<String> list2 = new ArrayList<>();
    	sc.setAttribute("strings", list2);
    	
    }
	
}
