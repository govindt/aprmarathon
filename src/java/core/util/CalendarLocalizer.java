/*
 * @(#)CalendarLocalizer.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.Hashtable;
import java.util.Locale;
import java.util.TimeZone;

/**
 * A singleton utility class to handle application localization.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class CalendarLocalizer extends Localizer {

    protected TimeZone timeZone;

    private static Hashtable<String,CalendarLocalizer> calendarLocalizers = new Hashtable<String,CalendarLocalizer>();

    // Disable public construction.

    protected CalendarLocalizer(Locale locale, TimeZone timeZone) {

        super(locale);
        this.timeZone = timeZone;
    }

    /**
     * Returns the reference to its singleton instance for the locale.
     *
     * @return    the singleton localizer instance for the locale
     */

    public static synchronized Localizer getInstance(Locale forLocale,
                                                     TimeZone timeZone) {

        if (forLocale == null) {
            forLocale = Locale.getDefault();
        }

        if (timeZone == null) {
            timeZone = TimeZone.getDefault();
        }

        String key = forLocale.toString() + "_" + timeZone.getID();

        CalendarLocalizer loc = calendarLocalizers.get(key);

        if (loc == null) {
            loc = new CalendarLocalizer(forLocale, timeZone);
            calendarLocalizers.put(key, loc);
        }

        return loc;
    }

    /**
     * Returns the localized calendarted string for the specified date value.
     *
     * @param date        date value
     * @return            localized date
     */

    public String getDate(Date date, int style) {

        DateFormat dateFormat = DateFormat.getDateInstance(style, locale);
        Calendar cal = Calendar.getInstance(timeZone, locale);

        cal.setLenient(false);
        cal.setTime(date);
        dateFormat.setCalendar(cal);

        return dateFormat.format(date);
    }

    /**
     * Returns the localized calendarted string for the specified date-time
     * value.
     *
     * @param dateTime        date-time value
     * @return                localized date-time
     */

    public String getDateTime(Date dateTime,
                              int dateStyle,
                              int timeStyle) {

        DateFormat dateFormat
            = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);

        Calendar cal = Calendar.getInstance(timeZone, locale);

        cal.setLenient(false);
        cal.setTime(dateTime);
        dateFormat.setCalendar(cal);

        return dateFormat.format(dateTime);
    }
}
