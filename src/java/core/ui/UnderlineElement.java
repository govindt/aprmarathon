/*
 * @(#)UnderlineElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import core.util.*;

/**
 * The UnderlineElement class returns a string in an HTML <U></U> format.
 * <BR><BR>
 * Usage:
 * UnderlineElement str = new UnderlineElement("Hello World!");
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: <U>Hello World!</U>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new UnderlineElement("Hello World!").getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class UnderlineElement extends HTMLElementObject {

    /**
     * Constructs this object,
     *
     */

    public UnderlineElement() {
	super();
    }

   /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str		the string to be in underline tag.
     */

    public UnderlineElement(String str) {

	super(str);

    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();
	
	if (str != null) {
            buf.append("<");
            buf.append(UNDERLINE);
            buf.append(">");
            buf.append(str);
            buf.append("</");
            buf.append(UNDERLINE);
            buf.append(">");
            tag = buf.toString();
        }

    }
}

