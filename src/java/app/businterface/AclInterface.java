/*
 * AclInterface.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.AclObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface AclInterface {
    
    /**
     *
     * Interface that returns the AclObject given a AclObject filled with values that will be used for query from the underlying datasource.
     *
     * @param acl_obj	AclObject
     *
     * @return      Returns the Vector of AclObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Vector<AclObject> getAcls(AclObject acl_obj) throws AppException;
    
    /**
     *
     * Interface that returns the AclObject given acl_id from the underlying datasource.
     *
     * @param acl_id     int
     *
     * @return      Returns the AclObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public AclObject getAcl(int acl_id) throws AppException;
    
    /**
     *
     * Interface that returns all the <code>AclObject</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>AclObject</code>
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public AclObject[] getAllAcls() throws AppException;
    
    /**
     *
     * Interface to add the <code>AclObject</code> to the underlying datasource.
     *
     * @param aclObject     AclObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer addAcl(AclObject aclObject) throws AppException;
    
    /**
     *
     * Interface to update the <code>AclObject</code> in the underlying datasource.
     *
     * @param aclObject     AclObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer updateAcl(AclObject aclObject) throws AppException;
    
    /**
     *
     * Interface to delete the <code>AclObject</code> in the underlying datasource.
     *
     * @param aclObject     AclObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer deleteAcl(AclObject aclObject) throws AppException;
}
