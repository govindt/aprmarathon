/*
 * ParticipantInterface.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.businterface;

import java.lang.*;
import java.util.*;
import core.util.AppException;
import app.busobj.ParticipantObject;

/**
 * The interface which encapsulates the access of database tables
 * in the database
 * @version 1.0
 * @author 
 * @since 1.0
 */

public interface ParticipantInterface {
	
	/**
	 *
	 * Interface that returns the ParticipantObject given a ParticipantObject filled with values that will be used for query from the underlying datasource.
	 *
	 * @param participant_obj	ParticipantObject
	 *
	 * @return	  Returns the ArrayList of ParticipantObjects
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public ArrayList<ParticipantObject> getParticipants(ParticipantObject participant_obj) throws AppException;
	
	/**
	 *
	 * Interface that returns the ParticipantObject given participant_id from the underlying datasource.
	 *
	 * @param participant_id	 int
	 *
	 * @return	  Returns the ParticipantObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public ParticipantObject getParticipant(int participant_id) throws AppException;
	
	/**
	 *
	 * Interface that returns all the <code>ParticipantObject</code> from the underlying datasource.
	 *
	 * @return	  Returns an Array of <code>ParticipantObject</code>
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public ParticipantObject[] getAllParticipants() throws AppException;
	
	/**
	 *
	 * Interface to add the <code>ParticipantObject</code> to the underlying datasource.
	 *
	 * @param participantObject	 ParticipantObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer addParticipant(ParticipantObject participantObject) throws AppException;
	
	/**
	 *
	 * Interface to update the <code>ParticipantObject</code> in the underlying datasource.
	 *
	 * @param participantObject	 ParticipantObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer updateParticipant(ParticipantObject participantObject) throws AppException;
	
	/**
	 *
	 * Interface to delete the <code>ParticipantObject</code> in the underlying datasource.
	 *
	 * @param participantObject	 ParticipantObject
	 *
	 * @throws AppException if the underlying operation fails
	 *
	 */
	
	public Integer deleteParticipant(ParticipantObject participantObject) throws AppException;
}
