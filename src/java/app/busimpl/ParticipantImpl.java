/*
 * ParticipantImpl.java
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
import app.busobj.ParticipantObject;
import app.busobj.AgeCategoryObject;
import app.busobj.RegistrantEventObject;
import app.busobj.EventObject;
import app.businterface.ParticipantInterface;
import app.businterface.RegistrantEventInterface;
import app.businterface.AgeCategoryInterface;
import app.businterface.EventInterface;
import app.util.AppConstants;

/**
 * The implementation of the ParticipantInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class ParticipantImpl implements ParticipantInterface  {
	private String PARTICIPANT = "ParticipantInterface.getAllParticipant";
	
	public ParticipantObject updateParticipantAgeCategory(ParticipantObject pObj) throws AppException {
		RegistrantEventInterface rEIf = new RegistrantEventImpl();
		RegistrantEventObject rEObj = rEIf.getRegistrantEvent(pObj.getParticipantGroup());
		int eventId = rEObj.getRegistrantEvent();
		EventInterface eIf = new EventImpl();
		EventObject eObj = eIf.getEvent(eventId);
		if ( eObj == null || eObj.getEventStartDate() == null )
			throw new AppException("Event not in DB or Event Start Date is null");
		if ( pObj.getParticipantDateOfBirth() == null )
			throw new AppException("Date of Birth is null for " + pObj);
		
		int years = Util.calculateAge(pObj.getParticipantDateOfBirth(), eObj.getEventStartDate());
		AgeCategoryInterface aCIf = new AgeCategoryImpl();
		AgeCategoryObject[] aCObjArr = aCIf.getAllAgeCategorys();
		for ( int i = 0; i < aCObjArr.length; i++) {
			if ( years <= aCObjArr[i].getMaxAge() && years >= aCObjArr[i].getMinAge() ) {
				ParticipantObject pObj1 = (ParticipantObject)pObj.clone();
				pObj1.setParticipantAgeCategory(aCObjArr[i].getAgeCategoryId());
				DebugHandler.info("DOB: " + pObj1.getParticipantDateOfBirth() + " Age : " + years + " Age Category: " + aCObjArr[i].getAgeCategory());
				return pObj1;
			}
		}
		
		return pObj;
	}
	
	/**
	 *
	 * Implementation that returns the ParticipantObject given a ParticipantObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param participant_obj	ParticipantObject
	 *
	 * @return	  Returns the ArrayList of ParticipantObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public ArrayList<ParticipantObject> getParticipants(ParticipantObject participant_obj) throws AppException{
		ParticipantObject[] participantObjectArr = getAllParticipants();
		if ( participant_obj.getParticipantId() == Constants.GET_ALL ) {
			if ( participantObjectArr == null )
				return null;
			ArrayList<ParticipantObject> v = new ArrayList<ParticipantObject>();
			for ( int i = 0; i < participantObjectArr.length; i++ ) {
				v.add((ParticipantObject)participantObjectArr[i].clone());
			}
			return v;
		}
		else {
			@SuppressWarnings("unchecked")
			ArrayList<ParticipantObject> v = (ArrayList<ParticipantObject>)DBUtil.list(participant_obj,participant_obj);
			DebugHandler.finest("v: " + v);
			return v;
		}
	}
	
	/**
	 *
	 * Implementation of the method that returns the ParticipantObject from the underlying datasource.
	 * given participant_id.
	 *
	 * @param participant_id	 int
	 *
	 * @return	  Returns the ParticipantObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
	
	public ParticipantObject getParticipant(int participant_id) throws AppException{
		ParticipantObject[] participantObjectArr = getAllParticipants();
		if ( participantObjectArr == null )
			return null;
		for ( int i = 0; i < participantObjectArr.length; i++ ) {
			if ( participantObjectArr[i] == null ) { // Try database and add to cache if found.
				ParticipantObject participantObj = new ParticipantObject();
				participantObj.setParticipantId(participant_id);
				@SuppressWarnings("unchecked")
				ArrayList<ParticipantObject> v = (ArrayList)DBUtil.fetch(participantObj);
				if ( v == null || v.size() == 0 )
					return null;
				else {
					participantObjectArr[i] = (ParticipantObject)participantObj.clone();
					Util.putInCache(PARTICIPANT, participantObjectArr);
				}
			}
			if ( participantObjectArr[i].getParticipantId() == participant_id ) {
				DebugHandler.debug("Returning " + participantObjectArr[i]);
				return (ParticipantObject)participantObjectArr[i].clone();
			}
		}
		return null;
	}
	
	
	/**
	 *
	 * Implementation that returns all the <code>ParticipantObjects</code> from the underlying datasource.
	 *
	 * @return	  Returns an Array of <code>ParticipantObject</code>
	 *
	 * @throws AppException if the operation fails
	 *
	 */
	
	public ParticipantObject[] getAllParticipants() throws AppException{
		ParticipantObject participantObject = new ParticipantObject();
		ParticipantObject[] participantObjectArr = (ParticipantObject[])Util.getAppCache().get(PARTICIPANT);
		if ( participantObjectArr == null ) {
			DebugHandler.info("Getting participant from database");
			@SuppressWarnings("unchecked")
			ArrayList<ParticipantObject> v = (ArrayList)DBUtil.list(participantObject);
			DebugHandler.finest(":v: " +  v);
			if ( v == null )
				return null;
			participantObjectArr = new ParticipantObject[v.size()];
			for ( int idx = 0; idx < v.size(); idx++ ) {
				participantObjectArr[idx] = v.get(idx);
			}
			Util.putInCache(PARTICIPANT, participantObjectArr);
		}
		return participantObjectArr;
	}
	
	
	/**
	 *
	 * Implementation to add the <code>ParticipantObject</code> to the underlying datasource.
	 *
	 * @param participantObject	 ParticipantObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
	
	public Integer addParticipant(ParticipantObject participantObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Participant_seq");
			participantObject.setParticipantId((int)l);
		}
		
		participantObject = updateParticipantAgeCategory(participantObject);
		Integer i = (Integer)DBUtil.insert(participantObject);
		DebugHandler.fine("i: " +  i);
	
		// Do for Non Oracle where there is auto increment
		if ( ! AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			participantObject.setParticipantId(i.intValue());
			DebugHandler.fine(participantObject);
		}
		ParticipantObject buf = new ParticipantObject();
		buf.setParticipantId(participantObject.getParticipantId());
		@SuppressWarnings("unchecked")
		ArrayList<ParticipantObject> v = (ArrayList)DBUtil.list(participantObject, buf);
		participantObject = v.get(0);
		ParticipantObject[] participantObjectArr = getAllParticipants();
		boolean foundSpace = false;

		for ( int idx = 0; idx < participantObjectArr.length; idx++ ) {
			if ( participantObjectArr[idx] == null ) {
				participantObjectArr[idx] = (ParticipantObject)participantObject.clone();
				foundSpace = true;
				break;
			}
		}
		if ( foundSpace == false ) {
			ParticipantObject[] newparticipantObjectArr = new ParticipantObject[participantObjectArr.length + 1];
			int idx = 0;
			for ( idx = 0; idx < participantObjectArr.length; idx++ ) {
				newparticipantObjectArr[idx] = (ParticipantObject)participantObjectArr[idx].clone();
			}
			newparticipantObjectArr[idx] = (ParticipantObject)participantObject.clone();
			Util.putInCache(PARTICIPANT, newparticipantObjectArr);
		} else {
			Util.putInCache(PARTICIPANT, participantObjectArr);
		}
		return i;
	}
	
	
	/**
	 *
	 * Implementation to update the <code>ParticipantObject</code> in the underlying datasource.
	 *
	 * @param participantObject	 ParticipantObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
	
	public Integer updateParticipant(ParticipantObject participantObject) throws AppException{
		ParticipantObject newParticipantObject = getParticipant(participantObject.getParticipantId()); // This call will make sure cache/db are in sync
		participantObject = updateParticipantAgeCategory(participantObject);
		Integer i = (Integer)DBUtil.update(participantObject);
		DebugHandler.fine("i: " +  i);
		ParticipantObject[] participantObjectArr = getAllParticipants();
		if ( participantObjectArr == null )
			return null;
		for ( int idx = 0; idx < participantObjectArr.length; idx++ ) {
			if ( participantObjectArr[idx] != null ) {
				if ( participantObjectArr[idx].getParticipantId() == participantObject.getParticipantId() ) {
					DebugHandler.debug("Found Participant " + participantObject.getParticipantId());
					participantObjectArr[idx] = (ParticipantObject)participantObject.clone();
					Util.putInCache(PARTICIPANT, participantObjectArr);
				}
			}
		}
		return i;
	}
	
	
	/**
	 *
	 * Implementation to delete the <code>ParticipantObject</code> in the underlying datasource.
	 *
	 * @param participantObject	 ParticipantObject
	 *
	 * @throws AppException if the operation fails
	 *
	 */
	
	public Integer deleteParticipant(ParticipantObject participantObject) throws AppException{
	ParticipantObject newParticipantObject = getParticipant(participantObject.getParticipantId()); // This call will make sure cache/db are in sync
	ParticipantObject[] participantObjectArr = getAllParticipants();
	Integer i = new Integer(0);
	if ( participantObject != null ) {
		i = (Integer)DBUtil.delete(participantObject);
		DebugHandler.fine("i: " +  i);
		boolean found = false;
		if ( participantObjectArr != null ) {
			for (int idx = 0; idx < participantObjectArr.length; idx++ ) {
				if ( participantObjectArr[idx] != null && participantObjectArr[idx].getParticipantId() == participantObject.getParticipantId() ) {
					found = true;
				}
				if ( found ) {
					if ( idx != (participantObjectArr.length - 1) )
						participantObjectArr[idx] = participantObjectArr[idx + 1]; // Move the array
					else
						participantObjectArr[idx] = null;
				}
				if ( participantObjectArr[idx] == null )
					break;
			}
		}
		if ( found )
			Util.putInCache(PARTICIPANT, participantObjectArr);
		}
		return i;
	}
}
