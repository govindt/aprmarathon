/*
 * PaymentTypeImpl.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.busimpl;

import java.lang.*;
import java.util.*;
import core.util.Constants;
import core.util.DebugHandler;
import core.db.DBUtil;
import core.util.AppException;
import core.util.Util;
import app.busobj.PaymentTypeObject;
import app.businterface.PaymentTypeInterface;
import app.util.AppConstants;

/**
 * The implementation of the PaymentTypeInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PaymentTypeImpl implements PaymentTypeInterface  {
    private String PAYMENTTYPE = "PaymentTypeInterface.getAllPaymentType";
    
    /**
     *
     * Implementation that returns the PaymentTypeObject given a PaymentTypeObject filled with values that will be used for query from the underlying datasource.
     *
     * @param paymenttype_obj	PaymentTypeObject
     *
     * @return      Returns the ArrayList of PaymentTypeObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
	public ArrayList<PaymentTypeObject> getPaymentTypes(PaymentTypeObject paymenttype_obj) throws AppException{
		@SuppressWarnings("unchecked")
		ArrayList<PaymentTypeObject> v = (ArrayList<PaymentTypeObject>)DBUtil.list(paymenttype_obj,paymenttype_obj);
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the PaymentTypeObject from the underlying datasource.
     * given payment_type_id.
     *
     * @param payment_type_id     int
     *
     * @return      Returns the PaymentTypeObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public PaymentTypeObject getPaymentType(int payment_type_id) throws AppException{
	PaymentTypeObject[] paymentTypeObjectArr = getAllPaymentTypes();
	if ( paymentTypeObjectArr == null )
	    return null;
	for ( int i = 0; i < paymentTypeObjectArr.length; i++ ) {
	    if ( paymentTypeObjectArr[i] == null ) { // Try database and add to cache if found.
		    PaymentTypeObject paymenttypeObj = new PaymentTypeObject();
		    paymenttypeObj.setPaymentTypeId(payment_type_id);
		    @SuppressWarnings("unchecked")
		    ArrayList<PaymentTypeObject> v = (ArrayList)DBUtil.fetch(paymenttypeObj);
		    if ( v == null || v.size() == 0 )
			    return null;
		    else {
			    paymentTypeObjectArr[i] = (PaymentTypeObject)paymenttypeObj.clone();
			    Util.putInCache(PAYMENTTYPE, paymentTypeObjectArr);
		    }
	    }
	    if ( paymentTypeObjectArr[i].getPaymentTypeId() == payment_type_id ) {
		    DebugHandler.debug("Returning " + paymentTypeObjectArr[i]);
		    return (PaymentTypeObject)paymentTypeObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>PaymentTypeObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>PaymentTypeObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public PaymentTypeObject[] getAllPaymentTypes() throws AppException{
		PaymentTypeObject paymentTypeObject = new PaymentTypeObject();
		PaymentTypeObject[] paymentTypeObjectArr = (PaymentTypeObject[])Util.getAppCache().get(PAYMENTTYPE);
		if ( paymentTypeObjectArr == null ) {
		    DebugHandler.info("Getting paymenttype from database");
		    @SuppressWarnings("unchecked")
		    ArrayList<PaymentTypeObject> v = (ArrayList)DBUtil.list(paymentTypeObject);
		    DebugHandler.finest(":v: " +  v);
		    if ( v == null )
			    return null;
		    paymentTypeObjectArr = new PaymentTypeObject[v.size()];
		    for ( int idx = 0; idx < v.size(); idx++ ) {
			    paymentTypeObjectArr[idx] = v.get(idx);
		    }
		    Util.putInCache(PAYMENTTYPE, paymentTypeObjectArr);
		}
		return paymentTypeObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>PaymentTypeObject</code> to the underlying datasource.
     *
     * @param paymentTypeObject     PaymentTypeObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addPaymentType(PaymentTypeObject paymentTypeObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Payment_Type_seq");
			paymentTypeObject.setPaymentTypeId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(paymentTypeObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			paymentTypeObject.setPaymentTypeId(i.intValue());
			DebugHandler.fine(paymentTypeObject);
		}
		PaymentTypeObject buf = new PaymentTypeObject();
		buf.setPaymentTypeId(paymentTypeObject.getPaymentTypeId());
		@SuppressWarnings("unchecked")
		ArrayList<PaymentTypeObject> v = (ArrayList)DBUtil.list(paymentTypeObject, buf);
		paymentTypeObject = v.get(0);
		PaymentTypeObject[] paymentTypeObjectArr = getAllPaymentTypes();
		boolean foundSpace = false;

		for ( int idx = 0; idx < paymentTypeObjectArr.length; idx++ ) {
			if ( paymentTypeObjectArr[idx] == null ) {
				paymentTypeObjectArr[idx] = (PaymentTypeObject)paymentTypeObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			PaymentTypeObject[] newpaymentTypeObjectArr = new PaymentTypeObject[paymentTypeObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < paymentTypeObjectArr.length; idx++ ) {
				newpaymentTypeObjectArr[idx] = (PaymentTypeObject)paymentTypeObjectArr[idx].clone();
			}
			newpaymentTypeObjectArr[idx] = (PaymentTypeObject)paymentTypeObject.clone();
			Util.putInCache(PAYMENTTYPE, newpaymentTypeObjectArr);
		} else {
			Util.putInCache(PAYMENTTYPE, paymentTypeObjectArr);
		}
		return i;
	}
	
    
    /**
     *
     * Implementation to update the <code>PaymentTypeObject</code> in the underlying datasource.
     *
     * @param paymentTypeObject     PaymentTypeObject
     *
     * @throws AppException if the operation fails
     *
     */
    
	public Integer updatePaymentType(PaymentTypeObject paymentTypeObject) throws AppException{
		PaymentTypeObject newPaymentTypeObject = getPaymentType(paymentTypeObject.getPaymentTypeId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(paymentTypeObject);
		DebugHandler.fine("i: " +  i);
		PaymentTypeObject[] paymentTypeObjectArr = getAllPaymentTypes();
		if ( paymentTypeObjectArr == null )
			return null;
		for ( int idx = 0; idx < paymentTypeObjectArr.length; idx++ ) {
			if ( paymentTypeObjectArr[idx] != null ) {
				if ( paymentTypeObjectArr[idx].getPaymentTypeId() == paymentTypeObject.getPaymentTypeId() ) {
					DebugHandler.debug("Found PaymentType " + paymentTypeObject.getPaymentTypeId());
					paymentTypeObjectArr[idx] = (PaymentTypeObject)paymentTypeObject.clone();
					Util.putInCache(PAYMENTTYPE, paymentTypeObjectArr);
				}
			}
		}
		return i;
	}
    
    
    /**
     *
     * Implementation to delete the <code>PaymentTypeObject</code> in the underlying datasource.
     *
     * @param paymentTypeObject     PaymentTypeObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deletePaymentType(PaymentTypeObject paymentTypeObject) throws AppException{
	PaymentTypeObject newPaymentTypeObject = getPaymentType(paymentTypeObject.getPaymentTypeId()); // This call will make sure cache/db are in sync
	PaymentTypeObject[] paymentTypeObjectArr = getAllPaymentTypes();
	Integer i = new Integer(0);
	if ( paymentTypeObject != null ) {
		i = (Integer)DBUtil.delete(paymentTypeObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( paymentTypeObjectArr != null ) {
			for (int idx = 0; idx < paymentTypeObjectArr.length; idx++ ) {
				if ( paymentTypeObjectArr[idx] != null && paymentTypeObjectArr[idx].getPaymentTypeId() == paymentTypeObject.getPaymentTypeId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (paymentTypeObjectArr.length - 1) )
						paymentTypeObjectArr[idx] = paymentTypeObjectArr[idx + 1]; // Move the array
					else
						paymentTypeObjectArr[idx] = null;
				}
				if ( paymentTypeObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(PAYMENTTYPE, paymentTypeObjectArr);
		}
		return i;
	}
}
