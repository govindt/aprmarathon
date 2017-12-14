/*
 * @(#)DBManager.java	1.3 02/09/30
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.db;

import java.sql.Connection;
import java.sql.DriverManager;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Enumeration;

import core.ConnectionPool.*;
import core.util.DebugHandler;

import core.db.DBException;
import core.db.TransactionContext;

/**
 * Models a singleton Database Manager that handles getting and releasing
 * database connections using the connection pool and transaction management.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class DBManager {

    private final int DB_INIT_CONNECTIONS = 1;
    private final int DB_MAX_CONNECTIONS = 20;
    private final int DB_TIME_OUT = 1000;
    private final int DB_CONNECTION_MAX_USES = 20;
    
    private static DBManager dbManager = null;
    private ConnectionPool connectionPool = null;
    private String dbPropertiesFile = "core.db.db"; // Default Properties File
	  

    /**
     * The no-argument constructor.
     */

    private DBManager() {

    }

    /**
     * Returns the reference to its singleton instance.
     *
     * @return    the singleton database manager instance
     */

    public static synchronized DBManager getInstance() {

        if (dbManager == null) {
            dbManager = new DBManager();
        }
	
        return dbManager;
    }
    
    /** 
     * Sets dbProperties File
     */
    public void setDbProperties(String dbPropertiesFile) {
	this.dbPropertiesFile = dbPropertiesFile;
    }

    /**
     * Returns the transction context that initiates a transaction. 
     *
     * @return    the transaction context
     */

    public TransactionContext beginTransaction() throws DBException {

        Connection connection = getConnection();

        return new TransactionContext(connection);
    }

    /**
     * Ends a transaction for the specified context.
     *
     * @param tc        the transaction context
     * @param commit    flag indicating a commit or a rollback
     * @exception DBException
     */

    protected void endTransaction(TransactionContext tc, boolean commit)
                                                        throws DBException {

        Connection con = tc.getConnection();

        if (commit) {
            tc.commit();
        } else {
            tc.rollback();
        }

        tc.close();
        releaseConnection(con);
    }

    /**
     * Commits a transaction for the specified context.
     *
     * @param tc        the transaction context
     * @exception DBException
     */

    public void commitTransaction(TransactionContext tc) throws DBException { 
        endTransaction(tc, true);
    }

    /**
     * Rolls back a transaction for the specified context.
     *
     * @param tc        the transaction context
     * @exception DBException
     */

    public void rollbackTransaction(TransactionContext tc) throws DBException { 
        endTransaction(tc, false);
    }

    /**
     * Returns a database connection from the connection pool. Creates a
     * conection pool, if its not already instantiated.
     *
     * @return    the database connection
     * @exception DBException
     */
  
    public synchronized Connection getConnection() throws DBException {
	
	if (connectionPool == null) {
	    Properties dbProps = new Properties();
	    
	    try {
		ResourceBundle rb = ResourceBundle.getBundle(dbPropertiesFile);
		for ( Enumeration<String> iterator = rb.getKeys(); iterator.hasMoreElements(); ) {
		    String key = iterator.nextElement();
		    dbProps.put(key, rb.getString(key));
		}
	    } catch (Exception e) {
		throw new DBException("dbPropsLoadError");
	    }
	    
	    String dbDriver = dbProps.getProperty("db.driver");
	    String dbProtocol = dbProps.getProperty("db.protocol");
	    String dbProtocolSep = dbProps.getProperty("db.protocol.separator");
	    if (dbProtocolSep == null )
		dbProtocolSep = "@";
	    String dbServer = dbProps.getProperty("db.server");
	    String dbPort = dbProps.getProperty("db.port");
	    String dbName = dbProps.getProperty("db.name");
	    String dbNameSep = dbProps.getProperty("db.name.separator");
	    if (dbNameSep == null )
		dbNameSep = ":";
	    String dbUser = dbProps.getProperty("db.user");
	    String dbPassword = dbProps.getProperty("db.password");
	    
	    if ((dbDriver == null) ||
		(dbProtocol == null) ||
		(dbServer == null) ||
		(dbPort == null) ||
		(dbName == null) ||
		(dbUser == null) ||
		(dbPassword == null)) {
		
		throw new DBException("dbPropNotFound");
	    }
	    
	    int dbInitConnections;
	    int dbMaxConnections;
	    int dbTimeOut;
	    int dbConnectionMaxUses;
	    
	    try {
		dbInitConnections = Integer.parseInt(dbProps.getProperty("db.initConnections"));
	    } catch (NumberFormatException nfe) {
		dbInitConnections = DB_INIT_CONNECTIONS;
	    }
	    
	    try {
		dbMaxConnections = Integer.parseInt(dbProps.getProperty("db.maxConnections"));
	    } catch (NumberFormatException nfe) {
		dbMaxConnections = DB_MAX_CONNECTIONS;
	    }
	    
	    try {
		dbTimeOut = Integer.parseInt(dbProps.getProperty("db.timeOut"));
	    } catch (NumberFormatException nfe) {
		dbTimeOut = DB_TIME_OUT;
	    }
	    
	    try {
		dbConnectionMaxUses = Integer.parseInt(dbProps.
						       getProperty("db.connectionMaxUses"));
	    } catch (NumberFormatException nfe) {
		dbConnectionMaxUses = DB_CONNECTION_MAX_USES;
	    }
	    
	    /*
	     * Make sure that the database driver exists.
	     */
	    
	    try {
		Class.forName(dbDriver);
	    } catch (ClassNotFoundException e) {
		throw new DBException(e);
	    }
	    
	    /*
	     * Create a pool that initially has DB_INIT_CONNECTIONS connections
	     * in it, and may grow to contain a maximum of DB_MAX_CONNECTIONS
	     * connections; requests will block for up to DB_TIME_OUT
	     * milli-seconds waiting for a free connection.
	     */
	    
	    try {
		PrintWriter logWriter = new PrintWriter(System.out);
		String URL = dbProtocol + ":" + dbProtocolSep + dbServer + ":" + dbPort + dbNameSep + dbName;
		DebugHandler.debug("URL : " + URL);
		DebugHandler.debug("Name : " + dbUser);
		DebugHandler.debug("Password : " + dbPassword);
		connectionPool = new ConnectionPool(dbName,
						    URL,
						    dbUser,
						    dbPassword,
						    dbMaxConnections,
						    dbInitConnections,
						    dbTimeOut,
						    logWriter, 
						    LogWriter.ERROR);		// log errors only -JCH-
	    } catch (Exception se) {
		throw new DBException(se);
	    }
	    
	    /*
	     * Set the max number of uses for a connection in the pool. After
	     * a connection has been used this many times, the connection would
	     * be closed and all the resources (database and other) held by the
	     * connection would be released.
	     */
	    
	}
	
	Connection con;
	
	try {
	    con = connectionPool.getConnection();
	} catch (Exception e) {
	    throw new DBException(e);
	}
	
	return con;
  }
    
    /**
     * Releases the database connection to the connection pool.
     *
     * @param con    the database connection
     */
    
    public synchronized void releaseConnection(Connection con) {
	
	if (connectionPool != null) {
	    connectionPool.freeConnection(con);
	    con = null;
	}
    }
    
    /**
     * Rolls back and releases the database connection to the connection pool.
     *
     * @param con    the database connection
     * @exception DBException
     */
    
    public void cleanupConnection(Connection con) throws DBException {
	
        if (con == null)
            return;
	
        try {
            con.rollback();
            con.setAutoCommit(true);
        } catch (SQLException e) {
            // Reset the connection handle.
            con = null;
            throw new DBException(e);
        }
	
        releaseConnection(con);
    }
    
    /**
     * Returns the number of free database connections available in the
     * underlying connection pool.
     *
     * @return    the number of free database connections
     * @exception DBException
     */
    
    public synchronized int getFreeConnectionCount() throws DBException {
	
        try {
            return connectionPool.getNumFreeCons();
        } catch (NullPointerException npe) {
            throw new DBException("connectionPoolNotInstantiated");
        }
    }
}
