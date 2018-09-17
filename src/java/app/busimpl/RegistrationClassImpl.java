/*
 * RegistrationClassImpl.java
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
import app.busobj.RegistrationClassObject;
import app.businterface.RegistrationClassInterface;
import app.util.AppConstants;

/**
 * The implementation of the RegistrationClassInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrationClassImpl implements RegistrationClassInterface  {
	private String REGISTRATIONCLASS = "RegistrationClassInterface.getAllRegistrationClass";
	
    /**
	 *
	 * Implementation that returns the RegistrationClassObject given a RegistrationClassObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param registrationclass_obj	RegistrationClassObject
	 *
	 * @return      Returns the ArrayList of RegistrationClassObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public ArrayList<RegistrationClassObject> getRegistrationClass(RegistrationClassObject registrationclass_obj) throws AppException{
		RegistrationClassObject[] registrationClassObjectArr = getAllRegistrationClass();
		@SuppressWarnings("unchecked")
		ArrayList<RegistrationClassObject> v = (ArrayList<RegistrationClassObject>)DBUtil.list(registrationclass_obj,registrationclass_obj);
		DebugHandler.finest("v: " + v);
		return v;
	}
	
    /**
	 *
	 * Implementation of the method that returns the RegistrationClassObject from the underlying datasource.
	 * given registration_class_id.
	 *
	 * @param registration_class_id     int
	 *
	 * @return      Returns the RegistrationClassObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public RegistrationClassObject getRegistrationClas(int registration_class_id) throws AppException{
		RegistrationClassObject[] registrationClassObjectArr = getAllRegistrationClass();
		if ( registrationClassObjectArr == null )
			return null;
		for ( int i = 0; i < registrationClassObjectArr.length; i++ ) {
			if ( registrationClassObjectArr[i] == null ) { // Try database and add to cache if found.
				RegistrationClassObject registrationclassObj = new RegistrationClassObject();
				registrationclassObj.setRegistrationClassId(registration_class_id);
				@SuppressWarnings("unchecked")
				ArrayList<RegistrationClassObject> v = (ArrayList)DBUtil.fetch(registrationclassObj);
				if ( v == null || v.size() == 0 )
					return null;
				else {
					registrationClassObjectArr[i] = (RegistrationClassObject)registrationclassObj.clone();
					Util.putInCache(REGISTRATIONCLASS, registrationClassObjectArr);
				}
			}
			if ( registrationClassObjectArr[i].getRegistrationClassId() == registration_class_id ) {
				DebugHandler.debug("Returning " + registrationClassObjectArr[i]);
				return (RegistrationClassObject)registrationClassObjectArr[i].clone();
			}
		}
		return null;
	}
    
	
    /**
	 *
	 * Implementation that returns all the <code>RegistrationClassObjects</code> from the underlying datasource.
	 *
	 * @return      Returns an Array of <code>RegistrationClassObject</code>
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public RegistrationClassObject[] getAllRegistrationClass() throws AppException{
		RegistrationClassObject registrationClassObject = new RegistrationClassObject();
		RegistrationClassObject[] registrationClassObjectArr = (RegistrationClassObject[])Util.getAppCache().get(REGISTRATIONCLASS);
		if ( registrationClassObjectArr == null ) {
			DebugHandler.info("Getting registrationclass from database");
			@SuppressWarnings("unchecked")
			ArrayList<RegistrationClassObject> v = (ArrayList)DBUtil.list(registrationClassObject);
			DebugHandler.finest(":v: " +  v);
			if ( v == null )
				return null;
			registrationClassObjectArr = new RegistrationClassObject[v.size()];
			for ( int idx = 0; idx < v.size(); idx++ ) {
				registrationClassObjectArr[idx] = v.get(idx);
			}
			Util.putInCache(REGISTRATIONCLASS, registrationClassObjectArr);
		}
		return registrationClassObjectArr;
	}
    
	
    /**
	 *
	 * Implementation to add the <code>RegistrationClassObject</code> to the underlying datasource.
	 *
	 * @param registrationClassObject     RegistrationClassObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer addRegistrationClass(RegistrationClassObject registrationClassObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Registration_Class_seq");
			registrationClassObject.setRegistrationClassId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(registrationClassObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			registrationClassObject.setRegistrationClassId(i.intValue());
			DebugHandler.fine(registrationClassObject);
		}
		RegistrationClassObject buf = new RegistrationClassObject();
		buf.setRegistrationClassId(registrationClassObject.getRegistrationClassId());
		@SuppressWarnings("unchecked")
		ArrayList<RegistrationClassObject> v = (ArrayList)DBUtil.list(registrationClassObject, buf);
		registrationClassObject = v.get(0);
		RegistrationClassObject[] registrationClassObjectArr = getAllRegistrationClass();
		boolean foundSpace = false;

		for ( int idx = 0; idx < registrationClassObjectArr.length; idx++ ) {
			if ( registrationClassObjectArr[idx] == null ) {
				registrationClassObjectArr[idx] = (RegistrationClassObject)registrationClassObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			RegistrationClassObject[] newregistrationClassObjectArr = new RegistrationClassObject[registrationClassObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < registrationClassObjectArr.length; idx++ ) {
				newregistrationClassObjectArr[idx] = (RegistrationClassObject)registrationClassObjectArr[idx].clone();
			}
			newregistrationClassObjectArr[idx] = (RegistrationClassObject)registrationClassObject.clone();
			Util.putInCache(REGISTRATIONCLASS, newregistrationClassObjectArr);
		} else {
			Util.putInCache(REGISTRATIONCLASS, registrationClassObjectArr);
		}
		return i;
	}
	
	
    /**
	 *
	 * Implementation to update the <code>RegistrationClassObject</code> in the underlying datasource.
	 *
	 * @param registrationClassObject     RegistrationClassObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer updateRegistrationClass(RegistrationClassObject registrationClassObject) throws AppException{
		RegistrationClassObject newRegistrationClassObject = getRegistrationClas(registrationClassObject.getRegistrationClassId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(registrationClassObject);
		DebugHandler.fine("i: " +  i);
		RegistrationClassObject[] registrationClassObjectArr = getAllRegistrationClass();
		if ( registrationClassObjectArr == null )
			return null;
		for ( int idx = 0; idx < registrationClassObjectArr.length; idx++ ) {
			if ( registrationClassObjectArr[idx] != null ) {
				if ( registrationClassObjectArr[idx].getRegistrationClassId() == registrationClassObject.getRegistrationClassId() ) {
					DebugHandler.debug("Found RegistrationClass " + registrationClassObject.getRegistrationClassId());
					registrationClassObjectArr[idx] = (RegistrationClassObject)registrationClassObject.clone();
					Util.putInCache(REGISTRATIONCLASS, registrationClassObjectArr);
				}
			}
		}
		return i;
	}
    
	
    /**
	 *
	 * Implementation to delete the <code>RegistrationClassObject</code> in the underlying datasource.
	 *
	 * @param registrationClassObject     RegistrationClassObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer deleteRegistrationClass(RegistrationClassObject registrationClassObject) throws AppException{
	RegistrationClassObject newRegistrationClassObject = getRegistrationClas(registrationClassObject.getRegistrationClassId()); // This call will make sure cache/db are in sync
	RegistrationClassObject[] registrationClassObjectArr = getAllRegistrationClass();
	Integer i = new Integer(0);
	if ( registrationClassObject != null ) {
		i = (Integer)DBUtil.delete(registrationClassObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( registrationClassObjectArr != null ) {
			for (int idx = 0; idx < registrationClassObjectArr.length; idx++ ) {
				if ( registrationClassObjectArr[idx] != null && registrationClassObjectArr[idx].getRegistrationClassId() == registrationClassObject.getRegistrationClassId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (registrationClassObjectArr.length - 1) )
						registrationClassObjectArr[idx] = registrationClassObjectArr[idx + 1]; // Move the array
					else
						registrationClassObjectArr[idx] = null;
				}
				if ( registrationClassObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(REGISTRATIONCLASS, registrationClassObjectArr);
		}
		return i;
	}
}
