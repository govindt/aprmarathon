/*
 * ParticipantEventObject.java
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
 * The implementation of the ParticipantEventObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class ParticipantEventObject implements Cloneable {
	private int participant_event_id;
	private int participant_id;
	private int participant_event;
	private int participant_type;
	private int participant_event_type;
	private String participant_bib_no;
	private int participant_group;
	
	/**
	 *
	 * Returns the String representation of the ParticipantEventObject.
	 *
	 * @return	 Returns the String representation of the ParticipantEventObject.
	 *
	 */
    
	public String toString() {
	   return	"participant_event_id : " + participant_event_id + "\n" +
		"participant_id : " + participant_id + "\n" +
		"participant_event : " + participant_event + "\n" +
		"participant_type : " + participant_type + "\n" +
		"participant_event_type : " + participant_event_type + "\n" +
		"participant_bib_no : " + participant_bib_no + "\n" +
		"participant_group : " + participant_group + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the ParticipantEventObject.
	 *
	 * @return      Returns the JSON representation of the ParticipantEventObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("participant_event_id", participant_event_id);
			 jo.put("participant_id", participant_id);
			 jo.put("participant_event", participant_event);
			 jo.put("participant_type", participant_type);
			 jo.put("participant_event_type", participant_event_type);
			 jo.put("participant_bib_no", participant_bib_no);
			 jo.put("participant_group", participant_group);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the ParticipantEventObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return participant_event_id;
	}
    
	/**
	 * Constructs the ParticipantEventObject
	 *
	 */
    
	public ParticipantEventObject () {
		setParticipantEventId(0);
		setParticipantId(0);
		setParticipantEvent(0);
		setParticipantType(0);
		setParticipantEventType(0);
		setParticipantBibNo("");
		setParticipantGroup(0);
	}
    
	/**
	 * Constructs the ParticipantEventObject from JSONObject
	 *
	 */
    
	public ParticipantEventObject (JSONObject jObject) {
		try {
			participant_event_id = jObject.getInt("participant_event_id");
		} catch (JSONException je) {}
		try {
			participant_id = jObject.getInt("participant_id");
		} catch (JSONException je) {}
		try {
			participant_event = jObject.getInt("participant_event");
		} catch (JSONException je) {}
		try {
			participant_type = jObject.getInt("participant_type");
		} catch (JSONException je) {}
		try {
			participant_event_type = jObject.getInt("participant_event_type");
		} catch (JSONException je) {}
		try {
			participant_bib_no = jObject.getString("participant_bib_no");
		} catch (JSONException je) {}
		try {
			participant_group = jObject.getInt("participant_group");
		} catch (JSONException je) {}
	}
    
	
    /**
	 *
	 * Sets the <code>participant_event_id</code> field
	 *
	 * @param participant_event_id      int
	 *
	 */
    
	public void setParticipantEventId(int participant_event_id) {
	    this.participant_event_id = participant_event_id;
	}
    
	
    /**
	 *
	 * Gets the <code>participant_event_id</code> field
	 *
	 * @returns participant_event_id
	 *
	 */
    
	public int getParticipantEventId() {
	    return participant_event_id;
	}

	
    /**
	 *
	 * Sets the <code>participant_id</code> field
	 *
	 * @param participant_id      int
	 *
	 */
    
	public void setParticipantId(int participant_id) {
	    this.participant_id = participant_id;
	}
    
	
    /**
	 *
	 * Gets the <code>participant_id</code> field
	 *
	 * @returns participant_id
	 *
	 */
    
	public int getParticipantId() {
	    return participant_id;
	}

	
    /**
	 *
	 * Sets the <code>participant_event</code> field
	 *
	 * @param participant_event      int
	 *
	 */
    
	public void setParticipantEvent(int participant_event) {
	    this.participant_event = participant_event;
	}
    
	
    /**
	 *
	 * Gets the <code>participant_event</code> field
	 *
	 * @returns participant_event
	 *
	 */
    
	public int getParticipantEvent() {
	    return participant_event;
	}

	
    /**
	 *
	 * Sets the <code>participant_type</code> field
	 *
	 * @param participant_type      int
	 *
	 */
    
	public void setParticipantType(int participant_type) {
	    this.participant_type = participant_type;
	}
    
	
    /**
	 *
	 * Gets the <code>participant_type</code> field
	 *
	 * @returns participant_type
	 *
	 */
    
	public int getParticipantType() {
	    return participant_type;
	}

	
    /**
	 *
	 * Sets the <code>participant_event_type</code> field
	 *
	 * @param participant_event_type      int
	 *
	 */
    
	public void setParticipantEventType(int participant_event_type) {
	    this.participant_event_type = participant_event_type;
	}
    
	
    /**
	 *
	 * Gets the <code>participant_event_type</code> field
	 *
	 * @returns participant_event_type
	 *
	 */
    
	public int getParticipantEventType() {
	    return participant_event_type;
	}

	
    /**
	 *
	 * Sets the <code>participant_bib_no</code> field
	 *
	 * @param participant_bib_no      String
	 *
	 */
    
	public void setParticipantBibNo(String participant_bib_no) {
	    this.participant_bib_no = participant_bib_no;
	}
    
	
    /**
	 *
	 * Gets the <code>participant_bib_no</code> field
	 *
	 * @returns participant_bib_no
	 *
	 */
    
	public String getParticipantBibNo() {
	    return participant_bib_no;
	}

	
    /**
	 *
	 * Sets the <code>participant_group</code> field
	 *
	 * @param participant_group      int
	 *
	 */
    
	public void setParticipantGroup(int participant_group) {
	    this.participant_group = participant_group;
	}
    
	
    /**
	 *
	 * Gets the <code>participant_group</code> field
	 *
	 * @returns participant_group
	 *
	 */
    
	public int getParticipantGroup() {
	    return participant_group;
	}

	
    /**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
    
	public boolean equals(Object obj) {
	    ParticipantEventObject other = (ParticipantEventObject)obj;
	    DebugHandler.finest("This: " + this);
	    DebugHandler.finest("Other: " + other);
	    return
	        participant_event_id == other.getParticipantEventId() &&
	        participant_id == other.getParticipantId() &&
	        participant_event == other.getParticipantEvent() &&
	        participant_type == other.getParticipantType() &&
	        participant_event_type == other.getParticipantEventType() &&
	   Util.trim(participant_bib_no).equals(Util.trim(other.getParticipantBibNo())) &&
	        participant_group == other.getParticipantGroup();
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
