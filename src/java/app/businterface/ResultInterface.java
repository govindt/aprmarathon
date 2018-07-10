/*
 * ResultInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.ResultObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface ResultInterface {
    
    /**
     *
     * Interface that returns the ResultObject given a ResultObject filled with values that will be used for query from the underlying datasource.
     *
     * @param result_obj	ResultObject
     *
     * @return      Returns the ArrayList of ResultObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<ResultObject> getResults(ResultObject result_obj) throws AppException;
    
    /**
     *
     * Interface that returns the ResultObject given result_id from the underlying datasource.
     *
     * @param result_id     int
     *
     * @return      Returns the ResultObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ResultObject getResult(int result_id) throws AppException;
    
    /**
     *
     * Interface that returns all the <code>ResultObject</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>ResultObject</code>
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ResultObject[] getAllResults() throws AppException;
    
    /**
     *
     * Interface to add the <code>ResultObject</code> to the underlying datasource.
     *
     * @param resultObject     ResultObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer addResult(ResultObject resultObject) throws AppException;
    
    /**
     *
     * Interface to update the <code>ResultObject</code> in the underlying datasource.
     *
     * @param resultObject     ResultObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer updateResult(ResultObject resultObject) throws AppException;
    
    /**
     *
     * Interface to delete the <code>ResultObject</code> in the underlying datasource.
     *
     * @param resultObject     ResultObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer deleteResult(ResultObject resultObject) throws AppException;
}
