/*
 * EventImpl.java
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
import app.busobj.EventObject;
import app.businterface.EventInterface;
import app.util.AppConstants;

/**
 * The implementation of the EventInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class EventImpl implements EventInterface  {
    private String EVENT = "EventInterface.getAllEvent";
    
    /**
     *
     * Implementation that returns the EventObject given a EventObject filled with values that will be used for query from the underlying datasource.
     *
     * @param event_obj	EventObject
     *
     * @return      Returns the ArrayList of EventObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<EventObject> getEvents(EventObject event_obj) throws AppException{
	EventObject[] eventObjectArr = getAllEvents();
	ArrayList<EventObject> v = new ArrayList<EventObject>();
	if ( eventObjectArr == null )
		return null;
	for ( int i = 0; i < eventObjectArr.length; i++ ) {
		if ( eventObjectArr[i] != null ) {
			if ( event_obj.getEventId() == Constants.GET_ALL ) {
				v.add((EventObject)eventObjectArr[i].clone());
			} else {
				if ( (event_obj.getEventId() != 0 && event_obj.getEventId() == eventObjectArr[i].getEventId())
 || (event_obj.getEventName() != null && event_obj.getEventName().equals(eventObjectArr[i].getEventName()))
 || (event_obj.getEventStartDate() != null && event_obj.getEventStartDate().equals(eventObjectArr[i].getEventStartDate()))
 || (event_obj.getEventEndDate() != null && event_obj.getEventEndDate().equals(eventObjectArr[i].getEventEndDate()))
 || (event_obj.getEventDescription() != null && event_obj.getEventDescription().equals(eventObjectArr[i].getEventDescription()))
 || (event_obj.getEventRegistationCloseDate() != null && event_obj.getEventRegistationCloseDate().equals(eventObjectArr[i].getEventRegistationCloseDate()))
 || (event_obj.getEventChangesCloseDate() != null && event_obj.getEventChangesCloseDate().equals(eventObjectArr[i].getEventChangesCloseDate()))
) {
					v.add((EventObject)eventObjectArr[i].clone());
				}
			}
		}
	}
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the EventObject from the underlying datasource.
     * given event_id.
     *
     * @param event_id     int
     *
     * @return      Returns the EventObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public EventObject getEvent(int event_id) throws AppException{
	EventObject[] eventObjectArr = getAllEvents();
	if ( eventObjectArr == null )
	    return null;
	for ( int i = 0; i < eventObjectArr.length; i++ ) {
	    if ( eventObjectArr[i] == null ) { // Try database and add to cache if found.
		EventObject eventObj = new EventObject();
		eventObj.setEventId(event_id);
		@SuppressWarnings("unchecked")
		ArrayList<EventObject> v = (ArrayList)DBUtil.fetch(eventObj);
		if ( v == null || v.size() == 0 )
		    return null;
		else {
		    eventObjectArr[i] = (EventObject)eventObj.clone();
		    Util.putInCache(EVENT, eventObjectArr);
		}
	    }
	    if ( eventObjectArr[i].getEventId() == event_id ) {
		DebugHandler.debug("Returning " + eventObjectArr[i]);
		return (EventObject)eventObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>EventObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>EventObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public EventObject[] getAllEvents() throws AppException{
	EventObject eventObject = new EventObject();
	EventObject[] eventObjectArr = (EventObject[])Util.getAppCache().get(EVENT);
	if ( eventObjectArr == null ) {
	    DebugHandler.info("Getting event from database");
	    @SuppressWarnings("unchecked")
	    ArrayList<EventObject> v = (ArrayList)DBUtil.list(eventObject);
	    DebugHandler.finest(":v: " +  v);
	    if ( v == null )
		return null;
	    eventObjectArr = new EventObject[v.size()];
	    for ( int idx = 0; idx < v.size(); idx++ ) {
		eventObjectArr[idx] = v.get(idx);
	    }
	    Util.putInCache(EVENT, eventObjectArr);
	}
	return eventObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>EventObject</code> to the underlying datasource.
     *
     * @param eventObject     EventObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addEvent(EventObject eventObject) throws AppException{
	if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
		long l = DBUtil.getNextId("Event_seq");
		eventObject.setEventId((int)l);
	}
	Integer i = (Integer)DBUtil.insert(eventObject);
	DebugHandler.fine("i: " +  i);
	EventObject buf = new EventObject();
	buf.setEventName(eventObject.getEventName());
	@SuppressWarnings("unchecked")
	ArrayList<EventObject> v = (ArrayList)DBUtil.list(eventObject, buf);
		eventObject = v.get(0);
	EventObject[] eventObjectArr = getAllEvents();
	boolean foundSpace = false;

	for ( int idx = 0; idx < eventObjectArr.length; idx++ ) {
	    if ( eventObjectArr[idx] == null ) {
		eventObjectArr[idx] = (EventObject)eventObject.clone();
		foundSpace = true;
		break;
	    }
	}
	if ( foundSpace == false ) {
	    EventObject[] neweventObjectArr = new EventObject[eventObjectArr.length + 1];
	    int idx = 0;
	    for ( idx = 0; idx < eventObjectArr.length; idx++ ) {
		neweventObjectArr[idx] = (EventObject)eventObjectArr[idx].clone();
	    }
	    neweventObjectArr[idx] = (EventObject)eventObject.clone();
	    Util.putInCache(EVENT, neweventObjectArr);
	} else {
	    Util.putInCache(EVENT, eventObjectArr);
	}
	return i;
    }
    
    
    /**
     *
     * Implementation to update the <code>EventObject</code> in the underlying datasource.
     *
     * @param eventObject     EventObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer updateEvent(EventObject eventObject) throws AppException{
	EventObject newEventObject = getEvent(eventObject.getEventId()); // This call will make sure cache/db are in sync
	Integer i = (Integer)DBUtil.update(eventObject);
	DebugHandler.fine("i: " +  i);
	EventObject[] eventObjectArr = getAllEvents();
	if ( eventObjectArr == null )
	    return null;
	for ( int idx = 0; idx < eventObjectArr.length; idx++ ) {
	    if ( eventObjectArr[idx] != null ) {
		if ( eventObjectArr[idx].getEventId() == eventObject.getEventId() ) {
		    DebugHandler.debug("Found Event " + eventObject.getEventId());
		    eventObjectArr[idx] = (EventObject)eventObject.clone();
		    Util.putInCache(EVENT, eventObjectArr);
		}
	    }
	}
	return i;
    }
    
    
    /**
     *
     * Implementation to delete the <code>EventObject</code> in the underlying datasource.
     *
     * @param eventObject     EventObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteEvent(EventObject eventObject) throws AppException{
	EventObject newEventObject = getEvent(eventObject.getEventId()); // This call will make sure cache/db are in sync
	EventObject[] eventObjectArr = getAllEvents();
	Integer i = new Integer(0);
	if ( eventObject != null ) {
		i = (Integer)DBUtil.delete(eventObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( eventObjectArr != null ) {
			for (int idx = 0; idx < eventObjectArr.length; idx++ ) {
				if ( eventObjectArr[idx] != null && eventObjectArr[idx].getEventId() == eventObject.getEventId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (eventObjectArr.length - 1) )
						eventObjectArr[idx] = eventObjectArr[idx + 1]; // Move the array
					else
						eventObjectArr[idx] = null;
				}
				if ( eventObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(EVENT, eventObjectArr);
		}
		return i;
	}
}
