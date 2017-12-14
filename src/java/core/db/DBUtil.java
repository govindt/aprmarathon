/*
 * @(#)DBUtil.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.db;

// Import statements

import core.db.DBException;
import core.db.PersistenceFactory;
import core.db.Persistent;
import core.db.TransactionContext;
import core.util.Cacheable;
import core.util.Cache;

import core.busobj.IdObject;

/**
 * A utility class that provides convenience methods for persistence/
 * database access.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class DBUtil {

    /*
     * Private constructor to disable instantiation.
     */

    private DBUtil() {

    }

    /**
     * Fetches business object from the database.
     *
     * @param         business object
     * @return        business object
     * @exception DBException
     */

    public static Object fetch(Object busObj) throws DBException {

        /*
        Cache c = Cache.getInstance();
        if( busObj instanceof Cacheable ) {
            Object cacheObj = c.getValue((Cacheable)busObj);
            if ( cacheObj != null ) 
                return cacheObj;
        }
        */

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.fetch();

        /*
        Object obj = p.fetch();

        if( obj instanceof Cacheable ) {
            String listCacheName = ((Cacheable)obj).getListCacheName();
            String cacheName = ((Cacheable)obj).getCacheName();
            if (!listCacheName.equals(cacheName)) 
                c.addValue((Cacheable)obj);
        }

        return obj;
        */
    }

    /**
     * Fetches collection of business objects from the database.
     *
     * @param         business object
     * @return        collection of business objects
     * @exception DBException
     */

    public static Object list(Object busObj) throws DBException {

        /*
        Cache c = Cache.getInstance();

        if( busObj instanceof Cacheable ) {
            String cacheName = ((Cacheable)busObj).getListCacheName();
            if ( cacheName != null ) {
                Object cacheObj = c.getList(cacheName);
                if ( cacheObj != null ) 
                    return cacheObj;
            }
        }
        */

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.list();

        /*
        Object obj = p.list();

        if( obj instanceof Cacheable ) {
            String cacheName = ((Cacheable)obj).getListCacheName();
            c.addList(cacheName, obj);
        }
            
        return obj;
        */
    }

    /**
     * Inserts into the database.
     *
     * @param         business object
     * @return        the update count
     * @exception DBException
     */

    public static Object insert(Object busObj) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.insert();

        /*
        Object obj = p.insert();

        Cache c = Cache.getInstance();

        if( obj instanceof Cacheable ) {
            String listCacheName = ((Cacheable)obj).getListCacheName();
            String cacheName = ((Cacheable)obj).getCacheName();
            if (listCacheName.equals(cacheName)) {
                Object cacheList = c.getList(cacheName);
                if ( cacheList != null )
                    c.addValue((Cacheable)obj);
            }
            else {
                c.addValue((Cacheable)obj);
            }
        }

        return obj;
        */
    }

    /**
     * Deletes from the database.
     *
     * @param         business object
     * @return        the update count
     * @exception DBException
     */

    public static Object delete(Object busObj) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.delete();
    }

    /**
     * Updates the database.
     *
     * @param         business object
     * @return        the update count
     * @exception DBException
     */

    public static Object update(Object busObj) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.update();
    }

    /**
     * Queries the database based on the specified object attributes.
     *
     * @param         business object
     * @return        collection of business objects
     * @exception DBException
     */

    public static Object query(Object busObj) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.query();
    }

    /**
     * Fetches business object from the database.
     *
     * @param busObj  business object
     * @param tc      the transaction context
     * @return        business object
     * @exception DBException
     */

    public static Object fetch(Object busObj, TransactionContext tc)
                                                            throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);
        p.setTransactionContext(tc);

        return p.fetch();
    }

    /**
     * Fetches collection of business objects from the database.
     *
     * @param busObj  business object
     * @param tc      the transaction context
     * @return        collection of business objects
     * @exception DBException
     */

    public static Object list(Object busObj, TransactionContext tc)
                                                            throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);
        p.setTransactionContext(tc);

        return p.list();
    }

    /**
     * Inserts into the database.
     *
     * @param busObj  business object
     * @param tc      the transaction context
     * @return        the update count
     * @exception DBException
     */

    public static Object insert(Object busObj, TransactionContext tc)
                                                            throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);
        p.setTransactionContext(tc);

        return p.insert();
    }

    /**
     * Deletes from the database.
     *
     * @param busObj  business object
     * @param tc      the transaction context
     * @return        the update count
     * @exception DBException
     */

    public static Object delete(Object busObj, TransactionContext tc)
                                                            throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);
        p.setTransactionContext(tc);

        return p.delete();
    }

    /**
     * Updates the database.
     *
     * @param busObj  business object
     * @param tc      the transaction context
     * @return        the update count
     * @exception DBException
     */

    public static Object update(Object busObj, TransactionContext tc)
                                                            throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);
        p.setTransactionContext(tc);

        return p.update();
    }

    /**
     * Queries the database based on the specified object attributes.
     *
     * @param busObj  business object
     * @param tc      the transaction context
     * @return        collection of business objects
     * @exception DBException
     */

    public static Object query(Object busObj, TransactionContext tc)
                                                            throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);
        p.setTransactionContext(tc);

        return p.query();
    }

    /**
     * Fetches business object from the database.
     *
     * @param busObj  business object
     * @param args    additional arguments/parameters
     * @return        business object
     * @exception DBException
     */

    public static Object fetch(Object busObj,
                               Object args) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.fetch(args);
    }

    /**
     * Fetches collection of business objects from the database.
     *
     * @param busObj  business object
     * @param args    additional arguments/parameters
     * @return        collection of business objects
     * @exception DBException
     */

    public static Object list(Object busObj,
                              Object args) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.list(args);
    }

    /**
     * Inserts into the database.
     *
     * @param busObj  business object
     * @param args    additional arguments/parameters
     * @return        the update count
     * @exception DBException
     */

    public static Object insert(Object busObj,
                                Object args) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.insert(args);
    }

    /**
     * Deletes from the database.
     *
     * @param busObj  business object
     * @param args    additional arguments/parameters
     * @return        the update count
     * @exception DBException
     */

    public static Object delete(Object busObj,
                                Object args) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.delete(args);
    }

    /**
     * Updates the database.
     *
     * @param busObj  business object
     * @param args    additional arguments/parameters
     * @return        the update count
     * @exception DBException
     */

    public static Object update(Object busObj,
                                Object args) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.update(args);
    }

    /**
     * Queries the database based on the specified object attributes.
     *
     * @param busObj  business object
     * @param args    additional arguments/parameters
     * @return        collection of business objects
     * @exception DBException
     */

    public static Object query(Object busObj,
                               Object args) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.query(args);
    }

    /**
     * Fetches business object from the database.
     *
     * @param busObj  business object
     * @param args    additional arguments/parameters
     * @param tc      the transaction context
     * @return        business object
     * @exception DBException
     */

    public static Object fetch(Object busObj,
                               Object args,
                               TransactionContext tc) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);
        p.setTransactionContext(tc);

        return p.fetch(args);
    }

    /**
     * Fetches collection of business objects from the database.
     *
     * @param busObj  business object
     * @param args    additional arguments/parameters
     * @param tc      the transaction context
     * @return        collection of business objects
     * @exception DBException
     */

    public static Object list(Object busObj,
                              Object args,
                              TransactionContext tc) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);

        return p.list(args);
    }

    /**
     * Inserts into the database.
     *
     * @param busObj  business object
     * @param args    additional arguments/parameters
     * @param tc      the transaction context
     * @return        the update count
     * @exception DBException
     */

    public static Object insert(Object busObj,
                                Object args,
                                TransactionContext tc) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);
        p.setTransactionContext(tc);

        return p.insert(args);
    }

    /**
     * Deletes from the database.
     *
     * @param busObj  business object
     * @param args    additional arguments/parameters
     * @param tc      the transaction context
     * @return        the update count
     * @exception DBException
     */

    public static Object delete(Object busObj,
                                Object args,
                                TransactionContext tc) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);
        p.setTransactionContext(tc);

        return p.delete(args);
    }

    /**
     * Updates the database.
     *
     * @param busObj  business object
     * @param args    additional arguments/parameters
     * @param tc      the transaction context
     * @return        the update count
     * @exception DBException
     */

    public static Object update(Object busObj,
                                Object args,
                                TransactionContext tc) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);
        p.setTransactionContext(tc);

        return p.update(args);
    }

    /**
     * Queries the database based on the specified object attributes.
     *
     * @param busObj  business object
     * @param args    additional arguments/parameters
     * @param tc      the transaction context
     * @return        collection of business objects
     * @exception DBException
     */

    public static Object query(Object busObj,
                               Object args,
                               TransactionContext tc) throws DBException {

        Persistent p = PersistenceFactory.createPersistentObject(busObj);
        p.setTransactionContext(tc);

        return p.query(args);
    }

    /**
     * Returns the next id generated through the specified table name.
     *
     * @param tableName        sequence table to be used for id generation
     * @return                 tuple id for the entity
     * @exception DBException
     */

    public static long getNextId(String tableName) throws DBException {

        IdObject id = new IdObject(tableName);

        Persistent p = PersistenceFactory.createPersistentObject(id);
        id = (IdObject) p.fetch();

        try {
            return id.getId();
        } catch (NullPointerException npe) {
            throw new DBException("idGenerationFailed");
        }
    }
}
