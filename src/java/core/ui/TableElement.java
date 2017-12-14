/*
 * @(#)TableElement.java	1.3 01/02/23
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
 * The TableElement class returns a string. 
 * <BR><BR>
 * Usage:
 * TableElement tbl = new TableElement();
 * <BR>
 * tbl.addElement("<TR><TD>Test data</TD></TR>");
 * tbl.addElement(new CaptionElement("This is a caption."));
 * tbl.addElement(new TableRowElement());
 * tbl.getHTMLTag();
 * <BR><BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class TableElement extends HTMLElementObject {
    
    private int border;
    private int width;
    private String widthStr;
    private String classStr;
    
    private int height;
    private String heightStr;
    private int cellPadding;
    private int cellSpacing;
    private Vector<String> vTable;
    
    /**
     * Constructs this object,
     *
     */
    
    public TableElement() {
	
	vTable = new Vector<String>();
    }
    
    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param border            the table border
     * @param width             the table width
     * @param cellSpacing       the cellspacing
     * @param cellPadding       the cellpadding
     */
    
    public TableElement(int border, 
			int width,
			int cellPadding, 
			int cellSpacing) {
	
	this.border = border;
	this.width = width;
	this.cellPadding = cellPadding;
	this.cellSpacing = cellSpacing;
	vTable = new Vector<String>();
	setHTMLTag();
	
    }
    
    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param border            the table border
     * @param width             the table width as %
     * @param cellSpacing       the cellspacing
     * @param cellPadding       the cellpadding
     */
    
    public TableElement(int border, 
			String widthStr,
			int cellPadding, 
			int cellSpacing) {
	
	this.border = border;
	this.widthStr = widthStr;
	this.cellPadding = cellPadding;
	this.cellSpacing = cellSpacing;
	vTable = new Vector<String>();
	setHTMLTag();
	
    }
    /**
     * Sets the table border.
     *
     * @param border		the table border
     */
    
    public final void setBorder(int border) {
	
	this.border = border;
	
	setHTMLTag();
    }
    
    /**
     * Sets the table width. 
     *
     * @param width		the table width 
     */
    
    public final void setWidth(int width) {
	
	this.width = width;
	
	setHTMLTag();
    }
    
    /**
     * Sets the table width as %.
     *
     * @param width		the table width as %
     */
    
    public final void setWidthStr(String widthStr) {
	
	this.widthStr = widthStr;
	
	setHTMLTag();
    }
    
    
    /**
     * Sets the table height. 
     *
     * @param width		the table height 
     */
    
    public final void setHeight(int height) {
	
	this.height = height;
	
	setHTMLTag();
    }

    /**
     * Sets the class as %.
     *
     * @param class		the table class as %
     */
    
    public final void setClass(String classStr) {
	
	this.classStr = classStr;
	
	setHTMLTag();
    }
    
    /**
     * Sets the table height as %.
     *
     * @param width		the table height as %
     */
    
    public final void setHeightStr(String heightStr) {
	
	this.heightStr = heightStr;
	
	setHTMLTag();
    }
    
    /**
     * Sets the table cellspacing. 
     *
     * @param cellSpacing       the cellspacing 
     */
    
    public final void setCellSpacing(int cellSpacing) {
	
	this.cellSpacing = cellSpacing;
	
	setHTMLTag();
    }
    
    /**
     * Sets the table cellpadding. 
     *
     * @param cellPadding       the cellpadding
     */
    
    public final void setCellPadding(int cellPadding) {
	
	this.cellPadding = cellPadding;
	
	setHTMLTag();
    }
    
    /**
     * Adds elements to the table. Allowed: <CAPTION> and <TR> .
     *
     * @param strElement	the elements in string format. 
     */
    
    public final void addElement(String strElement) {
	
	vTable.addElement(strElement);
	setHTMLTag();
    }
    
    /**
     * Adds elements to the table. Allowed: <CAPTION> and <TR> .
     *
     * @param captionElement    the elements of CaptionElement type.
     */
    
    public final void addElement(CaptionElement captionElement) {
	
	vTable.addElement(captionElement.getHTMLTag());
	setHTMLTag();
        
    }
    
    public final void addElement(TableRowElement tableRowElement) {
	
	vTable.addElement(tableRowElement.getHTMLTag());
	setHTMLTag();
	
    }
    
    /**
     * Constructs the HTML tag.
     */
    
    protected final void setHTMLTag() {
	
	StringBuffer buf = new StringBuffer();
	
	buf.append("<TABLE");
	if (border > -1) {
	    buf.append(" BORDER=");
	    buf.append(border);
	}
	if (cellPadding > 0) {
	    buf.append(" CELLPADDING=");
	    buf.append(cellPadding);
	}
	if (cellSpacing > 0) {
	    buf.append(" CELLSPACING=");
	    buf.append(cellSpacing);
	}
	if (width > 0 || widthStr != null) {
	    buf.append(" WIDTH=");
	    if ( width > 0 )
		buf.append(width);
	    else
		buf.append(widthStr);
	}
	if ( classStr != null ) {
	    buf.append(" CLASS=\"");
	    buf.append(classStr);
	    buf.append("\"");
	}
	
	if (height > 0 || heightStr != null) {
	    buf.append(" HEIGHT=");
	    if ( width > 0 )
		buf.append(height);
	    else
		buf.append(heightStr);
	}
	buf.append(">");
	
	Enumeration<String> e = vTable.elements();
	while (e.hasMoreElements()) {
	    String tableElements = e.nextElement();
	    buf.append(tableElements);
	}
	
	buf.append("</TABLE>");
	tag = buf.toString(); 
    }
}

