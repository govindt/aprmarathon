/*
 * EventTypeInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.EventTypeObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface EventTypeInterface {
	
	/**
	 *
	 * Interface that returns the EventTypeObject given a EventTypeObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param eventtype_obj	EventTypeObject
	 *
	 * @return	  Returns the ArrayList of EventTypeObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public ArrayList<EventTypeObject> getEventTypes(EventTypeObject eventtype_obj) throws AppException;
	
	/**
	 *
	 * Interface that returns the EventTypeObject given event_type_id from the underlying datasource.
	 *
	 * @param event_type_id	 int
	 *
	 * @return	  Returns the EventTypeObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public EventTypeObject getEventType(int event_type_id) throws AppException;
	
	/**
	 *
	 * Interface that returns all the <code>EventTypeObject</code> from the underlying datasource.
	 *
	 * @return	  Returns an Array of <code>EventTypeObject</code>
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public EventTypeObject[] getAllEventTypes() throws AppException;
	
	/**
	 *
	 * Interface to add the <code>EventTypeObject</code> to the underlying datasource.
	 *
	 * @param eventTypeObject	 EventTypeObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer addEventType(EventTypeObject eventTypeObject) throws AppException;
	
	/**
	 *
	 * Interface to update the <code>EventTypeObject</code> in the underlying datasource.
	 *
	 * @param eventTypeObject	 EventTypeObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer updateEventType(EventTypeObject eventTypeObject) throws AppException;
	
	/**
	 *
	 * Interface to delete the <code>EventTypeObject</code> in the underlying datasource.
	 *
	 * @param eventTypeObject	 EventTypeObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer deleteEventType(EventTypeObject eventTypeObject) throws AppException;
}
