/*
 * @(#)CaptionElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import core.util.*;

/**
 * The CaptionElement class returns a string in an HTML <CAPTION></CAPTION> format.
 * <BR><BR>
 * Usage:
 * CaptionElement str = new CaptionElement("Hello World!");
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: <CAPTION>Hello World!</CAPTION>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new CaptionElement("Hello World!").getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class CaptionElement extends HTMLElementObject {

    /**
     * Constructs this object,
     *
     */

    public CaptionElement() {
	super();
    }

   /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str		the string to be in bold tag.
     */

    public CaptionElement(String str) {

	super(str);

    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

        if (str != null) {
            buf.append("<");
            buf.append(CAPTION);
            buf.append(">");
            buf.append(str);
            buf.append("</");
            buf.append(CAPTION);
            buf.append(">");
            tag = buf.toString();
        }

    }
}

