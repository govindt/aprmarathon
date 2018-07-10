/*
 * PersistentMedal.java
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
import app.busobj.MedalObject;

/**
 * The persistent implementation of the MedalObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentMedal extends PersistentObject {
    private MedalObject medalObject;
    
    
    /**
     * Constructs a Persistent Object for the MedalObject
     *
     * @param medalObject    the MedalObject 
     */
    
    public PersistentMedal (MedalObject medalObject) {
        this.medalObject = medalObject;
    }
    
    
    /**
     * Returns the ArrayList of MedalObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of MedalObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT medal_id, medal_name, medal_rank from Medal";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<MedalObject> result = (ArrayList<MedalObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of MedalObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of MedalObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list(Object args) throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT medal_id, medal_name, medal_rank from Medal";
        int index = 1;
        MedalObject passedMedalObject = (MedalObject)args;
        boolean whereSpecified = false;

        if ( passedMedalObject.getMedalId() != 0 ) {
	    statement += " where medal_id = ?";
	    whereSpecified = true;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedMedalObject.getMedalId()), Types.INTEGER));
	}
        if ( ! passedMedalObject.getMedalName().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where medal_name = ?";
		whereSpecified = true;
	    } else
		statement += " and medal_name = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedMedalObject.getMedalName(), Types.VARCHAR));
	}
        if ( passedMedalObject.getMedalRank() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where medal_rank = ?";
		whereSpecified = true;
	    } else
		statement += " and medal_rank = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedMedalObject.getMedalRank()), Types.INTEGER));
	}
        sql.setStatement(statement);
        
        DebugHandler.debug(statement);
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<MedalObject> result = (ArrayList<MedalObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of one MedalObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     ArrayList of one MedalObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT medal_id, medal_name, medal_rank from Medal where medal_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(medalObject.getMedalId()), Types.INTEGER));
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<MedalObject> result = (ArrayList<MedalObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the medalObject.
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
            statement = "INSERT INTO Medal (medal_id, medal_name, medal_rank) VALUES(?, ?, ?) ";
            sql.setStatement(statement);
            sql.setInParams(new SQLParam(index++, new Integer(medalObject.getMedalId()), Types.INTEGER));
        } else {
            statement = "INSERT INTO Medal (medal_name, medal_rank) VALUES(?, ?) ";
            sql.setStatement(statement);
        }
        sql.setInParams(new SQLParam(index++,  medalObject.getMedalName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(medalObject.getMedalRank()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the medalObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Medal WHERE medal_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(medalObject.getMedalId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the medalObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Medal SET medal_id = ?, medal_name = ?, medal_rank = ? where medal_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(medalObject.getMedalId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  medalObject.getMedalName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(medalObject.getMedalRank()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(medalObject.getMedalId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a ArrayList of MedalObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a ArrayList of MedalObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        ArrayList<MedalObject> result = new ArrayList<MedalObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                MedalObject f = new MedalObject();
                f.setMedalId(rs.getInt(index++));
                f.setMedalName(rs.getString(index++));
                f.setMedalRank(rs.getInt(index++));
                result.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * Returns a MedalObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a MedalObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
        @SuppressWarnings("unchecked")
            ArrayList<MedalObject> result = (ArrayList<MedalObject>) getResultObjects(rs);
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
