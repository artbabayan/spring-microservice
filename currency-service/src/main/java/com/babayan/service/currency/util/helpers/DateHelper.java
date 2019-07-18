package com.babayan.service.currency.util.helpers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author by artbabayan
 */
public class DateHelper {
    public static final String DF_TIME = "HH:mm:ss";
    public static final String DF_DAY = "yyyy-MM-dd";
    public static final String DF_ISO_DATETIME = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
    	public static final DateFormat DF_DAYY = new SimpleDateFormat("yyyy-MM-dd");


    public static String convertFromDate(Date date) {
        // Creating date format
        DateFormat simple = new SimpleDateFormat(DF_DAY);
        Date result = new Date(date.getTime());

        return simple.format(result);
    }

    public static Date fromUnixTime(long unixTime) {
        return new Date(unixTime * 1000);
    }

    public static Date getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime();
    }



    public static Date parseDate(String dateString) {
        return parseDate(dateString, ((SimpleDateFormat) DF_DAYY).toPattern());
    }

    /**
     * Parses provided string to Date using the specified format.
     *
     * @param dateString
     * @param format
     * @return
     */
    public static Date parseDate(String dateString, String format) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            return dateFormat.parse(dateString);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }


    public static void main(String[] args) {
        Calendar calendar = Calendar.getInstance();
        Date time = calendar.getTime();

        System.out.println(convertFromDate(time));

//
//        Date date = fromUnixTime(1563110646);
//        System.out.println(convertFromTimeStamp(date));

    }
}
