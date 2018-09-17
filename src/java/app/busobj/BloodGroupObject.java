/*
 * BloodGroupObject.java
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
 * The implementation of the BloodGroupObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class BloodGroupObject implements Cloneable {
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
	   return	"blood_group_id : " + blood_group_id + "\n" +
		"blood_group_name : " + blood_group_name + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the BloodGroupObject.
	 *
	 * @return      Returns the JSON representation of the BloodGroupObject.
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
	 * @return      Returns the hashCode.
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
		} catch (JSONException je) {}
	}
    
	
    /**
	 *
	 * Sets the <code>blood_group_id</code> field
	 *
	 * @param blood_group_id      int
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
	 * @param blood_group_name      String
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
