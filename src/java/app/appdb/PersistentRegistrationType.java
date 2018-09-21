/*
 * PersistentRegistrationType.java
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
import app.busobj.RegistrationTypeObject;

/**
 * The persistent implementation of the RegistrationTypeObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentRegistrationType extends PersistentObject {
	private RegistrationTypeObject registrationTypeObject;
	
	
	/**
	 * Constructs a Persistent Object for the RegistrationTypeObject
	 *
	 * @param registrationTypeObject	the RegistrationTypeObject 
	 */
	
	public PersistentRegistrationType (RegistrationTypeObject registrationTypeObject) {
		this.registrationTypeObject = registrationTypeObject;
	}
	
	
	/**
	 * Returns the ArrayList of RegistrationTypeObject.
	 * It is Usually all the rows in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	ArrayList of RegistrationTypeObject 
	 *
	 * @throws	DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT registration_type_id, registration_type_name from Registration_Type";
		int index = 1;
		sql.setStatement(statement);
		
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<RegistrationTypeObject> result = (ArrayList<RegistrationTypeObject>) super.list();
		
	return result;
	}
	
	
	/**
	 * Returns the ArrayList of RegistrationTypeObjects.
	 * It is Usually all the rows that match the criteria in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	 ArrayList of RegistrationTypeObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT registration_type_id, registration_type_name from Registration_Type";
		int index = 1;
		RegistrationTypeObject passedRegistrationTypeObject = (RegistrationTypeObject)args;
		boolean whereSpecified = false;

		if ( passedRegistrationTypeObject.getRegistrationTypeId() != 0 ) {
			statement += " where registration_type_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrationTypeObject.getRegistrationTypeId()), Types.INTEGER));
		}
		if ( ! passedRegistrationTypeObject.getRegistrationTypeName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registration_type_name = ?";
				whereSpecified = true;
			} else
				statement += " and registration_type_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrationTypeObject.getRegistrationTypeName(), Types.VARCHAR));
		}
		sql.setStatement(statement);
		
		DebugHandler.debug(statement);
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<RegistrationTypeObject> result = (ArrayList<RegistrationTypeObject>) super.list();
		
		return result;
	}
	
	
	/**
	 * Returns the ArrayList of one RegistrationTypeObject.
	 * It is Usually the row that matches primary key.
	 * This calls getResultSetObject method in the super class.
	 *
	 * @return	 ArrayList of one RegistrationTypeObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultSetObject(ResultSet)
	 */
	
	public Object fetch() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT registration_type_id, registration_type_name from Registration_Type where registration_type_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(registrationTypeObject.getRegistrationTypeId()), Types.INTEGER));
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<RegistrationTypeObject> result = (ArrayList<RegistrationTypeObject>) super.fetch();
		
		return result;
	}
	
	
	/**
	 *
	 * Inserts a row in the database.  The values
	 * are got from the registrationTypeObject.
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
			statement = "INSERT INTO Registration_Type (registration_type_id, registration_type_name) VALUES(?, ?) ";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(registrationTypeObject.getRegistrationTypeId()), Types.INTEGER));
		} else {
			statement = "INSERT INTO Registration_Type (registration_type_name) VALUES(?) ";
			sql.setStatement(statement);
		}
		sql.setInParams(new SQLParam(index++,  registrationTypeObject.getRegistrationTypeName(), Types.VARCHAR));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.insert();
		
		return result;
	}
	
	
	/**
	 *
	 * Deletes a row in the database. The key is 
	 * in the registrationTypeObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object delete() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "DELETE FROM Registration_Type WHERE registration_type_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(registrationTypeObject.getRegistrationTypeId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.delete();
		
		return result;
	}
	
	
	/**
	 *
	 * Updates a row in the database. The values are 
	 * got from the registrationTypeObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object update() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "UPDATE Registration_Type SET registration_type_id = ?, registration_type_name = ? where registration_type_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(registrationTypeObject.getRegistrationTypeId()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++,  registrationTypeObject.getRegistrationTypeName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++, new Integer(registrationTypeObject.getRegistrationTypeId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.update();
		
		return result;
	}
	
	
	/**
	 *
	 * Returns a ArrayList of RegistrationTypeObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 * This is used by the list method.
	 *
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a ArrayList of RegistrationTypeObject from the ResultSet.
	 *
	 * @see	 #list()
	 *
	 */
	
	public Object getResultObjects(ResultSet rs) {
		ArrayList<RegistrationTypeObject> result = new ArrayList<RegistrationTypeObject>();
		try {
			while(rs.next()) {
				int index = 1;
				RegistrationTypeObject f = new RegistrationTypeObject();
				f.setRegistrationTypeId(rs.getInt(index++));
				f.setRegistrationTypeName(rs.getString(index++));
				result.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 *
	 * Returns a RegistrationTypeObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 *
	 * This is used by the fetch method.
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a RegistrationTypeObject from the ResultSet.
	 *
	 * @see	 #fetch()
	 *
	 */
	
	public Object getResultSetObject(ResultSet rs) {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<RegistrationTypeObject> result = (ArrayList<RegistrationTypeObject>) getResultObjects(rs);
			return result.get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
	
