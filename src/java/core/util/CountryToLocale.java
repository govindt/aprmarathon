/*
 * @(#)CountryToLocale.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;

// Import statements
import java.util.*;
import java.text.*;

/**
 * << CLASS DESCRIPTION GOES HERE >>
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class CountryToLocale {

    public static Object[] getLocaleTimeZone( String area ) {

            Locale value = Locale.US; // By Default
            String tmString = "GMT";

            Object ltm[] = new Object[2];
            ltm[0] = value;
            ltm[1] = tmString; 

            if ( area == null || area.equals(""))
                return ltm;

            if (area.equalsIgnoreCase("West")) {
                value = Locale.US;
                tmString = "PST";
            } 
            else if ( area.equalsIgnoreCase("East")) {
                value = Locale.US;
                tmString = "EST";
            }
            else if ( area.equalsIgnoreCase("Central")) {
                value = Locale.US;
                tmString = "CST";
            }    
            else if ( area.equalsIgnoreCase("Canada") ) {
                value = Locale.CANADA;    
                tmString = "PST";
            }
            else if ( area.equalsIgnoreCase("France") ) {
                value = Locale.FRANCE;    
                tmString = "GMT";
            }
            else if ( area.equalsIgnoreCase("Germany") ) {
                value = Locale.GERMANY;    
                tmString = "GMT";
            }
            else if ( area.equalsIgnoreCase("Italy") ) {
                value = Locale.ITALY;    
                tmString = "GMT";
            }
            else if ( area.equalsIgnoreCase("Japan") ) {
                value = Locale.JAPAN;    
                tmString = "GMT";
            }
            else if ( area.equalsIgnoreCase("Korea") ) {
                value = Locale.KOREA;    
                tmString = "GMT";
            }
            else if ( area.equalsIgnoreCase("PRC") ) {
                value = Locale.PRC;    
                tmString = "GMT";
            }
            else if ( area.equalsIgnoreCase("UK") ) {
                value = Locale.UK;    
                tmString = "GMT";
            }
            else {
                value = getLocaleForCountry(area);
                tmString = "GMT";
            }

            if ( value == null )
                value = Locale.US;

            ltm[0] = value;
            ltm[1] = tmString; 
            return ltm;

    }

    public static Locale getLocale( String area ) {

            Locale value = Locale.US; // By Default

            if ( area == null )
                return value;

            if ( area.equalsIgnoreCase("Canada") )
                value = Locale.CANADA;    
            else if ( area.equalsIgnoreCase("France") )
                value = Locale.FRANCE;    
            else if ( area.equalsIgnoreCase("Germany") )
                value = Locale.GERMANY;    
            else if ( area.equalsIgnoreCase("Italy") )
                value = Locale.ITALY;    
            else if ( area.equalsIgnoreCase("Japan") )
                value = Locale.JAPAN;    
            else if ( area.equalsIgnoreCase("Korea") )
                value = Locale.KOREA;    
            else if ( area.equalsIgnoreCase("PRC") )
                value = Locale.PRC;    
            else if ( area.equalsIgnoreCase("UK") )
                value = Locale.UK;    
            else {
                value = getLocaleForCountry(area);
            }

            if ( value == null )
                value = Locale.US;

            return value;
                

    }

    public static Locale getLocaleForCountry(String countryName) {

        if (countryName == null) {
            return null;
        }

        Locale loc[] = DateFormat.getAvailableLocales();

        for(int i=0; i < loc.length; i++){
            if (countryName.equalsIgnoreCase(loc[i].getDisplayCountry()) )
                return loc[i];    
        }

        return null;
    }

}

