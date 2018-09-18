/*
 * MedalImpl.java
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
import app.busobj.MedalObject;
import app.businterface.MedalInterface;
import app.util.AppConstants;

/**
 * The implementation of the MedalInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class MedalImpl implements MedalInterface  {
	private String MEDAL = "MedalInterface.getAllMedal";
	
    /**
	 *
	 * Implementation that returns the MedalObject given a MedalObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param medal_obj	MedalObject
	 *
	 * @return      Returns the ArrayList of MedalObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public ArrayList<MedalObject> getMedals(MedalObject medal_obj) throws AppException{
		MedalObject[] medalObjectArr = getAllMedals();
		if ( medal_obj.getMedalId() == Constants.GET_ALL ) {
			if ( medalObjectArr == null )
				return null;
			ArrayList<MedalObject> v = new ArrayList<MedalObject>();
			for ( int i = 0; i < medalObjectArr.length; i++ ) {
				v.add((MedalObject)medalObjectArr[i].clone());
			}
			return v;
		}
		else {
			@SuppressWarnings("unchecked")
			ArrayList<MedalObject> v = (ArrayList<MedalObject>)DBUtil.list(medal_obj,medal_obj);
			DebugHandler.finest("v: " + v);
			return v;
		}
	}
	
    /**
	 *
	 * Implementation of the method that returns the MedalObject from the underlying datasource.
	 * given medal_id.
	 *
	 * @param medal_id     int
	 *
	 * @return      Returns the MedalObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public MedalObject getMedal(int medal_id) throws AppException{
		MedalObject[] medalObjectArr = getAllMedals();
		if ( medalObjectArr == null )
			return null;
		for ( int i = 0; i < medalObjectArr.length; i++ ) {
			if ( medalObjectArr[i] == null ) { // Try database and add to cache if found.
				MedalObject medalObj = new MedalObject();
				medalObj.setMedalId(medal_id);
				@SuppressWarnings("unchecked")
				ArrayList<MedalObject> v = (ArrayList)DBUtil.fetch(medalObj);
				if ( v == null || v.size() == 0 )
					return null;
				else {
					medalObjectArr[i] = (MedalObject)medalObj.clone();
					Util.putInCache(MEDAL, medalObjectArr);
				}
			}
			if ( medalObjectArr[i].getMedalId() == medal_id ) {
				DebugHandler.debug("Returning " + medalObjectArr[i]);
				return (MedalObject)medalObjectArr[i].clone();
			}
		}
		return null;
	}
    
	
    /**
	 *
	 * Implementation that returns all the <code>MedalObjects</code> from the underlying datasource.
	 *
	 * @return      Returns an Array of <code>MedalObject</code>
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public MedalObject[] getAllMedals() throws AppException{
		MedalObject medalObject = new MedalObject();
		MedalObject[] medalObjectArr = (MedalObject[])Util.getAppCache().get(MEDAL);
		if ( medalObjectArr == null ) {
			DebugHandler.info("Getting medal from database");
			@SuppressWarnings("unchecked")
			ArrayList<MedalObject> v = (ArrayList)DBUtil.list(medalObject);
			DebugHandler.finest(":v: " +  v);
			if ( v == null )
				return null;
			medalObjectArr = new MedalObject[v.size()];
			for ( int idx = 0; idx < v.size(); idx++ ) {
				medalObjectArr[idx] = v.get(idx);
			}
			Util.putInCache(MEDAL, medalObjectArr);
		}
		return medalObjectArr;
	}
    
	
    /**
	 *
	 * Implementation to add the <code>MedalObject</code> to the underlying datasource.
	 *
	 * @param medalObject     MedalObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer addMedal(MedalObject medalObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Medal_seq");
			medalObject.setMedalId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(medalObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			medalObject.setMedalId(i.intValue());
			DebugHandler.fine(medalObject);
		}
		MedalObject buf = new MedalObject();
		buf.setMedalId(medalObject.getMedalId());
		@SuppressWarnings("unchecked")
		ArrayList<MedalObject> v = (ArrayList)DBUtil.list(medalObject, buf);
		medalObject = v.get(0);
		MedalObject[] medalObjectArr = getAllMedals();
		boolean foundSpace = false;

		for ( int idx = 0; idx < medalObjectArr.length; idx++ ) {
			if ( medalObjectArr[idx] == null ) {
				medalObjectArr[idx] = (MedalObject)medalObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			MedalObject[] newmedalObjectArr = new MedalObject[medalObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < medalObjectArr.length; idx++ ) {
				newmedalObjectArr[idx] = (MedalObject)medalObjectArr[idx].clone();
			}
			newmedalObjectArr[idx] = (MedalObject)medalObject.clone();
			Util.putInCache(MEDAL, newmedalObjectArr);
		} else {
			Util.putInCache(MEDAL, medalObjectArr);
		}
		return i;
	}
	
	
    /**
	 *
	 * Implementation to update the <code>MedalObject</code> in the underlying datasource.
	 *
	 * @param medalObject     MedalObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer updateMedal(MedalObject medalObject) throws AppException{
		MedalObject newMedalObject = getMedal(medalObject.getMedalId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(medalObject);
		DebugHandler.fine("i: " +  i);
		MedalObject[] medalObjectArr = getAllMedals();
		if ( medalObjectArr == null )
			return null;
		for ( int idx = 0; idx < medalObjectArr.length; idx++ ) {
			if ( medalObjectArr[idx] != null ) {
				if ( medalObjectArr[idx].getMedalId() == medalObject.getMedalId() ) {
					DebugHandler.debug("Found Medal " + medalObject.getMedalId());
					medalObjectArr[idx] = (MedalObject)medalObject.clone();
					Util.putInCache(MEDAL, medalObjectArr);
				}
			}
		}
		return i;
	}
    
	
    /**
	 *
	 * Implementation to delete the <code>MedalObject</code> in the underlying datasource.
	 *
	 * @param medalObject     MedalObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer deleteMedal(MedalObject medalObject) throws AppException{
	MedalObject newMedalObject = getMedal(medalObject.getMedalId()); // This call will make sure cache/db are in sync
	MedalObject[] medalObjectArr = getAllMedals();
	Integer i = new Integer(0);
	if ( medalObject != null ) {
		i = (Integer)DBUtil.delete(medalObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( medalObjectArr != null ) {
			for (int idx = 0; idx < medalObjectArr.length; idx++ ) {
				if ( medalObjectArr[idx] != null && medalObjectArr[idx].getMedalId() == medalObject.getMedalId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (medalObjectArr.length - 1) )
						medalObjectArr[idx] = medalObjectArr[idx + 1]; // Move the array
					else
						medalObjectArr[idx] = null;
				}
				if ( medalObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(MEDAL, medalObjectArr);
		}
		return i;
	}
}
