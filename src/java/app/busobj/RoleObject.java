/*
 * RoleObject.java
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
 * The implementation of the RoleObject which maps a table
 * in the database
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public class RoleObject implements Cloneable {
    private int role_id;
    private String role_name;
    private String is_valid;
    
    
    /**
     *
     * Returns the String representation of the RoleObject.
     *
     * @return      Returns the String representation of the RoleObject.
     *
     */
    
    public String toString() {
        return "role_id : " + role_id + "\n" +
            "role_name : " + role_name + "\n" +
            "is_valid : " + is_valid + "\n";
    }
    
    
    /**
     * Constructs the RoleObject
     *
     */
    
    public RoleObject () {
        setRoleId(0);
        setRoleName("");
        setIsValid("");
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
     * Sets the <code>role_name</code> field
     *
     * @param role_name      String
     *
     */
    
    public void setRoleName(String role_name) {
        this.role_name = role_name;
    }
    
    
    /**
     *
     * Gets the <code>role_name</code> field
     *
     * @returns role_name
     *
     */
    
    public String getRoleName() {
        return role_name;
    }

    
    /**
     *
     * Sets the <code>is_valid</code> field
     *
     * @param is_valid      String
     *
     */
    
    public void setIsValid(String is_valid) {
        this.is_valid = is_valid;
    }
    
    
    /**
     *
     * Gets the <code>is_valid</code> field
     *
     * @returns is_valid
     *
     */
    
    public String getIsValid() {
        return is_valid;
    }

    
    /**
     *
     * Tests if this object equals <code>obj</code>
     *
     * @returns true if equals
     *
     */
    
    public boolean equals(Object obj) {
        RoleObject other = (RoleObject)obj;
        DebugHandler.finest("This: " + this);
        DebugHandler.finest("Other: " + other);
        return
            role_id == other.getRoleId() &&
            Util.trim(role_name).equals(Util.trim(other.getRoleName())) &&
            Util.trim(is_valid).equals(Util.trim(other.getIsValid()));
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
