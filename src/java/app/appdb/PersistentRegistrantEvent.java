/*
 * PersistentRegistrantEvent.java
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
import app.busobj.RegistrantEventObject;

/**
 * The persistent implementation of the RegistrantEventObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentRegistrantEvent extends PersistentObject {
	private RegistrantEventObject registrantEventObject;
	
	
    /**
	 * Constructs a Persistent Object for the RegistrantEventObject
	 *
	 * @param registrantEventObject    the RegistrantEventObject 
	 */
    
	public PersistentRegistrantEvent (RegistrantEventObject registrantEventObject) {
	    this.registrantEventObject = registrantEventObject;
	}
    
	
    /**
	 * Returns the ArrayList of RegistrantEventObject.
	 * It is Usually all the rows in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return     ArrayList of RegistrantEventObject 
	 *
	 * @throws     DBException     If a database error occurs
	 *
	 * @see     #getResultObjects(ResultSet)
	 */
    
	public Object list() throws DBException {
	    PreparedSQLStatement sql = new PreparedSQLStatement();
	    String statement = "SELECT registrant_event_id, registrant_id, registrant_event, registrant_type, registrant_source, registrant_class, registrant_beneficiary, registrant_emergency_contact, registrant_emergency_phone from Registrant_Event";
	    int index = 1;
	    sql.setStatement(statement);
        
	    setSQLStatement(sql);
        
	    @SuppressWarnings("unchecked")
	    ArrayList<RegistrantEventObject> result = (ArrayList<RegistrantEventObject>) super.list();
        
	    return result;
	}
    
	
    /**
	 * Returns the ArrayList of RegistrantEventObjects.
	 * It is Usually all the rows that match the criteria in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return     ArrayList of RegistrantEventObject 
	 *
	 * @throws     DBException     If a database error occurs
	 *
	 * @see     #getResultObjects(ResultSet)
	 */
    
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT registrant_event_id, registrant_id, registrant_event, registrant_type, registrant_source, registrant_class, registrant_beneficiary, registrant_emergency_contact, registrant_emergency_phone from Registrant_Event";
		int index = 1;
		RegistrantEventObject passedRegistrantEventObject = (RegistrantEventObject)args;
		boolean whereSpecified = false;

		if ( passedRegistrantEventObject.getRegistrantEventId() != 0 ) {
			statement += " where registrant_event_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantEventObject.getRegistrantEventId()), Types.INTEGER));
		}
		if ( passedRegistrantEventObject.getRegistrantId() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_id = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_id = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantEventObject.getRegistrantId()), Types.INTEGER));
		}
		if ( passedRegistrantEventObject.getRegistrantEvent() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_event = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_event = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantEventObject.getRegistrantEvent()), Types.INTEGER));
		}
		if ( passedRegistrantEventObject.getRegistrantType() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_type = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_type = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantEventObject.getRegistrantType()), Types.INTEGER));
		}
		if ( passedRegistrantEventObject.getRegistrantSource() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_source = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_source = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantEventObject.getRegistrantSource()), Types.INTEGER));
		}
		if ( passedRegistrantEventObject.getRegistrantClass() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_class = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_class = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantEventObject.getRegistrantClass()), Types.INTEGER));
		}
		if ( passedRegistrantEventObject.getRegistrantBeneficiary() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_beneficiary = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_beneficiary = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantEventObject.getRegistrantBeneficiary()), Types.INTEGER));
		}
		if ( ! passedRegistrantEventObject.getRegistrantEmergencyContact().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_emergency_contact = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_emergency_contact = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantEventObject.getRegistrantEmergencyContact(), Types.VARCHAR));
		}
		if ( ! passedRegistrantEventObject.getRegistrantEmergencyPhone().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_emergency_phone = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_emergency_phone = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantEventObject.getRegistrantEmergencyPhone(), Types.VARCHAR));
		}
		sql.setStatement(statement);
        
		DebugHandler.debug(statement);
		setSQLStatement(sql);
        
		@SuppressWarnings("unchecked")
		ArrayList<RegistrantEventObject> result = (ArrayList<RegistrantEventObject>) super.list();
        
		return result;
	}
    
	
    /**
	 * Returns the ArrayList of one RegistrantEventObject.
	 * It is Usually the row that matches primary key.
	 * This calls getResultSetObject method in the super class.
	 *
	 * @return     ArrayList of one RegistrantEventObject 
	 *
	 * @throws     DBException     If a database error occurs
	 *
	 * @see     #getResultSetObject(ResultSet)
	 */
    
	public Object fetch() throws DBException {
	    PreparedSQLStatement sql = new PreparedSQLStatement();
	    String statement = "SELECT registrant_event_id, registrant_id, registrant_event, registrant_type, registrant_source, registrant_class, registrant_beneficiary, registrant_emergency_contact, registrant_emergency_phone from Registrant_Event where registrant_event_id = ? ";
	    int index = 1;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantEventId()), Types.INTEGER));
	    setSQLStatement(sql);
        
	    @SuppressWarnings("unchecked")
	    ArrayList<RegistrantEventObject> result = (ArrayList<RegistrantEventObject>) super.fetch();
        
	    return result;
	}
    
	
    /**
	 *
	 * Inserts a row in the database.  The values
	 * are got from the registrantEventObject.
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
	        statement = "INSERT INTO Registrant_Event (registrant_event_id, registrant_id, registrant_event, registrant_type, registrant_source, registrant_class, registrant_beneficiary, registrant_emergency_contact, registrant_emergency_phone) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?) ";
	        sql.setStatement(statement);
	        sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantEventId()), Types.INTEGER));
	    } else {
	        statement = "INSERT INTO Registrant_Event (registrant_id, registrant_event, registrant_type, registrant_source, registrant_class, registrant_beneficiary, registrant_emergency_contact, registrant_emergency_phone) VALUES(?, ?, ?, ?, ?, ?, ?, ?) ";
	        sql.setStatement(statement);
	    }
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantId()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantEvent()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantType()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantSource()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantClass()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantBeneficiary()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++,  registrantEventObject.getRegistrantEmergencyContact(), Types.VARCHAR));
	    sql.setInParams(new SQLParam(index++,  registrantEventObject.getRegistrantEmergencyPhone(), Types.VARCHAR));
	    setSQLStatement(sql);
        
	    Integer result = (Integer) super.insert();
        
	    return result;
	}
    
	
    /**
	 *
	 * Deletes a row in the database. The key is 
	 * in the registrantEventObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return      Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws     DBException     If a database error occurs
	 */
    
	public Object delete() throws DBException {
	    PreparedSQLStatement sql = new PreparedSQLStatement();
	    String statement = "DELETE FROM Registrant_Event WHERE registrant_event_id = ? ";
	    int index = 1;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantEventId()), Types.INTEGER));
	    setSQLStatement(sql);
        
	    Integer result = (Integer) super.delete();
        
	    return result;
	}
    
	
    /**
	 *
	 * Updates a row in the database. The values are 
	 * got from the registrantEventObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return      Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws     DBException     If a database error occurs
	 */
    
	public Object update() throws DBException {
	    PreparedSQLStatement sql = new PreparedSQLStatement();
	    String statement = "UPDATE Registrant_Event SET registrant_event_id = ?, registrant_id = ?, registrant_event = ?, registrant_type = ?, registrant_source = ?, registrant_class = ?, registrant_beneficiary = ?, registrant_emergency_contact = ?, registrant_emergency_phone = ? where registrant_event_id = ? ";
	    int index = 1;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantEventId()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantId()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantEvent()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantType()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantSource()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantClass()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantBeneficiary()), Types.INTEGER));
	    sql.setInParams(new SQLParam(index++,  registrantEventObject.getRegistrantEmergencyContact(), Types.VARCHAR));
	    sql.setInParams(new SQLParam(index++,  registrantEventObject.getRegistrantEmergencyPhone(), Types.VARCHAR));
	    sql.setInParams(new SQLParam(index++, new Integer(registrantEventObject.getRegistrantEventId()), Types.INTEGER));
	    setSQLStatement(sql);
        
	    Integer result = (Integer) super.update();
        
	    return result;
	}
    
	
    /**
	 *
	 * Returns a ArrayList of RegistrantEventObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 * This is used by the list method.
	 *
	 * @param rs      the ResultSet.
	 *
	 * @return      Returns a ArrayList of RegistrantEventObject from the ResultSet.
	 *
	 * @see     #list()
	 *
	 */
    
	public Object getResultObjects(ResultSet rs) {
	    ArrayList<RegistrantEventObject> result = new ArrayList<RegistrantEventObject>();
        
	    try {
	        while(rs.next()) {
	            int index = 1;
	            RegistrantEventObject f = new RegistrantEventObject();
	            f.setRegistrantEventId(rs.getInt(index++));
	            f.setRegistrantId(rs.getInt(index++));
	            f.setRegistrantEvent(rs.getInt(index++));
	            f.setRegistrantType(rs.getInt(index++));
	            f.setRegistrantSource(rs.getInt(index++));
	            f.setRegistrantClass(rs.getInt(index++));
	            f.setRegistrantBeneficiary(rs.getInt(index++));
	            f.setRegistrantEmergencyContact(rs.getString(index++));
	            f.setRegistrantEmergencyPhone(rs.getString(index++));
	            result.add(f);
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}
    
	
    /**
	 *
	 * Returns a RegistrantEventObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 *
	 * This is used by the fetch method.
	 * @param rs      the ResultSet.
	 *
	 * @return      Returns a RegistrantEventObject from the ResultSet.
	 *
	 * @see     #fetch()
	 *
	 */
    
	public Object getResultSetObject(ResultSet rs) {
	    try {
	    @SuppressWarnings("unchecked")
	        ArrayList<RegistrantEventObject> result = (ArrayList<RegistrantEventObject>) getResultObjects(rs);
	        return result.get(0);
	    } catch (Exception e) {
	        return null;
	    }
	}
}
    
