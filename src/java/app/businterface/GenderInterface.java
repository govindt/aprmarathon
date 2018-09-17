/*
 * GenderInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.GenderObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface GenderInterface {
	
    /**
	 *
	 * Interface that returns the GenderObject given a GenderObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param gender_obj	GenderObject
	 *
	 * @return      Returns the ArrayList of GenderObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public ArrayList<GenderObject> getGenders(GenderObject gender_obj) throws AppException;
	
    /**
	 *
	 * Interface that returns the GenderObject given gender_id from the underlying datasource.
	 *
	 * @param gender_id     int
	 *
	 * @return      Returns the GenderObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public GenderObject getGender(int gender_id) throws AppException;
	
    /**
	 *
	 * Interface that returns all the <code>GenderObject</code> from the underlying datasource.
	 *
	 * @return      Returns an Array of <code>GenderObject</code>
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public GenderObject[] getAllGenders() throws AppException;
	
    /**
	 *
	 * Interface to add the <code>GenderObject</code> to the underlying datasource.
	 *
	 * @param genderObject     GenderObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer addGender(GenderObject genderObject) throws AppException;
	
    /**
	 *
	 * Interface to update the <code>GenderObject</code> in the underlying datasource.
	 *
	 * @param genderObject     GenderObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer updateGender(GenderObject genderObject) throws AppException;
	
    /**
	 *
	 * Interface to delete the <code>GenderObject</code> in the underlying datasource.
	 *
	 * @param genderObject     GenderObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer deleteGender(GenderObject genderObject) throws AppException;
}
