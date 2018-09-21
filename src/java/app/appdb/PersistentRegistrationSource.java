/*
 * PersistentRegistrationSource.java
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
import app.busobj.RegistrationSourceObject;

/**
 * The persistent implementation of the RegistrationSourceObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentRegistrationSource extends PersistentObject {
	private RegistrationSourceObject registrationSourceObject;
	
	
	/**
	 * Constructs a Persistent Object for the RegistrationSourceObject
	 *
	 * @param registrationSourceObject	the RegistrationSourceObject 
	 */
	
	public PersistentRegistrationSource (RegistrationSourceObject registrationSourceObject) {
		this.registrationSourceObject = registrationSourceObject;
	}
	
	
	/**
	 * Returns the ArrayList of RegistrationSourceObject.
	 * It is Usually all the rows in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	ArrayList of RegistrationSourceObject 
	 *
	 * @throws	DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT registration_source_id, registration_source_name from Registration_Source";
		int index = 1;
		sql.setStatement(statement);
		
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<RegistrationSourceObject> result = (ArrayList<RegistrationSourceObject>) super.list();
		
	return result;
	}
	
	
	/**
	 * Returns the ArrayList of RegistrationSourceObjects.
	 * It is Usually all the rows that match the criteria in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	 ArrayList of RegistrationSourceObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT registration_source_id, registration_source_name from Registration_Source";
		int index = 1;
		RegistrationSourceObject passedRegistrationSourceObject = (RegistrationSourceObject)args;
		boolean whereSpecified = false;

		if ( passedRegistrationSourceObject.getRegistrationSourceId() != 0 ) {
			statement += " where registration_source_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrationSourceObject.getRegistrationSourceId()), Types.INTEGER));
		}
		if ( ! passedRegistrationSourceObject.getRegistrationSourceName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registration_source_name = ?";
				whereSpecified = true;
			} else
				statement += " and registration_source_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrationSourceObject.getRegistrationSourceName(), Types.VARCHAR));
		}
		sql.setStatement(statement);
		
		DebugHandler.debug(statement);
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<RegistrationSourceObject> result = (ArrayList<RegistrationSourceObject>) super.list();
		
		return result;
	}
	
	
	/**
	 * Returns the ArrayList of one RegistrationSourceObject.
	 * It is Usually the row that matches primary key.
	 * This calls getResultSetObject method in the super class.
	 *
	 * @return	 ArrayList of one RegistrationSourceObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultSetObject(ResultSet)
	 */
	
	public Object fetch() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT registration_source_id, registration_source_name from Registration_Source where registration_source_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(registrationSourceObject.getRegistrationSourceId()), Types.INTEGER));
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<RegistrationSourceObject> result = (ArrayList<RegistrationSourceObject>) super.fetch();
		
		return result;
	}
	
	
	/**
	 *
	 * Inserts a row in the database.  The values
	 * are got from the registrationSourceObject.
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
			statement = "INSERT INTO Registration_Source (registration_source_id, registration_source_name) VALUES(?, ?) ";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(registrationSourceObject.getRegistrationSourceId()), Types.INTEGER));
		} else {
			statement = "INSERT INTO Registration_Source (registration_source_name) VALUES(?) ";
			sql.setStatement(statement);
		}
		sql.setInParams(new SQLParam(index++,  registrationSourceObject.getRegistrationSourceName(), Types.VARCHAR));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.insert();
		
		return result;
	}
	
	
	/**
	 *
	 * Deletes a row in the database. The key is 
	 * in the registrationSourceObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object delete() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "DELETE FROM Registration_Source WHERE registration_source_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(registrationSourceObject.getRegistrationSourceId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.delete();
		
		return result;
	}
	
	
	/**
	 *
	 * Updates a row in the database. The values are 
	 * got from the registrationSourceObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object update() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "UPDATE Registration_Source SET registration_source_id = ?, registration_source_name = ? where registration_source_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(registrationSourceObject.getRegistrationSourceId()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++,  registrationSourceObject.getRegistrationSourceName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++, new Integer(registrationSourceObject.getRegistrationSourceId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.update();
		
		return result;
	}
	
	
	/**
	 *
	 * Returns a ArrayList of RegistrationSourceObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 * This is used by the list method.
	 *
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a ArrayList of RegistrationSourceObject from the ResultSet.
	 *
	 * @see	 #list()
	 *
	 */
	
	public Object getResultObjects(ResultSet rs) {
		ArrayList<RegistrationSourceObject> result = new ArrayList<RegistrationSourceObject>();
		try {
			while(rs.next()) {
				int index = 1;
				RegistrationSourceObject f = new RegistrationSourceObject();
				f.setRegistrationSourceId(rs.getInt(index++));
				f.setRegistrationSourceName(rs.getString(index++));
				result.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 *
	 * Returns a RegistrationSourceObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 *
	 * This is used by the fetch method.
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a RegistrationSourceObject from the ResultSet.
	 *
	 * @see	 #fetch()
	 *
	 */
	
	public Object getResultSetObject(ResultSet rs) {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<RegistrationSourceObject> result = (ArrayList<RegistrationSourceObject>) getResultObjects(rs);
			return result.get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
	
