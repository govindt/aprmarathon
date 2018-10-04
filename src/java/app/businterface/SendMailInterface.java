/*
 * SendMailInterface.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.SendMailObject;

/**
 * The interface which downloads from database and updates
 * google sheets
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public interface SendMailInterface {
    
    /**
     *
     * Interface that emails Receipt Registrant Info to the underlying datasource.
     *
     * @throws AppException if the underlying operation fails
     */
    
    public Integer mailReceiptRegistrants(SendMailObject smObj) throws AppException;
}
