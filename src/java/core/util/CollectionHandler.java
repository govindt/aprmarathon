/*
 * @(#)CollectionHandler.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;


import java.util.Enumeration;
import java.util.Vector;

/**
 * A utility class for Java "util" collection classes.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class CollectionHandler {

    /**
     * Adds the contents of the two specified Vectors to the first one,
     * and return the forst Vector. Allocates a new Vector, if the first
     * Vector is null; returns the first Vector if the second one is null.
     *
     * @param v1      a Vector, to be added into
     * @param v2      a Vector, to be added
     * @return        the collection of elements of specified Vectors
     */

    public static Vector<Object> add(Vector<Object> v1, Vector<Object> v2) {

        if (v2 == null) {
            return v1;
        }

        if (v1 == null) {
            v1 = new Vector<Object>();    
        }

	@SuppressWarnings("rawtypes")
        Enumeration iterator = v2.elements();

        while (iterator.hasMoreElements()) {

            Object element = iterator.nextElement();

            if (! v1.contains(element)) {
                v1.addElement(element);
            }
        }    

        return v1;
    }

    /**
     * Sorts the contents of the specified Vector by the String value of the
     * elements in the Vector.
     *
     * @param list    the Vector to be sorted
     */

    public static void sortByStringValue(Vector<Object> list) {

        if ((list == null) || (list.size() <= 1)) {
            return;
        }

        int size = list.size();

        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {

                Object element1 = list.elementAt(i);
                Object element2 = list.elementAt(j);

                String value1 = null;
                String value2 = null;

                try {
                    value1 = list.elementAt(i).toString();
                } catch (NullPointerException npe) {
                }

                try {
                    value2 = list.elementAt(j).toString();
                } catch (NullPointerException npe) {
                }

                try {
                    if (value1.compareTo(value2) > 0) {
                        list.setElementAt(element2, i);
                        list.setElementAt(element1, j);
                    }
                } catch (NullPointerException npe) {
                    continue;
                }
            }
        }
    }
}
