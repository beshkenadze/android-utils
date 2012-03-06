package net.beshkenadze.android.utils;

import java.io.File;

import android.os.Environment;

public class Filesystem {
	@SuppressWarnings("unused")
	public static boolean isExternalMemoryAvailble() {
		boolean mExternalStorageAvailable = false;
		boolean mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		return mExternalStorageAvailable;
	}
	public static File getExternalDir(){
		return Environment.getExternalStorageDirectory();
	}
}
