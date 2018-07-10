/*
 * @(#)UsersBean.java	1.3 04/04/13
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
import app.util.*;
import app.busobj.*;
import app.businterface.*;
import app.busimpl.*;

public class UsersBean {
    public int userId = 0;
    public int loggedUserId = 0;
    UsersObject selectedUserObj = new UsersObject();
    UsersInterface utif = new UsersImpl();
		

    public UsersBean() {}
    
    public void getRequestParameters(HttpServletRequest request) throws AppException {
	HttpSession session = request.getSession();
	if (session == null)
	    throw new NullPointerException();
	@SuppressWarnings("unchecked")
	Hashtable<String,String> valuepairs =
	    (Hashtable)session.getAttribute(Constants.VALUE_PAIR_STR);
	// Get core info id 
	try {
	    loggedUserId = Integer.parseInt(valuepairs.get(AppConstants.USER_ID_STR));
	    userId = Integer.parseInt(valuepairs.get(AppConstants.MODIFY_USER_STR));
	} catch (NumberFormatException nfe) {
	    userId = 0;
	}
	String saveProfile = valuepairs.get(UtilBean.SAVE_PROFILE_FLAG_STR);
	if ( saveProfile == null || 
	     Boolean.valueOf(saveProfile).booleanValue() == false ) { 
	    // This is to display the page
	    if ( userId != 0 ) // Display selected user
		selectedUserObj = utif.getUser(userId);
	    else
		selectedUserObj = utif.getUser(loggedUserId);
	}
	else {

	    // User can modify his password
	    if ( userId != 0 ) {
		UsersObject utObj = utif.getUser(userId);
		String buf = Util.trim(valuepairs.get(AppConstants.NEW_PASSWORD_STR));
		utObj.setPassword(buf);
		buf = Util.trim(valuepairs.get(AppConstants.MODIFY_ROLE_STR));
		DebugHandler.debug("Buf " + buf);
		if ( ! buf.equals(Constants.EMPTY) )
		    utObj.setRoleId(Integer.parseInt(buf));
		buf = Util.trim(valuepairs.get(AppConstants.EMAIL_STR));
		utObj.setEmail(buf);
		DebugHandler.debug("Modifying User Object " + utObj);
		utif.updateUsers(utObj);
	    } 
	    // Or add another user
	    else {
		String buf = Util.trim(valuepairs.get(AppConstants.NEW_PASSWORD_STR));
		selectedUserObj.setPassword(buf);
		buf = Util.trim(valuepairs.get(AppConstants.NEW_USERNAME_STR));
		selectedUserObj.setUsername(buf);
		buf = Util.trim(valuepairs.get(AppConstants.MODIFY_ROLE_STR));
		selectedUserObj.setRoleId(Integer.parseInt(buf));
		buf = Util.trim(valuepairs.get(AppConstants.EMAIL_STR));
		selectedUserObj.setEmail(buf);
		DebugHandler.debug("Adding User Object " + selectedUserObj);
		utif.addUsers(selectedUserObj);
	    }
	}
    }
    
    public String getUsersInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	String userIdStr = Constants.EMPTY;
	UsersInterface utif = new UsersImpl();
	RoleInterface rif = new RoleImpl();

	
	UsersObject loggedUserObj = utif.getUser(loggedUserId);
	if ( loggedUserObj == null )
	    return Constants.EMPTY;
	RoleObject rObj = rif.getRole(loggedUserObj.getRoleId());
	if ( rObj.getRoleName().equals(AppConstants.ADMINISTRATOR_STR) ) {
	    tr = new TableRowElement();
	    be = new BoldElement(AppConstants.CURRENT_USERS_LABEL);
	    be.setId(Constants.BODY_ROW_STYLE);
	    td = new TableDataElement(be);
	    tr.add(td);
	    UsersObject[] userTblArr = utif.getAllUsers();
	    ArrayList<String> nameArrayList = new ArrayList<String>();
	    ArrayList<Integer> valueArrayList = new ArrayList<Integer>();
	    nameArrayList.add(AppConstants.NEW_USER);
	    valueArrayList.add(new Integer(0));
	    for (int i = 0; i < userTblArr.length; i++ ) {
		UsersObject utObj = userTblArr[i];
		if ( utObj == null )
		    break;
		nameArrayList.add(utObj.getUsername());
		valueArrayList.add(new Integer(utObj.getUsersId()));
	    }
	    SelectElement se = new SelectElement(AppConstants.MODIFY_USER_STR,
						 nameArrayList,
						 valueArrayList,
						 String.valueOf(userId), 0);
	    
	    se.setOnChange(UtilBean.JS_SUBMIT_FORM);
	    td = new TableDataElement(se);
	    tr.add(td);
	    te.add(tr);
	    
	    tr = new TableRowElement();
	    be = new BoldElement(AppConstants.USER_ID_LABEL);
	    be.setId(Constants.BODY_ROW_STYLE);
	    td = new TableDataElement(be);
	    tr.add(td);
	    if ( userId != 0 )
		td = new TableDataElement(new InputElement(InputElement.TEXT, 
							   AppConstants.NEW_USERNAME_STR,
							   selectedUserObj.getUsername()));
	    else
		td = new TableDataElement(new InputElement(InputElement.TEXT, 
							   AppConstants.NEW_USERNAME_STR,
							   Constants.EMPTY));
	    tr.add(td);
	    te.add(tr);

	    tr = new TableRowElement();
	    be = new BoldElement(AppConstants.CURRENT_ROLE_LABEL);
	    be.setId(Constants.BODY_ROW_STYLE);
	    td = new TableDataElement(be);
	    tr.add(td);

	    nameArrayList = new ArrayList<String>();
	    valueArrayList = new ArrayList<Integer>();
	    RoleObject[] roleArr = rif.getAllRoles();
	    for (int iterator = 0; iterator < roleArr.length; iterator++ ) {
		rObj = roleArr[iterator];
		if ( rObj == null )
		    break;
		nameArrayList.add(rObj.getRoleName());
		valueArrayList.add(rObj.getRoleId());
	    }
	    se = new SelectElement(AppConstants.MODIFY_ROLE_STR,
				   nameArrayList,
				   valueArrayList,
				   String.valueOf(selectedUserObj.getRoleId()), 0);
	    td = new TableDataElement(se);
	}
	else {
	    tr = new TableRowElement();
	    be = new BoldElement(AppConstants.USER_ID_LABEL);
	    be.setId(Constants.BODY_ROW_STYLE);
	    td = new TableDataElement(be);
	    tr.add(td);
	    StringElement se = new StringElement(selectedUserObj.getUsername());
	    td = new TableDataElement(se);
	    userIdStr = UtilBean.getHiddenField(AppConstants.MODIFY_USER_STR, selectedUserObj.getUsersId() + Constants.EMPTY);
	    userIdStr += UtilBean.getHiddenField(AppConstants.NEW_USERNAME_STR, selectedUserObj.getUsername());;
	}
	tr.add(td);	
	te.add(tr);
	
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EMAIL_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	DebugHandler.debug(selectedUserObj);
	DebugHandler.debug(userId);
	if ( userId != 0 || loggedUserId != 0 ) {
	    td = new TableDataElement(new InputElement(InputElement.TEXT, 
						       AppConstants.EMAIL_STR,
						       selectedUserObj.getEmail()));
	}
	else {
	    td = new TableDataElement(new InputElement(InputElement.TEXT, 
						       AppConstants.EMAIL_STR,
						       Constants.EMPTY));
	}
	tr.add(td);
	te.add(tr);
	
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.NEW_PASSWORD_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);

	td = new TableDataElement(new InputElement(InputElement.PASSWORD, 
						   AppConstants.NEW_PASSWORD_STR,
						   Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	tr = new TableRowElement();
	be = new BoldElement(AppConstants.RETYPE_PASSWORD_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	td = new TableDataElement(new InputElement(InputElement.PASSWORD, 
						   AppConstants.RE_PASSWORD_STR,
						   Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	
	return te.getHTMLTag() + userIdStr +
	    new BreakElement().getHTMLTag() + 
	    new BreakElement().getHTMLTag() + 
	    UtilBean.getSubmitButton();
    }
}
