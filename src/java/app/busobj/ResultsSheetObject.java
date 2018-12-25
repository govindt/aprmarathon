/*
 * ResultsSheetObject.java
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

public class ResultsSheetObject implements Cloneable {
	private String results_bib_no;
	private String results_event_type;
	private String results_age_category;
	private String results_gender;
	private String results_gun_time;
	private String results_net_time;
	private int results_category_rank;
	private String results_db_operation;
	
	/**
	 *
	 * Returns the String representation of the RegistrantSheetObject.
	 *
	 * @return	 Returns the String representation of the RegistrantSheetObject.
	 *
	 */
    
	public String toString() {
	   return	"results_bib_no : " + results_bib_no + "\n" +
	    "results_event_type : " + results_event_type + "\n" +
		"results_age_category : " + results_age_category + "\n" +
		"results_gender : " + results_gender + "\n" +
		"results_gun_time : " + results_gun_time + "\n" +
		"results_net_time : " + results_net_time + "\n" +
		"results_category_rank : " + results_category_rank + "\n" +
		"results_db_operation : " + results_db_operation; 
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
			 jo.put("results_bib_no", results_bib_no);
			 jo.put("results_event_type", results_event_type);
			 jo.put("results_age_category", results_age_category);
			 jo.put("results_gender", results_gender);
			 jo.put("results_gun_time", results_gun_time);
			 jo.put("results_net_time", results_net_time);
			 jo.put("results_category_rank", results_category_rank);
			 jo.put("results_db_operation", results_db_operation);
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
		return results_bib_no.hashCode();
	}
    
	/**
	 * Constructs the ResultsSheetObject
	 *
	 */
    
	public ResultsSheetObject () {
		setResultsBibNo("");
		setResultsEventType("");
		setResultsAgeCategory("");
		setResultsGender("");
		setResultsGunTime("");
		setResultsNetTime("");
		setResultsCategoryRank(0);
		setResultsDbOperation(Constants.INFO_STR);
	}
    
	/**
	 * Constructs the RegistrantSheetObject from JSONObject
	 *
	 */
    
	public ResultsSheetObject (JSONObject jObject) {
		try {
			results_bib_no = jObject.getString("results_bib_no");
		} catch (JSONException je) {}
		try {
			results_event_type = jObject.getString("results_event_type");
		} catch (JSONException je) {}
		try {
			results_age_category = jObject.getString("results_age_category");
		} catch (JSONException je) {}
		try {
			results_gender = jObject.getString("results_gender");
		} catch (JSONException je) {}
		try {
			results_gun_time = jObject.getString("results_gun_time");
		} catch (JSONException je) {}
		try {
			results_net_time = jObject.getString("results_net_time");
		} catch (JSONException je) {}
		try {
			results_category_rank = jObject.getInt("results_category_rank");
		} catch (JSONException je) {}
		try {
			results_db_operation = jObject.getString("results_db_operation");
		} catch (JSONException je) {}
	}
	
   
    
    /**
     *
     * Sets the <code>results_bib_no</code> field
     *
     * @param results_bib_no      String
     *
     */
    
    public void setResultsBibNo(String results_bib_no) {
        this.results_bib_no = results_bib_no;
    }
    
    
    /**
     *
     * Gets the <code>results_bib_no</code> field
     *
     * @returns results_bib_no
     *
     */
    
    public String getResultsBibNo() {
        return results_bib_no;
    }
	
	/**
     *
     * Sets the <code>results_event_type</code> field
     *
     * @param results_event_type      String
     *
     */
    
    public void setResultsEventType(String results_event_type) {
        this.results_event_type = results_event_type;
    }
    
    
    /**
     *
     * Gets the <code>results_event_type</code> field
     *
     * @returns results_event_type
     *
     */
    
    public String getResultsEventType() {
        return results_event_type;
    }
	
	/**
     *
     * Sets the <code>results_age_category</code> field
     *
     * @param results_age_category      String
     *
     */
    
    public void setResultsAgeCategory(String results_age_category) {
        this.results_age_category = results_age_category;
    }
    
    
    /**
     *
     * Gets the <code>results_age_category</code> field
     *
     * @returns results_age_category
     *
     */
    
    public String getResultsAgeCategory() {
        return results_age_category;
    }
	
	/**
     *
     * Sets the <code>results_gender</code> field
     *
     * @param results_gender      String
     *
     */
    
    public void setResultsGender(String results_gender) {
        this.results_gender = results_gender;
    }
    
    /**
     *
     * Gets the <code>results_gender</code> field
     *
     * @returns results_gender
     *
     */
    
    public String getResultsGender() {
        return results_gender;
    }

	/**
     *
     * Sets the <code>results_gun_time</code> field
     *
     * @param results_gender      String
     *
     */
    
    public void setResultsGunTime(String results_gun_time) {
        this.results_gun_time = results_gun_time;
    }
    
    /**
     *
     * Gets the <code>results_gun_time</code> field
     *
     * @returns results_gun_time
     *
     */
    
    public String getResultsGunTime() {
        return results_gun_time;
    }

	/**
     *
     * Sets the <code>results_net_time</code> field
     *
     * @param results_net_time      String
     *
     */
    
    public void setResultsNetTime(String results_net_time) {
        this.results_net_time = results_net_time;
    }
    
    /**
     *
     * Gets the <code>results_net_time</code> field
     *
     * @returns results_net_time
     *
     */
    
    public String getResultsNetTime() {
        return results_net_time;
    }
	
	 /**
     *
     * Sets the <code>results_category_rank</code> field
     *
     * @param results_category_rank      int
     *
     */
    
    public void setResultsCategoryRank(int results_category_rank) {
        this.results_category_rank = results_category_rank;
    }
    
    
    /**
     *
     * Gets the <code>results_category_rank</code> field
     *
     * @returns results_category_rank
     *
     */
    
    public int getResultsCategoryRank() {
        return results_category_rank;
    }
    
    /**
     *
     * Gets the <code>results_db_operation</code> field
     *
     * @returns registraresults_db_operationnt_db_operation
     *
     */
    
    public String getResultsDbOperation() {
        return results_db_operation;
    }

	/**
     *
     * Sets the <code>results_db_operation</code> field
     *
     * @param results_db_operation      String
     *
     */
    
    public void setResultsDbOperation(String results_db_operation) {
        this.results_db_operation = results_db_operation;
    }
    
    /**
     *
     * Tests if this object equals <code>obj</code>
     *
     * @returns true if equals
     *
     */
    
    public boolean equals(Object obj) {
        ResultsSheetObject other = (ResultsSheetObject)obj;
        DebugHandler.finest("This: " + this);
        DebugHandler.finest("Other: " + other);
        return Util.trim(results_bib_no).equals(Util.trim(other.getResultsBibNo())) &&
            Util.trim(results_event_type).equals(Util.trim(other.getResultsEventType())) &&
            Util.trim(results_age_category).equals(Util.trim(other.getResultsAgeCategory())) &&
            Util.trim(results_gender).equals(Util.trim(other.getResultsGender())) &&
            Util.trim(results_gun_time).equals(Util.trim(other.getResultsGunTime())) &&
            Util.trim(results_net_time).equals(Util.trim(other.getResultsNetTime())) &&
            results_category_rank == other.getResultsCategoryRank() &&
			Util.trim(results_db_operation).equals(Util.trim(other.getResultsDbOperation()));
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
