/*
 * AgeCategoryInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.AgeCategoryObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface AgeCategoryInterface {
	
	/**
	 *
	 * Interface that returns the AgeCategoryObject given a AgeCategoryObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param agecategory_obj	AgeCategoryObject
	 *
	 * @return	  Returns the ArrayList of AgeCategoryObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public ArrayList<AgeCategoryObject> getAgeCategorys(AgeCategoryObject agecategory_obj) throws AppException;
	
	/**
	 *
	 * Interface that returns the AgeCategoryObject given age_category_id from the underlying datasource.
	 *
	 * @param age_category_id	 int
	 *
	 * @return	  Returns the AgeCategoryObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public AgeCategoryObject getAgeCategory(int age_category_id) throws AppException;
	
	/**
	 *
	 * Interface that returns all the <code>AgeCategoryObject</code> from the underlying datasource.
	 *
	 * @return	  Returns an Array of <code>AgeCategoryObject</code>
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public AgeCategoryObject[] getAllAgeCategorys() throws AppException;
	
	/**
	 *
	 * Interface to add the <code>AgeCategoryObject</code> to the underlying datasource.
	 *
	 * @param ageCategoryObject	 AgeCategoryObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer addAgeCategory(AgeCategoryObject ageCategoryObject) throws AppException;
	
	/**
	 *
	 * Interface to update the <code>AgeCategoryObject</code> in the underlying datasource.
	 *
	 * @param ageCategoryObject	 AgeCategoryObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer updateAgeCategory(AgeCategoryObject ageCategoryObject) throws AppException;
	
	/**
	 *
	 * Interface to delete the <code>AgeCategoryObject</code> in the underlying datasource.
	 *
	 * @param ageCategoryObject	 AgeCategoryObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer deleteAgeCategory(AgeCategoryObject ageCategoryObject) throws AppException;
}
