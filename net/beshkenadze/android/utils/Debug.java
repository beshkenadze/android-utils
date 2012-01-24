package net.beshkenadze.android.utils;

import java.util.Date;

import android.util.Log;

public class Debug {
	private static final String TAG = "LOGGER";
	private static final boolean DEBUG = false;

	public static void i(String string) {
		if (DEBUG) {
			StackTraceElement[] trace = new Throwable().getStackTrace();
			String fileName = trace[1].getFileName();
			String methodName = trace[1].getMethodName();
			int lineNumber = trace[1].getLineNumber();

			Date date = new Date();
			String currentTime = date.getHours() + ":" + date.getMinutes()
					+ ":" + date.getSeconds();
			Log.i(TAG, currentTime + ": message: " + string + " in " + fileName
					+ ", called " + methodName + " on " + lineNumber);
		}
	}

	public static void e(String string) {
		if (DEBUG) {
			StackTraceElement[] trace = new Throwable().getStackTrace();
			String fileName = trace[1].getFileName();
			String methodName = trace[1].getMethodName();
			int lineNumber = trace[1].getLineNumber();

			Date date = new Date();
			String currentTime = date.getHours() + ":" + date.getMinutes()
					+ ":" + date.getSeconds();
			Log.e(TAG, currentTime + ":!!!error!!!: " + string + " in "
					+ fileName + ", called " + methodName + " on " + lineNumber);
		}
	}

	public static void e(String string, StackTraceElement[] stackTrace) {
		if (DEBUG) {
			String fileName = stackTrace[1].getFileName();
			String methodName = stackTrace[1].getMethodName();
			int lineNumber = stackTrace[1].getLineNumber();

			Date date = new Date();
			String currentTime = date.getHours() + ":" + date.getMinutes()
					+ ":" + date.getSeconds();
			Log.e(TAG, currentTime + ":!!!error!!!: " + string + " in "
					+ fileName + ", called " + methodName + " on " + lineNumber);
		}
	}
}
