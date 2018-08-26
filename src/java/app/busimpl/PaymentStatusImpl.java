/*
 * PaymentStatusImpl.java
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
import app.busobj.PaymentStatusObject;
import app.businterface.PaymentStatusInterface;
import app.util.AppConstants;

/**
 * The implementation of the PaymentStatusInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PaymentStatusImpl implements PaymentStatusInterface  {
    private String PAYMENTSTATUS = "PaymentStatusInterface.getAllPaymentStatus";
    
    /**
     *
     * Implementation that returns the PaymentStatusObject given a PaymentStatusObject filled with values that will be used for query from the underlying datasource.
     *
     * @param paymentstatus_obj	PaymentStatusObject
     *
     * @return      Returns the ArrayList of PaymentStatusObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<PaymentStatusObject> getPaymentStatus(PaymentStatusObject paymentstatus_obj) throws AppException{
	PaymentStatusObject[] paymentStatusObjectArr = getAllPaymentStatus();
	ArrayList<PaymentStatusObject> v = new ArrayList<PaymentStatusObject>();
	if ( paymentStatusObjectArr == null )
		return null;
	for ( int i = 0; i < paymentStatusObjectArr.length; i++ ) {
		if ( paymentStatusObjectArr[i] != null ) {
			if ( paymentstatus_obj.getPaymentStatusId() == Constants.GET_ALL ) {
				v.add((PaymentStatusObject)paymentStatusObjectArr[i].clone());
			} else {
				if ( (paymentstatus_obj.getPaymentStatusId() != 0 && paymentstatus_obj.getPaymentStatusId() == paymentStatusObjectArr[i].getPaymentStatusId())
 || (paymentstatus_obj.getPaymentStatusName() != null && paymentstatus_obj.getPaymentStatusName().equals(paymentStatusObjectArr[i].getPaymentStatusName()))
) {
					v.add((PaymentStatusObject)paymentStatusObjectArr[i].clone());
				}
			}
		}
	}
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the PaymentStatusObject from the underlying datasource.
     * given payment_status_id.
     *
     * @param payment_status_id     int
     *
     * @return      Returns the PaymentStatusObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public PaymentStatusObject getPaymentStatu(int payment_status_id) throws AppException{
	PaymentStatusObject[] paymentStatusObjectArr = getAllPaymentStatus();
	if ( paymentStatusObjectArr == null )
	    return null;
	for ( int i = 0; i < paymentStatusObjectArr.length; i++ ) {
	    if ( paymentStatusObjectArr[i] == null ) { // Try database and add to cache if found.
		    PaymentStatusObject paymentstatusObj = new PaymentStatusObject();
		    paymentstatusObj.setPaymentStatusId(payment_status_id);
		    @SuppressWarnings("unchecked")
		    ArrayList<PaymentStatusObject> v = (ArrayList)DBUtil.fetch(paymentstatusObj);
		    if ( v == null || v.size() == 0 )
			    return null;
		    else {
			    paymentStatusObjectArr[i] = (PaymentStatusObject)paymentstatusObj.clone();
			    Util.putInCache(PAYMENTSTATUS, paymentStatusObjectArr);
		    }
	    }
	    if ( paymentStatusObjectArr[i].getPaymentStatusId() == payment_status_id ) {
		    DebugHandler.debug("Returning " + paymentStatusObjectArr[i]);
		    return (PaymentStatusObject)paymentStatusObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>PaymentStatusObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>PaymentStatusObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public PaymentStatusObject[] getAllPaymentStatus() throws AppException{
		PaymentStatusObject paymentStatusObject = new PaymentStatusObject();
		PaymentStatusObject[] paymentStatusObjectArr = (PaymentStatusObject[])Util.getAppCache().get(PAYMENTSTATUS);
		if ( paymentStatusObjectArr == null ) {
		    DebugHandler.info("Getting paymentstatus from database");
		    @SuppressWarnings("unchecked")
		    ArrayList<PaymentStatusObject> v = (ArrayList)DBUtil.list(paymentStatusObject);
		    DebugHandler.finest(":v: " +  v);
		    if ( v == null )
			    return null;
		    paymentStatusObjectArr = new PaymentStatusObject[v.size()];
		    for ( int idx = 0; idx < v.size(); idx++ ) {
			    paymentStatusObjectArr[idx] = v.get(idx);
		    }
		    Util.putInCache(PAYMENTSTATUS, paymentStatusObjectArr);
		}
		return paymentStatusObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>PaymentStatusObject</code> to the underlying datasource.
     *
     * @param paymentStatusObject     PaymentStatusObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addPaymentStatus(PaymentStatusObject paymentStatusObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Payment_Status_seq");
			paymentStatusObject.setPaymentStatusId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(paymentStatusObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			paymentStatusObject.setPaymentStatusId(i.intValue());
			DebugHandler.fine(paymentStatusObject);
		}
		PaymentStatusObject buf = new PaymentStatusObject();
		buf.setPaymentStatusId(paymentStatusObject.getPaymentStatusId());
		@SuppressWarnings("unchecked")
		ArrayList<PaymentStatusObject> v = (ArrayList)DBUtil.list(paymentStatusObject, buf);
		paymentStatusObject = v.get(0);
		PaymentStatusObject[] paymentStatusObjectArr = getAllPaymentStatus();
		boolean foundSpace = false;

		for ( int idx = 0; idx < paymentStatusObjectArr.length; idx++ ) {
			if ( paymentStatusObjectArr[idx] == null ) {
				paymentStatusObjectArr[idx] = (PaymentStatusObject)paymentStatusObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			PaymentStatusObject[] newpaymentStatusObjectArr = new PaymentStatusObject[paymentStatusObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < paymentStatusObjectArr.length; idx++ ) {
				newpaymentStatusObjectArr[idx] = (PaymentStatusObject)paymentStatusObjectArr[idx].clone();
			}
			newpaymentStatusObjectArr[idx] = (PaymentStatusObject)paymentStatusObject.clone();
			Util.putInCache(PAYMENTSTATUS, newpaymentStatusObjectArr);
		} else {
			Util.putInCache(PAYMENTSTATUS, paymentStatusObjectArr);
		}
		return i;
	}
	
    
    /**
     *
     * Implementation to update the <code>PaymentStatusObject</code> in the underlying datasource.
     *
     * @param paymentStatusObject     PaymentStatusObject
     *
     * @throws AppException if the operation fails
     *
     */
    
	public Integer updatePaymentStatus(PaymentStatusObject paymentStatusObject) throws AppException{
		PaymentStatusObject newPaymentStatusObject = getPaymentStatu(paymentStatusObject.getPaymentStatusId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(paymentStatusObject);
		DebugHandler.fine("i: " +  i);
		PaymentStatusObject[] paymentStatusObjectArr = getAllPaymentStatus();
		if ( paymentStatusObjectArr == null )
			return null;
		for ( int idx = 0; idx < paymentStatusObjectArr.length; idx++ ) {
			if ( paymentStatusObjectArr[idx] != null ) {
				if ( paymentStatusObjectArr[idx].getPaymentStatusId() == paymentStatusObject.getPaymentStatusId() ) {
					DebugHandler.debug("Found PaymentStatus " + paymentStatusObject.getPaymentStatusId());
					paymentStatusObjectArr[idx] = (PaymentStatusObject)paymentStatusObject.clone();
					Util.putInCache(PAYMENTSTATUS, paymentStatusObjectArr);
				}
			}
		}
		return i;
	}
    
    
    /**
     *
     * Implementation to delete the <code>PaymentStatusObject</code> in the underlying datasource.
     *
     * @param paymentStatusObject     PaymentStatusObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deletePaymentStatus(PaymentStatusObject paymentStatusObject) throws AppException{
	PaymentStatusObject newPaymentStatusObject = getPaymentStatu(paymentStatusObject.getPaymentStatusId()); // This call will make sure cache/db are in sync
	PaymentStatusObject[] paymentStatusObjectArr = getAllPaymentStatus();
	Integer i = new Integer(0);
	if ( paymentStatusObject != null ) {
		i = (Integer)DBUtil.delete(paymentStatusObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( paymentStatusObjectArr != null ) {
			for (int idx = 0; idx < paymentStatusObjectArr.length; idx++ ) {
				if ( paymentStatusObjectArr[idx] != null && paymentStatusObjectArr[idx].getPaymentStatusId() == paymentStatusObject.getPaymentStatusId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (paymentStatusObjectArr.length - 1) )
						paymentStatusObjectArr[idx] = paymentStatusObjectArr[idx + 1]; // Move the array
					else
						paymentStatusObjectArr[idx] = null;
				}
				if ( paymentStatusObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(PAYMENTSTATUS, paymentStatusObjectArr);
		}
		return i;
	}
}
