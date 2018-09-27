/*
 * GenderObject.java
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
 * The implementation of the GenderObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class GenderObject implements Cloneable {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
	private int gender_id;
	private String gender_name;
	
	/**
	 *
	 * Returns the String representation of the GenderObject.
	 *
	 * @return	 Returns the String representation of the GenderObject.
	 *
	 */
	
	public String toString() {
		String buf="";
		buf += "gender_id : " + gender_id + "\n";
		buf += "gender_name : " + gender_name + "\n";
		return buf;
	}

	/**
	 *
	 * Returns the List representation of the GenderObject.
	 *
	 * Returns the object as an array list of Strings.
	 *
	 */
	
	public List<Object> asList() {
		List<Object> list = new ArrayList<Object>();
		list.add(gender_id + "");
		list.add(gender_name + "");
		return list;
	}

	/**
	 *
	 * Returns the JSON representation of the GenderObject.
	 *
	 * @return  	Returns the JSON representation of the GenderObject.
	 *
	 */
	
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("gender_id", gender_id);
			jo.put("gender_name", gender_name);
		} catch (JSONException je) {}
		return jo;
	}
	
	/**
	 *
	 * Returns the hashCode representation of the GenderObject.
	 *
	 * @return	Returns the hashCode.
	 *
	*/
	
	public int hashCode() {
		return gender_id;
	}
	
	/**
	 * Constructs the GenderObject
	 *
	 */
	
	public GenderObject () {
		setGenderId(0);
		setGenderName("");
	}
	
	/**
	 * Constructs the GenderObject from JSONObject
	 *
	 */
	
	public GenderObject (JSONObject jObject) {
		try {
			gender_id = jObject.getInt("gender_id");
		} catch (JSONException je) {}
		try {
			gender_name = jObject.getString("gender_name");
		} catch (JSONException je) {gender_name = "";}
	}
	
	
	/**
	 *
	 * Sets the <code>gender_id</code> field
	 *
	 * @param gender_id	  int
	 *
	 */
	
	public void setGenderId(int gender_id) {
		this.gender_id = gender_id;
	}
	
	
	/**
	 *
	 * Gets the <code>gender_id</code> field
	 *
	 * @returns gender_id
	 *
	 */
	
	public int getGenderId() {
		return gender_id;
	}

	
	/**
	 *
	 * Sets the <code>gender_name</code> field
	 *
	 * @param gender_name	  String
	 *
	 */
	
	public void setGenderName(String gender_name) {
		this.gender_name = gender_name;
	}
	
	
	/**
	 *
	 * Gets the <code>gender_name</code> field
	 *
	 * @returns gender_name
	 *
	 */
	
	public String getGenderName() {
		return gender_name;
	}

	
	/**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
	
	public boolean equals(Object obj) {
		GenderObject other = (GenderObject)obj;
		DebugHandler.finest("This: " + this);
		DebugHandler.finest("Other: " + other);
		return
			gender_id == other.getGenderId() &&
			Util.trim(gender_name).equals(Util.trim(other.getGenderName()));
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
