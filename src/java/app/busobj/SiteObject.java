/*
 * SiteObject.java
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
 * The implementation of the SiteObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class SiteObject implements Cloneable {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
	private int site_id;
	private String site_name;
	private String site_url;
	
	/**
	 *
	 * Returns the String representation of the SiteObject.
	 *
	 * @return	 Returns the String representation of the SiteObject.
	 *
	 */
    
	public String toString() {
		String buf="";
		buf += "site_id : " + site_id + "\n";
		buf += "site_name : " + site_name + "\n";
		buf += "site_url : " + site_url + "\n";
		return buf;
	}
    
	/**
	 *
	 * Returns the JSON representation of the SiteObject.
	 *
	 * @return      Returns the JSON representation of the SiteObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("site_id", site_id);
			 jo.put("site_name", site_name);
			 jo.put("site_url", site_url);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the SiteObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return site_id;
	}
    
	/**
	 * Constructs the SiteObject
	 *
	 */
    
	public SiteObject () {
		setSiteId(0);
		setSiteName("");
		setSiteUrl("");
	}
    
	/**
	 * Constructs the SiteObject from JSONObject
	 *
	 */
    
	public SiteObject (JSONObject jObject) {
		try {
			site_id = jObject.getInt("site_id");
		} catch (JSONException je) {}
		try {
			site_name = jObject.getString("site_name");
		} catch (JSONException je) {}
		try {
			site_url = jObject.getString("site_url");
		} catch (JSONException je) {}
	}
    
    
    /**
     *
     * Sets the <code>site_id</code> field
     *
     * @param site_id      int
     *
     */
    
    public void setSiteId(int site_id) {
        this.site_id = site_id;
    }
    
    
    /**
     *
     * Gets the <code>site_id</code> field
     *
     * @returns site_id
     *
     */
    
    public int getSiteId() {
        return site_id;
    }

    
    /**
     *
     * Sets the <code>site_name</code> field
     *
     * @param site_name      String
     *
     */
    
    public void setSiteName(String site_name) {
        this.site_name = site_name;
    }
    
    
    /**
     *
     * Gets the <code>site_name</code> field
     *
     * @returns site_name
     *
     */
    
    public String getSiteName() {
        return site_name;
    }

    
    /**
     *
     * Sets the <code>site_url</code> field
     *
     * @param site_url      String
     *
     */
    
    public void setSiteUrl(String site_url) {
        this.site_url = site_url;
    }
    
    
    /**
     *
     * Gets the <code>site_url</code> field
     *
     * @returns site_url
     *
     */
    
    public String getSiteUrl() {
        return site_url;
    }

    
    /**
     *
     * Tests if this object equals <code>obj</code>
     *
     * @returns true if equals
     *
     */
    
    public boolean equals(Object obj) {
        SiteObject other = (SiteObject)obj;
        DebugHandler.finest("This: " + this);
        DebugHandler.finest("Other: " + other);
        return
            site_id == other.getSiteId() &&
            Util.trim(site_name).equals(Util.trim(other.getSiteName())) &&
            Util.trim(site_url).equals(Util.trim(other.getSiteUrl()));
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
