/*
 * EventTypeImpl.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.busimpl;

import java.lang.*;
import java.util.*;
import core.util.Constants;
import core.util.DebugHandler;
import core.db.DBUtil;
import core.util.AppException;
import core.util.Util;
import app.busobj.EventTypeObject;
import app.businterface.EventTypeInterface;
import app.util.AppConstants;

/**
 * The implementation of the EventTypeInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class EventTypeImpl implements EventTypeInterface  {
	private String EVENTTYPE = "EventTypeInterface.getAllEventType";
	
    /**
	 *
	 * Implementation that returns the EventTypeObject given a EventTypeObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param eventtype_obj	EventTypeObject
	 *
	 * @return      Returns the ArrayList of EventTypeObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public ArrayList<EventTypeObject> getEventTypes(EventTypeObject eventtype_obj) throws AppException{
		EventTypeObject[] eventTypeObjectArr = getAllEventTypes();
		if ( eventtype_obj.getEventTypeId() == Constants.GET_ALL ) {
			if ( eventTypeObjectArr == null )
				return null;
			ArrayList<EventTypeObject> v = new ArrayList<EventTypeObject>();
			for ( int i = 0; i < eventTypeObjectArr.length; i++ ) {
				v.add((EventTypeObject)eventTypeObjectArr[i].clone());
			}
			return v;
		}
		else {
			@SuppressWarnings("unchecked")
			ArrayList<EventTypeObject> v = (ArrayList<EventTypeObject>)DBUtil.list(eventtype_obj,eventtype_obj);
			DebugHandler.finest("v: " + v);
			return v;
		}
	}
	
    /**
	 *
	 * Implementation of the method that returns the EventTypeObject from the underlying datasource.
	 * given event_type_id.
	 *
	 * @param event_type_id     int
	 *
	 * @return      Returns the EventTypeObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public EventTypeObject getEventType(int event_type_id) throws AppException{
		EventTypeObject[] eventTypeObjectArr = getAllEventTypes();
		if ( eventTypeObjectArr == null )
			return null;
		for ( int i = 0; i < eventTypeObjectArr.length; i++ ) {
			if ( eventTypeObjectArr[i] == null ) { // Try database and add to cache if found.
				EventTypeObject eventtypeObj = new EventTypeObject();
				eventtypeObj.setEventTypeId(event_type_id);
				@SuppressWarnings("unchecked")
				ArrayList<EventTypeObject> v = (ArrayList)DBUtil.fetch(eventtypeObj);
				if ( v == null || v.size() == 0 )
					return null;
				else {
					eventTypeObjectArr[i] = (EventTypeObject)eventtypeObj.clone();
					Util.putInCache(EVENTTYPE, eventTypeObjectArr);
				}
			}
			if ( eventTypeObjectArr[i].getEventTypeId() == event_type_id ) {
				DebugHandler.debug("Returning " + eventTypeObjectArr[i]);
				return (EventTypeObject)eventTypeObjectArr[i].clone();
			}
		}
		return null;
	}
    
	
    /**
	 *
	 * Implementation that returns all the <code>EventTypeObjects</code> from the underlying datasource.
	 *
	 * @return      Returns an Array of <code>EventTypeObject</code>
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public EventTypeObject[] getAllEventTypes() throws AppException{
		EventTypeObject eventTypeObject = new EventTypeObject();
		EventTypeObject[] eventTypeObjectArr = (EventTypeObject[])Util.getAppCache().get(EVENTTYPE);
		if ( eventTypeObjectArr == null ) {
			DebugHandler.info("Getting eventtype from database");
			@SuppressWarnings("unchecked")
			ArrayList<EventTypeObject> v = (ArrayList)DBUtil.list(eventTypeObject);
			DebugHandler.finest(":v: " +  v);
			if ( v == null )
				return null;
			eventTypeObjectArr = new EventTypeObject[v.size()];
			for ( int idx = 0; idx < v.size(); idx++ ) {
				eventTypeObjectArr[idx] = v.get(idx);
			}
			Util.putInCache(EVENTTYPE, eventTypeObjectArr);
		}
		return eventTypeObjectArr;
	}
    
	
    /**
	 *
	 * Implementation to add the <code>EventTypeObject</code> to the underlying datasource.
	 *
	 * @param eventTypeObject     EventTypeObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer addEventType(EventTypeObject eventTypeObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Event_Type_seq");
			eventTypeObject.setEventTypeId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(eventTypeObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			eventTypeObject.setEventTypeId(i.intValue());
			DebugHandler.fine(eventTypeObject);
		}
		EventTypeObject buf = new EventTypeObject();
		buf.setEventTypeId(eventTypeObject.getEventTypeId());
		@SuppressWarnings("unchecked")
		ArrayList<EventTypeObject> v = (ArrayList)DBUtil.list(eventTypeObject, buf);
		eventTypeObject = v.get(0);
		EventTypeObject[] eventTypeObjectArr = getAllEventTypes();
		boolean foundSpace = false;

		for ( int idx = 0; idx < eventTypeObjectArr.length; idx++ ) {
			if ( eventTypeObjectArr[idx] == null ) {
				eventTypeObjectArr[idx] = (EventTypeObject)eventTypeObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			EventTypeObject[] neweventTypeObjectArr = new EventTypeObject[eventTypeObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < eventTypeObjectArr.length; idx++ ) {
				neweventTypeObjectArr[idx] = (EventTypeObject)eventTypeObjectArr[idx].clone();
			}
			neweventTypeObjectArr[idx] = (EventTypeObject)eventTypeObject.clone();
			Util.putInCache(EVENTTYPE, neweventTypeObjectArr);
		} else {
			Util.putInCache(EVENTTYPE, eventTypeObjectArr);
		}
		return i;
	}
	
	
    /**
	 *
	 * Implementation to update the <code>EventTypeObject</code> in the underlying datasource.
	 *
	 * @param eventTypeObject     EventTypeObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer updateEventType(EventTypeObject eventTypeObject) throws AppException{
		EventTypeObject newEventTypeObject = getEventType(eventTypeObject.getEventTypeId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(eventTypeObject);
		DebugHandler.fine("i: " +  i);
		EventTypeObject[] eventTypeObjectArr = getAllEventTypes();
		if ( eventTypeObjectArr == null )
			return null;
		for ( int idx = 0; idx < eventTypeObjectArr.length; idx++ ) {
			if ( eventTypeObjectArr[idx] != null ) {
				if ( eventTypeObjectArr[idx].getEventTypeId() == eventTypeObject.getEventTypeId() ) {
					DebugHandler.debug("Found EventType " + eventTypeObject.getEventTypeId());
					eventTypeObjectArr[idx] = (EventTypeObject)eventTypeObject.clone();
					Util.putInCache(EVENTTYPE, eventTypeObjectArr);
				}
			}
		}
		return i;
	}
    
	
    /**
	 *
	 * Implementation to delete the <code>EventTypeObject</code> in the underlying datasource.
	 *
	 * @param eventTypeObject     EventTypeObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
    
	public Integer deleteEventType(EventTypeObject eventTypeObject) throws AppException{
	EventTypeObject newEventTypeObject = getEventType(eventTypeObject.getEventTypeId()); // This call will make sure cache/db are in sync
	EventTypeObject[] eventTypeObjectArr = getAllEventTypes();
	Integer i = new Integer(0);
	if ( eventTypeObject != null ) {
		i = (Integer)DBUtil.delete(eventTypeObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( eventTypeObjectArr != null ) {
			for (int idx = 0; idx < eventTypeObjectArr.length; idx++ ) {
				if ( eventTypeObjectArr[idx] != null && eventTypeObjectArr[idx].getEventTypeId() == eventTypeObject.getEventTypeId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (eventTypeObjectArr.length - 1) )
						eventTypeObjectArr[idx] = eventTypeObjectArr[idx + 1]; // Move the array
					else
						eventTypeObjectArr[idx] = null;
				}
				if ( eventTypeObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(EVENTTYPE, eventTypeObjectArr);
		}
		return i;
	}
}
