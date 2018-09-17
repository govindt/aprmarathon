/*
 * RegistrationTypeInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.RegistrationTypeObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface RegistrationTypeInterface {
	
    /**
	 *
	 * Interface that returns the RegistrationTypeObject given a RegistrationTypeObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param registrationtype_obj	RegistrationTypeObject
	 *
	 * @return      Returns the ArrayList of RegistrationTypeObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public ArrayList<RegistrationTypeObject> getRegistrationTypes(RegistrationTypeObject registrationtype_obj) throws AppException;
	
    /**
	 *
	 * Interface that returns the RegistrationTypeObject given registration_type_id from the underlying datasource.
	 *
	 * @param registration_type_id     int
	 *
	 * @return      Returns the RegistrationTypeObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public RegistrationTypeObject getRegistrationType(int registration_type_id) throws AppException;
	
    /**
	 *
	 * Interface that returns all the <code>RegistrationTypeObject</code> from the underlying datasource.
	 *
	 * @return      Returns an Array of <code>RegistrationTypeObject</code>
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public RegistrationTypeObject[] getAllRegistrationTypes() throws AppException;
	
    /**
	 *
	 * Interface to add the <code>RegistrationTypeObject</code> to the underlying datasource.
	 *
	 * @param registrationTypeObject     RegistrationTypeObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer addRegistrationType(RegistrationTypeObject registrationTypeObject) throws AppException;
	
    /**
	 *
	 * Interface to update the <code>RegistrationTypeObject</code> in the underlying datasource.
	 *
	 * @param registrationTypeObject     RegistrationTypeObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer updateRegistrationType(RegistrationTypeObject registrationTypeObject) throws AppException;
	
    /**
	 *
	 * Interface to delete the <code>RegistrationTypeObject</code> in the underlying datasource.
	 *
	 * @param registrationTypeObject     RegistrationTypeObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer deleteRegistrationType(RegistrationTypeObject registrationTypeObject) throws AppException;
}
