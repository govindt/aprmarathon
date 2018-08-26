/*
 * PersistentEventType.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.appdb;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import core.db.PersistentObject;
import core.db.SQLParam;
import core.db.SQLStatement;
import core.db.PreparedSQLStatement;
import core.db.CallableSQLStatement;
import core.db.DBException;
import core.util.DebugHandler;
import core.util.Constants;
import app.util.AppConstants;
import app.busobj.EventTypeObject;

/**
 * The persistent implementation of the EventTypeObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentEventType extends PersistentObject {
    private EventTypeObject eventTypeObject;
    
    
    /**
     * Constructs a Persistent Object for the EventTypeObject
     *
     * @param eventTypeObject    the EventTypeObject 
     */
    
    public PersistentEventType (EventTypeObject eventTypeObject) {
        this.eventTypeObject = eventTypeObject;
    }
    
    
    /**
     * Returns the ArrayList of EventTypeObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of EventTypeObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT event_type_id, event_type_name, event, event_type_description, event_type_start_date, event_type_end_date, event_type_venue, online_registration_only from Event_Type";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<EventTypeObject> result = (ArrayList<EventTypeObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of EventTypeObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of EventTypeObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list(Object args) throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT event_type_id, event_type_name, event, event_type_description, event_type_start_date, event_type_end_date, event_type_venue, online_registration_only from Event_Type";
        int index = 1;
        EventTypeObject passedEventTypeObject = (EventTypeObject)args;
        boolean whereSpecified = false;

        if ( passedEventTypeObject.getEventTypeId() != 0 ) {
	    statement += " where event_type_id = ?";
	    whereSpecified = true;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedEventTypeObject.getEventTypeId()), Types.INTEGER));
	}
        if ( ! passedEventTypeObject.getEventTypeName().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where event_type_name = ?";
		whereSpecified = true;
	    } else
		statement += " and event_type_name = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedEventTypeObject.getEventTypeName(), Types.VARCHAR));
	}
        if ( passedEventTypeObject.getEvent() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where event = ?";
		whereSpecified = true;
	    } else
		statement += " and event = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedEventTypeObject.getEvent()), Types.INTEGER));
	}
        if ( ! passedEventTypeObject.getEventTypeDescription().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where event_type_description = ?";
		whereSpecified = true;
	    } else
		statement += " and event_type_description = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedEventTypeObject.getEventTypeDescription(), Types.VARCHAR));
	}
        if ( passedEventTypeObject.getEventTypeStartDate() != null ) {
	    if ( ! whereSpecified ) {
		statement += " where event_type_start_date = ?";
		whereSpecified = true;
	    } else
		statement += " and event_type_start_date = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedEventTypeObject.getEventTypeStartDate(), Types.TIMESTAMP));
	}
        if ( passedEventTypeObject.getEventTypeEndDate() != null ) {
	    if ( ! whereSpecified ) {
		statement += " where event_type_end_date = ?";
		whereSpecified = true;
	    } else
		statement += " and event_type_end_date = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedEventTypeObject.getEventTypeEndDate(), Types.TIMESTAMP));
	}
        if ( ! passedEventTypeObject.getEventTypeVenue().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where event_type_venue = ?";
		whereSpecified = true;
	    } else
		statement += " and event_type_venue = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedEventTypeObject.getEventTypeVenue(), Types.VARCHAR));
	}
        if ( ! passedEventTypeObject.getOnlineRegistrationOnly().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where online_registration_only = ?";
		whereSpecified = true;
	    } else
		statement += " and online_registration_only = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedEventTypeObject.getOnlineRegistrationOnly(), Types.VARCHAR));
	}
        sql.setStatement(statement);
        
        DebugHandler.debug(statement);
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<EventTypeObject> result = (ArrayList<EventTypeObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of one EventTypeObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     ArrayList of one EventTypeObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT event_type_id, event_type_name, event, event_type_description, event_type_start_date, event_type_end_date, event_type_venue, online_registration_only from Event_Type where event_type_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(eventTypeObject.getEventTypeId()), Types.INTEGER));
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<EventTypeObject> result = (ArrayList<EventTypeObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the eventTypeObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object insert() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement;
        int index = 1;

        if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
            statement = "INSERT INTO Event_Type (event_type_id, event_type_name, event, event_type_description, event_type_start_date, event_type_end_date, event_type_venue, online_registration_only) VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";
            sql.setStatement(statement);
            sql.setInParams(new SQLParam(index++, new Integer(eventTypeObject.getEventTypeId()), Types.INTEGER));
        } else {
            statement = "INSERT INTO Event_Type (event_type_name, event, event_type_description, event_type_start_date, event_type_end_date, event_type_venue, online_registration_only) VALUES(?, ?, ?, ?, ?, ?, ?) ";
            sql.setStatement(statement);
        }
        sql.setInParams(new SQLParam(index++,  eventTypeObject.getEventTypeName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(eventTypeObject.getEvent()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  eventTypeObject.getEventTypeDescription(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  eventTypeObject.getEventTypeStartDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  eventTypeObject.getEventTypeEndDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  eventTypeObject.getEventTypeVenue(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  eventTypeObject.getOnlineRegistrationOnly(), Types.VARCHAR));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the eventTypeObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Event_Type WHERE event_type_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(eventTypeObject.getEventTypeId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the eventTypeObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Event_Type SET event_type_id = ?, event_type_name = ?, event = ?, event_type_description = ?, event_type_start_date = ?, event_type_end_date = ?, event_type_venue = ?, online_registration_only = ? where event_type_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(eventTypeObject.getEventTypeId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  eventTypeObject.getEventTypeName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(eventTypeObject.getEvent()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  eventTypeObject.getEventTypeDescription(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  eventTypeObject.getEventTypeStartDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  eventTypeObject.getEventTypeEndDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  eventTypeObject.getEventTypeVenue(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  eventTypeObject.getOnlineRegistrationOnly(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(eventTypeObject.getEventTypeId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a ArrayList of EventTypeObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a ArrayList of EventTypeObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        ArrayList<EventTypeObject> result = new ArrayList<EventTypeObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                EventTypeObject f = new EventTypeObject();
                f.setEventTypeId(rs.getInt(index++));
                f.setEventTypeName(rs.getString(index++));
                f.setEvent(rs.getInt(index++));
                f.setEventTypeDescription(rs.getString(index++));
                f.setEventTypeStartDate(rs.getDate(index++));
                f.setEventTypeEndDate(rs.getDate(index++));
                f.setEventTypeVenue(rs.getString(index++));
                f.setOnlineRegistrationOnly(rs.getString(index++));
                result.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * Returns a EventTypeObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a EventTypeObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
        @SuppressWarnings("unchecked")
            ArrayList<EventTypeObject> result = (ArrayList<EventTypeObject>) getResultObjects(rs);
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
