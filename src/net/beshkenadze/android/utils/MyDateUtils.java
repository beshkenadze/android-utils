package net.beshkenadze.android.utils;

import java.util.Calendar;

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
}
