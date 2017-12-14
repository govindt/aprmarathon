/*
 * @(#)NotesBean.java	1.3 04/04/13
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */


package app.appui;

import java.util.*;
import javax.servlet.http.*;
import core.ui.*;
import core.appui.*;
import core.util.*;
import app.busobj.*;
import app.businterface.*;
import app.busimpl.*;

public class NotesBean {
    public NotesBean() {}
    
    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null || 
	     Boolean.valueOf(saveProfile).booleanValue() == false ) { 
	    // This is to display the page
	}
	else {
	}
    }
    
    public String getNotes() throws AppException {
	String buf = Constants.EMPTY;
	BreakElement br = new BreakElement();
	
	buf += br.getHTMLTag();
	return buf;
    }
}
