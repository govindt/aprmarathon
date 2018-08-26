/*
 * ResultImpl.java
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
import app.busobj.ResultObject;
import app.businterface.ResultInterface;
import app.util.AppConstants;

/**
 * The implementation of the ResultInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class ResultImpl implements ResultInterface  {
    private String RESULT = "ResultInterface.getAllResult";
    
    /**
     *
     * Implementation that returns the ResultObject given a ResultObject filled with values that will be used for query from the underlying datasource.
     *
     * @param result_obj	ResultObject
     *
     * @return      Returns the ArrayList of ResultObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<ResultObject> getResults(ResultObject result_obj) throws AppException{
	ResultObject[] resultObjectArr = getAllResults();
	ArrayList<ResultObject> v = new ArrayList<ResultObject>();
	if ( resultObjectArr == null )
		return null;
	for ( int i = 0; i < resultObjectArr.length; i++ ) {
		if ( resultObjectArr[i] != null ) {
			if ( result_obj.getResultId() == Constants.GET_ALL ) {
				v.add((ResultObject)resultObjectArr[i].clone());
			} else {
				if ( (result_obj.getResultId() != 0 && result_obj.getResultId() == resultObjectArr[i].getResultId())
 || (result_obj.getResultEvent() != 0 && result_obj.getResultEvent() == resultObjectArr[i].getResultEvent())
 || (result_obj.getResultEventType() != 0 && result_obj.getResultEventType() == resultObjectArr[i].getResultEventType())
 || (result_obj.getResultMedal() != 0 && result_obj.getResultMedal() == resultObjectArr[i].getResultMedal())
 || (result_obj.getResultWinner() != 0 && result_obj.getResultWinner() == resultObjectArr[i].getResultWinner())
 || (result_obj.getResultWinnerRegistrant() != 0 && result_obj.getResultWinnerRegistrant() == resultObjectArr[i].getResultWinnerRegistrant())
 || (result_obj.getResultScore() != null && result_obj.getResultScore().equals(resultObjectArr[i].getResultScore()))
 || (result_obj.getResultTiming() != null && result_obj.getResultTiming().equals(resultObjectArr[i].getResultTiming()))
) {
					v.add((ResultObject)resultObjectArr[i].clone());
				}
			}
		}
	}
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the ResultObject from the underlying datasource.
     * given result_id.
     *
     * @param result_id     int
     *
     * @return      Returns the ResultObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public ResultObject getResult(int result_id) throws AppException{
	ResultObject[] resultObjectArr = getAllResults();
	if ( resultObjectArr == null )
	    return null;
	for ( int i = 0; i < resultObjectArr.length; i++ ) {
	    if ( resultObjectArr[i] == null ) { // Try database and add to cache if found.
		    ResultObject resultObj = new ResultObject();
		    resultObj.setResultId(result_id);
		    @SuppressWarnings("unchecked")
		    ArrayList<ResultObject> v = (ArrayList)DBUtil.fetch(resultObj);
		    if ( v == null || v.size() == 0 )
			    return null;
		    else {
			    resultObjectArr[i] = (ResultObject)resultObj.clone();
			    Util.putInCache(RESULT, resultObjectArr);
		    }
	    }
	    if ( resultObjectArr[i].getResultId() == result_id ) {
		    DebugHandler.debug("Returning " + resultObjectArr[i]);
		    return (ResultObject)resultObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>ResultObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>ResultObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public ResultObject[] getAllResults() throws AppException{
		ResultObject resultObject = new ResultObject();
		ResultObject[] resultObjectArr = (ResultObject[])Util.getAppCache().get(RESULT);
		if ( resultObjectArr == null ) {
		    DebugHandler.info("Getting result from database");
		    @SuppressWarnings("unchecked")
		    ArrayList<ResultObject> v = (ArrayList)DBUtil.list(resultObject);
		    DebugHandler.finest(":v: " +  v);
		    if ( v == null )
			    return null;
		    resultObjectArr = new ResultObject[v.size()];
		    for ( int idx = 0; idx < v.size(); idx++ ) {
			    resultObjectArr[idx] = v.get(idx);
		    }
		    Util.putInCache(RESULT, resultObjectArr);
		}
		return resultObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>ResultObject</code> to the underlying datasource.
     *
     * @param resultObject     ResultObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addResult(ResultObject resultObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Result_seq");
			resultObject.setResultId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(resultObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			resultObject.setResultId(i.intValue());
			DebugHandler.fine(resultObject);
		}
		ResultObject buf = new ResultObject();
		buf.setResultId(resultObject.getResultId());
		@SuppressWarnings("unchecked")
		ArrayList<ResultObject> v = (ArrayList)DBUtil.list(resultObject, buf);
		resultObject = v.get(0);
		ResultObject[] resultObjectArr = getAllResults();
		boolean foundSpace = false;

		for ( int idx = 0; idx < resultObjectArr.length; idx++ ) {
			if ( resultObjectArr[idx] == null ) {
				resultObjectArr[idx] = (ResultObject)resultObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			ResultObject[] newresultObjectArr = new ResultObject[resultObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < resultObjectArr.length; idx++ ) {
				newresultObjectArr[idx] = (ResultObject)resultObjectArr[idx].clone();
			}
			newresultObjectArr[idx] = (ResultObject)resultObject.clone();
			Util.putInCache(RESULT, newresultObjectArr);
		} else {
			Util.putInCache(RESULT, resultObjectArr);
		}
		return i;
	}
	
    
    /**
     *
     * Implementation to update the <code>ResultObject</code> in the underlying datasource.
     *
     * @param resultObject     ResultObject
     *
     * @throws AppException if the operation fails
     *
     */
    
	public Integer updateResult(ResultObject resultObject) throws AppException{
		ResultObject newResultObject = getResult(resultObject.getResultId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(resultObject);
		DebugHandler.fine("i: " +  i);
		ResultObject[] resultObjectArr = getAllResults();
		if ( resultObjectArr == null )
			return null;
		for ( int idx = 0; idx < resultObjectArr.length; idx++ ) {
			if ( resultObjectArr[idx] != null ) {
				if ( resultObjectArr[idx].getResultId() == resultObject.getResultId() ) {
					DebugHandler.debug("Found Result " + resultObject.getResultId());
					resultObjectArr[idx] = (ResultObject)resultObject.clone();
					Util.putInCache(RESULT, resultObjectArr);
				}
			}
		}
		return i;
	}
    
    
    /**
     *
     * Implementation to delete the <code>ResultObject</code> in the underlying datasource.
     *
     * @param resultObject     ResultObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteResult(ResultObject resultObject) throws AppException{
	ResultObject newResultObject = getResult(resultObject.getResultId()); // This call will make sure cache/db are in sync
	ResultObject[] resultObjectArr = getAllResults();
	Integer i = new Integer(0);
	if ( resultObject != null ) {
		i = (Integer)DBUtil.delete(resultObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( resultObjectArr != null ) {
			for (int idx = 0; idx < resultObjectArr.length; idx++ ) {
				if ( resultObjectArr[idx] != null && resultObjectArr[idx].getResultId() == resultObject.getResultId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (resultObjectArr.length - 1) )
						resultObjectArr[idx] = resultObjectArr[idx + 1]; // Move the array
					else
						resultObjectArr[idx] = null;
				}
				if ( resultObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(RESULT, resultObjectArr);
		}
		return i;
	}
}
