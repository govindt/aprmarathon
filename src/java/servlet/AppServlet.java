/*
 * @(#)AppServlet.java	1.3 04/03/27
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

import java.lang.*;
import java.util.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.servlet.*;
import javax.servlet.http.*;
import core.util.*;
import core.appui.UtilBean;
import app.businterface.UsersInterface;
import app.businterface.SpreadSheetInterface;
import app.busimpl.UsersImpl;
import app.busobj.UsersObject;
import app.util.AppConstants;
import app.util.App;
import java.io.*;
import javax.activation.*;
import com.oreilly.servlet.multipart.Part;
import com.oreilly.servlet.multipart.MultipartParser;
import com.oreilly.servlet.multipart.ParamPart;
import com.oreilly.servlet.multipart.FilePart;

/**
 * This the servlet that is the main entry point.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */
public class AppServlet extends HttpServlet {
    private static final long serialVersionUID = 7526472295622776147L;
    String jsp;
    String jspBase;
    App    theApp;
    
    /**
     * called when the weblet is first called
     * initializes the weblet
     *
     * @param		conf		    configuration info
     * @exception	ServletException    if servlet cannot be initialized
     */
    
    public void init( ServletConfig config ) throws ServletException {
	super.init( config );
	String value =  null;
	String s = "";
	
	try {
	    theApp = App.getInstance();
	    DebugHandler.fine("init");
	} catch (AppException ae) {
	    throw new ServletException(ae.getMessage());
	}
	
	jsp = getInitParameter(Constants.JSP_STR); 
	jspBase = getInitParameter(Constants.JSP_BASE);
	if ( jsp == null || jspBase == null ) 
	    throw new ServletException("No initialization parameter " +
				       "could not be found");
	DebugHandler.fine("Jsp: " + jsp);
	DebugHandler.fine("JspBase: " + jspBase);
	if ( jspBase == null ) {
	    DebugHandler.fine("Setting Default jsp base to " + Constants.JSP_BASE_STR);
	    jspBase = Constants.JSP_BASE_STR;
	}
    }
    
    public void doPost( HttpServletRequest req, HttpServletResponse resp )
	throws ServletException, IOException {
	DebugHandler.fine("AppServlet...doPost");
    }
    
    public void doGet( HttpServletRequest req, HttpServletResponse resp )
	throws ServletException, IOException{
	DebugHandler.fine("AppServlet...doGet");
    }

    private void print_table( String msg, Hashtable<String,String> table ) {
	if ( table != null ) {
	    DebugHandler.fine("----------" + msg + "-------------");

	    for (Enumeration<String> e = table.keys(); e.hasMoreElements();) {
		String key = e.nextElement();
		if ( key.equals(AppConstants.PASSWORD_STR))
		    DebugHandler.fine(key + ": ******");
		else
		    DebugHandler.fine(key + ": " + table.get(key));
	    }
	    DebugHandler.fine("----------" + msg + "-------------");
	}
    }

    private void fillJspValue(Hashtable<String,String> table, String login, String jsp) {
	if ( jsp == null || jsp.equals("")) {
	    // If we are logging in then go to 
	    DebugHandler.fine("Login value " + login);
	    DebugHandler.fine("Putting " + AppConstants.DEFAULT_JSP_STR + " for jsp");
	    table.put(Constants.JSP_STR, AppConstants.DEFAULT_JSP_STR);
	}
	else {
	    DebugHandler.fine("Putting passed value " + jsp + " for jsp");
	    table.put(Constants.JSP_STR, jsp);
	}
    }
    
    private Hashtable<String,String> fillHash(HttpServletRequest req) throws IOException {
	Hashtable<String,String> table = new Hashtable<String,String>();
	String type = req.getContentType();
	String jsp = null, login = null;

	DebugHandler.fine("Request Type " + type);
	if ( type != null && type.startsWith("multipart/form-data") ) {
	    MultipartParser parser = new MultipartParser(req, Constants.FILE_SIZE);
	    Part aPart;
	    DebugHandler.fine("MultiPart");
	    while( (aPart = parser.readNextPart()) != null ) {
		if( aPart.isParam() == true ) {
		    ParamPart pPart = (ParamPart)aPart;
		    String key = pPart.getName();
		    String value = Util.trim(pPart.getStringValue());
		    if ( key.equals(Constants.JSP_STR) ) {
			jsp = value;
		    }
		    else if ( key.equals(AppConstants.LOGIN) ) {
			DebugHandler.fine("Multipart Putting " + value + " for " + key);
			login = value;
			table.put(key, value);
		    } else {
			if ( key.equals(AppConstants.PASSWORD_STR) ) {
			    DebugHandler.fine("Multipart Putting ******* for " + key);
			}
			else {
			    DebugHandler.fine("Multipart Putting " + value + " for " + key);
			}
			table.put(key, value);
		    }
		}
		else if( aPart.isFile() == true ) {
		    DebugHandler.fine("MultiPart: Part is File");
		    FilePart fPart = (FilePart)aPart;
		    String field = fPart.getName();
		    String fileName = fPart.getFileName();
		    if(fileName != null) {
			//write the image to a file
			String dir = System.getProperty("java.io.tmpdir");
			File theFile = new File(dir, fileName);
			fPart.writeTo(theFile);
			table.put(field, fileName);
		    }
		}
	    }
	    fillJspValue(table, login, jsp);
	    print_table("Table: ", table);

	}
	else {
	    @SuppressWarnings("unchecked")
	    Enumeration<String>e = req.getParameterNames();

	    while ( e.hasMoreElements() ) {
		String key = e.nextElement();
		String values[] = req.getParameterValues(key);
		String value = "";
		
		// for supporting multiple selects.
		if ( values.length == 1 )
		    value = values[0];
		else {
		    for ( int i = 0; i < values.length; i++ ) {
			value = value + " " + values[i];
		    }
		}
		DebugHandler.fine("Putting " + value + " for " + key);
		table.put(key, value);
	    }
	    jsp = req.getParameter(Constants.JSP_STR);
	    login = req.getParameter(AppConstants.LOGIN);
	    fillJspValue(table, login, jsp);
	}

	return table;
    }
    
    public void service(HttpServletRequest req,         
			HttpServletResponse res) throws ServletException, IOException 
    {
	boolean validSession = req.isRequestedSessionIdValid();
	HttpSession session = req.getSession(true);
	if ( session != null )
	    session.setMaxInactiveInterval(43200); // 12 Hours	

	@SuppressWarnings("unchecked")
	Hashtable<String,String>valuepairs = (Hashtable<String,String>)session.getAttribute(Constants.VALUE_PAIR_STR);
	
	String userNameStr = null;
	String userIdStr = null;
	String passwordStr = null;
	String alreadyLoggedIn = "false";

	print_table("AppServlet:valuepairs: 1: ", valuepairs);
	if ( valuepairs != null ) {
	    userNameStr = valuepairs.get(AppConstants.USER_NAME_STR);
	    userIdStr = valuepairs.get(AppConstants.USER_ID_STR);
	    passwordStr = valuepairs.get(AppConstants.PASSWORD_STR);
	    alreadyLoggedIn = valuepairs.get(AppConstants.ALREADY_LOGGED_IN_STR);
	}
	
	DebugHandler.fine("Username 1: " + userNameStr);
	
	// Now fill from the request
	valuepairs = fillHash(req);
	print_table("AppServlet:valuepairs: 2: ", valuepairs);

	// The following needs to be refilled because it gets reset in Admin Screens.
	String jspStr = valuepairs.get(Constants.JSP_STR);
	boolean loginRequired = false;
	
	if ( jspStr != null && App.isLoginRequired(jspStr)) {
	    // Check to see if we have already logged in
	    if ( alreadyLoggedIn == null || alreadyLoggedIn.equals("false")) {
		DebugHandler.fine("alreadyLoggedIn : " + alreadyLoggedIn);
		// Check to see if the previos request is the first time username was passed out.
		userNameStr = valuepairs.get(AppConstants.USER_NAME_STR);
		passwordStr = valuepairs.get(AppConstants.PASSWORD_STR);
		DebugHandler.fine("Not Already Logged in ");
		UsersInterface uif = new UsersImpl();
		try {
		    UsersObject utObj = uif.authenticate(userNameStr, passwordStr);
		    DebugHandler.fine(utObj);
		    boolean loggedIn = (utObj != null) && (utObj.getUsersId() != 0);
		    if ( loggedIn) {
			valuepairs.put(AppConstants.USER_NAME_STR, userNameStr);
			valuepairs.put(AppConstants.ALREADY_LOGGED_IN_STR, "true");
			valuepairs.put(AppConstants.USER_ID_STR, String.valueOf(utObj.getUsersId()));
			valuepairs.put(AppConstants.ERROR_STR,Constants.EMPTY);
			jspStr = AppConstants.DEFAULT_JSP_STR;
			valuepairs.put(Constants.JSP_STR, jspStr);
		    }
		    else {
			if ( userNameStr != null && passwordStr != null ) 
			    valuepairs.put(AppConstants.ERROR_STR, "Username/password is invalid");
			loginRequired = true;
			valuepairs.put(AppConstants.ALREADY_LOGGED_IN_STR, "false");
		    }

		} 
		catch (AppException ae) { 
		    ae.printStackTrace(); 
		}
	    } // If already logged in pass the values along
	    else {
		DebugHandler.fine("Here ");
		// Fill them from prior session to be used on subsequent access.
		valuepairs.put(AppConstants.ALREADY_LOGGED_IN_STR, alreadyLoggedIn);
		valuepairs.put(AppConstants.PASSWORD_STR, passwordStr);
		valuepairs.put(AppConstants.USER_NAME_STR, userNameStr);
		valuepairs.put(AppConstants.USER_ID_STR, userIdStr);
		String doDownload = valuepairs.get(UtilBean.DOWNLOAD_FLAG_STR);
		DebugHandler.fine("Download Flag : " + doDownload);
		if ( doDownload != null && Boolean.valueOf(doDownload).booleanValue() == true ) {
		    String implName = valuepairs.get(Constants.SS_IMPL_NAME_STR);
		    String implParamsName = valuepairs.get(Constants.SS_IMPL_PARAMS_NAME_STR);
		    String downloadFileName = userIdStr + "download.xlsx";
		    String temp = System.getProperty("java.io.tmpdir") + File.separatorChar + downloadFileName;
		    writeToFile(temp, implName, implParamsName);
		    DebugHandler.fine("Downloading..." +  temp);
		    doDownload( req, res, temp, downloadFileName );
		    return;
		}
		if ( jspStr != null && jspStr.equals(AppConstants.LOGIN_JSP_STR ) ) {
		    jspStr = AppConstants.DEFAULT_JSP_STR;
		    valuepairs.put(Constants.JSP_STR, jspStr);
		}
	    }
	} else { // Entering pages where no login is required
	    // In case we go into pages that does not require login.
	    if ( alreadyLoggedIn != null )
		valuepairs.put(AppConstants.ALREADY_LOGGED_IN_STR, alreadyLoggedIn);
	    if ( passwordStr != null )
		valuepairs.put(AppConstants.PASSWORD_STR, passwordStr);
	    if ( userNameStr != null )
		valuepairs.put(AppConstants.USER_NAME_STR, userNameStr);
	    if ( userIdStr != null )
		valuepairs.put(AppConstants.USER_ID_STR, userIdStr);
	    String doDownload = valuepairs.get(UtilBean.DOWNLOAD_FLAG_STR);
	    DebugHandler.fine("Download Flag : " + doDownload);
	    if ( doDownload != null && Boolean.valueOf(doDownload).booleanValue() == true ) {
		String implName = valuepairs.get(Constants.SS_IMPL_NAME_STR);
		String implParamsName = valuepairs.get(Constants.SS_IMPL_PARAMS_NAME_STR);
		String downloadFileName = userIdStr + "download.xlsx";
		String temp = System.getProperty("java.io.tmpdir") + File.separatorChar + downloadFileName;
		writeToFile(temp, implName, implParamsName);
		DebugHandler.fine("Downloading..." +  temp);
		doDownload( req, res, temp, downloadFileName );
		return;
	    }
	}
	if ( loginRequired ) {
	    valuepairs.put(Constants.NEXT_JSP_STR, jspStr);
	} 
	session.setAttribute(Constants.VALUE_PAIR_STR, valuepairs);
	
	print_table("AppServlet:valuepairs: 3: ", valuepairs);
	DebugHandler.fine("LoginRequired : " + loginRequired);
	// Check for a new session
	if (session != null && session.isNew()) {
	    DebugHandler.fine("jspStr: " + jspStr);
	    if ( jspStr == null || jspStr.equals("") ) {
		DebugHandler.fine("Setting jsp to default " );
		jsp = AppConstants.DEFAULT_JSP_STR;
	    }
	    else
		jsp = jspStr;
	} else {
	    if (!validSession) {
		jsp = "InvalidatePage.jsp";
	    }
	    else {
		jsp = jspStr;
	    }
	}

	// If login is required forward then to the current jsp
	if ( loginRequired ) {
	    getServletConfig().getServletContext().getRequestDispatcher(jspBase + AppConstants.LOGIN_JSP_STR).forward(req, res);
	}
	else {
	    getServletConfig().getServletContext().getRequestDispatcher(jspBase + jsp).forward(req, res);
	    DebugHandler.fine("JSP " + jspBase + jsp);
	}
    }

    void writeToFile(String downloadFileName,String implName, String implParamsName) {
	SpreadSheetInterface ssIf = null;
	Object implParamsObject = null;
	if ( implName != null ) {
	    try {
		@SuppressWarnings("rawtypes")
		Class implDefinition = Class.forName(implName);
		@SuppressWarnings("rawtypes")
		Class implParamsDefinition = null;
		if ( implParamsDefinition != null ) 
		    implParamsDefinition = Class.forName(implParamsName);
		Object implObject = implDefinition.newInstance();
		if ( implParamsName != null ) {
		    implParamsObject = implParamsDefinition.newInstance();
		}
		ssIf = (SpreadSheetInterface)(implObject);
	    } catch (InstantiationException e) {
		DebugHandler.severe(e);
	    } catch (IllegalAccessException e) {
		DebugHandler.severe(e);
	    } catch (ClassNotFoundException e) {
		DebugHandler.severe(e);
	    }
	}
	try {
	    if ( ssIf != null ) {
		DebugHandler.fine("Writing..." +  downloadFileName);
		ssIf.writeToFile(downloadFileName, implParamsObject);
	    }
	} catch (AppException ae) {
	    DebugHandler.severe("Exception while creating the file");
	}
    }

    /**
     *  Sends a file to the ServletResponse output stream.  Typically
     *  you want the browser to receive a different name than the
     *  name the file has been saved in your local database, since
     *  your local names need to be unique.
     *
     *  @param req The request
     *  @param resp The response
     *  @param filename The name of the file you want to download.
     *  @param original_filename The name the browser should receive.
     */
    private void doDownload( HttpServletRequest req, HttpServletResponse resp,
                             String filename, String original_filename )
        throws IOException    {
        File                f        = new File(filename);
        int                 length   = 0;
        ServletOutputStream op       = resp.getOutputStream();

        //
        //  Set the response and go!
        //
        //  Yes, I know that the RFC says 'attachment'.  Unfortunately, IE has a typo
        //  in it somewhere, and Netscape seems to accept this typing as well.
        //
        resp.setContentType( "application/octet-stream" );
        resp.setContentLength( (int)f.length() );
        resp.setHeader( "Content-Disposition", "attachement; filename=\"" + original_filename + "\"" );

        //
        //  Stream to the requester.
        //
        byte[] bbuf = new byte[Constants.FILE_SIZE];
        DataInputStream in = new DataInputStream(new FileInputStream(f));

        while ((in != null) && ((length = in.read(bbuf)) != -1))
        {
            op.write(bbuf,0,length);
        }

        in.close();
        op.flush();
        op.close();
	f.delete();
    }

    
}


