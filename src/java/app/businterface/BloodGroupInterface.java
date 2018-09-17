/*
 * BloodGroupInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.BloodGroupObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface BloodGroupInterface {
	
    /**
	 *
	 * Interface that returns the BloodGroupObject given a BloodGroupObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param bloodgroup_obj	BloodGroupObject
	 *
	 * @return      Returns the ArrayList of BloodGroupObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public ArrayList<BloodGroupObject> getBloodGroups(BloodGroupObject bloodgroup_obj) throws AppException;
	
    /**
	 *
	 * Interface that returns the BloodGroupObject given blood_group_id from the underlying datasource.
	 *
	 * @param blood_group_id     int
	 *
	 * @return      Returns the BloodGroupObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public BloodGroupObject getBloodGroup(int blood_group_id) throws AppException;
	
    /**
	 *
	 * Interface that returns all the <code>BloodGroupObject</code> from the underlying datasource.
	 *
	 * @return      Returns an Array of <code>BloodGroupObject</code>
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public BloodGroupObject[] getAllBloodGroups() throws AppException;
	
    /**
	 *
	 * Interface to add the <code>BloodGroupObject</code> to the underlying datasource.
	 *
	 * @param bloodGroupObject     BloodGroupObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer addBloodGroup(BloodGroupObject bloodGroupObject) throws AppException;
	
    /**
	 *
	 * Interface to update the <code>BloodGroupObject</code> in the underlying datasource.
	 *
	 * @param bloodGroupObject     BloodGroupObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer updateBloodGroup(BloodGroupObject bloodGroupObject) throws AppException;
	
    /**
	 *
	 * Interface to delete the <code>BloodGroupObject</code> in the underlying datasource.
	 *
	 * @param bloodGroupObject     BloodGroupObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer deleteBloodGroup(BloodGroupObject bloodGroupObject) throws AppException;
}
