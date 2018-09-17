/*
 * RegistrationClassObject.java
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
 * The implementation of the RegistrationClassObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrationClassObject implements Cloneable {
	private int registration_class_id;
	private String registration_class_name;
	private int registration_type;
	private int registration_event;
	private double registration_class_value;
	private int registration_free_tickets;
	
	/**
	 *
	 * Returns the String representation of the RegistrationClassObject.
	 *
	 * @return	 Returns the String representation of the RegistrationClassObject.
	 *
	 */
    
	public String toString() {
	   return	"registration_class_id : " + registration_class_id + "\n" +
		"registration_class_name : " + registration_class_name + "\n" +
		"registration_type : " + registration_type + "\n" +
		"registration_event : " + registration_event + "\n" +
		"registration_class_value : " + registration_class_value + "\n" +
		"registration_free_tickets : " + registration_free_tickets + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the RegistrationClassObject.
	 *
	 * @return      Returns the JSON representation of the RegistrationClassObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("registration_class_id", registration_class_id);
			 jo.put("registration_class_name", registration_class_name);
			 jo.put("registration_type", registration_type);
			 jo.put("registration_event", registration_event);
			 jo.put("registration_class_value", registration_class_value);
			 jo.put("registration_free_tickets", registration_free_tickets);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the RegistrationClassObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return registration_class_id;
	}
    
	/**
	 * Constructs the RegistrationClassObject
	 *
	 */
    
	public RegistrationClassObject () {
		setRegistrationClassId(0);
		setRegistrationClassName("");
		setRegistrationType(0);
		setRegistrationEvent(0);
		setRegistrationClassValue(0.0);
		setRegistrationFreeTickets(0);
	}
    
	/**
	 * Constructs the RegistrationClassObject from JSONObject
	 *
	 */
    
	public RegistrationClassObject (JSONObject jObject) {
		try {
			registration_class_id = jObject.getInt("registration_class_id");
		} catch (JSONException je) {}
		try {
			registration_class_name = jObject.getString("registration_class_name");
		} catch (JSONException je) {}
		try {
			registration_type = jObject.getInt("registration_type");
		} catch (JSONException je) {}
		try {
			registration_event = jObject.getInt("registration_event");
		} catch (JSONException je) {}
		try {
			try {
				registration_class_value = Double.parseDouble(jObject.getString("registration_class_value"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			registration_free_tickets = jObject.getInt("registration_free_tickets");
		} catch (JSONException je) {}
	}
    
	
    /**
	 *
	 * Sets the <code>registration_class_id</code> field
	 *
	 * @param registration_class_id      int
	 *
	 */
    
	public void setRegistrationClassId(int registration_class_id) {
	    this.registration_class_id = registration_class_id;
	}
    
	
    /**
	 *
	 * Gets the <code>registration_class_id</code> field
	 *
	 * @returns registration_class_id
	 *
	 */
    
	public int getRegistrationClassId() {
	    return registration_class_id;
	}

	
    /**
	 *
	 * Sets the <code>registration_class_name</code> field
	 *
	 * @param registration_class_name      String
	 *
	 */
    
	public void setRegistrationClassName(String registration_class_name) {
	    this.registration_class_name = registration_class_name;
	}
    
	
    /**
	 *
	 * Gets the <code>registration_class_name</code> field
	 *
	 * @returns registration_class_name
	 *
	 */
    
	public String getRegistrationClassName() {
	    return registration_class_name;
	}

	
    /**
	 *
	 * Sets the <code>registration_type</code> field
	 *
	 * @param registration_type      int
	 *
	 */
    
	public void setRegistrationType(int registration_type) {
	    this.registration_type = registration_type;
	}
    
	
    /**
	 *
	 * Gets the <code>registration_type</code> field
	 *
	 * @returns registration_type
	 *
	 */
    
	public int getRegistrationType() {
	    return registration_type;
	}

	
    /**
	 *
	 * Sets the <code>registration_event</code> field
	 *
	 * @param registration_event      int
	 *
	 */
    
	public void setRegistrationEvent(int registration_event) {
	    this.registration_event = registration_event;
	}
    
	
    /**
	 *
	 * Gets the <code>registration_event</code> field
	 *
	 * @returns registration_event
	 *
	 */
    
	public int getRegistrationEvent() {
	    return registration_event;
	}

	
    /**
	 *
	 * Sets the <code>registration_class_value</code> field
	 *
	 * @param registration_class_value      double
	 *
	 */
    
	public void setRegistrationClassValue(double registration_class_value) {
	    this.registration_class_value = registration_class_value;
	}
    
	
    /**
	 *
	 * Gets the <code>registration_class_value</code> field
	 *
	 * @returns registration_class_value
	 *
	 */
    
	public double getRegistrationClassValue() {
	    return registration_class_value;
	}

	
    /**
	 *
	 * Sets the <code>registration_free_tickets</code> field
	 *
	 * @param registration_free_tickets      int
	 *
	 */
    
	public void setRegistrationFreeTickets(int registration_free_tickets) {
	    this.registration_free_tickets = registration_free_tickets;
	}
    
	
    /**
	 *
	 * Gets the <code>registration_free_tickets</code> field
	 *
	 * @returns registration_free_tickets
	 *
	 */
    
	public int getRegistrationFreeTickets() {
	    return registration_free_tickets;
	}

	
    /**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
    
	public boolean equals(Object obj) {
	    RegistrationClassObject other = (RegistrationClassObject)obj;
	    DebugHandler.finest("This: " + this);
	    DebugHandler.finest("Other: " + other);
	    return
	        registration_class_id == other.getRegistrationClassId() &&
	   Util.trim(registration_class_name).equals(Util.trim(other.getRegistrationClassName())) &&
	        registration_type == other.getRegistrationType() &&
	        registration_event == other.getRegistrationEvent() &&
	        registration_class_value == other.getRegistrationClassValue() &&
	        registration_free_tickets == other.getRegistrationFreeTickets();
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
