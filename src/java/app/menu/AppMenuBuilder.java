/*
 *
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.menu;

import java.util.*;
import core.util.DebugHandler;
import core.util.Constants;
import core.util.AppException;
import core.util.Util;
import app.businterface.*;
import app.busimpl.*;
import app.busobj.*;
import app.util.AppConstants;

public class AppMenuBuilder implements MenuBuilder
{
    private int coord=0;
    private int usersId = 0;
    public static int TOP_PARENT_ID = -1;
    private ArrayList<CompositeMenu> topMenus = new ArrayList<CompositeMenu>();

    public AppMenuBuilder(int usersId)
    {
	this.usersId = usersId;
    }

    public String renderMenu() throws MenuException
    {
	String str = "<ul id=\"nav\" class=\"dropdown dropdown-horizontal\">\n" + 
	    buildTopMenu() 
	    + "\n</ul>\n";

	return str;
    }

    private String buildTopMenu() throws MenuException {
	UsersInterface uif = new UsersImpl();
	if ( usersId != 0 ) {
	    try {
		UsersObject usersObj = uif.getUser(usersId);
		DebugHandler.debug("Logged UsersObj " + usersObj);
		usersId = usersObj.getUsersId();
		MenuInterface mif = new MenuImpl();
		MenuObject mObj = new MenuObject();
		mObj.setRoleId(usersObj.getRoleId());
		DebugHandler.debug("Menu Object " + mObj);
		Vector<MenuObject> v = mif.getMenus(mObj);
		DebugHandler.debug("Menu Vector " + v);
		for ( int i = 0; i < v.size(); i++) {
		    mObj = v.elementAt(i);
		    if ( mObj.getParentMenuId() == TOP_PARENT_ID ) {
			CompositeMenu aTopMenu = new CompositeMenu(Constants.EMPTY + mObj.getMenuId(), mObj.getMenuName(), mObj.getUrl());
			topMenus.add(aTopMenu);
		    }
		}
		CompositeMenu logoutMenu = new CompositeMenu(Constants.EMPTY + -2, AppConstants.LOGOUT_STR, Util.getBaseurl() + Constants.URL_SEPARATOR + Constants.JSP_STR + Constants.EQUALS_STR + AppConstants.LOGOUT_JSP_STR);
		topMenus.add(logoutMenu);
	    } catch (AppException ae) {
		DebugHandler.debug("AppException getting menus");
	    }
	}
        StringBuffer sb = new StringBuffer();
        int j=1;
        for (int i=0;i<topMenus.size();i++)
        {
	    CompositeMenu menu = topMenus.get(i);
	    menu.setLevelCoord(Integer.toString(j));
	    j++;
	    buildMenu(menu.getMenuId(), menu);
	    sb.append(menu.render());
	}
        DebugHandler.debug(sb.toString());
        return sb.toString();
    }

    private boolean isLeaf(String menuId)throws MenuException
    {
	MenuObject mObj = new MenuObject();
        DebugHandler.debug("MenuId " + menuId);
        boolean isLeaf = false;
	MenuInterface mIf = new MenuImpl();
	mObj.setParentMenuId(Integer.parseInt(menuId));
	try {
	    Vector<MenuObject> v = mIf.getMenus(mObj);
	    if ( v != null && v.size() == 0 )
		isLeaf = true;
	} catch (AppException ae) {
	    DebugHandler.debug("AppException from menu while checking isLeaf");
	}
	return isLeaf;
    }

    private void buildMenu(String menuId, CompositeMenu comSrc) throws MenuException
    {
        DebugHandler.debug(menuId);
	MenuInterface mIf = new MenuImpl();
	MenuObject mObj = new MenuObject();
	mObj.setParentMenuId(Integer.parseInt(menuId));
	try {
	    Vector<MenuObject> v = mIf.getMenus(mObj);
	    for (int i = 0; i < v.size(); i++ ) { 
		mObj = v.elementAt(i);
		String childMenuId = mObj.getMenuId() + Constants.EMPTY;
		String menuName = mObj.getMenuName();
		String href = mObj.getUrl();
		String parentId = mObj.getParentMenuId() + Constants.EMPTY;
		DebugHandler.debug(childMenuId + " " + menuName + " " + parentId + " " + href);
		if (isLeaf(childMenuId)) { //simple menu 
		    SimpleMenu sm = new SimpleMenu(childMenuId , menuName, href);
		    comSrc.add(sm);
		}
		else {
		    DebugHandler.debug("inside submenu" + childMenuId);
		    CompositeMenu aParentMenu = new CompositeMenu(childMenuId, menuName);
		    comSrc.add(aParentMenu);
		    buildMenu(childMenuId, aParentMenu);
		}
	    }
	} catch (AppException ae) {
	    DebugHandler.debug("AppException in buildMenu");
	}
    }



    public static void main(String argv[]) throws Exception
    {
        AppMenuBuilder builder = new AppMenuBuilder(1);
        builder.renderMenu();
    }

}
