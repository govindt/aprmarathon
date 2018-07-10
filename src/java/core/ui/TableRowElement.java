/*
 * @(#)TableRowElement.java	1.2 01/02/22
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
 * The TableRowElement class returns a string in an HTML <TR..></TR> format.
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
 * tblrow.add(new TableDataElement(text));
 * <BR>
 * tblrow.add(new TableDataElement(anchor));
 * <BR><BR>
 *
 * Add elements to the table:
 * <BR>
 * tbl.add(new CaptionElement("My Table"));
 * <BR>
 * tbl.add((TableRowElement)tblrow);
 * <BR>
 * System.out.println(tbl.getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class TableRowElement extends HTMLElementObject {

    private String align;
    private String id;
    private String valign;
    private String bgcolor;
    private int width;
    private ArrayList<String> vTableRow;

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

    public TableRowElement() {

        vTableRow = new ArrayList<String>();
    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param align             the table row align attribute.
     * @param valign            the table row valign attribute.
     * @param bgcolor           the table row bgcolor attribute.
     */

    public TableRowElement(String align,
                           String valign,
                           String bgcolor) {

        this.align = align;
        this.valign = valign;
        this.bgcolor = bgcolor;
        this.width = width;
        vTableRow = new ArrayList<String>();
        setHTMLTag();

    }

    /**
     * Sets the table row align attribute.
     *
     * @param align             the table row align attribute.
     */

    public final void setAlign(String align) {

        this.align = align;

        setHTMLTag();
    }

    /**
     * Sets the table row valign attribute.
     *
     * @param valign            the table row valign attribute.
     */

    public final void setValign(String valign) {

        this.valign = valign;

        setHTMLTag();
    }
    public final void setWidth(int width) {

        this.width = width;

        setHTMLTag();
    }

    /**
     * Sets the table row bgcolor attribute.
     *
     * @param bgcolor           the table row bgcolor attribute.
     */

    public final void setBgcolor(String bgcolor) {

        this.bgcolor = bgcolor;

        setHTMLTag();
    }

    /**
     * Adds elements to the table row. Allowed: <TH> and <TD> .
     *
     * @param strElement        the elements in string format.
     */

    public final void add(String strElement) {

        vTableRow.add(strElement);
        setHTMLTag();
    }

    /**
     * Adds elements to the table. Allowed: <TH> and <TD> .
     *
     * @param tableHeadingElement    the elements of TableHeadingElement type.
     */

    public final void add(TableHeadingElement tableHeadingElement) {

        vTableRow.add(tableHeadingElement.getHTMLTag());
        setHTMLTag();
 
    }

    /**
     * Adds elements to the table. Allowed: <TH> and <TD> .
     *
     * @param tableDataElement    the elements of TableDataElement type.
     */

    public final void add(TableDataElement tableDataElement) {

        vTableRow.add(tableDataElement.getHTMLTag());
        setHTMLTag();
 
    }

    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {

        StringBuffer buf = new StringBuffer();

        buf.append("<TR");
        if ( width > 0){
	   buf.append(" width=");
	   buf.append(width); 
	   buf.append("%");
	}
        if (align != null) {
            buf.append(" ALIGN=");
            buf.append(align);
        }
        if (valign != null) {
            buf.append(" VALIGN=");
            buf.append(valign);
        }
        if (bgcolor != null) {
            buf.append(" BGCOLOR=\"");
            buf.append(bgcolor);
	    buf.append("\"");
        }
        buf.append(">");

	int i = 0;
        while (i < vTableRow.size()) {
             String tableRowElements = vTableRow.get(i);
             buf.append(tableRowElements);
	     i++;
        }

        buf.append("</TR>");
        tag = buf.toString() + "\n";

    }
}

