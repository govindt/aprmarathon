/*
 * RegistrantObject.java
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
 * The implementation of the RegistrantObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrantObject implements Cloneable {
	private int registrant_id;
	private String registrant_name;
	private String registrant_middle_name;
	private String registrant_last_name;
	private String registrant_email;
	private String registrant_additional_email;
	private String registrant_phone;
	private String registrant_address;
	private String registrant_city;
	private String registrant_state;
	private String registrant_pincode;
	private String registrant_pan;
	
	/**
	 *
	 * Returns the String representation of the RegistrantObject.
	 *
	 * @return	 Returns the String representation of the RegistrantObject.
	 *
	 */
    
	public String toString() {
	   return	"registrant_id : " + registrant_id + "\n" +
		"registrant_name : " + registrant_name + "\n" +
		"registrant_middle_name : " + registrant_middle_name + "\n" +
		"registrant_last_name : " + registrant_last_name + "\n" +
		"registrant_email : " + registrant_email + "\n" +
		"registrant_additional_email : " + registrant_additional_email + "\n" +
		"registrant_phone : " + registrant_phone + "\n" +
		"registrant_address : " + registrant_address + "\n" +
		"registrant_city : " + registrant_city + "\n" +
		"registrant_state : " + registrant_state + "\n" +
		"registrant_pincode : " + registrant_pincode + "\n" +
		"registrant_pan : " + registrant_pan + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the RegistrantObject.
	 *
	 * @return      Returns the JSON representation of the RegistrantObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("registrant_id", registrant_id);
			 jo.put("registrant_name", registrant_name);
			 jo.put("registrant_middle_name", registrant_middle_name);
			 jo.put("registrant_last_name", registrant_last_name);
			 jo.put("registrant_email", registrant_email);
			 jo.put("registrant_additional_email", registrant_additional_email);
			 jo.put("registrant_phone", registrant_phone);
			 jo.put("registrant_address", registrant_address);
			 jo.put("registrant_city", registrant_city);
			 jo.put("registrant_state", registrant_state);
			 jo.put("registrant_pincode", registrant_pincode);
			 jo.put("registrant_pan", registrant_pan);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the RegistrantObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return registrant_id;
	}
    
	/**
	 * Constructs the RegistrantObject
	 *
	 */
    
	public RegistrantObject () {
		setRegistrantId(0);
		setRegistrantName("");
		setRegistrantMiddleName("");
		setRegistrantLastName("");
		setRegistrantEmail("");
		setRegistrantAdditionalEmail("");
		setRegistrantPhone("");
		setRegistrantAddress("");
		setRegistrantCity("");
		setRegistrantState("");
		setRegistrantPincode("");
		setRegistrantPan("");
	}
    
	/**
	 * Constructs the RegistrantObject from JSONObject
	 *
	 */
    
	public RegistrantObject (JSONObject jObject) {
		try {
			registrant_id = jObject.getInt("registrant_id");
		} catch (JSONException je) {}
		try {
			registrant_name = jObject.getString("registrant_name");
		} catch (JSONException je) {}
		try {
			registrant_middle_name = jObject.getString("registrant_middle_name");
		} catch (JSONException je) {}
		try {
			registrant_last_name = jObject.getString("registrant_last_name");
		} catch (JSONException je) {}
		try {
			registrant_email = jObject.getString("registrant_email");
		} catch (JSONException je) {}
		try {
			registrant_additional_email = jObject.getString("registrant_additional_email");
		} catch (JSONException je) {}
		try {
			registrant_phone = jObject.getString("registrant_phone");
		} catch (JSONException je) {}
		try {
			registrant_address = jObject.getString("registrant_address");
		} catch (JSONException je) {}
		try {
			registrant_city = jObject.getString("registrant_city");
		} catch (JSONException je) {}
		try {
			registrant_state = jObject.getString("registrant_state");
		} catch (JSONException je) {}
		try {
			registrant_pincode = jObject.getString("registrant_pincode");
		} catch (JSONException je) {}
		try {
			registrant_pan = jObject.getString("registrant_pan");
		} catch (JSONException je) {}
	}
    
	
    /**
	 *
	 * Sets the <code>registrant_id</code> field
	 *
	 * @param registrant_id      int
	 *
	 */
    
	public void setRegistrantId(int registrant_id) {
	    this.registrant_id = registrant_id;
	}
    
	
    /**
	 *
	 * Gets the <code>registrant_id</code> field
	 *
	 * @returns registrant_id
	 *
	 */
    
	public int getRegistrantId() {
	    return registrant_id;
	}

	
    /**
	 *
	 * Sets the <code>registrant_name</code> field
	 *
	 * @param registrant_name      String
	 *
	 */
    
	public void setRegistrantName(String registrant_name) {
	    this.registrant_name = registrant_name;
	}
    
	
    /**
	 *
	 * Gets the <code>registrant_name</code> field
	 *
	 * @returns registrant_name
	 *
	 */
    
	public String getRegistrantName() {
	    return registrant_name;
	}

	
    /**
	 *
	 * Sets the <code>registrant_middle_name</code> field
	 *
	 * @param registrant_middle_name      String
	 *
	 */
    
	public void setRegistrantMiddleName(String registrant_middle_name) {
	    this.registrant_middle_name = registrant_middle_name;
	}
    
	
    /**
	 *
	 * Gets the <code>registrant_middle_name</code> field
	 *
	 * @returns registrant_middle_name
	 *
	 */
    
	public String getRegistrantMiddleName() {
	    return registrant_middle_name;
	}

	
    /**
	 *
	 * Sets the <code>registrant_last_name</code> field
	 *
	 * @param registrant_last_name      String
	 *
	 */
    
	public void setRegistrantLastName(String registrant_last_name) {
	    this.registrant_last_name = registrant_last_name;
	}
    
	
    /**
	 *
	 * Gets the <code>registrant_last_name</code> field
	 *
	 * @returns registrant_last_name
	 *
	 */
    
	public String getRegistrantLastName() {
	    return registrant_last_name;
	}

	
    /**
	 *
	 * Sets the <code>registrant_email</code> field
	 *
	 * @param registrant_email      String
	 *
	 */
    
	public void setRegistrantEmail(String registrant_email) {
	    this.registrant_email = registrant_email;
	}
    
	
    /**
	 *
	 * Gets the <code>registrant_email</code> field
	 *
	 * @returns registrant_email
	 *
	 */
    
	public String getRegistrantEmail() {
	    return registrant_email;
	}

	
    /**
	 *
	 * Sets the <code>registrant_additional_email</code> field
	 *
	 * @param registrant_additional_email      String
	 *
	 */
    
	public void setRegistrantAdditionalEmail(String registrant_additional_email) {
	    this.registrant_additional_email = registrant_additional_email;
	}
    
	
    /**
	 *
	 * Gets the <code>registrant_additional_email</code> field
	 *
	 * @returns registrant_additional_email
	 *
	 */
    
	public String getRegistrantAdditionalEmail() {
	    return registrant_additional_email;
	}

	
    /**
	 *
	 * Sets the <code>registrant_phone</code> field
	 *
	 * @param registrant_phone      String
	 *
	 */
    
	public void setRegistrantPhone(String registrant_phone) {
	    this.registrant_phone = registrant_phone;
	}
    
	
    /**
	 *
	 * Gets the <code>registrant_phone</code> field
	 *
	 * @returns registrant_phone
	 *
	 */
    
	public String getRegistrantPhone() {
	    return registrant_phone;
	}

	
    /**
	 *
	 * Sets the <code>registrant_address</code> field
	 *
	 * @param registrant_address      String
	 *
	 */
    
	public void setRegistrantAddress(String registrant_address) {
	    this.registrant_address = registrant_address;
	}
    
	
    /**
	 *
	 * Gets the <code>registrant_address</code> field
	 *
	 * @returns registrant_address
	 *
	 */
    
	public String getRegistrantAddress() {
	    return registrant_address;
	}

	
    /**
	 *
	 * Sets the <code>registrant_city</code> field
	 *
	 * @param registrant_city      String
	 *
	 */
    
	public void setRegistrantCity(String registrant_city) {
	    this.registrant_city = registrant_city;
	}
    
	
    /**
	 *
	 * Gets the <code>registrant_city</code> field
	 *
	 * @returns registrant_city
	 *
	 */
    
	public String getRegistrantCity() {
	    return registrant_city;
	}

	
    /**
	 *
	 * Sets the <code>registrant_state</code> field
	 *
	 * @param registrant_state      String
	 *
	 */
    
	public void setRegistrantState(String registrant_state) {
	    this.registrant_state = registrant_state;
	}
    
	
    /**
	 *
	 * Gets the <code>registrant_state</code> field
	 *
	 * @returns registrant_state
	 *
	 */
    
	public String getRegistrantState() {
	    return registrant_state;
	}

	
    /**
	 *
	 * Sets the <code>registrant_pincode</code> field
	 *
	 * @param registrant_pincode      String
	 *
	 */
    
	public void setRegistrantPincode(String registrant_pincode) {
	    this.registrant_pincode = registrant_pincode;
	}
    
	
    /**
	 *
	 * Gets the <code>registrant_pincode</code> field
	 *
	 * @returns registrant_pincode
	 *
	 */
    
	public String getRegistrantPincode() {
	    return registrant_pincode;
	}

	
    /**
	 *
	 * Sets the <code>registrant_pan</code> field
	 *
	 * @param registrant_pan      String
	 *
	 */
    
	public void setRegistrantPan(String registrant_pan) {
	    this.registrant_pan = registrant_pan;
	}
    
	
    /**
	 *
	 * Gets the <code>registrant_pan</code> field
	 *
	 * @returns registrant_pan
	 *
	 */
    
	public String getRegistrantPan() {
	    return registrant_pan;
	}

	
    /**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
    
	public boolean equals(Object obj) {
	    RegistrantObject other = (RegistrantObject)obj;
	    DebugHandler.finest("This: " + this);
	    DebugHandler.finest("Other: " + other);
	    return
	        registrant_id == other.getRegistrantId() &&
	   Util.trim(registrant_name).equals(Util.trim(other.getRegistrantName())) &&
	   Util.trim(registrant_middle_name).equals(Util.trim(other.getRegistrantMiddleName())) &&
	   Util.trim(registrant_last_name).equals(Util.trim(other.getRegistrantLastName())) &&
	   Util.trim(registrant_email).equals(Util.trim(other.getRegistrantEmail())) &&
	   Util.trim(registrant_additional_email).equals(Util.trim(other.getRegistrantAdditionalEmail())) &&
	   Util.trim(registrant_phone).equals(Util.trim(other.getRegistrantPhone())) &&
	   Util.trim(registrant_address).equals(Util.trim(other.getRegistrantAddress())) &&
	   Util.trim(registrant_city).equals(Util.trim(other.getRegistrantCity())) &&
	   Util.trim(registrant_state).equals(Util.trim(other.getRegistrantState())) &&
	   Util.trim(registrant_pincode).equals(Util.trim(other.getRegistrantPincode())) &&
	        Util.trim(registrant_pan).equals(Util.trim(other.getRegistrantPan()));
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
