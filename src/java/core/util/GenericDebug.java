/*
 * @(#)GenericDebug.java	1.3 01/02/23
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;

import java.io.*;
import java.lang.reflect.*;

/**
 * Generic Implementation of DebugInterface
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class GenericDebug implements DebugInterface {

	PrintWriter logWriter;
	PrintWriter debugWriter;
	boolean sendDebugToLog;
	boolean logSwitch;
	boolean debugSwitch;

	public PrintWriter getLogWriter() {
		return logWriter;
	}

	public PrintWriter getDebugWriter() {
		return debugWriter;
	}

	public GenericDebug() {
		logSwitch = true;
		debugSwitch = true;
	}

	public void setLogFile( String logFileName ) throws DebugException {
		try {
			logWriter = new PrintWriter(new BufferedWriter(new FileWriter(logFileName, true)));
		} catch ( IOException io ) {
			throw new DebugException(io.getMessage(), null, AppException.SEVERITY_FATAL, io);
		}
	}

	public void setLogStream( OutputStream logStream ) {
		this.logWriter = new PrintWriter(logStream);
	}

	public void setDebugStream( OutputStream debugStream ) {
		this.debugWriter = new PrintWriter(debugStream);
	}

	public void setLogWriter( Writer logWriter ) {
		this.logWriter = new PrintWriter(logWriter);
	}

	public void setDebugWriter( Writer debugWriter ) {
		this.debugWriter = new PrintWriter(debugWriter);
	}

	public void debug(Object msg) {
		if ( debugSwitch ) {
			if ( debugWriter != null ) {
				debugWriter.println(msg);
				debugWriter.flush();
			}
			if ( sendDebugToLog )
				log(msg);
		}
	}

	public void debugObject(Object obj) {
	    if ( debugSwitch ) {
		@SuppressWarnings("rawtypes")
		Class cls = obj.getClass();
		debugFields(cls,obj);
	    }
	}

        @SuppressWarnings("rawtypes")
	protected void debugFields(Class cls, Object obj) {
		String msg = "Class : " + cls.getName();
		debug(msg);
		@SuppressWarnings("rawtypes")
		Class superCls = cls.getSuperclass();
		if ( superCls != null )
			debugFields(superCls, obj);	
		Field fld[] = cls.getDeclaredFields();
		for(int i=0; i<fld.length;i++) {
			msg = "\t" + fld[i].getName() + " :\t"; 
			try {
				msg += fld[i].get(obj);
			} catch ( IllegalAccessException iae ) {
				msg += "Field Inaccessible";
			}
			debug(msg);
		}
	}
	
	public void log(Object msg) {
		if ( logSwitch ) {
			if ( logWriter != null )
				logWriter.println(msg);
		}
	}

	public void trace(Object obj) {

	}

	public void sendDebugToLog( boolean flg ) {
		sendDebugToLog = flg;
	}

	public void turnDebug( boolean on_off ) {
		debugSwitch = on_off;	
	}

	public void turnLog( boolean on_off ) {
		logSwitch = on_off;	
	}

}
