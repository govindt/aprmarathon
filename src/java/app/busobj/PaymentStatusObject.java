/*
 * PaymentStatusObject.java
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
 * The implementation of the PaymentStatusObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PaymentStatusObject implements Cloneable {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
	private int payment_status_id;
	private String payment_status_name;
	
	/**
	 *
	 * Returns the String representation of the PaymentStatusObject.
	 *
	 * @return	 Returns the String representation of the PaymentStatusObject.
	 *
	 */
	
	public String toString() {
		String buf="";
		buf += "payment_status_id : " + payment_status_id + "\n";
		buf += "payment_status_name : " + payment_status_name + "\n";
		return buf;
	}

	/**
	 *
	 * Returns the List representation of the PaymentStatusObject.
	 *
	 * Returns the object as an array list of Strings.
	 *
	 */
	
	public List<Object> asList() {
		List<Object> list = new ArrayList<Object>();
		list.add(payment_status_id + "");
		list.add(payment_status_name + "");
		return list;
	}

	/**
	 *
	 * Returns the JSON representation of the PaymentStatusObject.
	 *
	 * @return  	Returns the JSON representation of the PaymentStatusObject.
	 *
	 */
	
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("payment_status_id", payment_status_id);
			jo.put("payment_status_name", payment_status_name);
		} catch (JSONException je) {}
		return jo;
	}
	
	/**
	 *
	 * Returns the hashCode representation of the PaymentStatusObject.
	 *
	 * @return	Returns the hashCode.
	 *
	*/
	
	public int hashCode() {
		return payment_status_id;
	}
	
	/**
	 * Constructs the PaymentStatusObject
	 *
	 */
	
	public PaymentStatusObject () {
		setPaymentStatusId(0);
		setPaymentStatusName("");
	}
	
	/**
	 * Constructs the PaymentStatusObject from JSONObject
	 *
	 */
	
	public PaymentStatusObject (JSONObject jObject) {
		try {
			payment_status_id = jObject.getInt("payment_status_id");
		} catch (JSONException je) {}
		try {
			payment_status_name = jObject.getString("payment_status_name");
		} catch (JSONException je) {payment_status_name = "";}
	}
	
	
	/**
	 *
	 * Sets the <code>payment_status_id</code> field
	 *
	 * @param payment_status_id	  int
	 *
	 */
	
	public void setPaymentStatusId(int payment_status_id) {
		this.payment_status_id = payment_status_id;
	}
	
	
	/**
	 *
	 * Gets the <code>payment_status_id</code> field
	 *
	 * @returns payment_status_id
	 *
	 */
	
	public int getPaymentStatusId() {
		return payment_status_id;
	}

	
	/**
	 *
	 * Sets the <code>payment_status_name</code> field
	 *
	 * @param payment_status_name	  String
	 *
	 */
	
	public void setPaymentStatusName(String payment_status_name) {
		this.payment_status_name = payment_status_name;
	}
	
	
	/**
	 *
	 * Gets the <code>payment_status_name</code> field
	 *
	 * @returns payment_status_name
	 *
	 */
	
	public String getPaymentStatusName() {
		return payment_status_name;
	}

	
	/**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
	
	public boolean equals(Object obj) {
		PaymentStatusObject other = (PaymentStatusObject)obj;
		DebugHandler.finest("This: " + this);
		DebugHandler.finest("Other: " + other);
		return
			payment_status_id == other.getPaymentStatusId() &&
			Util.trim(payment_status_name).equals(Util.trim(other.getPaymentStatusName()));
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
