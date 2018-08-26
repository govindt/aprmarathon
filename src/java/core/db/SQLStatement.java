/*
 * @(#)SQLStatement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Vector;

import core.db.DBException;
import core.db.DBManager;
import core.db.TransactionContext;
import core.util.DebugHandler;

/**
 * Models a SQL statement with the SQL statement text, input-output parameters,
 * and references to the database manager, database connection and SQL
 * statement.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class SQLStatement {

    /**
     * The database manager.
     */

    protected DBManager dbManager = null;

    /**
     * The SQL statement text.
     */

    protected String statement;

    /**
     * The SQL statement.
     */

    protected Statement sqlStatement = null;

    /**
     * The connection to the database server.
     */

    protected Connection connection = null;

    /**
     * Constructs this object with the specified statement.
     *
     * @param statement        the SQL statement text
     */

    public SQLStatement(String statement) {

        this();
        this.statement = statement;
    }

    /**
     * The no-argument constructor.
     */

    public SQLStatement() {

        dbManager = DBManager.getInstance();
    }

    /**
     * Sets the SQL statement text.
     *
     * @param statement        the SQL statement text
     */

    public void setStatement(String statement) {
        this.statement = statement;
    }

    /**
     * Returns the SQL statement text.
     *
     * @return        the SQL statement text
     */

    public String getStatement() {

        return statement;
    }

    /**
     * Sets the SQL statement.
     *
     * @param sqlStatement        the SQL statement
     */

    protected void setSQLStatement(Statement sqlStatement) {

        this.sqlStatement = sqlStatement;
    }

    /**
     * Returns the SQL statement.
     *
     * @return        the SQL statement
     */

    protected Statement getSQLStatement() {

        return sqlStatement;
    }

    /**
     * Gets a SQL statement from the database server.
     *
     * @exception DBException
     */

    protected void createStatement() throws DBException {

        try {
            sqlStatement = connection.createStatement();
        } catch (SQLException se) {
            throw new DBException(se);
        }
    }

    /**
     * Releases the connection to the database server.
     *
     * @exception DBException
     */

    public void closeConnection() throws DBException {

        if (connection != null) {

            dbManager.releaseConnection(connection);
            connection = null;
        }
    }

    /**
     * Closes the SQL statement.
     *
     * @exception DBException
     */

    public void close() throws DBException {

        if (sqlStatement != null) {
            try {
                sqlStatement.close();
            } catch (SQLException se) {
                throw new DBException(se);
            }
        }
    }

    /**
     * Executes the query using the SQL statement. Uses the connection from
     * the specified transaction context. If none is specified, gets a
     * connection from the database manager.
     *
     * @param tranContext        the transaction context
     * @return                   the query result set
     * @exception DBException
     */

    public ResultSet query(TransactionContext tranContext) throws DBException {

        if (tranContext != null) {
            connection = tranContext.getConnection();
        } else {
            if (connection == null) {
                connection = dbManager.getConnection();
            }
        }

        createStatement();

        Statement s = getSQLStatement();

        ResultSet rs;
        
        try {
            rs = (s instanceof PreparedStatement) ?
                    ((PreparedStatement) s).executeQuery() :
                    s.executeQuery(getStatement());
        } catch (SQLException se) {
            throw new DBException(se);
        }

        return rs;
    }

    /**
     * Executes the query using the SQL statement. Uses the connection from
     * the specified transaction context. If none is specified, gets a
     * connection from the database manager. Returns the query result set as
     * a business object.
     *
     * @param tranContext        the transaction context
     * @param orMap              the persistent business object
     * @return                   the business object
     * @exception DBException
     */

    public Object query(TransactionContext tranContext,
                        ObjectRelationalMap orMap) throws DBException {

        ResultSet rs = query(tranContext);

        Object result = orMap.getResultObject(rs);

        if (tranContext == null) {
            close();
            dbManager.releaseConnection(connection);
            connection = null;
        }

        return result;
    }

    /**
     * Executes the query using the SQL statement. Uses the connection from
     * the specified transaction context. If none is specified, gets a
     * connection from the database manager. Returns the query result set as
     * a collection of business objects.
     *
     * @param tranContext        the transaction context
     * @param orMap              the persistent business object
     * @return                   the business object collection
     * @exception DBException
     */

    public Object list(TransactionContext tranContext,
                       ObjectRelationalMap orMap) throws DBException {

        ResultSet rs = query(tranContext);

        Object result = orMap.getResultObjects(rs);

        if (tranContext == null) {
            close();
            dbManager.releaseConnection(connection);
            connection = null;
        }

        return result;
    }

    /**
     * Executes the update using the SQL statement. Uses the connection from
     * the specified transaction context. If none is specified, gets a
     * connection from the database manager. Returns the count of the rows
     * affected by the update operation.
     *
     * @param tranContext        the transaction context
     * @return                   the update count
     * @exception DBException
     */

    public Object update(TransactionContext tranContext) throws DBException {

        if (tranContext != null) {
            connection = tranContext.getConnection();
        } else {
            if (connection == null) {
                connection = dbManager.getConnection();
            }
        }

        createStatement();

        Statement s = getSQLStatement();

        int count;
        
        try {
            count = (s instanceof PreparedStatement) ?
                            ((PreparedStatement) s).executeUpdate() :
                            s.executeUpdate(getStatement());
			
			ResultSet rs = s.getGeneratedKeys();
			if(rs.next()){
				count = rs.getInt(1);
				DebugHandler.fine("ID " + count);
			}
        } catch (SQLException se) {
            throw new DBException(se);
        }

        if (tranContext == null) {
            close();
            dbManager.releaseConnection(connection);
            connection = null;
        }

        return new Integer(count);
    }
}
