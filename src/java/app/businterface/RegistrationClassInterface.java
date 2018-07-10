/*
 * RegistrationClassInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.RegistrationClassObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface RegistrationClassInterface {
    
    /**
     *
     * Interface that returns the RegistrationClassObject given a RegistrationClassObject filled with values that will be used for query from the underlying datasource.
     *
     * @param registrationclass_obj	RegistrationClassObject
     *
     * @return      Returns the ArrayList of RegistrationClassObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<RegistrationClassObject> getRegistrationClass(RegistrationClassObject registrationclass_obj) throws AppException;
    
    /**
     *
     * Interface that returns the RegistrationClassObject given registration_class_id from the underlying datasource.
     *
     * @param registration_class_id     int
     *
     * @return      Returns the RegistrationClassObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public RegistrationClassObject getRegistrationClas(int registration_class_id) throws AppException;
    
    /**
     *
     * Interface that returns all the <code>RegistrationClassObject</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>RegistrationClassObject</code>
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public RegistrationClassObject[] getAllRegistrationClass() throws AppException;
    
    /**
     *
     * Interface to add the <code>RegistrationClassObject</code> to the underlying datasource.
     *
     * @param registrationClassObject     RegistrationClassObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer addRegistrationClass(RegistrationClassObject registrationClassObject) throws AppException;
    
    /**
     *
     * Interface to update the <code>RegistrationClassObject</code> in the underlying datasource.
     *
     * @param registrationClassObject     RegistrationClassObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer updateRegistrationClass(RegistrationClassObject registrationClassObject) throws AppException;
    
    /**
     *
     * Interface to delete the <code>RegistrationClassObject</code> in the underlying datasource.
     *
     * @param registrationClassObject     RegistrationClassObject
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public Integer deleteRegistrationClass(RegistrationClassObject registrationClassObject) throws AppException;
}
