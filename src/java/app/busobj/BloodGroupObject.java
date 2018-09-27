/*
 * BloodGroupObject.java
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
 * The implementation of the BloodGroupObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class BloodGroupObject implements Cloneable {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
	private int blood_group_id;
	private String blood_group_name;
	
	/**
	 *
	 * Returns the String representation of the BloodGroupObject.
	 *
	 * @return	 Returns the String representation of the BloodGroupObject.
	 *
	 */
	
	public String toString() {
		String buf="";
		buf += "blood_group_id : " + blood_group_id + "\n";
		buf += "blood_group_name : " + blood_group_name + "\n";
		return buf;
	}

	/**
	 *
	 * Returns the List representation of the BloodGroupObject.
	 *
	 * Returns the object as an array list of Strings.
	 *
	 */
	
	public List<Object> asList() {
		List<Object> list = new ArrayList<Object>();
		list.add(blood_group_id + "");
		list.add(blood_group_name + "");
		return list;
	}

	/**
	 *
	 * Returns the JSON representation of the BloodGroupObject.
	 *
	 * @return  	Returns the JSON representation of the BloodGroupObject.
	 *
	 */
	
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("blood_group_id", blood_group_id);
			jo.put("blood_group_name", blood_group_name);
		} catch (JSONException je) {}
		return jo;
	}
	
	/**
	 *
	 * Returns the hashCode representation of the BloodGroupObject.
	 *
	 * @return	Returns the hashCode.
	 *
	*/
	
	public int hashCode() {
		return blood_group_id;
	}
	
	/**
	 * Constructs the BloodGroupObject
	 *
	 */
	
	public BloodGroupObject () {
		setBloodGroupId(0);
		setBloodGroupName("");
	}
	
	/**
	 * Constructs the BloodGroupObject from JSONObject
	 *
	 */
	
	public BloodGroupObject (JSONObject jObject) {
		try {
			blood_group_id = jObject.getInt("blood_group_id");
		} catch (JSONException je) {}
		try {
			blood_group_name = jObject.getString("blood_group_name");
		} catch (JSONException je) {blood_group_name = "";}
	}
	
	
	/**
	 *
	 * Sets the <code>blood_group_id</code> field
	 *
	 * @param blood_group_id	  int
	 *
	 */
	
	public void setBloodGroupId(int blood_group_id) {
		this.blood_group_id = blood_group_id;
	}
	
	
	/**
	 *
	 * Gets the <code>blood_group_id</code> field
	 *
	 * @returns blood_group_id
	 *
	 */
	
	public int getBloodGroupId() {
		return blood_group_id;
	}

	
	/**
	 *
	 * Sets the <code>blood_group_name</code> field
	 *
	 * @param blood_group_name	  String
	 *
	 */
	
	public void setBloodGroupName(String blood_group_name) {
		this.blood_group_name = blood_group_name;
	}
	
	
	/**
	 *
	 * Gets the <code>blood_group_name</code> field
	 *
	 * @returns blood_group_name
	 *
	 */
	
	public String getBloodGroupName() {
		return blood_group_name;
	}

	
	/**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
	
	public boolean equals(Object obj) {
		BloodGroupObject other = (BloodGroupObject)obj;
		DebugHandler.finest("This: " + this);
		DebugHandler.finest("Other: " + other);
		return
			blood_group_id == other.getBloodGroupId() &&
			Util.trim(blood_group_name).equals(Util.trim(other.getBloodGroupName()));
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
