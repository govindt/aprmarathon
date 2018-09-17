/*
 * RegistrationSourceInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.RegistrationSourceObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface RegistrationSourceInterface {
	
    /**
	 *
	 * Interface that returns the RegistrationSourceObject given a RegistrationSourceObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param registrationsource_obj	RegistrationSourceObject
	 *
	 * @return      Returns the ArrayList of RegistrationSourceObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public ArrayList<RegistrationSourceObject> getRegistrationSources(RegistrationSourceObject registrationsource_obj) throws AppException;
	
    /**
	 *
	 * Interface that returns the RegistrationSourceObject given registration_source_id from the underlying datasource.
	 *
	 * @param registration_source_id     int
	 *
	 * @return      Returns the RegistrationSourceObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public RegistrationSourceObject getRegistrationSource(int registration_source_id) throws AppException;
	
    /**
	 *
	 * Interface that returns all the <code>RegistrationSourceObject</code> from the underlying datasource.
	 *
	 * @return      Returns an Array of <code>RegistrationSourceObject</code>
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public RegistrationSourceObject[] getAllRegistrationSources() throws AppException;
	
    /**
	 *
	 * Interface to add the <code>RegistrationSourceObject</code> to the underlying datasource.
	 *
	 * @param registrationSourceObject     RegistrationSourceObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer addRegistrationSource(RegistrationSourceObject registrationSourceObject) throws AppException;
	
    /**
	 *
	 * Interface to update the <code>RegistrationSourceObject</code> in the underlying datasource.
	 *
	 * @param registrationSourceObject     RegistrationSourceObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer updateRegistrationSource(RegistrationSourceObject registrationSourceObject) throws AppException;
	
    /**
	 *
	 * Interface to delete the <code>RegistrationSourceObject</code> in the underlying datasource.
	 *
	 * @param registrationSourceObject     RegistrationSourceObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer deleteRegistrationSource(RegistrationSourceObject registrationSourceObject) throws AppException;
}
