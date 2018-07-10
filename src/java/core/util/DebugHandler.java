/*
 * @(#)DebugHandler.java	1.3 01/02/23
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;
import java.util.logging.*;
import java.io.IOException;

/**
 * DebugHandler Provides a Way of creating the DebugInterface Implementations
 * on the Fly
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */


public class DebugHandler {
    private static Logger logger;
    public static void initialize(String logFile) throws IOException {
	logger = Logger.getLogger(DebugHandler.class.getName());
	FileHandler fHandler = new FileHandler(logFile);
	fHandler.setFormatter(new ConsoleHandlerFormatter());
	logger.addHandler(fHandler);
    }
    
    public static void debug(Object msg) {
	if ( msg != null ) {
		StackTraceElement[] st = new Throwable().getStackTrace();
		if ( st.length > 2 )
			logger.log(Level.FINEST, st[2].getClassName() + " : " + st[1].getMethodName() + " : " + msg.toString());
		else 	
			logger.log(Level.FINEST, msg.toString()); 
	}
    }

    public static void log(Level l, Object msg) {
	if ( msg != null ) {
		StackTraceElement[] st = new Throwable().getStackTrace();
		if ( st.length > 2 )
			logger.log(l, st[2].getClassName() + " : " + st[1].getMethodName() + " : " + msg.toString()); 	
		else
			logger.log(l, msg.toString());
	}
    }

    public static void info(Object msg) {
	if ( msg != null ) {
		StackTraceElement[] st = new Throwable().getStackTrace();
		if ( st.length > 2 )
			logger.info(st[2].getClassName() + " : " + st[1].getMethodName() + " : " + msg.toString());
		else
			logger.info(msg.toString());
	}
    }

    public static void warning(Object msg) {
	if ( msg != null ) {
		StackTraceElement[] st = new Throwable().getStackTrace();
		logger.warning(st[2].getClassName() + " : " + st[1].getMethodName() + " : " + msg.toString());
	}
    }

    public static void fine(Object msg) {
	if ( msg != null ) {
		StackTraceElement[] st = new Throwable().getStackTrace();
		if ( st.length > 2 )
			logger.fine(st[2].getClassName() + " : " + st[1].getMethodName() + " : " + msg.toString());
		else
			logger.fine(msg.toString());
	}
    }

    public static void finer(Object msg) {
	if ( msg != null ) {
		StackTraceElement[] st = new Throwable().getStackTrace();
		if ( st.length > 2 )
			logger.finer(st[2].getClassName() + " : " + st[1].getMethodName() + " : " + msg.toString());
		else
			logger.finer(msg.toString());
	}
    }
    
    public static void finest(Object msg) {
	if ( msg != null ) {
		StackTraceElement[] st = new Throwable().getStackTrace();
		if ( st.length > 2 )
			logger.finest(st[2].getClassName() + " : " + st[1].getMethodName() + " : " + msg.toString());
		else
			logger.finest(msg.toString());
	}
    }

    public static void config(Object msg) {
	if ( msg != null ) {
		StackTraceElement[] st = new Throwable().getStackTrace();
		if ( st.length > 2 )
			logger.config(st[2].getClassName() + " : " + st[1].getMethodName() + " : " + msg.toString());
		else
			logger.config(msg.toString());
	}
    }
    
    public static void severe(Object msg) {
	if ( msg != null ) {
		StackTraceElement[] st = new Throwable().getStackTrace();
		if ( st.length > 2 )
			logger.severe(st[2].getClassName() + " : " + st[1].getMethodName() + " : " + msg.toString());
		else
			logger.severe(msg.toString());
	}
    }
}
