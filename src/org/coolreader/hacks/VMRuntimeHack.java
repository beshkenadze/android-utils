package org.coolreader.hacks;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.util.Log;

public class VMRuntimeHack {
	private Object mRuntime = null;
	private Method mTrackAllocation = null;
	private Method mTrackFree = null;
	public static final String TAG = "bmphack";

	public boolean trackAlloc(long size) {
		if (mRuntime == null)
			return false;
		try {
			Object res = mTrackAllocation.invoke(mRuntime, Long.valueOf(size));
			return (res instanceof Boolean) ? (Boolean) res : true;
		} catch (IllegalArgumentException e) {
			return false;
		} catch (IllegalAccessException e) {
			return false;
		} catch (InvocationTargetException e) {
			return false;
		}
	}

	public boolean trackFree(long size) {
		if (mRuntime == null)
			return false;
		try {
			Object res = mTrackFree.invoke(mRuntime, Long.valueOf(size));
			return (res instanceof Boolean) ? (Boolean) res : true;
		} catch (IllegalArgumentException e) {
			return false;
		} catch (IllegalAccessException e) {
			return false;
		} catch (InvocationTargetException e) {
			return false;
		}
	}

	public VMRuntimeHack() {
		boolean success = false;
		try {
			Class<?> cl = Class.forName("dalvik.system.VMRuntime");
			Method getRt = cl.getMethod("getRuntime", new Class[0]);
			mRuntime = getRt.invoke(null, new Object[0]);
			mTrackAllocation = cl.getMethod("trackExternalAllocation",
					new Class[] { long.class });
			mTrackFree = cl.getMethod("trackExternalFree",
					new Class[] { long.class });
			success = true;
		} catch (ClassNotFoundException e) {
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
		} catch (IllegalArgumentException e) {
		} catch (IllegalAccessException e) {
		} catch (InvocationTargetException e) {
		}
		if (!success) {
			Log.i(TAG, "VMRuntime hack does not work!");
			mRuntime = null;
			mTrackAllocation = null;
			mTrackFree = null;
		}
	}
}
