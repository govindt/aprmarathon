/*
 * @(#)PersistentId.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */

package core.appdb;

// Import statements

import java.sql.ResultSet;
import java.sql.Types;

import core.db.DBException;
import core.db.PersistentObject;
import core.db.SQLParam;
import core.db.SQLStatement;

import core.busobj.IdObject;

/**
 * The persistent implementation for IdObject.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class PersistentId extends PersistentObject {

    private IdObject id;

    public PersistentId(IdObject id) {

        this.id = id;
    }

    /**
     * Returns the next id from the table that generates ids for a particular
     * entity.
     *
     * @return        the id object
     */

    public Object fetch() throws DBException {

        SQLStatement sql = new SQLStatement();

        String selectExpression = id.getTableName() + ".nextval";
        String statement = "SELECT " + selectExpression + " FROM dual";

        sql.setStatement(statement);
        setSQLStatement(sql);

        return super.fetch();
    }
    
    /**
     * Returns a collection of business objects created from the specified
     * result set for a database query. Provides the mapping to convert the
     * relational result set to a set of business objects.
     * Here, just returns the business object returned by getResultObject().
     *
     * @param rs        the result set
     * @return          the collection of business objects
     */

    public Object getResultObjects(ResultSet rs) {

        return getResultObject(rs);
    }
    
    /**
     * Returns a business object created from the specified result set for a
     * database query. Provides the mapping to convert the relational result
     * set to a business object.
     *
     * @param rs        the result set
     * @return          the business object
     */

    public Object getResultObject(ResultSet rs) {

        try {
            if (rs.next()) {
                id.setId(rs.getLong(1));
                return id;
            }
        } catch (Exception e) {
            return null;
        }

        return null;
    }
}
