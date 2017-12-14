/*
 * @(#)UnorderedListElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import core.util.*;

/**
 * The UnorderedListElement class returns a string in an HTML <UL></UL> format.
 * <BR><BR>
 * Usage:
 * UnorderedListElement str = new UnorderedListElement("Hello World!");
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: <UL>Hello World!</UL>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new UnorderedListElement("Hello World!").getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class UnorderedListElement extends HTMLElementObject {

    /**
     * Constructs this object,
     *
     */

    public UnorderedListElement() {
        super();
    }

   /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str               the string to be in bold tag.
     */

    public UnorderedListElement(String str) {

        super(str);

    }

    /**
     * Constructs the HTML tag.
     */
   
    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

        if (str != null) {
            buf.append("<");
            buf.append(UNORDEREDLIST);
            buf.append(">");
            buf.append(str);
            buf.append("</");
            buf.append(UNORDEREDLIST);
            buf.append(">");
            tag = buf.toString();
        }

    }
}

