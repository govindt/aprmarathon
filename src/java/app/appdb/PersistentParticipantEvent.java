/*
 * PersistentParticipantEvent.java
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
import app.busobj.ParticipantEventObject;

/**
 * The persistent implementation of the ParticipantEventObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentParticipantEvent extends PersistentObject {
    private ParticipantEventObject participantEventObject;
    
    
    /**
     * Constructs a Persistent Object for the ParticipantEventObject
     *
     * @param participantEventObject    the ParticipantEventObject 
     */
    
    public PersistentParticipantEvent (ParticipantEventObject participantEventObject) {
        this.participantEventObject = participantEventObject;
    }
    
    
    /**
     * Returns the ArrayList of ParticipantEventObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of ParticipantEventObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT participant_event_id, participant_id, participant_event, participant_type, participant_event_type, participant_bib_no, participant_group from Participant_Event";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<ParticipantEventObject> result = (ArrayList<ParticipantEventObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of ParticipantEventObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of ParticipantEventObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list(Object args) throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT participant_event_id, participant_id, participant_event, participant_type, participant_event_type, participant_bib_no, participant_group from Participant_Event";
        int index = 1;
        ParticipantEventObject passedParticipantEventObject = (ParticipantEventObject)args;
        boolean whereSpecified = false;

        if ( passedParticipantEventObject.getParticipantEventId() != 0 ) {
	    statement += " where participant_event_id = ?";
	    whereSpecified = true;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedParticipantEventObject.getParticipantEventId()), Types.INTEGER));
	}
        if ( passedParticipantEventObject.getParticipantId() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where participant_id = ?";
		whereSpecified = true;
	    } else
		statement += " and participant_id = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedParticipantEventObject.getParticipantId()), Types.INTEGER));
	}
        if ( passedParticipantEventObject.getParticipantEvent() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where participant_event = ?";
		whereSpecified = true;
	    } else
		statement += " and participant_event = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedParticipantEventObject.getParticipantEvent()), Types.INTEGER));
	}
        if ( passedParticipantEventObject.getParticipantType() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where participant_type = ?";
		whereSpecified = true;
	    } else
		statement += " and participant_type = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedParticipantEventObject.getParticipantType()), Types.INTEGER));
	}
        if ( passedParticipantEventObject.getParticipantEventType() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where participant_event_type = ?";
		whereSpecified = true;
	    } else
		statement += " and participant_event_type = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedParticipantEventObject.getParticipantEventType()), Types.INTEGER));
	}
        if ( ! passedParticipantEventObject.getParticipantBibNo().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where participant_bib_no = ?";
		whereSpecified = true;
	    } else
		statement += " and participant_bib_no = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedParticipantEventObject.getParticipantBibNo(), Types.VARCHAR));
	}
        if ( passedParticipantEventObject.getParticipantGroup() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where participant_group = ?";
		whereSpecified = true;
	    } else
		statement += " and participant_group = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedParticipantEventObject.getParticipantGroup()), Types.INTEGER));
	}
        sql.setStatement(statement);
        
        DebugHandler.debug(statement);
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<ParticipantEventObject> result = (ArrayList<ParticipantEventObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of one ParticipantEventObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     ArrayList of one ParticipantEventObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT participant_event_id, participant_id, participant_event, participant_type, participant_event_type, participant_bib_no, participant_group from Participant_Event where participant_event_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantEventId()), Types.INTEGER));
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<ParticipantEventObject> result = (ArrayList<ParticipantEventObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the participantEventObject.
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
            statement = "INSERT INTO Participant_Event (participant_event_id, participant_id, participant_event, participant_type, participant_event_type, participant_bib_no, participant_group) VALUES(?, ?, ?, ?, ?, ?, ?) ";
            sql.setStatement(statement);
            sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantEventId()), Types.INTEGER));
        } else {
            statement = "INSERT INTO Participant_Event (participant_id, participant_event, participant_type, participant_event_type, participant_bib_no, participant_group) VALUES(?, ?, ?, ?, ?, ?) ";
            sql.setStatement(statement);
        }
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantEvent()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantType()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantEventType()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  participantEventObject.getParticipantBibNo(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantGroup()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the participantEventObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Participant_Event WHERE participant_event_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantEventId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the participantEventObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Participant_Event SET participant_event_id = ?, participant_id = ?, participant_event = ?, participant_type = ?, participant_event_type = ?, participant_bib_no = ?, participant_group = ? where participant_event_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantEventId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantEvent()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantType()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantEventType()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  participantEventObject.getParticipantBibNo(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantGroup()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(participantEventObject.getParticipantEventId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a ArrayList of ParticipantEventObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a ArrayList of ParticipantEventObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        ArrayList<ParticipantEventObject> result = new ArrayList<ParticipantEventObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                ParticipantEventObject f = new ParticipantEventObject();
                f.setParticipantEventId(rs.getInt(index++));
                f.setParticipantId(rs.getInt(index++));
                f.setParticipantEvent(rs.getInt(index++));
                f.setParticipantType(rs.getInt(index++));
                f.setParticipantEventType(rs.getInt(index++));
                f.setParticipantBibNo(rs.getString(index++));
                f.setParticipantGroup(rs.getInt(index++));
                result.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * Returns a ParticipantEventObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a ParticipantEventObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
        @SuppressWarnings("unchecked")
            ArrayList<ParticipantEventObject> result = (ArrayList<ParticipantEventObject>) getResultObjects(rs);
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
