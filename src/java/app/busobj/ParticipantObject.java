/*
 * ParticipantObject.java
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
 * The implementation of the ParticipantObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class ParticipantObject implements Cloneable {
	private int participant_id;
	private String participant_first_name;
	private String participant_middle_name;
	private String participant_last_name;
	private int participant_gender;
	private Date participant_date_of_birth;
	private int participant_age_category;
	private int participant_t_shirt_size;
	private int participant_blood_group;
	private String participant_cell_phone;
	private String participant_email;
	
	/**
	 *
	 * Returns the String representation of the ParticipantObject.
	 *
	 * @return	 Returns the String representation of the ParticipantObject.
	 *
	 */
    
	public String toString() {
	   return	"participant_id : " + participant_id + "\n" +
		"participant_first_name : " + participant_first_name + "\n" +
		"participant_middle_name : " + participant_middle_name + "\n" +
		"participant_last_name : " + participant_last_name + "\n" +
		"participant_gender : " + participant_gender + "\n" +
		"participant_date_of_birth : " + participant_date_of_birth + "\n" +
		"participant_age_category : " + participant_age_category + "\n" +
		"participant_t_shirt_size : " + participant_t_shirt_size + "\n" +
		"participant_blood_group : " + participant_blood_group + "\n" +
		"participant_cell_phone : " + participant_cell_phone + "\n" +
		"participant_email : " + participant_email + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the ParticipantObject.
	 *
	 * @return      Returns the JSON representation of the ParticipantObject.
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
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the ParticipantObject.
	 *
	 * @return      Returns the hashCode.
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
		setParticipantAgeCategory(0);
		setParticipantTShirtSize(0);
		setParticipantBloodGroup(0);
		setParticipantCellPhone("");
		setParticipantEmail("");
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
		} catch (JSONException je) {}
		try {
			participant_middle_name = jObject.getString("participant_middle_name");
		} catch (JSONException je) {}
		try {
			participant_last_name = jObject.getString("participant_last_name");
		} catch (JSONException je) {}
		try {
			participant_gender = jObject.getInt("participant_gender");
		} catch (JSONException je) {}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				participant_date_of_birth = dateFormatter.parse(jObject.getString("participant_date_of_birth"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			participant_age_category = jObject.getInt("participant_age_category");
		} catch (JSONException je) {}
		try {
			participant_t_shirt_size = jObject.getInt("participant_t_shirt_size");
		} catch (JSONException je) {}
		try {
			participant_blood_group = jObject.getInt("participant_blood_group");
		} catch (JSONException je) {}
		try {
			participant_cell_phone = jObject.getString("participant_cell_phone");
		} catch (JSONException je) {}
		try {
			participant_email = jObject.getString("participant_email");
		} catch (JSONException je) {}
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
     * Sets the <code>participant_first_name</code> field
     *
     * @param participant_first_name      String
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
     * @param participant_middle_name      String
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
     * @param participant_last_name      String
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
     * @param participant_gender      int
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
     * @param participant_date_of_birth      Date
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
     * @param participant_age_category      int
     *
     */
    
    public void setParticipantAgeCategory(int participant_age_category) {
        this.participant_age_category = participant_age_category;
    }
    
    
    /**
     *
     * Gets the <code>participant_age_category</code> field
     *
     * @returns participant_age_category
     *
     */
    
    public int getParticipantAgeCategory() {
        return participant_age_category;
    }

    
    /**
     *
     * Sets the <code>participant_t_shirt_size</code> field
     *
     * @param participant_t_shirt_size      int
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
     * @param participant_blood_group      int
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
     * @param participant_cell_phone      String
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
     * @param participant_email      String
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
            participant_age_category == other.getParticipantAgeCategory() &&
            participant_t_shirt_size == other.getParticipantTShirtSize() &&
            participant_blood_group == other.getParticipantBloodGroup() &&
            Util.trim(participant_cell_phone).equals(Util.trim(other.getParticipantCellPhone())) &&
            Util.trim(participant_email).equals(Util.trim(other.getParticipantEmail()));
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
