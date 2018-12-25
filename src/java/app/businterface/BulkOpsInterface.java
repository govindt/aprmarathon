/*
 * BulkOpsInterface.java
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
 * The interface which does bulks operation taking data from
 * google sheets
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public interface BulkOpsInterface {
    
    /**
     *
     * Interface that generates receipts for a particular year
     *
     * @throws AppException if the underlying operation fails
     */
    
    public Integer bulkReceiptGenerate(String year) throws AppException;
	
	/**
     *
     * Interface that updates db for a particular year
     *
     * @throws AppException if the underlying operation fails
     */
    
    public Integer bulkUpdateRegistrants(String year, int event_id) throws AppException;
	
	/**
     *
     * Interface that updates db for a particular year
     *
     * @throws AppException if the underlying operation fails
     */
    
    public Integer bulkUpdateParticipants(String year) throws AppException;
	
	/**
     *
     * Interface that updates db for a particular year
     *
     * @throws AppException if the underlying operation fails
     */
    
    public Integer bulkUpdateResults(String year) throws AppException;
}
