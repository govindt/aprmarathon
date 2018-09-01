/*
 * PersistentSite.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.appdb;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import core.db.PersistentObject;
import core.db.SQLParam;
import core.db.SQLStatement;
import core.db.PreparedSQLStatement;
import core.db.CallableSQLStatement;
import core.db.DBException;
import core.util.DebugHandler;
import core.util.Constants;
import app.util.AppConstants;
import app.busobj.SiteObject;

/**
 * The persistent implementation of the SiteObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentSite extends PersistentObject {
    private SiteObject siteObject;
    
    
    /**
     * Constructs a Persistent Object for the SiteObject
     *
     * @param siteObject    the SiteObject 
     */
    
    public PersistentSite (SiteObject siteObject) {
        this.siteObject = siteObject;
    }
    
    
    /**
     * Returns the ArrayList of SiteObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of SiteObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT site_id, site_name, site_url from Site";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<SiteObject> result = (ArrayList<SiteObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of SiteObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of SiteObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list(Object args) throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT site_id, site_name, site_url from Site";
        int index = 1;
        SiteObject passedSiteObject = (SiteObject)args;
        boolean whereSpecified = false;

        if ( passedSiteObject.getSiteId() != 0 ) {
	    statement += " where site_id = ?";
	    whereSpecified = true;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedSiteObject.getSiteId()), Types.INTEGER));
	}
        if ( ! passedSiteObject.getSiteName().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where site_name = ?";
		whereSpecified = true;
	    } else
		statement += " and site_name = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedSiteObject.getSiteName(), Types.VARCHAR));
	}
        if ( ! passedSiteObject.getSiteUrl().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where site_url = ?";
		whereSpecified = true;
	    } else
		statement += " and site_url = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedSiteObject.getSiteUrl(), Types.VARCHAR));
	}
        sql.setStatement(statement);
        
        DebugHandler.debug(statement);
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<SiteObject> result = (ArrayList<SiteObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of one SiteObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     ArrayList of one SiteObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT site_id, site_name, site_url from Site where site_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(siteObject.getSiteId()), Types.INTEGER));
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<SiteObject> result = (ArrayList<SiteObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the siteObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object insert() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement;
        int index = 1;

        if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
            statement = "INSERT INTO Site (site_id, site_name, site_url) VALUES(?, ?, ?) ";
            sql.setStatement(statement);
            sql.setInParams(new SQLParam(index++, new Integer(siteObject.getSiteId()), Types.INTEGER));
        } else {
            statement = "INSERT INTO Site (site_name, site_url) VALUES(?, ?) ";
            sql.setStatement(statement);
        }
        sql.setInParams(new SQLParam(index++,  siteObject.getSiteName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  siteObject.getSiteUrl(), Types.VARCHAR));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the siteObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Site WHERE site_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(siteObject.getSiteId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the siteObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Site SET site_id = ?, site_name = ?, site_url = ? where site_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(siteObject.getSiteId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  siteObject.getSiteName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  siteObject.getSiteUrl(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(siteObject.getSiteId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a ArrayList of SiteObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a ArrayList of SiteObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        ArrayList<SiteObject> result = new ArrayList<SiteObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                SiteObject f = new SiteObject();
                f.setSiteId(rs.getInt(index++));
                f.setSiteName(rs.getString(index++));
                f.setSiteUrl(rs.getString(index++));
                result.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * Returns a SiteObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a SiteObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
        @SuppressWarnings("unchecked")
            ArrayList<SiteObject> result = (ArrayList<SiteObject>) getResultObjects(rs);
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
