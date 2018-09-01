/*
 * SiteImpl.java
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
import app.busobj.SiteObject;
import app.businterface.SiteInterface;
import app.util.AppConstants;

/**
 * The implementation of the SiteInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class SiteImpl implements SiteInterface  {
    private String SITE = "SiteInterface.getAllSite";
    
    /**
     *
     * Implementation that returns the SiteObject given a SiteObject filled with values that will be used for query from the underlying datasource.
     *
     * @param site_obj	SiteObject
     *
     * @return      Returns the ArrayList of SiteObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<SiteObject> getSites(SiteObject site_obj) throws AppException{
	SiteObject[] siteObjectArr = getAllSites();
	ArrayList<SiteObject> v = new ArrayList<SiteObject>();
	if ( siteObjectArr == null )
		return null;
	for ( int i = 0; i < siteObjectArr.length; i++ ) {
		if ( siteObjectArr[i] != null ) {
			if ( site_obj.getSiteId() == Constants.GET_ALL ) {
				v.add((SiteObject)siteObjectArr[i].clone());
			} else {
				if ( (site_obj.getSiteId() != 0 && site_obj.getSiteId() == siteObjectArr[i].getSiteId())
 || (site_obj.getSiteName() != null && site_obj.getSiteName().equals(siteObjectArr[i].getSiteName()))
 || (site_obj.getSiteUrl() != null && site_obj.getSiteUrl().equals(siteObjectArr[i].getSiteUrl()))
) {
					v.add((SiteObject)siteObjectArr[i].clone());
				}
			}
		}
	}
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the SiteObject from the underlying datasource.
     * given site_id.
     *
     * @param site_id     int
     *
     * @return      Returns the SiteObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public SiteObject getSite(int site_id) throws AppException{
	SiteObject[] siteObjectArr = getAllSites();
	if ( siteObjectArr == null )
	    return null;
	for ( int i = 0; i < siteObjectArr.length; i++ ) {
	    if ( siteObjectArr[i] == null ) { // Try database and add to cache if found.
		    SiteObject siteObj = new SiteObject();
		    siteObj.setSiteId(site_id);
		    @SuppressWarnings("unchecked")
		    ArrayList<SiteObject> v = (ArrayList)DBUtil.fetch(siteObj);
		    if ( v == null || v.size() == 0 )
			    return null;
		    else {
			    siteObjectArr[i] = (SiteObject)siteObj.clone();
			    Util.putInCache(SITE, siteObjectArr);
		    }
	    }
	    if ( siteObjectArr[i].getSiteId() == site_id ) {
		    DebugHandler.debug("Returning " + siteObjectArr[i]);
		    return (SiteObject)siteObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>SiteObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>SiteObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public SiteObject[] getAllSites() throws AppException{
		SiteObject siteObject = new SiteObject();
		SiteObject[] siteObjectArr = (SiteObject[])Util.getAppCache().get(SITE);
		if ( siteObjectArr == null ) {
		    DebugHandler.info("Getting site from database");
		    @SuppressWarnings("unchecked")
		    ArrayList<SiteObject> v = (ArrayList)DBUtil.list(siteObject);
		    DebugHandler.finest(":v: " +  v);
		    if ( v == null )
			    return null;
		    siteObjectArr = new SiteObject[v.size()];
		    for ( int idx = 0; idx < v.size(); idx++ ) {
			    siteObjectArr[idx] = v.get(idx);
		    }
		    Util.putInCache(SITE, siteObjectArr);
		}
		return siteObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>SiteObject</code> to the underlying datasource.
     *
     * @param siteObject     SiteObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addSite(SiteObject siteObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Site_seq");
			siteObject.setSiteId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(siteObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			siteObject.setSiteId(i.intValue());
			DebugHandler.fine(siteObject);
		}
		SiteObject buf = new SiteObject();
		buf.setSiteId(siteObject.getSiteId());
		@SuppressWarnings("unchecked")
		ArrayList<SiteObject> v = (ArrayList)DBUtil.list(siteObject, buf);
		siteObject = v.get(0);
		SiteObject[] siteObjectArr = getAllSites();
		boolean foundSpace = false;

		for ( int idx = 0; idx < siteObjectArr.length; idx++ ) {
			if ( siteObjectArr[idx] == null ) {
				siteObjectArr[idx] = (SiteObject)siteObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			SiteObject[] newsiteObjectArr = new SiteObject[siteObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < siteObjectArr.length; idx++ ) {
				newsiteObjectArr[idx] = (SiteObject)siteObjectArr[idx].clone();
			}
			newsiteObjectArr[idx] = (SiteObject)siteObject.clone();
			Util.putInCache(SITE, newsiteObjectArr);
		} else {
			Util.putInCache(SITE, siteObjectArr);
		}
		return i;
	}
	
    
    /**
     *
     * Implementation to update the <code>SiteObject</code> in the underlying datasource.
     *
     * @param siteObject     SiteObject
     *
     * @throws AppException if the operation fails
     *
     */
    
	public Integer updateSite(SiteObject siteObject) throws AppException{
		SiteObject newSiteObject = getSite(siteObject.getSiteId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(siteObject);
		DebugHandler.fine("i: " +  i);
		SiteObject[] siteObjectArr = getAllSites();
		if ( siteObjectArr == null )
			return null;
		for ( int idx = 0; idx < siteObjectArr.length; idx++ ) {
			if ( siteObjectArr[idx] != null ) {
				if ( siteObjectArr[idx].getSiteId() == siteObject.getSiteId() ) {
					DebugHandler.debug("Found Site " + siteObject.getSiteId());
					siteObjectArr[idx] = (SiteObject)siteObject.clone();
					Util.putInCache(SITE, siteObjectArr);
				}
			}
		}
		return i;
	}
    
    
    /**
     *
     * Implementation to delete the <code>SiteObject</code> in the underlying datasource.
     *
     * @param siteObject     SiteObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteSite(SiteObject siteObject) throws AppException{
	SiteObject newSiteObject = getSite(siteObject.getSiteId()); // This call will make sure cache/db are in sync
	SiteObject[] siteObjectArr = getAllSites();
	Integer i = new Integer(0);
	if ( siteObject != null ) {
		i = (Integer)DBUtil.delete(siteObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( siteObjectArr != null ) {
			for (int idx = 0; idx < siteObjectArr.length; idx++ ) {
				if ( siteObjectArr[idx] != null && siteObjectArr[idx].getSiteId() == siteObject.getSiteId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (siteObjectArr.length - 1) )
						siteObjectArr[idx] = siteObjectArr[idx + 1]; // Move the array
					else
						siteObjectArr[idx] = null;
				}
				if ( siteObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(SITE, siteObjectArr);
		}
		return i;
	}
}
