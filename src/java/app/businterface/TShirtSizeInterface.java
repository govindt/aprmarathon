/*
 * TShirtSizeInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.TShirtSizeObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface TShirtSizeInterface {
    
    /**
     *
     * Interface that returns the TShirtSizeObject given a TShirtSizeObject filled with values that will be used for query from the underlying datasource.
     *
     * @param tshirtsize_obj	TShirtSizeObject
     *
     * @return      Returns the ArrayList of TShirtSizeObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<TShirtSizeObject> getTShirtSizes(TShirtSizeObject tshirtsize_obj) throws AppException;
    
    /**
     *
     * Interface that returns the TShirtSizeObject given t_shirt_size_id from the underlying datasource.
     *
     * @param t_shirt_size_id     int
     *
     * @return      Returns the TShirtSizeObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public TShirtSizeObject getTShirtSize(int t_shirt_size_id) throws AppException;
    
    /**
     *
     * Interface that returns all the <code>TShirtSizeObject</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>TShirtSizeObject</code>
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public TShirtSizeObject[] getAllTShirtSizes() throws AppException;
    
    /**
     *
     * Interface to add the <code>TShirtSizeObject</code> to the underlying datasource.
     *
     * @param tShirtSizeObject     TShirtSizeObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer addTShirtSize(TShirtSizeObject tShirtSizeObject) throws AppException;
    
    /**
     *
     * Interface to update the <code>TShirtSizeObject</code> in the underlying datasource.
     *
     * @param tShirtSizeObject     TShirtSizeObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer updateTShirtSize(TShirtSizeObject tShirtSizeObject) throws AppException;
    
    /**
     *
     * Interface to delete the <code>TShirtSizeObject</code> in the underlying datasource.
     *
     * @param tShirtSizeObject     TShirtSizeObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer deleteTShirtSize(TShirtSizeObject tShirtSizeObject) throws AppException;
}
