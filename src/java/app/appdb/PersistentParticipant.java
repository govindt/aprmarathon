/*
 * PersistentParticipant.java
 *
 * APR Marathon Registration App Project
 *
 * Author: Govind Thirumalai
 */

package app.appdb;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import core.db.PersistentObject;
import core.db.SQLParam;
import core.db.SQLStatement;
import core.db.PreparedSQLStatement;
import core.db.CallableSQLStatement;
import core.db.DBException;
import core.util.DebugHandler;
import core.util.Constants;
import app.util.AppConstants;
import app.busobj.ParticipantObject;

/**
 * The persistent implementation of the ParticipantObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentParticipant extends PersistentObject {
	private ParticipantObject participantObject;
	
	
	/**
	 * Constructs a Persistent Object for the ParticipantObject
	 *
	 * @param participantObject	the ParticipantObject 
	 */
	
	public PersistentParticipant (ParticipantObject participantObject) {
		this.participantObject = participantObject;
	}
	
	
	/**
	 * Returns the ArrayList of ParticipantObject.
	 * It is Usually all the rows in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	ArrayList of ParticipantObject 
	 *
	 * @throws	DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT participant_id, participant_first_name, participant_middle_name, participant_last_name, participant_gender, participant_date_of_birth, participant_t_shirt_size, participant_blood_group, participant_cell_phone, participant_email, participant_group from Participant";
		int index = 1;
		sql.setStatement(statement);
		
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<ParticipantObject> result = (ArrayList<ParticipantObject>) super.list();
		
		return result;
	}
	
	
	/**
	 * Returns the ArrayList of ParticipantObjects.
	 * It is Usually all the rows that match the criteria in the database.
	 * This calls getResultObjects method in the super class.
	 *
	 * @return	 ArrayList of ParticipantObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultObjects(ResultSet)
	 */
	
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT participant_id, participant_first_name, participant_middle_name, participant_last_name, participant_gender, participant_date_of_birth, participant_t_shirt_size, participant_blood_group, participant_cell_phone, participant_email, participant_group from Participant";
		int index = 1;
		ParticipantObject passedParticipantObject = (ParticipantObject)args;
		boolean whereSpecified = false;

		if ( passedParticipantObject.getParticipantId() != 0 ) {
			statement += " where participant_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedParticipantObject.getParticipantId()), Types.INTEGER));
		}
		if ( ! passedParticipantObject.getParticipantFirstName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where participant_first_name = ?";
				whereSpecified = true;
			} else
				statement += " and participant_first_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedParticipantObject.getParticipantFirstName(), Types.VARCHAR));
		}
		if ( ! passedParticipantObject.getParticipantMiddleName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where participant_middle_name = ?";
				whereSpecified = true;
			} else
				statement += " and participant_middle_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedParticipantObject.getParticipantMiddleName(), Types.VARCHAR));
		}
		if ( ! passedParticipantObject.getParticipantLastName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where participant_last_name = ?";
				whereSpecified = true;
			} else
				statement += " and participant_last_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedParticipantObject.getParticipantLastName(), Types.VARCHAR));
		}
		if ( passedParticipantObject.getParticipantGender() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where participant_gender = ?";
				whereSpecified = true;
			} else
				statement += " and participant_gender = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedParticipantObject.getParticipantGender()), Types.INTEGER));
		}
		if ( passedParticipantObject.getParticipantDateOfBirth() != null ) {
			if ( ! whereSpecified ) {
				statement += " where participant_date_of_birth = ?";
				whereSpecified = true;
			} else
				statement += " and participant_date_of_birth = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedParticipantObject.getParticipantDateOfBirth(), Types.TIMESTAMP));
		}
		if ( passedParticipantObject.getParticipantTShirtSize() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where participant_t_shirt_size = ?";
				whereSpecified = true;
			} else
				statement += " and participant_t_shirt_size = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedParticipantObject.getParticipantTShirtSize()), Types.INTEGER));
		}
		if ( passedParticipantObject.getParticipantBloodGroup() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where participant_blood_group = ?";
				whereSpecified = true;
			} else
				statement += " and participant_blood_group = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedParticipantObject.getParticipantBloodGroup()), Types.INTEGER));
		}
		if ( ! passedParticipantObject.getParticipantCellPhone().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where participant_cell_phone = ?";
				whereSpecified = true;
			} else
				statement += " and participant_cell_phone = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedParticipantObject.getParticipantCellPhone(), Types.VARCHAR));
		}
		if ( ! passedParticipantObject.getParticipantEmail().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where participant_email = ?";
				whereSpecified = true;
			} else
				statement += " and participant_email = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedParticipantObject.getParticipantEmail(), Types.VARCHAR));
		}
		if ( passedParticipantObject.getParticipantGroup() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where participant_group = ?";
				whereSpecified = true;
			} else
				statement += " and participant_group = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedParticipantObject.getParticipantGroup()), Types.INTEGER));
		}
		sql.setStatement(statement);
		
		DebugHandler.debug(statement);
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<ParticipantObject> result = (ArrayList<ParticipantObject>) super.list();
		
		return result;
	}
	
	
	/**
	 * Returns the ArrayList of one ParticipantObject.
	 * It is Usually the row that matches primary key.
	 * This calls getResultSetObject method in the super class.
	 *
	 * @return	 ArrayList of one ParticipantObject 
	 *
	 * @throws	 DBException	 If a database error occurs
	 *
	 * @see	 #getResultSetObject(ResultSet)
	 */
	
	public Object fetch() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT participant_id, participant_first_name, participant_middle_name, participant_last_name, participant_gender, participant_date_of_birth, participant_t_shirt_size, participant_blood_group, participant_cell_phone, participant_email, participant_group from Participant where participant_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantId()), Types.INTEGER));
		setSQLStatement(sql);
		
		@SuppressWarnings("unchecked")
		ArrayList<ParticipantObject> result = (ArrayList<ParticipantObject>) super.fetch();
		
		return result;
	}
	
	
	/**
	 *
	 * Inserts a row in the database.  The values
	 * are got from the participantObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object insert() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement;
		int index = 1;

		if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
			statement = "INSERT INTO Participant (participant_id, participant_first_name, participant_middle_name, participant_last_name, participant_gender, participant_date_of_birth, participant_t_shirt_size, participant_blood_group, participant_cell_phone, participant_email, participant_group) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantId()), Types.INTEGER));
		} else {
			statement = "INSERT INTO Participant (participant_first_name, participant_middle_name, participant_last_name, participant_gender, participant_date_of_birth, participant_t_shirt_size, participant_blood_group, participant_cell_phone, participant_email, participant_group) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ";
			sql.setStatement(statement);
		}
		sql.setInParams(new SQLParam(index++,  participantObject.getParticipantFirstName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  participantObject.getParticipantMiddleName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  participantObject.getParticipantLastName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantGender()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++,  participantObject.getParticipantDateOfBirth(), Types.TIMESTAMP));
		sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantTShirtSize()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantBloodGroup()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++,  participantObject.getParticipantCellPhone(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  participantObject.getParticipantEmail(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantGroup()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.insert();
		
		return result;
	}
	
	
	/**
	 *
	 * Deletes a row in the database. The key is 
	 * in the participantObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object delete() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "DELETE FROM Participant WHERE participant_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.delete();
		
		return result;
	}
	
	
	/**
	 *
	 * Updates a row in the database. The values are 
	 * got from the participantObject.
	 * Returns an Integer Object with value 0 on success
	 * and -1 on faliure.
	 *
	 * @return	  Returns an Integer indicating success/failure of the database operation
	 *
	 * @throws	 DBException	 If a database error occurs
	 */
	
	public Object update() throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "UPDATE Participant SET participant_id = ?, participant_first_name = ?, participant_middle_name = ?, participant_last_name = ?, participant_gender = ?, participant_date_of_birth = ?, participant_t_shirt_size = ?, participant_blood_group = ?, participant_cell_phone = ?, participant_email = ?, participant_group = ? where participant_id = ? ";
		int index = 1;
		sql.setStatement(statement);
		sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantId()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++,  participantObject.getParticipantFirstName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  participantObject.getParticipantMiddleName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  participantObject.getParticipantLastName(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantGender()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++,  participantObject.getParticipantDateOfBirth(), Types.TIMESTAMP));
		sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantTShirtSize()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantBloodGroup()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++,  participantObject.getParticipantCellPhone(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++,  participantObject.getParticipantEmail(), Types.VARCHAR));
		sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantGroup()), Types.INTEGER));
		sql.setInParams(new SQLParam(index++, new Integer(participantObject.getParticipantId()), Types.INTEGER));
		setSQLStatement(sql);
		
		Integer result = (Integer) super.update();
		
		return result;
	}
	
	
	/**
	 *
	 * Returns a ArrayList of ParticipantObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 * This is used by the list method.
	 *
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a ArrayList of ParticipantObject from the ResultSet.
	 *
	 * @see	 #list()
	 *
	 */
	
	public Object getResultObjects(ResultSet rs) {
		ArrayList<ParticipantObject> result = new ArrayList<ParticipantObject>();
		try {
			while(rs.next()) {
				int index = 1;
				ParticipantObject f = new ParticipantObject();
				f.setParticipantId(rs.getInt(index++));
				f.setParticipantFirstName(rs.getString(index++));
				f.setParticipantMiddleName(rs.getString(index++));
				f.setParticipantLastName(rs.getString(index++));
				f.setParticipantGender(rs.getInt(index++));
				f.setParticipantDateOfBirth(rs.getDate(index++));
				f.setParticipantTShirtSize(rs.getInt(index++));
				f.setParticipantBloodGroup(rs.getInt(index++));
				f.setParticipantCellPhone(rs.getString(index++));
				f.setParticipantEmail(rs.getString(index++));
				f.setParticipantGroup(rs.getInt(index++));
				result.add(f);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 *
	 * Returns a ParticipantObject from the ResultSet. The values for 
	 * each object is got from the ResultSet.
	 *
	 * This is used by the fetch method.
	 * @param rs	  the ResultSet.
	 *
	 * @return	  Returns a ParticipantObject from the ResultSet.
	 *
	 * @see	 #fetch()
	 *
	 */
	
	public Object getResultSetObject(ResultSet rs) {
		try {
			@SuppressWarnings("unchecked")
			ArrayList<ParticipantObject> result = (ArrayList<ParticipantObject>) getResultObjects(rs);
			return result.get(0);
		} catch (Exception e) {
			return null;
		}
	}
}
	
