/*
 * PersistentPaymentStatus.java
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
import app.busobj.PaymentStatusObject;

/**
 * The persistent implementation of the PaymentStatusObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentPaymentStatus extends PersistentObject {
	private PaymentStatusObject paymentStatusObject;
	
	
	/**
	 * Constructs a Persistent Object for the PaymentStatusObject
	 *
	 * @param paymentStatusObject	the PaymentStatusObject 
	 */
	
	public PersistentPaymentStatus (PaymentStatusObject paymentStatusObject) {
		this.paymentStatusObject = paymentStatusObject;
	}
	
	
	/**
	 * Returns the ArrayList of PaymentStatusObject.
	 * It is Usually all the rows in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	ArrayList of PaymentStatusObject 
	 *
	 * @throws	DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT payment_status_id, payment_status_name from Payment_Status";
		int index = 1;
		sql.setStatement(statement);
		
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<PaymentStatusObject> result = (ArrayList<PaymentStatusObject>) super.list();
		
	return result;
	}
	
	
	/**
	 * Returns the ArrayList of PaymentStatusObjects.
	 * It is Usually all the rows that match the criteria in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	 ArrayList of PaymentStatusObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT payment_status_id, payment_status_name from Payment_Status";
		int index = 1;
		PaymentStatusObject passedPaymentStatusObject = (PaymentStatusObject)args;
		boolean whereSpecified = false;

		if ( passedPaymentStatusObject.getPaymentStatusId() != 0 ) {
			statement += " where payment_status_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedPaymentStatusObject.getPaymentStatusId()), Types.INTEGER));
		}
		if ( ! passedPaymentStatusObject.getPaymentStatusName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where payment_status_name = ?";
				whereSpecified = true;
			} else
				statement += " and payment_status_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedPaymentStatusObject.getPaymentStatusName(), Types.VARCHAR));
		}
		sql.setStatement(statement);
		
		DebugHandler.debug(statement);
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<PaymentStatusObject> result = (ArrayList<PaymentStatusObject>) super.list();
		
		return result;
	}
	
	
	/**
	 * Returns the ArrayList of one PaymentStatusObject.
	 * It is Usually the row that matches primary key.
	 * This calls getResultSetObject method in the super class.
	 *
	 * @return	 ArrayList of one PaymentStatusObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultSetObject(ResultSet)
	 */
	
	public Object fetch() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT payment_status_id, payment_status_name from Payment_Status where payment_status_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(paymentStatusObject.getPaymentStatusId()), Types.INTEGER));
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<PaymentStatusObject> result = (ArrayList<PaymentStatusObject>) super.fetch();
		
		return result;
	}
	
	
	/**
	 *
	 * Inserts a row in the database.  The values
	 * are got from the paymentStatusObject.
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
			statement = "INSERT INTO Payment_Status (payment_status_id, payment_status_name) VALUES(?, ?) ";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(paymentStatusObject.getPaymentStatusId()), Types.INTEGER));
		} else {
			statement = "INSERT INTO Payment_Status (payment_status_name) VALUES(?) ";
			sql.setStatement(statement);
		}
		sql.setInParams(new SQLParam(index++,  paymentStatusObject.getPaymentStatusName(), Types.VARCHAR));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.insert();
		
		return result;
	}
	
	
	/**
	 *
	 * Deletes a row in the database. The key is 
	 * in the paymentStatusObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object delete() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "DELETE FROM Payment_Status WHERE payment_status_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(paymentStatusObject.getPaymentStatusId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.delete();
		
		return result;
	}
	
	
	/**
	 *
	 * Updates a row in the database. The values are 
	 * got from the paymentStatusObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object update() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "UPDATE Payment_Status SET payment_status_id = ?, payment_status_name = ? where payment_status_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(paymentStatusObject.getPaymentStatusId()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++,  paymentStatusObject.getPaymentStatusName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++, new Integer(paymentStatusObject.getPaymentStatusId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.update();
		
		return result;
	}
	
	
	/**
	 *
	 * Returns a ArrayList of PaymentStatusObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 * This is used by the list method.
	 *
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a ArrayList of PaymentStatusObject from the ResultSet.
	 *
	 * @see	 #list()
	 *
	 */
	
	public Object getResultObjects(ResultSet rs) {
		ArrayList<PaymentStatusObject> result = new ArrayList<PaymentStatusObject>();
		try {
			while(rs.next()) {
				int index = 1;
				PaymentStatusObject f = new PaymentStatusObject();
				f.setPaymentStatusId(rs.getInt(index++));
				f.setPaymentStatusName(rs.getString(index++));
				result.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 *
	 * Returns a PaymentStatusObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 *
	 * This is used by the fetch method.
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a PaymentStatusObject from the ResultSet.
	 *
	 * @see	 #fetch()
	 *
	 */
	
	public Object getResultSetObject(ResultSet rs) {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<PaymentStatusObject> result = (ArrayList<PaymentStatusObject>) getResultObjects(rs);
			return result.get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
	
