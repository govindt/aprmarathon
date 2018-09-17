/*
 * AgeCategoryObject.java
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
 * The implementation of the AgeCategoryObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class AgeCategoryObject implements Cloneable {
	private int age_category_id;
	private String age_category;
	
	/**
	 *
	 * Returns the String representation of the AgeCategoryObject.
	 *
	 * @return	 Returns the String representation of the AgeCategoryObject.
	 *
	 */
    
	public String toString() {
	   return	"age_category_id : " + age_category_id + "\n" +
		"age_category : " + age_category + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the AgeCategoryObject.
	 *
	 * @return      Returns the JSON representation of the AgeCategoryObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("age_category_id", age_category_id);
			 jo.put("age_category", age_category);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the AgeCategoryObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return age_category_id;
	}
    
	/**
	 * Constructs the AgeCategoryObject
	 *
	 */
    
	public AgeCategoryObject () {
		setAgeCategoryId(0);
		setAgeCategory("");
	}
    
	/**
	 * Constructs the AgeCategoryObject from JSONObject
	 *
	 */
    
	public AgeCategoryObject (JSONObject jObject) {
		try {
			age_category_id = jObject.getInt("age_category_id");
		} catch (JSONException je) {}
		try {
			age_category = jObject.getString("age_category");
		} catch (JSONException je) {}
	}
    
	
    /**
	 *
	 * Sets the <code>age_category_id</code> field
	 *
	 * @param age_category_id      int
	 *
	 */
    
	public void setAgeCategoryId(int age_category_id) {
	    this.age_category_id = age_category_id;
	}
    
	
    /**
	 *
	 * Gets the <code>age_category_id</code> field
	 *
	 * @returns age_category_id
	 *
	 */
    
	public int getAgeCategoryId() {
	    return age_category_id;
	}

	
    /**
	 *
	 * Sets the <code>age_category</code> field
	 *
	 * @param age_category      String
	 *
	 */
    
	public void setAgeCategory(String age_category) {
	    this.age_category = age_category;
	}
    
	
    /**
	 *
	 * Gets the <code>age_category</code> field
	 *
	 * @returns age_category
	 *
	 */
    
	public String getAgeCategory() {
	    return age_category;
	}

	
    /**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
    
	public boolean equals(Object obj) {
	    AgeCategoryObject other = (AgeCategoryObject)obj;
	    DebugHandler.finest("This: " + this);
	    DebugHandler.finest("Other: " + other);
	    return
	        age_category_id == other.getAgeCategoryId() &&
	        Util.trim(age_category).equals(Util.trim(other.getAgeCategory()));
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
