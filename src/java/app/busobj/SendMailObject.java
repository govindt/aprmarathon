/*
 * SendMailObject.java
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
 * The implementation of the SendMailObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class SendMailObject implements Cloneable {
	public static int GENERAL_EMAIL = 0;
	public static int RECEIPT_EMAIL = 1;
	private String subject;
	private String body;
	private String to;
	private String cc;
	private String registrant_id;
	private String receipt_date;
	private String receipt_year;
	private String receipt_no;
	private String registrant_name;
	private String registrant_last_name;
	private String receipt_address;
	private String transfer_type;
	private String transfer_date;
	private String transfer_details;
	private String towards;
	private String amount;
	private int email_type = GENERAL_EMAIL;
	
	/**
	 *
	 * Returns the String representation of the SendMailObject.
	 *
	 * @return	 Returns the String representation of the SendMailObject.
	 *
	 */
    
	public String toString() {
	   return	"subject : " + subject + "\n" +
		"body : " + body + "\n" +
		"to : " + to + "\n" +
		"cc : " + cc + "\n" +
		"registrant_id : " + registrant_id + "\n" +
		"receipt_date : " + receipt_date + "\n" +
		"receipt_year : " + receipt_year + "\n" +
		"receipt_no : " + receipt_no + "\n" +
		"registrant_name : " + registrant_name + "\n" +
		"registrant_last_name : " + registrant_last_name + "\n" +
		"receipt_address : " + receipt_address + "\n" +
		"transfer_type : " + transfer_type + "\n" +
		"transfer_date : " + transfer_date + "\n" +
		"transfer_details : " + transfer_details + "\n" +
		"towards : " + towards + "\n" +
		"amount : " + amount + "\n" +
		"email_type : " + email_type + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the SendMailObject.
	 *
	 * @return      Returns the JSON representation of the SendMailObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("subject", subject);
			jo.put("body", body);
			jo.put("to", to);
			jo.put("cc", cc);
			jo.put("registrant_id", registrant_id);
			jo.put("receipt_date", receipt_date);
			jo.put("receipt_year", receipt_year);
			jo.put("receipt_no", receipt_no);
			jo.put("registrant_name", registrant_name);
			jo.put("registrant_last_name", registrant_last_name);
			jo.put("receipt_address", receipt_address);
			jo.put("transfer_type", transfer_type);
			jo.put("transfer_date", transfer_date);
			jo.put("transfer_details", transfer_details);
			jo.put("towards", towards);
			jo.put("amount", amount);
			jo.put("email_type", email_type);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the SendMailObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return registrant_id.hashCode();
	}
    
	/**
	 * Constructs the SendMailObject
	 *
	 */
    
	public SendMailObject () {
		setSubject("");
		setBody("");
		setTo("");
		setCc("");
		setRegistrantId("");
		setReceiptDate("");
		setReceiptYear("");
		setReceiptNo("");
		setRegistrantName("");
		setRegistrantLastName("");
		setReceiptAddress("");
		setTransferType("");
		setTransferDate("");
		setTransferDetails("");
		setTowards("");
		setAmount("");
		setEmailType(GENERAL_EMAIL);
	}
    
	/**
	 * Constructs the SendMailObject from JSONObject
	 *
	 */
    
	public SendMailObject (JSONObject jObject) {
		try {
			subject = jObject.getString("subject");
		} catch (JSONException je) {}
		try {
			body = jObject.getString("body");
		} catch (JSONException je) {}
		try {
			to = jObject.getString("to");
		} catch (JSONException je) {}
		try {
			cc = jObject.getString("cc");
		} catch (JSONException je) {}
		try {
			registrant_id = jObject.getString("registrant_id");
		} catch (JSONException je) {}
		try {
			receipt_date = jObject.getString("receipt_date");
		} catch (JSONException je) {}
		try {
			receipt_year = jObject.getString("receipt_year");
		} catch (JSONException je) {}
		try {
			receipt_no = jObject.getString("receipt_no");
		} catch (JSONException je) {}
		try {
			registrant_name = jObject.getString("registrant_name");
		} catch (JSONException je) {}
		try {
			registrant_last_name = jObject.getString("registrant_last_name");
		} catch (JSONException je) {}
		try {
			receipt_address = jObject.getString("receipt_address");
		} catch (JSONException je) {}
		try {
			transfer_type = jObject.getString("transfer_type");
		} catch (JSONException je) {}
		try {
			transfer_date = jObject.getString("transfer_date");
		} catch (JSONException je) {}
		try {
			transfer_details = jObject.getString("transfer_details");
		} catch (JSONException je) {}
		try {
			towards = jObject.getString("towards");
		} catch (JSONException je) {}
		try {
			amount = jObject.getString("amount");
		} catch (JSONException je) {}
		try {
			email_type = Integer.parseInt(jObject.getString("email_type"));
		} catch (JSONException je) {}
	}
    
	
	/**
	 * Constructs the SendMailObject from RegistrantSheetObject
	 *
	 */
    
	public SendMailObject (	RegistrantSheetObject rSObj, 
							String subject, 
							String body,
							String receipt_prefix ) {
		this.subject = subject;
		this.body = body.replaceAll("%%FIRST_NAME%%",rSObj.getRegistrantName());
		to = rSObj.getRegistrantEmail();
		if ( ! Util.trim(rSObj.getRegistrantAdditionalEmail()).equals("") )
			cc = rSObj.getRegistrantAdditionalEmail();
		registrant_id = rSObj.getRegistrantId() + "";
		SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		receipt_date = df.format(rSObj.getRegistrantReceiptDate());
		SimpleDateFormat df1 = new SimpleDateFormat("yyyy");
		receipt_year = df1.format(rSObj.getRegistrantReceiptDate());
		receipt_no = receipt_prefix + "/" + registrant_id;
		registrant_name = rSObj.getRegistrantName();
		registrant_last_name = rSObj.getRegistrantLastName();
		receipt_address = rSObj.getRegistrantAddress();
		transfer_type = rSObj.getRegistrantPaymentTypeName();
		transfer_date = df.format(rSObj.getRegistrantPaymentDate());
		transfer_details = rSObj.getRegistrantPaymentDetails();
		towards = rSObj.getRegistrantPaymentTowards();
		amount = rSObj.getRegistrantPaymentAmount() + rSObj.getRegistrantAdditionalAmount() + "";
		email_type = SendMailObject.RECEIPT_EMAIL;
	}
	
	/**
     *
     * Sets the <code>subject</code> field
     *
     * @param subject      String
     *
     */
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    
    /**
     *
     * Gets the <code>subject</code> field
     *
     * @returns subject
     *
     */
    
    public String getSubject() {
        return subject;
    }

    
    /**
     *
     * Sets the <code>body</code> field
     *
     * @param body      String
     *
     */
    
    public void setBody(String body) {
        this.body = body;
    }
    
    
    /**
     *
     * Gets the <code>body</code> field
     *
     * @returns body
     *
     */
    
    public String getBody() {
        return body;
    }
	
	/**
     *
     * Sets the <code>to</code> field
     *
     * @param to      String
     *
     */
    
    public void setTo(String to) {
        this.to = to;
    }
    
    
    /**
     *
     * Gets the <code>to</code> field
     *
     * @returns to
     *
     */
    
    public String getTo() {
        return to;
    }

    /**
     *
     * Sets the <code>cc</code> field
     *
     * @param cc      String
     *
     */
    
    public void setCc(String cc) {
        this.cc = cc;
    }
    
    
    /**
     *
     * Gets the <code>cc</code> field
     *
     * @returns cc
     *
     */
    
    public String getCc() {
        return cc;
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
     * Sets the <code>receipt_date</code> field
     *
     * @param receipt_date      String
     *
     */
    
    public void setReceiptDate(String receipt_date) {
        this.receipt_date = receipt_date;
    }
    
    
    /**
     *
     * Gets the <code>receipt_date</code> field
     *
     * @returns receipt_date
     *
     */
    
    public String getReceiptDate() {
        return receipt_date;
    }

	/**
     *
     * Sets the <code>receipt_year</code> field
     *
     * @param receipt_year     String
     *
     */
    
    public void setReceiptYear(String receipt_year) {
        this.receipt_year = receipt_year;
    }
    
    
    /**
     *
     * Gets the <code>receipt_year</code> field
     *
     * @returns receipt_year
     *
     */
    
    public String getReceiptYear() {
        return receipt_year;
    }
	
	/**
     *
     * Sets the <code>receipt_no</code> field
     *
     * @param receipt_no     String
     *
     */
    
    public void setReceiptNo(String receipt_no) {
        this.receipt_no = receipt_no;
    }
    
    
    /**
     *
     * Gets the <code>receipt_no</code> field
     *
     * @returns receipt_no
     *
     */
    
    public String getReceiptNo() {
        return receipt_no;
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
     * Sets the <code>receipt_address</code> field
     *
     * @param receipt_address      String
     *
     */
    
    public void setReceiptAddress(String receipt_address) {
        this.receipt_address = receipt_address;
    }
    
    
    /**
     *
     * Gets the <code>receipt_address</code> field
     *
     * @returns receipt_address
     *
     */
    
    public String getReceiptAddress() {
        return receipt_address;
    }
	
	/**
     *
     * Sets the <code>transfer_date</code> field
     *
     * @param transfer_date      String
     *
     */
    
    public void setTransferDate(String transfer_date) {
        this.transfer_date = transfer_date;
    }
    
    
    /**
     *
     * Gets the <code>transfer_date</code> field
     *
     * @returns transfer_date
     *
     */
    
    public String getTransferDate() {
        return transfer_date;
    }
	
	/**
     *
     * Sets the <code>transfer_details</code> field
     *
     * @param transfer_details      String
     *
     */
    
    public void setTransferDetails(String transfer_details) {
        this.transfer_details = transfer_details;
    }
    
    
    /**
     *
     * Gets the <code>transfer_details</code> field
     *
     * @returns transfer_details
     *
     */
    
    public String getTransferDetails() {
        return transfer_details;
    }
	
	/**
     *
     * Sets the <code>transfer_type</code> field
     *
     * @param transfer_type      String
     *
     */
    
    public void setTransferType(String transfer_type) {
        this.transfer_type = transfer_type;
    }
    
    
    /**
     *
     * Gets the <code>transfer_type</code> field
     *
     * @returns transfer_type
     *
     */
    
    public String getTransferType() {
        return transfer_type;
    }
	
	/**
     *
     * Sets the <code>towards</code> field
     *
     * @param towards      String
     *
     */
    
    public void setTowards(String towards) {
        this.towards = towards;
    }
    
    
    /**
     *
     * Gets the <code>towards</code> field
     *
     * @returns towards
     *
     */
    
    public String getTowards() {
        return towards;
    }
	
	/**
     *
     * Sets the <code>amount</code> field
     *
     * @param amount      String
     *
     */
    
    public void setAmount(String amount) {
        this.amount = amount;
    }
    
    
    /**
     *
     * Gets the <code>amount</code> field
     *
     * @returns amount
     *
     */
    
    public String getAmount() {
        return amount;
    }
	
	/**
     *
     * Sets the <code>email_type</code> field
     *
     * @param email_type      int
     *
     */
    
    public void setEmailType(int email_type) {
        this.email_type = email_type;
    }
    
    
    /**
     *
     * Gets the <code>email_type</code> field
     *
     * @returns email_type
     *
     */
    
    public int getEmailType() {
        return email_type;
    }
	
    /**
     *
     * Tests if this object equals <code>obj</code>
     *
     * @returns true if equals
     *
     */
    
    public boolean equals(Object obj) {
        SendMailObject other = (SendMailObject)obj;
        DebugHandler.finest("This: " + this);
        DebugHandler.finest("Other: " + other);
        return
            Util.trim(subject).equals(Util.trim(other.getSubject())) &&
            Util.trim(body) == other.getBody() &&
			Util.trim(to) == other.getTo() &&
			Util.trim(cc) == other.getCc() &&
            Util.trim(registrant_id).equals(Util.trim(other.getRegistrantId())) &&
			Util.trim(receipt_date).equals(Util.trim(other.getReceiptDate())) &&
			Util.trim(receipt_year).equals(Util.trim(other.getReceiptYear())) &&
			Util.trim(receipt_no).equals(Util.trim(other.getReceiptNo())) &&
            Util.trim(registrant_name).equals(Util.trim(other.getRegistrantName())) &&
            Util.trim(registrant_last_name).equals(Util.trim(other.getRegistrantLastName())) &&
			Util.trim(receipt_address).equals(Util.trim(other.getReceiptAddress())) &&
			Util.trim(transfer_type).equals(Util.trim(other.getTransferType())) &&
			Util.trim(transfer_date).equals(Util.trim(other.getTransferDate())) &&
			Util.trim(transfer_details).equals(Util.trim(other.getTransferDetails())) &&
			Util.trim(towards).equals(Util.trim(other.getTowards())) &&
			Util.trim(amount).equals(Util.trim(other.getAmount())) &&
            email_type == other.getEmailType();
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
