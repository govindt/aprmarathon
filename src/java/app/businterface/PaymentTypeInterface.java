/*
 * PaymentTypeInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.PaymentTypeObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface PaymentTypeInterface {
    
    /**
     *
     * Interface that returns the PaymentTypeObject given a PaymentTypeObject filled with values that will be used for query from the underlying datasource.
     *
     * @param paymenttype_obj	PaymentTypeObject
     *
     * @return      Returns the ArrayList of PaymentTypeObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<PaymentTypeObject> getPaymentTypes(PaymentTypeObject paymenttype_obj) throws AppException;
    
    /**
     *
     * Interface that returns the PaymentTypeObject given payment_type_id from the underlying datasource.
     *
     * @param payment_type_id     int
     *
     * @return      Returns the PaymentTypeObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public PaymentTypeObject getPaymentType(int payment_type_id) throws AppException;
    
    /**
     *
     * Interface that returns all the <code>PaymentTypeObject</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>PaymentTypeObject</code>
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public PaymentTypeObject[] getAllPaymentTypes() throws AppException;
    
    /**
     *
     * Interface to add the <code>PaymentTypeObject</code> to the underlying datasource.
     *
     * @param paymentTypeObject     PaymentTypeObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer addPaymentType(PaymentTypeObject paymentTypeObject) throws AppException;
    
    /**
     *
     * Interface to update the <code>PaymentTypeObject</code> in the underlying datasource.
     *
     * @param paymentTypeObject     PaymentTypeObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer updatePaymentType(PaymentTypeObject paymentTypeObject) throws AppException;
    
    /**
     *
     * Interface to delete the <code>PaymentTypeObject</code> in the underlying datasource.
     *
     * @param paymentTypeObject     PaymentTypeObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer deletePaymentType(PaymentTypeObject paymentTypeObject) throws AppException;
}
