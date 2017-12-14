/*
 * @(#)DivElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import core.util.*;

/**
 * The DivElement class returns a string in an HTML <div> format.
 * <BR><BR>
 * Usage:
 * DivElement str = new DivElement("Hello World!");
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: <div>Hello World!</div>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new DivElement("Hello World!").getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class DivElement extends HTMLElementObject {
    private static String TEXT_ALIGN = "text-align: ";
    public static String CENTER = "center";
    public static String LEFT = "left";
    public static String RIGHT = "right";
    private String align;

    /**
     * Constructs this object,
     *
     */

    public DivElement() {
	super();
    }

   /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str		the string to be in paragraph tag.
     */

    public DivElement(String str) {

	super(str);

    }

     /*
     * Sets the value of align.
     *
     * @param align               the alignment
     */

    public final void setAlign(String align) {
	this.align = align;
	setHTMLTag();
    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

	if (str != null) {
            buf.append("<");
            buf.append(DIV);
	    if ( align != null ) {
		buf.append(" style=\"");
		buf.append(TEXT_ALIGN);
		buf.append(align);
		buf.append(";");
	    }
	    buf.append(">");
            buf.append(str);
	    buf.append("</");
	    buf.append(DIV);
	    buf.append(">");
            tag = buf.toString();
        }

    }
}



