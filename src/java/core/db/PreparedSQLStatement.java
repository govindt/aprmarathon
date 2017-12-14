/*
 * @(#)PreparedSQLStatement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.SQLException;

import core.db.DBException;
import core.db.SQLParam;
import core.db.SQLStatement;

/**
 * A specialization of SQLStatement that models prepared SQL statements.
 * This class holds a collection of input parameter specifications which would
 * be used by the database server to prepare the SQL statement.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class PreparedSQLStatement extends SQLStatement {

    /**
     * The collection of input parameter specifications.
     */

    protected SQLParam[] inParams;

    /**
     * Constructs this object with the specified statement and input parameters.
     *
     * @param statement        the SQL statement text
     * @param inParams         the input parameter specifications
     */

    public PreparedSQLStatement(String statement,
                                SQLParam[] inParams) {

        super(statement);

        this.inParams = inParams;
    }

    /**
     * Constructs this object with the specified statement.
     *
     * @param statement        the SQL statement text
     */

    public PreparedSQLStatement(String statement) {

        super(statement);
    }

    /**
     * The no-argument constructor.
     */

    public PreparedSQLStatement() {

        super();
    }

    /**
     * Sets the input parameter specifications.
     *
     * @param inParams        the input parameter specifications
     */

    public void setInParams(SQLParam[] inParams) {

        this.inParams = inParams;
    }

    /**
     * Sets the input parameter specification.
     *
     * @param inParam         the input parameter specification
     */

    public void setInParams(SQLParam inParam) {

        int index = 0;

        if ((inParams == null) || (inParams.length == 0)) {
            inParams = new SQLParam[1];
        } else {
            SQLParam[] prevInParams = inParams;
            inParams = new SQLParam[inParams.length + 1];

            for (index = 0; index < prevInParams.length; index++) {
                inParams[index] = prevInParams[index];    
            }
        }

        inParams[index] = inParam;
    }

    /**
     * Returns input parameters.
     *
     * @return        the input parameter specifications
     */

    public SQLParam[] getInParams() {

        return inParams;
    }

    /**
     * Overriden implementation to get a prepared SQL statement from the
     * database server.
     *
     * @exception DBException
     */

    protected void createStatement() throws DBException {

        PreparedStatement ps;
        
        try {
            ps = connection.prepareStatement(getStatement());
        } catch (SQLException se) {
            throw new DBException(se);
        }

        // Set input parameters.

        for (int i = 0; (inParams != null) && (i < inParams.length); i++) {

            SQLParam param = inParams[i];    

            /*
             * PreparedStatement.setObject() method does not work for "null"
             * objects with certain JDBC drivers; have to call the method 
             * PreparedStatement.setNull() explicitly for "null" objects.
             */

            try {
                if (param.getValue() != null) {
                    ps.setObject(param.getIndex(),
                                 param.getValue(),
                                 param.getSQLType(),
                                 param.getScale());
                } else {
                    ps.setNull(param.getIndex(), param.getSQLType());
                }
            } catch (SQLException se) {
                throw new DBException(se);
            }
        }

        sqlStatement = ps;
    }
}
