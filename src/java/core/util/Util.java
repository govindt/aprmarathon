/*
 * App.java	
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;

import java.io.*;
import java.lang.*;
import java.util.*;
import java.util.regex.*;
import org.apache.commons.jcs.JCS;
import org.apache.commons.jcs.access.CacheAccess;
import org.apache.commons.jcs.access.exception.CacheException;
import org.apache.commons.lang.exception.*;
import org.apache.commons.jcs.engine.control.CompositeCacheManager;
/*
 * Singletone class for the whole Application
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */


public class Util {

    private static Util 	self;
    private static String APP_CACHE = "APP_CACHE";
    private static String baseurl_;
    
    private static Properties siteProps = null;
    private static Properties otherProps = new Properties();
    private static Properties cacheProps = new Properties();

    //private static JCS appCache;
    private static CacheAccess<String, Object> appCache;
    
    private Util() throws AppException {
	// Change these later to get from Resource File as Properties
	siteProps = new Properties();	
    }
    
    
    public static Properties getOtherProperties() {
	return otherProps;
    }

    public static CacheAccess<String, Object> getAppCache() {
	return appCache;
    }

    public static void putInCache(String key, Object value) {
	try {
	    appCache.put(key, value);
	} catch (CacheException ce) {
	    System.err.println("Unable to put object for " + key);
	}
    }

    public static void removeFromCache(Object key) {
	try {
	    appCache.remove((String)key);
	} catch (CacheException ce) {
	    System.err.println("Unable to remove object for " + key);
	}
    }

    public static void clearCache() {
	try {
	    appCache.clear();
	} catch (CacheException ce) {
	    System.err.println("Unable to clear cache");
	}
    }
    
    public static Util getInstance() throws AppException {
	if ( self == null ) {
	    self = new Util();
	    loadSiteProperties();
	    loadCacheProperties();
	    try {
		DebugHandler.initialize(Constants.APP_LOG);
	    } catch (IOException ioe) {
		System.err.println("Unable to initialize log");
		ioe.printStackTrace();
		System.exit(1);
	    }
	    try {
		CompositeCacheManager ccm = CompositeCacheManager.getUnconfiguredInstance();
		ccm.configure(cacheProps);
		appCache = JCS.getInstance(APP_CACHE);
	    } catch (Exception ne) {
		System.err.println("Unable to initialize cache");
		ne.printStackTrace();
	    }
	}
	
	if ( self == null )
	    throw new AppException();
	
	return self;
    }

    private static Properties loadCacheProperties() throws AppException {
	String cachePropertiesFile = "core.util.cache";
	ResourceBundle rb = ResourceBundle.getBundle(cachePropertiesFile);
	Set<String> set = rb.keySet();

	Iterator<String> iterator = set.iterator();

	while (iterator.hasNext()) {
	    String key = iterator.next();
	    cacheProps.put(key, rb.getString(key));
	} 
	return cacheProps;
    }

    private static Properties loadSiteProperties() throws AppException {
	String sitePropertiesFile
	    = "core.util.site";
	ResourceBundle rb = ResourceBundle.getBundle(sitePropertiesFile);
	Set<String> set = rb.keySet();

	Iterator<String> iterator = set.iterator();

	while (iterator.hasNext()) {
	    String key = iterator.next();
	    siteProps.put(key, rb.getString(key));
	} 
	baseurl_ = siteProps.getProperty("site.baseurl");
	Constants.FONT_TYPE_STR = siteProps.getProperty("site.font_type_str");
	Constants.LIGHT_SHADE = siteProps.getProperty("site.light_shade");
	Constants.DARK_SHADE = siteProps.getProperty("site.dark_shade");
	Constants.SMALL_FONT = Integer.parseInt(siteProps.getProperty("site.small_font"));
	Constants.MEDIUM_FONT = Integer.parseInt(siteProps.getProperty("site.medium_font"));
	Constants.LARGE_FONT = Integer.parseInt(siteProps.getProperty("site.large_font"));
	Constants.LINK_COLOR = siteProps.getProperty("site.link_color");
	Constants.TEXT_COLOR = siteProps.getProperty("site.text_color");
	Constants.HEADING_COLOR = siteProps.getProperty("site.heading_color");
	Constants.HEADING_TEXT_COLOR = siteProps.getProperty("site.heading_text_color");
	Constants.NAV_BAR_BG_COLOR = siteProps.getProperty("site.nav_bar_bg_color");
	Constants.TEXT_LINK_STYLE = siteProps.getProperty("site.text_link_style");
	Constants.MAIN_HEADER_STYLE = siteProps.getProperty("site.main_header_style");
	Constants.BODY_TABLE_STYLE = siteProps.getProperty("site.body_table_style");
	Constants.BODY_ROW_STYLE = siteProps.getProperty("site.body_row_style");
	Constants.ERROR_STRING_STYLE = siteProps.getProperty("site.error_string_style");
	Constants.STATUS_STRING_STYLE = siteProps.getProperty("site.status_string_style");
	Constants.HEADER_TABLE_STYLE = siteProps.getProperty("site.header_table_style");
	Constants.HEADER_ROW_STYLE = siteProps.getProperty("site.header_row_style");
	Constants.SUB_HEADER_TABLE_STYLE = siteProps.getProperty("site.sub_header_table_style");
	Constants.SUB_HEADER_ROW_STYLE = siteProps.getProperty("site.sub_header_row_style");
	Constants.INVALID_TEXT_STYLE = siteProps.getProperty("site.invalid_text_style");
	Constants.MAX_ROWS_RETURNED = Integer.parseInt(siteProps.getProperty("site.max_rows_returned"));
	Constants.APP_LOG = siteProps.getProperty("site.logfile");
	Constants.SMTP_HOST = siteProps.getProperty("site.smtp_host");
	Constants.SMTP_FROM = siteProps.getProperty("site.smtp_from");
	
	Constants.TO_STR = siteProps.getProperty("site.to_str");
	Constants.SUBJECT_STR = siteProps.getProperty("site.subject_str");
	Constants.DELETE_STR = siteProps.getProperty("site.delete_str");
	
	// Regular Expression 
	Constants.SORT_STR = siteProps.getProperty("site.sort_str");
	
	Constants.DATE_FORMAT_STR = siteProps.getProperty("site.date_format");	
	Constants.TIME24_FORMAT_STR = siteProps.getProperty("site.time24_format");	
	Constants.CHANGE_ALL_STR = siteProps.getProperty("site.change_all");

	Constants.NOTE_FOR_LABEL = siteProps.getProperty("site.note_for_label");
	Constants.NUMBER_LABEL = siteProps.getProperty("site.number_label"); // i.e #
	Constants.LABEL_SEPARATOR = siteProps.getProperty("site.label_separator"); // :
	Constants.CLICK_HERE_LABEL = siteProps.getProperty("site.click_here_label");
	Constants.IS_VALID_LABEL = siteProps.getProperty("site.is_valid_label");
	Constants.CLONE_FROM_LABEL = siteProps.getProperty("site.clone_from_label");
	Constants.ADVANCED_LABEL = siteProps.getProperty("site.advanced_label");
	Constants.UPLOAD_FILE_LABEL = siteProps.getProperty("site.upload_file_label");
	Constants.ADD_TO_TO_LIST_LABEL = siteProps.getProperty("site.add_to_to_list_label");
	Constants.ADD_ALL_TO_TO_LIST_LABEL = siteProps.getProperty("site.add_all_to_to_list_label");
	Constants.ADD_TO_FROM_LIST_LABEL = siteProps.getProperty("site.add_to_from_list_label");
	Constants.ADD_ALL_TO_FROM_LIST_LABEL = siteProps.getProperty("site.add_all_to_from_list_label");
	return siteProps;
    }
    
    public static String getSiteProperty (String varKey)
    {
	return (siteProps.getProperty (varKey));
    }
    
    
    public static String getBaseurl() {
	return baseurl_;
    }
    
    public static String trim(String s) {
	if ( s != null )
	    return s.trim();
	else
	    return "";
    }
    
    public static String removeMultipleSpaces(String s) {
	String new_s = "";
	if ( s != null ) {
	    StringTokenizer removeSpaces = new StringTokenizer(s);
	    while (removeSpaces.hasMoreTokens() ) {
		if ( !new_s.equals("") )
		    new_s += " " + removeSpaces.nextToken();
		else
		    new_s = removeSpaces.nextToken();
	    }
	    DebugHandler.debug("New S " + new_s);
	}
	return new_s;
    }

    // Copies src file to dst file.
    // If the dst file does not exist, it is created
    public static void copy(String src, String dst) throws IOException {
        InputStream in = new FileInputStream(new File(src));
        OutputStream out = new FileOutputStream(new File(dst));
    
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ((len = in.read(buf)) > 0) {
            out.write(buf, 0, len);
        }
        in.close();
        out.close();
    }

    public static double round(double value, int decimalPlace) {
    double power_of_ten = 1;
    while (decimalPlace-- > 0)
       power_of_ten *= 10.0;
    return Math.round(value * power_of_ten) 
       / power_of_ten;
    }

    public static Date convertFromTime(Date ssTime) {
	DebugHandler.severe(ssTime);
	Calendar inCal = Calendar.getInstance();
	Calendar ssCal = Calendar.getInstance();
	ssCal.setTime(ssTime);
	inCal.set(Calendar.HOUR_OF_DAY, ssCal.get(Calendar.HOUR_OF_DAY));
	inCal.set(Calendar.MINUTE, ssCal.get(Calendar.MINUTE));
	inCal.set(Calendar.SECOND, ssCal.get(Calendar.SECOND));
	inCal.set(Calendar.YEAR, 1970);
	inCal.set(Calendar.MONTH, 0);
	inCal.set(Calendar.DAY_OF_YEAR, 1);
	return inCal.getTime();
    }
}
