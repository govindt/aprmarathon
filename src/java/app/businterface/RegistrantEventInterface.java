/*
 * RegistrantEventInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.RegistrantEventObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface RegistrantEventInterface {
    
    /**
     *
     * Interface that returns the RegistrantEventObject given a RegistrantEventObject filled with values that will be used for query from the underlying datasource.
     *
     * @param registrantevent_obj	RegistrantEventObject
     *
     * @return      Returns the ArrayList of RegistrantEventObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<RegistrantEventObject> getRegistrantEvents(RegistrantEventObject registrantevent_obj) throws AppException;
    
    /**
     *
     * Interface that returns the RegistrantEventObject given registrant_event_id from the underlying datasource.
     *
     * @param registrant_event_id     int
     *
     * @return      Returns the RegistrantEventObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public RegistrantEventObject getRegistrantEvent(int registrant_event_id) throws AppException;
    
    /**
     *
     * Interface that returns all the <code>RegistrantEventObject</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>RegistrantEventObject</code>
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public RegistrantEventObject[] getAllRegistrantEvents() throws AppException;
    
    /**
     *
     * Interface to add the <code>RegistrantEventObject</code> to the underlying datasource.
     *
     * @param registrantEventObject     RegistrantEventObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer addRegistrantEvent(RegistrantEventObject registrantEventObject) throws AppException;
    
    /**
     *
     * Interface to update the <code>RegistrantEventObject</code> in the underlying datasource.
     *
     * @param registrantEventObject     RegistrantEventObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer updateRegistrantEvent(RegistrantEventObject registrantEventObject) throws AppException;
    
    /**
     *
     * Interface to delete the <code>RegistrantEventObject</code> in the underlying datasource.
     *
     * @param registrantEventObject     RegistrantEventObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer deleteRegistrantEvent(RegistrantEventObject registrantEventObject) throws AppException;
}
