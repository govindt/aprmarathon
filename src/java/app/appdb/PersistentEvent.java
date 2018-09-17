/*
 * PersistentEvent.java
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
import app.busobj.EventObject;

/**
 * The persistent implementation of the EventObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentEvent extends PersistentObject {
    private EventObject eventObject;
    
    
    /**
     * Constructs a Persistent Object for the EventObject
     *
     * @param eventObject    the EventObject 
     */
    
    public PersistentEvent (EventObject eventObject) {
        this.eventObject = eventObject;
    }
    
    
    /**
     * Returns the ArrayList of EventObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of EventObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT event_id, event_name, event_start_date, event_end_date, event_description, event_registation_close_date, event_changes_close_date from Event";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<EventObject> result = (ArrayList<EventObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of EventObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of EventObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT event_id, event_name, event_start_date, event_end_date, event_description, event_registation_close_date, event_changes_close_date from Event";
		int index = 1;
		EventObject passedEventObject = (EventObject)args;
		boolean whereSpecified = false;

		if ( passedEventObject.getEventId() != 0 ) {
			statement += " where event_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedEventObject.getEventId()), Types.INTEGER));
		}
		if ( ! passedEventObject.getEventName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where event_name = ?";
				whereSpecified = true;
			} else
				statement += " and event_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedEventObject.getEventName(), Types.VARCHAR));
		}
		if ( passedEventObject.getEventStartDate() != null ) {
			if ( ! whereSpecified ) {
				statement += " where event_start_date = ?";
				whereSpecified = true;
			} else
				statement += " and event_start_date = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedEventObject.getEventStartDate(), Types.TIMESTAMP));
		}
		if ( passedEventObject.getEventEndDate() != null ) {
			if ( ! whereSpecified ) {
				statement += " where event_end_date = ?";
				whereSpecified = true;
			} else
				statement += " and event_end_date = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedEventObject.getEventEndDate(), Types.TIMESTAMP));
		}
		if ( ! passedEventObject.getEventDescription().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where event_description = ?";
				whereSpecified = true;
			} else
				statement += " and event_description = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedEventObject.getEventDescription(), Types.VARCHAR));
		}
		if ( passedEventObject.getEventRegistationCloseDate() != null ) {
			if ( ! whereSpecified ) {
				statement += " where event_registation_close_date = ?";
				whereSpecified = true;
			} else
				statement += " and event_registation_close_date = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedEventObject.getEventRegistationCloseDate(), Types.TIMESTAMP));
		}
		if ( passedEventObject.getEventChangesCloseDate() != null ) {
			if ( ! whereSpecified ) {
				statement += " where event_changes_close_date = ?";
				whereSpecified = true;
			} else
				statement += " and event_changes_close_date = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedEventObject.getEventChangesCloseDate(), Types.TIMESTAMP));
		}
		sql.setStatement(statement);
        
		DebugHandler.debug(statement);
		setSQLStatement(sql);
        
		@SuppressWarnings("unchecked")
		ArrayList<EventObject> result = (ArrayList<EventObject>) super.list();
        
		return result;
	}
    
    
    /**
     * Returns the ArrayList of one EventObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     ArrayList of one EventObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT event_id, event_name, event_start_date, event_end_date, event_description, event_registation_close_date, event_changes_close_date from Event where event_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(eventObject.getEventId()), Types.INTEGER));
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<EventObject> result = (ArrayList<EventObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the eventObject.
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
            statement = "INSERT INTO Event (event_id, event_name, event_start_date, event_end_date, event_description, event_registation_close_date, event_changes_close_date) VALUES(?, ?, ?, ?, ?, ?, ?) ";
            sql.setStatement(statement);
            sql.setInParams(new SQLParam(index++, new Integer(eventObject.getEventId()), Types.INTEGER));
        } else {
            statement = "INSERT INTO Event (event_name, event_start_date, event_end_date, event_description, event_registation_close_date, event_changes_close_date) VALUES(?, ?, ?, ?, ?, ?) ";
            sql.setStatement(statement);
        }
        sql.setInParams(new SQLParam(index++,  eventObject.getEventName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  eventObject.getEventStartDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  eventObject.getEventEndDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  eventObject.getEventDescription(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  eventObject.getEventRegistationCloseDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  eventObject.getEventChangesCloseDate(), Types.TIMESTAMP));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the eventObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Event WHERE event_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(eventObject.getEventId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the eventObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Event SET event_id = ?, event_name = ?, event_start_date = ?, event_end_date = ?, event_description = ?, event_registation_close_date = ?, event_changes_close_date = ? where event_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(eventObject.getEventId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  eventObject.getEventName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  eventObject.getEventStartDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  eventObject.getEventEndDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  eventObject.getEventDescription(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  eventObject.getEventRegistationCloseDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  eventObject.getEventChangesCloseDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++, new Integer(eventObject.getEventId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a ArrayList of EventObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a ArrayList of EventObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        ArrayList<EventObject> result = new ArrayList<EventObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                EventObject f = new EventObject();
                f.setEventId(rs.getInt(index++));
                f.setEventName(rs.getString(index++));
                f.setEventStartDate(rs.getDate(index++));
                f.setEventEndDate(rs.getDate(index++));
                f.setEventDescription(rs.getString(index++));
                f.setEventRegistationCloseDate(rs.getDate(index++));
                f.setEventChangesCloseDate(rs.getDate(index++));
                result.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * Returns a EventObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a EventObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
        @SuppressWarnings("unchecked")
            ArrayList<EventObject> result = (ArrayList<EventObject>) getResultObjects(rs);
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
