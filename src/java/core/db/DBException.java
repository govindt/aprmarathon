/*
 * @(#)DBException.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.db;

// Import statements

import java.lang.Exception;
import core.util.AppException;

/**
 * A specialized application exception representing database exceptions.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class DBException extends AppException {
    static final long serialVersionUID = 1;

    /**
     * Constructs this object with the specified parameters.
     *
     * @param message       exception message
     * @param errorCode     exception error code
     * @param severity      exception severity
     * @param excception    underlying exception
     */

    public DBException(String message,
                       String errorCode,
                       int severity,
                       Exception exception) {

        super(message, errorCode, severity, exception);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param message       exception message
     * @param errorCode     exception error code
     * @param errorCodeArgs arguments qualifying the error code
     * @param severity      exception severity
     * @param excception    underlying exception
     */

    public DBException(String message,
                       String errorCode,
                       Object[] errorCodeArgs,
                       int severity,
                       Exception exception) {

        super(message, errorCode, errorCodeArgs, severity, exception);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode     exception error code
     * @param severity      exception severity
     * @param exception     underlying exception
     */

    public DBException(String errorCode,
                       int severity,
                       Exception exception) {

        super(errorCode, severity, exception);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode     exception error code
     * @param errorCodeArgs arguments qualifying the error code
     * @param severity      exception severity
     * @param exception     underlying exception
     */

    public DBException(String errorCode,
                       Object[] errorCodeArgs,
                       int severity,
                       Exception exception) {

        super(errorCode, errorCodeArgs, severity, exception);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode       exception error code
     * @param severity        exception severity
     */

    public DBException(String errorCode,
                       int severity) {

        super(errorCode, severity);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode       exception error code
     * @param errorCodeArgs   arguments qualifying the error code
     * @param severity        exception severity
     */

    public DBException(String errorCode,
                       Object[] errorCodeArgs,
                       int severity) {

        super(errorCode, errorCodeArgs, severity);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode     exception error code
     * @param exception     underlying exception
     */

    public DBException(String errorCode,
                       Exception exception) {

        super(errorCode, exception);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode     exception error code
     * @param errorCodeArgs arguments qualifying the error code
     * @param exception     underlying exception
     */

    public DBException(String errorCode,
                       Object[] errorCodeArgs,
                       Exception exception) {

        super(errorCode, exception);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode        exception error code
     */

    public DBException(String errorCode) {

        super(errorCode);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode        exception error code
     * @param errorCodeArgs    arguments qualifying the error code
     */

    public DBException(String errorCode, Object[] errorCodeArgs) {

        super(errorCode, errorCodeArgs);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param severity        exception severity
     */

    public DBException(int severity) {

        super(severity);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param excception    underlying exception
     */

    public DBException(Exception exception) {

        super(exception);
    }

    /**
     * The no-argument constructor.
     */

    public DBException() {

        super();
    }

    /**
     * Returns the error code. The sub-string extraction for the vendor error
     * code is specific to Oracle error codes.
     *
     * @return    error code
     */

    public String getErrorCode() {

        String code = errorCode;

        if (code == null) {
            try {
                code = getException().getMessage();
            } catch (NullPointerException npe) {
            }
        }

        try {
            if (code.startsWith("ORA-")) {
                code = code.substring(0, 9);
            }
        } catch (NullPointerException npe) {
        }

        return code;
    }
}
