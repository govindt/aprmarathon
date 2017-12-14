/*
 * @(#)LoginBean.java	1.3 04/03/09
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */


package app.appui;

import java.util.*;
import javax.servlet.http.*;
import core.ui.*;
import core.util.*;
import app.util.*;
import app.busobj.*;
import app.businterface.*;
import app.busimpl.*;
import core.appui.UtilBean;

public class LoginBean {
    public String nextJsp = AppConstants.MANAGE_USERS_JSP_STR;
    private String userNameStr = Constants.EMPTY;
    public String errString = Constants.EMPTY;
    public String statusString = Constants.EMPTY;
    public LoginBean() {}
    
    public String getNextJsp() {
	return nextJsp;
    }
    
    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	
	nextJsp = valuepairs.get(Constants.NEXT_JSP_STR);
	userNameStr = valuepairs.get(AppConstants.USER_NAME_STR);
	String buf = valuepairs.get(AppConstants.LOGIN);
	DebugHandler.fine("login value : " + buf);
	errString = Util.trim((valuepairs.get(AppConstants.ERROR_STR)));
	statusString = Util.trim((valuepairs.get(AppConstants.STATUS_STR)));
	DebugHandler.debug("Error String : " + errString);
	DebugHandler.debug("Status String : " + statusString);
	if ( buf != null ) {
	    if ( nextJsp == null || nextJsp.equals(Constants.EMPTY) )
		nextJsp = AppConstants.LOGIN_JSP_STR;
	} 

	String logout = valuepairs.get(AppConstants.LOGOUT_STR);
	if ( logout != null && logout.equals(Constants.YES_STR)) {
	    session.removeAttribute(Constants.VALUE_PAIR_STR);
	    session.invalidate();
	}
	
    }
    
    public String getPriorFields(HttpServletRequest request) {
	String buf = Constants.EMPTY;
	HttpSession session = request.getSession();
	if (session == null)
	    return buf;
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	if ( valuepairs == null )
	    return buf;
	Set<String> set = valuepairs.keySet();
	Iterator<String> iterator = set.iterator();
	while (iterator.hasNext() ) {
	    String s = iterator.next();
	    if ( s != null && ! s.equals(AppConstants.USER_NAME_STR) && 
		 !s.equals(AppConstants.LOGIN) &&
		 !s.equals(AppConstants.ADMIN_STR) &&
		 !s.equals(AppConstants.PASSWORD_STR)) {
		buf += UtilBean.getHiddenField(s, valuepairs.get(s));
	    }
	}
	buf = Constants.EMPTY;
	return buf;
    }

    public String getUserInfo() throws AppException {
	TableElement te = new TableElement();
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	StringElement se = null;
	
	if ( ! errString.equals(Constants.EMPTY) ||
	     ! statusString.equals(Constants.EMPTY)) {
	    tr = new TableRowElement();
	    if ( ! errString.equals(Constants.EMPTY) ) {
		be = new BoldElement(errString);
		be.setId(Constants.ERROR_STRING_STYLE);
	    }
	    else {
		be = new BoldElement(statusString);
		be.setId(Constants.STATUS_STRING_STYLE);
	    }
	    td = new TableDataElement(be);
	    td.setColspan(2);
	    tr.addElement(td);
	    te.addElement(tr);
	}
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.USER_ID_LABEL);
	be.setId("body_label");
	td = new TableDataElement(be);
	tr.addElement(td);

	td = new TableDataElement(new InputElement(InputElement.TEXT, 
						   AppConstants.USER_NAME_STR,
						   userNameStr));
	tr.addElement(td);
	te.addElement(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.PASSWORD_LABEL);
	be.setId("body_label");
	td = new TableDataElement(be);
	tr.addElement(td);

	td = new TableDataElement(new InputElement(InputElement.PASSWORD, 
						   AppConstants.PASSWORD_STR,
						   Constants.EMPTY));
	tr.addElement(td);
	te.addElement(tr);
	

	
	return te.getHTMLTag() + 
	    new BreakElement().getHTMLTag() + 
	    new BreakElement().getHTMLTag();
    }
}
