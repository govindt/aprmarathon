/*
 * PersistentPaymentType.java
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
import app.busobj.PaymentTypeObject;

/**
 * The persistent implementation of the PaymentTypeObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentPaymentType extends PersistentObject {
    private PaymentTypeObject paymentTypeObject;
    
    
    /**
     * Constructs a Persistent Object for the PaymentTypeObject
     *
     * @param paymentTypeObject    the PaymentTypeObject 
     */
    
    public PersistentPaymentType (PaymentTypeObject paymentTypeObject) {
        this.paymentTypeObject = paymentTypeObject;
    }
    
    
    /**
     * Returns the ArrayList of PaymentTypeObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of PaymentTypeObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT payment_type_id, payment_type_name from Payment_Type";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<PaymentTypeObject> result = (ArrayList<PaymentTypeObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of PaymentTypeObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of PaymentTypeObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list(Object args) throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT payment_type_id, payment_type_name from Payment_Type";
        int index = 1;
        PaymentTypeObject passedPaymentTypeObject = (PaymentTypeObject)args;
        boolean whereSpecified = false;

        if ( passedPaymentTypeObject.getPaymentTypeId() != 0 ) {
	    statement += " where payment_type_id = ?";
	    whereSpecified = true;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedPaymentTypeObject.getPaymentTypeId()), Types.INTEGER));
	}
        if ( ! passedPaymentTypeObject.getPaymentTypeName().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where payment_type_name = ?";
		whereSpecified = true;
	    } else
		statement += " and payment_type_name = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedPaymentTypeObject.getPaymentTypeName(), Types.VARCHAR));
	}
        sql.setStatement(statement);
        
        DebugHandler.debug(statement);
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<PaymentTypeObject> result = (ArrayList<PaymentTypeObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of one PaymentTypeObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     ArrayList of one PaymentTypeObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT payment_type_id, payment_type_name from Payment_Type where payment_type_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(paymentTypeObject.getPaymentTypeId()), Types.INTEGER));
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<PaymentTypeObject> result = (ArrayList<PaymentTypeObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the paymentTypeObject.
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
            statement = "INSERT INTO Payment_Type (payment_type_id, payment_type_name) VALUES(?, ?) ";
            sql.setStatement(statement);
            sql.setInParams(new SQLParam(index++, new Integer(paymentTypeObject.getPaymentTypeId()), Types.INTEGER));
        } else {
            statement = "INSERT INTO Payment_Type (payment_type_name) VALUES(?) ";
            sql.setStatement(statement);
        }
        sql.setInParams(new SQLParam(index++,  paymentTypeObject.getPaymentTypeName(), Types.VARCHAR));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the paymentTypeObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Payment_Type WHERE payment_type_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(paymentTypeObject.getPaymentTypeId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the paymentTypeObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Payment_Type SET payment_type_id = ?, payment_type_name = ? where payment_type_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(paymentTypeObject.getPaymentTypeId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  paymentTypeObject.getPaymentTypeName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(paymentTypeObject.getPaymentTypeId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a ArrayList of PaymentTypeObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a ArrayList of PaymentTypeObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        ArrayList<PaymentTypeObject> result = new ArrayList<PaymentTypeObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                PaymentTypeObject f = new PaymentTypeObject();
                f.setPaymentTypeId(rs.getInt(index++));
                f.setPaymentTypeName(rs.getString(index++));
                result.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * Returns a PaymentTypeObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a PaymentTypeObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
        @SuppressWarnings("unchecked")
            ArrayList<PaymentTypeObject> result = (ArrayList<PaymentTypeObject>) getResultObjects(rs);
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
