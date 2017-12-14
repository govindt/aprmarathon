/*
 * @(#)SelectElement.java	1.3 01/07/24
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
 * The SelectElement class returns a string in an HTML <SELECT></SELECT> format.
 * <BR><BR>
 * Usage:
 * SelectElement str = new SelectElement("product", Hashtable list);
 * <BR>
 * Assuming that Hashtable list contains the following:
 *      Key     Name
 *      ---     ----
 *      A7K     A7000
 *      E10K    E10000
 *      A5x     A5x00
 *      CLU     Cluster
 *
 * str.getHTMLTag();
 * <BR><BR>
 * output: <SELECT name='product'>
 *         <OPTION VALUE='A7K'>A7000
 *         <OPTION VALUE='E10K'>E10000
 *         <OPTION VALUE='A5x'>A5x00
 *         <OPTION VALUE='CLU'>Cluster
 *         </SELECT>
 * <BR><BR>
 * You can also use this as:
 * <BR><BR>
 * return (new SelectElement("product", Hashtable list).getHTMLTag());
 * <BR>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class SelectElement extends HTMLElementObject { 

    private Hashtable<String,String> hList;   // Name-Value hash-table
    private Vector<String> nList;      // Name List
    private Vector<Integer> vList;      // Value List
    private String selectedValue;
    private int size;
	private boolean multiple = false;

    /**
     * Constructs this object,
     *
     */

    public SelectElement() {

        super();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param name            the name of the select list 
     */

    public SelectElement(String name) {

        this.name = name;

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param name                     the name of the select list 
     * @param hList                     the name of the hashtable that contains the options 
     */

    public SelectElement(String name, 
			 Hashtable<String,String> hList) {

        this.name = name;
        this.hList = hList;

        setHTMLTag();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param name                      the name of the select list
     * @param hList                     the name of the hashtable that contains the options
     * @param selectedValue        the selected value 
     * @param size            the select size
     */

    public SelectElement(String name,
                         Hashtable<String,String> hList,
			 String selectedValue,
			 int size) {

        this.name = name;
        this.hList = hList;
	this.selectedValue = selectedValue;
	this.size = size;

        setHTMLTag();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param name                      the name of the select list
     * @param vList                     the name of the vector that contains the options
     */

    public SelectElement(String name,
                         Vector<Integer> vList) {

        this.name = name;
        this.vList = vList;

        setHTMLTag();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param name                      the name of the select list
     * @param nList                     the name of the vector that contains the option names
     * @param vList                     the name of the vector that contains the option values
     */

    public SelectElement(String name,
                         Vector<String> nList,
                         Vector<Integer> vList) {

        this.name = name;
        this.nList = nList;
        this.vList = vList;

        setHTMLTag();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param name                      the name of the select list
     * @param vList                     the name of the vector that contains the options
     * @param selectedValue        the selected value
     * @param size            the select size
     */

    public SelectElement(String name,
                         Vector<Integer> vList,
			 String selectedValue,
			 int size) {

        this.name = name;
        this.vList = vList;
        this.selectedValue = selectedValue;
        this.size = size;

        setHTMLTag();

    }

    /**
     * Constructs this object with the specified statement, input parameters
     * and output parameters.
     *
     * @param name                      the name of the select list
     * @param nList                     the name of the vector that contains the option names
     * @param vList                     the name of the vector that contains the option values
     * @param selectedValue        the selected value
     * @param size            the select size
     */

    public SelectElement(String name,
                         Vector<String> nList,
                         Vector<Integer> vList,
                         String selectedValue,
                         int size) {

        this.name = name;
        this.nList = nList;
        this.vList = vList;
        this.selectedValue = selectedValue;
        this.size = size;

        setHTMLTag();

    }


    /**
     * Sets the options for the select list.
     *
     * @param hList                     the name of the hashtable that contains the options 
     */

    public final void setList(Hashtable<String,String> hList) {

        this.hList = hList;
        setHTMLTag();
    }

    /**
     * Sets the options for the select list.
     *
     * @param vList                     the name of the vector that contains the options
     */

    public final void setList(Vector<Integer> vList) {

        this.vList = vList;
        setHTMLTag();
    }

    /**
     * Sets the options for the select list.
     *
     * @param nList                     the name of the vector that contains the option names
     * @param vList                     the name of the vector that contains the option values
     */

    public final void setList(Vector<String> nList, Vector<Integer> vList) {

        this.nList = nList;
        this.vList = vList;
        setHTMLTag();
    }

    /**
     * Sets the options for the select list.
     *
     * @param selectedValue              the selected value 
     */

    public final void setSelectedValue(String selectedValue) {

        this.selectedValue = selectedValue;
        setHTMLTag();
    }

	/**
     * Sets the multiple attribute for the select list.
     *
     * @param multiple  the boolean atribute
     */

    public final void setMultiple(boolean multiple)  {

        this.multiple = multiple;
        setHTMLTag();
    }

    /**
     * Sets the options for the select size 
     *
     * @param size                 the size 
     */

    public final void setSize(int size) {

        this.size = size; 
        setHTMLTag();
    }


    /**
     * Constructs the HTML tag.
     */

    protected final void setHTMLTag() {
		
        StringBuffer buf = new StringBuffer();
		
        if (name != null && !name.equals("") && (hList != null || vList != null)) { 
            buf.append("<");
            buf.append(SELECT);
			if ( multiple )
				buf.append(" MULTIPLE");
            buf.append(" name=\"");
            buf.append(name);
            buf.append("\"");
            if (size > 0) {
                buf.append(" size=");
				buf.append(size);
            }
            if (onChangeScript != null) {
				buf.append(" onChange=\"");
				buf.append(onChangeScript);
            	buf.append("\"");
            }
            buf.append(">");
			
            if (hList != null) {
                for (Enumeration<String> e = hList.keys(); e.hasMoreElements();) {
                    String hName = null;
                    String hDesc = null;
					try {
						hName =	e.nextElement().toString();
						hDesc = hList.get(hName).toString();
					} catch (NullPointerException npe) {
						hName =	"";
						hDesc = "";
					}
					
                    buf.append("<OPTION");
					if ( multiple ) {
						if ( selectedValue != null ) {
							if ( selectedValue.indexOf(",") != -1) {
								StringTokenizer st = new StringTokenizer(selectedValue, ",");
								while ( st.hasMoreTokens() ) {
									String token = st.nextToken();
									if ( token.equals(hName) ) 
										buf.append(" SELECTED");
								}
							} else { // Only one selected 
								if ( selectedValue.equals(hName))
									buf.append(" SELECTED");
							}
						}
					}
					else {
						if (selectedValue != null && selectedValue.equals(hName)) {
							buf.append(" SELECTED");
						}
					}
                    buf.append(" VALUE=\"");
                    buf.append(hName);
                    buf.append("\">");
                    buf.append(hDesc);
                }
            } else if (vList != null) {
            	for (int i = 0; i < vList.size(); i++) {
                	String vElement = null;
			try {
				vElement = vList.elementAt(i).toString();
			} catch (NullPointerException npe) {
				vElement = "";
			}
                    	buf.append("<OPTION");
			if ( multiple ) {
				if ( selectedValue != null ) {
					if ( selectedValue.indexOf(",") != -1) {
						StringTokenizer st = new StringTokenizer(selectedValue, ",");
						while ( st.hasMoreTokens() ) {
							String token = st.nextToken();
							if ( token.equals(vElement) ) {
								buf.append(" SELECTED");
							}
						}
					} else { // Only one selected 
						if ( selectedValue.equals(vElement))
							buf.append(" SELECTED");
					}
				}
			}
			else {
				if (selectedValue != null && selectedValue.equals(vElement)) {
					buf.append(" SELECTED");
				}
			}
                    	buf.append(" VALUE=\"");
                    	buf.append(vElement);
                    	buf.append("\">");
                    	try {
                        	String name = nList.elementAt(i).toString();
                        	buf.append(name);
                    	} catch (NullPointerException npe) {
                        	buf.append(vElement);
                    	}
                }
            }
            buf.append("</");
            buf.append(SELECT);
            buf.append(">");
            tag = buf.toString() + "\n"; 
        }
    }
}

