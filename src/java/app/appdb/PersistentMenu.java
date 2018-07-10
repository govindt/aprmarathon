/*
 * PersistentMenu.java
 *
 * Project Name Project
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
import app.busobj.MenuObject;

/**
 * The persistent implementation of the MenuObject
 * @version 1.0
 * @author 
 * @since 1.0
 */

public class PersistentMenu extends PersistentObject {
    private MenuObject menuObject;
    
    
    /**
     * Constructs a Persistent Object for the MenuObject
     *
     * @param menuObject    the MenuObject 
     */
    
    public PersistentMenu (MenuObject menuObject) {
        this.menuObject = menuObject;
    }
    
    
    /**
     * Returns the ArrayList of MenuObject.
     * It is Usually all the rows in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of MenuObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
	String statement = "SELECT menu_id, menu_name, url, menu_order, parent_menu_id, role_id from Menu order by parent_menu_id, menu_order";
        int index = 1;
        sql.setStatement(statement);
        
        setSQLStatement(sql);
        
	@SuppressWarnings("unchecked")
        ArrayList<MenuObject> result = (ArrayList<MenuObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of MenuObjects.
     * It is Usually all the rows that match the criteria in the database.
     * This calls getResultObjects method in the super class.
     *
     * @return     ArrayList of MenuObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultObjects(ResultSet)
     */
    
    public Object list(Object args) throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT menu_id, menu_name, url, menu_order, parent_menu_id, role_id from Menu";
        int index = 1;
        MenuObject passedMenuObject = (MenuObject)args;
        boolean whereSpecified = false;

        if ( passedMenuObject.getMenuId() != 0 ) {
	    statement += " where menu_id = ?";
	    whereSpecified = true;
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedMenuObject.getMenuId()), Types.INTEGER));
	}
        if ( ! passedMenuObject.getMenuName().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where menu_name = ?";
		whereSpecified = true;
	    } else
		statement += " and menu_name = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedMenuObject.getMenuName(), Types.VARCHAR));
	}
        if ( ! passedMenuObject.getUrl().equals("") ) {
	    if ( ! whereSpecified ) {
		statement += " where url = ?";
		whereSpecified = true;
	    } else
		statement += " and url = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++,  passedMenuObject.getUrl(), Types.VARCHAR));
	}
        if ( passedMenuObject.getMenuOrder() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where menu_order = ?";
		whereSpecified = true;
	    } else
		statement += " and menu_order = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedMenuObject.getMenuOrder()), Types.INTEGER));
	}
        if ( passedMenuObject.getParentMenuId() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where parent_menu_id = ?";
		whereSpecified = true;
	    } else
		statement += " and parent_menu_id = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedMenuObject.getParentMenuId()), Types.INTEGER));
	}
        if ( passedMenuObject.getRoleId() != 0 ) {
	    if ( ! whereSpecified ) {
		statement += " where role_id = ?";
		whereSpecified = true;
	    } else
		statement += " and role_id = ?";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(passedMenuObject.getRoleId()), Types.INTEGER));
	}
        sql.setStatement(statement);
        
        DebugHandler.debug(statement);
        setSQLStatement(sql);
        
	@SuppressWarnings("unchecked")
        ArrayList<MenuObject> result = (ArrayList<MenuObject>) super.list();
        
        return result;
    }
    
    
    /**
     * Returns the ArrayList of one MenuObject.
     * It is Usually the row that matches primary key.
     * This calls getResultSetObject method in the super class.
     *
     * @return     ArrayList of one MenuObject 
     *
     * @throws     DBException     If a database error occurs
     *
     * @see     #getResultSetObject(ResultSet)
     */
    
    public Object fetch() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "SELECT menu_id, menu_name, url, menu_order, parent_menu_id, role_id from Menu where menu_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(menuObject.getMenuId()), Types.INTEGER));
        setSQLStatement(sql);

	@SuppressWarnings("unchecked")
        ArrayList<MenuObject> result = (ArrayList<MenuObject>) super.fetch();
        
        return result;
    }
    
    
    /**
     *
     * Inserts a row in the database.  The values
     * are got from the menuObject.
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
	    statement = "INSERT INTO Menu (menu_id, menu_name, url, menu_order, parent_menu_id, role_id) VALUES(?, ?, ?, ?, ?, ?) ";
	    sql.setStatement(statement);
	    sql.setInParams(new SQLParam(index++, new Integer(menuObject.getMenuId()), Types.INTEGER));
	} else {
	    statement = "INSERT INTO Menu (menu_name, url, menu_order, parent_menu_id, role_id) VALUES(?, ?, ?, ?, ?) ";
	    sql.setStatement(statement);
	}
        sql.setInParams(new SQLParam(index++,  menuObject.getMenuName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  menuObject.getUrl(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(menuObject.getMenuOrder()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(menuObject.getParentMenuId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(menuObject.getRoleId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.insert();
        
        return result;
    }
    
    
    /**
     *
     * Deletes a row in the database. The key is 
     * in the menuObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object delete() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "DELETE FROM Menu WHERE menu_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(menuObject.getMenuId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.delete();
        
        return result;
    }
    
    
    /**
     *
     * Updates a row in the database. The values are 
     * got from the menuObject.
     * Returns an Integer Object with value 0 on success
     * and -1 on faliure.
     *
     * @return      Returns an Integer indicating success/failure of the database operation
     *
     * @throws     DBException     If a database error occurs
     */
    
    public Object update() throws DBException {
        PreparedSQLStatement sql = new PreparedSQLStatement();
        String statement = "UPDATE Menu SET menu_id = ?, menu_name = ?, url = ?, menu_order = ?, parent_menu_id = ?, role_id = ? where menu_id = ? ";
        int index = 1;
        sql.setStatement(statement);
        sql.setInParams(new SQLParam(index++, new Integer(menuObject.getMenuId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++,  menuObject.getMenuName(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++,  menuObject.getUrl(), Types.VARCHAR));
        sql.setInParams(new SQLParam(index++, new Integer(menuObject.getMenuOrder()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(menuObject.getParentMenuId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(menuObject.getRoleId()), Types.INTEGER));
        sql.setInParams(new SQLParam(index++, new Integer(menuObject.getMenuId()), Types.INTEGER));
        setSQLStatement(sql);
        
        Integer result = (Integer) super.update();
        
        return result;
    }
    
    
    /**
     *
     * Returns a ArrayList of MenuObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     * This is used by the list method.
     *
     * @param rs      the ResultSet.
     *
     * @return      Returns a ArrayList of MenuObject from the ResultSet.
     *
     * @see     #list()
     *
     */
    
    public Object getResultObjects(ResultSet rs) {
        ArrayList<MenuObject> result = new ArrayList<MenuObject>();
        
        try {
            while(rs.next()) {
                int index = 1;
                MenuObject f = new MenuObject();
                f.setMenuId(rs.getInt(index++));
                f.setMenuName(rs.getString(index++));
                f.setUrl(rs.getString(index++));
                f.setMenuOrder(rs.getInt(index++));
                f.setParentMenuId(rs.getInt(index++));
                f.setRoleId(rs.getInt(index++));
                result.add(f);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /**
     *
     * Returns a MenuObject from the ResultSet. The values for 
     * each object is got from the ResultSet.
     *
     * This is used by the fetch method.
     * @param rs      the ResultSet.
     *
     * @return      Returns a MenuObject from the ResultSet.
     *
     * @see     #fetch()
     *
     */
    
    public Object getResultSetObject(ResultSet rs) {
        try {
	    @SuppressWarnings("unchecked")
            ArrayList<MenuObject> result = (ArrayList<MenuObject>) getResultObjects(rs);
            return result.get(0);
        } catch (Exception e) {
            return null;
        }
    }
}
    
