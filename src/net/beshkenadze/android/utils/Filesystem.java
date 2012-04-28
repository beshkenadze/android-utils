package net.beshkenadze.android.utils;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;

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
	public static void deleteRecursive(File fileOrDirectory) {
	    if (fileOrDirectory.isDirectory())
	        for (File child : fileOrDirectory.listFiles())
	        	deleteRecursive(child);

	    fileOrDirectory.delete();
	}
	
	public static String getUpPath(String path) {
		if(path == null) return "/";
		ArrayList<String> pathArray = new ArrayList<String>(
				Arrays.asList(path.split("/")));
		if (pathArray.size() <= 1) {
			return null;
		}
		pathArray.remove(pathArray.size() - 1);
		String upDir = MyStringUtils.implodeArray(pathArray, "/");
		if (upDir.isEmpty())
			return "/";
		else
			return upDir;
	}
	public static String getFileNameFromPath(String path) {
		if (path == null) {
			return null;
		}
		ArrayList<String> urlArray = new ArrayList<String>(Arrays.asList(path
				.split("/")));
		String encodedName = urlArray.get(urlArray.size() - 1).trim();
		try {
			return URLDecoder.decode(encodedName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
		}
		return encodedName;
	}
	public static long getSizeRecursive(File dir) {
	    long result = 0;
	    if (dir.isDirectory())
	        for (File child : dir.listFiles())
	        	getSizeRecursive(child);

	    result += dir.length();
	    return result; // return the file size
	}
}
