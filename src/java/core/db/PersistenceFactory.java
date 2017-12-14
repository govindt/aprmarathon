/*
 * @(#)PersistenceFactory.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.db;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.util.Hashtable;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.Enumeration;

import core.db.DBException;
import core.db.Persistent;

/**
 * An AbstractFactory implementation for getting persistent business object
 * instances for the specified business objects; maintains the mapping of
 * business object class names to their persistent counterparts.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class PersistenceFactory {

    private static Hashtable<String,String> persistenceMap = null;

    static {

        /*
         * Load the persistence map. The persistence map would be set to "null"
         * if there is any error in loading the persistence mapping, and
         * subsequent attempts to access the persistence map would throw an
         * appropriate exception. This is necessary, since the mapping would be
         * statically loaded only once and in case of its failure, all
         * subsequent attempts to access the mapping should raise the same
         * exception. 
         */

        persistenceMap = loadPersistenceMap();
    }

    /*
     * Loads the persistence map with the name-value pairs for the business
     * object class names and the corresponding persistence class names.
     */

    private static Hashtable<String,String> loadPersistenceMap() {

        String persistencePropertiesFile
            = "core.db.persistence";

	ResourceBundle rb = ResourceBundle.getBundle(persistencePropertiesFile);
        Hashtable<String, String> persistenceProps = new Hashtable<String,String>();

        try {
	    for ( Enumeration<String> iterator = rb.getKeys(); iterator.hasMoreElements(); ) {
		String key = iterator.nextElement();
		persistenceProps.put(key, rb.getString(key));
	    }
            //persistenceProps.load(new BufferedInputStream(new
            //                    FileInputStream(persistencePropertiesFile)));
        } catch (Exception e) {
            return null;
        }

        return persistenceProps;
    }

    /**
     * Returns a persistent business object instance for the specified business
     * object type.
     *
     * @param o        the business object
     * @exception DBException
     */

    public static Persistent createPersistentObject(Object o)
                                                        throws DBException {

        String objectClass = null;
        String persistent = null;

        try {
            objectClass = o.getClass().getName(); 
        } catch (NullPointerException npe) {
            throw new DBException("cannotCreatePersistentObjectForNullObject");
        }

        try {
            persistent = persistenceMap.get(objectClass);
        } catch (NullPointerException npe) {
            throw new DBException("persistencePropsLoadError");
        }

        if (persistent != null) {

            try {
                Class[] argTypes = { o.getClass() };
                Object[] argValues = { o };
            
                return (Persistent) Class.forName(persistent).
                        getConstructor(argTypes).newInstance(argValues);
            } catch (Exception e) {
                throw new DBException(e);
            }
        } else {
            Object args[] = { objectClass };
            throw new DBException("persistentObjectNotDefinedForClassArgs",
                                  args);
        }
    }
}
