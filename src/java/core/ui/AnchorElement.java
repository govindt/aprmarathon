/*
 * @(#)AnchorElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui; 

import java.lang.*;
import core.util.*;

/**
 * The AnchorElement class returns a string in an HTML <A HREF..></A> format.
 * <BR><BR>
 * Usage:
 * AnchorElement str = new AnchorElement("www.yahoo.com", null, null, "This is a link");
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: <A HREF='www.yahoo.com'>This is a link</A>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new AnchorElement("www.yahoo.com", null, null, "This is a link").getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class AnchorElement extends HTMLElementObject {
    
    private String name;
    private String url;
    private String target;
    private String id;
    private InputElement inputElement;
    private ImageElement imageElement;

    /**
     * Constructs this object.
     *
     */
    public AnchorElement() {

	super();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param url               	the url. 
     * @param target			the link target. 
     * @param inputElement		the anchor as InputElement.
     */

    public AnchorElement(String url, 
		         String target, 
		         InputElement inputElement) {

        this.inputElement = inputElement;
        this.target = target;
        this.url = url;
	setHTMLTag();
    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param url		the URL of the anchor
     * @param target            the target window/area
     * @param imageElement      the image object for anchor as ImageElement
     */

    public AnchorElement(String url, 
			 String target, 
		         ImageElement imageElement) {

        this.imageElement = imageElement;
        this.target = target;
        this.url = url;
	setHTMLTag();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param url               the URL of the anchor
     * @param target            the target window/area
     * @param name		the anchorname as string
     */

    public AnchorElement(String url, 
		         String target, 
		         String name) {

        this.name = name;
        this.target = target;
        this.url = url;
	setHTMLTag();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param url               the URL of the anchor
     * @param target            the target window/area
     * @param name		the anchorname as string
     */

    public AnchorElement(String url, 
		         String target, 
		         String name,
			 String id) {

        this.name = name;
        this.target = target;
        this.url = url;
	this.id = id;
	setHTMLTag();

    }

    /*
     * Sets the value of anchorname.
     *
     * @param name		the name of the anchor
     */

    public final void setAnchor(String name) {

        this.name = name;
	setHTMLTag();

    }

    /*
     * Sets the value of anchor.
     *
     * @param inputElement	the inputElement object as anchor
     */

    public final void setAnchor(InputElement inputElement) {

        this.inputElement = inputElement;
	setHTMLTag();

    }

    /*
     * Sets the value of anchor.
     *
     * @param imageElement	the image object as anchor
     */

    public final void setAnchor(ImageElement imageElement) {

        this.imageElement = imageElement;
	setHTMLTag();

    }

    /*
     * Sets the value of target window/area. 
     *
     * @param target            the target window/area 
     */

    public final void setTarget(String target) {

        this.target = target;
	setHTMLTag();

    }

    /*
     * Sets the value of URL.
     *
     * @param url               the URL of the anchor
     */

    public final void setURL(String url) {

        this.url = url;
	setHTMLTag();

    }

    /*
     * Constructs the HTML tag. 
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

	if (url != null && 
	    ((name != null) ||
             (inputElement != null) ||
	     (imageElement != null))) {
            buf.append("<A HREF=\"");
            buf.append(url);
            buf.append("\"");
	    if ( id != null ) {
		buf.append(" id=\"");
		buf.append(id);
		buf.append("\"");
	    }
	    if (target != null) {
                buf.append(" TARGET=\"");
                buf.append(target);
                buf.append("\"");
	    }
            if (onClickScript != null) {
                buf.append(" onClick=\"");
                buf.append(onClickScript);
                buf.append("\"");
            }
            buf.append(">");
            if (inputElement != null) {
                name = inputElement.getHTMLTag();
            } else if (imageElement != null) {
                name = imageElement.getHTMLTag();
            }
            buf.append(name);
            buf.append("</A>");
            tag = buf.toString();
	}
    }
}

