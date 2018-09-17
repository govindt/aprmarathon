/*
 * GenderImpl.java
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
import app.busobj.GenderObject;
import app.businterface.GenderInterface;
import app.util.AppConstants;

/**
 * The implementation of the GenderInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class GenderImpl implements GenderInterface  {
	private String GENDER = "GenderInterface.getAllGender";
	
    /**
	 *
	 * Implementation that returns the GenderObject given a GenderObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param gender_obj	GenderObject
	 *
	 * @return      Returns the ArrayList of GenderObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public ArrayList<GenderObject> getGenders(GenderObject gender_obj) throws AppException{
		GenderObject[] genderObjectArr = getAllGenders();
		@SuppressWarnings("unchecked")
		ArrayList<GenderObject> v = (ArrayList<GenderObject>)DBUtil.list(gender_obj,gender_obj);
		DebugHandler.finest("v: " + v);
		return v;
	}
	
    /**
	 *
	 * Implementation of the method that returns the GenderObject from the underlying datasource.
	 * given gender_id.
	 *
	 * @param gender_id     int
	 *
	 * @return      Returns the GenderObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public GenderObject getGender(int gender_id) throws AppException{
		GenderObject[] genderObjectArr = getAllGenders();
		if ( genderObjectArr == null )
			return null;
		for ( int i = 0; i < genderObjectArr.length; i++ ) {
			if ( genderObjectArr[i] == null ) { // Try database and add to cache if found.
				GenderObject genderObj = new GenderObject();
				genderObj.setGenderId(gender_id);
				@SuppressWarnings("unchecked")
				ArrayList<GenderObject> v = (ArrayList)DBUtil.fetch(genderObj);
				if ( v == null || v.size() == 0 )
					return null;
				else {
					genderObjectArr[i] = (GenderObject)genderObj.clone();
					Util.putInCache(GENDER, genderObjectArr);
				}
			}
			if ( genderObjectArr[i].getGenderId() == gender_id ) {
				DebugHandler.debug("Returning " + genderObjectArr[i]);
				return (GenderObject)genderObjectArr[i].clone();
			}
		}
		return null;
	}
    
	
    /**
	 *
	 * Implementation that returns all the <code>GenderObjects</code> from the underlying datasource.
	 *
	 * @return      Returns an Array of <code>GenderObject</code>
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public GenderObject[] getAllGenders() throws AppException{
		GenderObject genderObject = new GenderObject();
		GenderObject[] genderObjectArr = (GenderObject[])Util.getAppCache().get(GENDER);
		if ( genderObjectArr == null ) {
			DebugHandler.info("Getting gender from database");
			@SuppressWarnings("unchecked")
			ArrayList<GenderObject> v = (ArrayList)DBUtil.list(genderObject);
			DebugHandler.finest(":v: " +  v);
			if ( v == null )
				return null;
			genderObjectArr = new GenderObject[v.size()];
			for ( int idx = 0; idx < v.size(); idx++ ) {
				genderObjectArr[idx] = v.get(idx);
			}
			Util.putInCache(GENDER, genderObjectArr);
		}
		return genderObjectArr;
	}
    
	
    /**
	 *
	 * Implementation to add the <code>GenderObject</code> to the underlying datasource.
	 *
	 * @param genderObject     GenderObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer addGender(GenderObject genderObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Gender_seq");
			genderObject.setGenderId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(genderObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			genderObject.setGenderId(i.intValue());
			DebugHandler.fine(genderObject);
		}
		GenderObject buf = new GenderObject();
		buf.setGenderId(genderObject.getGenderId());
		@SuppressWarnings("unchecked")
		ArrayList<GenderObject> v = (ArrayList)DBUtil.list(genderObject, buf);
		genderObject = v.get(0);
		GenderObject[] genderObjectArr = getAllGenders();
		boolean foundSpace = false;

		for ( int idx = 0; idx < genderObjectArr.length; idx++ ) {
			if ( genderObjectArr[idx] == null ) {
				genderObjectArr[idx] = (GenderObject)genderObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			GenderObject[] newgenderObjectArr = new GenderObject[genderObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < genderObjectArr.length; idx++ ) {
				newgenderObjectArr[idx] = (GenderObject)genderObjectArr[idx].clone();
			}
			newgenderObjectArr[idx] = (GenderObject)genderObject.clone();
			Util.putInCache(GENDER, newgenderObjectArr);
		} else {
			Util.putInCache(GENDER, genderObjectArr);
		}
		return i;
	}
	
	
    /**
	 *
	 * Implementation to update the <code>GenderObject</code> in the underlying datasource.
	 *
	 * @param genderObject     GenderObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer updateGender(GenderObject genderObject) throws AppException{
		GenderObject newGenderObject = getGender(genderObject.getGenderId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(genderObject);
		DebugHandler.fine("i: " +  i);
		GenderObject[] genderObjectArr = getAllGenders();
		if ( genderObjectArr == null )
			return null;
		for ( int idx = 0; idx < genderObjectArr.length; idx++ ) {
			if ( genderObjectArr[idx] != null ) {
				if ( genderObjectArr[idx].getGenderId() == genderObject.getGenderId() ) {
					DebugHandler.debug("Found Gender " + genderObject.getGenderId());
					genderObjectArr[idx] = (GenderObject)genderObject.clone();
					Util.putInCache(GENDER, genderObjectArr);
				}
			}
		}
		return i;
	}
    
	
    /**
	 *
	 * Implementation to delete the <code>GenderObject</code> in the underlying datasource.
	 *
	 * @param genderObject     GenderObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer deleteGender(GenderObject genderObject) throws AppException{
	GenderObject newGenderObject = getGender(genderObject.getGenderId()); // This call will make sure cache/db are in sync
	GenderObject[] genderObjectArr = getAllGenders();
	Integer i = new Integer(0);
	if ( genderObject != null ) {
		i = (Integer)DBUtil.delete(genderObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( genderObjectArr != null ) {
			for (int idx = 0; idx < genderObjectArr.length; idx++ ) {
				if ( genderObjectArr[idx] != null && genderObjectArr[idx].getGenderId() == genderObject.getGenderId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (genderObjectArr.length - 1) )
						genderObjectArr[idx] = genderObjectArr[idx + 1]; // Move the array
					else
						genderObjectArr[idx] = null;
				}
				if ( genderObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(GENDER, genderObjectArr);
		}
		return i;
	}
}
