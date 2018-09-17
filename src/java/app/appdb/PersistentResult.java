/*
 * PersistentResult.java
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
import app.busobj.ResultObject;

/**
 * The persistent implementation of the ResultObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentResult extends PersistentObject {
	private ResultObject resultObject;
	
	
    /**
	 * Constructs a Persistent Object for the ResultObject
	 *
	 * @param resultObject    the ResultObject 
	 */
    
	public PersistentResult (ResultObject resultObject) {
	    this.resultObject = resultObject;
	}
    
	
    /**
	 * Returns the ArrayList of ResultObject.
	 * It is Usually all the rows in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return     ArrayList of ResultObject 
	 *
	 * @throws     DBException     If a database error occurs
	 *
	 * @see     #getResultObjects(ResultSet)
	 */
    
	public Object list() throws DBException {
	    PreparedSQLStatement sql = new PreparedSQLStatement();
	    String statement = "SELECT result_id, result_event, result_event_type, result_medal, result_winner, result_winner_registrant, result_score, result_timing from Result";
	    int index = 1;
	    sql.setStatement(statement);
        
	    setSQLStatement(sql);
        
	    @SuppressWarnings("unchecked")
	    ArrayList<ResultObject> result = (ArrayList<ResultObject>) super.list();
        
	    return result;
	}
    
	
    /**
	 * Returns the ArrayList of ResultObjects.
	 * It is Usually all the rows that match the criteria in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return     ArrayList of ResultObject 
	 *
	 * @throws     DBException     If a database error occurs
	 *
	 * @see     #getResultObjects(ResultSet)
	 */
    
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT result_id, result_event, result_event_type, result_medal, result_winner, result_winner_registrant, result_score, result_timing from Result";
		int index = 1;
		ResultObject passedResultObject = (ResultObject)args;
		boolean whereSpecified = false;

		if ( passedResultObject.getResultId() != 0 ) {
			statement += " where result_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedResultObject.getResultId()), Types.INTEGER));
		}
		if ( passedResultObject.getResultEvent() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where result_event = ?";
				whereSpecified = true;
			} else
				statement += " and result_event = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedResultObject.getResultEvent()), Types.INTEGER));
		}
		if ( passedResultObject.getResultEventType() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where result_event_type = ?";
				whereSpecified = true;
			} else
				statement += " and result_event_type = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedResultObject.getResultEventType()), Types.INTEGER));
		}
		if ( passedResultObject.getResultMedal() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where result_medal = ?";
				whereSpecified = true;
			} else
				statement += " and result_medal = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedResultObject.getResultMedal()), Types.INTEGER));
		}
		if ( passedResultObject.getResultWinner() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where result_winner = ?";
				whereSpecified = true;
			} else
				statement += " and result_winner = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedResultObject.getResultWinner()), Types.INTEGER));
		}
		if ( passedResultObject.getResultWinnerRegistrant() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where result_winner_registrant = ?";
				whereSpecified = true;
			} else
				statement += " and result_winner_registrant = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedResultObject.getResultWinnerRegistrant()), Types.INTEGER));
		}
		if ( ! passedResultObject.getResultScore().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where result_score = ?";
				whereSpecified = true;
			} else
				statement += " and result_score = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedResultObject.getResultScore(), Types.VARCHAR));
		}
		if ( passedResultObject.getResultTiming() != null ) {
			if ( ! whereSpecified ) {
				statement += " where result_timing = ?";
				whereSpecified = true;
			} else
				statement += " and result_timing = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedResultObject.getResultTiming(), Types.TIMESTAMP));
		}
		sql.setStatement(statement);
        
		DebugHandler.debug(statement);
		setSQLStatement(sql);
        
		@SuppressWarnings("unchecked")
		ArrayList<ResultObject> result = (ArrayList<ResultObject>) super.list();
        
		return result;
	}
    
	
    /**
	 * Returns the ArrayList of one ResultObject.
	 * It is Usually the row that matches primary key.
	 * This calls getResultSetObject method in the super class.
	 *
	 * @return     ArrayList of one ResultObject 
	 *
	 * @throws     DBException     If a database error occurs
	 *
	 * @see     #getResultSetObject(ResultSet)
	 */
    
	public Object fetch() throws DBException {
	    PreparedSQLStatement sql = new PreparedSQLStatement();
	    String statement = "SELECT result_id, result_event, result_event_type, result_medal, result_winner, result_winner_registrant, result_score, result_timing from Result where result_id = ? ";
	    int index = 1;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultId()), Types.INTEGER));
	    setSQLStatement(sql);
        
	    @SuppressWarnings("unchecked")
	    ArrayList<ResultObject> result = (ArrayList<ResultObject>) super.fetch();
        
	    return result;
	}
    
	
    /**
	 *
	 * Inserts a row in the database.  The values
	 * are got from the resultObject.
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
	        statement = "INSERT INTO Result (result_id, result_event, result_event_type, result_medal, result_winner, result_winner_registrant, result_score, result_timing) VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";
	        sql.setStatement(statement);
	        sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultId()), Types.INTEGER));
	    } else {
	        statement = "INSERT INTO Result (result_event, result_event_type, result_medal, result_winner, result_winner_registrant, result_score, result_timing) VALUES(?, ?, ?, ?, ?, ?, ?) ";
	        sql.setStatement(statement);
	    }
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultEvent()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultEventType()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultMedal()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultWinner()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultWinnerRegistrant()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++,  resultObject.getResultScore(), Types.VARCHAR));
	    sql.setInParams(new SQLParam(index++,  resultObject.getResultTiming(), Types.TIMESTAMP));
	    setSQLStatement(sql);
        
	    Integer result = (Integer) super.insert();
        
	    return result;
	}
    
	
    /**
	 *
	 * Deletes a row in the database. The key is 
	 * in the resultObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return      Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws     DBException     If a database error occurs
	 */
    
	public Object delete() throws DBException {
	    PreparedSQLStatement sql = new PreparedSQLStatement();
	    String statement = "DELETE FROM Result WHERE result_id = ? ";
	    int index = 1;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultId()), Types.INTEGER));
	    setSQLStatement(sql);
        
	    Integer result = (Integer) super.delete();
        
	    return result;
	}
    
	
    /**
	 *
	 * Updates a row in the database. The values are 
	 * got from the resultObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return      Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws     DBException     If a database error occurs
	 */
    
	public Object update() throws DBException {
	    PreparedSQLStatement sql = new PreparedSQLStatement();
	    String statement = "UPDATE Result SET result_id = ?, result_event = ?, result_event_type = ?, result_medal = ?, result_winner = ?, result_winner_registrant = ?, result_score = ?, result_timing = ? where result_id = ? ";
	    int index = 1;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultId()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultEvent()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultEventType()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultMedal()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultWinner()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultWinnerRegistrant()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++,  resultObject.getResultScore(), Types.VARCHAR));
	    sql.setInParams(new SQLParam(index++,  resultObject.getResultTiming(), Types.TIMESTAMP));
	    sql.setInParams(new SQLParam(index++, new Integer(resultObject.getResultId()), Types.INTEGER));
	    setSQLStatement(sql);
        
	    Integer result = (Integer) super.update();
        
	    return result;
	}
    
	
    /**
	 *
	 * Returns a ArrayList of ResultObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 * This is used by the list method.
	 *
	 * @param rs      the ResultSet.
	 *
	 * @return      Returns a ArrayList of ResultObject from the ResultSet.
	 *
	 * @see     #list()
	 *
	 */
    
	public Object getResultObjects(ResultSet rs) {
	    ArrayList<ResultObject> result = new ArrayList<ResultObject>();
        
	    try {
	        while(rs.next()) {
	            int index = 1;
	            ResultObject f = new ResultObject();
	            f.setResultId(rs.getInt(index++));
	            f.setResultEvent(rs.getInt(index++));
	            f.setResultEventType(rs.getInt(index++));
	            f.setResultMedal(rs.getInt(index++));
	            f.setResultWinner(rs.getInt(index++));
	            f.setResultWinnerRegistrant(rs.getInt(index++));
	            f.setResultScore(rs.getString(index++));
	            f.setResultTiming(rs.getDate(index++));
	            result.add(f);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
    
	
    /**
	 *
	 * Returns a ResultObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 *
	 * This is used by the fetch method.
	 * @param rs      the ResultSet.
	 *
	 * @return      Returns a ResultObject from the ResultSet.
	 *
	 * @see     #fetch()
	 *
	 */
    
	public Object getResultSetObject(ResultSet rs) {
	    try {
	    @SuppressWarnings("unchecked")
	        ArrayList<ResultObject> result = (ArrayList<ResultObject>) getResultObjects(rs);
	        return result.get(0);
	    } catch (Exception e) {
	        return null;
	    }
	}
}
    
