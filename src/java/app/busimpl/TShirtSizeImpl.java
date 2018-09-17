/*
 * TShirtSizeImpl.java
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
import app.busobj.TShirtSizeObject;
import app.businterface.TShirtSizeInterface;
import app.util.AppConstants;

/**
 * The implementation of the TShirtSizeInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class TShirtSizeImpl implements TShirtSizeInterface  {
    private String TSHIRTSIZE = "TShirtSizeInterface.getAllTShirtSize";
    
    /**
     *
     * Implementation that returns the TShirtSizeObject given a TShirtSizeObject filled with values that will be used for query from the underlying datasource.
     *
     * @param tshirtsize_obj	TShirtSizeObject
     *
     * @return      Returns the ArrayList of TShirtSizeObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
	public ArrayList<TShirtSizeObject> getTShirtSizes(TShirtSizeObject tshirtsize_obj) throws AppException{
		@SuppressWarnings("unchecked")
		ArrayList<TShirtSizeObject> v = (ArrayList<TShirtSizeObject>)DBUtil.list(tshirtsize_obj,tshirtsize_obj);
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the TShirtSizeObject from the underlying datasource.
     * given t_shirt_size_id.
     *
     * @param t_shirt_size_id     int
     *
     * @return      Returns the TShirtSizeObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public TShirtSizeObject getTShirtSize(int t_shirt_size_id) throws AppException{
	TShirtSizeObject[] tShirtSizeObjectArr = getAllTShirtSizes();
	if ( tShirtSizeObjectArr == null )
	    return null;
	for ( int i = 0; i < tShirtSizeObjectArr.length; i++ ) {
	    if ( tShirtSizeObjectArr[i] == null ) { // Try database and add to cache if found.
		    TShirtSizeObject tshirtsizeObj = new TShirtSizeObject();
		    tshirtsizeObj.setTShirtSizeId(t_shirt_size_id);
		    @SuppressWarnings("unchecked")
		    ArrayList<TShirtSizeObject> v = (ArrayList)DBUtil.fetch(tshirtsizeObj);
		    if ( v == null || v.size() == 0 )
			    return null;
		    else {
			    tShirtSizeObjectArr[i] = (TShirtSizeObject)tshirtsizeObj.clone();
			    Util.putInCache(TSHIRTSIZE, tShirtSizeObjectArr);
		    }
	    }
	    if ( tShirtSizeObjectArr[i].getTShirtSizeId() == t_shirt_size_id ) {
		    DebugHandler.debug("Returning " + tShirtSizeObjectArr[i]);
		    return (TShirtSizeObject)tShirtSizeObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>TShirtSizeObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>TShirtSizeObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public TShirtSizeObject[] getAllTShirtSizes() throws AppException{
		TShirtSizeObject tShirtSizeObject = new TShirtSizeObject();
		TShirtSizeObject[] tShirtSizeObjectArr = (TShirtSizeObject[])Util.getAppCache().get(TSHIRTSIZE);
		if ( tShirtSizeObjectArr == null ) {
		    DebugHandler.info("Getting tshirtsize from database");
		    @SuppressWarnings("unchecked")
		    ArrayList<TShirtSizeObject> v = (ArrayList)DBUtil.list(tShirtSizeObject);
		    DebugHandler.finest(":v: " +  v);
		    if ( v == null )
			    return null;
		    tShirtSizeObjectArr = new TShirtSizeObject[v.size()];
		    for ( int idx = 0; idx < v.size(); idx++ ) {
			    tShirtSizeObjectArr[idx] = v.get(idx);
		    }
		    Util.putInCache(TSHIRTSIZE, tShirtSizeObjectArr);
		}
		return tShirtSizeObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>TShirtSizeObject</code> to the underlying datasource.
     *
     * @param tShirtSizeObject     TShirtSizeObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addTShirtSize(TShirtSizeObject tShirtSizeObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("T_Shirt_Size_seq");
			tShirtSizeObject.setTShirtSizeId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(tShirtSizeObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			tShirtSizeObject.setTShirtSizeId(i.intValue());
			DebugHandler.fine(tShirtSizeObject);
		}
		TShirtSizeObject buf = new TShirtSizeObject();
		buf.setTShirtSizeId(tShirtSizeObject.getTShirtSizeId());
		@SuppressWarnings("unchecked")
		ArrayList<TShirtSizeObject> v = (ArrayList)DBUtil.list(tShirtSizeObject, buf);
		tShirtSizeObject = v.get(0);
		TShirtSizeObject[] tShirtSizeObjectArr = getAllTShirtSizes();
		boolean foundSpace = false;

		for ( int idx = 0; idx < tShirtSizeObjectArr.length; idx++ ) {
			if ( tShirtSizeObjectArr[idx] == null ) {
				tShirtSizeObjectArr[idx] = (TShirtSizeObject)tShirtSizeObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			TShirtSizeObject[] newtShirtSizeObjectArr = new TShirtSizeObject[tShirtSizeObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < tShirtSizeObjectArr.length; idx++ ) {
				newtShirtSizeObjectArr[idx] = (TShirtSizeObject)tShirtSizeObjectArr[idx].clone();
			}
			newtShirtSizeObjectArr[idx] = (TShirtSizeObject)tShirtSizeObject.clone();
			Util.putInCache(TSHIRTSIZE, newtShirtSizeObjectArr);
		} else {
			Util.putInCache(TSHIRTSIZE, tShirtSizeObjectArr);
		}
		return i;
	}
	
    
    /**
     *
     * Implementation to update the <code>TShirtSizeObject</code> in the underlying datasource.
     *
     * @param tShirtSizeObject     TShirtSizeObject
     *
     * @throws AppException if the operation fails
     *
     */
    
	public Integer updateTShirtSize(TShirtSizeObject tShirtSizeObject) throws AppException{
		TShirtSizeObject newTShirtSizeObject = getTShirtSize(tShirtSizeObject.getTShirtSizeId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(tShirtSizeObject);
		DebugHandler.fine("i: " +  i);
		TShirtSizeObject[] tShirtSizeObjectArr = getAllTShirtSizes();
		if ( tShirtSizeObjectArr == null )
			return null;
		for ( int idx = 0; idx < tShirtSizeObjectArr.length; idx++ ) {
			if ( tShirtSizeObjectArr[idx] != null ) {
				if ( tShirtSizeObjectArr[idx].getTShirtSizeId() == tShirtSizeObject.getTShirtSizeId() ) {
					DebugHandler.debug("Found TShirtSize " + tShirtSizeObject.getTShirtSizeId());
					tShirtSizeObjectArr[idx] = (TShirtSizeObject)tShirtSizeObject.clone();
					Util.putInCache(TSHIRTSIZE, tShirtSizeObjectArr);
				}
			}
		}
		return i;
	}
    
    
    /**
     *
     * Implementation to delete the <code>TShirtSizeObject</code> in the underlying datasource.
     *
     * @param tShirtSizeObject     TShirtSizeObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteTShirtSize(TShirtSizeObject tShirtSizeObject) throws AppException{
	TShirtSizeObject newTShirtSizeObject = getTShirtSize(tShirtSizeObject.getTShirtSizeId()); // This call will make sure cache/db are in sync
	TShirtSizeObject[] tShirtSizeObjectArr = getAllTShirtSizes();
	Integer i = new Integer(0);
	if ( tShirtSizeObject != null ) {
		i = (Integer)DBUtil.delete(tShirtSizeObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( tShirtSizeObjectArr != null ) {
			for (int idx = 0; idx < tShirtSizeObjectArr.length; idx++ ) {
				if ( tShirtSizeObjectArr[idx] != null && tShirtSizeObjectArr[idx].getTShirtSizeId() == tShirtSizeObject.getTShirtSizeId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (tShirtSizeObjectArr.length - 1) )
						tShirtSizeObjectArr[idx] = tShirtSizeObjectArr[idx + 1]; // Move the array
					else
						tShirtSizeObjectArr[idx] = null;
				}
				if ( tShirtSizeObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(TSHIRTSIZE, tShirtSizeObjectArr);
		}
		return i;
	}
}
