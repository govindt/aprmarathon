/*
 * @(#)CenteredElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import core.util.*;

/**
 * The CenteredElement class returns a string in an HTML <CENTER></CENTER> format.
 * <BR><BR>
 * Usage:
 * CenteredElement str = new CenteredElement("Hello World!");
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: <CENTER>Hello World!</CENTER>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new CenteredElement("Hello World!").getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class CenteredElement extends HTMLElementObject {

    /**
     * Constructs this object,
     *
     */

    public CenteredElement() {
	super();
    }

   /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str		the string to be in center tag.
     */

    public CenteredElement(String str) {

	super(str);

    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

	if (str != null) {
            buf.append("<");
            buf.append(CENTER);
            buf.append(">");
            buf.append(str);
            buf.append("</");
            buf.append(CENTER);
            buf.append(">");
            tag = buf.toString();
        }

    }
}

