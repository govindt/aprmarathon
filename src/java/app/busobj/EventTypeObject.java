/*
 * EventTypeObject.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.busobj;

import java.util.Date;
import core.util.DebugHandler;
import core.util.Util;
import core.util.Constants;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;

/**
 * The implementation of the EventTypeObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class EventTypeObject implements Cloneable {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
	private int event_type_id;
	private String event_type_name;
	private int event;
	private String event_type_description;
	private Date event_type_start_date;
	private Date event_type_end_date;
	private String event_type_venue;
	private String online_registration_only;
	
	/**
	 *
	 * Returns the String representation of the EventTypeObject.
	 *
	 * @return	 Returns the String representation of the EventTypeObject.
	 *
	 */
    
	public String toString() {
		String buf="";
		buf += "event_type_id : " + event_type_id + "\n";
		buf += "event_type_name : " + event_type_name + "\n";
		buf += "event : " + event + "\n";
		buf += "event_type_description : " + event_type_description + "\n";
		if (event_type_start_date != null)
			buf += "event_type_start_date : " + dateFormatter.format(event_type_start_date) + "\n";
		else
			buf += "event_type_start_date : " + "\n";
		if (event_type_end_date != null)
			buf += "event_type_end_date : " + dateFormatter.format(event_type_end_date) + "\n";
		else
			buf += "event_type_end_date : " + "\n";
		buf += "event_type_venue : " + event_type_venue + "\n";
		return buf;
	}

	/**
	 *
	 * Returns the JSON representation of the EventTypeObject.
	 *
	 * @return      Returns the JSON representation of the EventTypeObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("event_type_id", event_type_id);
			 jo.put("event_type_name", event_type_name);
			 jo.put("event", event);
			 jo.put("event_type_description", event_type_description);
			 jo.put("event_type_start_date", event_type_start_date);
			 jo.put("event_type_end_date", event_type_end_date);
			 jo.put("event_type_venue", event_type_venue);
			 jo.put("online_registration_only", online_registration_only);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the EventTypeObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return event_type_id;
	}
    
	/**
	 * Constructs the EventTypeObject
	 *
	 */
    
	public EventTypeObject () {
		setEventTypeId(0);
		setEventTypeName("");
		setEvent(0);
		setEventTypeDescription("");
		setEventTypeStartDate(null);
		setEventTypeEndDate(null);
		setEventTypeVenue("");
		setOnlineRegistrationOnly("");
	}
    
	/**
	 * Constructs the EventTypeObject from JSONObject
	 *
	 */
    
	public EventTypeObject (JSONObject jObject) {
		try {
			event_type_id = jObject.getInt("event_type_id");
		} catch (JSONException je) {}
		try {
			event_type_name = jObject.getString("event_type_name");
		} catch (JSONException je) {event_type_name = "";}
		try {
			event = jObject.getInt("event");
		} catch (JSONException je) {}
		try {
			event_type_description = jObject.getString("event_type_description");
		} catch (JSONException je) {event_type_description = "";}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				event_type_start_date = dateFormatter.parse(jObject.getString("event_type_start_date"));
			} catch (ParseException e) {event_type_start_date = new Date();
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				event_type_end_date = dateFormatter.parse(jObject.getString("event_type_end_date"));
			} catch (ParseException e) {event_type_end_date = new Date();
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			event_type_venue = jObject.getString("event_type_venue");
		} catch (JSONException je) {event_type_venue = "";}
		try {
			online_registration_only = jObject.getString("online_registration_only");
		} catch (JSONException je) {online_registration_only = "";}
	}
    
	
    /**
	 *
	 * Sets the <code>event_type_id</code> field
	 *
	 * @param event_type_id      int
	 *
	 */
    
	public void setEventTypeId(int event_type_id) {
	    this.event_type_id = event_type_id;
	}
    
	
    /**
	 *
	 * Gets the <code>event_type_id</code> field
	 *
	 * @returns event_type_id
	 *
	 */
    
	public int getEventTypeId() {
	    return event_type_id;
	}

	
    /**
	 *
	 * Sets the <code>event_type_name</code> field
	 *
	 * @param event_type_name      String
	 *
	 */
    
	public void setEventTypeName(String event_type_name) {
	    this.event_type_name = event_type_name;
	}
    
	
    /**
	 *
	 * Gets the <code>event_type_name</code> field
	 *
	 * @returns event_type_name
	 *
	 */
    
	public String getEventTypeName() {
	    return event_type_name;
	}

	
    /**
	 *
	 * Sets the <code>event</code> field
	 *
	 * @param event      int
	 *
	 */
    
	public void setEvent(int event) {
	    this.event = event;
	}
    
	
    /**
	 *
	 * Gets the <code>event</code> field
	 *
	 * @returns event
	 *
	 */
    
	public int getEvent() {
	    return event;
	}

	
    /**
	 *
	 * Sets the <code>event_type_description</code> field
	 *
	 * @param event_type_description      String
	 *
	 */
    
	public void setEventTypeDescription(String event_type_description) {
	    this.event_type_description = event_type_description;
	}
    
	
    /**
	 *
	 * Gets the <code>event_type_description</code> field
	 *
	 * @returns event_type_description
	 *
	 */
    
	public String getEventTypeDescription() {
	    return event_type_description;
	}

	
    /**
	 *
	 * Sets the <code>event_type_start_date</code> field
	 *
	 * @param event_type_start_date      Date
	 *
	 */
    
	public void setEventTypeStartDate(Date event_type_start_date) {
	    this.event_type_start_date = event_type_start_date;
	}
    
	
    /**
	 *
	 * Gets the <code>event_type_start_date</code> field
	 *
	 * @returns event_type_start_date
	 *
	 */
    
	public Date getEventTypeStartDate() {
	    return event_type_start_date;
	}

	
    /**
	 *
	 * Sets the <code>event_type_end_date</code> field
	 *
	 * @param event_type_end_date      Date
	 *
	 */
    
	public void setEventTypeEndDate(Date event_type_end_date) {
	    this.event_type_end_date = event_type_end_date;
	}
    
	
    /**
	 *
	 * Gets the <code>event_type_end_date</code> field
	 *
	 * @returns event_type_end_date
	 *
	 */
    
	public Date getEventTypeEndDate() {
	    return event_type_end_date;
	}

	
    /**
	 *
	 * Sets the <code>event_type_venue</code> field
	 *
	 * @param event_type_venue      String
	 *
	 */
    
	public void setEventTypeVenue(String event_type_venue) {
	    this.event_type_venue = event_type_venue;
	}
    
	
    /**
	 *
	 * Gets the <code>event_type_venue</code> field
	 *
	 * @returns event_type_venue
	 *
	 */
    
	public String getEventTypeVenue() {
	    return event_type_venue;
	}

	
    /**
	 *
	 * Sets the <code>online_registration_only</code> field
	 *
	 * @param online_registration_only      String
	 *
	 */
    
	public void setOnlineRegistrationOnly(String online_registration_only) {
	    this.online_registration_only = online_registration_only;
	}
    
	
    /**
	 *
	 * Gets the <code>online_registration_only</code> field
	 *
	 * @returns online_registration_only
	 *
	 */
    
	public String getOnlineRegistrationOnly() {
	    return online_registration_only;
	}

	
    /**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
    
	public boolean equals(Object obj) {
	    EventTypeObject other = (EventTypeObject)obj;
	    DebugHandler.finest("This: " + this);
	    DebugHandler.finest("Other: " + other);
	    return
	        event_type_id == other.getEventTypeId() &&
	   Util.trim(event_type_name).equals(Util.trim(other.getEventTypeName())) &&
	        event == other.getEvent() &&
	   Util.trim(event_type_description).equals(Util.trim(other.getEventTypeDescription())) &&
	   event_type_start_date.equals(other.getEventTypeStartDate()) &&
	   event_type_end_date.equals(other.getEventTypeEndDate()) &&
	   Util.trim(event_type_venue).equals(Util.trim(other.getEventTypeVenue())) &&
	        Util.trim(online_registration_only).equals(Util.trim(other.getOnlineRegistrationOnly()));
	}
	
    /**
	 *
	 * Clones this object
	 *
	 * @returns the clone of this object
	 *
	 */
    
	public Object clone() {
	    Object theClone = null;
	    try {
	   theClone = super.clone();
	    } catch (CloneNotSupportedException ce) {
	   DebugHandler.severe("Cannot clone " + this);
	    }
	    return theClone;
	}
}
