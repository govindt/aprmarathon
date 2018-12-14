/*
 * ParticipantEventInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.ParticipantEventObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface ParticipantEventInterface {
	
	public ParticipantEventObject updateParticipantEventAgeCategory(ParticipantEventObject pObj) throws AppException;
	
	/**
	 *
	 * Interface that returns the ParticipantEventObject given a ParticipantEventObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param participantevent_obj	ParticipantEventObject
	 *
	 * @return	  Returns the ArrayList of ParticipantEventObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public ArrayList<ParticipantEventObject> getParticipantEvents(ParticipantEventObject participantevent_obj) throws AppException;
	
	/**
	 *
	 * Interface that returns the ParticipantEventObject given participant_event_id from the underlying datasource.
	 *
	 * @param participant_event_id	 int
	 *
	 * @return	  Returns the ParticipantEventObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public ParticipantEventObject getParticipantEvent(int participant_event_id) throws AppException;
	
	/**
	 *
	 * Interface that returns all the <code>ParticipantEventObject</code> from the underlying datasource.
	 *
	 * @return	  Returns an Array of <code>ParticipantEventObject</code>
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public ParticipantEventObject[] getAllParticipantEvents() throws AppException;
	
	/**
	 *
	 * Interface to add the <code>ParticipantEventObject</code> to the underlying datasource.
	 *
	 * @param participantEventObject	 ParticipantEventObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer addParticipantEvent(ParticipantEventObject participantEventObject) throws AppException;
	
	/**
	 *
	 * Interface to update the <code>ParticipantEventObject</code> in the underlying datasource.
	 *
	 * @param participantEventObject	 ParticipantEventObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer updateParticipantEvent(ParticipantEventObject participantEventObject) throws AppException;
	
	/**
	 *
	 * Interface to delete the <code>ParticipantEventObject</code> in the underlying datasource.
	 *
	 * @param participantEventObject	 ParticipantEventObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer deleteParticipantEvent(ParticipantEventObject participantEventObject) throws AppException;
}
