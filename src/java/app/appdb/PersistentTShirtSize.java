/*
 * PersistentTShirtSize.java
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
import app.busobj.TShirtSizeObject;

/**
 * The persistent implementation of the TShirtSizeObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentTShirtSize extends PersistentObject {
	private TShirtSizeObject tShirtSizeObject;
	
	
	/**
	 * Constructs a Persistent Object for the TShirtSizeObject
	 *
	 * @param tShirtSizeObject	the TShirtSizeObject 
	 */
	
	public PersistentTShirtSize (TShirtSizeObject tShirtSizeObject) {
		this.tShirtSizeObject = tShirtSizeObject;
	}
	
	
	/**
	 * Returns the ArrayList of TShirtSizeObject.
	 * It is Usually all the rows in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	ArrayList of TShirtSizeObject 
	 *
	 * @throws	DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT t_shirt_size_id, t_shirt_size_name, t_shirt_gender from T_Shirt_Size";
		int index = 1;
		sql.setStatement(statement);
		
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<TShirtSizeObject> result = (ArrayList<TShirtSizeObject>) super.list();
		
		return result;
	}
	
	
	/**
	 * Returns the ArrayList of TShirtSizeObjects.
	 * It is Usually all the rows that match the criteria in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	 ArrayList of TShirtSizeObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT t_shirt_size_id, t_shirt_size_name, t_shirt_gender from T_Shirt_Size";
		int index = 1;
		TShirtSizeObject passedTShirtSizeObject = (TShirtSizeObject)args;
		boolean whereSpecified = false;

		if ( passedTShirtSizeObject.getTShirtSizeId() != 0 ) {
			statement += " where t_shirt_size_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedTShirtSizeObject.getTShirtSizeId()), Types.INTEGER));
		}
		if ( ! passedTShirtSizeObject.getTShirtSizeName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where t_shirt_size_name = ?";
				whereSpecified = true;
			} else
				statement += " and t_shirt_size_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedTShirtSizeObject.getTShirtSizeName(), Types.VARCHAR));
		}
		if ( passedTShirtSizeObject.getTShirtGender() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where t_shirt_gender = ?";
				whereSpecified = true;
			} else
				statement += " and t_shirt_gender = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedTShirtSizeObject.getTShirtGender()), Types.INTEGER));
		}
		sql.setStatement(statement);
		
		DebugHandler.debug(statement);
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<TShirtSizeObject> result = (ArrayList<TShirtSizeObject>) super.list();
		
		return result;
	}
	
	
	/**
	 * Returns the ArrayList of one TShirtSizeObject.
	 * It is Usually the row that matches primary key.
	 * This calls getResultSetObject method in the super class.
	 *
	 * @return	 ArrayList of one TShirtSizeObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultSetObject(ResultSet)
	 */
	
	public Object fetch() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT t_shirt_size_id, t_shirt_size_name, t_shirt_gender from T_Shirt_Size where t_shirt_size_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(tShirtSizeObject.getTShirtSizeId()), Types.INTEGER));
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<TShirtSizeObject> result = (ArrayList<TShirtSizeObject>) super.fetch();
		
		return result;
	}
	
	
	/**
	 *
	 * Inserts a row in the database.  The values
	 * are got from the tShirtSizeObject.
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
			statement = "INSERT INTO T_Shirt_Size (t_shirt_size_id, t_shirt_size_name, t_shirt_gender) VALUES(?, ?, ?) ";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(tShirtSizeObject.getTShirtSizeId()), Types.INTEGER));
		} else {
			statement = "INSERT INTO T_Shirt_Size (t_shirt_size_name, t_shirt_gender) VALUES(?, ?) ";
			sql.setStatement(statement);
		}
		sql.setInParams(new SQLParam(index++,  tShirtSizeObject.getTShirtSizeName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++, new Integer(tShirtSizeObject.getTShirtGender()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.insert();
		
		return result;
	}
	
	
	/**
	 *
	 * Deletes a row in the database. The key is 
	 * in the tShirtSizeObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object delete() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "DELETE FROM T_Shirt_Size WHERE t_shirt_size_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(tShirtSizeObject.getTShirtSizeId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.delete();
		
		return result;
	}
	
	
	/**
	 *
	 * Updates a row in the database. The values are 
	 * got from the tShirtSizeObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object update() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "UPDATE T_Shirt_Size SET t_shirt_size_id = ?, t_shirt_size_name = ?, t_shirt_gender = ? where t_shirt_size_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(tShirtSizeObject.getTShirtSizeId()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++,  tShirtSizeObject.getTShirtSizeName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++, new Integer(tShirtSizeObject.getTShirtGender()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++, new Integer(tShirtSizeObject.getTShirtSizeId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.update();
		
		return result;
	}
	
	
	/**
	 *
	 * Returns a ArrayList of TShirtSizeObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 * This is used by the list method.
	 *
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a ArrayList of TShirtSizeObject from the ResultSet.
	 *
	 * @see	 #list()
	 *
	 */
	
	public Object getResultObjects(ResultSet rs) {
		ArrayList<TShirtSizeObject> result = new ArrayList<TShirtSizeObject>();
		try {
			while(rs.next()) {
				int index = 1;
				TShirtSizeObject f = new TShirtSizeObject();
				f.setTShirtSizeId(rs.getInt(index++));
				f.setTShirtSizeName(rs.getString(index++));
				f.setTShirtGender(rs.getInt(index++));
				result.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 *
	 * Returns a TShirtSizeObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 *
	 * This is used by the fetch method.
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a TShirtSizeObject from the ResultSet.
	 *
	 * @see	 #fetch()
	 *
	 */
	
	public Object getResultSetObject(ResultSet rs) {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<TShirtSizeObject> result = (ArrayList<TShirtSizeObject>) getResultObjects(rs);
			return result.get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
	
