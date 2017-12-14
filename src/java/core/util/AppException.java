/*
 * @(#)AppException.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;

import java.lang.Exception;

/**
 * A generic Exception specialization representing application exceptions.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class AppException extends Exception {
    static final long serialVersionUID = 1;
    public static final int SEVERITY_NONE = 0;
    public static final int SEVERITY_INFO = 1;
    public static final int SEVERITY_WARN = 2;
    public static final int SEVERITY_ERROR = 3;
    public static final int SEVERITY_FATAL = 4;

    protected String errorCode;
    protected int severity;
    protected Exception exception; 

    /*
     * Additional argunments to qualify the error code; inspired by the
     * localization needs.
     */

    protected Object[] errorCodeArgs;

    /**
     * Constructs this object with the specified parameters.
     *
     * @param message       exception message
     * @param errorCode     exception error code
     * @param errorCodeArgs arguments qualifying the error code
     * @param severity      exception severity
     * @param exception     underlying exception
     */

    public AppException(String message,
                        String errorCode,
                        Object[] errorCodeArgs,
                        int severity,
                        Exception exception) {

        super(message);

        this.errorCode = errorCode;
        this.errorCodeArgs = errorCodeArgs;
        this.severity = severity;
        this.exception = exception;
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param message       exception message
     * @param errorCode     exception error code
     * @param severity      exception severity
     * @param exception     underlying exception
     */

    public AppException(String message,
                        String errorCode,
                        int severity,
                        Exception exception) {

        this(message, errorCode, null, severity, null);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode     exception error code
     * @param severity      exception severity
     * @param exception     underlying exception
     */

    public AppException(String errorCode,
                        int severity,
                        Exception exception) {

        this(errorCode, errorCode, null, severity, exception);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode     exception error code
     * @param errorCodeArgs arguments qualifying the error code
     * @param severity      exception severity
     * @param exception     underlying exception
     */

    public AppException(String errorCode,
                        Object[] errorCodeArgs,
                        int severity,
                        Exception exception) {

        this(errorCode, errorCode, errorCodeArgs, severity, exception);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode       exception error code
     * @param severity        exception severity
     */

    public AppException(String errorCode,
                        int severity) {

        this(errorCode, errorCode, null, severity, null);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode       exception error code
     * @param errorCodeArgs   arguments qualifying the error code
     * @param severity        exception severity
     */

    public AppException(String errorCode,
                        Object[] errorCodeArgs,
                        int severity) {

        this(errorCode, errorCode, errorCodeArgs, severity, null);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode     exception error code
     * @param exception     underlying exception
     */

    public AppException(String errorCode,
                        Exception exception) {

        this(errorCode, errorCode, null, SEVERITY_INFO, exception);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode     exception error code
     * @param errorCodeArgs arguments qualifying the error code
     * @param exception     underlying exception
     */

    public AppException(String errorCode,
                        Object[] errorCodeArgs,
                        Exception exception) {

        this(errorCode, errorCode, errorCodeArgs, SEVERITY_INFO, exception);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode        exception error code
     */

    public AppException(String errorCode) {

        this(errorCode, errorCode, null, SEVERITY_INFO, null);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param errorCode        exception error code
     * @param errorCodeArgs    arguments qualifying the error code
     */

    public AppException(String errorCode, Object[] errorCodeArgs) {

        this(errorCode, errorCode, errorCodeArgs, SEVERITY_INFO, null);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param severity        exception severity
     */

    public AppException(int severity) {

        this(null, null, null, severity, null);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param excception    underlying exception
     */

    public AppException(Exception exception) {

        this(null, null, null, SEVERITY_INFO, exception);
    }

    /**
     * The no-argument constructor.
     */

    public AppException() {

        this(null, null, null, SEVERITY_INFO, null);
    }

    /**
     * Sets the error code to the specified value.
     *
     * @param errorCode        exception error code
     */

    public void setErrorCode(String errorCode) {

        this.errorCode = errorCode;
    }

    /**
     * Sets the error code argumets to the specified value.
     *
     * @param errorCodeArgs        arguments qualifying the error code
     */

    public void setErrorCode(Object[] errorCodeArgs) {

        this.errorCodeArgs = errorCodeArgs;
    }

    /**
     * Sets the severity to the specified value.
     *
     * @param severity        exception severity
     */

    public void setValue(int severity) {

        this.severity = severity;
    }

    /**
     * Sets the underlying exception to the specified value.
     *
     * @param exception        underlying exception
     */

    public void setException(Exception exception) {

        this.exception = exception;
    }

    /**
     * Returns the error code.
     *
     * @return    error code
     */

    public String getErrorCode() {

        return errorCode;
    }

    /**
     * Returns the error code arguments.
     *
     * @return    arguments qualifying the error code
     */

    public Object[] getErrorCodeArgs() {

        return errorCodeArgs;
    }

    /**
     * Returns the severity.
     *
     * @return    severity
     */

    public int getSeverity() {

        return severity;
    }

    /**
     * Returns the underlying exception.
     *
     * @return    underlying exception
     */

    public Exception getException() {

        return exception;
    }

    /**
     * Returns the exeption message.
     *
     * @return    exception message
     */

    public String getMessage() {

        try {
            return exception.getMessage();
        } catch (NullPointerException npe) {
            return super.getMessage();
        }
    }
}
