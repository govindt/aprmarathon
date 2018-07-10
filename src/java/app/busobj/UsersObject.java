/*
 * UsersObject.java
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
 * The implementation of the UsersObject which maps a table
 * in the database
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public class UsersObject implements Cloneable {
    private int users_id;
    private String username;
    private String password;
    private String email;
    private int role_id;
    private String is_valid;
    
    
    /**
    *
     * Returns the String representation of the UsersObject.
     *
     * @return      Returns the String representation of the UsersObject.
     *
     */
    
    public String toString() {
        return "users_id : " + users_id + "\n" +
            "username : " + username + "\n" +
            "password : ******\n" +
            "email : " + email + "\n" +
            "role_Id : " + role_id + "\n" +
            "is_valid : " + is_valid + "\n";
    }
    
    /**
     *
     * Returns the String representation of the UsersObject.
     *
     * @return      Returns the String representation of the UsersObject.
     *
     */
    
    public JSONObject toJSON() {
	JSONObject jo = new JSONObject();
	try {
            jo.put("users_id", users_id);
            jo.put("username", username);
            jo.put("password", password);
            jo.put("email", email);
            jo.put("role_id", role_id);
            jo.put("is_valid", is_valid);
	} catch (JSONException je) {};
	return jo;
    }

    /**
     *
     * Returns the hashCode representation of the UsersObject.
     *
     * @return      Returns the hashCode
     *
     */
    
    public int hashCode() {
	return users_id;
    }

    /**
     * Constructs the UsersObject
     *
     */
    
    public UsersObject () {
        setUsersId(0);
        setUsername("");
        setPassword("");
        setEmail("");
        setRoleId(0);
        setIsValid("");
    }

    /**
     * Constructs the UsersObject from JSONObject
     *
     */
    
    public UsersObject(JSONObject jObject) {
	try {
	    users_id = jObject.getInt("users_id");
	} catch (JSONException je) {}
	try {
	    username = Util.trim(jObject.getString("username"));
	} catch (JSONException je) {}
        try {
	    password = Util.trim(jObject.getString("password"));
	} catch (JSONException je) {}
        try {
	    email = Util.trim(jObject.getString("email"));
	} catch (JSONException je) {}
        try {
	    role_id = jObject.getInt("role_id");
	} catch (JSONException je) {}
        try {
	    is_valid = Util.trim(jObject.getString("is_valid"));
	} catch (JSONException je) {}
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
     * Sets the <code>username</code> field
     *
     * @param username      String
     *
     */
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    
    /**
     *
     * Gets the <code>username</code> field
     *
     * @returns username
     *
     */
    
    public String getUsername() {
        return username;
    }

    
    /**
     *
     * Sets the <code>password</code> field
     *
     * @param password      String
     *
     */
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    
    /**
     *
     * Gets the <code>password</code> field
     *
     * @returns password
     *
     */
    
    public String getPassword() {
        return password;
    }

    
    /**
     *
     * Sets the <code>email</code> field
     *
     * @param email      String
     *
     */
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    
    /**
     *
     * Gets the <code>email</code> field
     *
     * @returns email
     *
     */
    
    public String getEmail() {
        return email;
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
        UsersObject other = (UsersObject)obj;
        DebugHandler.finest("This: " + this);
        DebugHandler.finest("Other: " + other);
        return
            users_id == other.getUsersId() &&
            Util.trim(username).equals(Util.trim(other.getUsername())) &&
            Util.trim(password).equals(Util.trim(other.getPassword())) &&
            Util.trim(email).equals(Util.trim(other.getEmail())) &&
            role_id == other.getRoleId() &&
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
