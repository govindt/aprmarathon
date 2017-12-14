/*
 * @(#)UtilBean.java	1.25 04/08/24
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */



package app.appui;

import java.lang.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import core.ui.*;
import core.util.*;
import app.util.*;
import app.businterface.*;
import app.busimpl.*;
import app.busobj.*;

public class AppUtilBean {
    public AppUtilBean() {
    }

    public static String getLoggedUsername(HttpServletRequest request) {
	HttpSession session = request.getSession();
	if (session == null)
	    return Constants.EMPTY;
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	
	String userNameStr = valuepairs.get(AppConstants.USER_NAME_STR);
	if ( userNameStr != null ) 
	    return "Logged User : " + userNameStr + new BreakElement().getHTMLTag() + new BreakElement().getHTMLTag();
	else
	    return Constants.EMPTY;
    }

    public static String getLoginUrl() {
	StringElement se = new StringElement(AppConstants.LOGIN_STR);
	AnchorElement ae = new AnchorElement(Util.getBaseurl() + Constants.URL_SEPARATOR +
					     Constants.JSP_STR + Constants.EQUALS_STR + 
					     AppConstants.LOGIN_JSP_STR,
					     "", se.asBoldElement().getHTMLTag(), Constants.TEXT_LINK_STYLE);
	return ae.getHTMLTag();
    }
    
    public static String getLogoutUrl() {
	StringElement se = new StringElement(AppConstants.LOGOUT_STR);
	AnchorElement ae = new AnchorElement(Util.getBaseurl() + Constants.URL_SEPARATOR +
					     Constants.JSP_STR + Constants.EQUALS_STR + 
					     AppConstants.LOGOUT_JSP_STR,
					     "", se.asBoldElement().getHTMLTag(), Constants.TEXT_LINK_STYLE);
	return ae.getHTMLTag();
    }


    public static void logout(HttpServletRequest request) {
	HttpSession session = request.getSession();
	session.removeAttribute(Constants.VALUE_PAIR_STR);
	session.invalidate();
    }
    
    private static String getJspUrl(String name, String jsp) {
	StringElement se = new StringElement(name);
	AnchorElement ae = new AnchorElement(Util.getBaseurl() + Constants.URL_SEPARATOR +
					     Constants.JSP_STR + Constants.EQUALS_STR + 
					     jsp,
					     "", se.asBoldElement().getHTMLTag(), Constants.TEXT_LINK_STYLE);
	return ae.getHTMLTag();
    }
    
    public static String getManageUsersUrl() {
	return getJspUrl(AppConstants.MANAGE_USERS_STR, AppConstants.MANAGE_USERS_JSP_STR);
    }

    public static String getManageRoleUrl() {
	return getJspUrl(AppConstants.MANAGE_ROLE_STR, AppConstants.MANAGE_ROLE_JSP_STR);
    }

    public static String getManageAclUrl() {
	return getJspUrl(AppConstants.MANAGE_ACL_STR, AppConstants.MANAGE_ACL_JSP_STR);
    }

    public static String getRegistrationUrl() {
	return getJspUrl(AppConstants.REGISTRATION_LABEL, AppConstants.REGISTRATION_JSP_STR);
    }

    public static String getResetPasswordUrl() {
	return getJspUrl(AppConstants.RESET_PASSWORD_LABEL, AppConstants.RESET_PASSWORD_JSP_STR);
    }

    public int getLoggedUserId(HttpServletRequest req) throws AppException {
	HttpSession session = req.getSession();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	String loggedUserIdStr = Util.trim(valuepairs.get(AppConstants.USER_ID_STR));
	try {
	    return (Integer.parseInt(loggedUserIdStr));
	} catch (NumberFormatException nfe) {
	    return 0;
	}
    }

    public static String getNavigationBar(HttpServletRequest req) throws AppException {
	HttpSession session = req.getSession();
	if (session == null)
	    return "";
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	
	String alreadyLoggedIn = Util.trim(valuepairs.get(AppConstants.ALREADY_LOGGED_IN_STR));
	String loggedUserIdStr = Util.trim(valuepairs.get(AppConstants.USER_ID_STR));
	String roleName = AppConstants.GUEST_STR;
	if ( ! loggedUserIdStr.equals(Constants.EMPTY) ) {
	    UsersInterface uif = new UsersImpl();
	    UsersObject loggedUser = uif.getUser(Integer.parseInt(loggedUserIdStr));
	    RoleInterface rif = new RoleImpl();
	    DebugHandler.fine("Logged User " + loggedUser);
	    RoleObject rObj = rif.getRole(loggedUser.getRoleId());
	    roleName = rObj.getRoleName();
	}

	TableElement te = new TableElement();
	te.setClass(Constants.HEADER_TABLE_STYLE);
	te.setBorder(2);
	te.setCellPadding(2);
	te.setWidthStr(Constants.FULL_WIDTH_STR);

	TableRowElement tr = new TableRowElement();
	tr.setAlign(Constants.LEFT_STR);
	TableDataElement td = null;
	BoldElement bold = null;
	StringElement se = null;
	

	if ( alreadyLoggedIn.equals("true") ) {
	    bold = new BoldElement(getManageUsersUrl());
	    bold.setId(Constants.HEADER_ROW_STYLE);
	    se = new StringElement(bold.getHTMLTag());
	    td = new TableDataElement(se);
	    td.setAlign(Constants.LEFT_STR);
	    tr.addElement(td);

	    if ( roleName.equals(AppConstants.ADMINISTRATOR_STR) ) {
		bold = new BoldElement(getManageRoleUrl());
		bold.setId(Constants.HEADER_ROW_STYLE);
		se = new StringElement(bold.getHTMLTag());
		td = new TableDataElement(se);
		td.setAlign(Constants.LEFT_STR);
		tr.addElement(td);

		bold = new BoldElement(getManageAclUrl());
		bold.setId(Constants.HEADER_ROW_STYLE);
		se = new StringElement(bold.getHTMLTag());
		td = new TableDataElement(se);
		td.setAlign(Constants.LEFT_STR);
		tr.addElement(td);

	    } 
	    bold = new BoldElement(getLogoutUrl());
	    bold.setId(Constants.HEADER_ROW_STYLE);
	    se = new StringElement(bold.getHTMLTag());
	    td = new TableDataElement(se);
	    td.setAlign(Constants.LEFT_STR);
	    tr.addElement(td);
	} else {
	    bold = new BoldElement(getLoginUrl());
	    bold.setId(Constants.HEADER_ROW_STYLE);
	    se = new StringElement(bold.getHTMLTag());
	    td = new TableDataElement(se);
	    td.setAlign(Constants.LEFT_STR);
	    tr.addElement(td);
	}
	te.addElement(tr);
	return te.getHTMLTag();
    }
}

