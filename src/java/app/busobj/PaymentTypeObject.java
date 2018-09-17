/*
 * PaymentTypeObject.java
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
 * The implementation of the PaymentTypeObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PaymentTypeObject implements Cloneable {
	private int payment_type_id;
	private String payment_type_name;
	
	/**
	 *
	 * Returns the String representation of the PaymentTypeObject.
	 *
	 * @return	 Returns the String representation of the PaymentTypeObject.
	 *
	 */
    
	public String toString() {
	   return	"payment_type_id : " + payment_type_id + "\n" +
		"payment_type_name : " + payment_type_name + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the PaymentTypeObject.
	 *
	 * @return      Returns the JSON representation of the PaymentTypeObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("payment_type_id", payment_type_id);
			 jo.put("payment_type_name", payment_type_name);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the PaymentTypeObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return payment_type_id;
	}
    
	/**
	 * Constructs the PaymentTypeObject
	 *
	 */
    
	public PaymentTypeObject () {
		setPaymentTypeId(0);
		setPaymentTypeName("");
	}
    
	/**
	 * Constructs the PaymentTypeObject from JSONObject
	 *
	 */
    
	public PaymentTypeObject (JSONObject jObject) {
		try {
			payment_type_id = jObject.getInt("payment_type_id");
		} catch (JSONException je) {}
		try {
			payment_type_name = jObject.getString("payment_type_name");
		} catch (JSONException je) {}
	}
    
	
    /**
	 *
	 * Sets the <code>payment_type_id</code> field
	 *
	 * @param payment_type_id      int
	 *
	 */
    
	public void setPaymentTypeId(int payment_type_id) {
	    this.payment_type_id = payment_type_id;
	}
    
	
    /**
	 *
	 * Gets the <code>payment_type_id</code> field
	 *
	 * @returns payment_type_id
	 *
	 */
    
	public int getPaymentTypeId() {
	    return payment_type_id;
	}

	
    /**
	 *
	 * Sets the <code>payment_type_name</code> field
	 *
	 * @param payment_type_name      String
	 *
	 */
    
	public void setPaymentTypeName(String payment_type_name) {
	    this.payment_type_name = payment_type_name;
	}
    
	
    /**
	 *
	 * Gets the <code>payment_type_name</code> field
	 *
	 * @returns payment_type_name
	 *
	 */
    
	public String getPaymentTypeName() {
	    return payment_type_name;
	}

	
    /**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
    
	public boolean equals(Object obj) {
	    PaymentTypeObject other = (PaymentTypeObject)obj;
	    DebugHandler.finest("This: " + this);
	    DebugHandler.finest("Other: " + other);
	    return
	        payment_type_id == other.getPaymentTypeId() &&
	        Util.trim(payment_type_name).equals(Util.trim(other.getPaymentTypeName()));
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
