/*
 * @(#)UtilBean.java	1.25 04/08/24
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.appui;

import java.lang.*;
import java.util.*;
import java.text.*;
import javax.servlet.*;
import javax.servlet.http.*;
import core.ui.*;
import core.util.*;
import java.util.regex.*;

public class UtilBean {
  /* The following constants are the names used in the JSP for the form fields */
    public static final String SAVE_VALUE_STR = "Submit";
    public static final String SAVE_STR = "save";
    public static final String PREVIEW_VALUE_STR = "Preview";
    public static final String PREVIEW_STR = "preview";
    public static final String UPDATE_VALUE_STR = "Update";
    public static final String UPDATE_STR = "update";
    public static final String DELETE_VALUE_STR = "Delete";
    public static final String CANCEL_STR = "cancel";
    public static final String CANCEL_VALUE_STR = "Cancel";
    public static final String CONFIRM_DELETE_VALUE_STR = "Confirm Delete";
    public static final String DELETE_STR = "delete";
    public static final String SAVE_PROFILE_FLAG_STR = "saveProfileFlag";
    public static final String DOWNLOAD_FLAG_STR = "downloadFlag";
    public static final String PREVIEW_FLAG_STR = "previewFlag";
    public static final String CONFIRM_DELETE_STR = "confirmDelete";
    public static final String DOWNLOAD_VALUE_STR="Download";
    public static final String DOWNLOAD_STR="download";

    // Javascript functions
    public static final String JS_SUBMIT_FORM = "form.submit();";
    public static final String JS_START = "javascript:start('";
    public static final String JS_CLOSE = "')";
    public static final String JS_CHANGE = "change(form, '";
    public static final String JS_CHANGE_PARAM_SEP = "', '";
    public static final String JS_MOVE_DUAL = "moveDualList( ";
    public static final String JS_FUNCTION_CLOSE = ")";
    public static final String JS_FORM = "this.form.";    
    public static final String JS_COMMA = ", ";    
    public static final String YES_VALUE_STR="Yes";
    public static final String NO_VALUE_STR="No";
    
    public static final String BACK_STR = "back";
    public static final String BACK_VALUE_STR = "Back";
    
    public static final int NEW = 0;
    public static final int VIEW = 1;
    public static final int UPDATE = 2;
    public static final int DELETE = 3;
    public static final int VIEW_FOR_UPDATE = 4;
    
    public UtilBean() {
    }
    
    public static String getSubmitButton() {
	InputElement ie;
	ie = new InputElement(InputElement.BUTTON, SAVE_STR, SAVE_VALUE_STR);
	ie.setOnClick("if ( validateForm(this.form)) { form." + SAVE_PROFILE_FLAG_STR + ".value=true; form.submit();}");
	return ie.getHTMLTag();
    }

    public static String getDownloadButton() {
	InputElement ie;
	ie = new InputElement(InputElement.BUTTON, DOWNLOAD_STR, DOWNLOAD_VALUE_STR);
	ie.setOnClick("if ( downloadValidateForm(this.form)) { form." + DOWNLOAD_FLAG_STR + ".value=true; form.submit(); form." + DOWNLOAD_FLAG_STR + ".value=false;}");
	return ie.getHTMLTag();
    }
    
    public static String getPreviewButton(String nextJsp, boolean setSaveProfile) {
	InputElement ie;
	ie = new InputElement(InputElement.BUTTON, PREVIEW_STR, PREVIEW_VALUE_STR);
	String onclickScript = "if ( validateForm(this.form)) { ";
	if ( setSaveProfile ) {
	    onclickScript += "form." + SAVE_PROFILE_FLAG_STR + ".value=true;";
	}
	onclickScript += "form." + PREVIEW_FLAG_STR + ".value=true; form." + 
	    Constants.JSP_STR + ".value='" + nextJsp + 
	    "'; form.submit(); }";
	ie.setOnClick(onclickScript);
	return ie.getHTMLTag();
    }
    
    public static String getUpdateButton(String nextJsp) {
	InputElement ie;
	ie = new InputElement(InputElement.BUTTON, UPDATE_STR, UPDATE_VALUE_STR);
	if ( nextJsp == null || nextJsp.equals("") )
	    ie.setOnClick("form.submit();");
	else
	    ie.setOnClick("form." + Constants.JSP_STR + ".value='" + nextJsp + "';form.submit();");
	return ie.getHTMLTag();
    }
    
    public static String getSaveProfileFlag() throws AppException {
	InputElement ie;
	ie = new InputElement(InputElement.HIDDEN, SAVE_PROFILE_FLAG_STR, "false");
	return ie.getHTMLTag();
    }

    public static String getDownloadFlag() throws AppException {
	InputElement ie;
	ie = new InputElement(InputElement.HIDDEN, DOWNLOAD_FLAG_STR, "false");
	return ie.getHTMLTag();
    }
    
    public static String getPreviewFlag() throws AppException {
	InputElement ie;
	ie = new InputElement(InputElement.HIDDEN, PREVIEW_FLAG_STR, "false");
	return ie.getHTMLTag();
    }
    
    public static String getImageurl(String url) throws AppException {
	return Util.getBaseurl () + Constants.URL_SEPARATOR +
	    Constants.JSP_STR +
	    Constants.EQUALS_STR +
	    url;
    }
    
    public static String getDeleteButton(String nextJsp) {
	InputElement ie;
	ie = new InputElement(InputElement.BUTTON, DELETE_STR, DELETE_VALUE_STR);
	if ( nextJsp == null || nextJsp.equals("") )
	    ie.setOnClick("form.submit();");
	else
	    ie.setOnClick("form." + Constants.JSP_STR + ".value='" + nextJsp + "';form.submit();");
	return ie.getHTMLTag();
    }
    
    public static String getConfirmDeleteButton() {
	InputElement ie;
	ie = new InputElement(InputElement.BUTTON, 
			      UtilBean.DELETE_STR, 
			      UtilBean.CONFIRM_DELETE_VALUE_STR);
	ie.setOnClick("form." + CONFIRM_DELETE_STR + ".value=true; submit();");
	return ie.getHTMLTag();
    }
    
    public static String getCancelButton(String jsp) {
	InputElement ie;
	ie = new InputElement(InputElement.SUBMIT, 
			      UtilBean.CANCEL_STR, 
			      UtilBean.CANCEL_VALUE_STR);
	ie.setOnClick("form." + Constants.JSP_STR + ".value='" + jsp + "';");
	return ie.getHTMLTag();
    }
    
    public static String getNextJsp(String jsp) {
	InputElement ie;
	ie = new InputElement(InputElement.HIDDEN, 
			      Constants.JSP_STR,
			      jsp);
	return ie.getHTMLTag();
    }
    
    public static String getConfirmDelete() {
	InputElement ie;
	ie = new InputElement(InputElement.HIDDEN, 
			      UtilBean.CONFIRM_DELETE_STR,
			      "false");
	return ie.getHTMLTag();
    }
    
    public static String getUrlString(String name, String value, boolean isLast) {
	if (Util.trim(value).equals("") )
	    return "";
	String buf = name + Constants.EQUALS_STR + value;
	if ( isLast ) 
	    return buf;
	else
	    return buf + Constants.AMP_STR;
    }
    
    public static String getHiddenField(String name, String value) {
	InputElement ie;
	ie = new InputElement(InputElement.HIDDEN,
			      name,
			      value);
	
	return ie.getHTMLTag();
    }
    
    public static String getInputField(String name, String value) {
	InputElement ie;
	ie = new InputElement(InputElement.TEXT,
			      name,
			      value);
	
	return ie.getHTMLTag();
    }
    
    public static String roundOf(double d) {
	NumberFormat nf = NumberFormat.getInstance();
	nf.setMaximumFractionDigits(2);
	return nf.format(d);
    }
    
    public static String getBackButton() {
	InputElement ie;
	ie = new InputElement(InputElement.BUTTON,
			      UtilBean.BACK_STR,
			      UtilBean.BACK_VALUE_STR);
	ie.setOnClick("javascript:history.back()");
	return ie.getHTMLTag();
    }
    
    
    public static String getYesNoStr(String name) {
	if ( name == null )
	    return "";
	if ( name.equals(Constants.YES_STR) ) 
	    return UtilBean.YES_VALUE_STR;
	if ( name.equals(Constants.NO_STR) )
	    return UtilBean.NO_VALUE_STR;
	else
	    return "";
    }
    
    public static String getYesNoRadio(String name, String initial_value, String onclick) throws AppException {
	String buf = "";
	InputElement ie = new InputElement(InputElement.RADIO, 
					   name, Constants.YES_STR, 1);
	if ( initial_value.equalsIgnoreCase(Constants.YES_STR))
	    ie.setIsChecked(true);
	ie.setOnClick(onclick);
	buf += ie.getHTMLTag() + YES_VALUE_STR;
	ie = new InputElement(InputElement.RADIO, 
			      name, Constants.NO_STR, 1);
	if ( initial_value.equalsIgnoreCase(Constants.NO_STR))
	    ie.setIsChecked(true);
	ie.setOnClick(onclick);
	buf += ie.getHTMLTag() + NO_VALUE_STR;
	return buf;
    }
    
    
    public static String getYesNoRadio(String name, String initial_value) throws AppException {
	String buf = "";
	InputElement ie = new InputElement(InputElement.RADIO, 
					   name, Constants.YES_STR, 1);
	if ( initial_value.equalsIgnoreCase(Constants.YES_STR))
	    ie.setIsChecked(true);
	buf += ie.getHTMLTag() + YES_VALUE_STR;
	ie = new InputElement(InputElement.RADIO, 
			      name, Constants.NO_STR, 1);
	if ( initial_value.equalsIgnoreCase(Constants.NO_STR))
	    ie.setIsChecked(true);
	buf += ie.getHTMLTag() + NO_VALUE_STR;
	return buf;
    }
    
    public static String getPassFailRadio(String name, String initial_value) throws AppException {
	String buf = "";
	InputElement ie = new InputElement(InputElement.RADIO, 
					   name, Constants.PASS_STR, 1);
	if ( initial_value.equalsIgnoreCase(Constants.PASS_STR))
	    ie.setIsChecked(true);
	buf += ie.getHTMLTag() + Constants.PASS_LABEL;
	ie = new InputElement(InputElement.RADIO, 
			      name, Constants.FAIL_STR, 1);
	if ( initial_value.equalsIgnoreCase(Constants.FAIL_STR))
	    ie.setIsChecked(true);
	buf += ie.getHTMLTag() + Constants.FAIL_LABEL;
	return buf;
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

    public static String getFromToList(Vector<String> nameVector, Vector<Integer> valueVector) {
	TableElement te = new TableElement();
	te.setBorder(0);
	TableRowElement tr = new TableRowElement();
	SelectElement se = new SelectElement(Constants.FROM_LIST_STR,
					     nameVector,
					     valueVector,
					     Constants.EMPTY,
					     Constants.FROM_LIST_SIZE);
	se.setMultiple(true);
	TableDataElement td = new TableDataElement(se);
	tr.addElement(td);

	String buttonList = "";
	BreakElement br = new BreakElement();

	InputElement ie = new InputElement(InputElement.BUTTON, Constants.ADD_TO_RIGHT_STR, Constants.ADD_TO_TO_LIST_LABEL);
	ie.setOnClick(JS_MOVE_DUAL + JS_FORM +
		      Constants.FROM_LIST_STR + JS_COMMA + JS_FORM + 
		      Constants.TO_LIST_STR + JS_COMMA + Constants.FALSE + JS_FUNCTION_CLOSE);
	buttonList += ie.getHTMLTag() + br.getHTMLTag();

	ie = new InputElement(InputElement.BUTTON, Constants.ADD_ALL_TO_RIGHT_STR, Constants.ADD_ALL_TO_TO_LIST_LABEL);
	ie.setOnClick(JS_MOVE_DUAL + JS_FORM +
		      Constants.FROM_LIST_STR + JS_COMMA + JS_FORM + 
		      Constants.TO_LIST_STR + JS_COMMA + Constants.TRUE + JS_FUNCTION_CLOSE);
	buttonList += ie.getHTMLTag() + br.getHTMLTag();

	ie = new InputElement(InputElement.BUTTON, Constants.ADD_TO_LEFT_STR, Constants.ADD_TO_FROM_LIST_LABEL);
	ie.setOnClick(JS_MOVE_DUAL + JS_FORM +
		      Constants.TO_LIST_STR + JS_COMMA + JS_FORM + 
		      Constants.FROM_LIST_STR + JS_COMMA + Constants.FALSE + JS_FUNCTION_CLOSE);
	buttonList += ie.getHTMLTag() + br.getHTMLTag();
	
	ie = new InputElement(InputElement.BUTTON, Constants.ADD_ALL_TO_LEFT_STR, Constants.ADD_ALL_TO_FROM_LIST_LABEL);
	ie.setOnClick(JS_MOVE_DUAL + JS_FORM +
		      Constants.TO_LIST_STR + JS_COMMA + JS_FORM +
		      Constants.FROM_LIST_STR + JS_COMMA + Constants.TRUE + JS_FUNCTION_CLOSE);
	buttonList += ie.getHTMLTag() + br.getHTMLTag();
	td = new TableDataElement(new StringElement(buttonList));
	tr.addElement(td);

	se = new SelectElement(Constants.TO_LIST_STR,
			       new Vector<String>(),
			       new Vector<Integer>(),
			       Constants.EMPTY,
			       Constants.TO_LIST_SIZE);
	se.setMultiple(true);
	td = new TableDataElement(se);
	tr.addElement(td);
	te.addElement(tr);

	return te.getHTMLTag();
    }

    public static String keepFormat(String str) {
	return HTMLElementObject.PRE + str + HTMLElementObject.PRE_END;
    }
}

