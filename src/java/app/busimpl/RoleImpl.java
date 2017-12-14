/*
 * RoleImpl.java
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
import app.busobj.RoleObject;
import app.businterface.RoleInterface;
import app.util.AppConstants;

/**
 * The implementation of the RoleInterface
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public class RoleImpl implements RoleInterface  {
    private String ROLE = "RoleInterface.getAllRole";
    
    /**
     *
     * Implementation that returns the RoleObject given a RoleObject filled with values that will be used for query from the underlying datasource.
     *
     * @param role_obj	RoleObject
     *
     * @return      Returns the Vector of RoleObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Vector<RoleObject> getRoles(RoleObject role_obj) throws AppException{
	RoleObject[] roleObjectArr = getAllRoles();
	Vector<RoleObject> v = new Vector<RoleObject>();
	if ( roleObjectArr == null )
	    return null;
	for ( int i = 0; i < roleObjectArr.length; i++ ) {
	    if ( roleObjectArr[i] != null ) {
		if ( (role_obj.getRoleId() != 0 && role_obj.getRoleId() == roleObjectArr[i].getRoleId())
 || (role_obj.getRoleName() != null && role_obj.getRoleName().equals(roleObjectArr[i].getRoleName()))
 || (role_obj.getIsValid() != null && role_obj.getIsValid().equals(roleObjectArr[i].getIsValid()))
) {
		    v.addElement((RoleObject)roleObjectArr[i].clone());
		}
	    }
	}
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the RoleObject from the underlying datasource.
     * given role_id.
     *
     * @param role_id     int
     *
     * @return      Returns the RoleObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public RoleObject getRole(int role_id) throws AppException{
	RoleObject[] roleObjectArr = getAllRoles();
	if ( roleObjectArr == null )
	    return null;
	for ( int i = 0; i < roleObjectArr.length; i++ ) {
	    if ( roleObjectArr[i] == null ) { // Try database and add to cache if found.
		RoleObject roleObj = new RoleObject();
		roleObj.setRoleId(role_id);
		@SuppressWarnings("unchecked")
		Vector<RoleObject> v = (Vector)DBUtil.fetch(roleObj);
		if ( v == null || v.size() == 0 )
		    return null;
		else {
		    roleObjectArr[i] = (RoleObject)roleObj.clone();
		    Util.putInCache(ROLE, roleObjectArr);
		}
	    }
	    if ( roleObjectArr[i].getRoleId() == role_id ) {
		DebugHandler.debug("Returning " + roleObjectArr[i]);
		return (RoleObject)roleObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>RoleObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>RoleObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public RoleObject[] getAllRoles() throws AppException{
	RoleObject roleObject = new RoleObject();
	RoleObject[] roleObjectArr = (RoleObject[])Util.getAppCache().get(ROLE);
	if ( roleObjectArr == null ) {
	    DebugHandler.info("Getting role from database");
	    @SuppressWarnings("unchecked")
	    Vector<RoleObject> v = (Vector)DBUtil.list(roleObject);
	    DebugHandler.finest(":v: " +  v);
	    if ( v == null )
		return null;
	    roleObjectArr = new RoleObject[v.size()];
	    for ( int idx = 0; idx < v.size(); idx++ ) {
		roleObjectArr[idx] = v.elementAt(idx);
	    }
	    Util.putInCache(ROLE, roleObjectArr);
	}
	return roleObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>RoleObject</code> to the underlying datasource.
     *
     * @param roleObject     RoleObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addRole(RoleObject roleObject) throws AppException{
	if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
	    long l = DBUtil.getNextId("Role_seq");
	    roleObject.setRoleId((int)l);
	}
	Integer i = (Integer)DBUtil.insert(roleObject);
	DebugHandler.fine("i: " +  i);
	RoleObject buf = new RoleObject();
	buf.setRoleName(roleObject.getRoleName());
	@SuppressWarnings("unchecked")
	Vector<RoleObject> v = (Vector)DBUtil.list(roleObject, buf);
	if ( v != null && v.size() > 0 )
	    roleObject = v.elementAt(0);
	RoleObject[] roleObjectArr = getAllRoles();
	boolean foundSpace = false;

	for ( int idx = 0; idx < roleObjectArr.length; idx++ ) {
	    if ( roleObjectArr[idx] == null ) {
		roleObjectArr[idx] = (RoleObject)roleObject.clone();
		foundSpace = true;
		break;
	    }
	}
	if ( foundSpace == false ) {
	    RoleObject[] newroleObjectArr = new RoleObject[roleObjectArr.length + 1];
	    int idx = 0;
	    for ( idx = 0; idx < roleObjectArr.length; idx++ ) {
		newroleObjectArr[idx] = (RoleObject)roleObjectArr[idx].clone();
	    }
	    newroleObjectArr[idx] = (RoleObject)roleObject.clone();
	    Util.putInCache(ROLE, newroleObjectArr);
	} else {
	    Util.putInCache(ROLE, roleObjectArr);
	}
	return i;
    }
    
    
    /**
     *
     * Implementation to update the <code>RoleObject</code> in the underlying datasource.
     *
     * @param roleObject     RoleObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer updateRole(RoleObject roleObject) throws AppException{
	RoleObject newRoleObject = getRole(roleObject.getRoleId()); // This call will make sure cache/db are in sync
	Integer i = (Integer)DBUtil.update(roleObject);
	DebugHandler.fine("i: " +  i);
	RoleObject[] roleObjectArr = getAllRoles();
	if ( roleObjectArr == null )
	    return null;
	for ( int idx = 0; idx < roleObjectArr.length; idx++ ) {
	    if ( roleObjectArr[idx] != null ) {
		if ( roleObjectArr[idx].getRoleId() == roleObject.getRoleId() ) {
		    DebugHandler.debug("Found Role " + roleObject.getRoleId());
		    roleObjectArr[idx] = (RoleObject)roleObject.clone();
		    Util.putInCache(ROLE, roleObjectArr);
		}
	    }
	}
	return i;
    }
    
    
    /**
     *
     * Implementation to delete the <code>RoleObject</code> in the underlying datasource.
     *
     * @param roleObject     RoleObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteRole(RoleObject roleObject) throws AppException{
	RoleObject newRoleObject = getRole(roleObject.getRoleId()); // This call will make sure cache/db are in sync
	RoleObject[] roleObjectArr = getAllRoles();
	Integer i = new Integer(0);
	if ( roleObject != null ) {
	    i = (Integer)DBUtil.delete(roleObject);
	    DebugHandler.fine("i: " +  i);
	    boolean found = false;
	    if ( roleObjectArr != null ) {
		for (int idx = 0; idx < roleObjectArr.length; idx++ ) {
		    if ( roleObjectArr[idx] != null && roleObjectArr[idx].getRoleId() == roleObject.getRoleId() ) {
			found = true;
		    }
		    if ( found ) {
			roleObjectArr[idx] = roleObjectArr[idx + 1]; // Move the array
		    }
		    if ( roleObjectArr[idx] == null )
			break;
		}
	    }
	    if ( found )
		Util.putInCache(ROLE, roleObjectArr);
	}
	return i;
    }
}
