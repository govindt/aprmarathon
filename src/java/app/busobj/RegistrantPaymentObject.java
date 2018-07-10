/*
 * RegistrantPaymentObject.java
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
 * The implementation of the RegistrantPaymentObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrantPaymentObject implements Cloneable {
	private int registrant_payment_id;
	private int registrant_event;
	private int registrant;
	private int payment_type;
	private int payment_status;
	private double payment_amount;
	private double payment_additional_amount;
	private Date payment_date;
	private Date receipt_date;
	private String payment_details;
	private String payment_towards;
	private String payment_reference_id;
	private double payment_tax;
	private double payment_fee;
	
	/**
	 *
	 * Returns the String representation of the RegistrantPaymentObject.
	 *
	 * @return	 Returns the String representation of the RegistrantPaymentObject.
	 *
	 */
    
	public String toString() {
	   return	"registrant_payment_id : " + registrant_payment_id + "\n" +
		"registrant_event : " + registrant_event + "\n" +
		"registrant : " + registrant + "\n" +
		"payment_type : " + payment_type + "\n" +
		"payment_status : " + payment_status + "\n" +
		"payment_amount : " + payment_amount + "\n" +
		"payment_additional_amount : " + payment_additional_amount + "\n" +
		"payment_date : " + payment_date + "\n" +
		"receipt_date : " + receipt_date + "\n" +
		"payment_details : " + payment_details + "\n" +
		"payment_towards : " + payment_towards + "\n" +
		"payment_reference_id : " + payment_reference_id + "\n" +
		"payment_tax : " + payment_tax + "\n" +
		"payment_fee : " + payment_fee + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the RegistrantPaymentObject.
	 *
	 * @return      Returns the JSON representation of the RegistrantPaymentObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("registrant_payment_id", registrant_payment_id);
			 jo.put("registrant_event", registrant_event);
			 jo.put("registrant", registrant);
			 jo.put("payment_type", payment_type);
			 jo.put("payment_status", payment_status);
			 jo.put("payment_amount", payment_amount);
			 jo.put("payment_additional_amount", payment_additional_amount);
			 jo.put("payment_date", payment_date);
			 jo.put("receipt_date", receipt_date);
			 jo.put("payment_details", payment_details);
			 jo.put("payment_towards", payment_towards);
			 jo.put("payment_reference_id", payment_reference_id);
			 jo.put("payment_tax", payment_tax);
			 jo.put("payment_fee", payment_fee);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the RegistrantPaymentObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return registrant_payment_id;
	}
    
	/**
	 * Constructs the RegistrantPaymentObject
	 *
	 */
    
	public RegistrantPaymentObject () {
		setRegistrantPaymentId(0);
		setRegistrantEvent(0);
		setRegistrant(0);
		setPaymentType(0);
		setPaymentStatus(0);
		setPaymentAmount(0.0);
		setPaymentAdditionalAmount(0.0);
		setPaymentDate(null);
		setReceiptDate(null);
		setPaymentDetails("");
		setPaymentTowards("");
		setPaymentReferenceId("");
		setPaymentTax(0.0);
		setPaymentFee(0.0);
	}
    
	/**
	 * Constructs the RegistrantPaymentObject from JSONObject
	 *
	 */
    
	public RegistrantPaymentObject (JSONObject jObject) {
		try {
			registrant_payment_id = jObject.getInt("registrant_payment_id");
		} catch (JSONException je) {}
		try {
			registrant_event = jObject.getInt("registrant_event");
		} catch (JSONException je) {}
		try {
			registrant = jObject.getInt("registrant");
		} catch (JSONException je) {}
		try {
			payment_type = jObject.getInt("payment_type");
		} catch (JSONException je) {}
		try {
			payment_status = jObject.getInt("payment_status");
		} catch (JSONException je) {}
		try {
			try {
				payment_amount = Double.parseDouble(jObject.getString("payment_amount"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			try {
				payment_additional_amount = Double.parseDouble(jObject.getString("payment_additional_amount"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				payment_date = dateFormatter.parse(jObject.getString("payment_date"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				receipt_date = dateFormatter.parse(jObject.getString("receipt_date"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			payment_details = jObject.getString("payment_details");
		} catch (JSONException je) {}
		try {
			payment_towards = jObject.getString("payment_towards");
		} catch (JSONException je) {}
		try {
			payment_reference_id = jObject.getString("payment_reference_id");
		} catch (JSONException je) {}
		try {
			try {
				payment_tax = Double.parseDouble(jObject.getString("payment_tax"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			try {
				payment_fee = Double.parseDouble(jObject.getString("payment_fee"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
	}
    
    
    /**
     *
     * Sets the <code>registrant_payment_id</code> field
     *
     * @param registrant_payment_id      int
     *
     */
    
    public void setRegistrantPaymentId(int registrant_payment_id) {
        this.registrant_payment_id = registrant_payment_id;
    }
    
    
    /**
     *
     * Gets the <code>registrant_payment_id</code> field
     *
     * @returns registrant_payment_id
     *
     */
    
    public int getRegistrantPaymentId() {
        return registrant_payment_id;
    }

    
    /**
     *
     * Sets the <code>registrant_event</code> field
     *
     * @param registrant_event      int
     *
     */
    
    public void setRegistrantEvent(int registrant_event) {
        this.registrant_event = registrant_event;
    }
    
    
    /**
     *
     * Gets the <code>registrant_event</code> field
     *
     * @returns registrant_event
     *
     */
    
    public int getRegistrantEvent() {
        return registrant_event;
    }

    
    /**
     *
     * Sets the <code>registrant</code> field
     *
     * @param registrant      int
     *
     */
    
    public void setRegistrant(int registrant) {
        this.registrant = registrant;
    }
    
    
    /**
     *
     * Gets the <code>registrant</code> field
     *
     * @returns registrant
     *
     */
    
    public int getRegistrant() {
        return registrant;
    }

    
    /**
     *
     * Sets the <code>payment_type</code> field
     *
     * @param payment_type      int
     *
     */
    
    public void setPaymentType(int payment_type) {
        this.payment_type = payment_type;
    }
    
    
    /**
     *
     * Gets the <code>payment_type</code> field
     *
     * @returns payment_type
     *
     */
    
    public int getPaymentType() {
        return payment_type;
    }

    
    /**
     *
     * Sets the <code>payment_status</code> field
     *
     * @param payment_status      int
     *
     */
    
    public void setPaymentStatus(int payment_status) {
        this.payment_status = payment_status;
    }
    
    
    /**
     *
     * Gets the <code>payment_status</code> field
     *
     * @returns payment_status
     *
     */
    
    public int getPaymentStatus() {
        return payment_status;
    }

    
    /**
     *
     * Sets the <code>payment_amount</code> field
     *
     * @param payment_amount      double
     *
     */
    
    public void setPaymentAmount(double payment_amount) {
        this.payment_amount = payment_amount;
    }
    
    
    /**
     *
     * Gets the <code>payment_amount</code> field
     *
     * @returns payment_amount
     *
     */
    
    public double getPaymentAmount() {
        return payment_amount;
    }

    
    /**
     *
     * Sets the <code>payment_additional_amount</code> field
     *
     * @param payment_additional_amount      double
     *
     */
    
    public void setPaymentAdditionalAmount(double payment_additional_amount) {
        this.payment_additional_amount = payment_additional_amount;
    }
    
    
    /**
     *
     * Gets the <code>payment_additional_amount</code> field
     *
     * @returns payment_additional_amount
     *
     */
    
    public double getPaymentAdditionalAmount() {
        return payment_additional_amount;
    }

    
    /**
     *
     * Sets the <code>payment_date</code> field
     *
     * @param payment_date      Date
     *
     */
    
    public void setPaymentDate(Date payment_date) {
        this.payment_date = payment_date;
    }
    
    
    /**
     *
     * Gets the <code>payment_date</code> field
     *
     * @returns payment_date
     *
     */
    
    public Date getPaymentDate() {
        return payment_date;
    }

    
    /**
     *
     * Sets the <code>receipt_date</code> field
     *
     * @param receipt_date      Date
     *
     */
    
    public void setReceiptDate(Date receipt_date) {
        this.receipt_date = receipt_date;
    }
    
    
    /**
     *
     * Gets the <code>receipt_date</code> field
     *
     * @returns receipt_date
     *
     */
    
    public Date getReceiptDate() {
        return receipt_date;
    }

    
    /**
     *
     * Sets the <code>payment_details</code> field
     *
     * @param payment_details      String
     *
     */
    
    public void setPaymentDetails(String payment_details) {
        this.payment_details = payment_details;
    }
    
    
    /**
     *
     * Gets the <code>payment_details</code> field
     *
     * @returns payment_details
     *
     */
    
    public String getPaymentDetails() {
        return payment_details;
    }

    
    /**
     *
     * Sets the <code>payment_towards</code> field
     *
     * @param payment_towards      String
     *
     */
    
    public void setPaymentTowards(String payment_towards) {
        this.payment_towards = payment_towards;
    }
    
    
    /**
     *
     * Gets the <code>payment_towards</code> field
     *
     * @returns payment_towards
     *
     */
    
    public String getPaymentTowards() {
        return payment_towards;
    }

    
    /**
     *
     * Sets the <code>payment_reference_id</code> field
     *
     * @param payment_reference_id      String
     *
     */
    
    public void setPaymentReferenceId(String payment_reference_id) {
        this.payment_reference_id = payment_reference_id;
    }
    
    
    /**
     *
     * Gets the <code>payment_reference_id</code> field
     *
     * @returns payment_reference_id
     *
     */
    
    public String getPaymentReferenceId() {
        return payment_reference_id;
    }

    
    /**
     *
     * Sets the <code>payment_tax</code> field
     *
     * @param payment_tax      double
     *
     */
    
    public void setPaymentTax(double payment_tax) {
        this.payment_tax = payment_tax;
    }
    
    
    /**
     *
     * Gets the <code>payment_tax</code> field
     *
     * @returns payment_tax
     *
     */
    
    public double getPaymentTax() {
        return payment_tax;
    }

    
    /**
     *
     * Sets the <code>payment_fee</code> field
     *
     * @param payment_fee      double
     *
     */
    
    public void setPaymentFee(double payment_fee) {
        this.payment_fee = payment_fee;
    }
    
    
    /**
     *
     * Gets the <code>payment_fee</code> field
     *
     * @returns payment_fee
     *
     */
    
    public double getPaymentFee() {
        return payment_fee;
    }

    
    /**
     *
     * Tests if this object equals <code>obj</code>
     *
     * @returns true if equals
     *
     */
    
    public boolean equals(Object obj) {
        RegistrantPaymentObject other = (RegistrantPaymentObject)obj;
        DebugHandler.finest("This: " + this);
        DebugHandler.finest("Other: " + other);
        return
            registrant_payment_id == other.getRegistrantPaymentId() &&
            registrant_event == other.getRegistrantEvent() &&
            registrant == other.getRegistrant() &&
            payment_type == other.getPaymentType() &&
            payment_status == other.getPaymentStatus() &&
            payment_amount == other.getPaymentAmount() &&
            payment_additional_amount == other.getPaymentAdditionalAmount() &&
            payment_date.equals(other.getPaymentDate()) &&
            receipt_date.equals(other.getReceiptDate()) &&
            Util.trim(payment_details).equals(Util.trim(other.getPaymentDetails())) &&
            Util.trim(payment_towards).equals(Util.trim(other.getPaymentTowards())) &&
            Util.trim(payment_reference_id).equals(Util.trim(other.getPaymentReferenceId())) &&
            payment_tax == other.getPaymentTax() &&
            payment_fee == other.getPaymentFee();
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
