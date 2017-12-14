/*
 * %W% %E%
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */


import java.lang.*;
import java.util.*;
import core.db.*;
import core.util.*;
import core.busobj.*;
import app.util.App;
import app.busobj.*;
import app.businterface.*;
import app.busimpl.*;


public class DbTest {
    public static void main(String args[]) throws core.util.AppException {
	App.getInstance();
	UsersInterface uIf = new UsersImpl();
	UsersObject[] usersObj;
	long start = System.currentTimeMillis();
	usersObj = uIf.getAllUsers();
	DebugHandler.info(usersObj.length);
	long end = System.currentTimeMillis();
    }
}
