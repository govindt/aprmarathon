/*
 * @(#)TableHeadingElement.java	1.2 01/02/22
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
 * The TableHeadingElement class returns a string in an HTML <TH..></TH> format.
 * <BR><BR>
 * Usage:
 * <BR><BR>
 * Define an input element.
 * <BR>
 * HTMLElement text = new InputElement(InputElement.TEXT, "text1", "This is a test.");
 * <BR>
 * text.setSize(9);
 * <BR>
 * text.setMaxlength(78);
 * <BR><BR>
 *
 * Define an anchor element.
 * <BR>
 * HTMLElement anchor = new AnchorElement();
 * <BR>
 * anchor.setTarget("window1");
 * <BR>
 * anchor.setURL("www.yahoo.com");
 * <BR>
 * anchor.setAnchor("Click me!");
 * <BR><BR>
 *
 * Create the table:
 * <BR>
 * HTMLElement tbl = new TableElement();
 * <BR>
 * tbl.setBorder(0);
 * <BR>
 * tbl.setWidth(200);
 * <BR>
 * tbl.setCellPadding(100);
 * <BR>
 * tbl.setCellSpacing(100);
 * <BR><BR>
 *
 * Create a table row:
 * <BR>
 * HTMLElement tblrow = new TableRowElement();
 * <BR>
 * tblrow.setAlign(TableRowElement.LEFT);
 * <BR><BR>
 *
 * Create table data:
 * <BR>
 * tblrow.addElement(new TableHeadingElement(text));
 * <BR>
 * tblrow.addElement(new TableHeadingElement(anchor));
 * <BR><BR>
 *
 * Add elements to the table:
 * <BR>
 * tbl.addElement(new CaptionElement("My Table"));
 * <BR>
 * tbl.addElement((TableRowElement)tblrow);
 * <BR>
 * System.out.println(tbl.getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class TableHeadingElement extends HTMLElementObject {

    private String align;
    private String valign;
    private String bgcolor;
    private int colspan;
    private int rowspan;
    private boolean wrap;
    private Vector<String> vTableHeading;
    private HTMLElement htmlElement;

    public static final String LEFT = "LEFT";
    public static final String RIGHT = "RIGHT";
    public static final String CENTER = "CENTER";
    public static final String TOP = "TOP";
    public static final String MIDDLE = "MIDDLE";
    public static final String BOTTOM = "BOTTOM";
    public static final String BASELINE = "BASELINE";

    /**
     * Constructs this object,
     *
     */

    public TableHeadingElement() {

        vTableHeading = new Vector<String>();
    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param htmlElement       the htmlElement
     */

    public TableHeadingElement(HTMLElement htmlElement) {

        this.htmlElement = htmlElement;
        vTableHeading = new Vector<String>();
        setHTMLTag();
    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param align             the table data align attribute.
     * @param valign            the table data valign attribute.
     * @param rowspan           the table data rowspan attribute.
     * @param colspan           the table data colspan attribute.
     * @param wrap              the table data wrap attribute.
     * @param bgcolor           the table data bgcolor attribute.
     */

    public TableHeadingElement(HTMLElement htmlElement,
                               String align,
                               String valign,
                               int rowspan,
                               int colspan,
                               boolean wrap,
                               String bgcolor) {

        this.htmlElement = htmlElement;
        this.align = align;
        this.valign = valign;
        this.rowspan = rowspan;
        this.colspan = colspan;
        this.wrap = wrap;
        this.bgcolor = bgcolor;
        vTableHeading = new Vector<String>();
        setHTMLTag();

    }

    /**
     * Sets the table data align attribute.
     *
     * @param align             the table data align attribute.
     */

    public final void setAlign(String align) {

        this.align = align;

        setHTMLTag();
    }

    /**
     * Sets the table data valign attribute.
     *
     * @param valign            the table data valign attribute.
     */

    public final void setValign(String valign) {

        this.valign = valign;

        setHTMLTag();
    }

    /**
     * Sets the table data colspan attribute.
     *
     * @param colspan           the table data colspan attribute.
     */

    public final void setColspan(int colspan) {

        this.colspan = colspan;

        setHTMLTag();
    }

    /**
     * Sets the table data rowspan attribute.
     *
     * @param rowspan           the table data rowspan attribute.
     */

    public final void setRowspan(int rowspan) {

        this.rowspan = rowspan;

        setHTMLTag();
    }

    /**
     * Sets the table data wrap attribute.
     *
     * @param wrap              the table data wrap attribute.
     */

    public final void setWrap(boolean wrap) {

        this.wrap = wrap;

        setHTMLTag();
    }

 
    /**
     * Sets the table data bgcolor attribute.
     *
     * @param bgcolor           the table data bgcolor attribute.
     */

    public final void setBgcolor(String bgcolor) {

        this.bgcolor = bgcolor;

        setHTMLTag();
    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

        if (htmlElement != null) {
            buf.append("<TH");
            if (align != null) {
                buf.append(" ALIGN=");
                buf.append(align);
            }
            if (valign != null) {
                buf.append(" VALIGN=");
                buf.append(valign);
            }
            if (bgcolor != null) {
                buf.append(" BGCOLOR=");
                buf.append(bgcolor);
            }
            if (rowspan > 0) {
                buf.append(" ROWSPAN=");
                buf.append(rowspan);
            }
            if (colspan > 0) {
                buf.append(" COLSPAN=");
                buf.append(colspan);
            }
            if (!wrap) {
                buf.append(" NOWRAP");
            }
            buf.append(">");
            buf.append(htmlElement.getHTMLTag());
            buf.append("</TH>");
            tag = buf.toString();
        }

    }
}

