/*
 * RegistrantSheetObject.java
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
 * The implementation of the RegistrantSheetObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrantSheetObject implements Cloneable {
	private String registrant_id;
	private String registrant_name;
	private String registrant_middle_name;
	private String registrant_last_name;
	private String registrant_email;
	private String registrant_additional_email;
	private String registrant_phone_number;
	private String registrant_address;
	private String registrant_city;
	private String registrant_state;
	private String registrant_pincode;
	private String registrant_pan;
	private String registrant_event;
	private String registrant_type_name;
	private String registrant_source_name;
	private String registrant_class_name;
	private String registrant_beneficiary_name;
	private String registrant_emergency_contact;
	private String registrant_emergency_phone;
	private String registrant_payment_type_name;
	private String registrant_payment_status_name;
	private double registrant_payment_amount;
	private double registrant_additional_amount;
	private Date registrant_payment_date;
	private Date registrant_receipt_date;
	private String registrant_payment_details;
	private String registrant_payment_towards;
	private String registrant_payment_reference_id;
	private double registrant_payment_tax;
	private double registrant_payment_fee;
	
	/**
	 *
	 * Returns the String representation of the RegistrantSheetObject.
	 *
	 * @return	 Returns the String representation of the RegistrantSheetObject.
	 *
	 */
    
	public String toString() {
	   return	"registrant_id : " + registrant_id + "\n" +
	    "registrant_name : " + registrant_name + "\n" +
		"registrant_middle_name : " + registrant_middle_name + "\n" +
		"registrant_last_name : " + registrant_last_name + "\n" +
		"registrant_email : " + registrant_email + "\n" +
		"registrant_additional_email : " + registrant_additional_email + "\n" +
		"registrant_phone_number : " + registrant_phone_number + "\n" +
		"registrant_address : " + registrant_address + "\n" +
		"registrant_city : " + registrant_city + "\n" +
		"registrant_state : " + registrant_state + "\n" +
		"registrant_pincode : " + registrant_pincode + "\n" +
		"registrant_pan : " + registrant_pan + "\n" +
		"registrant_event : " + registrant_event + "\n" +
		"registrant_type_name : " + registrant_type_name + "\n" +
		"registrant_source_name : " + registrant_source_name + "\n" +
		"registrant_class_name : " + registrant_class_name + "\n" +
		"registrant_beneficiary_name : " + registrant_beneficiary_name + "\n" +
		"registrant_emergency_contact : " + registrant_emergency_contact + "\n" +
		"registrant_emergency_phone : " + registrant_emergency_phone + "\n" +
		"registrant_payment_type_name : " + registrant_payment_type_name + "\n" +
		"registrant_payment_status_name : " + registrant_payment_status_name + "\n" +
		"registrant_payment_amount : " + registrant_payment_amount + "\n" +
		"registrant_additional_amount : " + registrant_additional_amount + "\n" +
		"registrant_payment_date : " + registrant_payment_date + "\n" +
		"registrant_receipt_date : " + registrant_receipt_date + "\n" +
		"registrant_payment_details : " + registrant_payment_details + "\n" +
		"registrant_payment_towards : " + registrant_payment_towards + "\n" +
		"registrant_payment_reference_id : " + registrant_payment_reference_id + "\n" +
		"registrant_payment_tax : " + registrant_payment_tax + "\n" +
		"registrant_payment_fee : " + registrant_payment_fee + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the RegistrantSheetObject.
	 *
	 * @return      Returns the JSON representation of the RegistrantSheetObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("registrant_name", registrant_name);
			 jo.put("registrant_middle_name", registrant_middle_name);
			 jo.put("registrant_last_name", registrant_last_name);
			 jo.put("registrant_email", registrant_email);
			 jo.put("registrant_additional_email", registrant_additional_email);
			 jo.put("registrant_phone_number", registrant_phone_number);
			 jo.put("registrant_address", registrant_address);
			 jo.put("registrant_city", registrant_city);
			 jo.put("registrant_state", registrant_state);
			 jo.put("registrant_pincode", registrant_pincode);
			 jo.put("registrant_pan", registrant_pan);
			 jo.put("registrant_event", registrant_event);
			 jo.put("registrant_type_name", registrant_type_name);
			 jo.put("registrant_source_name", registrant_source_name);
			 jo.put("registrant_class_name", registrant_class_name);
			 jo.put("registrant_beneficiary_name", registrant_beneficiary_name);
			 jo.put("registrant_emergency_contact", registrant_emergency_contact);
			 jo.put("registrant_emergency_phone", registrant_emergency_phone);
			 jo.put("registrant_payment_type_name", registrant_payment_type_name);
			 jo.put("registrant_payment_status_name", registrant_payment_status_name);
			 jo.put("registrant_payment_amount", registrant_payment_amount);
			 jo.put("registrant_additional_amount", registrant_additional_amount);
			 jo.put("registrant_payment_date", registrant_payment_date);
			 jo.put("registrant_receipt_date", registrant_receipt_date);
			 jo.put("registrant_payment_details", registrant_payment_details);
			 jo.put("registrant_payment_towards", registrant_payment_towards);
			 jo.put("registrant_payment_reference_id", registrant_payment_reference_id);
			 jo.put("registrant_payment_tax", registrant_payment_tax);
			 jo.put("registrant_payment_fee", registrant_payment_fee);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the RegistrantSheetObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return registrant_name.hashCode();
	}
    
	/**
	 * Constructs the RegistrantSheetObject
	 *
	 */
    
	public RegistrantSheetObject () {
		setRegistrantId("0");
		setRegistrantName("");
		setRegistrantMiddleName("");
		setRegistrantLastName("");
		setRegistrantEmail("");
		setRegistrantAdditionalEmail("");
		setRegistrantPhoneNumber("");
		setRegistrantAddress("");
		setRegistrantCity("");
		setRegistrantState("");
		setRegistrantPincode("");
		setRegistrantPan("");
		setRegistrantTypeName("");
		setRegistrantSourceName("");
		setRegistrantClassName("");
		setRegistrantBeneficiaryName("");
		setRegistrantEmergencyContact("");
		setRegistrantEmergencyPhone("");
		setRegistrantPaymentTypeName("");
		setRegistrantPaymentStatusName("");
		setRegistrantPaymentAmount(0.0);
		setRegistrantAdditionalAmount(0.0);
		setRegistrantPaymentDate(null);
		setRegistrantReceiptDate(null);
		setRegistrantPaymentDetails("");
		setRegistrantPaymentTowards("");
		setRegistrantPaymentReferenceId("");
		setRegistrantPaymentTax(0.0);
		setRegistrantPaymentFee(0.0);
	}
    
	/**
	 * Constructs the RegistrantSheetObject from JSONObject
	 *
	 */
    
	public RegistrantSheetObject (JSONObject jObject) {
		try {
			registrant_id = jObject.getString("registrant_id");
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
			registrant_phone_number = jObject.getString("registrant_phone_number");
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
		try {
			registrant_event = jObject.getString("registrant_event");
		} catch (JSONException je) {}
		try {
			registrant_type_name = jObject.getString("registrant_type_name");
		} catch (JSONException je) {}
		try {
			registrant_source_name = jObject.getString("registrant_source_name");
		} catch (JSONException je) {}
		try {
			registrant_class_name = jObject.getString("registrant_class_name");
		} catch (JSONException je) {}
		try {
			registrant_beneficiary_name = jObject.getString("registrant_beneficiary_name");
		} catch (JSONException je) {}
		try {
			registrant_emergency_contact = jObject.getString("registrant_emergency_contact");
		} catch (JSONException je) {}
		try {
			registrant_emergency_phone = jObject.getString("registrant_emergency_phone");
		} catch (JSONException je) {}
		try {
			registrant_payment_type_name = jObject.getString("registrant_payment_type_name");
		} catch (JSONException je) {}
		try {
			registrant_payment_status_name = jObject.getString("registrant_payment_status_name");
		} catch (JSONException je) {}
		try {
			try {
				registrant_payment_amount = Double.parseDouble(jObject.getString("registrant_payment_amount"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			try {
				registrant_additional_amount = Double.parseDouble(jObject.getString("registrant_additional_amount"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				registrant_payment_date = dateFormatter.parse(jObject.getString("registrant_payment_date"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				registrant_receipt_date = dateFormatter.parse(jObject.getString("registrant_receipt_date"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			registrant_payment_details = jObject.getString("registrant_payment_details");
		} catch (JSONException je) {}
		try {
			registrant_payment_towards = jObject.getString("registrant_payment_towards");
		} catch (JSONException je) {}
		try {
			registrant_payment_reference_id = jObject.getString("registrant_payment_reference_id");
		} catch (JSONException je) {}
		try {
			try {
				registrant_payment_tax = Double.parseDouble(jObject.getString("registrant_payment_tax"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
		try {
			try {
				registrant_payment_fee = Double.parseDouble(jObject.getString("registrant_payment_fee"));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
	}
	
    /**
     *
     * Sets the <code>registrant_id</code> field
     *
     * @param registrant_id      String
     *
     */
    
    public void setRegistrantId(String registrant_id) {
        this.registrant_id = registrant_id;
    }
    
    
    /**
     *
     * Gets the <code>registrant_id</code> field
     *
     * @returns registrant_id
     *
     */
    
    public String getRegistrantId() {
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
     * Sets the <code>registrant_phone_number</code> field
     *
     * @param registrant_phone_number      String
     *
     */
    
    public void setRegistrantPhoneNumber(String registrant_phone_number) {
        this.registrant_phone_number = registrant_phone_number;
    }
    
    
    /**
     *
     * Gets the <code>registrant_phone_number</code> field
     *
     * @returns registrant_phone_number
     *
     */
    
    public String getRegistrantPhoneNumber() {
        return registrant_phone_number;
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
     * Sets the <code>registrant_event</code> field
     *
     * @param registrant_event      
     *
     */
    
    public void setRegistrantEvent(String registrant_event) {
        this.registrant_event = registrant_event;
    }
    
    
    /**
     *
     * Gets the <code>registrant_event</code> field
     *
     * @returns registrant_event
     *
     */
    
    public String getRegistrantEvent() {
        return registrant_event;
    }

    
    /**
     *
     * Sets the <code>registrant_type_name</code> field
     *
     * @param registrant_type_name      String
     *
     */
    
    public void setRegistrantTypeName(String registrant_type_name) {
        this.registrant_type_name = registrant_type_name;
    }
    
    
    /**
     *
     * Gets the <code>registrant_type_name</code> field
     *
     * @returns registrant_type_name
     *
     */
    
    public String getRegistrantTypeName() {
        return registrant_type_name;
    }

    
    /**
     *
     * Sets the <code>registrant_source_name</code> field
     *
     * @param registrant_source_name      String
     *
     */
    
    public void setRegistrantSourceName(String registrant_source_name) {
        this.registrant_source_name = registrant_source_name;
    }
    
    
    /**
     *
     * Gets the <code>registrant_source_name</code> field
     *
     * @returns registrant_source_name
     *
     */
    
    public String getRegistrantSourceName() {
        return registrant_source_name;
    }

    
    /**
     *
     * Sets the <code>registrant_class_name</code> field
     *
     * @param registrant_class_name      String
     *
     */
    
    public void setRegistrantClassName(String registrant_class_name) {
        this.registrant_class_name = registrant_class_name;
    }
    
    
    /**
     *
     * Gets the <code>registrant_class_name</code> field
     *
     * @returns registrant_class_name
     *
     */
    
    public String getRegistrantClassName() {
        return registrant_class_name;
    }

    
    /**
     *
     * Sets the <code>registrant_beneficiary_name</code> field
     *
     * @param registrant_beneficiary_name      String
     *
     */
    
    public void setRegistrantBeneficiaryName(String registrant_beneficiary_name) {
        this.registrant_beneficiary_name = registrant_beneficiary_name;
    }
    
    
    /**
     *
     * Gets the <code>registrant_beneficiary_name</code> field
     *
     * @returns registrant_beneficiary_name
     *
     */
    
    public String getRegistrantBeneficiaryName() {
        return registrant_beneficiary_name;
    }

    
    /**
     *
     * Sets the <code>registrant_emergency_contact</code> field
     *
     * @param registrant_emergency_contact      String
     *
     */
    
    public void setRegistrantEmergencyContact(String registrant_emergency_contact) {
        this.registrant_emergency_contact = registrant_emergency_contact;
    }
    
    
    /**
     *
     * Gets the <code>registrant_emergency_contact</code> field
     *
     * @returns registrant_emergency_contact
     *
     */
    
    public String getRegistrantEmergencyContact() {
        return registrant_emergency_contact;
    }

    
    /**
     *
     * Sets the <code>registrant_emergency_phone</code> field
     *
     * @param registrant_emergency_phone      String
     *
     */
    
    public void setRegistrantEmergencyPhone(String registrant_emergency_phone) {
        this.registrant_emergency_phone = registrant_emergency_phone;
    }
    
    
    /**
     *
     * Gets the <code>registrant_emergency_phone</code> field
     *
     * @returns registrant_emergency_phone
     *
     */
    
    public String getRegistrantEmergencyPhone() {
        return registrant_emergency_phone;
    }

    
    /**
     *
     * Sets the <code>registrant_payment_type_name</code> field
     *
     * @param registrant_payment_type_name      String
     *
     */
    
    public void setRegistrantPaymentTypeName(String registrant_payment_type_name) {
        this.registrant_payment_type_name = registrant_payment_type_name;
    }
    
    
    /**
     *
     * Gets the <code>registrant_payment_type_name</code> field
     *
     * @returns registrant_payment_type_name
     *
     */
    
    public String getRegistrantPaymentTypeName() {
        return registrant_payment_type_name;
    }

    
    /**
     *
     * Sets the <code>registrant_payment_status_name</code> field
     *
     * @param registrant_payment_status_name      String
     *
     */
    
    public void setRegistrantPaymentStatusName(String registrant_payment_status_name) {
        this.registrant_payment_status_name = registrant_payment_status_name;
    }
    
    
    /**
     *
     * Gets the <code>registrant_payment_status_name</code> field
     *
     * @returns registrant_payment_status_name
     *
     */
    
    public String getRegistrantPaymentStatusName() {
        return registrant_payment_status_name;
    }

    
    /**
     *
     * Sets the <code>registrant_payment_amount</code> field
     *
     * @param registrant_payment_amount      double
     *
     */
    
    public void setRegistrantPaymentAmount(double registrant_payment_amount) {
        this.registrant_payment_amount = registrant_payment_amount;
    }
    
    
    /**
     *
     * Gets the <code>registrant_payment_amount</code> field
     *
     * @returns registrant_payment_amount
     *
     */
    
    public double getRegistrantPaymentAmount() {
        return registrant_payment_amount;
    }

    
    /**
     *
     * Sets the <code>registrant_additional_amount</code> field
     *
     * @param registrant_additional_amount      double
     *
     */
    
    public void setRegistrantAdditionalAmount(double registrant_additional_amount) {
        this.registrant_additional_amount = registrant_additional_amount;
    }
    
    
    /**
     *
     * Gets the <code>registrant_additional_amount</code> field
     *
     * @returns registrant_additional_amount
     *
     */
    
    public double getRegistrantAdditionalAmount() {
        return registrant_additional_amount;
    }

    
    /**
     *
     * Sets the <code>registrant_payment_date</code> field
     *
     * @param registrant_payment_date      Date
     *
     */
    
    public void setRegistrantPaymentDate(Date registrant_payment_date) {
        this.registrant_payment_date = registrant_payment_date;
    }
    
    
    /**
     *
     * Gets the <code>registrant_payment_date</code> field
     *
     * @returns registrant_payment_date
     *
     */
    
    public Date getRegistrantPaymentDate() {
        return registrant_payment_date;
    }

    
    /**
     *
     * Sets the <code>registrant_receipt_date</code> field
     *
     * @param registrant_receipt_date      Date
     *
     */
    
    public void setRegistrantReceiptDate(Date registrant_receipt_date) {
        this.registrant_receipt_date = registrant_receipt_date;
    }
    
    
    /**
     *
     * Gets the <code>registrant_receipt_date</code> field
     *
     * @returns registrant_receipt_date
     *
     */
    
    public Date getRegistrantReceiptDate() {
        return registrant_receipt_date;
    }

    
    /**
     *
     * Sets the <code>registrant_payment_details</code> field
     *
     * @param registrant_payment_details      String
     *
     */
    
    public void setRegistrantPaymentDetails(String registrant_payment_details) {
        this.registrant_payment_details = registrant_payment_details;
    }
    
    
    /**
     *
     * Gets the <code>registrant_payment_details</code> field
     *
     * @returns registrant_payment_details
     *
     */
    
    public String getRegistrantPaymentDetails() {
        return registrant_payment_details;
    }

    
    /**
     *
     * Sets the <code>registrant_payment_towards</code> field
     *
     * @param registrant_payment_towards      String
     *
     */
    
    public void setRegistrantPaymentTowards(String registrant_payment_towards) {
        this.registrant_payment_towards = registrant_payment_towards;
    }
    
    
    /**
     *
     * Gets the <code>registrant_payment_towards</code> field
     *
     * @returns registrant_payment_towards
     *
     */
    
    public String getRegistrantPaymentTowards() {
        return registrant_payment_towards;
    }

    
    /**
     *
     * Sets the <code>registrant_payment_reference_id</code> field
     *
     * @param registrant_payment_reference_id      String
     *
     */
    
    public void setRegistrantPaymentReferenceId(String registrant_payment_reference_id) {
        this.registrant_payment_reference_id = registrant_payment_reference_id;
    }
    
    
    /**
     *
     * Gets the <code>registrant_payment_reference_id</code> field
     *
     * @returns registrant_payment_reference_id
     *
     */
    
    public String getRegistrantPaymentReferenceId() {
        return registrant_payment_reference_id;
    }

    
    /**
     *
     * Sets the <code>registrant_payment_tax</code> field
     *
     * @param registrant_payment_tax      double
     *
     */
    
    public void setRegistrantPaymentTax(double registrant_payment_tax) {
        this.registrant_payment_tax = registrant_payment_tax;
    }
    
    
    /**
     *
     * Gets the <code>registrant_payment_tax</code> field
     *
     * @returns registrant_payment_tax
     *
     */
    
    public double getRegistrantPaymentTax() {
        return registrant_payment_tax;
    }

    
    /**
     *
     * Sets the <code>registrant_payment_fee</code> field
     *
     * @param registrant_payment_fee      double
     *
     */
    
    public void setRegistrantPaymentFee(double registrant_payment_fee) {
        this.registrant_payment_fee = registrant_payment_fee;
    }
    
    
    /**
     *
     * Gets the <code>registrant_payment_fee</code> field
     *
     * @returns registrant_payment_fee
     *
     */
    
    public double getRegistrantPaymentFee() {
        return registrant_payment_fee;
    }

    
    /**
     *
     * Tests if this object equals <code>obj</code>
     *
     * @returns true if equals
     *
     */
    
    public boolean equals(Object obj) {
        RegistrantSheetObject other = (RegistrantSheetObject)obj;
        DebugHandler.finest("This: " + this);
        DebugHandler.finest("Other: " + other);
        return
            Util.trim(registrant_name).equals(Util.trim(other.getRegistrantName())) &&
            Util.trim(registrant_middle_name).equals(Util.trim(other.getRegistrantMiddleName())) &&
            Util.trim(registrant_last_name).equals(Util.trim(other.getRegistrantLastName())) &&
            Util.trim(registrant_email).equals(Util.trim(other.getRegistrantEmail())) &&
            Util.trim(registrant_additional_email).equals(Util.trim(other.getRegistrantAdditionalEmail())) &&
            Util.trim(registrant_phone_number).equals(Util.trim(other.getRegistrantPhoneNumber())) &&
            Util.trim(registrant_address).equals(Util.trim(other.getRegistrantAddress())) &&
            Util.trim(registrant_city).equals(Util.trim(other.getRegistrantCity())) &&
            Util.trim(registrant_state).equals(Util.trim(other.getRegistrantState())) &&
            Util.trim(registrant_pincode).equals(Util.trim(other.getRegistrantPincode())) &&
            Util.trim(registrant_pan).equals(Util.trim(other.getRegistrantPan())) &&
            Util.trim(registrant_type_name).equals(Util.trim(other.getRegistrantTypeName())) &&
            Util.trim(registrant_source_name).equals(Util.trim(other.getRegistrantSourceName())) &&
            Util.trim(registrant_class_name).equals(Util.trim(other.getRegistrantClassName())) &&
            Util.trim(registrant_beneficiary_name).equals(Util.trim(other.getRegistrantBeneficiaryName())) &&
            Util.trim(registrant_emergency_contact).equals(Util.trim(other.getRegistrantEmergencyContact())) &&
            Util.trim(registrant_emergency_phone).equals(Util.trim(other.getRegistrantEmergencyPhone())) &&
            Util.trim(registrant_payment_type_name).equals(Util.trim(other.getRegistrantPaymentTypeName())) &&
            Util.trim(registrant_payment_status_name).equals(Util.trim(other.getRegistrantPaymentStatusName())) &&
            registrant_payment_amount == other.getRegistrantPaymentAmount() &&
            registrant_additional_amount == other.getRegistrantAdditionalAmount() &&
            registrant_payment_date.equals(other.getRegistrantPaymentDate()) &&
            registrant_receipt_date.equals(other.getRegistrantReceiptDate()) &&
            Util.trim(registrant_payment_details).equals(Util.trim(other.getRegistrantPaymentDetails())) &&
            Util.trim(registrant_payment_towards).equals(Util.trim(other.getRegistrantPaymentTowards())) &&
            Util.trim(registrant_payment_reference_id).equals(Util.trim(other.getRegistrantPaymentReferenceId())) &&
            registrant_payment_tax == other.getRegistrantPaymentTax() &&
            registrant_payment_fee == other.getRegistrantPaymentFee();
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
