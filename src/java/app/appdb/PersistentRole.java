/*
 * PersistentRole.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.appdb;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.Vector;
import core.db.PersistentObject;
import core.db.SQLParam;
import core.db.SQLStatement;
import core.db.PreparedSQLStatement;
import core.db.CallableSQLStatement;
import core.db.DBException;
import core.util.DebugHandler;
import core.util.Constants;
import app.util.AppConstants;
import app.busobj.RoleObject;

/**
 * The persistent implementation of the RoleObject
 * @version 1.0
 * @author Genius Trivia Inc.
 * @since 1.0
 */

public class PersistentRole extends PersistentObject {
    private RoleObject roleObject;
    
    
    /**
     * Constructs a Persistent Object for the RoleObject
     *
     * @param roleObject    the RoleObject 
     */
    
    public PersistentRole (RoleObject roleObject) {
        this.roleObject = roleObject;
    }
    
    
    /**
     * Returns the Vector of RoleObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     Vector of RoleObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT role_id, role_name, is_valid from Role";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);

        @SuppressWarnings("unchecked")
        Vector<RoleObject> result = (Vector<RoleObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the Vector of RoleObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     Vector of RoleObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list(Object args) throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT role_id, role_name, is_valid from Role";
        int index = 1;
        RoleObject passedRoleObject = (RoleObject)args;
        boolean whereSpecified = false;

        if ( passedRoleObject.getRoleId() != 0 ) {
	    statement += " where role_id = ?";
	    whereSpecified = true;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedRoleObject.getRoleId()), Types.INTEGER));
	}
        if ( ! passedRoleObject.getRoleName().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where role_name = ?";
		whereSpecified = true;
	    } else
		statement += " and role_name = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedRoleObject.getRoleName(), Types.VARCHAR));
	}
        if ( ! passedRoleObject.getIsValid().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where is_valid = ?";
		whereSpecified = true;
	    } else
		statement += " and is_valid = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedRoleObject.getIsValid(), Types.VARCHAR));
	}
        sql.setStatement(statement);
        
        DebugHandler.debug(statement);
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        Vector<RoleObject> result = (Vector<RoleObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the Vector of one RoleObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     Vector of one RoleObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT role_id, role_name, is_valid from Role where role_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(roleObject.getRoleId()), Types.INTEGER));
        setSQLStatement(sql);

        @SuppressWarnings("unchecked")        
        Vector<RoleObject> result = (Vector<RoleObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the roleObject.
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
	    statement = "INSERT INTO Role (role_id, role_name, is_valid) VALUES(?, ?, ?) ";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(roleObject.getRoleId()), Types.INTEGER));
	} else {
	    statement = "INSERT INTO Role (role_name, is_valid) VALUES(?, ?) ";
	    sql.setStatement(statement);
	}
	
        sql.setInParams(new SQLParam(index++,  roleObject.getRoleName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  roleObject.getIsValid(), Types.VARCHAR));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the roleObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Role WHERE role_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(roleObject.getRoleId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the roleObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Role SET role_id = ?, role_name = ?, is_valid = ? where role_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(roleObject.getRoleId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  roleObject.getRoleName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  roleObject.getIsValid(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(roleObject.getRoleId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a Vector of RoleObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a Vector of RoleObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        Vector<RoleObject> result = new Vector<RoleObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                RoleObject f = new RoleObject();
                f.setRoleId(rs.getInt(index++));
                f.setRoleName(rs.getString(index++));
                f.setIsValid(rs.getString(index++));
                result.addElement(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * Returns a RoleObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a RoleObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
	    @SuppressWarnings("unchecked")
            Vector<RoleObject> result = (Vector<RoleObject>) getResultObjects(rs);
            return result.elementAt(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
