/*
 * ParticipantSheetObject.java
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
 * The implementation of the ParticipantSheetObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class ParticipantSheetObject implements Cloneable {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
	private int participant_id;
	private String participant_first_name;
	private String participant_middle_name;
	private String participant_last_name;
	private String participant_gender;
	private Date participant_date_of_birth;
	private String participant_age_category;
	private String participant_t_shirt_size;
	private String participant_blood_group;
	private String participant_cell_phone;
	private String participant_email;
	private String participant_group;
	private String registrant_participant_email;
	private int participant_event_id;
	private String participant_event;
	private String participant_type;
	private String participant_source;
	private String participant_event_type;
	private String participant_bib_no;
	private String participant_db_operation;
	
	/**
	 *
	 * Returns the String representation of the ParticipantSheetObject.
	 *
	 * @return	 Returns the String representation of the ParticipantSheetObject.
	 *
	 */
	
	public String toString() {
		String buf="";
		buf += "participant_id : " + participant_id + "\n";
		buf += "participant_first_name : " + participant_first_name + "\n";
		buf += "participant_middle_name : " + participant_middle_name + "\n";
		buf += "participant_last_name : " + participant_last_name + "\n";
		buf += "participant_gender : " + participant_gender + "\n";
		if (participant_date_of_birth != null)
			buf += "participant_date_of_birth : " + dateFormatter.format(participant_date_of_birth) + "\n";
		else
			buf += "participant_date_of_birth : " + "\n";
		buf += "participant_age_category : " + participant_age_category + "\n";
		buf += "participant_t_shirt_size : " + participant_t_shirt_size + "\n";
		buf += "participant_blood_group : " + participant_blood_group + "\n";
		buf += "participant_cell_phone : " + participant_cell_phone + "\n";
		buf += "participant_email : " + participant_email + "\n";
		buf += "participant_group : " + participant_group + "\n";
		buf += "registrant_participant_email : " + registrant_participant_email + "\n";
		buf += "participant_event_id : " + participant_event_id + "\n";
		buf += "participant_event : " + participant_event + "\n";
		buf += "participant_type : " + participant_type + "\n";
		buf += "participant_source : " + participant_source + "\n";
		buf += "participant_event_type : " + participant_event_type + "\n";
		buf += "participant_bib_no : " + participant_bib_no + "\n";
		buf += "participant_db_operation : " + participant_db_operation + "\n";
		return buf;
	}

	/**
	 *
	 * Returns the List representation of the ParticipantSheetObject.
	 *
	 * Returns the object as an array list of Strings.
	 *
	 */
	
	public List<Object> asList() {
		List<Object> list = new ArrayList<Object>();
		list.add(participant_id + "");
		list.add(participant_first_name + "");
		list.add(participant_middle_name + "");
		list.add(participant_last_name + "");
		list.add(participant_gender + "");
		list.add(participant_date_of_birth + "");
		list.add(participant_age_category + "");
		list.add(participant_t_shirt_size + "");
		list.add(participant_blood_group + "");
		list.add(participant_cell_phone + "");
		list.add(participant_email + "");
		list.add(participant_group + "");
		list.add(registrant_participant_email + "");
		list.add(participant_event_id + "");
		list.add(participant_event + "");
		list.add(participant_type + "");
		list.add(participant_source + "");
		list.add(participant_event_type + "");
		list.add(participant_bib_no + "");
		list.add(participant_db_operation + "");
		return list;
	}

	/**
	 *
	 * Returns the JSON representation of the ParticipantSheetObject.
	 *
	 * @return  	Returns the JSON representation of the ParticipantSheetObject.
	 *
	 */
	
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("participant_id", participant_id);
			jo.put("participant_first_name", participant_first_name);
			jo.put("participant_middle_name", participant_middle_name);
			jo.put("participant_last_name", participant_last_name);
			jo.put("participant_gender", participant_gender);
			jo.put("participant_date_of_birth", participant_date_of_birth);
			jo.put("participant_age_category", participant_age_category);
			jo.put("participant_t_shirt_size", participant_t_shirt_size);
			jo.put("participant_blood_group", participant_blood_group);
			jo.put("participant_cell_phone", participant_cell_phone);
			jo.put("participant_email", participant_email);
			jo.put("participant_group", participant_group);
			jo.put("registrant_participant_email", registrant_participant_email);
			jo.put("participant_event_id", participant_event_id);
			jo.put("participant_event", participant_event);
			jo.put("participant_type", participant_type);
			jo.put("participant_source", participant_source);
			jo.put("participant_event_type", participant_event_type);
			jo.put("participant_bib_no", participant_bib_no);
			jo.put("participant_db_operation", participant_db_operation);
		} catch (JSONException je) {}
		return jo;
	}
	
	/**
	 *
	 * Returns the hashCode representation of the ParticipantSheetObject.
	 *
	 * @return	Returns the hashCode.
	 *
	*/
	
	public int hashCode() {
		return participant_id;
	}
	
	/**
	 * Constructs the ParticipantSheetObject
	 *
	 */
	
	public ParticipantSheetObject () {
		setParticipantId(0);
		setParticipantFirstName("");
		setParticipantMiddleName("");
		setParticipantLastName("");
		setParticipantGender("");
		setParticipantDateOfBirth(null);
		setParticipantAgeCategory("");
		setParticipantTShirtSize("");
		setParticipantBloodGroup("");
		setParticipantCellPhone("");
		setParticipantEmail("");
		setParticipantGroup("");
		setRegistrantParticipantEmail("");
		setParticipantEventId(0);
		setParticipantEvent("");
		setParticipantType("");
		setParticipantSource("");
		setParticipantEventType("");
		setParticipantBibNo("");
		setParticipantDbOperation("");
	}
	
	/**
	 * Constructs the ParticipantSheetObject from JSONObject
	 *
	 */
	
	public ParticipantSheetObject (JSONObject jObject) {
		try {
			participant_id = jObject.getInt("participant_id");
		} catch (JSONException je) {}
		try {
			participant_first_name = jObject.getString("participant_first_name");
		} catch (JSONException je) {participant_first_name = "";}
		try {
			participant_middle_name = jObject.getString("participant_middle_name");
		} catch (JSONException je) {participant_middle_name = "";}
		try {
			participant_last_name = jObject.getString("participant_last_name");
		} catch (JSONException je) {participant_last_name = "";}
		try {
			participant_gender = jObject.getString("participant_gender");
		} catch (JSONException je) {participant_gender = "";}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				participant_date_of_birth = dateFormatter.parse(jObject.getString("participant_date_of_birth"));
			} catch (ParseException e) {participant_date_of_birth = new Date();
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			participant_age_category = jObject.getString("participant_age_category");
		} catch (JSONException je) {participant_age_category = "";}
		try {
			participant_t_shirt_size = jObject.getString("participant_t_shirt_size");
		} catch (JSONException je) {participant_t_shirt_size = "";}
		try {
			participant_blood_group = jObject.getString("participant_blood_group");
		} catch (JSONException je) {participant_blood_group = "";}
		try {
			participant_cell_phone = jObject.getString("participant_cell_phone");
		} catch (JSONException je) {participant_cell_phone = "";}
		try {
			participant_email = jObject.getString("participant_email");
		} catch (JSONException je) {participant_email = "";}
		try {
			participant_group = jObject.getString("participant_group");
		} catch (JSONException je) {participant_group = "";}
		try {
			participant_event_id = jObject.getInt("participant_event_id");
		} catch (JSONException je) {}
		try {
			registrant_participant_email = jObject.getString("registrant_participant_email");
		} catch (JSONException je) {registrant_participant_email = "";}
		try {
			participant_event_id = jObject.getInt("participant_event_id");
		} catch (JSONException je) {}
		try {
			participant_event = jObject.getString("participant_event");
		} catch (JSONException je) {participant_event = "";}
		try {
			participant_type = jObject.getString("participant_type");
		} catch (JSONException je) {participant_type = "";}
		try {
			participant_source = jObject.getString("participant_source");
		} catch (JSONException je) {participant_source = "";}
		try {
			participant_event_type = jObject.getString("participant_event_type");
		} catch (JSONException je) {participant_event_type = "";}
		try {
			participant_bib_no = jObject.getString("participant_bib_no");
		} catch (JSONException je) {participant_bib_no = "";}
		try {
			participant_db_operation = jObject.getString("participant_db_operation");
		} catch (JSONException je) {participant_db_operation = "";}
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
	 * Sets the <code>participant_first_name</code> field
	 *
	 * @param participant_first_name	  String
	 *
	 */
	
	public void setParticipantFirstName(String participant_first_name) {
		this.participant_first_name = participant_first_name;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_first_name</code> field
	 *
	 * @returns participant_first_name
	 *
	 */
	
	public String getParticipantFirstName() {
		return participant_first_name;
	}

	
	/**
	 *
	 * Sets the <code>participant_middle_name</code> field
	 *
	 * @param participant_middle_name	  String
	 *
	 */
	
	public void setParticipantMiddleName(String participant_middle_name) {
		this.participant_middle_name = participant_middle_name;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_middle_name</code> field
	 *
	 * @returns participant_middle_name
	 *
	 */
	
	public String getParticipantMiddleName() {
		return participant_middle_name;
	}

	
	/**
	 *
	 * Sets the <code>participant_last_name</code> field
	 *
	 * @param participant_last_name	  String
	 *
	 */
	
	public void setParticipantLastName(String participant_last_name) {
		this.participant_last_name = participant_last_name;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_last_name</code> field
	 *
	 * @returns participant_last_name
	 *
	 */
	
	public String getParticipantLastName() {
		return participant_last_name;
	}

	
	/**
	 *
	 * Sets the <code>participant_gender</code> field
	 *
	 * @param participant_gender	  String
	 *
	 */
	
	public void setParticipantGender(String participant_gender) {
		this.participant_gender = participant_gender;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_gender</code> field
	 *
	 * @returns participant_gender
	 *
	 */
	
	public String getParticipantGender() {
		return participant_gender;
	}

	
	/**
	 *
	 * Sets the <code>participant_date_of_birth</code> field
	 *
	 * @param participant_date_of_birth	  Date
	 *
	 */
	
	public void setParticipantDateOfBirth(Date participant_date_of_birth) {
		this.participant_date_of_birth = participant_date_of_birth;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_date_of_birth</code> field
	 *
	 * @returns participant_date_of_birth
	 *
	 */
	
	public Date getParticipantDateOfBirth() {
		return participant_date_of_birth;
	}

	
	/**
	 *
	 * Sets the <code>participant_age_category</code> field
	 *
	 * @param participant_age_category	  String
	 *
	 */
	
	public void setParticipantAgeCategory(String participant_age_category) {
		this.participant_age_category = participant_age_category;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_age_category</code> field
	 *
	 * @returns participant_age_category
	 *
	 */
	
	public String getParticipantAgeCategory() {
		return participant_age_category;
	}

	
	/**
	 *
	 * Sets the <code>participant_t_shirt_size</code> field
	 *
	 * @param participant_t_shirt_size	  String
	 *
	 */
	
	public void setParticipantTShirtSize(String participant_t_shirt_size) {
		this.participant_t_shirt_size = participant_t_shirt_size;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_t_shirt_size</code> field
	 *
	 * @returns participant_t_shirt_size
	 *
	 */
	
	public String getParticipantTShirtSize() {
		return participant_t_shirt_size;
	}

	
	/**
	 *
	 * Sets the <code>participant_blood_group</code> field
	 *
	 * @param participant_blood_group	  String
	 *
	 */
	
	public void setParticipantBloodGroup(String participant_blood_group) {
		this.participant_blood_group = participant_blood_group;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_blood_group</code> field
	 *
	 * @returns participant_blood_group
	 *
	 */
	
	public String getParticipantBloodGroup() {
		return participant_blood_group;
	}

	
	/**
	 *
	 * Sets the <code>participant_cell_phone</code> field
	 *
	 * @param participant_cell_phone	  String
	 *
	 */
	
	public void setParticipantCellPhone(String participant_cell_phone) {
		this.participant_cell_phone = participant_cell_phone;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_cell_phone</code> field
	 *
	 * @returns participant_cell_phone
	 *
	 */
	
	public String getParticipantCellPhone() {
		return participant_cell_phone;
	}

	
	/**
	 *
	 * Sets the <code>participant_email</code> field
	 *
	 * @param participant_email	  String
	 *
	 */
	
	public void setParticipantEmail(String participant_email) {
		this.participant_email = participant_email;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_email</code> field
	 *
	 * @returns participant_email
	 *
	 */
	
	public String getParticipantEmail() {
		return participant_email;
	}

	
	/**
	 *
	 * Sets the <code>participant_group</code> field
	 *
	 * @param participant_group	  String
	 *
	 */
	
	public void setParticipantGroup(String participant_group) {
		this.participant_group = participant_group;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_group</code> field
	 *
	 * @returns participant_group
	 *
	 */
	
	public String getParticipantGroup() {
		return participant_group;
	}
	
	/**
	 *
	 * Sets the <code>registrant_participant_email</code> field
	 *
	 * @param registrant_participant_email	  String
	 *
	 */
	
	public void setRegistrantParticipantEmail(String registrant_participant_email) {
		this.registrant_participant_email = registrant_participant_email;
	}
	
	
	/**
	 *
	 * Gets the <code>registrant_participant_email</code> field
	 *
	 * @returns registrant_participant_email
	 *
	 */
	
	public String getRegistrantParticipantEmail() {
		return registrant_participant_email;
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
	 * Sets the <code>participant_event</code> field
	 *
	 * @param participant_event	  String
	 *
	 */
	
	public void setParticipantEvent(String participant_event) {
		this.participant_event = participant_event;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_event</code> field
	 *
	 * @returns participant_event
	 *
	 */
	
	public String getParticipantEvent() {
		return participant_event;
	}

	
	/**
	 *
	 * Sets the <code>participant_type</code> field
	 *
	 * @param participant_type	  String
	 *
	 */
	
	public void setParticipantType(String participant_type) {
		this.participant_type = participant_type;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_type</code> field
	 *
	 * @returns participant_type
	 *
	 */
	
	public String getParticipantType() {
		return participant_type;
	}
	
	/**
	 *
	 * Sets the <code>participant_source</code> field
	 *
	 * @param participant_source	  String
	 *
	 */
	
	public void setParticipantSource(String participant_source) {
		this.participant_source = participant_source;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_source</code> field
	 *
	 * @returns participant_source
	 *
	 */
	
	public String getParticipantSource() {
		return participant_source;
	}

	
	/**
	 *
	 * Sets the <code>participant_event_type</code> field
	 *
	 * @param participant_event_type	  String
	 *
	 */
	
	public void setParticipantEventType(String participant_event_type) {
		this.participant_event_type = participant_event_type;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_event_type</code> field
	 *
	 * @returns participant_event_type
	 *
	 */
	
	public String getParticipantEventType() {
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
	 * Sets the <code>participant_db_operation</code> field
	 *
	 * @param participant_db_operation	  String
	 *
	 */
	
	public void setParticipantDbOperation(String participant_db_operation) {
		this.participant_db_operation = participant_db_operation;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_db_operation</code> field
	 *
	 * @returns participant_db_operation
	 *
	 */
	
	public String getParticipantDbOperation() {
		return participant_db_operation;
	}

	
	/**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
	
	public boolean equals(Object obj) {
		ParticipantSheetObject other = (ParticipantSheetObject)obj;
		DebugHandler.finest("This: " + this);
		DebugHandler.finest("Other: " + other);
		return
			participant_id == other.getParticipantId() &&
			Util.trim(participant_first_name).equals(Util.trim(other.getParticipantFirstName())) &&
			Util.trim(participant_middle_name).equals(Util.trim(other.getParticipantMiddleName())) &&
			Util.trim(participant_last_name).equals(Util.trim(other.getParticipantLastName())) &&
			Util.trim(participant_gender).equals(Util.trim(other.getParticipantGender())) &&
			participant_date_of_birth.equals(other.getParticipantDateOfBirth()) &&
			Util.trim(participant_age_category).equals(Util.trim(other.getParticipantAgeCategory())) &&
			Util.trim(participant_t_shirt_size).equals(Util.trim(other.getParticipantTShirtSize())) &&
			Util.trim(participant_blood_group).equals(Util.trim(other.getParticipantBloodGroup())) &&
			Util.trim(participant_cell_phone).equals(Util.trim(other.getParticipantCellPhone())) &&
			Util.trim(participant_email).equals(Util.trim(other.getParticipantEmail())) &&
			Util.trim(participant_group).equals(Util.trim(other.getParticipantGroup())) &&
			participant_event_id == other.getParticipantEventId() &&
			Util.trim(participant_event).equals(Util.trim(other.getParticipantEvent())) &&
			Util.trim(participant_type).equals(Util.trim(other.getParticipantType())) &&
			Util.trim(participant_source).equals(Util.trim(other.getParticipantSource())) &&
			Util.trim(participant_event_type).equals(Util.trim(other.getParticipantEventType())) &&
			Util.trim(participant_bib_no).equals(Util.trim(other.getParticipantBibNo())) &&
			Util.trim(participant_db_operation).equals(Util.trim(other.getParticipantDbOperation()));
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
