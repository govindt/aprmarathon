/*
 * @(#)ObjectRelationalMap.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.db;

import java.sql.ResultSet;

/**
 * The interface for the object-relational mapping for the persistent business
 * objects.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public interface ObjectRelationalMap {
    
    /**
     * Returns a business object created from the specified result set for a
     * database query. Provides the mapping to convert the relational result
     * set to a business object.
     *
     * @param rs        the result set
     * @return          the business object
     */

    public Object getResultObject(ResultSet rs);
    
    /**
     * Returns a collection of business objects created from the specified
     * result set for a database query. Provides the mapping to convert the
     * relational result set to a set of business objects.
     *
     * @param rs        the result set
     * @return          the collection of business objects
     */

    public Object getResultObjects(ResultSet rs);
}
