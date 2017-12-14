/*
 * @(#)HTMLElementObject.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */





package core.ui;

import java.lang.*;
import java.util.*;
import core.util.*;

/**
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public abstract class HTMLElementObject implements HTMLElement {

    protected String str;
    protected String name;
    protected String tag;
    protected String onClickScript;
    protected String onSubmitScript;
    protected String onChangeScript;
    protected String onSelectScript;


    protected final static String BOLD = "B";
    protected final static String BLINK = "BLINK";
    protected final static String CENTER = "CENTER";
    protected final static String ITALIC = "I";
    protected final static String UNDERLINE = "U";
    protected final static String HEADING = "H";
    protected final static String PARAGRAPH = "P";
    protected final static String DIV = "div";
    protected final static String SELECT = "SELECT";
    protected final static String TEXTAREA = "TEXTAREA";
    protected final static String CAPTION = "CAPTION";
    protected final static String BREAK = "BR"; 
    protected final static String UNORDEREDLIST = "UL"; 
    protected final static String HORIZONTALRULE = "HR"; 
    public final static String PRE = "<pre>"; 
    public final static String PRE_END = "</pre>";

    /**
     * Constructs this object,
     *
     */

    public HTMLElementObject() {

    }

   /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param str		the string to be in blink tag.
     */

    public HTMLElementObject(String str) {

        this.str = str;
	setHTMLTag();

    }

    /**
     * Sets the string to be embedded in the HTML tag.
     *
     * @param str               the string to be embedded.
     */

    public void setString(String str) {

        this.str = str;
        setHTMLTag();

    }

    /**
     * Sets the name of the HTML tag.
     *
     * @param name              the name of the HTML tag. 
     */

    public final void setName(String name) {

        this.name = name;
        setHTMLTag();

    }

    /**
     * Sets the heading level. 
     *
     * @param str               the string to be embedded.
     */

    public void setLevel(int level) {
    }

    /**
     * Sets the select hashtable list. 
     *
     * @param hList             the hashtable list. 
     */

    public void setList(Hashtable<String,String> hList) {
    }

    /**
     * Sets the select vector list.
     *
     * @param vList             the vector list.
     */

    public void setList(Vector<Integer> vList) {
    }

    /**
     * Sets the number of rows in  a textarea.
     *
     * @param rows                      the number of rows in a textarea
     *
     */

    public void setRows(int rows) {
    }

    /**
     * Sets the number of cols in  a textarea.
     *
     * @param cols                      the number of cols in a textarea
     *
     */

    public void setCols(int cols) {
    }

    /**
     * Sets the wrap mode of  a textarea.
     *
     * @param wrap                      the number of wrap in a textarea
     *
     */

    public void setWrap(String wrap) {
    }

    /**
     * Sets the image source.
     *
     * @param source                    the image source
     */

    public void setSource(String source) {
    }

    /**
     * Sets the image border.
     *
     * @param border                    the image border
     */

    public void setBorder(int border) {
    }

    /**
     * Sets the image height. 
     *
     * @param height                    the image height
     */

    public void setHeight(int height) {
    }
    public void setHeight(String height) {
    }
    /**
     * Sets the image width.
     *
     * @param width                     the image width
     */

    public void setWidth(int width) {
    }
    public void setWidth(String width) {
    }
    /**
     * Sets the image alternate string.
     *
     * @param alt                       the image alt
     */

    public void setAlt(String alt) {
    }
    
    /**
     * Sets the image ismap attribute.
     *
     * @param ismap                     the image ismap attribute
     */

    public void setIsMap(boolean ismap) {
    }

    /**
     * Sets the image usemap.
     *
     * @param usemap                    the image usemap
     */

    public void setUseMap(String usemap) {
    }


    /**
     * Sets the string color.
     *
     * @param color             the string color.
     */
    public void setColor(String color) {
    }

    /**
     * Sets the string face.
     *
     * @param face              the string face.
     */

    public void setFace(String face) {
    }

    /**
     * Sets the string size.
     *
     * @param size              the string size.
     */
    public void setSize(int size) {
    }

    /*
     * Sets the value of anchorname.
     *
     * @param name              the name of the anchor
     */

    public void setAnchor(String name) {
    }

    /*
     * Sets the value of anchor.
     *
     * @param inputElement      the inputElement object as anchor
     */

    public void setAnchor(InputElement inputElement) {
    }

    /*
     * Sets the value of anchor.
     *
     * @param imageElement      the image object as anchor
     */

    public void setAnchor(ImageElement imageElement) {
    }

    /*
     * Sets the value of target window/area.
     *
     * @param target            the target window/area
     */

    public void setTarget(String target) {
    }

    /*
     * Sets the value of URL.
     *
     * @param url               the URL of the anchor
     */

    public void setURL(String url) {
    }


    /*
     * Sets the value of onClick script.
     *
     * @param script            the javascript for onClick().
     */

    public void setOnClick(String onClickScript) {

        this.onClickScript = onClickScript;
	setHTMLTag();
    }


	/*
     * Sets the value of onSelect script.
     *
     * @param script            the javascript for onSelect().
     */

    public void setOnSelect(String onSelectScript) {

        this.onSelectScript = onSelectScript;
		setHTMLTag();
    }

    /**
     * Sets the input type.
     *
     * @param type              the input type
     */

    public void setType(String type) {
    }

    /**
     * Sets the input value.
     *
     * @param value             the input value
     */

    public void setValue(int value) {
    }


    /**
     * Sets the input value.
     *
     * @param value             the input value
     */

    public void setValue(String value) {
    }

    /**
     * Sets the checked attribute of checkbox/radio.
     *
     * @param isChecked         the checked attribute of radios/checkbox.
     */

    public void setIsChecked(boolean isChecked) {
    }

    /**
     * Sets the file accept attribute.
     *
     * @param accept            the accept file attribute
     */

    public void setAccept(String accept) {
    }

    /**
     * Sets the text maxlength.
     *
     * @param maxlength         the text maxlength
     */

    public void setMaxlength(int maxlength) {
    }

    /**
     * Sets the onChange script.
     *
     * @param onChange          the onChange script. 
     */

    public void setOnChange(String onChangeScript) {

        this.onChangeScript = onChangeScript;
	setHTMLTag();
    }

    /**
     * Sets the onSubmit script.
     *
     * @param onSubmit          the onSubmit script. 
     */

    public void setOnSubmit(String onSubmitScript) {

	this.onSubmitScript = onSubmitScript;
	setHTMLTag();

    }

    /**
     * Sets the table cellspacing.
     *
     * @param cellSpacing       the cellspacing
     */

    public void setCellSpacing(int cellSpacing) {
    }

    /**
     * Sets the table cellpadding.
     *
     * @param cellPadding       the cellpadding
     */

    public void setCellPadding(int cellPadding) {
    }

    /**
     * Adds elements to the table. Allowed: <CAPTION> and <TR> .
     *
     * @param strElement        the elements in string format.
     */

    public void addElement(String strElement) {
    }

    /**
     * Adds elements to the table. Allowed: <CAPTION> and <TR> .
     *
     * @param captionElement    the elements of CaptionElement type.
     */

    public void addElement(CaptionElement captionElement) {
    }

    /**
     * Adds elements to the table. Allowed: <CAPTION> and <TR> .
     *
     * @param tableRowElement   the elements of TableRowElement type.
     */

    public void addElement(TableRowElement tableRowElement) {
    }

    /**
     * Adds elements to the table. Allowed: <TH> and <TD> .
     *
     * @param tableDataElement    the elements of TableDataElement type.
     */

    public void addElement(TableDataElement tableDataElement) {
    }

    /**
     * Adds elements to the table. Allowed: <TH> and <TD> .
     *
     * @param tableHeadingElement    the elements of TableHeadingElement type.
     */

    public void addElement(TableHeadingElement tableHeadingElement) {
    }

    /**
     * Sets the table row align attribute.
     *
     * @param align             the table row align attribute.
     */

    public void setAlign(String align) {
    }

    /**
     * Sets the table row valign attribute.
     *
     * @param valign            the table row valign attribute.
     */

    public void setValign(String valign) {
    }

    /**
     * Sets the table row bgcolor attribute.
     *
     * @param bgcolor           the table row bgcolor attribute.
     */

    public void setBgcolor(String bgcolor) {
    }

    /**
     * Sets the table data colspan attribute.
     *
     * @param colspan           the table data colspan attribute.
     */

    public void setColspan(int colspan) {
    }

    /**
     * Sets the table data rowspan attribute.
     *
     * @param rowspan           the table data rowspan attribute.
     */

    public void setRowspan(int rowspan) {
    }

    /**
     * Sets the table data wrap attribute.
     *
     * @param wrap              the table data wrap attribute.
     */

    public void setWrap(boolean wrap) {
    }

    /**
     * Sets the options for the select list.
     *
     * @param selectedValue              the selected value
     */

    public void setSelectedValue(String selectedValue) {
    }

    /**
     * Sets the label for input type elements.
     *
     * @param label              the label 
     */

    public void setLabel(String label) {
    }


    /**
     * Returns the HTML tag.  
     *
     * @return 			the HTML tag.
     */

    public final String getHTMLTag() {
	
	if (tag != null && !tag.equals("")) { 
	    return (tag);
	} else {
	    return "";
	}
	

    }

    /**
     * Sets the HTML tag. 
     *
     */
    abstract protected void setHTMLTag();


}

