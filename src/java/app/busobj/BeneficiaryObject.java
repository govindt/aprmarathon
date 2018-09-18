/*
 * BeneficiaryObject.java
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
 * The implementation of the BeneficiaryObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class BeneficiaryObject implements Cloneable {
	private int beneficiary_id;
	private String beneficiary_name;
	private int beneficiary_event;
	
	/**
	 *
	 * Returns the String representation of the BeneficiaryObject.
	 *
	 * @return	 Returns the String representation of the BeneficiaryObject.
	 *
	 */
    
	public String toString() {
	   return	"beneficiary_id : " + beneficiary_id + "\n" +
		"beneficiary_name : " + beneficiary_name + "\n" +
		"beneficiary_event : " + beneficiary_event + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the BeneficiaryObject.
	 *
	 * @return      Returns the JSON representation of the BeneficiaryObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("beneficiary_id", beneficiary_id);
			 jo.put("beneficiary_name", beneficiary_name);
			 jo.put("beneficiary_event", beneficiary_event);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the BeneficiaryObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return beneficiary_id;
	}
    
	/**
	 * Constructs the BeneficiaryObject
	 *
	 */
    
	public BeneficiaryObject () {
		setBeneficiaryId(0);
		setBeneficiaryName("");
		setBeneficiaryEvent(0);
	}
    
	/**
	 * Constructs the BeneficiaryObject from JSONObject
	 *
	 */
    
	public BeneficiaryObject (JSONObject jObject) {
		try {
			beneficiary_id = jObject.getInt("beneficiary_id");
		} catch (JSONException je) {}
		try {
			beneficiary_name = jObject.getString("beneficiary_name");
		} catch (JSONException je) {beneficiary_name = "";}
		try {
			beneficiary_event = jObject.getInt("beneficiary_event");
		} catch (JSONException je) {}
	}
    
	
    /**
	 *
	 * Sets the <code>beneficiary_id</code> field
	 *
	 * @param beneficiary_id      int
	 *
	 */
    
	public void setBeneficiaryId(int beneficiary_id) {
	    this.beneficiary_id = beneficiary_id;
	}
    
	
    /**
	 *
	 * Gets the <code>beneficiary_id</code> field
	 *
	 * @returns beneficiary_id
	 *
	 */
    
	public int getBeneficiaryId() {
	    return beneficiary_id;
	}

	
    /**
	 *
	 * Sets the <code>beneficiary_name</code> field
	 *
	 * @param beneficiary_name      String
	 *
	 */
    
	public void setBeneficiaryName(String beneficiary_name) {
	    this.beneficiary_name = beneficiary_name;
	}
    
	
    /**
	 *
	 * Gets the <code>beneficiary_name</code> field
	 *
	 * @returns beneficiary_name
	 *
	 */
    
	public String getBeneficiaryName() {
	    return beneficiary_name;
	}

	
    /**
	 *
	 * Sets the <code>beneficiary_event</code> field
	 *
	 * @param beneficiary_event      int
	 *
	 */
    
	public void setBeneficiaryEvent(int beneficiary_event) {
	    this.beneficiary_event = beneficiary_event;
	}
    
	
    /**
	 *
	 * Gets the <code>beneficiary_event</code> field
	 *
	 * @returns beneficiary_event
	 *
	 */
    
	public int getBeneficiaryEvent() {
	    return beneficiary_event;
	}

	
    /**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
    
	public boolean equals(Object obj) {
	    BeneficiaryObject other = (BeneficiaryObject)obj;
	    DebugHandler.finest("This: " + this);
	    DebugHandler.finest("Other: " + other);
	    return
	        beneficiary_id == other.getBeneficiaryId() &&
	   Util.trim(beneficiary_name).equals(Util.trim(other.getBeneficiaryName())) &&
	        beneficiary_event == other.getBeneficiaryEvent();
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
