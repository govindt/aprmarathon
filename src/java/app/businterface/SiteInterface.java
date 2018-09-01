/*
 * SiteInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.SiteObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface SiteInterface {
    
    /**
     *
     * Interface that returns the SiteObject given a SiteObject filled with values that will be used for query from the underlying datasource.
     *
     * @param site_obj	SiteObject
     *
     * @return      Returns the ArrayList of SiteObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<SiteObject> getSites(SiteObject site_obj) throws AppException;
    
    /**
     *
     * Interface that returns the SiteObject given site_id from the underlying datasource.
     *
     * @param site_id     int
     *
     * @return      Returns the SiteObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public SiteObject getSite(int site_id) throws AppException;
    
    /**
     *
     * Interface that returns all the <code>SiteObject</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>SiteObject</code>
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public SiteObject[] getAllSites() throws AppException;
    
    /**
     *
     * Interface to add the <code>SiteObject</code> to the underlying datasource.
     *
     * @param siteObject     SiteObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer addSite(SiteObject siteObject) throws AppException;
    
    /**
     *
     * Interface to update the <code>SiteObject</code> in the underlying datasource.
     *
     * @param siteObject     SiteObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer updateSite(SiteObject siteObject) throws AppException;
    
    /**
     *
     * Interface to delete the <code>SiteObject</code> in the underlying datasource.
     *
     * @param siteObject     SiteObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer deleteSite(SiteObject siteObject) throws AppException;
}
