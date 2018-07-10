/*
 * UsersImpl.java
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai
 */

package app.busimpl;

import java.lang.*;
import java.util.*;
import core.util.Constants;
import core.util.DebugHandler;
import core.db.DBUtil;
import core.util.Util;
import core.util.AppException;
import app.busobj.UsersObject;
import app.businterface.UsersInterface;
import core.util.PasswordCrypt;
import app.util.AppConstants;

/**
 * The implementation of the UsersInterface
 * @version 1.0
 * @author Govind Thirumalai
 * @since 1.0
 */

public class UsersImpl implements UsersInterface  {
    private String USERS = "UsersInterface.getAllUsers";
    private static Random rn = new Random();

    private static int rand(int lo, int hi)
    {
	int n = hi - lo + 1;
	int i = rn.nextInt() % n;
	if (i < 0)
	    i = -i;
	return lo + i;
    }

    private String getRandomSalt() {
	int n = 2;
	byte b[] = new byte[n];
	for (int i = 0; i < n; i++)
	    b[i] = (byte)rand('a', 'z');
	return new String(b);
    }

    /**
     *
     * Implementation of the method does returns a crypted password given plain text password
     *
     * @param password String
     *
     * @return Returns Crypted Password
     *
     *  @throws AppException if the operation fails
     *
     */

    public String getCryptedPassword(String password, String salt) throws AppException {
	PasswordCrypt pCrypt = new PasswordCrypt();
	//String salt = password.substring(0,2);
	String cryptedPassword = PasswordCrypt.crypt(salt, password);
	return cryptedPassword;
    }

    /**
     *
     * Implementation of the method that returns the UsersObject from the underlying datasource.
     * given Users_id.
     *
     * @param Users_id     int
     *
     * @return      Returns the UsersObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public UsersObject getUser(int Users_id) throws AppException{
	UsersObject[] usersArr = getAllUsers();
	if ( usersArr == null )
	    return null;
	for ( int i = 0; i < usersArr.length; i++ ) {
	    if ( usersArr[i] == null ) { // Try database and add it to cache 
		UsersObject uObj = new UsersObject();
		uObj.setUsersId(Users_id);
		@SuppressWarnings("unchecked")
		Vector<UsersObject> v = (Vector)DBUtil.fetch(uObj);
		if ( v == null || v.size() == 0 )
		    return null;
		else {
		    usersArr[i] = (UsersObject)uObj.clone();
		    Util.putInCache(USERS, usersArr);
		}
	    }
	    if ( usersArr[i].getUsersId() == Users_id ) {
		DebugHandler.debug("Returning " + usersArr[i]);
		return (UsersObject)usersArr[i].clone();
	    }
	}
	return null;
    }
    
     /**
     *
     * Implementation of the method that returns the UsersObject from the underlying datasource.
     * given user_name.
     *
     * @param user_name	 String
     *
     * @return 	 Returns the UsersObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public UsersObject getUser(String user_name) throws AppException{
	UsersObject[] usersArr = getAllUsers();
	if ( usersArr == null )
	    return null;
	for ( int i = 0; i < usersArr.length; i++ ) {
	    if ( usersArr[i] == null ) { // Try db and add it
		UsersObject uObj = new UsersObject();
		uObj.setUsername(user_name);
		@SuppressWarnings("unchecked")
		Vector<UsersObject> v = (Vector)DBUtil.list(new UsersObject(),uObj);
		if ( v == null || v.size() == 0 )
		    return null;
		else {
		    usersArr[i] = (UsersObject)uObj.clone();
		    Util.putInCache(USERS, usersArr);
		}
	    }
	    if ( usersArr[i].getUsername().equals(user_name) ) {
		return (UsersObject)usersArr[i].clone();
	    }
	}
	return null;
    }

      /**
     *
     * Implementation of the method that returns the UsersObject from the underlying datasource.
     * given email.
     *
     * @param email	 String
     *
     * @return 	 Returns the Vector of UsersObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Vector<UsersObject> getUserFromEmail(String email) throws AppException{
	UsersObject[] usersArr = getAllUsers();
	Vector<UsersObject> v = new Vector<UsersObject>();
	if ( usersArr == null )
	    return null;
	for ( int i = 0; i < usersArr.length; i++ ) {
	    if ( usersArr[i] == null ) 
		return v;
	    if ( usersArr[i].getEmail().equalsIgnoreCase(email) ) {
		v.addElement((UsersObject)usersArr[i].clone());
	    }
	}
	return v;
    }
    
    /**
     *
     * Implementation of the method that returns the UsersObject from the underlying datasource.
     * given user_name and password.
     *
     * @param user_name	 String
     * @param password	 String
     *
     * @return 	 Returns the UsersObject
     *
     * @throws AppException if the operation fails
     *
     */

    public UsersObject authenticate(String user_name, String password) throws AppException{
	if (user_name == null || password == null)
	    return null;
	UsersObject usersObject = getUser(user_name);
	DebugHandler.debug("usersObject: " +  usersObject);
	if ( usersObject == null )
	    return null;
	else {
	    String dbPassword = usersObject.getPassword();
	    if ( dbPassword != null && ! dbPassword.equals("")) {
		String cryptedPassword = getCryptedPassword(password, dbPassword.substring(0,2));
		if ( cryptedPassword.equals(dbPassword) )
		    return usersObject;
		else
		    return null;
	    }
	    else
		return null;
	}
    }
    
    
    /**
     *
     * Implementation that returns all the <code>UsersObjects</code> from the underlying datasource.
     *
     * @return      Returns an Array of <code>UsersObject</code>
     *
     * @throws AppException if the operation fails
     *
     */
    
    public UsersObject[] getAllUsers() throws AppException{
	UsersObject usersObject = new UsersObject();
	String key = USERS;
	UsersObject[] utArr = (UsersObject[])Util.getAppCache().get(key);
	if ( utArr == null ) {
	    DebugHandler.debug("Getting users from database");
	    @SuppressWarnings("unchecked")
	    Vector<UsersObject> v = (Vector)DBUtil.list(usersObject);
	    DebugHandler.debug(":v: " +  v);
	    if ( v == null )
		return null;
	    utArr = new UsersObject[v.size()];
	    for ( int idx = 0; idx < v.size(); idx++ ) {
		utArr[idx] = v.elementAt(idx);
	    }
	    Util.putInCache(key, utArr);
	}
	return utArr;
    }
    
    
    /**
     *
     * Implementation to add the <code>UsersObject</code> to the underlying datasource.
     *
     * @param usersObject     UsersObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer addUsers(UsersObject usersObject) throws AppException{
	String password = usersObject.getPassword();
	String key = USERS;
	
	UsersObject buf = getUser(usersObject.getUsername());
	if ( buf != null )
	    throw new AppException("User already exists");
	if ( AppConstants.DB_TYPE.equalsIgnoreCase(Constants.ORACLE) ) {
	    long l = DBUtil.getNextId("users_seq");
	    usersObject.setUsersId((int)l);
	}
	usersObject.setPassword(getCryptedPassword(password, getRandomSalt()));
        Integer i = (Integer)DBUtil.insert(usersObject);
        DebugHandler.fine("i: " +  i);	
	buf = new UsersObject();
	buf.setUsername(usersObject.getUsername());

	@SuppressWarnings("unchecked")
	Vector<UsersObject> v = (Vector)DBUtil.list(usersObject, buf);
	if ( v != null && v.size() > 0 )
	    usersObject = v.elementAt(0);
	UsersObject[] usersArr = getAllUsers();
	boolean foundSpace = false;

	for ( int idx = 0; idx < usersArr.length; idx++ ) {
	    if ( usersArr[idx] == null ) {
		usersArr[idx] = (UsersObject)usersObject.clone();
		foundSpace = true;
		break;
	    }
	}
	if ( foundSpace == false ) {
	    UsersObject[] newUserArr = new UsersObject[usersArr.length + 1];
	    int idx = 0;
	    for ( idx = 0; idx < usersArr.length; idx++ ) {
		newUserArr[idx] = (UsersObject)usersArr[idx].clone();
	    }
	    newUserArr[idx] = (UsersObject)usersObject.clone();
	    Util.putInCache(USERS, newUserArr);
	} else 
	    Util.putInCache(USERS, usersArr);
	
        return i;
    }
    
    
    /**
     *
     * Implementation to update the <code>UsersObject</code> in the underlying datasource.
     *
     * @param usersObject     UsersObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer updateUsers(UsersObject usersObject) throws AppException{
	String password = usersObject.getPassword();
	UsersObject newUsersObject = getUser(usersObject.getUsersId()); // Check to make sure cache/db are in sync
	usersObject.setPassword(getCryptedPassword(password, getRandomSalt()));
	Integer i = (Integer)DBUtil.update(usersObject);
	DebugHandler.debug("i: " +  i);
	UsersObject[] usersArr = getAllUsers();
	if ( usersArr == null )
	    return null;
	for ( int idx = 0; idx < usersArr.length; idx++ ) {
	    if ( usersArr[idx] != null ) {
		if ( usersArr[idx].getUsersId() == usersObject.getUsersId() ) {
		    DebugHandler.debug("Found User " + usersObject.getUsersId());
		    usersArr[idx] = (UsersObject)usersObject.clone();
		    Util.putInCache(USERS, usersArr);
		    break;
		}
	    }
	}
	return i;
    }
    
    
    /**
     *
     * Implementation to delete the <code>UsersObject</code> in the underlying datasource.
     *
     * @param usersObject     UsersObject
     *
     * @throws AppException if the operation fails
     *
     */
    
    public Integer deleteUsers(UsersObject usersObject) throws AppException{
	UsersObject[] usersArr = getAllUsers();
	UsersObject uObj = getUser(usersObject.getUsersId());   // This call will make sure cache/db are in sync
	Integer i = new Integer(0);
	if ( uObj != null ) { // Found in cache as well as db
	    i = (Integer)DBUtil.delete(usersObject);
	    DebugHandler.debug("i: " +  i);
	    boolean found = false;

	    if ( usersArr != null ) {		
		for ( int idx = 0; idx < usersArr.length; idx++ ) {
		    if ( usersArr[idx] != null && usersArr[idx].getUsersId() == usersObject.getUsersId()) {
			found = true;
		    }
		    if (found) {
			if ( idx != (usersArr.length - 1) )
				usersArr[idx] = usersArr[idx + 1]; // Move the array
			else
				usersArr[idx] = null;
		    }
		    if ( usersArr[idx] == null )
			break;
		}
	    }
	    if ( found )
		Util.putInCache(USERS, usersArr);
	}
	return i;
    }
}
