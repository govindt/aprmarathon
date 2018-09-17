/*
 * PersistentBeneficiary.java
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
import app.busobj.BeneficiaryObject;

/**
 * The persistent implementation of the BeneficiaryObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentBeneficiary extends PersistentObject {
    private BeneficiaryObject beneficiaryObject;
    
    
    /**
     * Constructs a Persistent Object for the BeneficiaryObject
     *
     * @param beneficiaryObject    the BeneficiaryObject 
     */
    
    public PersistentBeneficiary (BeneficiaryObject beneficiaryObject) {
        this.beneficiaryObject = beneficiaryObject;
    }
    
    
    /**
     * Returns the ArrayList of BeneficiaryObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of BeneficiaryObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT beneficiary_id, beneficiary_name, beneficiary_event from Beneficiary";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<BeneficiaryObject> result = (ArrayList<BeneficiaryObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of BeneficiaryObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of BeneficiaryObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
	public Object list(Object args) throws DBException {
		PreparedSQLStatement sql = new PreparedSQLStatement();
		String statement = "SELECT beneficiary_id, beneficiary_name, beneficiary_event from Beneficiary";
		int index = 1;
		BeneficiaryObject passedBeneficiaryObject = (BeneficiaryObject)args;
		boolean whereSpecified = false;

		if ( passedBeneficiaryObject.getBeneficiaryId() != 0 ) {
			statement += " where beneficiary_id = ?";
			whereSpecified = true;
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedBeneficiaryObject.getBeneficiaryId()), Types.INTEGER));
		}
		if ( ! passedBeneficiaryObject.getBeneficiaryName().equals("") ) {
			if ( ! whereSpecified ) {
				statement += " where beneficiary_name = ?";
				whereSpecified = true;
			} else
				statement += " and beneficiary_name = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++,  passedBeneficiaryObject.getBeneficiaryName(), Types.VARCHAR));
		}
		if ( passedBeneficiaryObject.getBeneficiaryEvent() != 0 ) {
			if ( ! whereSpecified ) {
				statement += " where beneficiary_event = ?";
				whereSpecified = true;
			} else
				statement += " and beneficiary_event = ?";
			sql.setStatement(statement);
			sql.setInParams(new SQLParam(index++, new Integer(passedBeneficiaryObject.getBeneficiaryEvent()), Types.INTEGER));
		}
		sql.setStatement(statement);
        
		DebugHandler.debug(statement);
		setSQLStatement(sql);
        
		@SuppressWarnings("unchecked")
		ArrayList<BeneficiaryObject> result = (ArrayList<BeneficiaryObject>) super.list();
        
		return result;
	}
    
    
    /**
     * Returns the ArrayList of one BeneficiaryObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     ArrayList of one BeneficiaryObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT beneficiary_id, beneficiary_name, beneficiary_event from Beneficiary where beneficiary_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(beneficiaryObject.getBeneficiaryId()), Types.INTEGER));
        setSQLStatement(sql);
        
        @SuppressWarnings("unchecked")
        ArrayList<BeneficiaryObject> result = (ArrayList<BeneficiaryObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the beneficiaryObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object insert() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement;
        int index = 1;

        if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
            statement = "INSERT INTO Beneficiary (beneficiary_id, beneficiary_name, beneficiary_event) VALUES(?, ?, ?) ";
            sql.setStatement(statement);
            sql.setInParams(new SQLParam(index++, new Integer(beneficiaryObject.getBeneficiaryId()), Types.INTEGER));
        } else {
            statement = "INSERT INTO Beneficiary (beneficiary_name, beneficiary_event) VALUES(?, ?) ";
            sql.setStatement(statement);
        }
        sql.setInParams(new SQLParam(index++,  beneficiaryObject.getBeneficiaryName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(beneficiaryObject.getBeneficiaryEvent()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the beneficiaryObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Beneficiary WHERE beneficiary_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(beneficiaryObject.getBeneficiaryId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the beneficiaryObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Beneficiary SET beneficiary_id = ?, beneficiary_name = ?, beneficiary_event = ? where beneficiary_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(beneficiaryObject.getBeneficiaryId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  beneficiaryObject.getBeneficiaryName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(beneficiaryObject.getBeneficiaryEvent()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(beneficiaryObject.getBeneficiaryId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a ArrayList of BeneficiaryObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a ArrayList of BeneficiaryObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        ArrayList<BeneficiaryObject> result = new ArrayList<BeneficiaryObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                BeneficiaryObject f = new BeneficiaryObject();
                f.setBeneficiaryId(rs.getInt(index++));
                f.setBeneficiaryName(rs.getString(index++));
                f.setBeneficiaryEvent(rs.getInt(index++));
                result.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * Returns a BeneficiaryObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a BeneficiaryObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
        @SuppressWarnings("unchecked")
            ArrayList<BeneficiaryObject> result = (ArrayList<BeneficiaryObject>) getResultObjects(rs);
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
