/*
 * MedalObject.java
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
 * The implementation of the MedalObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class MedalObject implements Cloneable {
	private int medal_id;
	private String medal_name;
	private int medal_rank;
	
	/**
	 *
	 * Returns the String representation of the MedalObject.
	 *
	 * @return	 Returns the String representation of the MedalObject.
	 *
	 */
    
	public String toString() {
	   return	"medal_id : " + medal_id + "\n" +
		"medal_name : " + medal_name + "\n" +
		"medal_rank : " + medal_rank + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the MedalObject.
	 *
	 * @return      Returns the JSON representation of the MedalObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("medal_id", medal_id);
			 jo.put("medal_name", medal_name);
			 jo.put("medal_rank", medal_rank);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the MedalObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return medal_id;
	}
    
	/**
	 * Constructs the MedalObject
	 *
	 */
    
	public MedalObject () {
		setMedalId(0);
		setMedalName("");
		setMedalRank(0);
	}
    
	/**
	 * Constructs the MedalObject from JSONObject
	 *
	 */
    
	public MedalObject (JSONObject jObject) {
		try {
			medal_id = jObject.getInt("medal_id");
		} catch (JSONException je) {medal_id = 0;}
		try {
			medal_name = jObject.getString("medal_name");
		} catch (JSONException je) {medal_name = "";}
		try {
			medal_rank = jObject.getInt("medal_rank");
		} catch (JSONException je) {medal_rank = 0;}
	}
    
	
    /**
	 *
	 * Sets the <code>medal_id</code> field
	 *
	 * @param medal_id      int
	 *
	 */
    
	public void setMedalId(int medal_id) {
	    this.medal_id = medal_id;
	}
    
	
    /**
	 *
	 * Gets the <code>medal_id</code> field
	 *
	 * @returns medal_id
	 *
	 */
    
	public int getMedalId() {
	    return medal_id;
	}

	
    /**
	 *
	 * Sets the <code>medal_name</code> field
	 *
	 * @param medal_name      String
	 *
	 */
    
	public void setMedalName(String medal_name) {
	    this.medal_name = medal_name;
	}
    
	
    /**
	 *
	 * Gets the <code>medal_name</code> field
	 *
	 * @returns medal_name
	 *
	 */
    
	public String getMedalName() {
	    return medal_name;
	}

	
    /**
	 *
	 * Sets the <code>medal_rank</code> field
	 *
	 * @param medal_rank      int
	 *
	 */
    
	public void setMedalRank(int medal_rank) {
	    this.medal_rank = medal_rank;
	}
    
	
    /**
	 *
	 * Gets the <code>medal_rank</code> field
	 *
	 * @returns medal_rank
	 *
	 */
    
	public int getMedalRank() {
	    return medal_rank;
	}

	
    /**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
    
	public boolean equals(Object obj) {
	    MedalObject other = (MedalObject)obj;
	    DebugHandler.finest("This: " + this);
	    DebugHandler.finest("Other: " + other);
	    return
	        medal_id == other.getMedalId() &&
	   Util.trim(medal_name).equals(Util.trim(other.getMedalName())) &&
	        medal_rank == other.getMedalRank();
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
