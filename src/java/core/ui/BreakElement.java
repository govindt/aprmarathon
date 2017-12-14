/*
 * @(#)BreakElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;
import core.util.*;

import java.lang.*;

/**
 * The BreakElement class returns a string in an HTML <BR> format.
 * <BR><BR>
 * Usage:
 * BreakElement break = new BreakElement(); 
 * <BR>
 * break.getHTMLTag();
 * <BR><BR>
 * output: <BR>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new BreakElement().getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class BreakElement extends HTMLElementObject {

    /**
     * Constructs this object,
     *
     */

    public BreakElement() {
	setHTMLTag();
    }


    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

        buf.append("<");
        buf.append(BREAK);
        buf.append(">");
        tag = buf.toString();

    }
}

