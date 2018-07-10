/*
 * RegistrantEventObject.java
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
 * The implementation of the RegistrantEventObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrantEventObject implements Cloneable {
	private int registrant_event_id;
	private int registrant_id;
	private int registrant_event;
	private int registrant_type;
	private int registrant_source;
	private int registrant_class;
	private int registrant_beneficiary;
	private String registrant_emergency_contact;
	private String registrant_emergency_phone;
	
	/**
	 *
	 * Returns the String representation of the RegistrantEventObject.
	 *
	 * @return	 Returns the String representation of the RegistrantEventObject.
	 *
	 */
    
	public String toString() {
	   return	"registrant_event_id : " + registrant_event_id + "\n" +
		"registrant_id : " + registrant_id + "\n" +
		"registrant_event : " + registrant_event + "\n" +
		"registrant_type : " + registrant_type + "\n" +
		"registrant_source : " + registrant_source + "\n" +
		"registrant_class : " + registrant_class + "\n" +
		"registrant_beneficiary : " + registrant_beneficiary + "\n" +
		"registrant_emergency_contact : " + registrant_emergency_contact + "\n" +
		"registrant_emergency_phone : " + registrant_emergency_phone + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the RegistrantEventObject.
	 *
	 * @return      Returns the JSON representation of the RegistrantEventObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("registrant_event_id", registrant_event_id);
			 jo.put("registrant_id", registrant_id);
			 jo.put("registrant_event", registrant_event);
			 jo.put("registrant_type", registrant_type);
			 jo.put("registrant_source", registrant_source);
			 jo.put("registrant_class", registrant_class);
			 jo.put("registrant_beneficiary", registrant_beneficiary);
			 jo.put("registrant_emergency_contact", registrant_emergency_contact);
			 jo.put("registrant_emergency_phone", registrant_emergency_phone);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the RegistrantEventObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return registrant_event_id;
	}
    
	/**
	 * Constructs the RegistrantEventObject
	 *
	 */
    
	public RegistrantEventObject () {
		setRegistrantEventId(0);
		setRegistrantId(0);
		setRegistrantEvent(0);
		setRegistrantType(0);
		setRegistrantSource(0);
		setRegistrantClass(0);
		setRegistrantBeneficiary(0);
		setRegistrantEmergencyContact("");
		setRegistrantEmergencyPhone("");
	}
    
	/**
	 * Constructs the RegistrantEventObject from JSONObject
	 *
	 */
    
	public RegistrantEventObject (JSONObject jObject) {
		try {
			registrant_event_id = jObject.getInt("registrant_event_id");
		} catch (JSONException je) {}
		try {
			registrant_id = jObject.getInt("registrant_id");
		} catch (JSONException je) {}
		try {
			registrant_event = jObject.getInt("registrant_event");
		} catch (JSONException je) {}
		try {
			registrant_type = jObject.getInt("registrant_type");
		} catch (JSONException je) {}
		try {
			registrant_source = jObject.getInt("registrant_source");
		} catch (JSONException je) {}
		try {
			registrant_class = jObject.getInt("registrant_class");
		} catch (JSONException je) {}
		try {
			registrant_beneficiary = jObject.getInt("registrant_beneficiary");
		} catch (JSONException je) {}
		try {
			registrant_emergency_contact = jObject.getString("registrant_emergency_contact");
		} catch (JSONException je) {}
		try {
			registrant_emergency_phone = jObject.getString("registrant_emergency_phone");
		} catch (JSONException je) {}
	}
    
    
    /**
     *
     * Sets the <code>registrant_event_id</code> field
     *
     * @param registrant_event_id      int
     *
     */
    
    public void setRegistrantEventId(int registrant_event_id) {
        this.registrant_event_id = registrant_event_id;
    }
    
    
    /**
     *
     * Gets the <code>registrant_event_id</code> field
     *
     * @returns registrant_event_id
     *
     */
    
    public int getRegistrantEventId() {
        return registrant_event_id;
    }

    
    /**
     *
     * Sets the <code>registrant_id</code> field
     *
     * @param registrant_id      int
     *
     */
    
    public void setRegistrantId(int registrant_id) {
        this.registrant_id = registrant_id;
    }
    
    
    /**
     *
     * Gets the <code>registrant_id</code> field
     *
     * @returns registrant_id
     *
     */
    
    public int getRegistrantId() {
        return registrant_id;
    }

    
    /**
     *
     * Sets the <code>registrant_event</code> field
     *
     * @param registrant_event      int
     *
     */
    
    public void setRegistrantEvent(int registrant_event) {
        this.registrant_event = registrant_event;
    }
    
    
    /**
     *
     * Gets the <code>registrant_event</code> field
     *
     * @returns registrant_event
     *
     */
    
    public int getRegistrantEvent() {
        return registrant_event;
    }

    
    /**
     *
     * Sets the <code>registrant_type</code> field
     *
     * @param registrant_type      int
     *
     */
    
    public void setRegistrantType(int registrant_type) {
        this.registrant_type = registrant_type;
    }
    
    
    /**
     *
     * Gets the <code>registrant_type</code> field
     *
     * @returns registrant_type
     *
     */
    
    public int getRegistrantType() {
        return registrant_type;
    }

    
    /**
     *
     * Sets the <code>registrant_source</code> field
     *
     * @param registrant_source      int
     *
     */
    
    public void setRegistrantSource(int registrant_source) {
        this.registrant_source = registrant_source;
    }
    
    
    /**
     *
     * Gets the <code>registrant_source</code> field
     *
     * @returns registrant_source
     *
     */
    
    public int getRegistrantSource() {
        return registrant_source;
    }

    
    /**
     *
     * Sets the <code>registrant_class</code> field
     *
     * @param registrant_class      int
     *
     */
    
    public void setRegistrantClass(int registrant_class) {
        this.registrant_class = registrant_class;
    }
    
    
    /**
     *
     * Gets the <code>registrant_class</code> field
     *
     * @returns registrant_class
     *
     */
    
    public int getRegistrantClass() {
        return registrant_class;
    }

    
    /**
     *
     * Sets the <code>registrant_beneficiary</code> field
     *
     * @param registrant_beneficiary      int
     *
     */
    
    public void setRegistrantBeneficiary(int registrant_beneficiary) {
        this.registrant_beneficiary = registrant_beneficiary;
    }
    
    
    /**
     *
     * Gets the <code>registrant_beneficiary</code> field
     *
     * @returns registrant_beneficiary
     *
     */
    
    public int getRegistrantBeneficiary() {
        return registrant_beneficiary;
    }

    
    /**
     *
     * Sets the <code>registrant_emergency_contact</code> field
     *
     * @param registrant_emergency_contact      String
     *
     */
    
    public void setRegistrantEmergencyContact(String registrant_emergency_contact) {
        this.registrant_emergency_contact = registrant_emergency_contact;
    }
    
    
    /**
     *
     * Gets the <code>registrant_emergency_contact</code> field
     *
     * @returns registrant_emergency_contact
     *
     */
    
    public String getRegistrantEmergencyContact() {
        return registrant_emergency_contact;
    }

    
    /**
     *
     * Sets the <code>registrant_emergency_phone</code> field
     *
     * @param registrant_emergency_phone      String
     *
     */
    
    public void setRegistrantEmergencyPhone(String registrant_emergency_phone) {
        this.registrant_emergency_phone = registrant_emergency_phone;
    }
    
    
    /**
     *
     * Gets the <code>registrant_emergency_phone</code> field
     *
     * @returns registrant_emergency_phone
     *
     */
    
    public String getRegistrantEmergencyPhone() {
        return registrant_emergency_phone;
    }

    
    /**
     *
     * Tests if this object equals <code>obj</code>
     *
     * @returns true if equals
     *
     */
    
    public boolean equals(Object obj) {
        RegistrantEventObject other = (RegistrantEventObject)obj;
        DebugHandler.finest("This: " + this);
        DebugHandler.finest("Other: " + other);
        return
            registrant_event_id == other.getRegistrantEventId() &&
            registrant_id == other.getRegistrantId() &&
            registrant_event == other.getRegistrantEvent() &&
            registrant_type == other.getRegistrantType() &&
            registrant_source == other.getRegistrantSource() &&
            registrant_class == other.getRegistrantClass() &&
            registrant_beneficiary == other.getRegistrantBeneficiary() &&
            Util.trim(registrant_emergency_contact).equals(Util.trim(other.getRegistrantEmergencyContact())) &&
            Util.trim(registrant_emergency_phone).equals(Util.trim(other.getRegistrantEmergencyPhone()));
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
