/*
 * TShirtSizeObject.java
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
 * The implementation of the TShirtSizeObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class TShirtSizeObject implements Cloneable {
	private int t_shirt_size_id;
	private String t_shirt_size_name;
	private int t_shirt_gender;
	
	/**
	 *
	 * Returns the String representation of the TShirtSizeObject.
	 *
	 * @return	 Returns the String representation of the TShirtSizeObject.
	 *
	 */
    
	public String toString() {
	   return	"t_shirt_size_id : " + t_shirt_size_id + "\n" +
		"t_shirt_size_name : " + t_shirt_size_name + "\n" +
		"t_shirt_gender : " + t_shirt_gender + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the TShirtSizeObject.
	 *
	 * @return      Returns the JSON representation of the TShirtSizeObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("t_shirt_size_id", t_shirt_size_id);
			 jo.put("t_shirt_size_name", t_shirt_size_name);
			 jo.put("t_shirt_gender", t_shirt_gender);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the TShirtSizeObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return t_shirt_size_id;
	}
    
	/**
	 * Constructs the TShirtSizeObject
	 *
	 */
    
	public TShirtSizeObject () {
		setTShirtSizeId(0);
		setTShirtSizeName("");
		setTShirtGender(0);
	}
    
	/**
	 * Constructs the TShirtSizeObject from JSONObject
	 *
	 */
    
	public TShirtSizeObject (JSONObject jObject) {
		try {
			t_shirt_size_id = jObject.getInt("t_shirt_size_id");
		} catch (JSONException je) {}
		try {
			t_shirt_size_name = jObject.getString("t_shirt_size_name");
		} catch (JSONException je) {t_shirt_size_name = "";}
		try {
			t_shirt_gender = jObject.getInt("t_shirt_gender");
		} catch (JSONException je) {}
	}
    
	
    /**
	 *
	 * Sets the <code>t_shirt_size_id</code> field
	 *
	 * @param t_shirt_size_id      int
	 *
	 */
    
	public void setTShirtSizeId(int t_shirt_size_id) {
	    this.t_shirt_size_id = t_shirt_size_id;
	}
    
	
    /**
	 *
	 * Gets the <code>t_shirt_size_id</code> field
	 *
	 * @returns t_shirt_size_id
	 *
	 */
    
	public int getTShirtSizeId() {
	    return t_shirt_size_id;
	}

	
    /**
	 *
	 * Sets the <code>t_shirt_size_name</code> field
	 *
	 * @param t_shirt_size_name      String
	 *
	 */
    
	public void setTShirtSizeName(String t_shirt_size_name) {
	    this.t_shirt_size_name = t_shirt_size_name;
	}
    
	
    /**
	 *
	 * Gets the <code>t_shirt_size_name</code> field
	 *
	 * @returns t_shirt_size_name
	 *
	 */
    
	public String getTShirtSizeName() {
	    return t_shirt_size_name;
	}

	
    /**
	 *
	 * Sets the <code>t_shirt_gender</code> field
	 *
	 * @param t_shirt_gender      int
	 *
	 */
    
	public void setTShirtGender(int t_shirt_gender) {
	    this.t_shirt_gender = t_shirt_gender;
	}
    
	
    /**
	 *
	 * Gets the <code>t_shirt_gender</code> field
	 *
	 * @returns t_shirt_gender
	 *
	 */
    
	public int getTShirtGender() {
	    return t_shirt_gender;
	}

	
    /**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
    
	public boolean equals(Object obj) {
	    TShirtSizeObject other = (TShirtSizeObject)obj;
	    DebugHandler.finest("This: " + this);
	    DebugHandler.finest("Other: " + other);
	    return
	        t_shirt_size_id == other.getTShirtSizeId() &&
	   Util.trim(t_shirt_size_name).equals(Util.trim(other.getTShirtSizeName())) &&
	        t_shirt_gender == other.getTShirtGender();
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
