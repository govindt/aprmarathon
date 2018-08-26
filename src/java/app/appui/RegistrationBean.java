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
import javax.mail.*;
import core.ui.*;
import core.appui.*;
import core.util.*;
import app.util.*;
import app.busobj.*;
import app.businterface.*;
import app.busimpl.*;

public class RegistrationBean {
    public int userId = 0;
    public int loggedUserId = 0;
    UsersInterface utif = new UsersImpl();
    public String errString = Constants.EMPTY;
    public String statusString = Constants.EMPTY;
    public RegistrationBean() {}
    
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
			errString = Util.trim((valuepairs.get(AppConstants.ERROR_STR)));
			statusString = Util.trim((valuepairs.get(AppConstants.STATUS_STR)));
			DebugHandler.debug("Error String : " + errString);
			DebugHandler.debug("Status String : " + statusString);
		} else {
			RoleInterface rif = new RoleImpl();
			RoleObject rObj = new RoleObject();
			rObj.setRoleName(AppConstants.NORMAL_USER_STR);

			UsersObject uObj = new UsersObject();
			Vector<RoleObject> v = rif.getRoles(rObj);
			if ( v != null && v.size() > 0 ) {
				rObj = v.elementAt(0);
				uObj.setRoleId(rObj.getRoleId());
			} else {
				uObj.setRoleId(2);
			}
			String buf = Util.trim(valuepairs.get(AppConstants.NEW_USERNAME_STR));
			uObj.setUsername(buf);
			buf = Util.trim(valuepairs.get(AppConstants.EMAIL_STR));
			uObj.setEmail(buf);
			UsersObject uObj1 = utif.getUser(uObj.getUsername());
			if ( uObj1 != null ) {
				errString = "User " + uObj.getUsername() + " already exists ";
				statusString = Constants.EMPTY;
			} else {
				String password = PasswordCrypt.genPassword(8);
				uObj.setPassword(password);
				uObj.setIsValid(Constants.YES_STR);
				DebugHandler.fine("Adding User Object " + uObj);
				utif.addUsers(uObj);
				SendMail sm = new SendMail();
				String to[] = new String[1];
				to[0] = uObj.getEmail();
				try {
					String subject = AppConstants.EMAIL_SUBJECT;
					String body = AppConstants.EMAIL_BODY_HEADER;
					body += "Username : " + uObj.getUsername() + "\n";
					body += "Password : " + password + "\n";
					body += "Registered Email : " + uObj.getEmail() + "\n";
					body += AppConstants.EMAIL_BODY_FOOTER;
					SendMail.postMail(to, subject, body, "%MAIL%", null);
					DebugHandler.fine(body);
				} catch (Exception me) {
					DebugHandler.severe("Could not send mail to " + uObj.getEmail());
					errString = "Could not send mail to " + uObj.getEmail();
				}
				statusString = "Mail sent to " + uObj.getEmail();
				errString = Constants.EMPTY;
			}
		}
    }

    public String getRegistrationInfo() throws AppException {
	TableElement te = new TableElement();
	te.setClass(Constants.BODY_TABLE_STYLE);
	TableRowElement tr = null;
	TableDataElement td = null;
	BoldElement be = null;
	StringElement str = null;
	DebugHandler.debug("Error String 1: " + errString);
	DebugHandler.debug("Status String 1: " + statusString);
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
	    tr.add(td);
	    te.add(tr);
	}
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.USER_ID_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);
	td = new TableDataElement(new InputElement(InputElement.TEXT, 
						   AppConstants.NEW_USERNAME_STR,
						   Constants.EMPTY));
	tr.add(td);	
	te.add(tr);

	
	tr = new TableRowElement();
	be = new BoldElement(AppConstants.EMAIL_LABEL);
	be.setId(Constants.BODY_ROW_STYLE);
	td = new TableDataElement(be);
	tr.add(td);

	td = new TableDataElement(new InputElement(InputElement.TEXT, 
						   AppConstants.EMAIL_STR,
						   Constants.EMPTY));
	tr.add(td);
	te.add(tr);

	return te.getHTMLTag() + new BreakElement().getHTMLTag() + 
	    new BreakElement().getHTMLTag() + 
	    UtilBean.getSubmitButton();
    }
}
