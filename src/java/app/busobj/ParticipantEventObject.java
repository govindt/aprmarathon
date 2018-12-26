/*
 * ParticipantEventObject.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.busobj;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;
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
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
	private int participant_event_id;
	private int participant_id;
	private int participant_event;
	private int participant_type;
	private int participant_event_type;
	private String participant_bib_no;
	private int participant_group;
	private int participant_event_age_category;
	private String participant_event_net_time;
	private String participant_event_gun_time;
	private int participant_event_category_rank;
	
	/**
	 *
	 * Returns the String representation of the ParticipantEventObject.
	 *
	 * @return	 Returns the String representation of the ParticipantEventObject.
	 *
	 */
	
	public String toString() {
		String buf="";
		buf += "participant_event_id : " + participant_event_id + "\n";
		buf += "participant_id : " + participant_id + "\n";
		buf += "participant_event : " + participant_event + "\n";
		buf += "participant_type : " + participant_type + "\n";
		buf += "participant_event_type : " + participant_event_type + "\n";
		buf += "participant_bib_no : " + participant_bib_no + "\n";
		buf += "participant_group : " + participant_group + "\n";
		buf += "participant_event_age_category : " + participant_event_age_category + "\n";
		buf += "participant_event_net_time : " + participant_event_net_time + "\n";
		buf += "participant_event_gun_time : " + participant_event_gun_time + "\n";
		buf += "participant_event_category_rank : " + participant_event_category_rank + "\n";
		return buf;
	}

	/**
	 *
	 * Returns the List representation of the ParticipantEventObject.
	 *
	 * Returns the object as an array list of Strings.
	 *
	 */
	
	public List<Object> asList() {
		List<Object> list = new ArrayList<Object>();
		list.add(participant_event_id + "");
		list.add(participant_id + "");
		list.add(participant_event + "");
		list.add(participant_type + "");
		list.add(participant_event_type + "");
		list.add(participant_bib_no + "");
		list.add(participant_group + "");
		list.add(participant_event_age_category + "");
		list.add(participant_event_net_time + "");
		list.add(participant_event_gun_time + "");
		list.add(participant_event_category_rank + "");
		return list;
	}

	/**
	 *
	 * Returns the JSON representation of the ParticipantEventObject.
	 *
	 * @return  	Returns the JSON representation of the ParticipantEventObject.
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
			jo.put("participant_event_age_category", participant_event_age_category);
			jo.put("participant_event_net_time", participant_event_net_time);
			jo.put("participant_event_gun_time", participant_event_gun_time);
			jo.put("participant_event_category_rank", participant_event_category_rank);
		} catch (JSONException je) {}
		return jo;
	}
	
	/**
	 *
	 * Returns the hashCode representation of the ParticipantEventObject.
	 *
	 * @return	Returns the hashCode.
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
		setParticipantEventAgeCategory(0);
		setParticipantEventNetTime("");
		setParticipantEventGunTime("");
		setParticipantEventCategoryRank(0);
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
		} catch (JSONException je) {participant_bib_no = "";}
		try {
			participant_group = jObject.getInt("participant_group");
		} catch (JSONException je) {}
		try {
			participant_event_age_category = jObject.getInt("participant_event_age_category");
		} catch (JSONException je) {}
		try {
			participant_event_net_time = jObject.getString("participant_event_net_time");
		} catch (JSONException je) {participant_event_net_time = "";}
		try {
			participant_event_gun_time = jObject.getString("participant_event_gun_time");
		} catch (JSONException je) {participant_event_gun_time = "";}
		try {
			participant_event_category_rank = jObject.getInt("participant_event_category_rank");
		} catch (JSONException je) {}
	}
	
	
	/**
	 *
	 * Sets the <code>participant_event_id</code> field
	 *
	 * @param participant_event_id	  int
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
	 * @param participant_id	  int
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
	 * @param participant_event	  int
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
	 * @param participant_type	  int
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
	 * @param participant_event_type	  int
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
	 * @param participant_bib_no	  String
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
	 * @param participant_group	  int
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
	 * Sets the <code>participant_event_age_category</code> field
	 *
	 * @param participant_event_age_category	  int
	 *
	 */
	
	public void setParticipantEventAgeCategory(int participant_event_age_category) {
		this.participant_event_age_category = participant_event_age_category;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_event_age_category</code> field
	 *
	 * @returns participant_event_age_category
	 *
	 */
	
	public int getParticipantEventAgeCategory() {
		return participant_event_age_category;
	}

	
	/**
	 *
	 * Sets the <code>participant_event_net_time</code> field
	 *
	 * @param participant_event_net_time	  String
	 *
	 */
	
	public void setParticipantEventNetTime(String participant_event_net_time) {
		this.participant_event_net_time = participant_event_net_time;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_event_net_time</code> field
	 *
	 * @returns participant_event_net_time
	 *
	 */
	
	public String getParticipantEventNetTime() {
		return participant_event_net_time;
	}

	
	/**
	 *
	 * Sets the <code>participant_event_gun_time</code> field
	 *
	 * @param participant_event_gun_time	  String
	 *
	 */
	
	public void setParticipantEventGunTime(String participant_event_gun_time) {
		this.participant_event_gun_time = participant_event_gun_time;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_event_gun_time</code> field
	 *
	 * @returns participant_event_gun_time
	 *
	 */
	
	public String getParticipantEventGunTime() {
		return participant_event_gun_time;
	}

	
	/**
	 *
	 * Sets the <code>participant_event_category_rank</code> field
	 *
	 * @param participant_event_category_rank	  int
	 *
	 */
	
	public void setParticipantEventCategoryRank(int participant_event_category_rank) {
		this.participant_event_category_rank = participant_event_category_rank;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_event_category_rank</code> field
	 *
	 * @returns participant_event_category_rank
	 *
	 */
	
	public int getParticipantEventCategoryRank() {
		return participant_event_category_rank;
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
			participant_group == other.getParticipantGroup() &&
			participant_event_age_category == other.getParticipantEventAgeCategory() &&
			Util.trim(participant_event_net_time).equals(Util.trim(other.getParticipantEventNetTime())) &&
			Util.trim(participant_event_gun_time).equals(Util.trim(other.getParticipantEventGunTime())) &&
			participant_event_category_rank == other.getParticipantEventCategoryRank();
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
