/*
 * @(#)RequestURL.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;

import java.lang.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * This is a bean that returns the values to the calling JSP
 * used primarily as a way to reconstruct the url used to call the page
 * Initially used for the back button history as a simplified method.rej.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class RequestURL { 

    private String retString = null;
    
    public RequestURL() {
	
    }
    
    public String getRequestUrl(HttpServletRequest request) {
	return getRequestUrl(request, null);
    }
    
    public String getRequestUrl(HttpServletRequest request,
				Hashtable<String,String> replaceValues) {
	
	HttpSession session = request.getSession(false);
	
	// first check that the session is valid
	
	if(session == null) return retString;
	
        // let's give the user a URL to href!
	
        // hack to deal with bug in JavaWebServer
        // if port is 80, no port number returns!
        int portNumber = request.getServerPort();
        if (portNumber == 0) {
            portNumber = 80; // default to port 80
        }

        StringBuffer myhref = new StringBuffer();
	myhref.append(request.getScheme());
	myhref.append("://");
	myhref.append(request.getServerName());
	myhref.append(":");
	myhref.append(portNumber);
	myhref.append("/servlet/IndiaMailServlet");
	
        boolean firstTime = true;
        // add parameters if any.
	@SuppressWarnings("unchecked")
	Enumeration<String> e = request.getParameterNames();
        while (e.hasMoreElements()) {
            String key = e.nextElement();
	    String value = null;
	    
	    if ((replaceValues == null) || (replaceValues.get(key) == null)) {
		String values[] = request.getParameterValues(key);
		value = values[0];
	    } else {
		value = replaceValues.get(key);
	    } 
	    
            if (firstTime) {
                myhref.append("?");
                firstTime = false;
            }
            else
                myhref.append("&");
	    
            myhref.append(key);
            myhref.append("=");
	    
        }
	
	retString = myhref.toString();
	DebugHandler.debug("RequestURL url request = " + retString);
	
	return retString;
    }
}
