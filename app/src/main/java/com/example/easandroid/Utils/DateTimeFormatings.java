package com.example.easandroid.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeFormatings {

    private static String sdateformat = "yyyy/MM/dd";
    private static String sdateformat_log = "yyyyMMdd";
    private static String sdatetimeformat_webservice = "yyyy-MM-dd HH:mm:ss";
    private static String sdatetimeformat_webservice2 = "yyyy/MM/dd HH:mm:ss";
    private static String sdatetimeformat_webservice3 = "EEE MMM dd HH:mm:ss z yyyy";
    private static String sdateformat_webservice = "yyyy-MM-dd";
    // private static String sdatetimeformat = "dd\\MM\\yyyy hh:mm aa";
    private static String sdatetimeformat = "yyyy-MM-dd HH:mm:ss";
    private static String sdatetimeformatwithsec = "yyyy/MM/dd HH:mm:ss";
    // private static SimpleDateFormat sdfDateFormating;
    private static String stimeformat = "HH:mm:ss";

    // private static SimpleDateFormat sdfTimeFormating;

    public static String getFormatedDate(int year, int month, int day) {
        SimpleDateFormat sdfDateFormating = new SimpleDateFormat(sdateformat, Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.MONTH, month);
        cal.set(Calendar.DAY_OF_MONTH, day);
        return sdfDateFormating.format(cal.getTime());
    }

    public static String getFormatedTime(int hourOfDay, int min) {
        SimpleDateFormat sdfTimeFormating = new SimpleDateFormat(stimeformat, Locale.ENGLISH);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, min);
        return sdfTimeFormating.format(cal.getTime());
    }

    public static Calendar getNow() {
        return Calendar.getInstance();
    }

    public static Date getDate(String datetime) {
        try {
            SimpleDateFormat sdfDateFormating = new SimpleDateFormat(sdateformat, Locale.ENGLISH);
            return sdfDateFormating.parse(datetime);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return new Date();
    }

    public static Date getDateTimeWeb(String datetime) {
        try {
            SimpleDateFormat sdfDateFormating = null;
            try {
                sdfDateFormating = new SimpleDateFormat(sdatetimeformat_webservice, Locale.ENGLISH);
                return sdfDateFormating.parse(datetime);
            } catch (Exception e) {
                // TODO: handle exception
            }
            try {
                sdfDateFormating = new SimpleDateFormat(sdatetimeformat_webservice2, Locale.ENGLISH);
                return sdfDateFormating.parse(datetime);
            } catch (Exception e) {
                // TODO: handle exception
            }
            try {
                sdfDateFormating = new SimpleDateFormat(sdatetimeformat, Locale.ENGLISH);
                return sdfDateFormating.parse(datetime);
            } catch (Exception e) {
                // TODO: handle exception
            }
            try {
                sdfDateFormating = new SimpleDateFormat(sdatetimeformat_webservice3, Locale.ENGLISH);
                return sdfDateFormating.parse(datetime);
            } catch (Exception e) {
                // TODO: handle exception
            }

            if (sdfDateFormating != null) {
                return sdfDateFormating.parse(datetime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static Date getDateWeb(String datetime) {
        try {
            SimpleDateFormat sdfDateFormating = new SimpleDateFormat(sdateformat_webservice, Locale.ENGLISH);
            return sdfDateFormating.parse(datetime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }
    public static Date getDateWebTime(String datetime) {
        try {
            SimpleDateFormat sdfDateFormating = new SimpleDateFormat(sdatetimeformat, Locale.ENGLISH);
            return sdfDateFormating.parse(datetime);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public Date getDate(String date, String time) {
        try {
            SimpleDateFormat sdfDateFormating = new SimpleDateFormat(sdateformat, Locale.ENGLISH);
            return sdfDateFormating.parse(date + " " + time);
        } catch (Exception e) {
            // TODO: handle exception
        }

        return new Date();
    }

    public static String getDateTime(Date date) {
        try {
            SimpleDateFormat sdfDateFormating = new SimpleDateFormat(sdatetimeformat, Locale.ENGLISH);
            return sdfDateFormating.format(date);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    public static String getDateTimeSec(Date date) {
        try {
            SimpleDateFormat sdfDateFormating = new SimpleDateFormat(sdatetimeformatwithsec, Locale.ENGLISH);
            return sdfDateFormating.format(date);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    public static String getDate(Date date) {
        try {
            SimpleDateFormat sdfDateFormating = new SimpleDateFormat(sdateformat, Locale.ENGLISH);
            return sdfDateFormating.format(date);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }
    public static String getDateForLogs() {
        try {
            SimpleDateFormat sdfDateFormating = new SimpleDateFormat(sdateformat_log, Locale.ENGLISH);
            return sdfDateFormating.format(new Date());
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }

    public static CharSequence getTime(Date date) {
        try {
            SimpleDateFormat sdfDateFormating = new SimpleDateFormat(stimeformat, Locale.ENGLISH);
            return sdfDateFormating.format(date);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return "";
    }
    public static boolean isInWorkingHours() {

        Calendar calOn = Calendar.getInstance();
        Calendar calOff = Calendar.getInstance();

        calOn.set(Calendar.HOUR_OF_DAY, 6);
        calOn.set(Calendar.MINUTE, 00);

        calOff.set(Calendar.HOUR_OF_DAY, 18);
        calOff.set(Calendar.MINUTE, 00);


        int day_of_week = calOn.get(Calendar.DAY_OF_WEEK);

        if(day_of_week == Calendar.SUNDAY){
            return false;
        }

        long ontime = calOn.getTimeInMillis();
        long offttime = calOff.getTimeInMillis();

        long currentTime = System.currentTimeMillis();

        if (currentTime > ontime && currentTime < offttime) {
            return true;
        }
        return false;

    }
    public static boolean isInOfficeHours() {

        return  true;

		/*Calendar calOn = Calendar.getInstance();
		Calendar calOff = Calendar.getInstance();

		calOn.set(Calendar.HOUR_OF_DAY, 8);
		calOn.set(Calendar.MINUTE, 00);

		calOff.set(Calendar.HOUR_OF_DAY, 17);
		calOff.set(Calendar.MINUTE, 00);


		int day_of_week = calOn.get(Calendar.DAY_OF_WEEK);

		if(day_of_week == Calendar.SUNDAY){
			return false;
		}

		if(day_of_week == Calendar.SATURDAY){
			return false;
		}

		long ontime = calOn.getTimeInMillis();
		long offttime = calOff.getTimeInMillis();

		long currentTime = System.currentTimeMillis();

		if (currentTime > ontime && currentTime < offttime) {
			return true;
		}
		return false;*/

    }




}
