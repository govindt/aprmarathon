/*
 * RegistrationSourceImpl.java
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
import app.busobj.RegistrationSourceObject;
import app.businterface.RegistrationSourceInterface;
import app.util.AppConstants;

/**
 * The implementation of the RegistrationSourceInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrationSourceImpl implements RegistrationSourceInterface  {
	private String REGISTRATIONSOURCE = "RegistrationSourceInterface.getAllRegistrationSource";
	
    /**
	 *
	 * Implementation that returns the RegistrationSourceObject given a RegistrationSourceObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param registrationsource_obj	RegistrationSourceObject
	 *
	 * @return      Returns the ArrayList of RegistrationSourceObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public ArrayList<RegistrationSourceObject> getRegistrationSources(RegistrationSourceObject registrationsource_obj) throws AppException{
		RegistrationSourceObject[] registrationSourceObjectArr = getAllRegistrationSources();
		@SuppressWarnings("unchecked")
		ArrayList<RegistrationSourceObject> v = (ArrayList<RegistrationSourceObject>)DBUtil.list(registrationsource_obj,registrationsource_obj);
		DebugHandler.finest("v: " + v);
		return v;
	}
	
    /**
	 *
	 * Implementation of the method that returns the RegistrationSourceObject from the underlying datasource.
	 * given registration_source_id.
	 *
	 * @param registration_source_id     int
	 *
	 * @return      Returns the RegistrationSourceObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public RegistrationSourceObject getRegistrationSource(int registration_source_id) throws AppException{
		RegistrationSourceObject[] registrationSourceObjectArr = getAllRegistrationSources();
		if ( registrationSourceObjectArr == null )
			return null;
		for ( int i = 0; i < registrationSourceObjectArr.length; i++ ) {
			if ( registrationSourceObjectArr[i] == null ) { // Try database and add to cache if found.
				RegistrationSourceObject registrationsourceObj = new RegistrationSourceObject();
				registrationsourceObj.setRegistrationSourceId(registration_source_id);
				@SuppressWarnings("unchecked")
				ArrayList<RegistrationSourceObject> v = (ArrayList)DBUtil.fetch(registrationsourceObj);
				if ( v == null || v.size() == 0 )
					return null;
				else {
					registrationSourceObjectArr[i] = (RegistrationSourceObject)registrationsourceObj.clone();
					Util.putInCache(REGISTRATIONSOURCE, registrationSourceObjectArr);
				}
			}
			if ( registrationSourceObjectArr[i].getRegistrationSourceId() == registration_source_id ) {
				DebugHandler.debug("Returning " + registrationSourceObjectArr[i]);
				return (RegistrationSourceObject)registrationSourceObjectArr[i].clone();
			}
		}
		return null;
	}
    
	
    /**
	 *
	 * Implementation that returns all the <code>RegistrationSourceObjects</code> from the underlying datasource.
	 *
	 * @return      Returns an Array of <code>RegistrationSourceObject</code>
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public RegistrationSourceObject[] getAllRegistrationSources() throws AppException{
		RegistrationSourceObject registrationSourceObject = new RegistrationSourceObject();
		RegistrationSourceObject[] registrationSourceObjectArr = (RegistrationSourceObject[])Util.getAppCache().get(REGISTRATIONSOURCE);
		if ( registrationSourceObjectArr == null ) {
			DebugHandler.info("Getting registrationsource from database");
			@SuppressWarnings("unchecked")
			ArrayList<RegistrationSourceObject> v = (ArrayList)DBUtil.list(registrationSourceObject);
			DebugHandler.finest(":v: " +  v);
			if ( v == null )
				return null;
			registrationSourceObjectArr = new RegistrationSourceObject[v.size()];
			for ( int idx = 0; idx < v.size(); idx++ ) {
				registrationSourceObjectArr[idx] = v.get(idx);
			}
			Util.putInCache(REGISTRATIONSOURCE, registrationSourceObjectArr);
		}
		return registrationSourceObjectArr;
	}
    
	
    /**
	 *
	 * Implementation to add the <code>RegistrationSourceObject</code> to the underlying datasource.
	 *
	 * @param registrationSourceObject     RegistrationSourceObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer addRegistrationSource(RegistrationSourceObject registrationSourceObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Registration_Source_seq");
			registrationSourceObject.setRegistrationSourceId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(registrationSourceObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			registrationSourceObject.setRegistrationSourceId(i.intValue());
			DebugHandler.fine(registrationSourceObject);
		}
		RegistrationSourceObject buf = new RegistrationSourceObject();
		buf.setRegistrationSourceId(registrationSourceObject.getRegistrationSourceId());
		@SuppressWarnings("unchecked")
		ArrayList<RegistrationSourceObject> v = (ArrayList)DBUtil.list(registrationSourceObject, buf);
		registrationSourceObject = v.get(0);
		RegistrationSourceObject[] registrationSourceObjectArr = getAllRegistrationSources();
		boolean foundSpace = false;

		for ( int idx = 0; idx < registrationSourceObjectArr.length; idx++ ) {
			if ( registrationSourceObjectArr[idx] == null ) {
				registrationSourceObjectArr[idx] = (RegistrationSourceObject)registrationSourceObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			RegistrationSourceObject[] newregistrationSourceObjectArr = new RegistrationSourceObject[registrationSourceObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < registrationSourceObjectArr.length; idx++ ) {
				newregistrationSourceObjectArr[idx] = (RegistrationSourceObject)registrationSourceObjectArr[idx].clone();
			}
			newregistrationSourceObjectArr[idx] = (RegistrationSourceObject)registrationSourceObject.clone();
			Util.putInCache(REGISTRATIONSOURCE, newregistrationSourceObjectArr);
		} else {
			Util.putInCache(REGISTRATIONSOURCE, registrationSourceObjectArr);
		}
		return i;
	}
	
	
    /**
	 *
	 * Implementation to update the <code>RegistrationSourceObject</code> in the underlying datasource.
	 *
	 * @param registrationSourceObject     RegistrationSourceObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer updateRegistrationSource(RegistrationSourceObject registrationSourceObject) throws AppException{
		RegistrationSourceObject newRegistrationSourceObject = getRegistrationSource(registrationSourceObject.getRegistrationSourceId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(registrationSourceObject);
		DebugHandler.fine("i: " +  i);
		RegistrationSourceObject[] registrationSourceObjectArr = getAllRegistrationSources();
		if ( registrationSourceObjectArr == null )
			return null;
		for ( int idx = 0; idx < registrationSourceObjectArr.length; idx++ ) {
			if ( registrationSourceObjectArr[idx] != null ) {
				if ( registrationSourceObjectArr[idx].getRegistrationSourceId() == registrationSourceObject.getRegistrationSourceId() ) {
					DebugHandler.debug("Found RegistrationSource " + registrationSourceObject.getRegistrationSourceId());
					registrationSourceObjectArr[idx] = (RegistrationSourceObject)registrationSourceObject.clone();
					Util.putInCache(REGISTRATIONSOURCE, registrationSourceObjectArr);
				}
			}
		}
		return i;
	}
    
	
    /**
	 *
	 * Implementation to delete the <code>RegistrationSourceObject</code> in the underlying datasource.
	 *
	 * @param registrationSourceObject     RegistrationSourceObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer deleteRegistrationSource(RegistrationSourceObject registrationSourceObject) throws AppException{
	RegistrationSourceObject newRegistrationSourceObject = getRegistrationSource(registrationSourceObject.getRegistrationSourceId()); // This call will make sure cache/db are in sync
	RegistrationSourceObject[] registrationSourceObjectArr = getAllRegistrationSources();
	Integer i = new Integer(0);
	if ( registrationSourceObject != null ) {
		i = (Integer)DBUtil.delete(registrationSourceObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( registrationSourceObjectArr != null ) {
			for (int idx = 0; idx < registrationSourceObjectArr.length; idx++ ) {
				if ( registrationSourceObjectArr[idx] != null && registrationSourceObjectArr[idx].getRegistrationSourceId() == registrationSourceObject.getRegistrationSourceId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (registrationSourceObjectArr.length - 1) )
						registrationSourceObjectArr[idx] = registrationSourceObjectArr[idx + 1]; // Move the array
					else
						registrationSourceObjectArr[idx] = null;
				}
				if ( registrationSourceObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(REGISTRATIONSOURCE, registrationSourceObjectArr);
		}
		return i;
	}
}
