/*
 * AclImpl.java
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
import app.busobj.AclObject;
import app.businterface.AclInterface;
import app.util.AppConstants;

/**
 * The implementation of the AclInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class AclImpl implements AclInterface  {
    private String ACL = "AclInterface.getAllAcl";
    
    /**
     *
     * Implementation that returns the AclObject given a AclObject filled with values that will be used for query from the underlying datasource.
     *
     * @param acl_obj	AclObject
     *
     * @return      Returns the Vector of AclObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Vector<AclObject> getAcls(AclObject acl_obj) throws AppException{
	AclObject[] aclObjectArr = getAllAcls();
	Vector<AclObject> v = new Vector<AclObject>();
	if ( aclObjectArr == null )
	    return null;
	for ( int i = 0; i < aclObjectArr.length; i++ ) {
	    if ( aclObjectArr[i] != null ) {
		if ( acl_obj.getAclId() == Constants.GET_ALL ) {
		    v.addElement((AclObject)aclObjectArr[i].clone());
		} else {
			if ( (acl_obj.getAclId() != 0 && acl_obj.getAclId() == aclObjectArr[i].getAclId())
 || (acl_obj.getAclPage() != null && acl_obj.getAclPage().equals(aclObjectArr[i].getAclPage()))
 || (acl_obj.getIsValid() != null && acl_obj.getIsValid().equals(aclObjectArr[i].getIsValid()))
 || (acl_obj.getRoleId() != 0 && acl_obj.getRoleId() == aclObjectArr[i].getRoleId())
 || (acl_obj.getUsersId() != 0 && acl_obj.getUsersId() == aclObjectArr[i].getUsersId())
 || (acl_obj.getPermission() != 0 && acl_obj.getPermission() == aclObjectArr[i].getPermission())
) {
			    v.addElement((AclObject)aclObjectArr[i].clone());
			}
		}
	    }
	}
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the AclObject from the underlying datasource.
     * given acl_id.
     *
     * @param acl_id     int
     *
     * @return      Returns the AclObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public AclObject getAcl(int acl_id) throws AppException{
	AclObject[] aclObjectArr = getAllAcls();
	if ( aclObjectArr == null )
	    return null;
	for ( int i = 0; i < aclObjectArr.length; i++ ) {
	    if ( aclObjectArr[i] == null ) { // Try database and add to cache if found.
		AclObject aclObj = new AclObject();
		aclObj.setAclId(acl_id);
		@SuppressWarnings("unchecked")
		Vector<AclObject> v = (Vector)DBUtil.fetch(aclObj);
		if ( v == null || v.size() == 0 )
		    return null;
		else {
		    aclObjectArr[i] = (AclObject)aclObj.clone();
		    Util.putInCache(ACL, aclObjectArr);
		}
	    }
	    if ( aclObjectArr[i].getAclId() == acl_id ) {
		DebugHandler.debug("Returning " + aclObjectArr[i]);
		return (AclObject)aclObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>AclObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>AclObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public AclObject[] getAllAcls() throws AppException{
	AclObject aclObject = new AclObject();
	AclObject[] aclObjectArr = (AclObject[])Util.getAppCache().get(ACL);
	if ( aclObjectArr == null ) {
	    DebugHandler.info("Getting acl from database");
	    @SuppressWarnings("unchecked")
	    Vector<AclObject> v = (Vector)DBUtil.list(aclObject);
	    DebugHandler.finest(":v: " +  v);
	    if ( v == null )
		return null;
	    aclObjectArr = new AclObject[v.size()];
	    for ( int idx = 0; idx < v.size(); idx++ ) {
		aclObjectArr[idx] = v.elementAt(idx);
	    }
	    Util.putInCache(ACL, aclObjectArr);
	}
	return aclObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>AclObject</code> to the underlying datasource.
     *
     * @param aclObject     AclObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addAcl(AclObject aclObject) throws AppException{
	if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
	    long l = DBUtil.getNextId("Acl_seq");
	    aclObject.setAclId((int)l);
	}
	Integer i = (Integer)DBUtil.insert(aclObject);
	DebugHandler.fine("i: " +  i);
	AclObject buf = new AclObject();
	buf.setAclPage(aclObject.getAclPage());
	@SuppressWarnings("unchecked")
	Vector<AclObject> v = (Vector)DBUtil.list(aclObject, buf);
	if ( v != null && v.size() > 0 )
	    aclObject = v.elementAt(0);
	AclObject[] aclObjectArr = getAllAcls();
	boolean foundSpace = false;

	for ( int idx = 0; idx < aclObjectArr.length; idx++ ) {
	    if ( aclObjectArr[idx] == null ) {
		aclObjectArr[idx] = (AclObject)aclObject.clone();
		foundSpace = true;
		break;
	    }
	}
	if ( foundSpace == false ) {
	    AclObject[] newaclObjectArr = new AclObject[aclObjectArr.length + 1];
	    int idx = 0;
	    for ( idx = 0; idx < aclObjectArr.length; idx++ ) {
		newaclObjectArr[idx] = (AclObject)aclObjectArr[idx].clone();
	    }
	    newaclObjectArr[idx] = (AclObject)aclObject.clone();
	    Util.putInCache(ACL, newaclObjectArr);
	} else {
	    Util.putInCache(ACL, aclObjectArr);
	}
	return i;
    }
    
    
    /**
     *
     * Implementation to update the <code>AclObject</code> in the underlying datasource.
     *
     * @param aclObject     AclObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer updateAcl(AclObject aclObject) throws AppException{
	AclObject newAclObject = getAcl(aclObject.getAclId()); // This call will make sure cache/db are in sync
	Integer i = (Integer)DBUtil.update(aclObject);
	DebugHandler.fine("i: " +  i);
	AclObject[] aclObjectArr = getAllAcls();
	if ( aclObjectArr == null )
	    return null;
	for ( int idx = 0; idx < aclObjectArr.length; idx++ ) {
	    if ( aclObjectArr[idx] != null ) {
		if ( aclObjectArr[idx].getAclId() == aclObject.getAclId() ) {
		    DebugHandler.debug("Found Acl " + aclObject.getAclId());
		    aclObjectArr[idx] = (AclObject)aclObject.clone();
		    Util.putInCache(ACL, aclObjectArr);
		}
	    }
	}
	return i;
    }
    
    
    /**
     *
     * Implementation to delete the <code>AclObject</code> in the underlying datasource.
     *
     * @param aclObject     AclObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteAcl(AclObject aclObject) throws AppException{
	AclObject newAclObject = getAcl(aclObject.getAclId()); // This call will make sure cache/db are in sync
	AclObject[] aclObjectArr = getAllAcls();
	Integer i = new Integer(0);
	if ( aclObject != null ) {
	    i = (Integer)DBUtil.delete(aclObject);
	    DebugHandler.fine("i: " +  i);
	    boolean found = false;
	    if ( aclObjectArr != null ) {
		for (int idx = 0; idx < aclObjectArr.length; idx++ ) {
		    if ( aclObjectArr[idx] != null && aclObjectArr[idx].getAclId() == aclObject.getAclId() ) {
			found = true;
		    }
		    if ( found ) {
			if ( idx != (aclObjectArr.length - 1) )
				aclObjectArr[idx] = aclObjectArr[idx + 1]; // Move the array
			else
				aclObjectArr[idx] = null;
		    }
		    if ( aclObjectArr[idx] == null )
			break;
		}
	    }
	    if ( found )
		Util.putInCache(ACL, aclObjectArr);
	}
	return i;
    }
}
