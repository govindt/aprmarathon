/*
 * PersistentRegistrant.java
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
import app.busobj.RegistrantObject;

/**
 * The persistent implementation of the RegistrantObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentRegistrant extends PersistentObject {
	private RegistrantObject registrantObject;
	
	
	/**
	 * Constructs a Persistent Object for the RegistrantObject
	 *
	 * @param registrantObject	the RegistrantObject 
	 */
	
	public PersistentRegistrant (RegistrantObject registrantObject) {
		this.registrantObject = registrantObject;
	}
	
	
	/**
	 * Returns the ArrayList of RegistrantObject.
	 * It is Usually all the rows in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	ArrayList of RegistrantObject 
	 *
	 * @throws	DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT registrant_id, registrant_name, registrant_middle_name, registrant_last_name, registrant_email, registrant_additional_email, registrant_phone, registrant_address, registrant_city, registrant_state, registrant_pincode, registrant_pan from Registrant";
		int index = 1;
		sql.setStatement(statement);
		
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<RegistrantObject> result = (ArrayList<RegistrantObject>) super.list();
		
		return result;
	}
	
	
	/**
	 * Returns the ArrayList of RegistrantObjects.
	 * It is Usually all the rows that match the criteria in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	 ArrayList of RegistrantObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT registrant_id, registrant_name, registrant_middle_name, registrant_last_name, registrant_email, registrant_additional_email, registrant_phone, registrant_address, registrant_city, registrant_state, registrant_pincode, registrant_pan from Registrant";
		int index = 1;
		RegistrantObject passedRegistrantObject = (RegistrantObject)args;
		boolean whereSpecified = false;

		if ( passedRegistrantObject.getRegistrantId() != 0 ) {
			statement += " where registrant_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantObject.getRegistrantId()), Types.INTEGER));
		}
		if ( ! passedRegistrantObject.getRegistrantName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_name = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantObject.getRegistrantName(), Types.VARCHAR));
		}
		if ( ! passedRegistrantObject.getRegistrantMiddleName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_middle_name = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_middle_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantObject.getRegistrantMiddleName(), Types.VARCHAR));
		}
		if ( ! passedRegistrantObject.getRegistrantLastName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_last_name = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_last_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantObject.getRegistrantLastName(), Types.VARCHAR));
		}
		if ( ! passedRegistrantObject.getRegistrantEmail().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_email = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_email = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantObject.getRegistrantEmail(), Types.VARCHAR));
		}
		if ( ! passedRegistrantObject.getRegistrantAdditionalEmail().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_additional_email = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_additional_email = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantObject.getRegistrantAdditionalEmail(), Types.VARCHAR));
		}
		if ( ! passedRegistrantObject.getRegistrantPhone().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_phone = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_phone = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantObject.getRegistrantPhone(), Types.VARCHAR));
		}
		if ( ! passedRegistrantObject.getRegistrantAddress().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_address = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_address = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantObject.getRegistrantAddress(), Types.VARCHAR));
		}
		if ( ! passedRegistrantObject.getRegistrantCity().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_city = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_city = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantObject.getRegistrantCity(), Types.VARCHAR));
		}
		if ( ! passedRegistrantObject.getRegistrantState().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_state = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_state = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantObject.getRegistrantState(), Types.VARCHAR));
		}
		if ( ! passedRegistrantObject.getRegistrantPincode().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_pincode = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_pincode = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantObject.getRegistrantPincode(), Types.VARCHAR));
		}
		if ( ! passedRegistrantObject.getRegistrantPan().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_pan = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_pan = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantObject.getRegistrantPan(), Types.VARCHAR));
		}
		sql.setStatement(statement);
		
		DebugHandler.debug(statement);
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<RegistrantObject> result = (ArrayList<RegistrantObject>) super.list();
		
		return result;
	}
	
	
	/**
	 * Returns the ArrayList of one RegistrantObject.
	 * It is Usually the row that matches primary key.
	 * This calls getResultSetObject method in the super class.
	 *
	 * @return	 ArrayList of one RegistrantObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultSetObject(ResultSet)
	 */
	
	public Object fetch() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT registrant_id, registrant_name, registrant_middle_name, registrant_last_name, registrant_email, registrant_additional_email, registrant_phone, registrant_address, registrant_city, registrant_state, registrant_pincode, registrant_pan from Registrant where registrant_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(registrantObject.getRegistrantId()), Types.INTEGER));
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<RegistrantObject> result = (ArrayList<RegistrantObject>) super.fetch();
		
		return result;
	}
	
	
	/**
	 *
	 * Inserts a row in the database.  The values
	 * are got from the registrantObject.
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
			statement = "INSERT INTO Registrant (registrant_id, registrant_name, registrant_middle_name, registrant_last_name, registrant_email, registrant_additional_email, registrant_phone, registrant_address, registrant_city, registrant_state, registrant_pincode, registrant_pan) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(registrantObject.getRegistrantId()), Types.INTEGER));
		} else {
			statement = "INSERT INTO Registrant (registrant_name, registrant_middle_name, registrant_last_name, registrant_email, registrant_additional_email, registrant_phone, registrant_address, registrant_city, registrant_state, registrant_pincode, registrant_pan) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			sql.setStatement(statement);
		}
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantMiddleName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantLastName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantEmail(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantAdditionalEmail(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantPhone(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantAddress(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantCity(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantState(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantPincode(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantPan(), Types.VARCHAR));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.insert();
		
		return result;
	}
	
	
	/**
	 *
	 * Deletes a row in the database. The key is 
	 * in the registrantObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object delete() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "DELETE FROM Registrant WHERE registrant_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(registrantObject.getRegistrantId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.delete();
		
		return result;
	}
	
	
	/**
	 *
	 * Updates a row in the database. The values are 
	 * got from the registrantObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object update() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "UPDATE Registrant SET registrant_id = ?, registrant_name = ?, registrant_middle_name = ?, registrant_last_name = ?, registrant_email = ?, registrant_additional_email = ?, registrant_phone = ?, registrant_address = ?, registrant_city = ?, registrant_state = ?, registrant_pincode = ?, registrant_pan = ? where registrant_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(registrantObject.getRegistrantId()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantMiddleName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantLastName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantEmail(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantAdditionalEmail(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantPhone(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantAddress(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantCity(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantState(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantPincode(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  registrantObject.getRegistrantPan(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++, new Integer(registrantObject.getRegistrantId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.update();
		
		return result;
	}
	
	
	/**
	 *
	 * Returns a ArrayList of RegistrantObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 * This is used by the list method.
	 *
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a ArrayList of RegistrantObject from the ResultSet.
	 *
	 * @see	 #list()
	 *
	 */
	
	public Object getResultObjects(ResultSet rs) {
		ArrayList<RegistrantObject> result = new ArrayList<RegistrantObject>();
		try {
			while(rs.next()) {
				int index = 1;
				RegistrantObject f = new RegistrantObject();
				f.setRegistrantId(rs.getInt(index++));
				f.setRegistrantName(rs.getString(index++));
				f.setRegistrantMiddleName(rs.getString(index++));
				f.setRegistrantLastName(rs.getString(index++));
				f.setRegistrantEmail(rs.getString(index++));
				f.setRegistrantAdditionalEmail(rs.getString(index++));
				f.setRegistrantPhone(rs.getString(index++));
				f.setRegistrantAddress(rs.getString(index++));
				f.setRegistrantCity(rs.getString(index++));
				f.setRegistrantState(rs.getString(index++));
				f.setRegistrantPincode(rs.getString(index++));
				f.setRegistrantPan(rs.getString(index++));
				result.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 *
	 * Returns a RegistrantObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 *
	 * This is used by the fetch method.
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a RegistrantObject from the ResultSet.
	 *
	 * @see	 #fetch()
	 *
	 */
	
	public Object getResultSetObject(ResultSet rs) {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<RegistrantObject> result = (ArrayList<RegistrantObject>) getResultObjects(rs);
			return result.get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
	
