/*
 * EventInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.EventObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface EventInterface {
    
    /**
     *
     * Interface that returns the EventObject given a EventObject filled with values that will be used for query from the underlying datasource.
     *
     * @param event_obj	EventObject
     *
     * @return      Returns the ArrayList of EventObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<EventObject> getEvents(EventObject event_obj) throws AppException;
    
    /**
     *
     * Interface that returns the EventObject given event_id from the underlying datasource.
     *
     * @param event_id     int
     *
     * @return      Returns the EventObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public EventObject getEvent(int event_id) throws AppException;
    
    /**
     *
     * Interface that returns all the <code>EventObject</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>EventObject</code>
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public EventObject[] getAllEvents() throws AppException;
    
    /**
     *
     * Interface to add the <code>EventObject</code> to the underlying datasource.
     *
     * @param eventObject     EventObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer addEvent(EventObject eventObject) throws AppException;
    
    /**
     *
     * Interface to update the <code>EventObject</code> in the underlying datasource.
     *
     * @param eventObject     EventObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer updateEvent(EventObject eventObject) throws AppException;
    
    /**
     *
     * Interface to delete the <code>EventObject</code> in the underlying datasource.
     *
     * @param eventObject     EventObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer deleteEvent(EventObject eventObject) throws AppException;
}
