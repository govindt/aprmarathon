/*
 * @(#)Persistent.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.db;

import java.util.Vector;

import core.db.DBException;
import core.db.TransactionContext;

/**
 * The interface for the business objects which have a persistent state.
 * A typical persistent busniness object would contain the corresponding
 * business object reference and would implement this interface.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public interface Persistent {

    /**
     * Sets the transaction context.
     *
     * @param transContext        the transaction context
     */

    public void setTransactionContext(TransactionContext transContext);

    /**
     * Fetches business object from the database.
     *
     * @return        business object
     * @exception DBException
     */

    public Object fetch() throws DBException;

    /**
     * Fetches collection of business objects from the database.
     *
     * @return        collection of business objects
     * @exception DBException
     */

    public Object list() throws DBException;

    /**
     * Inserts into the database.
     *
     * @return        the update count
     * @exception DBException
     */

    public Object insert() throws DBException;

    /**
     * Deletes from the database.
     *
     * @return        the update count
     * @exception DBException
     */

    public Object delete() throws DBException;

    /**
     * Updates the database.
     *
     * @return        the update count
     * @exception DBException
     */

    public Object update() throws DBException;

    /**
     * Queries the database based on the specified object attributes.
     *
     * @return        collection of business objects
     * @exception DBException
     */

    public Object query() throws DBException;

    /**
     * Fetches business object from the database.
     *
     * @param args    additional arguments/parameters
     * @return        business object
     * @exception DBException
     */

    public Object fetch(Object args) throws DBException;

    /**
     * Fetches collection of business objects from the database.
     *
     * @param args    additional arguments/parameters
     * @return        collection of business objects
     * @exception DBException
     */

    public Object list(Object args) throws DBException;

    /**
     * Inserts into the database.
     *
     * @param args    additional arguments/parameters
     * @return        the update count
     * @exception DBException
     */

    public Object insert(Object args) throws DBException;

    /**
     * Deletes from the database.
     *
     * @param args    additional arguments/parameters
     * @return        the update count
     * @exception DBException
     */

    public Object delete(Object args) throws DBException;

    /**
     * Updates the database.
     *
     * @param args    additional arguments/parameters
     * @return        the update count
     * @exception DBException
     */

    public Object update(Object args) throws DBException;

    /**
     * Queries the database based on the specified object attributes.
     *
     * @param args    additional arguments/parameters
     * @return        collection of business objects
     * @exception DBException
     */

    public Object query(Object args) throws DBException;
}
