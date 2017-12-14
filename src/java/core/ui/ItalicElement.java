/*
 * @(#)ItalicElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import core.util.*;

/**
 * The ItalicElement class returns a string in an HTML <I></I> format.
 * <BR><BR>
 * Usage:
 * ItalicElement str = new ItalicElement("Hello World!");
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: <I>Hello World!</I>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new ItalicElement("Hello World!").getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class ItalicElement extends HTMLElementObject {

    /**
     * Constructs this object,
     *
     */

    public ItalicElement() {
	super();
    }

   /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str		the string to be in italic tag.
     */

    public ItalicElement(String str) {

	super(str);

    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

	if (str != null) {
            buf.append("<");
            buf.append(ITALIC);
            buf.append(">");
            buf.append(str);
            buf.append("</");
            buf.append(ITALIC);
            buf.append(">");
            tag = buf.toString();
        }
    }
}

