/*
 * @(#)InputElement.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import core.util.*;

public class InputElement extends HTMLElementObject {

    private String type;
    private String name;
    private String value;
    private String accept;
    private String source;
    private String label;
    private int maxlength;
    private int intValue;
    private int size;
    private int border;
    private int width;
    private int height;
    private boolean isChecked;

    /**
     * HTML Input types.
     */
    public static final String RADIO = "radio";
    public static final String CHECKBOX = "checkbox";
    public static final String FILE = "file";
    public static final String HIDDEN = "hidden";
    public static final String TEXT = "text";
    public static final String IMAGE = "image";
    public static final String PASSWORD = "password";
    public static final String RESET = "reset";
    public static final String BUTTON = "button";
    public static final String SUBMIT = "submit";


    /**
     * Constructs this object,
     *
     */

    public InputElement() {

	super();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters. This is used for types: 
     * RADIO, CHECKBOX, HIDDEN, PASSWORD, RESET, SUBMIT, BUTTON, TEXT
     *
     *
     * @param type              the input type
     * @param name		the input name
     * @param value		the input value
     */

    public InputElement(String type, 
		        String name, 
		        String value) {

        this.type = type;
        this.name = name;
        this.value = value;

	setHTMLTag();
    }

    public InputElement(String type,
                        String name,
                        int size) {

        this.type = type;
        this.name = name;
        this.size = size;

        setHTMLTag();
    }

    public InputElement(String type,
                        String name,
                        String value,
			int size) {

        this.type = type;
        this.name = name;
        this.value = value;
        this.size = size;

        setHTMLTag();
    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters. This is used for types: 
     * FILE, IMAGE 
     *
     *
     * @param type              the input type 
     * @param name              the input name
     */

    public InputElement(String type, 
		        String name) {

        this.type = type;
        this.name = name;

	setHTMLTag();

    }

    /** 
     * Sets the input type.
     *
     * @param type		the input type
     */

    public final void setType(String type) {

        this.type = type;

	setHTMLTag();
    }

    /** 
     * Sets the input value. 
     *
     * @param value             the input value 
     */

    public final void setValue(String value) {

        this.value = value;

	setHTMLTag();

    }

    /**
     * Sets the input value.
     *
     * @param value             the input value
     */

    public final void setValue(int value) {

        this.intValue = intValue;

        setHTMLTag();

    }


    /** 
     * Sets the checked attribute of checkbox/radio. 
     *
     * @param isChecked         the checked attribute of radios/checkbox. 
     */

    public final void setIsChecked(boolean isChecked) {

	this.isChecked = isChecked;

	setHTMLTag();

    }

    /** 
     * Sets the file accept attribute. 
     *
     * @param accept            the accept file attribute 
     */

    public final void setAccept(String accept) {

        this.accept = accept;

	setHTMLTag();

    }
   
    /** 
     * Sets the text size. 
     *
     * @param size              the text size. 
     */

    public final void setSize(int size) {

	this.size = size;

	setHTMLTag();

    }

    /** 
     * Sets the text maxlength. 
     *
     * @param maxlength         the text maxlength 
     */

    public final void setMaxlength(int maxlength) {

	this.maxlength = maxlength;

	setHTMLTag();

    }

    /**
     * Sets the image source.
     *
     * @param source                    the image source
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
     * Sets the label for input type elements.
     *
     * @param label              the label 
     */

    public final void setLabel(String label) {

	this.label = label;

	setHTMLTag();

    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {
		
        StringBuffer buf = new StringBuffer();
		
		if ((name != null && 
             type != null && !type.equals(InputElement.IMAGE)) ||  
			(source != null && type.equals(InputElement.IMAGE))) {             
            buf.append("<INPUT TYPE=\"");
            buf.append(type);
			buf.append("\"");
			buf.append(" NAME=\"");
			buf.append(name);
			buf.append("\"");
            if (value != null) {
				buf.append(" VALUE=\"");
				buf.append(value);
				buf.append("\"");
			}
			
			
			if (isChecked && (type.equals(InputElement.CHECKBOX) || type.equals(InputElement.RADIO))) {
				buf.append(" CHECKED");
            }
			
			if (onClickScript != null && 
				(type.equals(InputElement.RADIO) || type.equals(InputElement.CHECKBOX) ||
				 type.equals(InputElement.BUTTON) || type.equals(InputElement.RESET)) ||
				type.equals(InputElement.SUBMIT)) {
				buf.append(" onClick=\"");
				buf.append(onClickScript);
				buf.append("\"");
			}
			
			if (accept != null && type.equals(InputElement.FILE)) {
				buf.append(" ACCEPT=\"");
				buf.append(accept);
				buf.append("\"");
			}
			
			if (type.equals(InputElement.TEXT)) {
				if (size > 0) {
					buf.append(" SIZE=");
					buf.append(size);
				}
				if (maxlength > 0) {
					buf.append(" MAXLENGTH=");
					buf.append(maxlength);
				}
				if (onSelectScript != null ) {  
					buf.append(" onSelect=\"");
					buf.append(onSelectScript);
					buf.append("\"");
				}
			}
			
			if (type.equals(InputElement.IMAGE)) {
				if (source != null) {
					buf.append(" SRC=\"");
					buf.append(source);
					buf.append("\"");
				}
				if (border > -1) {
					buf.append(" BORDER=");
					buf.append(border);
				} 
				if (width > 0) {
					buf.append(" WIDTH=");
					buf.append(width);
				} 
				if (height > 0) {
					buf.append(" HEIGHT=");
					buf.append(height);
				} 
			}
			
			if (onSubmitScript != null && type.equals(InputElement.SUBMIT)) {
				buf.append(" onSubmit=\"");
				buf.append(onSubmitScript);
				buf.append("\"");
			}
            
			if (onChangeScript != null && 
				(type.equals(InputElement.PASSWORD) || type.equals(InputElement.BUTTON) ||
				 type.equals(InputElement.TEXT))) {
				buf.append(" onChange=\"");
				buf.append(onChangeScript);
				buf.append("\"");
	    }
			
			if (label != null) {
				buf.append(">");
				buf.append(label);
			} else {
				buf.append(">");
			}
			tag = buf.toString() + "\n";
		}
    }
}

