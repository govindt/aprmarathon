/*
 * ResultObject.java
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
 * The implementation of the ResultObject which maps a table
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class ResultObject implements Cloneable {
	private int result_id;
	private int result_event;
	private int result_event_type;
	private int result_medal;
	private int result_winner;
	private int result_winner_registrant;
	private String result_score;
	private Date result_timing;
	
	/**
	 *
	 * Returns the String representation of the ResultObject.
	 *
	 * @return	 Returns the String representation of the ResultObject.
	 *
	 */
    
	public String toString() {
	   return	"result_id : " + result_id + "\n" +
		"result_event : " + result_event + "\n" +
		"result_event_type : " + result_event_type + "\n" +
		"result_medal : " + result_medal + "\n" +
		"result_winner : " + result_winner + "\n" +
		"result_winner_registrant : " + result_winner_registrant + "\n" +
		"result_score : " + result_score + "\n" +
		"result_timing : " + result_timing + "\n";
	}
    
	/**
	 *
	 * Returns the JSON representation of the ResultObject.
	 *
	 * @return      Returns the JSON representation of the ResultObject.
	 *
	 */
    
	public JSONObject toJSON() {
		JSONObject jo = new JSONObject();
		try {
			 jo.put("result_id", result_id);
			 jo.put("result_event", result_event);
			 jo.put("result_event_type", result_event_type);
			 jo.put("result_medal", result_medal);
			 jo.put("result_winner", result_winner);
			 jo.put("result_winner_registrant", result_winner_registrant);
			 jo.put("result_score", result_score);
			 jo.put("result_timing", result_timing);
		} catch (JSONException je) {}
		return jo;
	}
    
	/**
	 *
	 * Returns the hashCode representation of the ResultObject.
	 *
	 * @return      Returns the hashCode.
	 *
	*/
    
	public int hashCode() {
		return result_id;
	}
    
	/**
	 * Constructs the ResultObject
	 *
	 */
    
	public ResultObject () {
		setResultId(0);
		setResultEvent(0);
		setResultEventType(0);
		setResultMedal(0);
		setResultWinner(0);
		setResultWinnerRegistrant(0);
		setResultScore("");
		setResultTiming(null);
	}
    
	/**
	 * Constructs the ResultObject from JSONObject
	 *
	 */
    
	public ResultObject (JSONObject jObject) {
		try {
			result_id = jObject.getInt("result_id");
		} catch (JSONException je) {}
		try {
			result_event = jObject.getInt("result_event");
		} catch (JSONException je) {}
		try {
			result_event_type = jObject.getInt("result_event_type");
		} catch (JSONException je) {}
		try {
			result_medal = jObject.getInt("result_medal");
		} catch (JSONException je) {}
		try {
			result_winner = jObject.getInt("result_winner");
		} catch (JSONException je) {}
		try {
			result_winner_registrant = jObject.getInt("result_winner_registrant");
		} catch (JSONException je) {}
		try {
			result_score = jObject.getString("result_score");
		} catch (JSONException je) {}
		try {
			try {
				SimpleDateFormat dateFormatter = new SimpleDateFormat(Constants.DATE_FORMAT_STR);
				result_timing = dateFormatter.parse(jObject.getString("result_timing"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} catch (JSONException je) {}
	}
    
	
    /**
	 *
	 * Sets the <code>result_id</code> field
	 *
	 * @param result_id      int
	 *
	 */
    
	public void setResultId(int result_id) {
	    this.result_id = result_id;
	}
    
	
    /**
	 *
	 * Gets the <code>result_id</code> field
	 *
	 * @returns result_id
	 *
	 */
    
	public int getResultId() {
	    return result_id;
	}

	
    /**
	 *
	 * Sets the <code>result_event</code> field
	 *
	 * @param result_event      int
	 *
	 */
    
	public void setResultEvent(int result_event) {
	    this.result_event = result_event;
	}
    
	
    /**
	 *
	 * Gets the <code>result_event</code> field
	 *
	 * @returns result_event
	 *
	 */
    
	public int getResultEvent() {
	    return result_event;
	}

	
    /**
	 *
	 * Sets the <code>result_event_type</code> field
	 *
	 * @param result_event_type      int
	 *
	 */
    
	public void setResultEventType(int result_event_type) {
	    this.result_event_type = result_event_type;
	}
    
	
    /**
	 *
	 * Gets the <code>result_event_type</code> field
	 *
	 * @returns result_event_type
	 *
	 */
    
	public int getResultEventType() {
	    return result_event_type;
	}

	
    /**
	 *
	 * Sets the <code>result_medal</code> field
	 *
	 * @param result_medal      int
	 *
	 */
    
	public void setResultMedal(int result_medal) {
	    this.result_medal = result_medal;
	}
    
	
    /**
	 *
	 * Gets the <code>result_medal</code> field
	 *
	 * @returns result_medal
	 *
	 */
    
	public int getResultMedal() {
	    return result_medal;
	}

	
    /**
	 *
	 * Sets the <code>result_winner</code> field
	 *
	 * @param result_winner      int
	 *
	 */
    
	public void setResultWinner(int result_winner) {
	    this.result_winner = result_winner;
	}
    
	
    /**
	 *
	 * Gets the <code>result_winner</code> field
	 *
	 * @returns result_winner
	 *
	 */
    
	public int getResultWinner() {
	    return result_winner;
	}

	
    /**
	 *
	 * Sets the <code>result_winner_registrant</code> field
	 *
	 * @param result_winner_registrant      int
	 *
	 */
    
	public void setResultWinnerRegistrant(int result_winner_registrant) {
	    this.result_winner_registrant = result_winner_registrant;
	}
    
	
    /**
	 *
	 * Gets the <code>result_winner_registrant</code> field
	 *
	 * @returns result_winner_registrant
	 *
	 */
    
	public int getResultWinnerRegistrant() {
	    return result_winner_registrant;
	}

	
    /**
	 *
	 * Sets the <code>result_score</code> field
	 *
	 * @param result_score      String
	 *
	 */
    
	public void setResultScore(String result_score) {
	    this.result_score = result_score;
	}
    
	
    /**
	 *
	 * Gets the <code>result_score</code> field
	 *
	 * @returns result_score
	 *
	 */
    
	public String getResultScore() {
	    return result_score;
	}

	
    /**
	 *
	 * Sets the <code>result_timing</code> field
	 *
	 * @param result_timing      Date
	 *
	 */
    
	public void setResultTiming(Date result_timing) {
	    this.result_timing = result_timing;
	}
    
	
    /**
	 *
	 * Gets the <code>result_timing</code> field
	 *
	 * @returns result_timing
	 *
	 */
    
	public Date getResultTiming() {
	    return result_timing;
	}

	
    /**
	 *
	 * Tests if this object equals <code>obj</code>
	 *
	 * @returns true if equals
	 *
	 */
    
	public boolean equals(Object obj) {
	    ResultObject other = (ResultObject)obj;
	    DebugHandler.finest("This: " + this);
	    DebugHandler.finest("Other: " + other);
	    return
	        result_id == other.getResultId() &&
	        result_event == other.getResultEvent() &&
	        result_event_type == other.getResultEventType() &&
	        result_medal == other.getResultMedal() &&
	        result_winner == other.getResultWinner() &&
	        result_winner_registrant == other.getResultWinnerRegistrant() &&
	   Util.trim(result_score).equals(Util.trim(other.getResultScore())) &&
	   result_timing.equals(other.getResultTiming());
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
