/*
 * EventObject.java
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
 * The implementation of the EventObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class EventObject implements Cloneable {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
	private int event_id;
	private String event_name;
	private Date event_start_date;
	private Date event_end_date;
	private String event_description;
	private Date event_registation_close_date;
	private Date event_changes_close_date;
	
	/**
	 *
	 * Returns the String representation of the EventObject.
	 *
	 * @return	 Returns the String representation of the EventObject.
	 *
	 */
	
	public String toString() {
		String buf="";
		buf += "event_id : " + event_id + "\n";
		buf += "event_name : " + event_name + "\n";
		if (event_start_date != null)
			buf += "event_start_date : " + dateFormatter.format(event_start_date) + "\n";
		else
			buf += "event_start_date : " + "\n";
		if (event_end_date != null)
			buf += "event_end_date : " + dateFormatter.format(event_end_date) + "\n";
		else
			buf += "event_end_date : " + "\n";
		buf += "event_description : " + event_description + "\n";
		if (event_registation_close_date != null)
			buf += "event_registation_close_date : " + dateFormatter.format(event_registation_close_date) + "\n";
		else
			buf += "event_registation_close_date : " + "\n";
		if (event_changes_close_date != null)
			buf += "event_changes_close_date : " + dateFormatter.format(event_changes_close_date) + "\n";
		else
			buf += "event_changes_close_date : " + "\n";
		return buf;
	}

	/**
	 *
	 * Returns the JSON representation of the EventObject.
	 *
	 * @return  	Returns the JSON representation of the EventObject.
	 *
	 */
	
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("event_id", event_id);
			 jo.put("event_name", event_name);
			 jo.put("event_start_date", event_start_date);
			 jo.put("event_end_date", event_end_date);
			 jo.put("event_description", event_description);
			 jo.put("event_registation_close_date", event_registation_close_date);
			 jo.put("event_changes_close_date", event_changes_close_date);
		} catch (JSONException je) {}
		return jo;
	}
	
	/**
	 *
	 * Returns the hashCode representation of the EventObject.
	 *
	 * @return	Returns the hashCode.
	 *
	*/
	
	public int hashCode() {
		return event_id;
	}
	
	/**
	 * Constructs the EventObject
	 *
	 */
	
	public EventObject () {
		setEventId(0);
		setEventName("");
		setEventStartDate(null);
		setEventEndDate(null);
		setEventDescription("");
		setEventRegistationCloseDate(null);
		setEventChangesCloseDate(null);
	}
	
	/**
	 * Constructs the EventObject from JSONObject
	 *
	 */
	
	public EventObject (JSONObject jObject) {
		try {
			event_id = jObject.getInt("event_id");
		} catch (JSONException je) {}
		try {
			event_name = jObject.getString("event_name");
		} catch (JSONException je) {event_name = "";}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				event_start_date = dateFormatter.parse(jObject.getString("event_start_date"));
			} catch (ParseException e) {event_start_date = new Date();
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				event_end_date = dateFormatter.parse(jObject.getString("event_end_date"));
			} catch (ParseException e) {event_end_date = new Date();
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			event_description = jObject.getString("event_description");
		} catch (JSONException je) {event_description = "";}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				event_registation_close_date = dateFormatter.parse(jObject.getString("event_registation_close_date"));
			} catch (ParseException e) {event_registation_close_date = new Date();
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				event_changes_close_date = dateFormatter.parse(jObject.getString("event_changes_close_date"));
			} catch (ParseException e) {event_changes_close_date = new Date();
				e.printStackTrace();
			}
		} catch (JSONException je) {}
	}
	
	
	/**
	 *
	 * Sets the <code>event_id</code> field
	 *
	 * @param event_id	  int
	 *
	 */
	
	public void setEventId(int event_id) {
		this.event_id = event_id;
	}
	
	
	/**
	 *
	 * Gets the <code>event_id</code> field
	 *
	 * @returns event_id
	 *
	 */
	
	public int getEventId() {
		return event_id;
	}

	
	/**
	 *
	 * Sets the <code>event_name</code> field
	 *
	 * @param event_name	  String
	 *
	 */
	
	public void setEventName(String event_name) {
		this.event_name = event_name;
	}
	
	
	/**
	 *
	 * Gets the <code>event_name</code> field
	 *
	 * @returns event_name
	 *
	 */
	
	public String getEventName() {
		return event_name;
	}

	
	/**
	 *
	 * Sets the <code>event_start_date</code> field
	 *
	 * @param event_start_date	  Date
	 *
	 */
	
	public void setEventStartDate(Date event_start_date) {
		this.event_start_date = event_start_date;
	}
	
	
	/**
	 *
	 * Gets the <code>event_start_date</code> field
	 *
	 * @returns event_start_date
	 *
	 */
	
	public Date getEventStartDate() {
		return event_start_date;
	}

	
	/**
	 *
	 * Sets the <code>event_end_date</code> field
	 *
	 * @param event_end_date	  Date
	 *
	 */
	
	public void setEventEndDate(Date event_end_date) {
		this.event_end_date = event_end_date;
	}
	
	
	/**
	 *
	 * Gets the <code>event_end_date</code> field
	 *
	 * @returns event_end_date
	 *
	 */
	
	public Date getEventEndDate() {
		return event_end_date;
	}

	
	/**
	 *
	 * Sets the <code>event_description</code> field
	 *
	 * @param event_description	  String
	 *
	 */
	
	public void setEventDescription(String event_description) {
		this.event_description = event_description;
	}
	
	
	/**
	 *
	 * Gets the <code>event_description</code> field
	 *
	 * @returns event_description
	 *
	 */
	
	public String getEventDescription() {
		return event_description;
	}

	
	/**
	 *
	 * Sets the <code>event_registation_close_date</code> field
	 *
	 * @param event_registation_close_date	  Date
	 *
	 */
	
	public void setEventRegistationCloseDate(Date event_registation_close_date) {
		this.event_registation_close_date = event_registation_close_date;
	}
	
	
	/**
	 *
	 * Gets the <code>event_registation_close_date</code> field
	 *
	 * @returns event_registation_close_date
	 *
	 */
	
	public Date getEventRegistationCloseDate() {
		return event_registation_close_date;
	}

	
	/**
	 *
	 * Sets the <code>event_changes_close_date</code> field
	 *
	 * @param event_changes_close_date	  Date
	 *
	 */
	
	public void setEventChangesCloseDate(Date event_changes_close_date) {
		this.event_changes_close_date = event_changes_close_date;
	}
	
	
	/**
	 *
	 * Gets the <code>event_changes_close_date</code> field
	 *
	 * @returns event_changes_close_date
	 *
	 */
	
	public Date getEventChangesCloseDate() {
		return event_changes_close_date;
	}

	
	/**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
	
	public boolean equals(Object obj) {
		EventObject other = (EventObject)obj;
		DebugHandler.finest("This: " + this);
		DebugHandler.finest("Other: " + other);
		return
			event_id == other.getEventId() &&
			Util.trim(event_name).equals(Util.trim(other.getEventName())) &&
			event_start_date.equals(other.getEventStartDate()) &&
			event_end_date.equals(other.getEventEndDate()) &&
			Util.trim(event_description).equals(Util.trim(other.getEventDescription())) &&
			event_registation_close_date.equals(other.getEventRegistationCloseDate()) &&
			event_changes_close_date.equals(other.getEventChangesCloseDate());
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
