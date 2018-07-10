/*
 * PersistentRegistrationClass.java
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
import app.busobj.RegistrationClassObject;

/**
 * The persistent implementation of the RegistrationClassObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentRegistrationClass extends PersistentObject {
    private RegistrationClassObject registrationClassObject;
    
    
    /**
     * Constructs a Persistent Object for the RegistrationClassObject
     *
     * @param registrationClassObject    the RegistrationClassObject 
     */
    
    public PersistentRegistrationClass (RegistrationClassObject registrationClassObject) {
        this.registrationClassObject = registrationClassObject;
    }
    
    
    /**
     * Returns the ArrayList of RegistrationClassObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of RegistrationClassObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT registration_class_id, registration_class_name, registration_type, registration_event, registration_class_value, registration_free_tickets from Registration_Class";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<RegistrationClassObject> result = (ArrayList<RegistrationClassObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of RegistrationClassObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of RegistrationClassObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list(Object args) throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT registration_class_id, registration_class_name, registration_type, registration_event, registration_class_value, registration_free_tickets from Registration_Class";
        int index = 1;
        RegistrationClassObject passedRegistrationClassObject = (RegistrationClassObject)args;
        boolean whereSpecified = false;

        if ( passedRegistrationClassObject.getRegistrationClassId() != 0 ) {
	    statement += " where registration_class_id = ?";
	    whereSpecified = true;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedRegistrationClassObject.getRegistrationClassId()), Types.INTEGER));
	}
        if ( ! passedRegistrationClassObject.getRegistrationClassName().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where registration_class_name = ?";
		whereSpecified = true;
	    } else
		statement += " and registration_class_name = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedRegistrationClassObject.getRegistrationClassName(), Types.VARCHAR));
	}
        if ( passedRegistrationClassObject.getRegistrationType() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where registration_type = ?";
		whereSpecified = true;
	    } else
		statement += " and registration_type = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedRegistrationClassObject.getRegistrationType()), Types.INTEGER));
	}
        if ( passedRegistrationClassObject.getRegistrationEvent() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where registration_event = ?";
		whereSpecified = true;
	    } else
		statement += " and registration_event = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedRegistrationClassObject.getRegistrationEvent()), Types.INTEGER));
	}
        if ( passedRegistrationClassObject.getRegistrationClassValue() != 0.0 ) {
	    if ( ! whereSpecified ) {
		statement += " where registration_class_value = ?";
		whereSpecified = true;
	    } else
		statement += " and registration_class_value = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  new Double(passedRegistrationClassObject.getRegistrationClassValue()), Types.DOUBLE));
	}
        if ( passedRegistrationClassObject.getRegistrationFreeTickets() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where registration_free_tickets = ?";
		whereSpecified = true;
	    } else
		statement += " and registration_free_tickets = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedRegistrationClassObject.getRegistrationFreeTickets()), Types.INTEGER));
	}
        sql.setStatement(statement);
        
        DebugHandler.debug(statement);
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<RegistrationClassObject> result = (ArrayList<RegistrationClassObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of one RegistrationClassObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     ArrayList of one RegistrationClassObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT registration_class_id, registration_class_name, registration_type, registration_event, registration_class_value, registration_free_tickets from Registration_Class where registration_class_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(registrationClassObject.getRegistrationClassId()), Types.INTEGER));
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<RegistrationClassObject> result = (ArrayList<RegistrationClassObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the registrationClassObject.
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
            statement = "INSERT INTO Registration_Class (registration_class_id, registration_class_name, registration_type, registration_event, registration_class_value, registration_free_tickets) VALUES(?, ?, ?, ?, ?, ?) ";
            sql.setStatement(statement);
            sql.setInParams(new SQLParam(index++, new Integer(registrationClassObject.getRegistrationClassId()), Types.INTEGER));
        } else {
            statement = "INSERT INTO Registration_Class (registration_class_name, registration_type, registration_event, registration_class_value, registration_free_tickets) VALUES(?, ?, ?, ?, ?) ";
            sql.setStatement(statement);
        }
        sql.setInParams(new SQLParam(index++,  registrationClassObject.getRegistrationClassName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(registrationClassObject.getRegistrationType()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(registrationClassObject.getRegistrationEvent()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  new Double(registrationClassObject.getRegistrationClassValue()), Types.DOUBLE));
        sql.setInParams(new SQLParam(index++, new Integer(registrationClassObject.getRegistrationFreeTickets()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the registrationClassObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Registration_Class WHERE registration_class_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(registrationClassObject.getRegistrationClassId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the registrationClassObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Registration_Class SET registration_class_id = ?, registration_class_name = ?, registration_type = ?, registration_event = ?, registration_class_value = ?, registration_free_tickets = ? where registration_class_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(registrationClassObject.getRegistrationClassId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  registrationClassObject.getRegistrationClassName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(registrationClassObject.getRegistrationType()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(registrationClassObject.getRegistrationEvent()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  new Double(registrationClassObject.getRegistrationClassValue()), Types.DOUBLE));
        sql.setInParams(new SQLParam(index++, new Integer(registrationClassObject.getRegistrationFreeTickets()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(registrationClassObject.getRegistrationClassId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a ArrayList of RegistrationClassObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a ArrayList of RegistrationClassObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        ArrayList<RegistrationClassObject> result = new ArrayList<RegistrationClassObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                RegistrationClassObject f = new RegistrationClassObject();
                f.setRegistrationClassId(rs.getInt(index++));
                f.setRegistrationClassName(rs.getString(index++));
                f.setRegistrationType(rs.getInt(index++));
                f.setRegistrationEvent(rs.getInt(index++));
                f.setRegistrationClassValue(rs.getDouble(index++));
                f.setRegistrationFreeTickets(rs.getInt(index++));
                result.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * Returns a RegistrationClassObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a RegistrationClassObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
        @SuppressWarnings("unchecked")
            ArrayList<RegistrationClassObject> result = (ArrayList<RegistrationClassObject>) getResultObjects(rs);
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
