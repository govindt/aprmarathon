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
import app.menu.*;
import core.util.*;
import app.util.App;
import java.net.*;

public class CssMenu {
    public static void main(String argv[]) throws AppException, MenuException {
	App.getInstance();
	int usersId = Integer.parseInt(argv[0]);
	AppMenuBuilder amb = new AppMenuBuilder(usersId);
	System.out.println(amb.renderMenu());
    }
}
