/*
 * RegistrationTypeImpl.java
 *
 * APR Marathon Registration App Project
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
import app.busobj.RegistrationTypeObject;
import app.businterface.RegistrationTypeInterface;
import app.util.AppConstants;

/**
 * The implementation of the RegistrationTypeInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrationTypeImpl implements RegistrationTypeInterface  {
    private String REGISTRATIONTYPE = "RegistrationTypeInterface.getAllRegistrationType";
    
    /**
     *
     * Implementation that returns the RegistrationTypeObject given a RegistrationTypeObject filled with values that will be used for query from the underlying datasource.
     *
     * @param registrationtype_obj	RegistrationTypeObject
     *
     * @return      Returns the ArrayList of RegistrationTypeObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
	public ArrayList<RegistrationTypeObject> getRegistrationTypes(RegistrationTypeObject registrationtype_obj) throws AppException{
		@SuppressWarnings("unchecked")
		ArrayList<RegistrationTypeObject> v = (ArrayList<RegistrationTypeObject>)DBUtil.list(registrationtype_obj,registrationtype_obj);
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the RegistrationTypeObject from the underlying datasource.
     * given registration_type_id.
     *
     * @param registration_type_id     int
     *
     * @return      Returns the RegistrationTypeObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public RegistrationTypeObject getRegistrationType(int registration_type_id) throws AppException{
	RegistrationTypeObject[] registrationTypeObjectArr = getAllRegistrationTypes();
	if ( registrationTypeObjectArr == null )
	    return null;
	for ( int i = 0; i < registrationTypeObjectArr.length; i++ ) {
	    if ( registrationTypeObjectArr[i] == null ) { // Try database and add to cache if found.
		    RegistrationTypeObject registrationtypeObj = new RegistrationTypeObject();
		    registrationtypeObj.setRegistrationTypeId(registration_type_id);
		    @SuppressWarnings("unchecked")
		    ArrayList<RegistrationTypeObject> v = (ArrayList)DBUtil.fetch(registrationtypeObj);
		    if ( v == null || v.size() == 0 )
			    return null;
		    else {
			    registrationTypeObjectArr[i] = (RegistrationTypeObject)registrationtypeObj.clone();
			    Util.putInCache(REGISTRATIONTYPE, registrationTypeObjectArr);
		    }
	    }
	    if ( registrationTypeObjectArr[i].getRegistrationTypeId() == registration_type_id ) {
		    DebugHandler.debug("Returning " + registrationTypeObjectArr[i]);
		    return (RegistrationTypeObject)registrationTypeObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>RegistrationTypeObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>RegistrationTypeObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public RegistrationTypeObject[] getAllRegistrationTypes() throws AppException{
		RegistrationTypeObject registrationTypeObject = new RegistrationTypeObject();
		RegistrationTypeObject[] registrationTypeObjectArr = (RegistrationTypeObject[])Util.getAppCache().get(REGISTRATIONTYPE);
		if ( registrationTypeObjectArr == null ) {
		    DebugHandler.info("Getting registrationtype from database");
		    @SuppressWarnings("unchecked")
		    ArrayList<RegistrationTypeObject> v = (ArrayList)DBUtil.list(registrationTypeObject);
		    DebugHandler.finest(":v: " +  v);
		    if ( v == null )
			    return null;
		    registrationTypeObjectArr = new RegistrationTypeObject[v.size()];
		    for ( int idx = 0; idx < v.size(); idx++ ) {
			    registrationTypeObjectArr[idx] = v.get(idx);
		    }
		    Util.putInCache(REGISTRATIONTYPE, registrationTypeObjectArr);
		}
		return registrationTypeObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>RegistrationTypeObject</code> to the underlying datasource.
     *
     * @param registrationTypeObject     RegistrationTypeObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addRegistrationType(RegistrationTypeObject registrationTypeObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Registration_Type_seq");
			registrationTypeObject.setRegistrationTypeId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(registrationTypeObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			registrationTypeObject.setRegistrationTypeId(i.intValue());
			DebugHandler.fine(registrationTypeObject);
		}
		RegistrationTypeObject buf = new RegistrationTypeObject();
		buf.setRegistrationTypeId(registrationTypeObject.getRegistrationTypeId());
		@SuppressWarnings("unchecked")
		ArrayList<RegistrationTypeObject> v = (ArrayList)DBUtil.list(registrationTypeObject, buf);
		registrationTypeObject = v.get(0);
		RegistrationTypeObject[] registrationTypeObjectArr = getAllRegistrationTypes();
		boolean foundSpace = false;

		for ( int idx = 0; idx < registrationTypeObjectArr.length; idx++ ) {
			if ( registrationTypeObjectArr[idx] == null ) {
				registrationTypeObjectArr[idx] = (RegistrationTypeObject)registrationTypeObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			RegistrationTypeObject[] newregistrationTypeObjectArr = new RegistrationTypeObject[registrationTypeObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < registrationTypeObjectArr.length; idx++ ) {
				newregistrationTypeObjectArr[idx] = (RegistrationTypeObject)registrationTypeObjectArr[idx].clone();
			}
			newregistrationTypeObjectArr[idx] = (RegistrationTypeObject)registrationTypeObject.clone();
			Util.putInCache(REGISTRATIONTYPE, newregistrationTypeObjectArr);
		} else {
			Util.putInCache(REGISTRATIONTYPE, registrationTypeObjectArr);
		}
		return i;
	}
	
    
    /**
     *
     * Implementation to update the <code>RegistrationTypeObject</code> in the underlying datasource.
     *
     * @param registrationTypeObject     RegistrationTypeObject
     *
     * @throws AppException if the operation fails
     *
     */
    
	public Integer updateRegistrationType(RegistrationTypeObject registrationTypeObject) throws AppException{
		RegistrationTypeObject newRegistrationTypeObject = getRegistrationType(registrationTypeObject.getRegistrationTypeId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(registrationTypeObject);
		DebugHandler.fine("i: " +  i);
		RegistrationTypeObject[] registrationTypeObjectArr = getAllRegistrationTypes();
		if ( registrationTypeObjectArr == null )
			return null;
		for ( int idx = 0; idx < registrationTypeObjectArr.length; idx++ ) {
			if ( registrationTypeObjectArr[idx] != null ) {
				if ( registrationTypeObjectArr[idx].getRegistrationTypeId() == registrationTypeObject.getRegistrationTypeId() ) {
					DebugHandler.debug("Found RegistrationType " + registrationTypeObject.getRegistrationTypeId());
					registrationTypeObjectArr[idx] = (RegistrationTypeObject)registrationTypeObject.clone();
					Util.putInCache(REGISTRATIONTYPE, registrationTypeObjectArr);
				}
			}
		}
		return i;
	}
    
    
    /**
     *
     * Implementation to delete the <code>RegistrationTypeObject</code> in the underlying datasource.
     *
     * @param registrationTypeObject     RegistrationTypeObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteRegistrationType(RegistrationTypeObject registrationTypeObject) throws AppException{
	RegistrationTypeObject newRegistrationTypeObject = getRegistrationType(registrationTypeObject.getRegistrationTypeId()); // This call will make sure cache/db are in sync
	RegistrationTypeObject[] registrationTypeObjectArr = getAllRegistrationTypes();
	Integer i = new Integer(0);
	if ( registrationTypeObject != null ) {
		i = (Integer)DBUtil.delete(registrationTypeObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( registrationTypeObjectArr != null ) {
			for (int idx = 0; idx < registrationTypeObjectArr.length; idx++ ) {
				if ( registrationTypeObjectArr[idx] != null && registrationTypeObjectArr[idx].getRegistrationTypeId() == registrationTypeObject.getRegistrationTypeId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (registrationTypeObjectArr.length - 1) )
						registrationTypeObjectArr[idx] = registrationTypeObjectArr[idx + 1]; // Move the array
					else
						registrationTypeObjectArr[idx] = null;
				}
				if ( registrationTypeObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(REGISTRATIONTYPE, registrationTypeObjectArr);
		}
		return i;
	}
}
