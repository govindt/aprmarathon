/*
 * RegistrantInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.RegistrantObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface RegistrantInterface {
	
    /**
	 *
	 * Interface that returns the RegistrantObject given a RegistrantObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param registrant_obj	RegistrantObject
	 *
	 * @return      Returns the ArrayList of RegistrantObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public ArrayList<RegistrantObject> getRegistrants(RegistrantObject registrant_obj) throws AppException;
	
    /**
	 *
	 * Interface that returns the RegistrantObject given registrant_id from the underlying datasource.
	 *
	 * @param registrant_id     int
	 *
	 * @return      Returns the RegistrantObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public RegistrantObject getRegistrant(int registrant_id) throws AppException;
	
    /**
	 *
	 * Interface that returns all the <code>RegistrantObject</code> from the underlying datasource.
	 *
	 * @return      Returns an Array of <code>RegistrantObject</code>
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public RegistrantObject[] getAllRegistrants() throws AppException;
	
    /**
	 *
	 * Interface to add the <code>RegistrantObject</code> to the underlying datasource.
	 *
	 * @param registrantObject     RegistrantObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer addRegistrant(RegistrantObject registrantObject) throws AppException;
	
    /**
	 *
	 * Interface to update the <code>RegistrantObject</code> in the underlying datasource.
	 *
	 * @param registrantObject     RegistrantObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer updateRegistrant(RegistrantObject registrantObject) throws AppException;
	
    /**
	 *
	 * Interface to delete the <code>RegistrantObject</code> in the underlying datasource.
	 *
	 * @param registrantObject     RegistrantObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer deleteRegistrant(RegistrantObject registrantObject) throws AppException;
}
