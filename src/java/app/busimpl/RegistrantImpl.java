/*
 * RegistrantImpl.java
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
import app.busobj.RegistrantObject;
import app.businterface.RegistrantInterface;
import app.util.AppConstants;

/**
 * The implementation of the RegistrantInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrantImpl implements RegistrantInterface  {
    private String REGISTRANT = "RegistrantInterface.getAllRegistrant";
    
    /**
     *
     * Implementation that returns the RegistrantObject given a RegistrantObject filled with values that will be used for query from the underlying datasource.
     *
     * @param registrant_obj	RegistrantObject
     *
     * @return      Returns the ArrayList of RegistrantObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<RegistrantObject> getRegistrants(RegistrantObject registrant_obj) throws AppException{
	RegistrantObject[] registrantObjectArr = getAllRegistrants();
	ArrayList<RegistrantObject> v = new ArrayList<RegistrantObject>();
	if ( registrantObjectArr == null )
		return null;
	for ( int i = 0; i < registrantObjectArr.length; i++ ) {
		if ( registrantObjectArr[i] != null ) {
			if ( registrant_obj.getRegistrantId() == Constants.GET_ALL ) {
				v.add((RegistrantObject)registrantObjectArr[i].clone());
			} else {
				if ( (registrant_obj.getRegistrantId() != 0 && registrant_obj.getRegistrantId() == registrantObjectArr[i].getRegistrantId())
 || (registrant_obj.getRegistrantName() != null && registrant_obj.getRegistrantName().equals(registrantObjectArr[i].getRegistrantName()))
 || (registrant_obj.getRegistrantMiddleName() != null && registrant_obj.getRegistrantMiddleName().equals(registrantObjectArr[i].getRegistrantMiddleName()))
 || (registrant_obj.getRegistrantLastName() != null && registrant_obj.getRegistrantLastName().equals(registrantObjectArr[i].getRegistrantLastName()))
 || (registrant_obj.getRegistrantEmail() != null && registrant_obj.getRegistrantEmail().equals(registrantObjectArr[i].getRegistrantEmail()))
 || (registrant_obj.getRegistrantAdditionalEmail() != null && registrant_obj.getRegistrantAdditionalEmail().equals(registrantObjectArr[i].getRegistrantAdditionalEmail()))
 || (registrant_obj.getRegistrantPhoneNumber() != null && registrant_obj.getRegistrantPhoneNumber().equals(registrantObjectArr[i].getRegistrantPhoneNumber()))
 || (registrant_obj.getRegistrantAddress() != null && registrant_obj.getRegistrantAddress().equals(registrantObjectArr[i].getRegistrantAddress()))
 || (registrant_obj.getRegistrantCity() != null && registrant_obj.getRegistrantCity().equals(registrantObjectArr[i].getRegistrantCity()))
 || (registrant_obj.getRegistrantState() != null && registrant_obj.getRegistrantState().equals(registrantObjectArr[i].getRegistrantState()))
 || (registrant_obj.getRegistrantPincode() != null && registrant_obj.getRegistrantPincode().equals(registrantObjectArr[i].getRegistrantPincode()))
 || (registrant_obj.getRegistrantPan() != null && registrant_obj.getRegistrantPan().equals(registrantObjectArr[i].getRegistrantPan()))
) {
					v.add((RegistrantObject)registrantObjectArr[i].clone());
				}
			}
		}
	}
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the RegistrantObject from the underlying datasource.
     * given registrant_id.
     *
     * @param registrant_id     int
     *
     * @return      Returns the RegistrantObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public RegistrantObject getRegistrant(int registrant_id) throws AppException{
	RegistrantObject[] registrantObjectArr = getAllRegistrants();
	if ( registrantObjectArr == null )
	    return null;
	for ( int i = 0; i < registrantObjectArr.length; i++ ) {
	    if ( registrantObjectArr[i] == null ) { // Try database and add to cache if found.
		RegistrantObject registrantObj = new RegistrantObject();
		registrantObj.setRegistrantId(registrant_id);
		@SuppressWarnings("unchecked")
		ArrayList<RegistrantObject> v = (ArrayList)DBUtil.fetch(registrantObj);
		if ( v == null || v.size() == 0 )
		    return null;
		else {
		    registrantObjectArr[i] = (RegistrantObject)registrantObj.clone();
		    Util.putInCache(REGISTRANT, registrantObjectArr);
		}
	    }
	    if ( registrantObjectArr[i].getRegistrantId() == registrant_id ) {
		DebugHandler.debug("Returning " + registrantObjectArr[i]);
		return (RegistrantObject)registrantObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>RegistrantObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>RegistrantObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public RegistrantObject[] getAllRegistrants() throws AppException{
	RegistrantObject registrantObject = new RegistrantObject();
	RegistrantObject[] registrantObjectArr = (RegistrantObject[])Util.getAppCache().get(REGISTRANT);
	if ( registrantObjectArr == null ) {
	    DebugHandler.info("Getting registrant from database");
	    @SuppressWarnings("unchecked")
	    ArrayList<RegistrantObject> v = (ArrayList)DBUtil.list(registrantObject);
	    DebugHandler.finest(":v: " +  v);
	    if ( v == null )
		return null;
	    registrantObjectArr = new RegistrantObject[v.size()];
	    for ( int idx = 0; idx < v.size(); idx++ ) {
		registrantObjectArr[idx] = v.get(idx);
	    }
	    Util.putInCache(REGISTRANT, registrantObjectArr);
	}
	return registrantObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>RegistrantObject</code> to the underlying datasource.
     *
     * @param registrantObject     RegistrantObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addRegistrant(RegistrantObject registrantObject) throws AppException{
	if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
		long l = DBUtil.getNextId("Registrant_seq");
		registrantObject.setRegistrantId((int)l);
	}
	Integer i = (Integer)DBUtil.insert(registrantObject);
	DebugHandler.fine("i: " +  i);
	RegistrantObject buf = new RegistrantObject();
	buf.setRegistrantName(registrantObject.getRegistrantName());
	@SuppressWarnings("unchecked")
	ArrayList<RegistrantObject> v = (ArrayList)DBUtil.list(registrantObject, buf);
		registrantObject = v.get(0);
	RegistrantObject[] registrantObjectArr = getAllRegistrants();
	boolean foundSpace = false;

	for ( int idx = 0; idx < registrantObjectArr.length; idx++ ) {
	    if ( registrantObjectArr[idx] == null ) {
		registrantObjectArr[idx] = (RegistrantObject)registrantObject.clone();
		foundSpace = true;
		break;
	    }
	}
	if ( foundSpace == false ) {
	    RegistrantObject[] newregistrantObjectArr = new RegistrantObject[registrantObjectArr.length + 1];
	    int idx = 0;
	    for ( idx = 0; idx < registrantObjectArr.length; idx++ ) {
		newregistrantObjectArr[idx] = (RegistrantObject)registrantObjectArr[idx].clone();
	    }
	    newregistrantObjectArr[idx] = (RegistrantObject)registrantObject.clone();
	    Util.putInCache(REGISTRANT, newregistrantObjectArr);
	} else {
	    Util.putInCache(REGISTRANT, registrantObjectArr);
	}
	return i;
    }
    
    
    /**
     *
     * Implementation to update the <code>RegistrantObject</code> in the underlying datasource.
     *
     * @param registrantObject     RegistrantObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer updateRegistrant(RegistrantObject registrantObject) throws AppException{
	RegistrantObject newRegistrantObject = getRegistrant(registrantObject.getRegistrantId()); // This call will make sure cache/db are in sync
	Integer i = (Integer)DBUtil.update(registrantObject);
	DebugHandler.fine("i: " +  i);
	RegistrantObject[] registrantObjectArr = getAllRegistrants();
	if ( registrantObjectArr == null )
	    return null;
	for ( int idx = 0; idx < registrantObjectArr.length; idx++ ) {
	    if ( registrantObjectArr[idx] != null ) {
		if ( registrantObjectArr[idx].getRegistrantId() == registrantObject.getRegistrantId() ) {
		    DebugHandler.debug("Found Registrant " + registrantObject.getRegistrantId());
		    registrantObjectArr[idx] = (RegistrantObject)registrantObject.clone();
		    Util.putInCache(REGISTRANT, registrantObjectArr);
		}
	    }
	}
	return i;
    }
    
    
    /**
     *
     * Implementation to delete the <code>RegistrantObject</code> in the underlying datasource.
     *
     * @param registrantObject     RegistrantObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteRegistrant(RegistrantObject registrantObject) throws AppException{
	RegistrantObject newRegistrantObject = getRegistrant(registrantObject.getRegistrantId()); // This call will make sure cache/db are in sync
	RegistrantObject[] registrantObjectArr = getAllRegistrants();
	Integer i = new Integer(0);
	if ( registrantObject != null ) {
		i = (Integer)DBUtil.delete(registrantObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( registrantObjectArr != null ) {
			for (int idx = 0; idx < registrantObjectArr.length; idx++ ) {
				if ( registrantObjectArr[idx] != null && registrantObjectArr[idx].getRegistrantId() == registrantObject.getRegistrantId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (registrantObjectArr.length - 1) )
						registrantObjectArr[idx] = registrantObjectArr[idx + 1]; // Move the array
					else
						registrantObjectArr[idx] = null;
				}
				if ( registrantObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(REGISTRANT, registrantObjectArr);
		}
		return i;
	}
}
