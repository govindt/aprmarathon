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


/**
 * The implementation of the UsersObject which maps a table
 * in the database
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public class UsersObject implements Cloneable {
    private int Users_id;
    private String Username;
    private String Password;
    private String Email;
    private int Role_Id;
    private String Is_Valid;
    
    
    /**
     *
     * Returns the String representation of the UsersObject.
     *
     * @return      Returns the String representation of the UsersObject.
     *
     */
    
    public String toString() {
        return "Users_id : " + Users_id + "\n" +
            "Username : " + Username + "\n" +
            "Password : ******\n" +
            "Email : " + Email + "\n" +
            "Role_Id : " + Role_Id + "\n" +
            "Is_Valid : " + Is_Valid + "\n";
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
     *
     * Sets the <code>Users_id</code> field
     *
     * @param Users_id      int
     *
     */
    
    public void setUsersId(int Users_id) {
        this.Users_id = Users_id;
    }
    
    
    /**
     *
     * Gets the <code>Users_id</code> field
     *
     * @returns Users_id
     *
     */
    
    public int getUsersId() {
        return Users_id;
    }

    
    /**
     *
     * Sets the <code>Username</code> field
     *
     * @param Username      String
     *
     */
    
    public void setUsername(String Username) {
        this.Username = Username;
    }
    
    
    /**
     *
     * Gets the <code>Username</code> field
     *
     * @returns Username
     *
     */
    
    public String getUsername() {
        return Username;
    }

    
    /**
     *
     * Sets the <code>Password</code> field
     *
     * @param Password      String
     *
     */
    
    public void setPassword(String Password) {
        this.Password = Password;
    }
    
    
    /**
     *
     * Gets the <code>Password</code> field
     *
     * @returns Password
     *
     */
    
    public String getPassword() {
        return Password;
    }

    
    /**
     *
     * Sets the <code>Email</code> field
     *
     * @param Email      String
     *
     */
    
    public void setEmail(String Email) {
        this.Email = Email;
    }
    
    
    /**
     *
     * Gets the <code>Email</code> field
     *
     * @returns Email
     *
     */
    
    public String getEmail() {
        return Email;
    }

    
    /**
     *
     * Sets the <code>Role_Id</code> field
     *
     * @param Role_Id      int
     *
     */
    
    public void setRoleId(int Role_Id) {
        this.Role_Id = Role_Id;
    }
    
    
    /**
     *
     * Gets the <code>Role_Id</code> field
     *
     * @returns Role_Id
     *
     */
    
    public int getRoleId() {
        return Role_Id;
    }

    
    /**
     *
     * Sets the <code>Is_Valid</code> field
     *
     * @param Is_Valid      String
     *
     */
    
    public void setIsValid(String Is_Valid) {
        this.Is_Valid = Is_Valid;
    }
    
    
    /**
     *
     * Gets the <code>Is_Valid</code> field
     *
     * @returns Is_Valid
     *
     */
    
    public String getIsValid() {
        return Is_Valid;
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
            Users_id == other.getUsersId() &&
            Util.trim(Username).equals(Util.trim(other.getUsername())) &&
            Util.trim(Password).equals(Util.trim(other.getPassword())) &&
            Util.trim(Email).equals(Util.trim(other.getEmail())) &&
            Role_Id == other.getRoleId() &&
            Util.trim(Is_Valid).equals(Util.trim(other.getIsValid()));
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
