/*
 * AgeCategoryObject.java
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
 * The implementation of the AgeCategoryObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class AgeCategoryObject implements Cloneable {
	private SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
	private int age_category_id;
	private String age_category;
	private int min_age;
	private int max_age;
	
	/**
	 *
	 * Returns the String representation of the AgeCategoryObject.
	 *
	 * @return	 Returns the String representation of the AgeCategoryObject.
	 *
	 */
	
	public String toString() {
		String buf="";
		buf += "age_category_id : " + age_category_id + "\n";
		buf += "age_category : " + age_category + "\n";
		buf += "min_age : " + min_age + "\n";
		buf += "max_age : " + max_age + "\n";
		return buf;
	}

	/**
	 *
	 * Returns the List representation of the AgeCategoryObject.
	 *
	 * Returns the object as an array list of Strings.
	 *
	 */
	
	public List<Object> asList() {
		List<Object> list = new ArrayList<Object>();
		list.add(age_category_id + "");
		list.add(age_category + "");
		list.add(min_age + "");
		list.add(max_age + "");
		return list;
	}

	/**
	 *
	 * Returns the JSON representation of the AgeCategoryObject.
	 *
	 * @return  	Returns the JSON representation of the AgeCategoryObject.
	 *
	 */
	
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			jo.put("age_category_id", age_category_id);
			jo.put("age_category", age_category);
			jo.put("min_age", min_age);
			jo.put("max_age", max_age);
		} catch (JSONException je) {}
		return jo;
	}
	
	/**
	 *
	 * Returns the hashCode representation of the AgeCategoryObject.
	 *
	 * @return	Returns the hashCode.
	 *
	*/
	
	public int hashCode() {
		return age_category_id;
	}
	
	/**
	 * Constructs the AgeCategoryObject
	 *
	 */
	
	public AgeCategoryObject () {
		setAgeCategoryId(0);
		setAgeCategory("");
		setMinAge(0);
		setMaxAge(0);
	}
	
	/**
	 * Constructs the AgeCategoryObject from JSONObject
	 *
	 */
	
	public AgeCategoryObject (JSONObject jObject) {
		try {
			age_category_id = jObject.getInt("age_category_id");
		} catch (JSONException je) {}
		try {
			age_category = jObject.getString("age_category");
		} catch (JSONException je) {age_category = "";}
		try {
			min_age = jObject.getInt("min_age");
		} catch (JSONException je) {}
		try {
			max_age = jObject.getInt("max_age");
		} catch (JSONException je) {}
	}
	
	
	/**
	 *
	 * Sets the <code>age_category_id</code> field
	 *
	 * @param age_category_id	  int
	 *
	 */
	
	public void setAgeCategoryId(int age_category_id) {
		this.age_category_id = age_category_id;
	}
	
	
	/**
	 *
	 * Gets the <code>age_category_id</code> field
	 *
	 * @returns age_category_id
	 *
	 */
	
	public int getAgeCategoryId() {
		return age_category_id;
	}

	
	/**
	 *
	 * Sets the <code>age_category</code> field
	 *
	 * @param age_category	  String
	 *
	 */
	
	public void setAgeCategory(String age_category) {
		this.age_category = age_category;
	}
	
	
	/**
	 *
	 * Gets the <code>age_category</code> field
	 *
	 * @returns age_category
	 *
	 */
	
	public String getAgeCategory() {
		return age_category;
	}

	
	/**
	 *
	 * Sets the <code>min_age</code> field
	 *
	 * @param min_age	  int
	 *
	 */
	
	public void setMinAge(int min_age) {
		this.min_age = min_age;
	}
	
	
	/**
	 *
	 * Gets the <code>min_age</code> field
	 *
	 * @returns min_age
	 *
	 */
	
	public int getMinAge() {
		return min_age;
	}

	
	/**
	 *
	 * Sets the <code>max_age</code> field
	 *
	 * @param max_age	  int
	 *
	 */
	
	public void setMaxAge(int max_age) {
		this.max_age = max_age;
	}
	
	
	/**
	 *
	 * Gets the <code>max_age</code> field
	 *
	 * @returns max_age
	 *
	 */
	
	public int getMaxAge() {
		return max_age;
	}

	
	/**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
	
	public boolean equals(Object obj) {
		AgeCategoryObject other = (AgeCategoryObject)obj;
		DebugHandler.finest("This: " + this);
		DebugHandler.finest("Other: " + other);
		return
			age_category_id == other.getAgeCategoryId() &&
			Util.trim(age_category).equals(Util.trim(other.getAgeCategory())) &&
			min_age == other.getMinAge() &&
			max_age == other.getMaxAge();
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
