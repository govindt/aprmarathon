/*
 * @(#)App.java	1.30 04/08/20
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */



package app.util;

import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.regex.*;
import core.util.*;
import org.apache.jcs.JCS;
import org.apache.jcs.access.exception.CacheException;
import org.apache.commons.lang.exception.*;

/*
 * Singletone class for the whole Application
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */


public class App {

    private static App 	self;
    private static String baseurl_;
    
    private static Properties siteProps = null;
    private static Vector<String> loginRequiredUrl = new Vector<String>();
    
    private App() throws AppException {
	// Change these later to get from Resource File as Properties
	siteProps = new Properties();	
    }
    
    
    public static App getInstance() throws AppException {
	if ( self == null ) {
	    self = new App();
	    loadSiteProperties();
	    Util util = Util.getInstance();
	}
	
	if ( self == null )
	    throw new AppException();
	
	return self;
    }
    
    private static Properties loadSiteProperties() throws AppException {
	String sitePropertiesFile
	    = "app.util.app";
	ResourceBundle rb = ResourceBundle.getBundle(sitePropertiesFile);
	Set<String> set = rb.keySet();
	Iterator<String> iterator = set.iterator();
	while ( iterator.hasNext()) {
	    String key = iterator.next();
	    siteProps.put(key, rb.getString(key));
	} 
	baseurl_ = siteProps.getProperty("site.baseurl");
	AppConstants.LOGIN_STR = siteProps.getProperty("app.login_str");
	AppConstants.LOGIN_REQUIRED_URLS = siteProps.getProperty("app.login_required_urls");
	StringTokenizer st = new StringTokenizer(AppConstants.LOGIN_REQUIRED_URLS);
	while (st.hasMoreTokens()) {
	    String url = st.nextToken();
	    if ( url != null )
		loginRequiredUrl.addElement(url);
	}
	AppConstants.LOGOUT_STR = siteProps.getProperty("app.logout_str");
	AppConstants.REGISTRATION_LABEL = siteProps.getProperty("app.registration_label");
	AppConstants.RESET_PASSWORD_LABEL = siteProps.getProperty("app.reset_password_label");
	AppConstants.USER_ID_LABEL = siteProps.getProperty("app.user_id_label");
	AppConstants.PASSWORD_LABEL = siteProps.getProperty("app.password_label");
	AppConstants.CURRENT_USERS_LABEL = siteProps.getProperty("app.current_users_label");
	AppConstants.CURRENT_ROLE_LABEL = siteProps.getProperty("app.current_role_label");
	AppConstants.NEW_PASSWORD_LABEL = siteProps.getProperty("app.new_password_label");
	AppConstants.RETYPE_PASSWORD_LABEL = siteProps.getProperty("app.retype_password_label");
	AppConstants.NEW_USER = siteProps.getProperty("app.new_user"); 
	AppConstants.EMAIL_LABEL = siteProps.getProperty("app.email_label"); 
	AppConstants.EMAIL_SUBJECT = siteProps.getProperty("app.email_subject"); 
	AppConstants.EMAIL_BODY_HEADER = siteProps.getProperty("app.email_body_header");
	AppConstants.EMAIL_BODY_FOOTER = siteProps.getProperty("app.email_body_footer");  
	AppConstants.SECURITY_MANAGEMENT_LABEL = siteProps.getProperty("app.security_management_label");  
	AppConstants.HOME_LABEL = siteProps.getProperty("app.home_label");  
	AppConstants.JSP_BASE = siteProps.getProperty("app.jsp_base");  
    AppConstants.CURRENT_MENU_LABEL = siteProps.getProperty("app.current_menu_label");
    AppConstants.NEW_MENU = siteProps.getProperty("app.new_menu");
    AppConstants.MENU_ID_LABEL = siteProps.getProperty("app.menu_id_label");
    AppConstants.MENU_NAME_LABEL = siteProps.getProperty("app.menu_name_label");
    AppConstants.URL_LABEL = siteProps.getProperty("app.url_label");
    AppConstants.MENU_ORDER_LABEL = siteProps.getProperty("app.menu_order_label");
    AppConstants.PARENT_MENU_ID_LABEL = siteProps.getProperty("app.parent_menu_id_label");
    AppConstants.NEW_PARENT_MENU = siteProps.getProperty("app.new_parent_menu");

    AppConstants.PERMISSION_LABEL = siteProps.getProperty("app.permisson_label");
    AppConstants.CURRENT_ACL_LABEL = siteProps.getProperty("app.current_acl_label");
    AppConstants.NEW_ACL = siteProps.getProperty("app.new_acl");
    AppConstants.ACL_ID_LABEL = siteProps.getProperty("app.acl_id_label");
    AppConstants.ACL_PAGE_LABEL = siteProps.getProperty("app.acl_page_label");
    AppConstants.ACCESS_LABEL = siteProps.getProperty("app.access_label");
    AppConstants.NEW_ROLE = siteProps.getProperty("app.new_role");
    AppConstants.ROLE_ID_LABEL = siteProps.getProperty("app.role_id_label");
    AppConstants.ROLE_NAME_LABEL = siteProps.getProperty("app.role_name_label");
    AppConstants.IS_VALID_LABEL = siteProps.getProperty("app.is_valid_label");

    AppConstants.POST_URL = siteProps.getProperty("app.post_url");
    AppConstants.DB_TYPE = siteProps.getProperty("app.dbtype");

	// INSERT GENERATED CODE
	
	return siteProps;
    }
    
    public static boolean isLoginRequired(String url) {
	DebugHandler.debug("isLoginRequired:URL: " + url);
	if (url == null)
	    return false;
	for (int i = 0; i < App.loginRequiredUrl.size(); i++) {
	    String vurl = App.loginRequiredUrl.elementAt(i);
	    DebugHandler.debug("VURL: " + vurl);
	    if (vurl.equals(url))
		return true;
	}
	return false;
    }
    
    public static String getSiteProperty (String varKey)
    {
	return (siteProps.getProperty (varKey));
    }
}
