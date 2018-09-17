/*
 * PersistentBloodGroup.java
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
import app.busobj.BloodGroupObject;

/**
 * The persistent implementation of the BloodGroupObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentBloodGroup extends PersistentObject {
	private BloodGroupObject bloodGroupObject;
	
	
    /**
	 * Constructs a Persistent Object for the BloodGroupObject
	 *
	 * @param bloodGroupObject    the BloodGroupObject 
	 */
    
	public PersistentBloodGroup (BloodGroupObject bloodGroupObject) {
	    this.bloodGroupObject = bloodGroupObject;
	}
    
	
    /**
	 * Returns the ArrayList of BloodGroupObject.
	 * It is Usually all the rows in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return     ArrayList of BloodGroupObject 
	 *
	 * @throws     DBException     If a database error occurs
	 *
	 * @see     #getResultObjects(ResultSet)
	 */
    
	public Object list() throws DBException {
	    PreparedSQLStatement sql = new PreparedSQLStatement();
	    String statement = "SELECT blood_group_id, blood_group_name from Blood_Group";
	    int index = 1;
	    sql.setStatement(statement);
        
	    setSQLStatement(sql);
        
	    @SuppressWarnings("unchecked")
	    ArrayList<BloodGroupObject> result = (ArrayList<BloodGroupObject>) super.list();
        
	    return result;
	}
    
	
    /**
	 * Returns the ArrayList of BloodGroupObjects.
	 * It is Usually all the rows that match the criteria in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return     ArrayList of BloodGroupObject 
	 *
	 * @throws     DBException     If a database error occurs
	 *
	 * @see     #getResultObjects(ResultSet)
	 */
    
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT blood_group_id, blood_group_name from Blood_Group";
		int index = 1;
		BloodGroupObject passedBloodGroupObject = (BloodGroupObject)args;
		boolean whereSpecified = false;

		if ( passedBloodGroupObject.getBloodGroupId() != 0 ) {
			statement += " where blood_group_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedBloodGroupObject.getBloodGroupId()), Types.INTEGER));
		}
		if ( ! passedBloodGroupObject.getBloodGroupName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where blood_group_name = ?";
				whereSpecified = true;
			} else
				statement += " and blood_group_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedBloodGroupObject.getBloodGroupName(), Types.VARCHAR));
		}
		sql.setStatement(statement);
        
		DebugHandler.debug(statement);
		setSQLStatement(sql);
        
		@SuppressWarnings("unchecked")
		ArrayList<BloodGroupObject> result = (ArrayList<BloodGroupObject>) super.list();
        
		return result;
	}
    
	
    /**
	 * Returns the ArrayList of one BloodGroupObject.
	 * It is Usually the row that matches primary key.
	 * This calls getResultSetObject method in the super class.
	 *
	 * @return     ArrayList of one BloodGroupObject 
	 *
	 * @throws     DBException     If a database error occurs
	 *
	 * @see     #getResultSetObject(ResultSet)
	 */
    
	public Object fetch() throws DBException {
	    PreparedSQLStatement sql = new PreparedSQLStatement();
	    String statement = "SELECT blood_group_id, blood_group_name from Blood_Group where blood_group_id = ? ";
	    int index = 1;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(bloodGroupObject.getBloodGroupId()), Types.INTEGER));
	    setSQLStatement(sql);
        
	    @SuppressWarnings("unchecked")
	    ArrayList<BloodGroupObject> result = (ArrayList<BloodGroupObject>) super.fetch();
        
	    return result;
	}
    
	
    /**
	 *
	 * Inserts a row in the database.  The values
	 * are got from the bloodGroupObject.
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
	        statement = "INSERT INTO Blood_Group (blood_group_id, blood_group_name) VALUES(?, ?) ";
	        sql.setStatement(statement);
	        sql.setInParams(new SQLParam(index++, new Integer(bloodGroupObject.getBloodGroupId()), Types.INTEGER));
	    } else {
	        statement = "INSERT INTO Blood_Group (blood_group_name) VALUES(?) ";
	        sql.setStatement(statement);
	    }
	    sql.setInParams(new SQLParam(index++,  bloodGroupObject.getBloodGroupName(), Types.VARCHAR));
	    setSQLStatement(sql);
        
	    Integer result = (Integer) super.insert();
        
	    return result;
	}
    
	
    /**
	 *
	 * Deletes a row in the database. The key is 
	 * in the bloodGroupObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return      Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws     DBException     If a database error occurs
	 */
    
	public Object delete() throws DBException {
	    PreparedSQLStatement sql = new PreparedSQLStatement();
	    String statement = "DELETE FROM Blood_Group WHERE blood_group_id = ? ";
	    int index = 1;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(bloodGroupObject.getBloodGroupId()), Types.INTEGER));
	    setSQLStatement(sql);
        
	    Integer result = (Integer) super.delete();
        
	    return result;
	}
    
	
    /**
	 *
	 * Updates a row in the database. The values are 
	 * got from the bloodGroupObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return      Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws     DBException     If a database error occurs
	 */
    
	public Object update() throws DBException {
	    PreparedSQLStatement sql = new PreparedSQLStatement();
	    String statement = "UPDATE Blood_Group SET blood_group_id = ?, blood_group_name = ? where blood_group_id = ? ";
	    int index = 1;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(bloodGroupObject.getBloodGroupId()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++,  bloodGroupObject.getBloodGroupName(), Types.VARCHAR));
	    sql.setInParams(new SQLParam(index++, new Integer(bloodGroupObject.getBloodGroupId()), Types.INTEGER));
	    setSQLStatement(sql);
        
	    Integer result = (Integer) super.update();
        
	    return result;
	}
    
	
    /**
	 *
	 * Returns a ArrayList of BloodGroupObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 * This is used by the list method.
	 *
	 * @param rs      the ResultSet.
	 *
	 * @return      Returns a ArrayList of BloodGroupObject from the ResultSet.
	 *
	 * @see     #list()
	 *
	 */
    
	public Object getResultObjects(ResultSet rs) {
	    ArrayList<BloodGroupObject> result = new ArrayList<BloodGroupObject>();
        
	    try {
	        while(rs.next()) {
	            int index = 1;
	            BloodGroupObject f = new BloodGroupObject();
	            f.setBloodGroupId(rs.getInt(index++));
	            f.setBloodGroupName(rs.getString(index++));
	            result.add(f);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
    
	
    /**
	 *
	 * Returns a BloodGroupObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 *
	 * This is used by the fetch method.
	 * @param rs      the ResultSet.
	 *
	 * @return      Returns a BloodGroupObject from the ResultSet.
	 *
	 * @see     #fetch()
	 *
	 */
    
	public Object getResultSetObject(ResultSet rs) {
	    try {
	    @SuppressWarnings("unchecked")
	        ArrayList<BloodGroupObject> result = (ArrayList<BloodGroupObject>) getResultObjects(rs);
	        return result.get(0);
	    } catch (Exception e) {
	        return null;
	    }
	}
}
    
