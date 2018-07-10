/*
 * PaymentStatusInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.PaymentStatusObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface PaymentStatusInterface {
    
    /**
     *
     * Interface that returns the PaymentStatusObject given a PaymentStatusObject filled with values that will be used for query from the underlying datasource.
     *
     * @param paymentstatus_obj	PaymentStatusObject
     *
     * @return      Returns the ArrayList of PaymentStatusObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<PaymentStatusObject> getPaymentStatus(PaymentStatusObject paymentstatus_obj) throws AppException;
    
    /**
     *
     * Interface that returns the PaymentStatusObject given payment_status_id from the underlying datasource.
     *
     * @param payment_status_id     int
     *
     * @return      Returns the PaymentStatusObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public PaymentStatusObject getPaymentStatu(int payment_status_id) throws AppException;
    
    /**
     *
     * Interface that returns all the <code>PaymentStatusObject</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>PaymentStatusObject</code>
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public PaymentStatusObject[] getAllPaymentStatus() throws AppException;
    
    /**
     *
     * Interface to add the <code>PaymentStatusObject</code> to the underlying datasource.
     *
     * @param paymentStatusObject     PaymentStatusObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer addPaymentStatus(PaymentStatusObject paymentStatusObject) throws AppException;
    
    /**
     *
     * Interface to update the <code>PaymentStatusObject</code> in the underlying datasource.
     *
     * @param paymentStatusObject     PaymentStatusObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer updatePaymentStatus(PaymentStatusObject paymentStatusObject) throws AppException;
    
    /**
     *
     * Interface to delete the <code>PaymentStatusObject</code> in the underlying datasource.
     *
     * @param paymentStatusObject     PaymentStatusObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer deletePaymentStatus(PaymentStatusObject paymentStatusObject) throws AppException;
}
