/*
 * RegistrantPaymentImpl.java
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
import app.busobj.RegistrantPaymentObject;
import app.businterface.RegistrantPaymentInterface;
import app.util.AppConstants;

/**
 * The implementation of the RegistrantPaymentInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrantPaymentImpl implements RegistrantPaymentInterface  {
    private String REGISTRANTPAYMENT = "RegistrantPaymentInterface.getAllRegistrantPayment";
    
    /**
     *
     * Implementation that returns the RegistrantPaymentObject given a RegistrantPaymentObject filled with values that will be used for query from the underlying datasource.
     *
     * @param registrantpayment_obj	RegistrantPaymentObject
     *
     * @return      Returns the ArrayList of RegistrantPaymentObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<RegistrantPaymentObject> getRegistrantPayments(RegistrantPaymentObject registrantpayment_obj) throws AppException{
	RegistrantPaymentObject[] registrantPaymentObjectArr = getAllRegistrantPayments();
	ArrayList<RegistrantPaymentObject> v = new ArrayList<RegistrantPaymentObject>();
	if ( registrantPaymentObjectArr == null )
		return null;
	for ( int i = 0; i < registrantPaymentObjectArr.length; i++ ) {
		if ( registrantPaymentObjectArr[i] != null ) {
			if ( registrantpayment_obj.getRegistrantPaymentId() == Constants.GET_ALL ) {
				v.add((RegistrantPaymentObject)registrantPaymentObjectArr[i].clone());
			} else {
				if ( (registrantpayment_obj.getRegistrantPaymentId() != 0 && registrantpayment_obj.getRegistrantPaymentId() == registrantPaymentObjectArr[i].getRegistrantPaymentId())
 || (registrantpayment_obj.getRegistrantEvent() != 0 && registrantpayment_obj.getRegistrantEvent() == registrantPaymentObjectArr[i].getRegistrantEvent())
 || (registrantpayment_obj.getRegistrant() != 0 && registrantpayment_obj.getRegistrant() == registrantPaymentObjectArr[i].getRegistrant())
 || (registrantpayment_obj.getPaymentType() != 0 && registrantpayment_obj.getPaymentType() == registrantPaymentObjectArr[i].getPaymentType())
 || (registrantpayment_obj.getPaymentStatus() != 0 && registrantpayment_obj.getPaymentStatus() == registrantPaymentObjectArr[i].getPaymentStatus())
 || (registrantpayment_obj.getPaymentAmount() != 0 && registrantpayment_obj.getPaymentAmount() == registrantPaymentObjectArr[i].getPaymentAmount())
 || (registrantpayment_obj.getPaymentAdditionalAmount() != 0 && registrantpayment_obj.getPaymentAdditionalAmount() == registrantPaymentObjectArr[i].getPaymentAdditionalAmount())
 || (registrantpayment_obj.getPaymentDate() != null && registrantpayment_obj.getPaymentDate().equals(registrantPaymentObjectArr[i].getPaymentDate()))
 || (registrantpayment_obj.getReceiptDate() != null && registrantpayment_obj.getReceiptDate().equals(registrantPaymentObjectArr[i].getReceiptDate()))
 || (registrantpayment_obj.getPaymentDetails() != null && registrantpayment_obj.getPaymentDetails().equals(registrantPaymentObjectArr[i].getPaymentDetails()))
 || (registrantpayment_obj.getPaymentTowards() != null && registrantpayment_obj.getPaymentTowards().equals(registrantPaymentObjectArr[i].getPaymentTowards()))
 || (registrantpayment_obj.getPaymentReferenceId() != null && registrantpayment_obj.getPaymentReferenceId().equals(registrantPaymentObjectArr[i].getPaymentReferenceId()))
 || (registrantpayment_obj.getPaymentTax() != 0 && registrantpayment_obj.getPaymentTax() == registrantPaymentObjectArr[i].getPaymentTax())
 || (registrantpayment_obj.getPaymentFee() != 0 && registrantpayment_obj.getPaymentFee() == registrantPaymentObjectArr[i].getPaymentFee())
) {
					v.add((RegistrantPaymentObject)registrantPaymentObjectArr[i].clone());
				}
			}
		}
	}
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the RegistrantPaymentObject from the underlying datasource.
     * given registrant_payment_id.
     *
     * @param registrant_payment_id     int
     *
     * @return      Returns the RegistrantPaymentObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public RegistrantPaymentObject getRegistrantPayment(int registrant_payment_id) throws AppException{
	RegistrantPaymentObject[] registrantPaymentObjectArr = getAllRegistrantPayments();
	if ( registrantPaymentObjectArr == null )
	    return null;
	for ( int i = 0; i < registrantPaymentObjectArr.length; i++ ) {
	    if ( registrantPaymentObjectArr[i] == null ) { // Try database and add to cache if found.
		    RegistrantPaymentObject registrantpaymentObj = new RegistrantPaymentObject();
		    registrantpaymentObj.setRegistrantPaymentId(registrant_payment_id);
		    @SuppressWarnings("unchecked")
		    ArrayList<RegistrantPaymentObject> v = (ArrayList)DBUtil.fetch(registrantpaymentObj);
		    if ( v == null || v.size() == 0 )
			    return null;
		    else {
			    registrantPaymentObjectArr[i] = (RegistrantPaymentObject)registrantpaymentObj.clone();
			    Util.putInCache(REGISTRANTPAYMENT, registrantPaymentObjectArr);
		    }
	    }
	    if ( registrantPaymentObjectArr[i].getRegistrantPaymentId() == registrant_payment_id ) {
		    DebugHandler.debug("Returning " + registrantPaymentObjectArr[i]);
		    return (RegistrantPaymentObject)registrantPaymentObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>RegistrantPaymentObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>RegistrantPaymentObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public RegistrantPaymentObject[] getAllRegistrantPayments() throws AppException{
		RegistrantPaymentObject registrantPaymentObject = new RegistrantPaymentObject();
		RegistrantPaymentObject[] registrantPaymentObjectArr = (RegistrantPaymentObject[])Util.getAppCache().get(REGISTRANTPAYMENT);
		if ( registrantPaymentObjectArr == null ) {
		    DebugHandler.info("Getting registrantpayment from database");
		    @SuppressWarnings("unchecked")
		    ArrayList<RegistrantPaymentObject> v = (ArrayList)DBUtil.list(registrantPaymentObject);
		    DebugHandler.finest(":v: " +  v);
		    if ( v == null )
			    return null;
		    registrantPaymentObjectArr = new RegistrantPaymentObject[v.size()];
		    for ( int idx = 0; idx < v.size(); idx++ ) {
			    registrantPaymentObjectArr[idx] = v.get(idx);
		    }
		    Util.putInCache(REGISTRANTPAYMENT, registrantPaymentObjectArr);
		}
		return registrantPaymentObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>RegistrantPaymentObject</code> to the underlying datasource.
     *
     * @param registrantPaymentObject     RegistrantPaymentObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addRegistrantPayment(RegistrantPaymentObject registrantPaymentObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Registrant_Payment_seq");
			registrantPaymentObject.setRegistrantPaymentId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(registrantPaymentObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			registrantPaymentObject.setRegistrantPaymentId(i.intValue());
			DebugHandler.fine(registrantPaymentObject);
		}
		RegistrantPaymentObject buf = new RegistrantPaymentObject();
		buf.setRegistrantPaymentId(registrantPaymentObject.getRegistrantPaymentId());
		@SuppressWarnings("unchecked")
		ArrayList<RegistrantPaymentObject> v = (ArrayList)DBUtil.list(registrantPaymentObject, buf);
		registrantPaymentObject = v.get(0);
		RegistrantPaymentObject[] registrantPaymentObjectArr = getAllRegistrantPayments();
		boolean foundSpace = false;

		for ( int idx = 0; idx < registrantPaymentObjectArr.length; idx++ ) {
			if ( registrantPaymentObjectArr[idx] == null ) {
				registrantPaymentObjectArr[idx] = (RegistrantPaymentObject)registrantPaymentObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			RegistrantPaymentObject[] newregistrantPaymentObjectArr = new RegistrantPaymentObject[registrantPaymentObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < registrantPaymentObjectArr.length; idx++ ) {
				newregistrantPaymentObjectArr[idx] = (RegistrantPaymentObject)registrantPaymentObjectArr[idx].clone();
			}
			newregistrantPaymentObjectArr[idx] = (RegistrantPaymentObject)registrantPaymentObject.clone();
			Util.putInCache(REGISTRANTPAYMENT, newregistrantPaymentObjectArr);
		} else {
			Util.putInCache(REGISTRANTPAYMENT, registrantPaymentObjectArr);
		}
		return i;
	}
	
    
    /**
     *
     * Implementation to update the <code>RegistrantPaymentObject</code> in the underlying datasource.
     *
     * @param registrantPaymentObject     RegistrantPaymentObject
     *
     * @throws AppException if the operation fails
     *
     */
    
	public Integer updateRegistrantPayment(RegistrantPaymentObject registrantPaymentObject) throws AppException{
		RegistrantPaymentObject newRegistrantPaymentObject = getRegistrantPayment(registrantPaymentObject.getRegistrantPaymentId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(registrantPaymentObject);
		DebugHandler.fine("i: " +  i);
		RegistrantPaymentObject[] registrantPaymentObjectArr = getAllRegistrantPayments();
		if ( registrantPaymentObjectArr == null )
			return null;
		for ( int idx = 0; idx < registrantPaymentObjectArr.length; idx++ ) {
			if ( registrantPaymentObjectArr[idx] != null ) {
				if ( registrantPaymentObjectArr[idx].getRegistrantPaymentId() == registrantPaymentObject.getRegistrantPaymentId() ) {
					DebugHandler.debug("Found RegistrantPayment " + registrantPaymentObject.getRegistrantPaymentId());
					registrantPaymentObjectArr[idx] = (RegistrantPaymentObject)registrantPaymentObject.clone();
					Util.putInCache(REGISTRANTPAYMENT, registrantPaymentObjectArr);
				}
			}
		}
		return i;
	}
    
    
    /**
     *
     * Implementation to delete the <code>RegistrantPaymentObject</code> in the underlying datasource.
     *
     * @param registrantPaymentObject     RegistrantPaymentObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteRegistrantPayment(RegistrantPaymentObject registrantPaymentObject) throws AppException{
	RegistrantPaymentObject newRegistrantPaymentObject = getRegistrantPayment(registrantPaymentObject.getRegistrantPaymentId()); // This call will make sure cache/db are in sync
	RegistrantPaymentObject[] registrantPaymentObjectArr = getAllRegistrantPayments();
	Integer i = new Integer(0);
	if ( registrantPaymentObject != null ) {
		i = (Integer)DBUtil.delete(registrantPaymentObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( registrantPaymentObjectArr != null ) {
			for (int idx = 0; idx < registrantPaymentObjectArr.length; idx++ ) {
				if ( registrantPaymentObjectArr[idx] != null && registrantPaymentObjectArr[idx].getRegistrantPaymentId() == registrantPaymentObject.getRegistrantPaymentId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (registrantPaymentObjectArr.length - 1) )
						registrantPaymentObjectArr[idx] = registrantPaymentObjectArr[idx + 1]; // Move the array
					else
						registrantPaymentObjectArr[idx] = null;
				}
				if ( registrantPaymentObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(REGISTRANTPAYMENT, registrantPaymentObjectArr);
		}
		return i;
	}
}
