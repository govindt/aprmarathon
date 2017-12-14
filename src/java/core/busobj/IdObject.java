/*
 * @(#)IdObject.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.busobj;

// Import statements

/**
 * Represents an object id in the application that gets generated from a
 * sequence table in the database.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class IdObject {

    protected long id;
    protected String tableName;

    /**
     * Constructs this object with the specified parameters.
     *
     * @param tableName        the table to be used for generating the id
     */

    public IdObject(String tableName) {

        this.tableName = tableName;
    }

    /*
     * The no-argument constructor; prevents no-argument instantiation.
     */

    private IdObject() {

    }

    /**
     * Sets the id.
     *
     * @param id        object id
     */

    public void setId(long id) {

        this.id = id;
    }

    /**
     * Returns the id.
     *
     * @return    object id
     */

    public long getId() {

        return id;
    }

    /**
     * Returns the table name.
     *
     * @return    table name
     */

    public String getTableName() {

        return tableName;
    }

    /**
     * Compares two Objects for equality.
     *
     * @param obj        the reference object with which to compare
     * @return true      if this object is the same as the obj argument;
     *                   false otherwise.
     */

    public boolean equals(Object obj) {

        try {
            IdObject other = (IdObject) obj;

            if ((other == this) || (getId() == other.getId())) {
                return true;
            }
        } catch (Exception e) {
            return false;
        }

        return false;
    }

    /**
     * Returns the String representation for this object.
     *
     * @return    the object's String representation
     */

    public String toString() {

        return String.valueOf(id);
    }
}
