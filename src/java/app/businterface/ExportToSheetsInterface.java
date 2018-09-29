/*
 * ExportToSheetsInterface.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;

/**
 * The interface which downloads from database and updates
 * google sheets
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public interface ExportToSheetsInterface {
    
    /**
     *
     * Interface that updates Registrant Info to the underlying datasource.
     *
     * @throws AppException if the underlying operation fails
     * @throws AppException if the underlying operation fails
     */
    
    public void updateRegistrants() throws AppException;

    /**
     *
     * Interface that updates Participants Info to the underlying datasource.
     *
     * @throws AppException if the underlying operation fails
     * @throws AppException if the underlying operation fails
     */
	 
	 public void updateParticipants() throws AppException;
     
}
