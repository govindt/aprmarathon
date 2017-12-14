/*
 * @(#)ExceptionHandler.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.lang.Throwable;
import java.util.Locale;

import core.util.AppException;
import core.util.Localizer;

/**
 * A handler for application exceptions with localization support for messages.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class ExceptionHandler {

    protected Localizer localizer;

    /**
     * Constructs an object for the specified locale. If no locale is
     * specified, sets to the default locale.
     */

    public ExceptionHandler(Locale locale) {

        setLocale(locale);
    }

    /**
     * The no-argument constructor.
     */

    public ExceptionHandler() {

        this(null);                                         
    }

    /**
     * Sets the locale.
     *
     * @param    locale    the locale for localized meassages
     */

    public void setLocale(Locale locale) {

        if (locale == null) {
            locale = Locale.getDefault();
        }

        localizer = Localizer.getInstance(locale);
    }

    /**
     * Returns the locale.
     *
     * @return    the locale for localized meassages
     */

    public Locale getLocale() {

        return localizer.getLocale();
    }

    /**
     * Returns the localizer for the current locale.
     *
     * @return    the localizer
     */

    public Localizer getLocalizer() {

        return localizer;
    }

    /**
     * Returns the exeption message. If the exception is an application
     * exception, it will try to return a localized message for the error
     * code for the exception.
     *
     * @param     e         the exception
     * @return    exception message
     */

    public String getMessage(Throwable e) {

        try {
            if (e instanceof AppException) {

                AppException ae = (AppException) e;

                String errCode = ae.getErrorCode();

                if (errCode == null) {
                    errCode = ae.getException().getMessage();
                }

                return localizer.getText(errCode, ae.getErrorCodeArgs());

            } else {
                return e.getMessage();
            }
        } catch (NullPointerException npe) {
            return null;
        }
    }

    /**
     * Returns the stack trace for the specified exeption.
     *
     * @param     e         the exception
     * @return    exception stack trace
     */

    public String getStackTrace(Throwable e) {

        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            e.printStackTrace(new PrintWriter(outStream, true));
            return outStream.toString();
        } catch (NullPointerException npe) {
            return null;
        }
    }
}
