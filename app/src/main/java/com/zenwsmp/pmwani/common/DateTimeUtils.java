/*
 * Copyright (c) 2017. Suthar Rohit
 * Developed by Suthar Rohit for NicheTech Computer Solutions Pvt. Ltd. use only.
 * <a href="http://RohitSuthar.com/">Suthar Rohit</a>
 *
 * @author Suthar Rohit
 */

package com.zenwsmp.pmwani.common;

import android.text.format.DateUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

/**
 * Matrubharti(com.nichetech.common) <br />
 * Developed by <b><a href="http://RohitSuthar.com/">Suthar Rohit</a></b>  <br />
 * on 15/12/15.
 *
 * @author Suthar Rohit
 */
public class DateTimeUtils {

    public static final String SERVER_FORMAT_DATE = "yyyy-MM-dd";
    public static final String SERVER_FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String OUTPUT_FORMAT_WITH_DAY = "MMM dd, yyyy\nEEEE";
    public static final String OUTPUT_FORMAT_WITH_DAY_FULL = "EEEE MMM dd, yyyy";
    public static final String OUTPUT_FORMAT = "MMM dd, yyyy";
    public static final String OUTPUT_FORMAT_DOB = "dd-MMM-yyyy";
    public static final String OUTPUT_FORMAT_FULL_DATE = "EEE, dd MMM";
    public static final String OUTPUT_FORMAT_DATE_TIME = "dd MMM, yyyy hh:mm a";
    public static final String SERVER_FORMAT1 = "dd-MM-yyyy HH:mm:ss";
    public static final String CALENDAR_DATE_FORMAT = "dd-MMMM-yyyy";
    public static final String FORMAT_DATE = "dd-MM-yyyy";

    public static final String DEMO = "EEEE, MMM dd, yyyy";
    public static final String DAY_NAME = "EEEE";
    public static final String MONTH_NAME = "MMM";
    public static final String DATE_F = "dd MMM yyyy";

    public static String getCurrentDateTimeMix() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault());
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return "0000-00-00 00:00:00";
        }
    }

    public static Date getTodayDate() {
        return Calendar.getInstance().getTime();
    }

    public static Date getTomorrowDate() {
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        return  dt;
    }

    public static String getCurrentDateTime() {
        try {
            Locale l = Locale.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat(SERVER_FORMAT_DATE_TIME, l);
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return "0000-00-00 00:00:00";
        }
    }

    public static String changeDateTimeFormat(@NonNull String dateTime,
                                              @NonNull String fromFormat,
                                              @NonNull String toFormat) {
        try {
            DateFormat fromSDF = new SimpleDateFormat(fromFormat, Locale.getDefault());
            //fromSDF.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
            Date date = fromSDF.parse(dateTime);
            DateFormat toSDF = new SimpleDateFormat(toFormat, Locale.getDefault());
            toSDF.setTimeZone(TimeZone.getDefault());
            return toSDF.format(Objects.requireNonNull(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return dateTime;
        }
    }

    public static String dateToString(@NonNull Date dateTime, @NonNull String format) {
        try {
            DateFormat fromSDF = new SimpleDateFormat(format, Locale.getDefault());
            //fromSDF.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
            DateFormat toSDF = new SimpleDateFormat(format, Locale.getDefault());
            toSDF.setTimeZone(TimeZone.getDefault());
            return toSDF.format(dateTime);
        } catch (Exception e) {
            e.printStackTrace();
            return dateTime.toString();
        }
    }

    public static String getCurrentDate() {
        Calendar c = Calendar.getInstance();
        int myYear = c.get(Calendar.YEAR);
        int myMonth = c.get(Calendar.MONTH);
        int myDay = c.get(Calendar.DAY_OF_MONTH);
        String moy1;
        String dom1;

        if (myMonth < 9) {
            moy1 = "0" + (myMonth + 1);
        } else {
            moy1 = String.valueOf(myMonth + 1);
        }
        if (myDay < 9) {
            dom1 = "0" + myDay;
        } else {
            dom1 = String.valueOf(myDay);
        }
        return myYear + "-" + moy1 + "-" + dom1;
    }

    public static String getCurrentDateWithFormat(String dateTime) {
        try {
            Locale l = Locale.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat(dateTime, l);
            return sdf.format(new Date());
        } catch (Exception e) {
            e.printStackTrace();
            return "0000-00-00 00:00:00";
        }
    }

    public static String getDayOldDateTime(int old_day) {
        try {
            Locale l = Locale.getDefault();
            SimpleDateFormat sdf = new SimpleDateFormat(SERVER_FORMAT_DATE_TIME, l);
            Calendar now = Calendar.getInstance();
            now.add(Calendar.DAY_OF_MONTH, old_day);

            Date date = sdf.parse(now.get(Calendar.YEAR) + "-" + (now.get(Calendar.MONTH) + 1) + "-"
                    + now.get(Calendar.DATE) + " " + now.get(Calendar.HOUR_OF_DAY) + ":"
                    + now.get(Calendar.MINUTE) + ":" + now.get(Calendar.SECOND));

            return sdf.format(Objects.requireNonNull(date));
        } catch (Exception e) {
            e.printStackTrace();
            return "0000-00-00 00:00:00";
        }
    }

    public static Date stringToDate(String date_time) {
        return stringToDate(date_time, SERVER_FORMAT_DATE_TIME);
    }


    public static Date stringToDate(@NonNull String date_time, @NonNull String format) {
        Date date = new Date();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
            //sdf.setTimeZone(TimeZone.getTimeZone("Asia/Calcutta"));
            date = sdf.parse(date_time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getLastMonthName() {
        Calendar cal1 = Calendar.getInstance();
        cal1.add(Calendar.DAY_OF_MONTH, -31);

        Calendar cal2 = Calendar.getInstance();
        cal2.add(Calendar.DAY_OF_MONTH, -60);

        if (cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH)) {
            return dateToString(cal2.getTime(), "MMMM");
        } else {
            return dateToString(cal2.getTime(), "MMMM") + "-" + dateToString(cal1.getTime(), "MMM");
        }
    }

    public static String getRelativeTimeSpanString(String dateTime) {
        try {
            long now = System.currentTimeMillis();
            Date yourTime = stringToDate(dateTime);
            return (String) DateUtils.getRelativeTimeSpanString(yourTime.getTime(), now, DateUtils.FORMAT_ABBREV_WEEKDAY);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static long getMaxDate(int maxDays) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, maxDays);
        //Log.e(TAG,"maxDate: ${millieSecondsToDate(cal.timeInMillis,SERVER_FORMAT_DATE)}")
        return cal.getTimeInMillis();
    }


    public static long getMaxDate1(String dt, int maxDays) {
        //String dt = "2018-12-12";  // Start event_date
        SimpleDateFormat sdf = new SimpleDateFormat(SERVER_FORMAT_DATE);
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(Objects.requireNonNull(sdf.parse(dt)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        cal.add(Calendar.DATE, maxDays);
        Log.e("DATEUTIL", "maxDate: " + millieSecondsToDate(cal.getTimeInMillis(), SERVER_FORMAT_DATE));
        return cal.getTimeInMillis();
    }

    public static long getMinDate(int maxHours) {
        Calendar cal = Calendar.getInstance();
        cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.HOUR_OF_DAY) + maxHours, 0, 0);
        return cal.getTimeInMillis();
    }

    public static Date getDate(int year, int monthOfYear, int dayOfMonth) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, monthOfYear);
        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        return cal.getTime();
    }

    public static String millieSecondsToDate(long milliSeconds, String strDateFormat) {
        DateFormat dateFormat = new SimpleDateFormat(strDateFormat, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return dateFormat.format(calendar.getTime());
    }

    // Convert String to date
    public static Date stringToDate1(String dateTime, String strDateFormat) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(strDateFormat, Locale.getDefault());
        try {
            return dateFormat.parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }


    public   static void getCurrentWeekDays(){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setFirstDayOfWeek(Calendar.MONDAY);
        calendar1.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);

        String[] days = new String[7];
        for (int i = 0; i < 7; i++)
        {
            days[i] = DateTimeUtils.dateToString(calendar1.getTime(),DateTimeUtils.SERVER_FORMAT_DATE);
            calendar1.add(Calendar.DAY_OF_MONTH, 1);
            Log.e("weekdays",""+days[i]);
        }


        Calendar calendar = Calendar.getInstance();
        // set the year,month and day to something else
       // calendar.set(2020, 1, 28);
        Log.e("sWeekGetTime",""+calendar.getTime());
        //int current_week = calendar.get(Calendar.WEEK_OF_MONTH);
        //Log.e("CurrentWeek",""+current_week);

        //first day of week
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date firstDate = calendar.getTime();
        Log.e("weekFirstDate",""+firstDate);
        String strWeekFirstDate = DateTimeUtils.dateToString(calendar.getTime(),DateTimeUtils.SERVER_FORMAT_DATE);
        Log.e("strWeekFirstDate",""+strWeekFirstDate);

        //last day of week
        calendar.add(Calendar.DATE, 6);
        Date lastDate = calendar.getTime();
        Log.e("weekLastDate",""+lastDate);
        String strWeekLastDate = DateTimeUtils.dateToString(calendar.getTime(),DateTimeUtils.SERVER_FORMAT_DATE);
        Log.e("strWeekLastDate",""+strWeekLastDate);

    }
}
