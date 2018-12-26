/*
 * ParticipantObject.java
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
 * The implementation of the ParticipantObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class ParticipantObject implements Cloneable {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
	private int participant_id;
	private String participant_first_name;
	private String participant_middle_name;
	private String participant_last_name;
	private int participant_gender;
	private Date participant_date_of_birth;
	private int participant_t_shirt_size;
	private int participant_blood_group;
	private String participant_cell_phone;
	private String participant_email;
	private int participant_group;
	
	/**
	 *
	 * Returns the String representation of the ParticipantObject.
	 *
	 * @return	 Returns the String representation of the ParticipantObject.
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
		buf += "participant_t_shirt_size : " + participant_t_shirt_size + "\n";
		buf += "participant_blood_group : " + participant_blood_group + "\n";
		buf += "participant_cell_phone : " + participant_cell_phone + "\n";
		buf += "participant_email : " + participant_email + "\n";
		buf += "participant_group : " + participant_group + "\n";
		return buf;
	}

	/**
	 *
	 * Returns the List representation of the ParticipantObject.
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
		list.add(participant_t_shirt_size + "");
		list.add(participant_blood_group + "");
		list.add(participant_cell_phone + "");
		list.add(participant_email + "");
		list.add(participant_group + "");
		return list;
	}

	/**
	 *
	 * Returns the JSON representation of the ParticipantObject.
	 *
	 * @return  	Returns the JSON representation of the ParticipantObject.
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
			jo.put("participant_t_shirt_size", participant_t_shirt_size);
			jo.put("participant_blood_group", participant_blood_group);
			jo.put("participant_cell_phone", participant_cell_phone);
			jo.put("participant_email", participant_email);
			jo.put("participant_group", participant_group);
		} catch (JSONException je) {}
		return jo;
	}
	
	/**
	 *
	 * Returns the hashCode representation of the ParticipantObject.
	 *
	 * @return	Returns the hashCode.
	 *
	*/
	
	public int hashCode() {
		return participant_id;
	}
	
	/**
	 * Constructs the ParticipantObject
	 *
	 */
	
	public ParticipantObject () {
		setParticipantId(0);
		setParticipantFirstName("");
		setParticipantMiddleName("");
		setParticipantLastName("");
		setParticipantGender(0);
		setParticipantDateOfBirth(null);
		setParticipantTShirtSize(0);
		setParticipantBloodGroup(0);
		setParticipantCellPhone("");
		setParticipantEmail("");
		setParticipantGroup(0);
	}
	
	/**
	 * Constructs the ParticipantObject from JSONObject
	 *
	 */
	
	public ParticipantObject (JSONObject jObject) {
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
			participant_gender = jObject.getInt("participant_gender");
		} catch (JSONException je) {}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				participant_date_of_birth = dateFormatter.parse(jObject.getString("participant_date_of_birth"));
			} catch (ParseException e) {participant_date_of_birth = new Date();
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			participant_t_shirt_size = jObject.getInt("participant_t_shirt_size");
		} catch (JSONException je) {}
		try {
			participant_blood_group = jObject.getInt("participant_blood_group");
		} catch (JSONException je) {}
		try {
			participant_cell_phone = jObject.getString("participant_cell_phone");
		} catch (JSONException je) {participant_cell_phone = "";}
		try {
			participant_email = jObject.getString("participant_email");
		} catch (JSONException je) {participant_email = "";}
		try {
			participant_group = jObject.getInt("participant_group");
		} catch (JSONException je) {}
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
	 * @param participant_gender	  int
	 *
	 */
	
	public void setParticipantGender(int participant_gender) {
		this.participant_gender = participant_gender;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_gender</code> field
	 *
	 * @returns participant_gender
	 *
	 */
	
	public int getParticipantGender() {
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
	 * Sets the <code>participant_t_shirt_size</code> field
	 *
	 * @param participant_t_shirt_size	  int
	 *
	 */
	
	public void setParticipantTShirtSize(int participant_t_shirt_size) {
		this.participant_t_shirt_size = participant_t_shirt_size;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_t_shirt_size</code> field
	 *
	 * @returns participant_t_shirt_size
	 *
	 */
	
	public int getParticipantTShirtSize() {
		return participant_t_shirt_size;
	}

	
	/**
	 *
	 * Sets the <code>participant_blood_group</code> field
	 *
	 * @param participant_blood_group	  int
	 *
	 */
	
	public void setParticipantBloodGroup(int participant_blood_group) {
		this.participant_blood_group = participant_blood_group;
	}
	
	
	/**
	 *
	 * Gets the <code>participant_blood_group</code> field
	 *
	 * @returns participant_blood_group
	 *
	 */
	
	public int getParticipantBloodGroup() {
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
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
	
	public boolean equals(Object obj) {
		ParticipantObject other = (ParticipantObject)obj;
		DebugHandler.finest("This: " + this);
		DebugHandler.finest("Other: " + other);
		return
			participant_id == other.getParticipantId() &&
			Util.trim(participant_first_name).equals(Util.trim(other.getParticipantFirstName())) &&
			Util.trim(participant_middle_name).equals(Util.trim(other.getParticipantMiddleName())) &&
			Util.trim(participant_last_name).equals(Util.trim(other.getParticipantLastName())) &&
			participant_gender == other.getParticipantGender() &&
			participant_date_of_birth.equals(other.getParticipantDateOfBirth()) &&
			participant_t_shirt_size == other.getParticipantTShirtSize() &&
			participant_blood_group == other.getParticipantBloodGroup() &&
			Util.trim(participant_cell_phone).equals(Util.trim(other.getParticipantCellPhone())) &&
			Util.trim(participant_email).equals(Util.trim(other.getParticipantEmail())) &&
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
