/*
 * @(#)PersistentObject.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.db;

import java.sql.ResultSet;

import core.db.DBException;
import core.db.ObjectRelationalMap;
import core.db.Persistent;
import core.db.SQLStatement;
import core.db.TransactionContext;

/**
 * An abstract class to serve as the super-class for all the persistent
 * business object classes. It contains the transaction context and the
 * SQL statement and provides the basic implementation for the Persistent
 * interface.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public abstract class PersistentObject implements Persistent,
                                                    ObjectRelationalMap {

    /**
     * The transaction context.
     */

    protected TransactionContext transactionContext = null;

    /**
     * The SQL statement.
     */

    protected SQLStatement sqlStatement = null;

    /**
     * The no-argument constructor.
     */

    public PersistentObject() {
    }

    /**
     * Sets the SQL statement
     *
     * @param sqlStatement        the SQL statement
     */

    protected void setSQLStatement(SQLStatement sqlStatement) {
        this.sqlStatement = sqlStatement;
    }

    /**
     * Returns the SQL statement
     *
     * @return    the SQL statement
     */

    protected SQLStatement getSQLStatement() {
        return sqlStatement;
    }

    /**
     * Sets the transaction context.
     *
     * @param transContext        the transaction context
     */
    
    public void setTransactionContext(TransactionContext transContext) {
        transactionContext = transContext;
    }

    /**
     * Returns the transaction context.
     *
     * @return    the transaction context
     */
    
    protected TransactionContext getTransactionContext() {
        return transactionContext;
    }

    /**
     * Fetches business object from the database.
     *
     * @return        business object
     * @exception DBException
     */

    public Object fetch() throws DBException {
        return sqlStatement.query(transactionContext, this);
    }

    /**
     * Fetches collection of business objects from the database.
     *
     * @return        collection of business objects
     * @exception DBException
     */

    public Object list() throws DBException {
        return sqlStatement.list(transactionContext, this);
    }

    /**
     * Inserts into the database.
     *
     * @return        the update count
     * @exception DBException
     */

    public Object insert() throws DBException {
        return sqlStatement.update(transactionContext);
    }

    /**
     * Deletes from the database.
     *
     * @return        the update count
     * @exception DBException
     */

    public Object delete() throws DBException {
        return sqlStatement.update(transactionContext);
    }

    /**
     * Updates the database.
     *
     * @return        the update count
     * @exception DBException
     */

    public Object update() throws DBException {
        return sqlStatement.update(transactionContext);
    }

    /**
     * Queries the database based on the specified object attributes.
     *
     * @return        collection of business objects
     * @exception DBException
     */

    public Object query() throws DBException {
        return sqlStatement.list(transactionContext, this);
    }

    /**
     * Fetches business object from the database.
     *
     * @param args    additional arguments/parameters
     * @return        business object
     * @exception DBException
     */

    public Object fetch(Object args) throws DBException {
        return fetch();
    }

    /**
     * Fetches collection of business objects from the database.
     *
     * @param args    additional arguments/parameters
     * @return        collection of business objects
     * @exception DBException
     */

    public Object list(Object args) throws DBException {
        return list();
    }

    /**
     * Inserts into the database.
     *
     * @param args    additional arguments/parameters
     * @return        the update count
     * @exception DBException
     */

    public Object insert(Object args) throws DBException {
        return insert();
    }

    /**
     * Deletes from the database.
     *
     * @param args    additional arguments/parameters
     * @return        the update count
     * @exception DBException
     */

    public Object delete(Object args) throws DBException {
        return delete();
    }

    /**
     * Updates the database.
     *
     * @param args    additional arguments/parameters
     * @return        the update count
     * @exception DBException
     */

    public Object update(Object args) throws DBException {
        return update();
    }

    /**
     * Queries the database based on the specified object attributes.
     *
     * @param args    additional arguments/parameters
     * @return        collection of business objects
     * @exception DBException
     */

    public Object query(Object args) throws DBException {
        return query();
    }
    
    /**
     * Returns a business object created from the specified result set for a
     * database query. Provides the mapping to convert the relational result
     * set to a business object.
     * Default implementation; returns the collection of business objects
     * returned by ObjectRelationalMap.getResultObjects(ResultSet rs). 
     *
     * @param rs        the result set
     * @return          the business object
     */

    public Object getResultObject(ResultSet rs) {

        return getResultObjects(rs);
    }
}
