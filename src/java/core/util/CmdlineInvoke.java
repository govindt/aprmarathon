/*
 * @(#)CmdlineInvoke.java	1.2 01/12/10
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */

package core.util;

import java.io.*;
import java.lang.*;
import java.text.*;

public class CmdlineInvoke {
    public static String invoke(String program, PrintWriter out, boolean htmlOutput) throws core.util.AppException {
	Runtime rt = Runtime.getRuntime();
	Process process;
	BufferedReader pInput;
	String retVal = "";
	try {
	    process = rt.exec( program );
	    process.waitFor();
	    pInput = new BufferedReader( new InputStreamReader(process.getInputStream()));
	    try {
		String s = pInput.readLine();
		while ( s != null )	{
		    if ( htmlOutput ) {
			if ( out != null )
			    out.println( s + "<BR>" );
			retVal += s + "<BR>";
			
		    }
		    else {
			if ( out != null )
			    out.println( s );
			retVal += s + "\n";
		    }
		    s = pInput.readLine();
		}
	    }
	    catch ( IOException e )	{
		System.out.println( e.toString());
	    }
	}
	catch ( IOException e )	{
	    if ( out != null )
		out.println( "I/O Error: " + e.toString());
	} catch (InterruptedException e1) {
	    if ( out != null )
		out.println("Exception: " + e1.getMessage());
	}
	return retVal;
    } 
}
