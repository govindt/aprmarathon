/*
 *  @(#)AddUser.java	1.4 04/05/28 
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
import core.util.*;
import app.util.App;
import java.net.*;

public class AddUser {
    public static void main(String argv[]) throws AppException {
	App.getInstance();
	UsersInterface uif = new UsersImpl();
	UsersObject uobj = new UsersObject();
	if ( argv.length == 2 ) {
	    uobj = uif.getUser(argv[0]);
	    if ( uobj == null ) {
		uobj = new UsersObject();
		uobj.setUsername(argv[0]);
		uobj.setPassword(argv[1]);
		uobj.setRoleId(1);
		DebugHandler.fine("Adding User " + argv[0]);
		uif.addUsers(uobj);
	    } else {
		DebugHandler.fine("User " + argv[0] + " is already present");
	    }
	}
	else if (argv.length == 3) {
	    uobj = uif.authenticate(argv[0], argv[1]);
	    if ( uobj != null ) {
		System.out.println("User " + argv[0] + " is valid user");
	    }
	    else {
		System.out.println("Username/Password for " + argv[0] + " is invalid.");
	    }
	} else {
	    System.out.println("USAGE: java AddUser <username> <password>");
	    System.out.println("\t- Adds user <username> <password>");
	    System.out.println("USAGE: java AddUser <username> <password> check");
	    System.out.println("\t- checks user <username> <password>");
	}
    }
}
