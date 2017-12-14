/*
 * @(#)DebugException.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;

/**
 * Debug Exception Handling
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class DebugException extends AppException {
    static final long serialVersionUID = 1;
    public DebugException(String message,
                         String errorCode,
                         int severity,
                         Exception exception) {

		super(message,errorCode,severity,exception);
	}

    public DebugException(String errorCode,
                         int severity) {

        super(errorCode, severity);
    }

    public DebugException(String errorCode) {
		super(errorCode);
    }

    public DebugException(int severity) {
		super(severity);
	}

}

