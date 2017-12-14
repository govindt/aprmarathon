/*
 * @(#)CallableSQLStatement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Vector;

import core.db.DBException;
import core.db.PreparedSQLStatement;
import core.db.SQLParam;

/**
 * A specialization of PreparedSQLStatement that models SQL statements that
 * call stored procedures. This class holds a collection of output parameter
 * specifications since the call to the stored procedure might yield some
 * output parameters.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class CallableSQLStatement extends PreparedSQLStatement {

    /**
     * The collection of output parameter specifications.
     */

    protected SQLParam[] outParams;

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param statement        the SQL statement text
     * @param inParams         the input parameter specifications
     * @param outParams        the output parameter specifications
     */

    public CallableSQLStatement(String statement,
                                SQLParam[] inParams,
                                SQLParam[] outParams) {

        super(statement, inParams);

        this.outParams = outParams;
    }

    /**
     * Constructs this object with the specified statement and input parameters.
     *
     * @param statement        the SQL statement text
     * @param inParams         the input parameter specifications
     */

    public CallableSQLStatement(String statement,
                                SQLParam[] inParams) {

        super(statement, inParams);
    }

    /**
     * Constructs this object with the specified statement.
     *
     * @param statement        the SQL statement text
     */

    public CallableSQLStatement(String statement) {

        super(statement);
    }

    /**
     * The no-argument constructor.
     */

    public CallableSQLStatement() {

        super();
    }

    /**
     * Sets the output parameter specifications.
     *
     * @param outParams        the output parameter specifications
     */

    public void setOutParams(SQLParam[] outParams) {

        this.outParams = outParams;
    }

    /**
     * Sets the output parameter specification.
     *
     * @param outParam        the output parameter specification
     */

    public void setOutParams(SQLParam param) {

        int index = 0;

        if ((outParams == null) || (outParams.length == 0)) {
            outParams = new SQLParam[1];
        } else {
            SQLParam[] prevOutParams = outParams;
            outParams = new SQLParam[outParams.length + 1];

            for (index = 0; index < prevOutParams.length; index++) {
                outParams[index] = prevOutParams[index];    
            }
        }

        outParams[index] = param;
    }

    /**
     * Returns output parameters.
     *
     * @return        the output parameter specifications
     */

    public SQLParam[] getOutParams() {

        return outParams;
    }

    /**
     * Overriden implementation to get a callable SQL statement from the
     * database server.
     *
     * @exception DBException
     */

    protected void createStatement() throws DBException {

        CallableStatement cs;
        
        try {
            cs = connection.prepareCall(getStatement());
        } catch (SQLException se) {
            throw new DBException(se);
        }

        // Set input parameters.

        SQLParam[] params = getInParams();

        for (int i = 0; (params != null) && (i < params.length); i++) {

            SQLParam param = params[i];    

            try {
                cs.setObject(param.getIndex(),
                             param.getValue(),
                             param.getSQLType(),
                             param.getScale());
            } catch (SQLException se) {
                throw new DBException(se);
            }
        }

        // Register output parameters.

        params = getOutParams();

        for (int i = 0; (params != null) && (i < params.length); i++) {

            SQLParam param = params[i];    

            try {
                cs.registerOutParameter(param.getIndex(),
                                        param.getSQLType(),
                                        param.getScale());
            } catch (SQLException se) {
                throw new DBException(se);
            }
        }

        sqlStatement = cs;
    }

    /**
     * Returns a collection of output parameter specifications with the
     * output values from the execution of the callable statement.
     *
     * @return             the output parameter specifications with values
     * @exception DBException
     */

    public SQLParam[] getOutParamValues() throws DBException {

        SQLParam[] params = getOutParams();

        if (params == null) {
            return null;
        }

        for (int i = 0; i < params.length; i++) {

            SQLParam param = params[i];    

            try {
                param.setValue(getOutParamValue(param.getIndex()));
            } catch (NullPointerException npe) {
                continue;
            }
        }

        return params;
    }

    /**
     * Returns the output values from the execution of the callable statement
     * for the specified index.
     *
     * @param index        the output parameter index
     * @return             the parameter value
     * @exception DBException
     */

    public Object getOutParamValue(int index) throws DBException {

        try {
            return ((CallableStatement) sqlStatement).getObject(index);
        } catch (SQLException se) {
            throw new DBException(se);
        }
    }
}
