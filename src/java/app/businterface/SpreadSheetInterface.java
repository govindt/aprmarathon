/*
 * SpreadSheetInterface.java
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
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public interface SpreadSheetInterface {
    public void writeToFile(String fileName, Object id) throws AppException;
    public void readFromFile(String fileName, Object id) throws AppException;
}
