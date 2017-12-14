/*
 * @(#)MultipartRequest.java	1.3 01/02/23
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Enumeration;

import java.util.Date;
import java.text.SimpleDateFormat;

// Copyright (C) 1998 by Jason Hunter <jhunter@acm.org>.  All rights reserved.
// Use of this class is limited.  Please see the LICENSE for more information.

import java.io.*;
import java.util.*;
import javax.servlet.*;

/** 
 * A utility class to handle <tt>multipart/form-data</tt> requests,
 * the kind of requests that support file uploads.  This class can 
 * receive arbitrarily large files (up to an artificial limit you can set),
 * and fairly efficiently too.  
 * It cannot handle nested data (multipart content within multipart content)
 * or internationalized content (such as non Latin-1 filenames).
 * <p>
 * It's used like this:
 * <blockquote><pre>
 * MultipartRequest multi = new MultipartRequest(req, ".");
 * &nbsp;
 * out.println("Params:");
 * Enumeration params = multi.getParameterNames();
 * while (params.hasMoreElements()) {
 *   String name = (String)params.nextElement();
 *   String value = multi.getParameter(name);
 *   out.println(name + " = " + value);
 * }
 * out.println();
 * &nbsp;
 * out.println("Files:");
 * Enumeration files = multi.getFileNames();
 * while (files.hasMoreElements()) {
 *   String name = (String)files.nextElement();
 *   String filename = multi.getFilesystemName(name);
 *   String type = multi.getContentType(name);
 *   File f = multi.getFile(name);
 *   out.println("name: " + name);
 *   out.println("filename: " + filename);
 *   out.println("type: " + type);
 *   if (f != null) {
 *     out.println("f.toString(): " + f.toString());
 *     out.println("f.getName(): " + f.getName());
 *     out.println("f.exists(): " + f.exists());
 *     out.println("f.length(): " + f.length());
 *     out.println();
 *   }
 * }
 * </pre></blockquote>
 *
 * A client can upload files using an HTML form with the following structure.
 * Note that not all browsers support file uploads.
 * <blockquote><pre>
 * &lt;FORM ACTION="/servlet/Handler" METHOD=POST
 *          ENCTYPE="multipart/form-data"&gt;
 * What is your name? &lt;INPUT TYPE=TEXT NAME=submitter&gt; &lt;BR&gt;
 * Which file to upload? &lt;INPUT TYPE=FILE NAME=file&gt; &lt;BR&gt;
 * &lt;INPUT TYPE=SUBMIT&GT;
 * &lt;/FORM&gt;
 * </pre></blockquote>
 * <p>
 * The full file upload specification is contained in experimental RFC 1867,
 * available at <a href="http://ds.internic.net/rfc/rfc1867.txt">
 * http://ds.internic.net/rfc/rfc1867.txt</a>.
 *
 * @author	Govind Thirumalai
 * @version	1.0
 * @version	1.0
 */
public class MultipartRequest {

    private static final int DEFAULT_MAX_POST_SIZE = 100 * 1024 * 1024;  // Maximum tested 100 Megabytes rej.
    private static final int DEFAULT_MIN_POST_SIZE = 1024 * 1024;  // Minimum upload sizing to make sure file upload
    private static final int DEFAULT_MAX_READ_BUFFER_SIZE = 100 * 1024;          //  100 KB
    private static final int DEFAULT_MAX_WRITE_BUFFER_SIZE = 100 * 1024;         //  100 KB
    
    private static final int DEFAULT_READ_BUFFER_SIZE = 8 * 1024;          //  8 KB and minimum
    private static final int DEFAULT_WRITE_BUFFER_SIZE = 8 * 1024;         //  8 KB and minimum
    
    
    private ServletRequest req;
    private File dir;
    private String saveDirectory;
    private int maxSize;
    private int maxReadBufferSize;
    private int maxWriteBufferSize;
    
    private Hashtable<String,String> parameters = new Hashtable<String, String>();  // name - value
    private Hashtable<String,UploadedFile> files = new Hashtable<String,UploadedFile>();       // name - UploadedFile
    
    private boolean hack_1 = false;	// Hack # 1 for setting directory based on hidden form data
    
    /**
     * Constructs a new MultipartRequest to handle the specified request, 
     * saving any uploaded files to the given directory, and limiting the 
     * upload size to 1 Megabyte.  If the content is too large, an
     * IOException is thrown.  This constructor actually parses the 
     * <tt>multipart/form-data</tt> and throws an IOException if there's any 
     * problem reading or parsing the request.
     *
     * @param request the servlet request
     * @param saveDirectory the directory in which to save any uploaded files
     * @exception IOException if the uploaded content is larger than 1 Megabyte
     * or there's a problem reading or parsing the request
     */
    public MultipartRequest(ServletRequest request,
			    String saveDirectory) throws AppException, IOException {
	this(request, saveDirectory, DEFAULT_MAX_POST_SIZE);
    }
    
    /**
     * Constructs a new MultipartRequest to handle the specified request, 
     * saving any uploaded files to the given directory, and limiting the 
     * upload size to the specified length.  If the content is too large, an 
     * IOException is thrown.  This constructor actually parses the 
     * <tt>multipart/form-data</tt> and throws an IOException if there's any 
     * problem reading or parsing the request.
     *
     * @param request the servlet request
     * @param saveDirectory the directory in which to save any uploaded files
     * @param maxPostSize the maximum size of the POST content
     * @exception IOException if the uploaded content is larger than 
     * <tt>maxPostSize</tt> or there's a problem reading or parsing the request
     * due to Requirements, we are going to guarentee all name/value params of type hidden come first
     * followed by the filetypename, filename block of files and lastly by the comments textarea/note
     * rej. special hacks for special cases.12-Dec-1999
     * Hack #1: if saveDirectory comes in as "directory" then set flag to call constructor on dir/File once the
     *          name/value has been retrieved from the hidden area of the form.
     */
    public MultipartRequest(ServletRequest request,
			    String saveDirectory,
			    int maxPostSize) throws AppException, IOException {
	
	// initialize all values which might come from the site.properties files
	// for maximum content upload, read and write buffer sizes
	this.saveDirectory = saveDirectory;
	loadSiteDefaults();
	
	
	// Sanity check values
	if (request == null)
	    throw new IllegalArgumentException("request cannot be null");
	if (saveDirectory == null)
	    throw new IllegalArgumentException("saveDirectory cannot be null");
	
	// Save the request, dir, and max size
	req = request;
	if (saveDirectory.equalsIgnoreCase("directory")) {
	    // we need to set flag to do initialization later
	    hack_1 = true;
	}
	
	
	// making use of Hack_1 here.  Please note, need to alter the readRequest to immediately trap
	// for this hack and set this value!
	if (!hack_1) {
	    setAndTestDirectory(saveDirectory);
	}
	
	// Now parse the request saving data to "parameters" and "files";
	// write the file contents to the saveDirectory
	readRequest();
    }
    
    /*******
     *
     Special site Properties load feature to override the hardcoded values for
     1) Maximum content during upload
     2) Read buffer size
     3) Write buffer size
     
     All parameters will have defaults in event the site.properties files causes difficulties
     *
     *******/
    
    private void loadSiteDefaults() throws AppException {
	
	String sitePropertiesFile
	    = "core.util.site";
	
	Properties siteProps = new Properties();
	try {
	    ResourceBundle rb = ResourceBundle.getBundle(sitePropertiesFile);
	    for ( Enumeration<String> iterator = rb.getKeys(); iterator.hasMoreElements(); ) {
		String key = iterator.nextElement();
		siteProps.put(key, rb.getString(key));
	    }
	} catch (Exception e) {
	    throw new AppException("sitePropsLoadError");
	}
	
	int maxPostSize;
	int readBufferSize;
	int writeBufferSize;
	
	try {
	    maxPostSize = Integer.parseInt(siteProps.getProperty("site.maxPostSize"));
	} catch (NumberFormatException nfe) {
	    maxPostSize = DEFAULT_MAX_POST_SIZE;
	}
	
	try {
	    readBufferSize = Integer.parseInt(siteProps.getProperty("site.readBufferSize"));
	} catch (NumberFormatException nfe) {
	    readBufferSize = DEFAULT_READ_BUFFER_SIZE;
	}
	
	try {
	    writeBufferSize = Integer.parseInt(siteProps.getProperty("site.writeBufferSize"));
	} catch (NumberFormatException nfe) {
	    writeBufferSize = DEFAULT_WRITE_BUFFER_SIZE;
	}
	
	// Now set the three values into the 'private int's setters
	setMaxPostSize(maxPostSize);
	setReadBufferSize(readBufferSize);
	setWriteBufferSize(writeBufferSize);
	
        // output some debug information to make sure everything looks okay, convert to the debugger when done.
	
	DebugHandler.debug("MultipartRequest:loadSiteDefaults()->setmaxPostSize = " + maxPostSize);
	DebugHandler.debug("MultipartRequest:loadSiteDefaults()->readBufferSize = " + readBufferSize);
	DebugHandler.debug("MultipartRequest:loadSiteDefaults()->writeBufferSize = " + writeBufferSize);
    }

    public String getSaveDirectory() {
	return saveDirectory;
    }

    private void setAndTestDirectory(String saveDirectory) throws IOException {
	
	// making use of Hack_1 here.  Please note, need to alter the readRequest to immediately trap
	// for this hack and set this value!
	if (saveDirectory == null)
	    throw new IllegalArgumentException("saveDirectory cannot be null");
	
	dir = new File(saveDirectory);
	
	if (!dir.exists()) {
	    if (!dir.mkdir()) 
		throw new IOException( "Failed to create dir: "+saveDirectory);
	}
	
	// Check saveDirectory is truly a directory
	if (!dir.isDirectory())
	    throw new IllegalArgumentException("Not a directory: " + saveDirectory);
	
	// Check saveDirectory is writable
	if (!dir.canWrite())
	    throw new IllegalArgumentException("Not writable: " + saveDirectory);
    }
    
    
    /**
     * Returns the names of all the parameters as an Enumeration of 
     * Strings.  It returns an empty Enumeration if there are no parameters.
     *
     * @return the names of all the parameters as an Enumeration of Strings
     */
    public Enumeration<String> getParameterNames() {
	return parameters.keys();
    }
    
    /**
     * Returns the names of all the uploaded files as an Enumeration of 
     * Strings.  It returns an empty Enumeration if there are no uploaded 
     * files.  Each file name is the name specified by the form, not by 
     * the user.
     *
     * @return the names of all the uploaded files as an Enumeration of Strings
     */
    public Enumeration<String> getFileNames() {
	return files.keys();
    }
    
    /**
     * Returns the value of the named parameter as a String, or null if 
     * the parameter was not given.  The value is guaranteed to be in its 
     * normal, decoded form.  If the parameter has multiple values, only 
     * the last one is returned.
     *
     * @param name the parameter name
     * @return the parameter value
     */
    public String getParameter(String name) {
	try {
	    String param = parameters.get(name);
	    if (param.equals("")) return null;
	    return param;
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    /**
     * Returns the filesystem name of the specified file, or null if the 
     * file was not included in the upload.  A filesystem name is the name 
     * specified by the user.  It is also the name under which the file is 
     * actually saved.
     *
     * @param name the file name
     * @return the filesystem name of the file
     */
    public String getFilesystemName(String name) {
	try {
	    UploadedFile file = files.get(name);
	    return file.getFilesystemName();  // may be null
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    /**
     * Returns the content type of the specified file (as supplied by the 
     * client browser), or null if the file was not included in the upload.
     *
     * @param name the file name
     * @return the content type of the file
     */
    public String getContentType(String name) {
	try {
	    UploadedFile file = files.get(name);
	    return file.getContentType();  // may be null
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    /**
     * Returns a File object for the specified file saved on the server's 
     * filesystem, or null if the file was not included in the upload.
     *
     * @param name the file name
     * @return a File object for the named file
     */
    public File getFile(String name) {
	try {
	    UploadedFile file = files.get(name);
	    return file.getFile();  // may be null
	}
	catch (Exception e) {
	    return null;
	}
    }
    
    /**
     * The workhorse method that actually parses the request.  A subclass 
     * can override this method for a better optimized or differently
     * behaved implementation.
     *
     * @exception IOException if the uploaded content is larger than 
     * <tt>maxSize</tt> or there's a problem parsing the request
     */
    protected void readRequest() throws AppException, IOException {
	// Check the content type to make sure it's "multipart/form-data"
	String type = req.getContentType();
	if (type == null || 
	    !type.toLowerCase().startsWith("multipart/form-data")) {
	    throw new IOException("Posted content type isn't multipart/form-data");
	}
	
	// Check the content length to prevent denial of service attacks
   	int length = req.getContentLength();
	DebugHandler.debug("MultipartRequest:readRequest Posted content of  = " + length + " max set limit of " + maxSize);
	//   try {
    	if (length > maxSize) {
	    DebugHandler.debug("MultipartRequest:readRequest MAXIMUM Posted content of  = " + length + " exceeds limit of " + maxSize);
	    Object[] args = { String.valueOf(maxSize) };
	    throw new AppException("contentLengthExceedsMaximum", args);
	}
	
	String boundary = extractBoundary(type);
	if (boundary == null) {
	    throw new IOException("Separation boundary was not specified");
	}
	
	// Construct the special input stream we'll read from
	MultipartInputStreamHandler in =
	    new MultipartInputStreamHandler(req.getInputStream(), boundary, length);
	
	// Read the first line, should be the first boundary
	String line = in.readLine();
	if (line == null) {
	    throw new IOException("Corrupt form data: premature ending");
	}
	
	// Verify that the line is the boundary
	if (!line.startsWith(boundary)) {
	    throw new IOException("Corrupt form data: no leading boundary");
	}
	
	// Now that we're just beyond the first boundary, loop over each part
	boolean done = false;
	while (!done) {
	    done = readNextPart(in, boundary);
	}
    }
    
    /**
     * A utility method that reads an individual part.  Discorees to 
     * readParameter() and readAndSaveFile() to do the actual work.  A 
     * subclass can override this method for a better optimized or 
     * differently behaved implementation.
     * 
     * @param in the stream from which to read the part
     * @param boundary the boundary separating parts
     * @return a flag indicating whether this is the last part
     * @exception IOException if there's a problem reading or parsing the
     * request
     *
     * @see readParameter
     * @see readAndSaveFile
     */
    protected boolean readNextPart(MultipartInputStreamHandler in,
				   String boundary) throws IOException {
	// Read the first line, should look like this:
	// content-disposition: form-data; name="field1"; filename="file1.txt"
	String line = in.readLine();
	if (line == null) {
	    return true;
	}
	
	// Parse the content-disposition line
	String[] dispInfo = extractDispositionInfo(line);
	String disposition = dispInfo[0];
	String name = dispInfo[1];
	String filename = dispInfo[2];
	
	// Now onto the next line.  This will either be empty 
	// or contain a Content-Type and then an empty line.
	line = in.readLine();
	if (line == null) {
	    // No parts left, we're done
	    return true;
	}
	
	// Get the content type, or null if none specified
	String contentType = extractContentType(line);
	if (contentType != null) {
	    // Eat the empty line
	    line = in.readLine();
	    if (line == null || line.length() > 0) {  // line should be empty
		throw new 
		    IOException("Malformed line after content type: " + line);
	    }
	}
	else {
	    // Assume a default content type
	    contentType = "application/octet-stream";
	}
	
	// apply continuation of hack_1 by adjusting things on the fly warning will robinson, danger!
	// ADDING HACK_1 in this block
	// rej 12-DEC-1999
	// Now, finally, we read the content (end after reading the boundary)
	if (filename == null) {
	    // This is a parameter
	    String value = readParameter(in, boundary);
	    parameters.put(name, value);
	    
	    // the hack_1 area starts here
	    // the hack_1 area starts here
	    // the hack_1 area starts here
	    
	    if (hack_1 && name.equalsIgnoreCase("directory")) {
        	// we need to set value for this special passed parameter to do initialization later
		// next we call the routine to set and test this parameter
        	setAndTestDirectory(value);
		
		// finally, since we're okay at this point unless we throw an exception, turn off hack_1
		// only used once and must be disabled
		hack_1 = false;
	    }
	    
	    // end of the hack_1 area stops here
	    // end of the hack_1 area stops here
	    // end of the hack_1 area stops here
	}
	else {
	    
	    // because the code handles any input that sends a null for filename and null field values, use special
	    // logic to trap and remove from the list!
	    
	    if (filename.equals("User Uploaded No File for this input file type field")) {
       		// we are not going to even bother to save these empty ones for now! 
		// process through the readAndSaveFile to walk over the non-file area
        	readAndSaveFile(in, boundary, filename);
		// now just unlink this temporary file
		File f = new File(dir + File.separator + filename);
        	// files.put(name, new UploadedFile(null, null, null));
		f.delete();
	    }
	    else {
		
		
		// fall through case of hack_1 by preventing the form from submitting without values for directory
		// special case where we've applied the hack but it's not in the stream
		
		// ADDING HACK_1 in this block
		// rej 12-DEC-1999
		
		// the hack_1 area starts here
		// the hack_1 area starts here
		// the hack_1 area starts here
		
		if (hack_1) {
		    // we need to use the default save directory or locate via App Stage Two
		    // we need to set value for this special passed parameter to do initialization later
		    // next we call the routine to set and test this parameter
		    setAndTestDirectory("/usr/tmp"); //  yes it's ugly, but thats a hack!
		    
		    // finally, since we're okay at this point unless we throw an exception, turn off hack_1
		    // only used once and must be disabled
		    hack_1 = false;
		}
		
		// end of the hack_1 area stops here
		// end of the hack_1 area stops here
		// end of the hack_1 area stops here
		
		
		String flname = filename;
		String ext = "";
		int ind = filename.lastIndexOf(".");
		if ( ind != -1 ) {
		    flname = filename.substring(0,ind);
		    ext = filename.substring(ind);
		}
		File tempfile = new File(dir,filename);
		while ( tempfile.exists() ) {
		    Date dt = new Date();
		    SimpleDateFormat sdf = new SimpleDateFormat("MM.dd.yy_HH.mm.ss");
		    String fmt = sdf.format(dt);
		    filename = flname + "." + fmt + ext;
		    tempfile = new File(dir,filename);
		}
		
		// okay, now lets save the updated name of the fully qualified filename and let the system save it
		
		// This is a file
		readAndSaveFile(in, boundary, filename);
		files.put(name,
			  new UploadedFile(dir.toString(), filename, contentType));
	    }
	}
	return false;  // there's more to read
    }
    
    /**
     * A utility method that reads a single part of the multipart request 
     * that represents a parameter.  A subclass can override this method 
     * for a better optimized or differently behaved implementation.
     *
     * @param in the stream from which to read the parameter information
     * @param boundary the boundary signifying the end of this part
     * @return the parameter value
     * @exception IOException if there's a problem reading or parsing the 
     * request
     */
    protected String readParameter(MultipartInputStreamHandler in,
				   String boundary) throws IOException {
	StringBuffer sbuf = new StringBuffer();
	String line;
	
	while ((line = in.readLine()) != null) {
	    if (line.startsWith(boundary)) break;
	    sbuf.append(line + "\r\n");  // add the \r\n in case there are many lines
	}
	
	if (sbuf.length() == 0) {
	    return null;  // nothing read
	}
	
	sbuf.setLength(sbuf.length() - 2);  // cut off the last line's \r\n
	return sbuf.toString();  // no URL decoding needed
    }
    
    /**
     * A utility method that reads a single part of the multipart request 
     * that represents a file, and saves the file to the given directory.
     * A subclass can override this method for a better optimized or 
     * differently behaved implementation.
     *
     * @param in the stream from which to read the file
     * @param boundary the boundary signifying the end of this part
     * @param dir the directory in which to save the uploaded file
     * @param filename the name under which to save the uploaded file
     * @exception IOException if there's a problem reading or parsing the 
     * request
     */
    protected void readAndSaveFile(MultipartInputStreamHandler in,
				   String boundary,
				   String filename) throws IOException {
	File f = new File(dir + File.separator + filename);
	FileOutputStream fos = new FileOutputStream(f);
	BufferedOutputStream out = new BufferedOutputStream(fos, maxWriteBufferSize); 
	byte[] bbuf = new byte[maxReadBufferSize];  // 100K altered to reflect site rejects
	int result;
	String line;
	
	// ServletInputStream.readLine() has the annoying habit of 
	// adding a \r\n to the end of the last line.  
	// Since we want a byte-for-byte transfer, we have to cut those chars.
	boolean rnflag = false;
	while ((result = in.readLine(bbuf, 0, bbuf.length)) != -1) {
	    // Check for boundary
	    if (result > 2 && bbuf[0] == '-' && bbuf[1] == '-') { // quick pre-check
		line = new String(bbuf, 0, result, "ISO-8859-1");
		if (line.startsWith(boundary)) break;
	    }
	    // Are we supposed to write \r\n for the last iteration?
	    if (rnflag) {
		out.write('\r'); out.write('\n');
		rnflag = false;
	    }
	    // Write the buffer, postpone any ending \r\n
	    if (result >= 2 && 
		bbuf[result - 2] == '\r' && 
		bbuf[result - 1] == '\n') {
		out.write(bbuf, 0, result - 2);  // skip the last 2 chars
		rnflag = true;  // make a note to write them on the next iteration
	    }
	    else {
		out.write(bbuf, 0, result);
	    }
	}
	out.flush();
	out.close();
	fos.close();
    }
    
    // Extracts and returns the boundary token from a line.
    //
    private String extractBoundary(String line) {
	int index = line.indexOf("boundary=");
	if (index == -1) {
	    return null;
	}
	String boundary = line.substring(index + 9);  // 9 for "boundary="
	
	// The real boundary is always preceeded by an extra "--"
	boundary = "--" + boundary;
	
	return boundary;
    }
    
    // Extracts and returns disposition info from a line, as a String array
    // with elements: disposition, name, filename.  Throws an IOException 
    // if the line is malformatted.
    //
    private String[] extractDispositionInfo(String line) throws IOException {
	// Return the line's data as an array: disposition, name, filename
	String[] retval = new String[3];
	
	// Convert the line to a lowercase string without the ending \r\n
	// Keep the original line for error messages and for variable names.
	String origline = line;
	line = origline.toLowerCase();
	
	// Get the content disposition, should be "form-data"
	int start = line.indexOf("content-disposition: ");
	int end = line.indexOf(";");
	if (start == -1 || end == -1) {
	    throw new IOException("Content disposition corrupt: " + origline);
	}
	String disposition = line.substring(start + 21, end);
	if (!disposition.equals("form-data")) {
	    throw new IOException("Invalid content disposition: " + disposition);
	}
	
	// Get the field name
	start = line.indexOf("name=\"", end);  // start at last semicolon
	end = line.indexOf("\"", start + 7);   // skip name=\"
	if (start == -1 || end == -1) {
	    throw new IOException("Content disposition corrupt: " + origline);
	}
	String name = origline.substring(start + 6, end);
	
	// Get the filename, if given
	String filename = null;
	start = line.indexOf("filename=\"", end + 2);  // start after name
	end = line.indexOf("\"", start + 10);          // skip filename=\"
	if (start != -1 && end != -1) {                // note the !=
	    filename = origline.substring(start + 10, end);
	    // The filename may contain a full path.  Cut to just the filename.
	    int slash =
		Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
	    if (slash > -1) {
		filename = filename.substring(slash + 1);  // past last slash
	    }
	    if (filename.equals("")) filename = "User Uploaded No File for this input file type field"; // sanity check
	}
	
	// Return a String array: disposition, name, filename
	retval[0] = disposition;
	retval[1] = name;
	retval[2] = filename;
	return retval;
    }
    
    // Extracts and returns the content type from a line, or null if the
    // line was empty.  Throws an IOException if the line is malformatted.
    //
    private String extractContentType(String line) throws IOException {
	String contentType = null;
	
	// Convert the line to a lowercase string
	String origline = line;
	line = origline.toLowerCase();
	
	// Get the content type, if any
	if (line.startsWith("content-type")) {
	    int start = line.indexOf(" ");
	    if (start == -1) {
		throw new IOException("Content type corrupt: " + origline);
	    }
	    contentType = line.substring(start + 1);
	}
	else if (line.length() != 0) {  // no content type, so should be empty
	    throw new IOException("Malformed line after disposition: " + origline);
	}
	
	return contentType;
    }
    
    /**
     * method to remove unwanted character from uploaded filename during uploading files
     *
     * @param original the parameter name
     * @return the parameter retval with the adjusted filename
     */
    
    private String resetFileName( String original )
    {
	// initially, we trim to remove extra white space front and back
	String orig = original.trim();
        char chars[] = orig.toCharArray();
	String otherCharacters = ".,+-:&";
        String retval = new String();
	
	
        for (int i=0;i<chars.length;i++)
	    {
                if (Character.isJavaIdentifierPart(chars[i]) || 
		    otherCharacters.indexOf(chars[i]) != -1) retval += chars[i];
	    }
	// now for sanity, insure file isn't totally removed
	if (retval == null || retval.length() < 1)
	    retval = "Unknown_Filename_Uploaded";
	
        return retval;
    }
    
    
    
    /**
     * setter for the maximum Post Size upload size when uploading files
     *
     * @param name the parameter size used for setting maximum content length
     * @return the parameter void just a setter
     */
    private void setMaxPostSize(int size) {
        // check against the maximum and only set if larger
        if(size > DEFAULT_MAX_POST_SIZE)
            maxSize = DEFAULT_MAX_POST_SIZE;
	else if(size < DEFAULT_MIN_POST_SIZE)
	    maxSize = DEFAULT_MIN_POST_SIZE;
        else
            maxSize = size;
	
    }
    
    

    /**
     * setter for the maximum Read Buffer Size During Upload files
     *
     * @param name the parameter size used to set the read buffer value
     * @return the parameter void just setter
     */
    private void setReadBufferSize(int size) {
        // check against the maximum and only reset if certain conditions occur
	// if the value coming in is greater than the tested max, reset
	// if the value coming in is smaller than the default, reset
	// else set to the value coming in
	
        if(size > DEFAULT_MAX_READ_BUFFER_SIZE)
            maxReadBufferSize = DEFAULT_MAX_READ_BUFFER_SIZE;
        else if(size < DEFAULT_READ_BUFFER_SIZE)
	    maxReadBufferSize = DEFAULT_READ_BUFFER_SIZE;
	else
            maxReadBufferSize = size;
    }
    
    
    /**
     * setter for the maximum Write Buffer Size During Upload files and writing to disk
     *
     * @param name the parameter size used to set the write buffer value
     * @return the parameter void just setter
     */
    private void setWriteBufferSize(int size) {
        // check against the maximum and only reset if certain conditions occur
        // if the value coming in is greater than the tested max, reset
        // if the value coming in is smaller than the default, reset
        // else set to the value coming in
	
        if(size > DEFAULT_MAX_WRITE_BUFFER_SIZE)
            maxWriteBufferSize = DEFAULT_MAX_WRITE_BUFFER_SIZE;
        else if(size < DEFAULT_WRITE_BUFFER_SIZE)
            maxWriteBufferSize = DEFAULT_WRITE_BUFFER_SIZE;
        else
            maxWriteBufferSize = size;
    }
}

// A class to hold information about an uploaded file.
//
class UploadedFile {
    
    private String dir;
    private String filename;
    private String type;
    
    UploadedFile(String dir, String filename, String type) {
	this.dir = dir;
	this.filename = filename;
	this.type = type;
    }
    
    public String getContentType() {
	return type;
    }
    
    public String getFilesystemName() {
	return filename;
    }
    
    public File getFile() {
	if (dir == null || filename == null) {
	    return null;
	}
	else {
	    return new File(dir + File.separator + filename);
	}
    }
}


// A class to aid in reading multipart/form-data from a ServletInputStream.
// It keeps track of how many bytes have been read and detects when the
// Content-Length limit has been reached.  This is necessary since some 
// servlet engines are slow to notice the end of stream.
//
class MultipartInputStreamHandler {
    
    ServletInputStream in;
    String boundary;
    int totalExpected;
    int totalRead = 0;
    byte[] buf = new byte[8 * 1024];
    
    public MultipartInputStreamHandler(ServletInputStream in,
				       String boundary,
				       int totalExpected) {
	this.in = in;
	this.boundary = boundary;
	this.totalExpected = totalExpected;
    }

    // Reads the next line of input.  Returns null to indicate the end
    // of stream.
    //
    public String readLine() throws IOException {
	StringBuffer sbuf = new StringBuffer();
	int result;
	String line;
	
	do {
	    result = this.readLine(buf, 0, buf.length);  // this.readLine() does +=
	    if (result != -1) {
		sbuf.append(new String(buf, 0, result, "ISO-8859-1"));
	    }
	} while (result == buf.length);  // loop only if the buffer was filled
	
	if (sbuf.length() == 0) {
	    return null;  // nothing read, must be at the end of stream
	}
	
	sbuf.setLength(sbuf.length() - 2);  // cut off the trailing \r\n
	return sbuf.toString();
    }
    
    // A pass-through to ServletInputStream.readLine() that keeps track
    // of how many bytes have been read and stops reading when the 
    // Content-Length limit has been reached.
    //
    public int readLine(byte b[], int off, int len) throws IOException {
	if (totalRead >= totalExpected) {
	    return -1;
	}
	else {
	    int result = in.readLine(b, off, len);
	    if (result > 0) {
		totalRead += result;
	    }
	    return result;
	}
    }
}
