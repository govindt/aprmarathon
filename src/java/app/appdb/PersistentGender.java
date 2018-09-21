/*
 * PersistentGender.java
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
import app.busobj.GenderObject;

/**
 * The persistent implementation of the GenderObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentGender extends PersistentObject {
	private GenderObject genderObject;
	
	
	/**
	 * Constructs a Persistent Object for the GenderObject
	 *
	 * @param genderObject	the GenderObject 
	 */
	
	public PersistentGender (GenderObject genderObject) {
		this.genderObject = genderObject;
	}
	
	
	/**
	 * Returns the ArrayList of GenderObject.
	 * It is Usually all the rows in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	ArrayList of GenderObject 
	 *
	 * @throws	DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT gender_id, gender_name from Gender";
		int index = 1;
		sql.setStatement(statement);
		
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<GenderObject> result = (ArrayList<GenderObject>) super.list();
		
	return result;
	}
	
	
	/**
	 * Returns the ArrayList of GenderObjects.
	 * It is Usually all the rows that match the criteria in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	 ArrayList of GenderObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT gender_id, gender_name from Gender";
		int index = 1;
		GenderObject passedGenderObject = (GenderObject)args;
		boolean whereSpecified = false;

		if ( passedGenderObject.getGenderId() != 0 ) {
			statement += " where gender_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedGenderObject.getGenderId()), Types.INTEGER));
		}
		if ( ! passedGenderObject.getGenderName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where gender_name = ?";
				whereSpecified = true;
			} else
				statement += " and gender_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedGenderObject.getGenderName(), Types.VARCHAR));
		}
		sql.setStatement(statement);
		
		DebugHandler.debug(statement);
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<GenderObject> result = (ArrayList<GenderObject>) super.list();
		
		return result;
	}
	
	
	/**
	 * Returns the ArrayList of one GenderObject.
	 * It is Usually the row that matches primary key.
	 * This calls getResultSetObject method in the super class.
	 *
	 * @return	 ArrayList of one GenderObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultSetObject(ResultSet)
	 */
	
	public Object fetch() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT gender_id, gender_name from Gender where gender_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(genderObject.getGenderId()), Types.INTEGER));
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<GenderObject> result = (ArrayList<GenderObject>) super.fetch();
		
		return result;
	}
	
	
	/**
	 *
	 * Inserts a row in the database.  The values
	 * are got from the genderObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object insert() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement;
		int index = 1;

		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			statement = "INSERT INTO Gender (gender_id, gender_name) VALUES(?, ?) ";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(genderObject.getGenderId()), Types.INTEGER));
		} else {
			statement = "INSERT INTO Gender (gender_name) VALUES(?) ";
			sql.setStatement(statement);
		}
		sql.setInParams(new SQLParam(index++,  genderObject.getGenderName(), Types.VARCHAR));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.insert();
		
		return result;
	}
	
	
	/**
	 *
	 * Deletes a row in the database. The key is 
	 * in the genderObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object delete() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "DELETE FROM Gender WHERE gender_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(genderObject.getGenderId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.delete();
		
		return result;
	}
	
	
	/**
	 *
	 * Updates a row in the database. The values are 
	 * got from the genderObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object update() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "UPDATE Gender SET gender_id = ?, gender_name = ? where gender_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(genderObject.getGenderId()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++,  genderObject.getGenderName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++, new Integer(genderObject.getGenderId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.update();
		
		return result;
	}
	
	
	/**
	 *
	 * Returns a ArrayList of GenderObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 * This is used by the list method.
	 *
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a ArrayList of GenderObject from the ResultSet.
	 *
	 * @see	 #list()
	 *
	 */
	
	public Object getResultObjects(ResultSet rs) {
		ArrayList<GenderObject> result = new ArrayList<GenderObject>();
		try {
			while(rs.next()) {
				int index = 1;
				GenderObject f = new GenderObject();
				f.setGenderId(rs.getInt(index++));
				f.setGenderName(rs.getString(index++));
				result.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 *
	 * Returns a GenderObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 *
	 * This is used by the fetch method.
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a GenderObject from the ResultSet.
	 *
	 * @see	 #fetch()
	 *
	 */
	
	public Object getResultSetObject(ResultSet rs) {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<GenderObject> result = (ArrayList<GenderObject>) getResultObjects(rs);
			return result.get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
	
