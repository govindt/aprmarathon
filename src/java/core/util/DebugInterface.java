/*
 * @(#)DebugInterface.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;

import java.io.*;


/**
 * DebugInterface needs to be implemented by all Debug Classes
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public interface DebugInterface {

	public void setLogFile( String logFile ) throws DebugException ;
	public void setLogStream( OutputStream logStream );
	public void setDebugStream( OutputStream debugStream );
	public void setLogWriter( Writer logWriter );
	public void setDebugWriter( Writer debugWriter );
	public PrintWriter getLogWriter( );
	public PrintWriter getDebugWriter( );

	public void debug(Object msg);
	public void debugObject(Object obj);
	public void log(Object msg);
	public void trace(Object obj);
	public void sendDebugToLog( boolean flg );

	public void turnDebug( boolean on_off );
	public void turnLog( boolean on_off );
	

}
