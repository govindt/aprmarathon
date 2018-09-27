/*
 * RegistrationTypeObject.java
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
 * The implementation of the RegistrationTypeObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrationTypeObject implements Cloneable {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
	private int registration_type_id;
	private String registration_type_name;
	
	/**
	 *
	 * Returns the String representation of the RegistrationTypeObject.
	 *
	 * @return	 Returns the String representation of the RegistrationTypeObject.
	 *
	 */
	
	public String toString() {
		String buf="";
		buf += "registration_type_id : " + registration_type_id + "\n";
		buf += "registration_type_name : " + registration_type_name + "\n";
		return buf;
	}

	/**
	 *
	 * Returns the List representation of the RegistrationTypeObject.
	 *
	 * Returns the object as an array list of Strings.
	 *
	 */
	
	public List<Object> asList() {
		List<Object> list = new ArrayList<Object>();
		list.add(registration_type_id + "");
		list.add(registration_type_name + "");
		return list;
	}

	/**
	 *
	 * Returns the JSON representation of the RegistrationTypeObject.
	 *
	 * @return  	Returns the JSON representation of the RegistrationTypeObject.
	 *
	 */
	
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("registration_type_id", registration_type_id);
			jo.put("registration_type_name", registration_type_name);
		} catch (JSONException je) {}
		return jo;
	}
	
	/**
	 *
	 * Returns the hashCode representation of the RegistrationTypeObject.
	 *
	 * @return	Returns the hashCode.
	 *
	*/
	
	public int hashCode() {
		return registration_type_id;
	}
	
	/**
	 * Constructs the RegistrationTypeObject
	 *
	 */
	
	public RegistrationTypeObject () {
		setRegistrationTypeId(0);
		setRegistrationTypeName("");
	}
	
	/**
	 * Constructs the RegistrationTypeObject from JSONObject
	 *
	 */
	
	public RegistrationTypeObject (JSONObject jObject) {
		try {
			registration_type_id = jObject.getInt("registration_type_id");
		} catch (JSONException je) {}
		try {
			registration_type_name = jObject.getString("registration_type_name");
		} catch (JSONException je) {registration_type_name = "";}
	}
	
	
	/**
	 *
	 * Sets the <code>registration_type_id</code> field
	 *
	 * @param registration_type_id	  int
	 *
	 */
	
	public void setRegistrationTypeId(int registration_type_id) {
		this.registration_type_id = registration_type_id;
	}
	
	
	/**
	 *
	 * Gets the <code>registration_type_id</code> field
	 *
	 * @returns registration_type_id
	 *
	 */
	
	public int getRegistrationTypeId() {
		return registration_type_id;
	}

	
	/**
	 *
	 * Sets the <code>registration_type_name</code> field
	 *
	 * @param registration_type_name	  String
	 *
	 */
	
	public void setRegistrationTypeName(String registration_type_name) {
		this.registration_type_name = registration_type_name;
	}
	
	
	/**
	 *
	 * Gets the <code>registration_type_name</code> field
	 *
	 * @returns registration_type_name
	 *
	 */
	
	public String getRegistrationTypeName() {
		return registration_type_name;
	}

	
	/**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
	
	public boolean equals(Object obj) {
		RegistrationTypeObject other = (RegistrationTypeObject)obj;
		DebugHandler.finest("This: " + this);
		DebugHandler.finest("Other: " + other);
		return
			registration_type_id == other.getRegistrationTypeId() &&
			Util.trim(registration_type_name).equals(Util.trim(other.getRegistrationTypeName()));
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
