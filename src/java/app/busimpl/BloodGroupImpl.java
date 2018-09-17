/*
 * BloodGroupImpl.java
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
import app.busobj.BloodGroupObject;
import app.businterface.BloodGroupInterface;
import app.util.AppConstants;

/**
 * The implementation of the BloodGroupInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class BloodGroupImpl implements BloodGroupInterface  {
    private String BLOODGROUP = "BloodGroupInterface.getAllBloodGroup";
    
    /**
     *
     * Implementation that returns the BloodGroupObject given a BloodGroupObject filled with values that will be used for query from the underlying datasource.
     *
     * @param bloodgroup_obj	BloodGroupObject
     *
     * @return      Returns the ArrayList of BloodGroupObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
	public ArrayList<BloodGroupObject> getBloodGroups(BloodGroupObject bloodgroup_obj) throws AppException{
		@SuppressWarnings("unchecked")
		ArrayList<BloodGroupObject> v = (ArrayList<BloodGroupObject>)DBUtil.list(bloodgroup_obj,bloodgroup_obj);
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the BloodGroupObject from the underlying datasource.
     * given blood_group_id.
     *
     * @param blood_group_id     int
     *
     * @return      Returns the BloodGroupObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public BloodGroupObject getBloodGroup(int blood_group_id) throws AppException{
	BloodGroupObject[] bloodGroupObjectArr = getAllBloodGroups();
	if ( bloodGroupObjectArr == null )
	    return null;
	for ( int i = 0; i < bloodGroupObjectArr.length; i++ ) {
	    if ( bloodGroupObjectArr[i] == null ) { // Try database and add to cache if found.
		    BloodGroupObject bloodgroupObj = new BloodGroupObject();
		    bloodgroupObj.setBloodGroupId(blood_group_id);
		    @SuppressWarnings("unchecked")
		    ArrayList<BloodGroupObject> v = (ArrayList)DBUtil.fetch(bloodgroupObj);
		    if ( v == null || v.size() == 0 )
			    return null;
		    else {
			    bloodGroupObjectArr[i] = (BloodGroupObject)bloodgroupObj.clone();
			    Util.putInCache(BLOODGROUP, bloodGroupObjectArr);
		    }
	    }
	    if ( bloodGroupObjectArr[i].getBloodGroupId() == blood_group_id ) {
		    DebugHandler.debug("Returning " + bloodGroupObjectArr[i]);
		    return (BloodGroupObject)bloodGroupObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>BloodGroupObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>BloodGroupObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public BloodGroupObject[] getAllBloodGroups() throws AppException{
		BloodGroupObject bloodGroupObject = new BloodGroupObject();
		BloodGroupObject[] bloodGroupObjectArr = (BloodGroupObject[])Util.getAppCache().get(BLOODGROUP);
		if ( bloodGroupObjectArr == null ) {
		    DebugHandler.info("Getting bloodgroup from database");
		    @SuppressWarnings("unchecked")
		    ArrayList<BloodGroupObject> v = (ArrayList)DBUtil.list(bloodGroupObject);
		    DebugHandler.finest(":v: " +  v);
		    if ( v == null )
			    return null;
		    bloodGroupObjectArr = new BloodGroupObject[v.size()];
		    for ( int idx = 0; idx < v.size(); idx++ ) {
			    bloodGroupObjectArr[idx] = v.get(idx);
		    }
		    Util.putInCache(BLOODGROUP, bloodGroupObjectArr);
		}
		return bloodGroupObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>BloodGroupObject</code> to the underlying datasource.
     *
     * @param bloodGroupObject     BloodGroupObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addBloodGroup(BloodGroupObject bloodGroupObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Blood_Group_seq");
			bloodGroupObject.setBloodGroupId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(bloodGroupObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			bloodGroupObject.setBloodGroupId(i.intValue());
			DebugHandler.fine(bloodGroupObject);
		}
		BloodGroupObject buf = new BloodGroupObject();
		buf.setBloodGroupId(bloodGroupObject.getBloodGroupId());
		@SuppressWarnings("unchecked")
		ArrayList<BloodGroupObject> v = (ArrayList)DBUtil.list(bloodGroupObject, buf);
		bloodGroupObject = v.get(0);
		BloodGroupObject[] bloodGroupObjectArr = getAllBloodGroups();
		boolean foundSpace = false;

		for ( int idx = 0; idx < bloodGroupObjectArr.length; idx++ ) {
			if ( bloodGroupObjectArr[idx] == null ) {
				bloodGroupObjectArr[idx] = (BloodGroupObject)bloodGroupObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			BloodGroupObject[] newbloodGroupObjectArr = new BloodGroupObject[bloodGroupObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < bloodGroupObjectArr.length; idx++ ) {
				newbloodGroupObjectArr[idx] = (BloodGroupObject)bloodGroupObjectArr[idx].clone();
			}
			newbloodGroupObjectArr[idx] = (BloodGroupObject)bloodGroupObject.clone();
			Util.putInCache(BLOODGROUP, newbloodGroupObjectArr);
		} else {
			Util.putInCache(BLOODGROUP, bloodGroupObjectArr);
		}
		return i;
	}
	
    
    /**
     *
     * Implementation to update the <code>BloodGroupObject</code> in the underlying datasource.
     *
     * @param bloodGroupObject     BloodGroupObject
     *
     * @throws AppException if the operation fails
     *
     */
    
	public Integer updateBloodGroup(BloodGroupObject bloodGroupObject) throws AppException{
		BloodGroupObject newBloodGroupObject = getBloodGroup(bloodGroupObject.getBloodGroupId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(bloodGroupObject);
		DebugHandler.fine("i: " +  i);
		BloodGroupObject[] bloodGroupObjectArr = getAllBloodGroups();
		if ( bloodGroupObjectArr == null )
			return null;
		for ( int idx = 0; idx < bloodGroupObjectArr.length; idx++ ) {
			if ( bloodGroupObjectArr[idx] != null ) {
				if ( bloodGroupObjectArr[idx].getBloodGroupId() == bloodGroupObject.getBloodGroupId() ) {
					DebugHandler.debug("Found BloodGroup " + bloodGroupObject.getBloodGroupId());
					bloodGroupObjectArr[idx] = (BloodGroupObject)bloodGroupObject.clone();
					Util.putInCache(BLOODGROUP, bloodGroupObjectArr);
				}
			}
		}
		return i;
	}
    
    
    /**
     *
     * Implementation to delete the <code>BloodGroupObject</code> in the underlying datasource.
     *
     * @param bloodGroupObject     BloodGroupObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteBloodGroup(BloodGroupObject bloodGroupObject) throws AppException{
	BloodGroupObject newBloodGroupObject = getBloodGroup(bloodGroupObject.getBloodGroupId()); // This call will make sure cache/db are in sync
	BloodGroupObject[] bloodGroupObjectArr = getAllBloodGroups();
	Integer i = new Integer(0);
	if ( bloodGroupObject != null ) {
		i = (Integer)DBUtil.delete(bloodGroupObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( bloodGroupObjectArr != null ) {
			for (int idx = 0; idx < bloodGroupObjectArr.length; idx++ ) {
				if ( bloodGroupObjectArr[idx] != null && bloodGroupObjectArr[idx].getBloodGroupId() == bloodGroupObject.getBloodGroupId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (bloodGroupObjectArr.length - 1) )
						bloodGroupObjectArr[idx] = bloodGroupObjectArr[idx + 1]; // Move the array
					else
						bloodGroupObjectArr[idx] = null;
				}
				if ( bloodGroupObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(BLOODGROUP, bloodGroupObjectArr);
		}
		return i;
	}
}
