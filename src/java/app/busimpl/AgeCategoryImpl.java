/*
 * AgeCategoryImpl.java
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
import app.busobj.AgeCategoryObject;
import app.businterface.AgeCategoryInterface;
import app.util.AppConstants;

/**
 * The implementation of the AgeCategoryInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class AgeCategoryImpl implements AgeCategoryInterface  {
    private String AGECATEGORY = "AgeCategoryInterface.getAllAgeCategory";
    
    /**
     *
     * Implementation that returns the AgeCategoryObject given a AgeCategoryObject filled with values that will be used for query from the underlying datasource.
     *
     * @param agecategory_obj	AgeCategoryObject
     *
     * @return      Returns the ArrayList of AgeCategoryObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
	public ArrayList<AgeCategoryObject> getAgeCategorys(AgeCategoryObject agecategory_obj) throws AppException{
		@SuppressWarnings("unchecked")
		ArrayList<AgeCategoryObject> v = (ArrayList<AgeCategoryObject>)DBUtil.list(agecategory_obj,agecategory_obj);
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the AgeCategoryObject from the underlying datasource.
     * given age_category_id.
     *
     * @param age_category_id     int
     *
     * @return      Returns the AgeCategoryObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public AgeCategoryObject getAgeCategory(int age_category_id) throws AppException{
	AgeCategoryObject[] ageCategoryObjectArr = getAllAgeCategorys();
	if ( ageCategoryObjectArr == null )
	    return null;
	for ( int i = 0; i < ageCategoryObjectArr.length; i++ ) {
	    if ( ageCategoryObjectArr[i] == null ) { // Try database and add to cache if found.
		    AgeCategoryObject agecategoryObj = new AgeCategoryObject();
		    agecategoryObj.setAgeCategoryId(age_category_id);
		    @SuppressWarnings("unchecked")
		    ArrayList<AgeCategoryObject> v = (ArrayList)DBUtil.fetch(agecategoryObj);
		    if ( v == null || v.size() == 0 )
			    return null;
		    else {
			    ageCategoryObjectArr[i] = (AgeCategoryObject)agecategoryObj.clone();
			    Util.putInCache(AGECATEGORY, ageCategoryObjectArr);
		    }
	    }
	    if ( ageCategoryObjectArr[i].getAgeCategoryId() == age_category_id ) {
		    DebugHandler.debug("Returning " + ageCategoryObjectArr[i]);
		    return (AgeCategoryObject)ageCategoryObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>AgeCategoryObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>AgeCategoryObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public AgeCategoryObject[] getAllAgeCategorys() throws AppException{
		AgeCategoryObject ageCategoryObject = new AgeCategoryObject();
		AgeCategoryObject[] ageCategoryObjectArr = (AgeCategoryObject[])Util.getAppCache().get(AGECATEGORY);
		if ( ageCategoryObjectArr == null ) {
		    DebugHandler.info("Getting agecategory from database");
		    @SuppressWarnings("unchecked")
		    ArrayList<AgeCategoryObject> v = (ArrayList)DBUtil.list(ageCategoryObject);
		    DebugHandler.finest(":v: " +  v);
		    if ( v == null )
			    return null;
		    ageCategoryObjectArr = new AgeCategoryObject[v.size()];
		    for ( int idx = 0; idx < v.size(); idx++ ) {
			    ageCategoryObjectArr[idx] = v.get(idx);
		    }
		    Util.putInCache(AGECATEGORY, ageCategoryObjectArr);
		}
		return ageCategoryObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>AgeCategoryObject</code> to the underlying datasource.
     *
     * @param ageCategoryObject     AgeCategoryObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addAgeCategory(AgeCategoryObject ageCategoryObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Age_Category_seq");
			ageCategoryObject.setAgeCategoryId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(ageCategoryObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			ageCategoryObject.setAgeCategoryId(i.intValue());
			DebugHandler.fine(ageCategoryObject);
		}
		AgeCategoryObject buf = new AgeCategoryObject();
		buf.setAgeCategoryId(ageCategoryObject.getAgeCategoryId());
		@SuppressWarnings("unchecked")
		ArrayList<AgeCategoryObject> v = (ArrayList)DBUtil.list(ageCategoryObject, buf);
		ageCategoryObject = v.get(0);
		AgeCategoryObject[] ageCategoryObjectArr = getAllAgeCategorys();
		boolean foundSpace = false;

		for ( int idx = 0; idx < ageCategoryObjectArr.length; idx++ ) {
			if ( ageCategoryObjectArr[idx] == null ) {
				ageCategoryObjectArr[idx] = (AgeCategoryObject)ageCategoryObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			AgeCategoryObject[] newageCategoryObjectArr = new AgeCategoryObject[ageCategoryObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < ageCategoryObjectArr.length; idx++ ) {
				newageCategoryObjectArr[idx] = (AgeCategoryObject)ageCategoryObjectArr[idx].clone();
			}
			newageCategoryObjectArr[idx] = (AgeCategoryObject)ageCategoryObject.clone();
			Util.putInCache(AGECATEGORY, newageCategoryObjectArr);
		} else {
			Util.putInCache(AGECATEGORY, ageCategoryObjectArr);
		}
		return i;
	}
	
    
    /**
     *
     * Implementation to update the <code>AgeCategoryObject</code> in the underlying datasource.
     *
     * @param ageCategoryObject     AgeCategoryObject
     *
     * @throws AppException if the operation fails
     *
     */
    
	public Integer updateAgeCategory(AgeCategoryObject ageCategoryObject) throws AppException{
		AgeCategoryObject newAgeCategoryObject = getAgeCategory(ageCategoryObject.getAgeCategoryId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(ageCategoryObject);
		DebugHandler.fine("i: " +  i);
		AgeCategoryObject[] ageCategoryObjectArr = getAllAgeCategorys();
		if ( ageCategoryObjectArr == null )
			return null;
		for ( int idx = 0; idx < ageCategoryObjectArr.length; idx++ ) {
			if ( ageCategoryObjectArr[idx] != null ) {
				if ( ageCategoryObjectArr[idx].getAgeCategoryId() == ageCategoryObject.getAgeCategoryId() ) {
					DebugHandler.debug("Found AgeCategory " + ageCategoryObject.getAgeCategoryId());
					ageCategoryObjectArr[idx] = (AgeCategoryObject)ageCategoryObject.clone();
					Util.putInCache(AGECATEGORY, ageCategoryObjectArr);
				}
			}
		}
		return i;
	}
    
    
    /**
     *
     * Implementation to delete the <code>AgeCategoryObject</code> in the underlying datasource.
     *
     * @param ageCategoryObject     AgeCategoryObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteAgeCategory(AgeCategoryObject ageCategoryObject) throws AppException{
	AgeCategoryObject newAgeCategoryObject = getAgeCategory(ageCategoryObject.getAgeCategoryId()); // This call will make sure cache/db are in sync
	AgeCategoryObject[] ageCategoryObjectArr = getAllAgeCategorys();
	Integer i = new Integer(0);
	if ( ageCategoryObject != null ) {
		i = (Integer)DBUtil.delete(ageCategoryObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( ageCategoryObjectArr != null ) {
			for (int idx = 0; idx < ageCategoryObjectArr.length; idx++ ) {
				if ( ageCategoryObjectArr[idx] != null && ageCategoryObjectArr[idx].getAgeCategoryId() == ageCategoryObject.getAgeCategoryId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (ageCategoryObjectArr.length - 1) )
						ageCategoryObjectArr[idx] = ageCategoryObjectArr[idx + 1]; // Move the array
					else
						ageCategoryObjectArr[idx] = null;
				}
				if ( ageCategoryObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(AGECATEGORY, ageCategoryObjectArr);
		}
		return i;
	}
}
