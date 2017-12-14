/*
 * @(#)ParagraphElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import core.util.*;

/**
 * The ParagraphElement class returns a string in an HTML <P> format.
 * <BR><BR>
 * Usage:
 * ParagraphElement str = new ParagraphElement("Hello World!");
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: <P>Hello World!
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new ParagraphElement("Hello World!").getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class ParagraphElement extends HTMLElementObject {

    /**
     * Constructs this object,
     *
     */

    public ParagraphElement() {
	super();
    }

   /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str		the string to be in paragraph tag.
     */

    public ParagraphElement(String str) {

	super(str);

    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

	if (str != null) {
            buf.append("<");
            buf.append(PARAGRAPH);
            buf.append(">");
            buf.append(str);
            tag = buf.toString();
        }

    }
}

