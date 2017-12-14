/*
 * @(#)SQLParam.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.db;

import java.sql.Types;
import java.util.Date;

/**
 * Models a SQL statement parameter for describing an input or output
 * parameter.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class SQLParam {

    private int index;
    private Object value;
    private int sqlType;
    private int scale;

    /**
     * Constructs this object with the specified parameters.
     *
     * @param index     parameter index
     * @param value     parameter value
     * @param sqlType   SQL type of the corresponding database field
     * @param scale     scale for the numeric parameters
     */

    public SQLParam(int index,
                    Object value,
                    int sqlType,
                    int scale) {

        this.index = index;
        setValue(value);
        this.sqlType = sqlType;
        this.scale = scale;
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param index     parameter index
     * @param value     parameter value
     * @param sqlType   SQL type of the corresponding database field
     */

    public SQLParam(int index,
                    Object value,
                    int sqlType) {

        this(index, value, sqlType, 0);
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param index     parameter index
     * @param sqlType   SQL type of the corresponding database field
     * @param scale     scale for the numeric parameters
     */

    public SQLParam(int index,
                    int sqlType,
                    int scale) {

        this.index = index;
        this.sqlType = sqlType;
        this.scale = scale;
    }

    /**
     * Constructs this object with the specified parameters.
     *
     * @param index     parameter index
     * @param sqlType   SQL type of the corresponding database field
     */

    public SQLParam(int index,
                    int sqlType) {

        this(index, null, sqlType, 0);
    }

    /*
     * Constructs this object with the specified parameters.
     *
     * @param index     parameter index
     * @param value     parameter value
     */

    private SQLParam(int index,
                     Object value) {

        this(index, value, toSQLType(value), 0);
    }

    /**
     * The no-argument constructor.
     */

    public SQLParam() {

        this(0, null, Types.NULL, 0);
    }

    /**
     * Returns the SQL type for the specified object's type. Returns
     * java.sql.Types.OTHER if the mapping is not found.
     */

    public static int toSQLType(Object obj) {

        if (obj instanceof String)
            return Types.VARCHAR;
        else if (obj instanceof Character)
            return Types.CHAR;
        else if (obj instanceof Integer)
            return Types.INTEGER;
        else if (obj instanceof Long)
            return Types.BIGINT;
        else if (obj instanceof Short)
            return Types.SMALLINT;
        else if (obj instanceof Double)
            return Types.DOUBLE;
        else if (obj instanceof Float)
            return Types.FLOAT;
        else if (obj instanceof Number)
            return Types.NUMERIC;
        else if (obj instanceof Date)
            return Types.TIMESTAMP;
        else if (obj instanceof StringBuffer)
            return Types.VARCHAR;
        else if (obj instanceof Byte)
            return Types.BINARY;
        else
            return Types.OTHER;
    }

    /**
     * Sets the index to the specified value.
     *
     * @param index        parameter index
     */

    public void setIndex(int index) {

        this.index = index;
    }

    /**
     * Sets the value to the specified value. Converts the value to the
     * type java.sql.Timestamp if it is any kind of a Date.
     *
     * @param value        parameter index
     */

    public void setValue(Object value) {

        if ((value != null) && (value instanceof Date)) {
            this.value = new java.sql.Timestamp(((Date) value).getTime());
        } else {
            this.value = value;
        }
    }

    /**
     * Sets the SQL type to the specified value.
     *
     * @param sqlType    SQL type of the corresponding database field
     */

    public void setSQLType(int sqlType) {

        this.sqlType = sqlType;
    }

    /**
     * Sets the scale to the specified value.
     *
     * @param scale      scale for the numeric parameters
     */

    public void setScale(int scale) {

        this.scale = scale;
    }

    /**
     * Returns the parameter index.
     *
     * @return    the parameter index
     */

    public int getIndex() {

        return index;
    }

    /**
     * Returns the parameter value.
     *
     * @return    the parameter value
     */

    public Object getValue() {

        return value;
    }

    /**
     * Returns the SQL type.
     *
     * @return    SQL type of the corresponding database field
     */

    public int getSQLType() {

        return sqlType;
    }

    /**
     * Returns the scale.
     *
     * @return    scale for the numeric parameters
     */

    public int getScale() {

        return scale;
    }
}
