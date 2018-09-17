/*
 * BeneficiaryInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.BeneficiaryObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface BeneficiaryInterface {
	
    /**
	 *
	 * Interface that returns the BeneficiaryObject given a BeneficiaryObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param beneficiary_obj	BeneficiaryObject
	 *
	 * @return      Returns the ArrayList of BeneficiaryObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public ArrayList<BeneficiaryObject> getBeneficiarys(BeneficiaryObject beneficiary_obj) throws AppException;
	
    /**
	 *
	 * Interface that returns the BeneficiaryObject given beneficiary_id from the underlying datasource.
	 *
	 * @param beneficiary_id     int
	 *
	 * @return      Returns the BeneficiaryObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public BeneficiaryObject getBeneficiary(int beneficiary_id) throws AppException;
	
    /**
	 *
	 * Interface that returns all the <code>BeneficiaryObject</code> from the underlying datasource.
	 *
	 * @return      Returns an Array of <code>BeneficiaryObject</code>
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public BeneficiaryObject[] getAllBeneficiarys() throws AppException;
	
    /**
	 *
	 * Interface to add the <code>BeneficiaryObject</code> to the underlying datasource.
	 *
	 * @param beneficiaryObject     BeneficiaryObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer addBeneficiary(BeneficiaryObject beneficiaryObject) throws AppException;
	
    /**
	 *
	 * Interface to update the <code>BeneficiaryObject</code> in the underlying datasource.
	 *
	 * @param beneficiaryObject     BeneficiaryObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer updateBeneficiary(BeneficiaryObject beneficiaryObject) throws AppException;
	
    /**
	 *
	 * Interface to delete the <code>BeneficiaryObject</code> in the underlying datasource.
	 *
	 * @param beneficiaryObject     BeneficiaryObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
    
	public Integer deleteBeneficiary(BeneficiaryObject beneficiaryObject) throws AppException;
}
