/*
 * PersistentUsers.java
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
import app.busobj.UsersObject;


/**
 * The persistent implementation of the UsersObject
 * @version 1.0
 * @author Genius Trivia Inc.
 * @since 1.0
 */

public class PersistentUsers extends PersistentObject {
    private UsersObject usersObject;
    
    
    /**
     * Constructs a Persistent Object for the UsersObject
     *
     * @param usersObject    the UsersObject 
     */
    
    public PersistentUsers (UsersObject usersObject) {
        this.usersObject = usersObject;
    }
    
    
    /**
     * Returns the Vector of UsersObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     Vector of UsersObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT Users_id, Username, Password, Email, Role_Id, Is_Valid from Users";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);

	@SuppressWarnings("unchecked")        
        Vector<UsersObject> result = (Vector<UsersObject>) super.list();
        
        return result;
    }
    
     /**
     * Returns the Vector of UsersObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     Vector of UsersObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list(Object args) throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT users_id, username, password, email, role_id, is_valid from Users";
        int index = 1;
        UsersObject passedUsersObject = (UsersObject)args;
        boolean whereSpecified = false;

        if ( passedUsersObject.getUsersId() != 0 ) {
	    statement += " where users_id = ?";
	    whereSpecified = true;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedUsersObject.getUsersId()), Types.INTEGER));
	}
        if ( ! passedUsersObject.getUsername().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where username = ?";
		whereSpecified = true;
	    } else
		statement += " and username = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedUsersObject.getUsername(), Types.VARCHAR));
	}
        if ( ! passedUsersObject.getPassword().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where password = ?";
		whereSpecified = true;
	    } else
		statement += " and password = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedUsersObject.getPassword(), Types.VARCHAR));
	}
        if ( ! passedUsersObject.getEmail().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where email = ?";
		whereSpecified = true;
	    } else
		statement += " and email = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedUsersObject.getEmail(), Types.VARCHAR));
	}
        if ( passedUsersObject.getRoleId() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where role_id = ?";
		whereSpecified = true;
	    } else
		statement += " and role_id = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedUsersObject.getRoleId()), Types.INTEGER));
	}
        if ( ! passedUsersObject.getIsValid().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where is_valid = ?";
		whereSpecified = true;
	    } else
		statement += " and is_valid = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedUsersObject.getIsValid(), Types.VARCHAR));
	}
        sql.setStatement(statement);
        
        DebugHandler.debug(statement);
        setSQLStatement(sql);

        @SuppressWarnings("unchecked")
        Vector<UsersObject> result = (Vector<UsersObject>) super.list();
        
        return result;
    }
    
    /**
     * Returns the Vector of one UsersObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     Vector of one UsersObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT Users_id, Username, Password, Email, Role_Id, Is_Valid from Users where Users_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(usersObject.getUsersId()), Types.INTEGER));
        setSQLStatement(sql);

	@SuppressWarnings("unchecked")        
        Vector<UsersObject> result = (Vector<UsersObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the usersObject.
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
	    statement = "INSERT INTO Users (Users_id, Username, Password, Email, Role_Id, Is_Valid) VALUES(?, ?, ?, ?, ?, ?) ";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(usersObject.getUsersId()), Types.INTEGER));
	} else {
	    statement = "INSERT INTO Users (Username, Password, Email, Role_Id, Is_Valid) VALUES(?, ?, ?, ?, ?) ";
	    sql.setStatement(statement);
	}
        sql.setInParams(new SQLParam(index++,  usersObject.getUsername(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  usersObject.getPassword(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  usersObject.getEmail(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(usersObject.getRoleId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  usersObject.getIsValid(), Types.VARCHAR));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the usersObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Users WHERE Users_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(usersObject.getUsersId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the usersObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Users SET Users_id = ?, Username = ?, Password = ?, Email = ?, Role_Id = ?, Is_Valid = ? where Users_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(usersObject.getUsersId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  usersObject.getUsername(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  usersObject.getPassword(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  usersObject.getEmail(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(usersObject.getRoleId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  usersObject.getIsValid(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(usersObject.getUsersId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a Vector of UsersObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a Vector of UsersObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        Vector<UsersObject> result = new Vector<UsersObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                UsersObject f = new UsersObject();
                f.setUsersId(rs.getInt(index++));
                f.setUsername(rs.getString(index++));
                f.setPassword(rs.getString(index++));
                f.setEmail(rs.getString(index++));
                f.setRoleId(rs.getInt(index++));
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
     * Returns a UsersObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a UsersObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
	    @SuppressWarnings("unchecked")
            Vector<UsersObject> result = (Vector<UsersObject>)getResultObjects(rs);
            return result.elementAt(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
