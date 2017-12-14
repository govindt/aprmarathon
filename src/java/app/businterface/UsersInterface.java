/*
 * UsersInterface.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.UsersObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public interface UsersInterface {
    
    /**
     *
     * Interface that returns the UsersObject given Users_id from the underlying datasource.
     *
     * @param Users_id     int
     *
     * @return      Returns the UsersObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public UsersObject getUser(int Users_id) throws AppException;

     /**
     *
     * Interface that returns the UsersObject given user_id from the underlying datasource.
     *
     * @param user_name	 String
     *
     * @return 	 Returns the UsersObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public UsersObject getUser(String user_name) throws AppException;

     /**
     *
     * Interface that returns the UsersObject given user_id from the underlying datasource.
     *
     * @param email	 String
     *
     * @return 	 Returns the Vector of UsersObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Vector<UsersObject> getUserFromEmail(String email) throws AppException;

    /**
     *
     * Interface that returns the UsersObject given user_name and password from the underlying datasource.
     *
     * @param user_name	 String
     * @param password	 String
     *
     * @return 	 Returns the UsersObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public UsersObject authenticate(String user_name, String password) throws AppException;
    
    /**
     *
     * Interface that returns all the <code>UsersObject</code> from the underlying datasource.
     *
     * @return      Returns a Vector of <code>UsersObject</code>
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public UsersObject[] getAllUsers() throws AppException;
    
    /**
     *
     * Interface to add the <code>UsersObject</code> to the underlying datasource.
     *
     * @param usersObject     UsersObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer addUsers(UsersObject usersObject) throws AppException;
    
    /**
     *
     * Interface to update the <code>UsersObject</code> in the underlying datasource.
     *
     * @param usersObject     UsersObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer updateUsers(UsersObject usersObject) throws AppException;
    
    /**
     *
     * Interface to delete the <code>UsersObject</code> in the underlying datasource.
     *
     * @param usersObject     UsersObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer deleteUsers(UsersObject usersObject) throws AppException;
}
