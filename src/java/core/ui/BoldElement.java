/*
 * @(#)BoldElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;
import core.util.*;

import java.lang.*;

/**
 * The BoldElement class returns a string in an HTML <BOLD></BOLD> format.
 * <BR><BR>
 * Usage:
 * BoldElement str = new BoldElement("Hello World!");
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: <BOLD>Hello World!</BOLD>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new BoldElement("Hello World!").getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class BoldElement extends HTMLElementObject {
    String id;

    /**
     * Constructs this object,
     *
     */

    public BoldElement() {
	super();
    }

   /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str		the string to be in bold tag.
     */

    public BoldElement(String str) {

	super(str);

    }

    public void setId(String id) {
	this.id=id;
	setHTMLTag();
    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

        if (str != null) {
            buf.append("<");
            buf.append(BOLD);
	    if ( id != null ) {
		buf.append(" ID=\"");
		buf.append(id);
		buf.append("\"");
	    }
            buf.append(">");
            buf.append(str);
            buf.append("</");
            buf.append(BOLD);
            buf.append(">");
            tag = buf.toString();
        }

    }
}

