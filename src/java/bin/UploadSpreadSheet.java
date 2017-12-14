/*
 *  @(#)UploradSpreadSheet.java	1.4 04/05/28 
 *
 *  Project Name Project
 *
 *  Author: Govind Thirumalai
 *
 */

import java.lang.*;
import java.util.*;
import app.busobj.*;
import app.busimpl.*;
import app.businterface.*;
import app.util.App;

import core.util.*;
import java.net.*;

public class UploadSpreadSheet {
    public static void main(String argv[]) throws AppException {
	App.getInstance();
	SpreadSheetInterface ssIf = null;
	Object implParamsObject = null;
	if ( argv.length < 1 ) {
	    DebugHandler.severe("Usage UploadSpreadSheet <Bean name>");
	    System.exit(1);
	}
	String xls_file = System.getProperty("XLS_FILES");
	if ( xls_file == null ) {
	    DebugHandler.severe("XLS_FILES property not passed");
	    System.exit(1);
	}
	if ( argv[0] != null ) {
	    try {
		@SuppressWarnings("rawtypes")
		Class implDefinition = Class.forName(argv[0]);
		Object implObject = implDefinition.newInstance();
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
		DebugHandler.fine("Reading from file.." +  xls_file);
		ssIf.readFromFile(xls_file, null);
	    }
	} catch (AppException ae) {
	    DebugHandler.severe("Exception while creating the file");
	}
    }
}
