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
	logger = Logger.getLogger("core.util");
	FileHandler fHandler = new FileHandler(logFile);
	logger.addHandler(fHandler);
    }
    
    public static void debug(Object msg) {
	if ( msg != null )
	    logger.log(Level.FINEST, msg.toString()); 
    }

    public static void log(Level l, Object msg) {
	if ( msg != null )
	    logger.log(l, msg.toString()); 
    }

    public static void info(Object msg) {
	if ( msg != null )
	    logger.info(msg.toString());
    }

    public static void warning(Object msg) {
	if ( msg != null )
	    logger.warning(msg.toString());
    }

    public static void fine(Object msg) {
	if ( msg != null )
	    logger.fine(msg.toString());
    }

    public static void finer(Object msg) {
	if ( msg != null )
	    logger.finer(msg.toString());
    }
    
    public static void finest(Object msg) {
	if ( msg != null )
	    logger.finest(msg.toString());
    }

    public static void config(Object msg) {
	if ( msg != null )
	    logger.config(msg.toString());
    }
    
    public static void severe(Object msg) {
	if ( msg != null )
	    logger.severe(msg.toString());
    }
}
