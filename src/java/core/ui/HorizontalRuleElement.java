/*
 * @(#)HorizontalRuleElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import core.util.*;

/**
 * The HorizontalRuleElement class returns a string in an HTML <HR> format.
 * <BR><BR>
 * Usage:
 * HorizontalRuleElement str = new HorizontalRuleElement();
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: <HR>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new HorizontalRuleElement().getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class HorizontalRuleElement extends HTMLElementObject {

    /**
     * Constructs this object,
     *
     */

    public HorizontalRuleElement() {
	setHTMLTag();
    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

        buf.append("<");
        buf.append(HORIZONTALRULE);
        buf.append(">");
        tag = buf.toString();

    }
}

