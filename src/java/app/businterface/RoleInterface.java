/*
 * RoleInterface.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.RoleObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public interface RoleInterface {
    
    /**
     *
     * Interface that returns the RoleObject given a RoleObject filled with values that will be used for query from the underlying datasource.
     *
     * @param role_obj	RoleObject
     *
     * @return      Returns the Vector of RoleObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Vector<RoleObject> getRoles(RoleObject role_obj) throws AppException;
    
    /**
     *
     * Interface that returns the RoleObject given role_id from the underlying datasource.
     *
     * @param role_id     int
     *
     * @return      Returns the RoleObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public RoleObject getRole(int role_id) throws AppException;
    
    /**
     *
     * Interface that returns all the <code>RoleObject</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>RoleObject</code>
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public RoleObject[] getAllRoles() throws AppException;
    
    /**
     *
     * Interface to add the <code>RoleObject</code> to the underlying datasource.
     *
     * @param roleObject     RoleObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer addRole(RoleObject roleObject) throws AppException;
    
    /**
     *
     * Interface to update the <code>RoleObject</code> in the underlying datasource.
     *
     * @param roleObject     RoleObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer updateRole(RoleObject roleObject) throws AppException;
    
    /**
     *
     * Interface to delete the <code>RoleObject</code> in the underlying datasource.
     *
     * @param roleObject     RoleObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer deleteRole(RoleObject roleObject) throws AppException;
}
