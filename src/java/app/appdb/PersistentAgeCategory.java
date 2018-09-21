/*
 * PersistentAgeCategory.java
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
import app.busobj.AgeCategoryObject;

/**
 * The persistent implementation of the AgeCategoryObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentAgeCategory extends PersistentObject {
	private AgeCategoryObject ageCategoryObject;
	
	
	/**
	 * Constructs a Persistent Object for the AgeCategoryObject
	 *
	 * @param ageCategoryObject	the AgeCategoryObject 
	 */
	
	public PersistentAgeCategory (AgeCategoryObject ageCategoryObject) {
		this.ageCategoryObject = ageCategoryObject;
	}
	
	
	/**
	 * Returns the ArrayList of AgeCategoryObject.
	 * It is Usually all the rows in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	ArrayList of AgeCategoryObject 
	 *
	 * @throws	DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT age_category_id, age_category from Age_Category";
		int index = 1;
		sql.setStatement(statement);
		
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<AgeCategoryObject> result = (ArrayList<AgeCategoryObject>) super.list();
		
	return result;
	}
	
	
	/**
	 * Returns the ArrayList of AgeCategoryObjects.
	 * It is Usually all the rows that match the criteria in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	 ArrayList of AgeCategoryObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT age_category_id, age_category from Age_Category";
		int index = 1;
		AgeCategoryObject passedAgeCategoryObject = (AgeCategoryObject)args;
		boolean whereSpecified = false;

		if ( passedAgeCategoryObject.getAgeCategoryId() != 0 ) {
			statement += " where age_category_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedAgeCategoryObject.getAgeCategoryId()), Types.INTEGER));
		}
		if ( ! passedAgeCategoryObject.getAgeCategory().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where age_category = ?";
				whereSpecified = true;
			} else
				statement += " and age_category = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedAgeCategoryObject.getAgeCategory(), Types.VARCHAR));
		}
		sql.setStatement(statement);
		
		DebugHandler.debug(statement);
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<AgeCategoryObject> result = (ArrayList<AgeCategoryObject>) super.list();
		
		return result;
	}
	
	
	/**
	 * Returns the ArrayList of one AgeCategoryObject.
	 * It is Usually the row that matches primary key.
	 * This calls getResultSetObject method in the super class.
	 *
	 * @return	 ArrayList of one AgeCategoryObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultSetObject(ResultSet)
	 */
	
	public Object fetch() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT age_category_id, age_category from Age_Category where age_category_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(ageCategoryObject.getAgeCategoryId()), Types.INTEGER));
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<AgeCategoryObject> result = (ArrayList<AgeCategoryObject>) super.fetch();
		
		return result;
	}
	
	
	/**
	 *
	 * Inserts a row in the database.  The values
	 * are got from the ageCategoryObject.
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
			statement = "INSERT INTO Age_Category (age_category_id, age_category) VALUES(?, ?) ";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(ageCategoryObject.getAgeCategoryId()), Types.INTEGER));
		} else {
			statement = "INSERT INTO Age_Category (age_category) VALUES(?) ";
			sql.setStatement(statement);
		}
		sql.setInParams(new SQLParam(index++,  ageCategoryObject.getAgeCategory(), Types.VARCHAR));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.insert();
		
		return result;
	}
	
	
	/**
	 *
	 * Deletes a row in the database. The key is 
	 * in the ageCategoryObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object delete() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "DELETE FROM Age_Category WHERE age_category_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(ageCategoryObject.getAgeCategoryId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.delete();
		
		return result;
	}
	
	
	/**
	 *
	 * Updates a row in the database. The values are 
	 * got from the ageCategoryObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object update() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "UPDATE Age_Category SET age_category_id = ?, age_category = ? where age_category_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(ageCategoryObject.getAgeCategoryId()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++,  ageCategoryObject.getAgeCategory(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++, new Integer(ageCategoryObject.getAgeCategoryId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.update();
		
		return result;
	}
	
	
	/**
	 *
	 * Returns a ArrayList of AgeCategoryObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 * This is used by the list method.
	 *
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a ArrayList of AgeCategoryObject from the ResultSet.
	 *
	 * @see	 #list()
	 *
	 */
	
	public Object getResultObjects(ResultSet rs) {
		ArrayList<AgeCategoryObject> result = new ArrayList<AgeCategoryObject>();
		try {
			while(rs.next()) {
				int index = 1;
				AgeCategoryObject f = new AgeCategoryObject();
				f.setAgeCategoryId(rs.getInt(index++));
				f.setAgeCategory(rs.getString(index++));
				result.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 *
	 * Returns a AgeCategoryObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 *
	 * This is used by the fetch method.
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a AgeCategoryObject from the ResultSet.
	 *
	 * @see	 #fetch()
	 *
	 */
	
	public Object getResultSetObject(ResultSet rs) {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<AgeCategoryObject> result = (ArrayList<AgeCategoryObject>) getResultObjects(rs);
			return result.get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
	
