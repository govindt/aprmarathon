/*
 * @(#)StringElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import core.util.*;

/**
 * The StringElement class returns a string. 
 * <BR><BR>
 * Usage:
 * StringElement str = new StringElement("Hello World!");
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: Hello World!
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new StringElement("Hello World!").getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class StringElement extends HTMLElementObject {

    private String face;
    private String color;
    private int size;


    private static final String COLOR = "COLOR";
    private static final String FACE = "FACE";
    private static final String SIZE = "SIZE";

    /**
     * Constructs this object,
     *
     */

    public StringElement() {

	super();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str               the string element.
     */

    public StringElement(String str) {

        this.str = str;
	setHTMLTag();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str               the string element.
     * @param face              the font face. 
     * @param color             the font color. 
     */

    public StringElement(String str, 
			 String face, 
			 String color) {

        this.str = str;
        this.face = face;
        this.color = color;
        setAttribute();
        setHTMLTag();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str               the string element.
     * @param face              the font face. 
     * @param color             the font color. 
     * @param size              the font size. 
     */

    public StringElement(String str, 
			 String face, 
			 String color, 
			 int size) {

        this.str = str;
        this.face = face;
        this.color = color;
        this.size = size;
        setAttribute();
	setHTMLTag();

    }

    /**
     * Constructs the FONT attribute of the string.
     *
     */

    private final void setAttribute() {

        if (color != null && !color.equals("")) {
            str = setFont(StringElement.COLOR, color, str);
        }
        if (face != null && !face.equals("")) {
            str = setFont(StringElement.FACE, face, str);
        }
        if (size > 0) {
	    StringBuffer buf = new StringBuffer();
	    buf.append(size);
            str = setFont(StringElement.SIZE, buf.toString(), str);
        }
        setHTMLTag();

    }

    /**
     * Constructs the FONT attribute of the string.
     */

    private final String setFont(String attribute,
			         String value,
				 String replaceStr) {

        StringBuffer buf = new StringBuffer();

	buf.append("<FONT ");
        buf.append(attribute);
	buf.append("=\"");
        buf.append(value);
        buf.append("\">");
        buf.append(replaceStr);
        buf.append("</FONT>");
        return (buf.toString());
	
    }

    /**
     * Sets the string element. 
     *
     * @param str               the string element. 
     */

    public final void setString(String str) {

        this.str = str; 
	setHTMLTag();

    }

    /**
     * Sets the string color. 
     *
     * @param color             the string color. 
     */

    public final void setColor(String color) {

        this.color = color;
        str = setFont(StringElement.COLOR, color, str);
	setHTMLTag();

    }

    /**
     * Sets the string face.
     *
     * @param face              the string face. 
     */

    public final void setFace(String face) {

        this.face = face;
        str = setFont(StringElement.FACE, face, str);
	setHTMLTag();

    }

    /**
     * Sets the string size. 
     *
     * @param size              the string size. 
     */

    public final void setSize(int size) {

        this.size = size;
	StringBuffer buf = new StringBuffer();
	buf.append(size);
        str = setFont(StringElement.SIZE, buf.toString(), str);
	setHTMLTag();

    }

    /**
     * Constructs the string in bold.
     *
     * @return 			the bold string.
     */

    public final StringElement asBoldElement() {

        str = new BoldElement(str).getHTMLTag();
        return (new StringElement(str));

    }

    /**
     * Constructs the string in italic.
     *
     * @return                  the italic string.
     */

    public final StringElement asItalicElement() {

        str = new ItalicElement(str).getHTMLTag();
	return (new StringElement(str));


    }

    /**
     * Constructs the string in paragraph. 
     *
     * @return                  the string in paragraph.
     */

    public final StringElement asParagraphElement() {

        str = new ParagraphElement(str).getHTMLTag();
	return (new StringElement(str));

    }

    /**
     * Constructs the string in underline. 
     *
     * @return                  the underlined string.
     */

    public final StringElement asUnderlineElement() {

        str = new UnderlineElement(str).getHTMLTag();
	return (new StringElement(str));

    }

    /**
     * Constructs the string in blinking mode.
     *
     * @return                  the blinking string.
     */

    public final StringElement asBlinkElement() {

        str = new BlinkElement(str).getHTMLTag();
	return (new StringElement(str));

    }

    /**
     * Constructs the heading string.
     *
     * @return                  the heading string.
     */

    public final StringElement asHeadingElement(int level) {

        str = new HeadingElement(str, level).getHTMLTag();
	return (new StringElement(str));

    }

    /**
     * Constructs the anchor string.
     *
     * @return                  the heading string.
     */

    public final StringElement asAnchorElement(String url, String target) {

        str = new AnchorElement(url, target, str).getHTMLTag();
	return (new StringElement(str));

    }

    /**
     * Constructs the centered string. 
     *
     * @return                  the centered string.
     */

    public final StringElement asCenteredElement() {

        str = new CenteredElement(str).getHTMLTag();
	return (new StringElement(str));

    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

	tag = str;

    }
}

