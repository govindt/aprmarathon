/*
 * @(#)ImageElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import core.util.*;

/**
 * The ImageElement class returns a string in an HTML <IMG ...> format.
 * <BR><BR>
 * Usage:
 * ImageElement str = new ImageElement("./images/sample.jpg", 0, 250, 250, "This is an alt text.", false, null);
 * <BR>
 * str.getHTMLTag();
 * <BR><BR>
 * output: The output will be an image with the above specification.
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new ImageElement("./images/sample.jpg", 0, 250, 250, "This is an alt text.", false, null).getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class ImageElement extends HTMLElementObject {

    private String source;
    private int border;
    private int height;
    private int width;
    private String alt;
    private boolean ismap;
    private String usemap;


    /**
     * Constructs this object,
     *
     */

    public ImageElement() {

        super();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param source                    the image source 
     */

    public ImageElement(String source) {
 
        this.source = source;
	setHTMLTag();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param source                    the image source
     * @param border			the image border
     * @param height			the image height
     * @param width			the image width
     */

    public ImageElement(String source, 
		        int border, 
			int height, 
			int width) {

        this.source = source;
        this.border = border;
        this.height = height;
        this.width = width;

        setHTMLTag();
    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param source                    the image source
     * @param border                    the image border
     * @param height                    the image height
     * @param width                     the image width
     * @param alt			the image alt string
     * @param ismap 			the image ismap attribute
     * @param usemap			the image usemap attribute
     */

    public ImageElement(String source,
                        int border,
                        int height,
                        int width,
                        String alt,
                        boolean ismap,
                        String usemap) {

        this.source = source;
        this.border = border;
        this.height = height;
        this.width = width;
        this.alt = alt;
        this.ismap = ismap;
        this.usemap = usemap;

        setHTMLTag();
    }

    /**
     * Sets the image source.
     *
     * @param source			the image source
     */

    public final void setSource(String source) {

        this.source = source;

        setHTMLTag();
    }

    /**
     * Sets the image border.
     *
     * @param border                    the image border 
     */

    public final void setBorder(int border) {

        this.border = border;

        setHTMLTag();
    }
	
    /**
     * Sets the image height. 
     *
     * @param height                    the image height 
     */

    public final void setHeight(int height) {

        this.height = height;

        setHTMLTag();
    }

    /**
     * Sets the image width.
     *
     * @param width                     the image width
     */

    public final void setWidth(int width) {

        this.width = width;

        setHTMLTag();

    }

    /**
     * Sets the image alternate string. 
     *
     * @param alt                       the image alt 
     */

    public final void setAlt(String alt) {

        this.alt = alt;

        setHTMLTag();
    }

    /**
     * Sets the image ismap attribute. 
     *
     * @param ismap                     the image ismap attribute 
     */

    public final void setIsMap(boolean ismap) {

        this.ismap = ismap;

        setHTMLTag();
    }

    /**
     * Sets the image usemap.
     *
     * @param usemap                    the image usemap 
     */

    public final void setUseMap(String usemap) {

        this.usemap = usemap;

        setHTMLTag();
    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

        if (source != null) {
            buf.append("<IMG");
            buf.append(" SRC=\"");
            buf.append(source);
            buf.append("\"");
            if (border > -1) {
                buf.append(" BORDER=");
                buf.append(border);
            }
            if (height > 0) {
                buf.append(" HEIGHT=");
                buf.append(height);
            }
            if (width > 0) {
                buf.append(" WIDTH=");
                buf.append(width);
            }
            if (usemap != null) {
                buf.append(" USEMAP=\"");
                buf.append(usemap);
                buf.append("\"");
            }
            if (alt != null) {
                buf.append(" ALT=\"");
                buf.append(alt);
                buf.append("\"");
            }
            if (ismap) {
                buf.append(" ISMAP");
            }
            buf.append(">");
            tag = buf.toString();
        }
    }
}

