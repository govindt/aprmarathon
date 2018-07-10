/*
 * MenuInterface.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.MenuObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface MenuInterface {
    
    /**
     *
     * Interface that returns the MenuObject given a MenuObject filled with values that will be used for query from the underlying datasource.
     *
     * @param menu_obj	MenuObject
     *
     * @return      Returns the ArrayList of MenuObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<MenuObject> getMenus(MenuObject menu_obj) throws AppException;
    
    /**
     *
     * Interface that returns the MenuObject given menu_id from the underlying datasource.
     *
     * @param menu_id     int
     *
     * @return      Returns the MenuObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public MenuObject getMenu(int menu_id) throws AppException;
    
    /**
     *
     * Interface that returns all the <code>MenuObject</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>MenuObject</code>
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public MenuObject[] getAllMenus() throws AppException;
    
    /**
     *
     * Interface to add the <code>MenuObject</code> to the underlying datasource.
     *
     * @param menuObject     MenuObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer addMenu(MenuObject menuObject) throws AppException;
    
    /**
     *
     * Interface to update the <code>MenuObject</code> in the underlying datasource.
     *
     * @param menuObject     MenuObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer updateMenu(MenuObject menuObject) throws AppException;
    
    /**
     *
     * Interface to delete the <code>MenuObject</code> in the underlying datasource.
     *
     * @param menuObject     MenuObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer deleteMenu(MenuObject menuObject) throws AppException;
}
