/*
 * @(#)Localizer.java	1.2 01/02/22
 *
 * Project Name Project
 *
 * Author: Govind Thirumalai 
 */



package core.util;

import java.text.DateFormat;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

/**
 * A singleton utility class to handle application localization.
 *
 * @version	1.0
 * @author	Govind Thirumalai
 * @since	1.0
 */

public class Localizer {

    protected Locale locale;
    protected ResourceBundle resources;

    protected int defaultDateStyle = DateFormat.SHORT;
    protected int defaultTimeStyle = DateFormat.SHORT;

    protected boolean isDateFormatLenient = false;

    private static Hashtable<Locale,Localizer> localizers = new Hashtable<Locale,Localizer>();

    // Disable public construction.

    /*
     * Constructs an object for the specified locale. If no locale is
     * specified, sets to the default locale.
     */

    protected Localizer(Locale locale) {

        if (locale == null) {
            locale = Locale.getDefault();
        }

        this.locale = locale;

        String localePropertiesFile
            = "core.util.LocalizerData";

        resources
            = PropertyResourceBundle.getBundle(localePropertiesFile, locale);
    }

    /*
     * The no-argument constructor.
     */

    protected Localizer() {

        this(null);                                         
    }

    /**
     * Returns the reference to its singleton instance.
     *
     * @return    the singleton localizer instance
     */

    public static synchronized Localizer getInstance() {

        return getInstance(null);
    }


    /**
     * Returns the reference to its singleton instance for the locale.
     *
     * @return    the singleton localizer instance for the locale
     */

    public static synchronized Localizer getInstance(Locale forLocale) {

        if (forLocale == null) {
            forLocale = Locale.getDefault();
        }

        Localizer loc = localizers.get(forLocale);

        if (loc == null) {

            loc = new Localizer(forLocale);
            localizers.put(loc.getLocale(), loc);
        }

        return loc;
    }

    /**
     * Returns the locale.
     *
     * @return    the locale
     */

    public Locale getLocale() {

        return locale;
    }

    /**
     * Returns the default date style.
     *
     * @return    the default date style
     */

    public int getDefaultDateStyle() {

        return defaultDateStyle;
    }

    /**
     * Sets the default date style.
     *
     * @param dateStyle        the default date style
     */

    public void setDefaultDateStyle(int dateStyle) {

        defaultDateStyle = dateStyle;
    }

    /**
     * Returns the default time style.
     *
     * @return    the default time style
     */

    public int getDefaultTimeStyle() {

        return defaultTimeStyle;
    }

    /**
     * Sets the default time style.
     *
     * @param timeStyle        the default time style
     */

    public void setDefaultTimeStyle(int timeStyle) {

        defaultTimeStyle = timeStyle;
    }

    /**
     * Returns whether or not the date/time parsing is lenient.
     *
     * @return    the flag indicating whether date/time parsing is lenient
     */

    public boolean isDateFormatLenient() {

        return isDateFormatLenient;
    }

    /**
     * Sets whether or not the date/time parsing is lenient.
     *
     * @param lenient the flag indicating whether date/time parsing is lenient
     */

    public void setDateFormatLenient(boolean lenient) {

        isDateFormatLenient = lenient;
    }

    /**
     * Returns the localized formatted string for the specified currency value.
     *
     * @param currency        currency value
     * @return                localized currency
     */

    public String getCurrency(double currency) {

        return NumberFormat.getCurrencyInstance(locale).format(currency);
    }

    /**
     * Returns the localized formatted string for the specified currency value.
     *
     * @param currency        currency value
     * @return                localized currency
     */

    public String getCurrency(long currency) {

        return NumberFormat.getCurrencyInstance(locale).format(currency);
    }

    /**
     * Returns the localized formatted string for the specified date value.
     *
     * @param date        date value
     * @return            localized date
     */

    public String getDate(Date date) {

        return getDate(date, defaultDateStyle);
    }

    /**
     * Returns the localized formatted string for the specified date value
     * and style.
     *
     * @param date        date value
     * @param style       date style
     * @return            localized date
     */

    public String getDate(Date date, int style) {

        return DateFormat.getDateInstance(style, locale).format(date);
    }

    /**
     * Returns the localized formatted string for the specified date-time
     * value.
     *
     * @param dateTime        date-time value
     * @return                localized date-time
     */

    public String getDateTime(Date dateTime) {

        return getDateTime(dateTime, defaultDateStyle, defaultTimeStyle);
    }

    /**
     * Returns the localized formatted string for the specified date-time
     * value, date style and time style.
     *
     * @param date            date value
     * @param dateStyle       date style
     * @param timeStyle       time style
     * @return                localized date-time
     */

    public String getDateTime(Date dateTime,
                              int dateStyle,
                              int timeStyle) {

        return DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale).
                          format(dateTime);
    }

    /**
     * Returns the date value for the specified localized formatted string.
     *
     * @param date        localized date
     * @return            date value
     */

    public Date getDate(String date) throws ParseException {

        DateFormat dateFormat
            = DateFormat.getDateInstance(defaultDateStyle, locale);

        dateFormat.setLenient(isDateFormatLenient);

        return dateFormat.parse(date);
    }

    /**
     * Returns the date value for the specified localized formatted string and
     * format style.
     *
     * @param date        date value
     * @param style       date style
     * @return            localized date
     */

    public Date getDate(String date, int style) throws ParseException {

        DateFormat dateFormat = DateFormat.getDateInstance(style, locale);

        dateFormat.setLenient(isDateFormatLenient);

        return dateFormat.parse(date);
    }

    /**
     * Returns the date-time value for the specified localized formatted string.
     *
     * @param dateTime    localized date-time
     * @return            date-time value
     */

    public Date getDateTime(String dateTime) throws ParseException {

        DateFormat dateFormat
            = DateFormat.getDateTimeInstance(defaultDateStyle,
                                              defaultTimeStyle,
                                              locale);

        dateFormat.setLenient(isDateFormatLenient);

        return dateFormat.parse(dateTime);
    }

    /**
     * Returns the date-time value for the specified localized formatted string
     * and date & time format styles. 
     *
     * @param dateTime    localized date-time
     * @param dateStyle   date format style
     * @param timeStyle   time format style
     * @return            date-time value
     */

    public Date getDateTime(String dateTime,
                            int dateStyle,
                            int timeStyle) throws ParseException {

        DateFormat dateFormat
            = DateFormat.getDateTimeInstance(dateStyle, timeStyle, locale);

        dateFormat.setLenient(isDateFormatLenient);

        return dateFormat.parse(dateTime);
    }

    /**
     * Returns the localized formatted string for the specified number value.
     *
     * @param number        number value
     * @return              localized number
     */

    public String getNumber(double number) {

        return NumberFormat.getNumberInstance(locale).format(number);
    }

    /**
     * Returns the localized formatted string for the specified number value.
     *
     * @param number        number value
     * @return              localized number
     */

    public String getNumber(long number) {

        return NumberFormat.getNumberInstance(locale).format(number);
    }

    /**
     * Returns the localized formatted string for the specified key.
     *
     * @param key        localization key
     * @return           localized text
     */

    public String getText(String key) {

        try {
            return resources.getString(key);
        } catch (MissingResourceException mre) {
            return key;
        }
    }

    /**
     * Returns the localized formatted string for the specified key and
     * argument objects to be embedded into the message placeholders.
     *
     * @param key        localization key
     * @param args       array of objects to be embedded into the localized
     *                   message text
     * @return           localized text
     * @see java.text.MessageFormat
     */

    public String getText(String key, Object[] args) {

        try {
            return MessageFormat.format(resources.getString(key), args);
        } catch (MissingResourceException mre) {
            return key;
        }
    }

    /**
     * Convenience method. Returns an array of localized formatted strings for
     * the specified array of keys.
     *
     * @param keys        array of localization keys
     * @return            array of localized text
     */

    public String[] getText(String[] keys) {

        if (keys == null) {
            return null;
        }

        String[] values = new String[keys.length];

        for (int i = 0; i < keys.length; i++) {
            values[i] = getText(keys[i]);
        }

        return values;
    }

    /**
     * Convenience method. Returns the localized date pattern.
     *
     * @return            localized date pattern
     */

    public String getDatePattern() {

        return getDatePattern(defaultDateStyle);
    }

    /**
     * Convenience method. Returns the localized date pattern for the specified
     * date style.
     *
     * @param style       date style
     * @return            localized date pattern
     */

    public String getDatePattern(int style) {

        return ((SimpleDateFormat)
                    DateFormat.getDateInstance(style, locale)).toPattern();
    }

    /**
     * Convenience method. Returns the localized date-time pattern.
     *
     * @return            localized date-time pattern
     */

    public String getDateTimePattern() {

        return getDateTimePattern(defaultDateStyle, defaultTimeStyle);
    }

    /**
     * Returns the localized date-time pattern for the
     * specified date and time styles.
     *
     * @param dateStyle   date style
     * @param timeStyle   time style
     * @return            localized date-time pattern
     */

    public String getDateTimePattern(int dateStyle, int timeStyle) {

        return ((SimpleDateFormat) DateFormat.
            getDateTimeInstance(dateStyle, timeStyle, locale)).toPattern();
    }

    /**
     * Static convenience method. Returns the locale for the specified display
     * name. The display name of locale is contains the language, counrty and
     * variant assembeled into a single string.
     *
     * @param displayName    the display name for the locale
     * @return               the locale
     */

    public static Locale getLocale(String displayName) {

        Locale[] locales = DateFormat.getAvailableLocales();

        if ((displayName == null) || (locales == null)) {
            return null;
        }

        for (int i = 0; i < locales.length; i++) {

            if (displayName.equals(locales[i].getDisplayName())) {
                return locales[i];
            }
        }

        return null;
    }
}
