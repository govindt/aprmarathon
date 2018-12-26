/*
 * ParticipantEventImpl.java
 *
 * APR Marathon Registration App Project - MANUAL EDIT
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
import app.busobj.ParticipantEventObject;
import app.busobj.ParticipantObject;
import app.businterface.ParticipantEventInterface;
import app.businterface.ParticipantInterface;
import app.busobj.AgeCategoryObject;
import app.busobj.RegistrantEventObject;
import app.busobj.EventObject;
import app.businterface.RegistrantEventInterface;
import app.businterface.AgeCategoryInterface;
import app.businterface.EventInterface;
import app.util.AppConstants;

/**
 * The implementation of the ParticipantEventInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class ParticipantEventImpl implements ParticipantEventInterface  {
	private String PARTICIPANTEVENT = "ParticipantEventInterface.getAllParticipantEvent";
	
	public ParticipantEventObject updateParticipantEventAgeCategory(ParticipantEventObject pEObj) throws AppException {
		RegistrantEventInterface rEIf = new RegistrantEventImpl();
		RegistrantEventObject rEObj = rEIf.getRegistrantEvent(pEObj.getParticipantGroup());
		ParticipantInterface pIf = new ParticipantImpl();
		int eventId = rEObj.getRegistrantEvent();
		EventInterface eIf = new EventImpl();
		EventObject eObj = eIf.getEvent(eventId);
		ParticipantObject pObj = pIf.getParticipant(pEObj.getParticipantId());
		if ( eObj == null || eObj.getEventStartDate() == null )
			throw new AppException("Event not in DB or Event Start Date is null");
		if ( pObj == null )
			throw new AppException("Event not in DB or Event Start Date is null");
		if ( pObj == null || pObj.getParticipantDateOfBirth() == null )
			throw new AppException("Participant not in DB or Date of Birth is null for " + pObj);
		
		int years = Util.calculateAge(pObj.getParticipantDateOfBirth(), eObj.getEventStartDate());
		AgeCategoryInterface aCIf = new AgeCategoryImpl();
		AgeCategoryObject[] aCObjArr = aCIf.getAllAgeCategorys();
		for ( int i = 0; i < aCObjArr.length; i++) {
			if ( years <= aCObjArr[i].getMaxAge() && years >= aCObjArr[i].getMinAge() ) {
				ParticipantEventObject pEObj1 = (ParticipantEventObject)pEObj.clone();
				pEObj1.setParticipantEventAgeCategory(aCObjArr[i].getAgeCategoryId());
				DebugHandler.info("DOB: " + pObj.getParticipantDateOfBirth() + " Age : " + years + " Age Category: " + aCObjArr[i].getAgeCategory());
				return pEObj1;
			}
		}
		
		return pEObj;
	}
	
	/**
	 *
	 * Implementation that returns the ParticipantEventObject given a ParticipantEventObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param participantevent_obj	ParticipantEventObject
	 *
	 * @return	  Returns the ArrayList of ParticipantEventObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public ArrayList<ParticipantEventObject> getParticipantEvents(ParticipantEventObject participantevent_obj) throws AppException{
		ParticipantEventObject[] participantEventObjectArr = getAllParticipantEvents();
		if ( participantevent_obj.getParticipantEventId() == Constants.GET_ALL ) {
			if ( participantEventObjectArr == null )
				return null;
			ArrayList<ParticipantEventObject> v = new ArrayList<ParticipantEventObject>();
			for ( int i = 0; i < participantEventObjectArr.length; i++ ) {
				v.add((ParticipantEventObject)participantEventObjectArr[i].clone());
			}
			return v;
		}
		else {
			@SuppressWarnings("unchecked")
			ArrayList<ParticipantEventObject> v = (ArrayList<ParticipantEventObject>)DBUtil.list(participantevent_obj,participantevent_obj);
			DebugHandler.finest("v: " + v);
			return v;
		}
	}
	
	/**
	 *
	 * Implementation of the method that returns the ParticipantEventObject from the underlying datasource.
	 * given participant_event_id.
	 *
	 * @param participant_event_id	 int
	 *
	 * @return	  Returns the ParticipantEventObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
	
	public ParticipantEventObject getParticipantEvent(int participant_event_id) throws AppException{
		ParticipantEventObject[] participantEventObjectArr = getAllParticipantEvents();
		if ( participantEventObjectArr == null )
			return null;
		for ( int i = 0; i < participantEventObjectArr.length; i++ ) {
			if ( participantEventObjectArr[i] == null ) { // Try database and add to cache if found.
				ParticipantEventObject participanteventObj = new ParticipantEventObject();
				participanteventObj.setParticipantEventId(participant_event_id);
				@SuppressWarnings("unchecked")
				ArrayList<ParticipantEventObject> v = (ArrayList)DBUtil.fetch(participanteventObj);
				if ( v == null || v.size() == 0 )
					return null;
				else {
					participantEventObjectArr[i] = (ParticipantEventObject)participanteventObj.clone();
					Util.putInCache(PARTICIPANTEVENT, participantEventObjectArr);
				}
			}
			if ( participantEventObjectArr[i].getParticipantEventId() == participant_event_id ) {
				DebugHandler.debug("Returning " + participantEventObjectArr[i]);
				return (ParticipantEventObject)participantEventObjectArr[i].clone();
			}
		}
		return null;
	}
	
	
	/**
	 *
	 * Implementation that returns all the <code>ParticipantEventObjects</code> from the underlying datasource.
	 *
	 * @return	  Returns an Array of <code>ParticipantEventObject</code>
	 *
	 * @throws AppException if the operation fails
	 *
	 */
	
	public ParticipantEventObject[] getAllParticipantEvents() throws AppException{
		ParticipantEventObject participantEventObject = new ParticipantEventObject();
		ParticipantEventObject[] participantEventObjectArr = (ParticipantEventObject[])Util.getAppCache().get(PARTICIPANTEVENT);
		if ( participantEventObjectArr == null ) {
			DebugHandler.info("Getting participantevent from database");
			@SuppressWarnings("unchecked")
			ArrayList<ParticipantEventObject> v = (ArrayList)DBUtil.list(participantEventObject);
			DebugHandler.finest(":v: " +  v);
			if ( v == null )
				return null;
			participantEventObjectArr = new ParticipantEventObject[v.size()];
			for ( int idx = 0; idx < v.size(); idx++ ) {
				participantEventObjectArr[idx] = v.get(idx);
			}
			Util.putInCache(PARTICIPANTEVENT, participantEventObjectArr);
		}
		return participantEventObjectArr;
	}
	
	
	/**
	 *
	 * Implementation to add the <code>ParticipantEventObject</code> to the underlying datasource.
	 *
	 * @param participantEventObject	 ParticipantEventObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
	
	public Integer addParticipantEvent(ParticipantEventObject participantEventObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Participant_Event_seq");
			participantEventObject.setParticipantEventId((int)l);
		}
		participantEventObject = updateParticipantEventAgeCategory(participantEventObject);
		Integer i = (Integer)DBUtil.insert(participantEventObject);
		DebugHandler.fine("i: " +  i);
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			participantEventObject.setParticipantEventId(i.intValue());
			DebugHandler.fine(participantEventObject);
		}
		ParticipantEventObject buf = new ParticipantEventObject();
		buf.setParticipantEventId(participantEventObject.getParticipantEventId());
		@SuppressWarnings("unchecked")
		ArrayList<ParticipantEventObject> v = (ArrayList)DBUtil.list(participantEventObject, buf);
		participantEventObject = v.get(0);
		ParticipantEventObject[] participantEventObjectArr = getAllParticipantEvents();
		boolean foundSpace = false;

		for ( int idx = 0; idx < participantEventObjectArr.length; idx++ ) {
			if ( participantEventObjectArr[idx] == null ) {
				participantEventObjectArr[idx] = (ParticipantEventObject)participantEventObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			ParticipantEventObject[] newparticipantEventObjectArr = new ParticipantEventObject[participantEventObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < participantEventObjectArr.length; idx++ ) {
				newparticipantEventObjectArr[idx] = (ParticipantEventObject)participantEventObjectArr[idx].clone();
			}
			newparticipantEventObjectArr[idx] = (ParticipantEventObject)participantEventObject.clone();
			Util.putInCache(PARTICIPANTEVENT, newparticipantEventObjectArr);
		} else {
			Util.putInCache(PARTICIPANTEVENT, participantEventObjectArr);
		}
		return i;
	}
	
	
	/**
	 *
	 * Implementation to update the <code>ParticipantEventObject</code> in the underlying datasource.
	 *
	 * @param participantEventObject	 ParticipantEventObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
	
	public Integer updateParticipantEvent(ParticipantEventObject participantEventObject) throws AppException{
		ParticipantEventObject newParticipantEventObject = getParticipantEvent(participantEventObject.getParticipantEventId()); // This call will make sure cache/db are in sync
		participantEventObject = updateParticipantEventAgeCategory(participantEventObject);
		Integer i = (Integer)DBUtil.update(participantEventObject);
		DebugHandler.fine("i: " +  i);
		ParticipantEventObject[] participantEventObjectArr = getAllParticipantEvents();
		if ( participantEventObjectArr == null )
			return null;
		for ( int idx = 0; idx < participantEventObjectArr.length; idx++ ) {
			if ( participantEventObjectArr[idx] != null ) {
				if ( participantEventObjectArr[idx].getParticipantEventId() == participantEventObject.getParticipantEventId() ) {
					DebugHandler.debug("Found ParticipantEvent " + participantEventObject.getParticipantEventId());
					participantEventObjectArr[idx] = (ParticipantEventObject)participantEventObject.clone();
					Util.putInCache(PARTICIPANTEVENT, participantEventObjectArr);
				}
			}
		}
		return i;
	}
	
	
	/**
	 *
	 * Implementation to delete the <code>ParticipantEventObject</code> in the underlying datasource.
	 *
	 * @param participantEventObject	 ParticipantEventObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
	
	public Integer deleteParticipantEvent(ParticipantEventObject participantEventObject) throws AppException{
	ParticipantEventObject newParticipantEventObject = getParticipantEvent(participantEventObject.getParticipantEventId()); // This call will make sure cache/db are in sync
	ParticipantEventObject[] participantEventObjectArr = getAllParticipantEvents();
	Integer i = new Integer(0);
	if ( participantEventObject != null ) {
		i = (Integer)DBUtil.delete(participantEventObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( participantEventObjectArr != null ) {
			for (int idx = 0; idx < participantEventObjectArr.length; idx++ ) {
				if ( participantEventObjectArr[idx] != null && participantEventObjectArr[idx].getParticipantEventId() == participantEventObject.getParticipantEventId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (participantEventObjectArr.length - 1) )
						participantEventObjectArr[idx] = participantEventObjectArr[idx + 1]; // Move the array
					else
						participantEventObjectArr[idx] = null;
				}
				if ( participantEventObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(PARTICIPANTEVENT, participantEventObjectArr);
		}
		return i;
	}
}
