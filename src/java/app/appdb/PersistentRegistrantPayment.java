/*
 * PersistentRegistrantPayment.java
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
import app.busobj.RegistrantPaymentObject;

/**
 * The persistent implementation of the RegistrantPaymentObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentRegistrantPayment extends PersistentObject {
    private RegistrantPaymentObject registrantPaymentObject;
    
    
    /**
     * Constructs a Persistent Object for the RegistrantPaymentObject
     *
     * @param registrantPaymentObject    the RegistrantPaymentObject 
     */
    
    public PersistentRegistrantPayment (RegistrantPaymentObject registrantPaymentObject) {
        this.registrantPaymentObject = registrantPaymentObject;
    }
    
    
    /**
     * Returns the ArrayList of RegistrantPaymentObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of RegistrantPaymentObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT registrant_payment_id, registrant_event, registrant, payment_type, payment_status, payment_amount, payment_additional_amount, payment_date, receipt_date, payment_details, payment_towards, payment_reference_id, payment_tax, payment_fee from Registrant_Payment";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<RegistrantPaymentObject> result = (ArrayList<RegistrantPaymentObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of RegistrantPaymentObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of RegistrantPaymentObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT registrant_payment_id, registrant_event, registrant, payment_type, payment_status, payment_amount, payment_additional_amount, payment_date, receipt_date, payment_details, payment_towards, payment_reference_id, payment_tax, payment_fee from Registrant_Payment";
		int index = 1;
		RegistrantPaymentObject passedRegistrantPaymentObject = (RegistrantPaymentObject)args;
		boolean whereSpecified = false;

		if ( passedRegistrantPaymentObject.getRegistrantPaymentId() != 0 ) {
			statement += " where registrant_payment_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantPaymentObject.getRegistrantPaymentId()), Types.INTEGER));
		}
		if ( passedRegistrantPaymentObject.getRegistrantEvent() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where registrant_event = ?";
				whereSpecified = true;
			} else
				statement += " and registrant_event = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantPaymentObject.getRegistrantEvent()), Types.INTEGER));
		}
		if ( passedRegistrantPaymentObject.getRegistrant() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where registrant = ?";
				whereSpecified = true;
			} else
				statement += " and registrant = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantPaymentObject.getRegistrant()), Types.INTEGER));
		}
		if ( passedRegistrantPaymentObject.getPaymentType() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where payment_type = ?";
				whereSpecified = true;
			} else
				statement += " and payment_type = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantPaymentObject.getPaymentType()), Types.INTEGER));
		}
		if ( passedRegistrantPaymentObject.getPaymentStatus() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where payment_status = ?";
				whereSpecified = true;
			} else
				statement += " and payment_status = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedRegistrantPaymentObject.getPaymentStatus()), Types.INTEGER));
		}
		if ( passedRegistrantPaymentObject.getPaymentAmount() != 0.0 ) {
			if ( ! whereSpecified ) {
				statement += " where payment_amount = ?";
				whereSpecified = true;
			} else
				statement += " and payment_amount = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  new Double(passedRegistrantPaymentObject.getPaymentAmount()), Types.DOUBLE));
		}
		if ( passedRegistrantPaymentObject.getPaymentAdditionalAmount() != 0.0 ) {
			if ( ! whereSpecified ) {
				statement += " where payment_additional_amount = ?";
				whereSpecified = true;
			} else
				statement += " and payment_additional_amount = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  new Double(passedRegistrantPaymentObject.getPaymentAdditionalAmount()), Types.DOUBLE));
		}
		if ( passedRegistrantPaymentObject.getPaymentDate() != null ) {
			if ( ! whereSpecified ) {
				statement += " where payment_date = ?";
				whereSpecified = true;
			} else
				statement += " and payment_date = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantPaymentObject.getPaymentDate(), Types.TIMESTAMP));
		}
		if ( passedRegistrantPaymentObject.getReceiptDate() != null ) {
			if ( ! whereSpecified ) {
				statement += " where receipt_date = ?";
				whereSpecified = true;
			} else
				statement += " and receipt_date = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantPaymentObject.getReceiptDate(), Types.TIMESTAMP));
		}
		if ( ! passedRegistrantPaymentObject.getPaymentDetails().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where payment_details = ?";
				whereSpecified = true;
			} else
				statement += " and payment_details = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantPaymentObject.getPaymentDetails(), Types.VARCHAR));
		}
		if ( ! passedRegistrantPaymentObject.getPaymentTowards().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where payment_towards = ?";
				whereSpecified = true;
			} else
				statement += " and payment_towards = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantPaymentObject.getPaymentTowards(), Types.VARCHAR));
		}
		if ( ! passedRegistrantPaymentObject.getPaymentReferenceId().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where payment_reference_id = ?";
				whereSpecified = true;
			} else
				statement += " and payment_reference_id = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedRegistrantPaymentObject.getPaymentReferenceId(), Types.VARCHAR));
		}
		if ( passedRegistrantPaymentObject.getPaymentTax() != 0.0 ) {
			if ( ! whereSpecified ) {
				statement += " where payment_tax = ?";
				whereSpecified = true;
			} else
				statement += " and payment_tax = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  new Double(passedRegistrantPaymentObject.getPaymentTax()), Types.DOUBLE));
		}
		if ( passedRegistrantPaymentObject.getPaymentFee() != 0.0 ) {
			if ( ! whereSpecified ) {
				statement += " where payment_fee = ?";
				whereSpecified = true;
			} else
				statement += " and payment_fee = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  new Double(passedRegistrantPaymentObject.getPaymentFee()), Types.DOUBLE));
		}
		sql.setStatement(statement);
        
		DebugHandler.debug(statement);
		setSQLStatement(sql);
        
		@SuppressWarnings("unchecked")
		ArrayList<RegistrantPaymentObject> result = (ArrayList<RegistrantPaymentObject>) super.list();
        
		return result;
	}
    
    
    /**
     * Returns the ArrayList of one RegistrantPaymentObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     ArrayList of one RegistrantPaymentObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT registrant_payment_id, registrant_event, registrant, payment_type, payment_status, payment_amount, payment_additional_amount, payment_date, receipt_date, payment_details, payment_towards, payment_reference_id, payment_tax, payment_fee from Registrant_Payment where registrant_payment_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getRegistrantPaymentId()), Types.INTEGER));
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<RegistrantPaymentObject> result = (ArrayList<RegistrantPaymentObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the registrantPaymentObject.
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
            statement = "INSERT INTO Registrant_Payment (registrant_payment_id, registrant_event, registrant, payment_type, payment_status, payment_amount, payment_additional_amount, payment_date, receipt_date, payment_details, payment_towards, payment_reference_id, payment_tax, payment_fee) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            sql.setStatement(statement);
            sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getRegistrantPaymentId()), Types.INTEGER));
        } else {
            statement = "INSERT INTO Registrant_Payment (registrant_event, registrant, payment_type, payment_status, payment_amount, payment_additional_amount, payment_date, receipt_date, payment_details, payment_towards, payment_reference_id, payment_tax, payment_fee) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
            sql.setStatement(statement);
        }
        sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getRegistrantEvent()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getRegistrant()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getPaymentType()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getPaymentStatus()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  new Double(registrantPaymentObject.getPaymentAmount()), Types.DOUBLE));
        sql.setInParams(new SQLParam(index++,  new Double(registrantPaymentObject.getPaymentAdditionalAmount()), Types.DOUBLE));
        sql.setInParams(new SQLParam(index++,  registrantPaymentObject.getPaymentDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  registrantPaymentObject.getReceiptDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  registrantPaymentObject.getPaymentDetails(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  registrantPaymentObject.getPaymentTowards(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  registrantPaymentObject.getPaymentReferenceId(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  new Double(registrantPaymentObject.getPaymentTax()), Types.DOUBLE));
        sql.setInParams(new SQLParam(index++,  new Double(registrantPaymentObject.getPaymentFee()), Types.DOUBLE));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the registrantPaymentObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Registrant_Payment WHERE registrant_payment_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getRegistrantPaymentId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the registrantPaymentObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Registrant_Payment SET registrant_payment_id = ?, registrant_event = ?, registrant = ?, payment_type = ?, payment_status = ?, payment_amount = ?, payment_additional_amount = ?, payment_date = ?, receipt_date = ?, payment_details = ?, payment_towards = ?, payment_reference_id = ?, payment_tax = ?, payment_fee = ? where registrant_payment_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getRegistrantPaymentId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getRegistrantEvent()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getRegistrant()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getPaymentType()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getPaymentStatus()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  new Double(registrantPaymentObject.getPaymentAmount()), Types.DOUBLE));
        sql.setInParams(new SQLParam(index++,  new Double(registrantPaymentObject.getPaymentAdditionalAmount()), Types.DOUBLE));
        sql.setInParams(new SQLParam(index++,  registrantPaymentObject.getPaymentDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  registrantPaymentObject.getReceiptDate(), Types.TIMESTAMP));
        sql.setInParams(new SQLParam(index++,  registrantPaymentObject.getPaymentDetails(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  registrantPaymentObject.getPaymentTowards(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  registrantPaymentObject.getPaymentReferenceId(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  new Double(registrantPaymentObject.getPaymentTax()), Types.DOUBLE));
        sql.setInParams(new SQLParam(index++,  new Double(registrantPaymentObject.getPaymentFee()), Types.DOUBLE));
        sql.setInParams(new SQLParam(index++, new Integer(registrantPaymentObject.getRegistrantPaymentId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a ArrayList of RegistrantPaymentObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a ArrayList of RegistrantPaymentObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        ArrayList<RegistrantPaymentObject> result = new ArrayList<RegistrantPaymentObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                RegistrantPaymentObject f = new RegistrantPaymentObject();
                f.setRegistrantPaymentId(rs.getInt(index++));
                f.setRegistrantEvent(rs.getInt(index++));
                f.setRegistrant(rs.getInt(index++));
                f.setPaymentType(rs.getInt(index++));
                f.setPaymentStatus(rs.getInt(index++));
                f.setPaymentAmount(rs.getDouble(index++));
                f.setPaymentAdditionalAmount(rs.getDouble(index++));
                f.setPaymentDate(rs.getDate(index++));
                f.setReceiptDate(rs.getDate(index++));
                f.setPaymentDetails(rs.getString(index++));
                f.setPaymentTowards(rs.getString(index++));
                f.setPaymentReferenceId(rs.getString(index++));
                f.setPaymentTax(rs.getDouble(index++));
                f.setPaymentFee(rs.getDouble(index++));
                result.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * Returns a RegistrantPaymentObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a RegistrantPaymentObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
        @SuppressWarnings("unchecked")
            ArrayList<RegistrantPaymentObject> result = (ArrayList<RegistrantPaymentObject>) getResultObjects(rs);
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
