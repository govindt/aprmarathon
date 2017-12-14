/*
 * @(#)HeadingElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */




package core.ui;

/**
 * The HeadingElement class returns a string in an HTML <Hn></Hn> format.
 * <BR><BR>
 * Usage:
 * HeadingElement str = new HeadingElement("Hello World!", 2);
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: <H2>Hello World!</H2>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new HeadingElement("Hello World!", 2).getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class HeadingElement extends HTMLElementObject {

    private int level;

    /**
     * Constructs this object,
     *
     */

    public HeadingElement() {
        super();
    }

   /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str               the string to be in heading tag.
     */

    public HeadingElement(String str) {

       	this.str = str; 

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str               the string to be in heading tag.
     * @param level             the heading level 
     */

    public HeadingElement(String str, 
		          int level) {
	
	this.str = str;
	this.level = level;
	setHTMLTag();
    }

    /**
     * Sets the heading level. 
     *
     * @param level             the heading level
     */

    public final void setLevel(int level) {

        this.level = level;
        setHTMLTag();

    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();
 
        if (str != null && level > 0) {
       	    buf.append("<");
  	    buf.append(HEADING);
            buf.append(level);
            buf.append(">");
            buf.append(str);
            buf.append("</");
  	    buf.append(HEADING);
            buf.append(level);
            buf.append(">");

	    tag = buf.toString();
	}
    }     
}

