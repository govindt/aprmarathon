/*
 * BeneficiaryImpl.java
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
import app.busobj.BeneficiaryObject;
import app.businterface.BeneficiaryInterface;
import app.util.AppConstants;

/**
 * The implementation of the BeneficiaryInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class BeneficiaryImpl implements BeneficiaryInterface  {
    private String BENEFICIARY = "BeneficiaryInterface.getAllBeneficiary";
    
    /**
     *
     * Implementation that returns the BeneficiaryObject given a BeneficiaryObject filled with values that will be used for query from the underlying datasource.
     *
     * @param beneficiary_obj	BeneficiaryObject
     *
     * @return      Returns the ArrayList of BeneficiaryObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<BeneficiaryObject> getBeneficiarys(BeneficiaryObject beneficiary_obj) throws AppException{
	BeneficiaryObject[] beneficiaryObjectArr = getAllBeneficiarys();
	ArrayList<BeneficiaryObject> v = new ArrayList<BeneficiaryObject>();
	if ( beneficiaryObjectArr == null )
		return null;
	for ( int i = 0; i < beneficiaryObjectArr.length; i++ ) {
		if ( beneficiaryObjectArr[i] != null ) {
			if ( beneficiary_obj.getBeneficiaryId() == Constants.GET_ALL ) {
				v.add((BeneficiaryObject)beneficiaryObjectArr[i].clone());
			} else {
				if ( (beneficiary_obj.getBeneficiaryId() != 0 && beneficiary_obj.getBeneficiaryId() == beneficiaryObjectArr[i].getBeneficiaryId())
 || (beneficiary_obj.getBeneficiaryName() != null && beneficiary_obj.getBeneficiaryName().equals(beneficiaryObjectArr[i].getBeneficiaryName()))
 || (beneficiary_obj.getBeneficiaryEvent() != 0 && beneficiary_obj.getBeneficiaryEvent() == beneficiaryObjectArr[i].getBeneficiaryEvent())
) {
					v.add((BeneficiaryObject)beneficiaryObjectArr[i].clone());
				}
			}
		}
	}
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the BeneficiaryObject from the underlying datasource.
     * given beneficiary_id.
     *
     * @param beneficiary_id     int
     *
     * @return      Returns the BeneficiaryObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public BeneficiaryObject getBeneficiary(int beneficiary_id) throws AppException{
	BeneficiaryObject[] beneficiaryObjectArr = getAllBeneficiarys();
	if ( beneficiaryObjectArr == null )
	    return null;
	for ( int i = 0; i < beneficiaryObjectArr.length; i++ ) {
	    if ( beneficiaryObjectArr[i] == null ) { // Try database and add to cache if found.
		    BeneficiaryObject beneficiaryObj = new BeneficiaryObject();
		    beneficiaryObj.setBeneficiaryId(beneficiary_id);
		    @SuppressWarnings("unchecked")
		    ArrayList<BeneficiaryObject> v = (ArrayList)DBUtil.fetch(beneficiaryObj);
		    if ( v == null || v.size() == 0 )
			    return null;
		    else {
			    beneficiaryObjectArr[i] = (BeneficiaryObject)beneficiaryObj.clone();
			    Util.putInCache(BENEFICIARY, beneficiaryObjectArr);
		    }
	    }
	    if ( beneficiaryObjectArr[i].getBeneficiaryId() == beneficiary_id ) {
		    DebugHandler.debug("Returning " + beneficiaryObjectArr[i]);
		    return (BeneficiaryObject)beneficiaryObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>BeneficiaryObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>BeneficiaryObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public BeneficiaryObject[] getAllBeneficiarys() throws AppException{
		BeneficiaryObject beneficiaryObject = new BeneficiaryObject();
		BeneficiaryObject[] beneficiaryObjectArr = (BeneficiaryObject[])Util.getAppCache().get(BENEFICIARY);
		if ( beneficiaryObjectArr == null ) {
		    DebugHandler.info("Getting beneficiary from database");
		    @SuppressWarnings("unchecked")
		    ArrayList<BeneficiaryObject> v = (ArrayList)DBUtil.list(beneficiaryObject);
		    DebugHandler.finest(":v: " +  v);
		    if ( v == null )
			    return null;
		    beneficiaryObjectArr = new BeneficiaryObject[v.size()];
		    for ( int idx = 0; idx < v.size(); idx++ ) {
			    beneficiaryObjectArr[idx] = v.get(idx);
		    }
		    Util.putInCache(BENEFICIARY, beneficiaryObjectArr);
		}
		return beneficiaryObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>BeneficiaryObject</code> to the underlying datasource.
     *
     * @param beneficiaryObject     BeneficiaryObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addBeneficiary(BeneficiaryObject beneficiaryObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Beneficiary_seq");
			beneficiaryObject.setBeneficiaryId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(beneficiaryObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			beneficiaryObject.setBeneficiaryId(i.intValue());
			DebugHandler.fine(beneficiaryObject);
		}
		BeneficiaryObject buf = new BeneficiaryObject();
		buf.setBeneficiaryId(beneficiaryObject.getBeneficiaryId());
		@SuppressWarnings("unchecked")
		ArrayList<BeneficiaryObject> v = (ArrayList)DBUtil.list(beneficiaryObject, buf);
		beneficiaryObject = v.get(0);
		BeneficiaryObject[] beneficiaryObjectArr = getAllBeneficiarys();
		boolean foundSpace = false;

		for ( int idx = 0; idx < beneficiaryObjectArr.length; idx++ ) {
			if ( beneficiaryObjectArr[idx] == null ) {
				beneficiaryObjectArr[idx] = (BeneficiaryObject)beneficiaryObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			BeneficiaryObject[] newbeneficiaryObjectArr = new BeneficiaryObject[beneficiaryObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < beneficiaryObjectArr.length; idx++ ) {
				newbeneficiaryObjectArr[idx] = (BeneficiaryObject)beneficiaryObjectArr[idx].clone();
			}
			newbeneficiaryObjectArr[idx] = (BeneficiaryObject)beneficiaryObject.clone();
			Util.putInCache(BENEFICIARY, newbeneficiaryObjectArr);
		} else {
			Util.putInCache(BENEFICIARY, beneficiaryObjectArr);
		}
		return i;
	}
	
    
    /**
     *
     * Implementation to update the <code>BeneficiaryObject</code> in the underlying datasource.
     *
     * @param beneficiaryObject     BeneficiaryObject
     *
     * @throws AppException if the operation fails
     *
     */
    
	public Integer updateBeneficiary(BeneficiaryObject beneficiaryObject) throws AppException{
		BeneficiaryObject newBeneficiaryObject = getBeneficiary(beneficiaryObject.getBeneficiaryId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(beneficiaryObject);
		DebugHandler.fine("i: " +  i);
		BeneficiaryObject[] beneficiaryObjectArr = getAllBeneficiarys();
		if ( beneficiaryObjectArr == null )
			return null;
		for ( int idx = 0; idx < beneficiaryObjectArr.length; idx++ ) {
			if ( beneficiaryObjectArr[idx] != null ) {
				if ( beneficiaryObjectArr[idx].getBeneficiaryId() == beneficiaryObject.getBeneficiaryId() ) {
					DebugHandler.debug("Found Beneficiary " + beneficiaryObject.getBeneficiaryId());
					beneficiaryObjectArr[idx] = (BeneficiaryObject)beneficiaryObject.clone();
					Util.putInCache(BENEFICIARY, beneficiaryObjectArr);
				}
			}
		}
		return i;
	}
    
    
    /**
     *
     * Implementation to delete the <code>BeneficiaryObject</code> in the underlying datasource.
     *
     * @param beneficiaryObject     BeneficiaryObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteBeneficiary(BeneficiaryObject beneficiaryObject) throws AppException{
	BeneficiaryObject newBeneficiaryObject = getBeneficiary(beneficiaryObject.getBeneficiaryId()); // This call will make sure cache/db are in sync
	BeneficiaryObject[] beneficiaryObjectArr = getAllBeneficiarys();
	Integer i = new Integer(0);
	if ( beneficiaryObject != null ) {
		i = (Integer)DBUtil.delete(beneficiaryObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( beneficiaryObjectArr != null ) {
			for (int idx = 0; idx < beneficiaryObjectArr.length; idx++ ) {
				if ( beneficiaryObjectArr[idx] != null && beneficiaryObjectArr[idx].getBeneficiaryId() == beneficiaryObject.getBeneficiaryId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (beneficiaryObjectArr.length - 1) )
						beneficiaryObjectArr[idx] = beneficiaryObjectArr[idx + 1]; // Move the array
					else
						beneficiaryObjectArr[idx] = null;
				}
				if ( beneficiaryObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(BENEFICIARY, beneficiaryObjectArr);
		}
		return i;
	}
}
