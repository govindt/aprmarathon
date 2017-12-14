/*
 * @(#)TextareaElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import java.io.*;
import java.util.*;
import core.util.*;

/**
 * The TextareaElement class returns a string in an HTML <TEXTAREA></TEXTAREA> format.
 * <BR><BR>
 * Usage:
 * TextareaElement str = new TextareaElement("Hello World!", 20, 30, TextareaElement.virtual);
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: <TEXTAREA ROWS=20 COLS=30 WRAP>Hello World!</TEXTAREA>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new TextareaElement("Hello World!", 20, 30, TextareaElement.VIRTUAL).getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class TextareaElement extends HTMLElementObject { 

    private int rows;
    private int cols;
    private String wrap;

    /**
     * Textarea WRAP modes.
     */

    public static final String VIRTUAL = "virtual";
    public static final String PHYSICAL = "physical";
    public static final String SOFT = "SOFT";
    public static final String WRAP = "wrap";


    /**
     * Constructs this object,
     *
     */

    public TextareaElement() {

	super();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param name			the name of the textarea 
     * @param rows			the number of rows in a textarea
     * @param cols			the number of cols in a textarea
     * @param wrap			the wrap attribute of textarea
     */

    public TextareaElement(String name, 
			   int rows, 
			   int cols, 
			   String wrap) {

        this.name = name;
        this.rows = rows;
        this.cols = cols;
        this.wrap = wrap;
        setHTMLTag();
    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param name                      the name of the textarea
     * @param rows                      the number of rows in a textarea
     * @param cols                      the number of cols in a textarea
     * @param wrap                      the wrap attribute of textarea
     * @param str			the string to be displayed by the textarea
     */

    public TextareaElement(String name,
                           int rows,
                           int cols,
                           String wrap,
			   String str) {

        this.name = name;
        this.rows = rows;
        this.cols = cols;
        this.wrap = wrap;
	this.str = str;
        setHTMLTag();
    }

    /** 
     * Sets the number of rows in  a textarea.
     *
     * @param rows			the number of rows in a textarea
     *
     */

    public final void setRows(int rows) {

        this.rows = rows;
        setHTMLTag();

    }

    /**
     * Sets the number of cols in  a textarea.
     *
     * @param cols                      the number of cols in a textarea
     *
     */

    public final void setCols(int cols) {

        this.cols = cols;
        setHTMLTag();

    }

    /**
     * Sets the wrap mode of  a textarea.
     *
     * @param wrap                      the number of wrap in a textarea
     *
     */

    public final void setWrap(String wrap) {

        this.wrap = wrap;
        setHTMLTag();

    } 
  
    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

        if (name != null && !name.equals("")) {
            buf.append("<");
	    buf.append(TEXTAREA);
	    buf.append(" NAME=\"");
	    buf.append(name);
	    buf.append("\"");
            if (rows > 0) {
                buf.append(" ROWS=");
                buf.append(rows);
            }
            if (cols > 0) {
                buf.append(" COLS=");
                buf.append(cols);
            }
            if (wrap != null) {
                buf.append(" WRAP=\"");
                buf.append(wrap);
                buf.append("\"");
            }
            buf.append(">");
            if (str != null) {
                buf.append(str);
	    }
            buf.append("</");
	    buf.append(TEXTAREA);
	    buf.append(">");
            tag = buf.toString();

	}
    }
}

