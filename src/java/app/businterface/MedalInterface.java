/*
 * MedalInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.MedalObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface MedalInterface {
	
	/**
	 *
	 * Interface that returns the MedalObject given a MedalObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param medal_obj	MedalObject
	 *
	 * @return	  Returns the ArrayList of MedalObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public ArrayList<MedalObject> getMedals(MedalObject medal_obj) throws AppException;
	
	/**
	 *
	 * Interface that returns the MedalObject given medal_id from the underlying datasource.
	 *
	 * @param medal_id	 int
	 *
	 * @return	  Returns the MedalObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public MedalObject getMedal(int medal_id) throws AppException;
	
	/**
	 *
	 * Interface that returns all the <code>MedalObject</code> from the underlying datasource.
	 *
	 * @return	  Returns an Array of <code>MedalObject</code>
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public MedalObject[] getAllMedals() throws AppException;
	
	/**
	 *
	 * Interface to add the <code>MedalObject</code> to the underlying datasource.
	 *
	 * @param medalObject	 MedalObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer addMedal(MedalObject medalObject) throws AppException;
	
	/**
	 *
	 * Interface to update the <code>MedalObject</code> in the underlying datasource.
	 *
	 * @param medalObject	 MedalObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer updateMedal(MedalObject medalObject) throws AppException;
	
	/**
	 *
	 * Interface to delete the <code>MedalObject</code> in the underlying datasource.
	 *
	 * @param medalObject	 MedalObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer deleteMedal(MedalObject medalObject) throws AppException;
}
