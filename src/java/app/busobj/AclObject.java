/*
 * AclObject.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.busobj;

import java.util.Date;
import core.util.DebugHandler;
import core.util.Util;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;

/**
 * The implementation of the AclObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class AclObject implements Cloneable {
    private int acl_id;
    private String acl_page;
    private String is_valid;
    private int role_id;
    private int users_id;
    private int permission;
    
    
    /**
     *
     * Returns the String representation of the AclObject.
     *
     * @return      Returns the String representation of the AclObject.
     *
     */
    
    public String toString() {
        return "acl_id : " + acl_id + "\n" +
            "acl_page : " + acl_page + "\n" +
            "is_valid : " + is_valid + "\n" +
            "role_id : " + role_id + "\n" +
            "users_id : " + users_id + "\n" +
            "permission : " + permission + "\n";
    }

    /**
     *
     * Returns the JSONObject representation of the AclObject.
     *
     * @return      Returns the JSON representation of the AclObject.
     *
     */
    
    public JSONObject toJSON() {
	JSONObject jo = new JSONObject();
	try {
            jo.put("acl_id", acl_id);
            jo.put("acl_page", acl_page);
            jo.put("is_valid", is_valid);
            jo.put("role_id", role_id);
            jo.put("users_id", users_id);
            jo.put("permission", permission);
	} catch (JSONException je) {};
	return jo;
    }
    
     /**
     *
     * Returns the hashCode representation of the AclObject.
     *
     * @return      Returns the hashCode
     *
     */
    
    public int hashCode() {
	return acl_id;
    }
    
    /**
     * Constructs the AclObject
     *
     */
    
    public AclObject () {
        setAclId(0);
        setAclPage("");
        setIsValid("");
        setRoleId(0);
        setUsersId(0);
        setPermission(0);
    }

    /**
     * Constructs the AclObject from JSONObject
     *
     */
    
    public AclObject (JSONObject jObject) {
	try {
	    acl_id = jObject.getInt("acl_id");
	} catch (JSONException je) {}
        try {
	    acl_page = Util.trim(jObject.getString("acl_page"));
	} catch (JSONException je) {}
	try {
	    is_valid = Util.trim(jObject.getString("is_valid"));
	} catch (JSONException je) {}
        try {
	    role_id = jObject.getInt("role_id");
	} catch (JSONException je) {}
        try {
	    users_id = jObject.getInt("users_id");
	} catch (JSONException je) {}
        try {
	    permission = jObject.getInt("permission");
	} catch (JSONException je) {}
    }
    
    
    /**
     *
     * Sets the <code>acl_id</code> field
     *
     * @param acl_id      int
     *
     */
    
    public void setAclId(int acl_id) {
        this.acl_id = acl_id;
    }
    
    
    /**
     *
     * Gets the <code>acl_id</code> field
     *
     * @returns acl_id
     *
     */
    
    public int getAclId() {
        return acl_id;
    }

    
    /**
     *
     * Sets the <code>acl_page</code> field
     *
     * @param acl_page      String
     *
     */
    
    public void setAclPage(String acl_page) {
        this.acl_page = acl_page;
    }
    
    
    /**
     *
     * Gets the <code>acl_page</code> field
     *
     * @returns acl_page
     *
     */
    
    public String getAclPage() {
        return acl_page;
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
     * Sets the <code>users_id</code> field
     *
     * @param users_id      int
     *
     */
    
    public void setUsersId(int users_id) {
        this.users_id = users_id;
    }
    
    
    /**
     *
     * Gets the <code>users_id</code> field
     *
     * @returns users_id
     *
     */
    
    public int getUsersId() {
        return users_id;
    }

    
    /**
     *
     * Sets the <code>permission</code> field
     *
     * @param permission      int
     *
     */
    
    public void setPermission(int permission) {
        this.permission = permission;
    }
    
    
    /**
     *
     * Gets the <code>permission</code> field
     *
     * @returns permission
     *
     */
    
    public int getPermission() {
        return permission;
    }

    
    /**
     *
     * Tests if this object equals <code>obj</code>
     *
     * @returns true if equals
     *
     */
    
    public boolean equals(Object obj) {
        AclObject other = (AclObject)obj;
        DebugHandler.finest("This: " + this);
        DebugHandler.finest("Other: " + other);
        return
            acl_id == other.getAclId() &&
            Util.trim(acl_page).equals(Util.trim(other.getAclPage())) &&
            Util.trim(is_valid).equals(Util.trim(other.getIsValid())) &&
            role_id == other.getRoleId() &&
            users_id == other.getUsersId() &&
            permission == other.getPermission();
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
