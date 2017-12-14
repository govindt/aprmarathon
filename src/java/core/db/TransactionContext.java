/*
 * @(#)TransactionContext.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.db;

import java.sql.Connection;
import java.sql.SQLException;

import core.db.DBException;

/**
 * Models a transaction context and contains reference to the database server
 * connection.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class TransactionContext {

    /*
     * The transaction context name.
     */

    private String name;

    /*
     * The database server connection.
     */

    private Connection connection;

    /**
     * Constructs this object with the specified name and connection.
     *
     * @param name          identifier
     * @param connection    connection to the database server
     */

    public TransactionContext(String name, Connection connection) {

        this.name = name;
        this.connection = connection;

        try {
            this.connection.setAutoCommit(false);
        } catch (SQLException sqle) {
            this.connection = null;
        }
    }

    /**
     * Constructs this object with the specified connection.
     *
     * @param connection    connection to the database server
     */

    public TransactionContext(Connection connection) {
        this(null, connection);
    }

    /**
     * Returns the database server connection.
     *
     * @return        connection to the database server
     */

    public Connection getConnection() {
        return connection;
    }

    /**
     * Restores the default auto-commit policy (to true) and removes the
     * reference to the database connection for this transaction.
     */

    public void close() {
        try {
            this.connection.setAutoCommit(true);
        } catch (SQLException sqle) {
        }

        connection = null;
    }

    /**
     * Commits the transaction.
	 *
	 * @exception DBException
     */

    public void commit() throws DBException {

        if (connection != null) {
            try {
                connection.commit();
            } catch (SQLException se) {
                throw new DBException(se);
            }
        }
    }

    /**
     * Rolls back the transaction.
	 *
	 * @exception DBException
     */

    public void rollback() throws DBException {

        if (connection != null) {
            try {
                connection.rollback();
            } catch (SQLException se) {
                throw new DBException(se);
            }
        }
    }

    /**
     * Returns the identifier.
     */

    public String toString() {
        return name;
    }
}
