/*
 * MenuObject.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.busobj;

import java.util.Date;
import core.util.DebugHandler;
import core.util.Util;


/**
 * The implementation of the MenuObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class MenuObject implements Cloneable {
    private int menu_id;
    private String menu_name;
    private String url;
    private int menu_order;
    private int parent_menu_id;
    private int role_id;
    
    
    /**
     *
     * Returns the String representation of the MenuObject.
     *
     * @return      Returns the String representation of the MenuObject.
     *
     */
    
    public String toString() {
        return "menu_id : " + menu_id + "\n" +
            "menu_name : " + menu_name + "\n" +
            "url : " + url + "\n" +
            "menu_order : " + menu_order + "\n" +
            "parent_menu_id : " + parent_menu_id + "\n" +
            "role_id : " + role_id + "\n";
    }
    
    
    /**
     * Constructs the MenuObject
     *
     */
    
    public MenuObject () {
        setMenuId(0);
        setMenuName("");
        setUrl("");
        setMenuOrder(0);
        setParentMenuId(0);
        setRoleId(0);
    }
    
    
    /**
     *
     * Sets the <code>menu_id</code> field
     *
     * @param menu_id      int
     *
     */
    
    public void setMenuId(int menu_id) {
        this.menu_id = menu_id;
    }
    
    
    /**
     *
     * Gets the <code>menu_id</code> field
     *
     * @returns menu_id
     *
     */
    
    public int getMenuId() {
        return menu_id;
    }

    
    /**
     *
     * Sets the <code>menu_name</code> field
     *
     * @param menu_name      String
     *
     */
    
    public void setMenuName(String menu_name) {
        this.menu_name = menu_name;
    }
    
    
    /**
     *
     * Gets the <code>menu_name</code> field
     *
     * @returns menu_name
     *
     */
    
    public String getMenuName() {
        return menu_name;
    }

    
    /**
     *
     * Sets the <code>url</code> field
     *
     * @param url      String
     *
     */
    
    public void setUrl(String url) {
        this.url = url;
    }
    
    
    /**
     *
     * Gets the <code>url</code> field
     *
     * @returns url
     *
     */
    
    public String getUrl() {
        return url;
    }

    
    /**
     *
     * Sets the <code>menu_order</code> field
     *
     * @param menu_order      int
     *
     */
    
    public void setMenuOrder(int menu_order) {
        this.menu_order = menu_order;
    }
    
    
    /**
     *
     * Gets the <code>menu_order</code> field
     *
     * @returns menu_order
     *
     */
    
    public int getMenuOrder() {
        return menu_order;
    }

    
    /**
     *
     * Sets the <code>parent_menu_id</code> field
     *
     * @param parent_menu_id      int
     *
     */
    
    public void setParentMenuId(int parent_menu_id) {
        this.parent_menu_id = parent_menu_id;
    }
    
    
    /**
     *
     * Gets the <code>parent_menu_id</code> field
     *
     * @returns parent_menu_id
     *
     */
    
    public int getParentMenuId() {
        return parent_menu_id;
    }

    
    /**
     *
     * Sets the <code>role_id</code> field
     *
     * @param role_id      int
     *
     */
    
    public void setRoleId(int role_id) {
        this.role_id = role_id;
    }
    
    
    /**
     *
     * Gets the <code>role_id</code> field
     *
     * @returns role_id
     *
     */
    
    public int getRoleId() {
        return role_id;
    }

    
    /**
     *
     * Tests if this object equals <code>obj</code>
     *
     * @returns true if equals
     *
     */
    
    public boolean equals(Object obj) {
        MenuObject other = (MenuObject)obj;
        DebugHandler.finest("This: " + this);
        DebugHandler.finest("Other: " + other);
        return
            menu_id == other.getMenuId() &&
            Util.trim(menu_name).equals(Util.trim(other.getMenuName())) &&
            Util.trim(url).equals(Util.trim(other.getUrl())) &&
            menu_order == other.getMenuOrder() &&
            parent_menu_id == other.getParentMenuId() &&
            role_id == other.getRoleId();
    }
    
    /**
     *
     * Clones this object
     *
     * @returns the clone of this object
     *
     */
    
    public Object clone() {
        Object theClone = null;
        try {
            theClone = super.clone();
        } catch (CloneNotSupportedException ce) {
            DebugHandler.severe("Cannot clone " + this);
        }
        return theClone;
    }
}
