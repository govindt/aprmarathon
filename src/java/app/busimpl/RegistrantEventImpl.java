/*
 * RegistrantEventImpl.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.busimpl;

import java.lang.*;
import java.util.*;
import core.util.Constants;
import core.util.DebugHandler;
import core.db.DBUtil;
import core.util.AppException;
import core.util.Util;
import app.busobj.RegistrantEventObject;
import app.businterface.RegistrantEventInterface;
import app.util.AppConstants;

/**
 * The implementation of the RegistrantEventInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class RegistrantEventImpl implements RegistrantEventInterface  {
    private String REGISTRANTEVENT = "RegistrantEventInterface.getAllRegistrantEvent";
    
    /**
     *
     * Implementation that returns the RegistrantEventObject given a RegistrantEventObject filled with values that will be used for query from the underlying datasource.
     *
     * @param registrantevent_obj	RegistrantEventObject
     *
     * @return      Returns the ArrayList of RegistrantEventObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<RegistrantEventObject> getRegistrantEvents(RegistrantEventObject registrantevent_obj) throws AppException{
	RegistrantEventObject[] registrantEventObjectArr = getAllRegistrantEvents();
	ArrayList<RegistrantEventObject> v = new ArrayList<RegistrantEventObject>();
	if ( registrantEventObjectArr == null )
		return null;
	for ( int i = 0; i < registrantEventObjectArr.length; i++ ) {
		if ( registrantEventObjectArr[i] != null ) {
			if ( registrantevent_obj.getRegistrantEventId() == Constants.GET_ALL ) {
				v.add((RegistrantEventObject)registrantEventObjectArr[i].clone());
			} else {
				if ( (registrantevent_obj.getRegistrantEventId() != 0 && registrantevent_obj.getRegistrantEventId() == registrantEventObjectArr[i].getRegistrantEventId())
 || (registrantevent_obj.getRegistrantId() != 0 && registrantevent_obj.getRegistrantId() == registrantEventObjectArr[i].getRegistrantId())
 || (registrantevent_obj.getRegistrantEvent() != 0 && registrantevent_obj.getRegistrantEvent() == registrantEventObjectArr[i].getRegistrantEvent())
 || (registrantevent_obj.getRegistrantType() != 0 && registrantevent_obj.getRegistrantType() == registrantEventObjectArr[i].getRegistrantType())
 || (registrantevent_obj.getRegistrantSource() != 0 && registrantevent_obj.getRegistrantSource() == registrantEventObjectArr[i].getRegistrantSource())
 || (registrantevent_obj.getRegistrantClass() != 0 && registrantevent_obj.getRegistrantClass() == registrantEventObjectArr[i].getRegistrantClass())
 || (registrantevent_obj.getRegistrantBeneficiary() != 0 && registrantevent_obj.getRegistrantBeneficiary() == registrantEventObjectArr[i].getRegistrantBeneficiary())
 || (registrantevent_obj.getRegistrantEmergencyContact() != null && registrantevent_obj.getRegistrantEmergencyContact().equals(registrantEventObjectArr[i].getRegistrantEmergencyContact()))
 || (registrantevent_obj.getRegistrantEmergencyPhone() != null && registrantevent_obj.getRegistrantEmergencyPhone().equals(registrantEventObjectArr[i].getRegistrantEmergencyPhone()))
) {
					v.add((RegistrantEventObject)registrantEventObjectArr[i].clone());
				}
			}
		}
	}
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the RegistrantEventObject from the underlying datasource.
     * given registrant_event_id.
     *
     * @param registrant_event_id     int
     *
     * @return      Returns the RegistrantEventObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public RegistrantEventObject getRegistrantEvent(int registrant_event_id) throws AppException{
	RegistrantEventObject[] registrantEventObjectArr = getAllRegistrantEvents();
	if ( registrantEventObjectArr == null )
	    return null;
	for ( int i = 0; i < registrantEventObjectArr.length; i++ ) {
	    if ( registrantEventObjectArr[i] == null ) { // Try database and add to cache if found.
		    RegistrantEventObject registranteventObj = new RegistrantEventObject();
		    registranteventObj.setRegistrantEventId(registrant_event_id);
		    @SuppressWarnings("unchecked")
		    ArrayList<RegistrantEventObject> v = (ArrayList)DBUtil.fetch(registranteventObj);
		    if ( v == null || v.size() == 0 )
			    return null;
		    else {
			    registrantEventObjectArr[i] = (RegistrantEventObject)registranteventObj.clone();
			    Util.putInCache(REGISTRANTEVENT, registrantEventObjectArr);
		    }
	    }
	    if ( registrantEventObjectArr[i].getRegistrantEventId() == registrant_event_id ) {
		    DebugHandler.debug("Returning " + registrantEventObjectArr[i]);
		    return (RegistrantEventObject)registrantEventObjectArr[i].clone();
	    }
	}
	return null;
    }
    
    
    /**
     *
     * Implementation that returns all the <code>RegistrantEventObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>RegistrantEventObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public RegistrantEventObject[] getAllRegistrantEvents() throws AppException{
		RegistrantEventObject registrantEventObject = new RegistrantEventObject();
		RegistrantEventObject[] registrantEventObjectArr = (RegistrantEventObject[])Util.getAppCache().get(REGISTRANTEVENT);
		if ( registrantEventObjectArr == null ) {
		    DebugHandler.info("Getting registrantevent from database");
		    @SuppressWarnings("unchecked")
		    ArrayList<RegistrantEventObject> v = (ArrayList)DBUtil.list(registrantEventObject);
		    DebugHandler.finest(":v: " +  v);
		    if ( v == null )
			    return null;
		    registrantEventObjectArr = new RegistrantEventObject[v.size()];
		    for ( int idx = 0; idx < v.size(); idx++ ) {
			    registrantEventObjectArr[idx] = v.get(idx);
		    }
		    Util.putInCache(REGISTRANTEVENT, registrantEventObjectArr);
		}
		return registrantEventObjectArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>RegistrantEventObject</code> to the underlying datasource.
     *
     * @param registrantEventObject     RegistrantEventObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addRegistrantEvent(RegistrantEventObject registrantEventObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Registrant_Event_seq");
			registrantEventObject.setRegistrantEventId((int)l);
		}
		Integer i = (Integer)DBUtil.insert(registrantEventObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			registrantEventObject.setRegistrantEventId(i.intValue());
			DebugHandler.fine(registrantEventObject);
		}
		RegistrantEventObject buf = new RegistrantEventObject();
		buf.setRegistrantEventId(registrantEventObject.getRegistrantEventId());
		@SuppressWarnings("unchecked")
		ArrayList<RegistrantEventObject> v = (ArrayList)DBUtil.list(registrantEventObject, buf);
		registrantEventObject = v.get(0);
		RegistrantEventObject[] registrantEventObjectArr = getAllRegistrantEvents();
		boolean foundSpace = false;

		for ( int idx = 0; idx < registrantEventObjectArr.length; idx++ ) {
			if ( registrantEventObjectArr[idx] == null ) {
				registrantEventObjectArr[idx] = (RegistrantEventObject)registrantEventObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			RegistrantEventObject[] newregistrantEventObjectArr = new RegistrantEventObject[registrantEventObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < registrantEventObjectArr.length; idx++ ) {
				newregistrantEventObjectArr[idx] = (RegistrantEventObject)registrantEventObjectArr[idx].clone();
			}
			newregistrantEventObjectArr[idx] = (RegistrantEventObject)registrantEventObject.clone();
			Util.putInCache(REGISTRANTEVENT, newregistrantEventObjectArr);
		} else {
			Util.putInCache(REGISTRANTEVENT, registrantEventObjectArr);
		}
		return i;
	}
	
    
    /**
     *
     * Implementation to update the <code>RegistrantEventObject</code> in the underlying datasource.
     *
     * @param registrantEventObject     RegistrantEventObject
     *
     * @throws AppException if the operation fails
     *
     */
    
	public Integer updateRegistrantEvent(RegistrantEventObject registrantEventObject) throws AppException{
		RegistrantEventObject newRegistrantEventObject = getRegistrantEvent(registrantEventObject.getRegistrantEventId()); // This call will make sure cache/db are in sync
		Integer i = (Integer)DBUtil.update(registrantEventObject);
		DebugHandler.fine("i: " +  i);
		RegistrantEventObject[] registrantEventObjectArr = getAllRegistrantEvents();
		if ( registrantEventObjectArr == null )
			return null;
		for ( int idx = 0; idx < registrantEventObjectArr.length; idx++ ) {
			if ( registrantEventObjectArr[idx] != null ) {
				if ( registrantEventObjectArr[idx].getRegistrantEventId() == registrantEventObject.getRegistrantEventId() ) {
					DebugHandler.debug("Found RegistrantEvent " + registrantEventObject.getRegistrantEventId());
					registrantEventObjectArr[idx] = (RegistrantEventObject)registrantEventObject.clone();
					Util.putInCache(REGISTRANTEVENT, registrantEventObjectArr);
				}
			}
		}
		return i;
	}
    
    
    /**
     *
     * Implementation to delete the <code>RegistrantEventObject</code> in the underlying datasource.
     *
     * @param registrantEventObject     RegistrantEventObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteRegistrantEvent(RegistrantEventObject registrantEventObject) throws AppException{
	RegistrantEventObject newRegistrantEventObject = getRegistrantEvent(registrantEventObject.getRegistrantEventId()); // This call will make sure cache/db are in sync
	RegistrantEventObject[] registrantEventObjectArr = getAllRegistrantEvents();
	Integer i = new Integer(0);
	if ( registrantEventObject != null ) {
		i = (Integer)DBUtil.delete(registrantEventObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( registrantEventObjectArr != null ) {
			for (int idx = 0; idx < registrantEventObjectArr.length; idx++ ) {
				if ( registrantEventObjectArr[idx] != null && registrantEventObjectArr[idx].getRegistrantEventId() == registrantEventObject.getRegistrantEventId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (registrantEventObjectArr.length - 1) )
						registrantEventObjectArr[idx] = registrantEventObjectArr[idx + 1]; // Move the array
					else
						registrantEventObjectArr[idx] = null;
				}
				if ( registrantEventObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(REGISTRANTEVENT, registrantEventObjectArr);
		}
		return i;
	}
}
