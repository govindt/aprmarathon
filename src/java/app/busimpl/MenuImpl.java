/*
 * MenuImpl.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.busimpl;

import java.lang.*;
import java.util.*;
import core.util.Constants;
import core.util.DebugHandler;
import core.db.DBUtil;
import core.util.AppException;
import core.util.Util;
import app.busobj.MenuObject;
import app.businterface.MenuInterface;
import app.util.AppConstants;

/**
 * The implementation of the MenuInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class MenuImpl implements MenuInterface  {
    private String MENU = "MenuInterface.getAllMenu";
    
    /**
     *
     * Implementation that returns the MenuObject given a MenuObject filled with values that will be used for query from the underlying datasource.
     *
     * @param menu_obj	MenuObject
     *
     * @return      Returns the ArrayList of MenuObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<MenuObject> getMenus(MenuObject menu_obj) throws AppException{
		MenuObject[] menuObjectArr = getAllMenus();
		if ( menu_obj.getMenuId() == Constants.GET_ALL ) {
			if ( menuObjectArr == null )
				return null;
			ArrayList<MenuObject> v = new ArrayList<MenuObject>();
			for ( int i = 0; i < menuObjectArr.length; i++ ) {
				v.add((MenuObject)menuObjectArr[i].clone());
			}
			return v;
		}
		else {
			@SuppressWarnings("unchecked")
			ArrayList<MenuObject> v = (ArrayList<MenuObject>)DBUtil.list(menu_obj,menu_obj);
			DebugHandler.finest("v: " + v);
			return v;
		}
    }
    
    /**
     *
     * Implementation of the method that returns the MenuObject from the underlying datasource.
     * given menu_id.
     *
     * @param menu_id     int
     *
     * @return      Returns the MenuObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public MenuObject getMenu(int menu_id) throws AppException{
	MenuObject[] menuObjectArr = getAllMenus();
	if ( menuObjectArr == null )
	    return null;
	for ( int i = 0; i < menuObjectArr.length; i++ ) {
	    if ( menuObjectArr[i] == null ) { // Try database and add to cache if found.
		MenuObject menuObj = new MenuObject();
		menuObj.setMenuId(menu_id);
		@SuppressWarnings("unchecked")
		ArrayList<MenuObject> v = (ArrayList)DBUtil.fetch(menuObj);
		if ( v == null || v.size() == 0 )
		    return null;
		else {
		    menuObjectArr[i] = (MenuObject)menuObj.clone();
		    Util.putInCache(MENU, menuObjectArr);
		}
	    }
	    if ( menuObjectArr[i].getMenuId() == menu_id ) {
		DebugHandler.debug("Returning " + menuObjectArr[i]);
		return (MenuObject)menuObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>MenuObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>MenuObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public MenuObject[] getAllMenus() throws AppException{
	MenuObject menuObject = new MenuObject();
	MenuObject[] menuObjectArr = (MenuObject[])Util.getAppCache().get(MENU);
	if ( menuObjectArr == null ) {
	    DebugHandler.info("Getting menu from database");
	    @SuppressWarnings("unchecked")
	    ArrayList<MenuObject> v = (ArrayList)DBUtil.list(menuObject);
	    DebugHandler.finest(":v: " +  v);
	    if ( v == null )
		return null;
	    menuObjectArr = new MenuObject[v.size()];
	    for ( int idx = 0; idx < v.size(); idx++ ) {
		menuObjectArr[idx] = v.get(idx);
	    }
	    Util.putInCache(MENU, menuObjectArr);
	}
	return menuObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>MenuObject</code> to the underlying datasource.
     *
     * @param menuObject     MenuObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addMenu(MenuObject menuObject) throws AppException{
	if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
	    long l = DBUtil.getNextId("Menu_seq");
	    menuObject.setMenuId((int)l);
	}
	Integer i = (Integer)DBUtil.insert(menuObject);
	DebugHandler.fine("i: " +  i);
	MenuObject buf = new MenuObject();
	buf.setMenuName(menuObject.getMenuName());
	@SuppressWarnings("unchecked")
	ArrayList<MenuObject> v = (ArrayList)DBUtil.list(menuObject, buf);
	if ( v != null && v.size() > 0 )
	    menuObject = v.get(0);
	MenuObject[] menuObjectArr = getAllMenus();
	boolean foundSpace = false;

	for ( int idx = 0; idx < menuObjectArr.length; idx++ ) {
	    if ( menuObjectArr[idx] == null ) {
		menuObjectArr[idx] = (MenuObject)menuObject.clone();
		foundSpace = true;
		break;
	    }
	}
	if ( foundSpace == false ) {
	    MenuObject[] newmenuObjectArr = new MenuObject[menuObjectArr.length + 1];
	    int idx = 0;
	    for ( idx = 0; idx < menuObjectArr.length; idx++ ) {
		newmenuObjectArr[idx] = (MenuObject)menuObjectArr[idx].clone();
	    }
	    newmenuObjectArr[idx] = (MenuObject)menuObject.clone();
	    Util.putInCache(MENU, newmenuObjectArr);
	} else {
	    Util.putInCache(MENU, menuObjectArr);
	}
	return i;
    }
    
    
    /**
     *
     * Implementation to update the <code>MenuObject</code> in the underlying datasource.
     *
     * @param menuObject     MenuObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer updateMenu(MenuObject menuObject) throws AppException{
	MenuObject newMenuObject = getMenu(menuObject.getMenuId()); // This call will make sure cache/db are in sync
	Integer i = (Integer)DBUtil.update(menuObject);
	DebugHandler.fine("i: " +  i);
	MenuObject[] menuObjectArr = getAllMenus();
	if ( menuObjectArr == null )
	    return null;
	for ( int idx = 0; idx < menuObjectArr.length; idx++ ) {
	    if ( menuObjectArr[idx] != null ) {
		if ( menuObjectArr[idx].getMenuId() == menuObject.getMenuId() ) {
		    DebugHandler.debug("Found Menu " + menuObject.getMenuId());
		    menuObjectArr[idx] = (MenuObject)menuObject.clone();
		    Util.putInCache(MENU, menuObjectArr);
		}
	    }
	}
	return i;
    }
    
    
    /**
     *
     * Implementation to delete the <code>MenuObject</code> in the underlying datasource.
     *
     * @param menuObject     MenuObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteMenu(MenuObject menuObject) throws AppException{
	MenuObject newMenuObject = getMenu(menuObject.getMenuId()); // This call will make sure cache/db are in sync
	MenuObject[] menuObjectArr = getAllMenus();
	Integer i = new Integer(0);
	if ( menuObject != null ) {
	    i = (Integer)DBUtil.delete(menuObject);
	    DebugHandler.fine("i: " +  i);
	    boolean found = false;
	    if ( menuObjectArr != null ) {
		for (int idx = 0; idx < menuObjectArr.length; idx++ ) {
		    if ( menuObjectArr[idx] != null && menuObjectArr[idx].getMenuId() == menuObject.getMenuId() ) {
			found = true;
		    }
		    if ( found ) {
			if ( idx != (menuObjectArr.length - 1) )
				menuObjectArr[idx] = menuObjectArr[idx + 1]; // Move the array
			else
				menuObjectArr[idx] = null;
		    }
		    if ( menuObjectArr[idx] == null )
			break;
		}
	    }
	    if ( found )
		Util.putInCache(MENU, menuObjectArr);
	}
	return i;
    }
}
