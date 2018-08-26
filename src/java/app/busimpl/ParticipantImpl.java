/*
 * ParticipantImpl.java
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
import app.busobj.ParticipantObject;
import app.businterface.ParticipantInterface;
import app.util.AppConstants;

/**
 * The implementation of the ParticipantInterface
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class ParticipantImpl implements ParticipantInterface  {
    private String PARTICIPANT = "ParticipantInterface.getAllParticipant";
    
    /**
     *
     * Implementation that returns the ParticipantObject given a ParticipantObject filled with values that will be used for query from the underlying datasource.
     *
     * @param participant_obj	ParticipantObject
     *
     * @return      Returns the ArrayList of ParticipantObjects
     *
     * @throws AppException if the underlying operation fails
     *
     */
    
    public ArrayList<ParticipantObject> getParticipants(ParticipantObject participant_obj) throws AppException{
	ParticipantObject[] participantObjectArr = getAllParticipants();
	ArrayList<ParticipantObject> v = new ArrayList<ParticipantObject>();
	if ( participantObjectArr == null )
		return null;
	for ( int i = 0; i < participantObjectArr.length; i++ ) {
		if ( participantObjectArr[i] != null ) {
			if ( participant_obj.getParticipantId() == Constants.GET_ALL ) {
				v.add((ParticipantObject)participantObjectArr[i].clone());
			} else {
				if ( (participant_obj.getParticipantId() != 0 && participant_obj.getParticipantId() == participantObjectArr[i].getParticipantId())
 || (participant_obj.getParticipantFirstName() != null && participant_obj.getParticipantFirstName().equals(participantObjectArr[i].getParticipantFirstName()))
 || (participant_obj.getParticipantMiddleName() != null && participant_obj.getParticipantMiddleName().equals(participantObjectArr[i].getParticipantMiddleName()))
 || (participant_obj.getParticipantLastName() != null && participant_obj.getParticipantLastName().equals(participantObjectArr[i].getParticipantLastName()))
 || (participant_obj.getParticipantGender() != 0 && participant_obj.getParticipantGender() == participantObjectArr[i].getParticipantGender())
 || (participant_obj.getParticipantDateOfBirth() != null && participant_obj.getParticipantDateOfBirth().equals(participantObjectArr[i].getParticipantDateOfBirth()))
 || (participant_obj.getParticipantAgeCategory() != 0 && participant_obj.getParticipantAgeCategory() == participantObjectArr[i].getParticipantAgeCategory())
 || (participant_obj.getParticipantTShirtSize() != 0 && participant_obj.getParticipantTShirtSize() == participantObjectArr[i].getParticipantTShirtSize())
 || (participant_obj.getParticipantBloodGroup() != 0 && participant_obj.getParticipantBloodGroup() == participantObjectArr[i].getParticipantBloodGroup())
 || (participant_obj.getParticipantCellPhone() != null && participant_obj.getParticipantCellPhone().equals(participantObjectArr[i].getParticipantCellPhone()))
 || (participant_obj.getParticipantEmail() != null && participant_obj.getParticipantEmail().equals(participantObjectArr[i].getParticipantEmail()))
) {
					v.add((ParticipantObject)participantObjectArr[i].clone());
				}
			}
		}
	}
	DebugHandler.finest("v: " + v);
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the ParticipantObject from the underlying datasource.
     * given participant_id.
     *
     * @param participant_id     int
     *
     * @return      Returns the ParticipantObject
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
     * @return      Returns an Array of <code>ParticipantObject</code>
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
     * @param participantObject     ParticipantObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addParticipant(ParticipantObject participantObject) throws AppException{
		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			long l = DBUtil.getNextId("Participant_seq");
			participantObject.setParticipantId((int)l);
		}
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
     * @param participantObject     ParticipantObject
     *
     * @throws AppException if the operation fails
     *
     */
    
	public Integer updateParticipant(ParticipantObject participantObject) throws AppException{
		ParticipantObject newParticipantObject = getParticipant(participantObject.getParticipantId()); // This call will make sure cache/db are in sync
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
     * @param participantObject     ParticipantObject
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
