/*
 * RegistrationSourceObject.java
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
 * The implementation of the RegistrationSourceObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrationSourceObject implements Cloneable {
	private int registration_source_id;
	private String registration_source_name;
	
	/**
	 *
	 * Returns the String representation of the RegistrationSourceObject.
	 *
	 * @return	 Returns the String representation of the RegistrationSourceObject.
	 *
	 */
    
	public String toString() {
	   return	"registration_source_id : " + registration_source_id + "\n" +
		"registration_source_name : " + registration_source_name + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the RegistrationSourceObject.
	 *
	 * @return      Returns the JSON representation of the RegistrationSourceObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("registration_source_id", registration_source_id);
			 jo.put("registration_source_name", registration_source_name);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the RegistrationSourceObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return registration_source_id;
	}
    
	/**
	 * Constructs the RegistrationSourceObject
	 *
	 */
    
	public RegistrationSourceObject () {
		setRegistrationSourceId(0);
		setRegistrationSourceName("");
	}
    
	/**
	 * Constructs the RegistrationSourceObject from JSONObject
	 *
	 */
    
	public RegistrationSourceObject (JSONObject jObject) {
		try {
			registration_source_id = jObject.getInt("registration_source_id");
		} catch (JSONException je) {}
		try {
			registration_source_name = jObject.getString("registration_source_name");
		} catch (JSONException je) {registration_source_name = "";}
	}
    
	
    /**
	 *
	 * Sets the <code>registration_source_id</code> field
	 *
	 * @param registration_source_id      int
	 *
	 */
    
	public void setRegistrationSourceId(int registration_source_id) {
	    this.registration_source_id = registration_source_id;
	}
    
	
    /**
	 *
	 * Gets the <code>registration_source_id</code> field
	 *
	 * @returns registration_source_id
	 *
	 */
    
	public int getRegistrationSourceId() {
	    return registration_source_id;
	}

	
    /**
	 *
	 * Sets the <code>registration_source_name</code> field
	 *
	 * @param registration_source_name      String
	 *
	 */
    
	public void setRegistrationSourceName(String registration_source_name) {
	    this.registration_source_name = registration_source_name;
	}
    
	
    /**
	 *
	 * Gets the <code>registration_source_name</code> field
	 *
	 * @returns registration_source_name
	 *
	 */
    
	public String getRegistrationSourceName() {
	    return registration_source_name;
	}

	
    /**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
    
	public boolean equals(Object obj) {
	    RegistrationSourceObject other = (RegistrationSourceObject)obj;
	    DebugHandler.finest("This: " + this);
	    DebugHandler.finest("Other: " + other);
	    return
	        registration_source_id == other.getRegistrationSourceId() &&
	        Util.trim(registration_source_name).equals(Util.trim(other.getRegistrationSourceName()));
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
