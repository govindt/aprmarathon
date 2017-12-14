/*
 *  HttpClientPost.java
 *
 *  Project Name Project
 *
 *  Author: Govind Thirumalai
 *
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.io.File;
import java.io.FileNotFoundException ; 
import java.io.Console;
import java.io.IOException ; 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import app.util.AppConstants;
import app.util.App;
import core.util.Constants;
import core.appui.UtilBean;
import core.util.Util;
import core.util.DebugHandler;
import app.businterface.UsersInterface;
import app.busimpl.UsersImpl;
import app.busobj.UsersObject;

public class HttpClientPost {

    public static void main(String[] args) throws Exception {
	App.getInstance();
	if ( args.length < 1 ) {
	    DebugHandler.severe("Usage HttpClientPost <Jsp name>");
	    System.exit(1);
	}
	String xls_files = System.getProperty("XLS_FILES");
	if ( xls_files == null ) {
	    DebugHandler.severe("XLS_FILES property not passed");
	    System.exit(1);
	}
	StringTokenizer st = new StringTokenizer(xls_files, ",");
	if ( st.countTokens() != args.length ) {
	    DebugHandler.severe("XLS_FILES property and args mismatch");
	    System.exit(1);
	}
	String[] xls_files_arr = new String[st.countTokens()];
	int xls_idx = 0;
	while (st.hasMoreTokens()) {
	    xls_files_arr[xls_idx++] = Util.trim(st.nextToken());
	}
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpPost httpost = new HttpPost(AppConstants.POST_URL + Constants.URL_SEPARATOR + Constants.JSP_STR + Constants.EQUALS_STR + AppConstants.LOGIN_JSP_STR);

	Console cons;
	char[] passwd;
	String passwordStr = null;
	String userNameStr;
	Scanner in = new Scanner(System.in);

	// Reads a single line from the console 
	// and stores into name variable
	System.out.print("[Username:] ");
	userNameStr = in.nextLine();

	if ((cons = System.console()) != null &&
	    (passwd = cons.readPassword("[%s] ", "Password:")) != null) {
	    passwordStr = new String(passwd);
	}


        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair(AppConstants.USER_NAME_STR, 
					userNameStr));
        nvps.add(new BasicNameValuePair(AppConstants.PASSWORD_STR, 
					passwordStr));
        nvps.add(new BasicNameValuePair(AppConstants.LOGIN, 
					"login"));
	UsersInterface uIf = new UsersImpl();
	UsersObject uObj = uIf.authenticate(userNameStr, passwordStr);
	boolean loggedIn = (uObj != null) && (uObj.getUsersId() != 0);
	
	if ( ! loggedIn ) {
	    DebugHandler.severe("Invalid username/password");
	    System.exit(1);
	}
	DebugHandler.fine("Username: " + userNameStr);
	DebugHandler.fine("Password: *******");
        httpost.setEntity(new UrlEncodedFormEntity(nvps, HTTP.UTF_8));

        HttpResponse response = httpclient.execute(httpost);
        HttpEntity entity = response.getEntity();

        DebugHandler.fine("Login form get: " + response.getStatusLine());
        DebugHandler.fine("Post logon cookies:");
	CookieStore cs = httpclient.getCookieStore();
        List <Cookie> cookies = cs.getCookies();
        if (cookies.isEmpty()) {
            DebugHandler.fine("None");
        } else {
            for (int i = 0; i < cookies.size(); i++) {
                DebugHandler.fine("- " + cookies.get(i).toString());
            }
        }

	for ( int idx = 0; idx < args.length; idx++ ) {
	    String jsp_name = args[idx];
	    File f = new File(xls_files_arr[idx]);
	    DebugHandler.fine(jsp_name);
	    DebugHandler.fine(xls_files_arr[idx]);
	    FileBody fb = new FileBody(f);
	    httpost = new HttpPost(AppConstants.POST_URL + Constants.URL_SEPARATOR + Constants.JSP_STR + Constants.EQUALS_STR + jsp_name);
	    DebugHandler.fine(AppConstants.POST_URL + Constants.URL_SEPARATOR + Constants.JSP_STR + Constants.EQUALS_STR + jsp_name);
	    MultipartEntity mEntity = new MultipartEntity();
	    mEntity.addPart(Constants.UPLOAD_FILE_NAME_STR, fb);
	    mEntity.addPart(UtilBean.SAVE_PROFILE_FLAG_STR, new StringBody("true"));
	    // This is a bug in servlet code, which requires a parameter 
	    // other than just file.
	    mEntity.addPart(Constants.JSP_STR, new StringBody(jsp_name)); 
	    
	    httpost.setEntity(mEntity);
	    
	    response = httpclient.execute(httpost);
	    DebugHandler.fine("Post logon cookies:");
	    cookies = httpclient.getCookieStore().getCookies();
	    if (cookies.isEmpty()) {
		DebugHandler.fine("None");
	    } else {
		for (int i = 0; i < cookies.size(); i++) {
		    DebugHandler.fine("- " + cookies.get(i).toString());
		}
	    }
	    
	    DebugHandler.fine("Response " + response.getStatusLine());
	    
	    entity = response.getEntity();
	    if (entity != null) {
		entity.consumeContent();
	    }
	}
    }
}
