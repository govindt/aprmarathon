/*
 * @(#)Cache.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */

package core.util;
import java.util.*;

public class Cache {

    private static Cache 	self;
    private Hashtable<String, Object> cacheData;

    private Cache() {
	cacheData = new Hashtable<String, Object>();	
    }
    
    public static Cache getInstance() {
	if ( self == null ) 
	    self = new Cache();
	
	return self;
    }
    
    public void addList(String cacheName, Object list) {
	cacheData.put(cacheName,list);	
    }  
    
    public Object getList(String cacheName) {
	return cacheData.get(cacheName);	
    }  
    
    public void addValue(Cacheable cacheObject) {
	String cacheName = cacheObject.getCacheName();
        @SuppressWarnings("unchecked")
	Hashtable<String,Cacheable> tableObjects = (Hashtable<String, Cacheable>)cacheData.get(cacheName);
	if ( tableObjects == null ) {
	    tableObjects = new Hashtable<String, Cacheable>();
	    cacheData.put(cacheName,tableObjects);
	}
	Object key = cacheObject.getCacheKey();
	tableObjects.put((String)key,cacheObject);
    }
    
    public Cacheable getValue(Cacheable cacheObject) {
	String cacheName = cacheObject.getCacheName();
        @SuppressWarnings("rawtypes")
	Hashtable tableObjects = (Hashtable)cacheData.get(cacheName);
	if ( tableObjects == null ) {
	    return null;
	}
	Object key = cacheObject.getCacheKey();
	return (Cacheable)tableObjects.get(key);
    }
    
    public Cacheable getValue(String cacheName, Object keyValue  ) {
        @SuppressWarnings("rawtypes")
	Hashtable tableObjects = (Hashtable)cacheData.get(cacheName);
	if ( tableObjects == null ) {
	    return null;
	}
	return (Cacheable)tableObjects.get(keyValue);
    }

}
