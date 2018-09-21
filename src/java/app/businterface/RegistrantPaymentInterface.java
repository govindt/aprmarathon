/*
 * RegistrantPaymentInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.RegistrantPaymentObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface RegistrantPaymentInterface {
	
	/**
	 *
	 * Interface that returns the RegistrantPaymentObject given a RegistrantPaymentObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param registrantpayment_obj	RegistrantPaymentObject
	 *
	 * @return	  Returns the ArrayList of RegistrantPaymentObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public ArrayList<RegistrantPaymentObject> getRegistrantPayments(RegistrantPaymentObject registrantpayment_obj) throws AppException;
	
	/**
	 *
	 * Interface that returns the RegistrantPaymentObject given registrant_payment_id from the underlying datasource.
	 *
	 * @param registrant_payment_id	 int
	 *
	 * @return	  Returns the RegistrantPaymentObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public RegistrantPaymentObject getRegistrantPayment(int registrant_payment_id) throws AppException;
	
	/**
	 *
	 * Interface that returns all the <code>RegistrantPaymentObject</code> from the underlying datasource.
	 *
	 * @return	  Returns an Array of <code>RegistrantPaymentObject</code>
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public RegistrantPaymentObject[] getAllRegistrantPayments() throws AppException;
	
	/**
	 *
	 * Interface to add the <code>RegistrantPaymentObject</code> to the underlying datasource.
	 *
	 * @param registrantPaymentObject	 RegistrantPaymentObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer addRegistrantPayment(RegistrantPaymentObject registrantPaymentObject) throws AppException;
	
	/**
	 *
	 * Interface to update the <code>RegistrantPaymentObject</code> in the underlying datasource.
	 *
	 * @param registrantPaymentObject	 RegistrantPaymentObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer updateRegistrantPayment(RegistrantPaymentObject registrantPaymentObject) throws AppException;
	
	/**
	 *
	 * Interface to delete the <code>RegistrantPaymentObject</code> in the underlying datasource.
	 *
	 * @param registrantPaymentObject	 RegistrantPaymentObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer deleteRegistrantPayment(RegistrantPaymentObject registrantPaymentObject) throws AppException;
}
