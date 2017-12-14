/*
 * PersistentAcl.java
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
import app.busobj.AclObject;

/**
 * The persistent implementation of the AclObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentAcl extends PersistentObject {
    private AclObject aclObject;
    
    
    /**
     * Constructs a Persistent Object for the AclObject
     *
     * @param aclObject    the AclObject 
     */
    
    public PersistentAcl (AclObject aclObject) {
        this.aclObject = aclObject;
    }
    
    
    /**
     * Returns the Vector of AclObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     Vector of AclObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT acl_id, acl_page, is_valid, role_id, users_id, permission from Acl";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);
        
	@SuppressWarnings("unchecked")
        Vector<AclObject> result = (Vector<AclObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the Vector of AclObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     Vector of AclObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list(Object args) throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT acl_id, acl_page, is_valid, role_id, users_id, permission from Acl";
        int index = 1;
        AclObject passedAclObject = (AclObject)args;
        boolean whereSpecified = false;

        if ( passedAclObject.getAclId() != 0 ) {
	    statement += " where acl_id = ?";
	    whereSpecified = true;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedAclObject.getAclId()), Types.INTEGER));
	}
        if ( ! passedAclObject.getAclPage().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where acl_page = ?";
		whereSpecified = true;
	    } else
		statement += " and acl_page = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedAclObject.getAclPage(), Types.VARCHAR));
	}
        if ( ! passedAclObject.getIsValid().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where is_valid = ?";
		whereSpecified = true;
	    } else
		statement += " and is_valid = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedAclObject.getIsValid(), Types.VARCHAR));
	}
        if ( passedAclObject.getRoleId() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where role_id = ?";
		whereSpecified = true;
	    } else
		statement += " and role_id = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedAclObject.getRoleId()), Types.INTEGER));
	}
        if ( passedAclObject.getUsersId() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where users_id = ?";
		whereSpecified = true;
	    } else
		statement += " and users_id = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedAclObject.getUsersId()), Types.INTEGER));
	}
        if ( passedAclObject.getPermission() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where permission = ?";
		whereSpecified = true;
	    } else
		statement += " and permission = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedAclObject.getPermission()), Types.INTEGER));
	}
        sql.setStatement(statement);
        
        DebugHandler.debug(statement);
        setSQLStatement(sql);

	@SuppressWarnings("unchecked")        
        Vector<AclObject> result = (Vector<AclObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the Vector of one AclObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     Vector of one AclObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT acl_id, acl_page, is_valid, role_id, users_id, permission from Acl where acl_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(aclObject.getAclId()), Types.INTEGER));
        setSQLStatement(sql);
        
	@SuppressWarnings("unchecked")
        Vector<AclObject> result = (Vector<AclObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the aclObject.
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
	    statement = "INSERT INTO Acl (acl_id, acl_page, is_valid, role_id, users_id, permission) VALUES(?, ?, ?, ?, ?, ?) ";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(aclObject.getAclId()), Types.INTEGER));
	} else {
	    statement = "INSERT INTO Acl (acl_page, is_valid, role_id, users_id, permission) VALUES(?, ?, ?, ?, ?) ";
	    sql.setStatement(statement);
	}
        sql.setInParams(new SQLParam(index++,  aclObject.getAclPage(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  aclObject.getIsValid(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(aclObject.getRoleId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(aclObject.getUsersId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(aclObject.getPermission()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the aclObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Acl WHERE acl_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(aclObject.getAclId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the aclObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Acl SET acl_id = ?, acl_page = ?, is_valid = ?, role_id = ?, users_id = ?, permission = ? where acl_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(aclObject.getAclId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  aclObject.getAclPage(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  aclObject.getIsValid(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(aclObject.getRoleId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(aclObject.getUsersId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(aclObject.getPermission()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(aclObject.getAclId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a Vector of AclObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a Vector of AclObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        Vector<AclObject> result = new Vector<AclObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                AclObject f = new AclObject();
                f.setAclId(rs.getInt(index++));
                f.setAclPage(rs.getString(index++));
                f.setIsValid(rs.getString(index++));
                f.setRoleId(rs.getInt(index++));
                f.setUsersId(rs.getInt(index++));
                f.setPermission(rs.getInt(index++));
                result.addElement(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * Returns a AclObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a AclObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
	    @SuppressWarnings("unchecked")
            Vector<AclObject> result = (Vector<AclObject>) getResultObjects(rs);
            return result.elementAt(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
