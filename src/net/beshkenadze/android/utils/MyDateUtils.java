package net.beshkenadze.android.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyDateUtils {
	public static Long getToday() {
		Calendar today = Calendar.getInstance();
		long diff = today.getTimeInMillis();
		long days = diff / (24 * 60 * 60 * 1000);
		return days;
	}

	public static Long getTomorrow() {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, 1);
		long diff = today.getTimeInMillis();
		long days = diff / (24 * 60 * 60 * 1000);
		return days;
	}

	public static Long getDayAfterTommorow() {
		Calendar today = Calendar.getInstance();
		today.add(Calendar.DAY_OF_YEAR, 2);
		long diff = today.getTimeInMillis();
		long days = diff / (24 * 60 * 60 * 1000);
		return days;
	}

	public static String formatTimestamp(String format, long time) {
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return formatTimestamp(format, cal);
	}

	public static String formatTimestamp(String format, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	public static String formatTimestamp(String format, Calendar cal) {
		return formatTimestamp(format, cal.getTime());
	}
}
