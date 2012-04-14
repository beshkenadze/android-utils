package net.beshkenadze.android.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;

import android.content.Context;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.webkit.MimeTypeMap;

public class MyFileUtils {
	static public byte[] readFileToByteArray(File file) throws IOException {
		return org.apache.commons.io.FileUtils.readFileToByteArray(file);
	}

	public static String getContentTypeFromFileString(File file) {
		String type = null;
		if (file != null) {
			type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
					MimeTypeMap.getFileExtensionFromUrl((Uri.fromFile(file)
							.toString())));
		}

		return type;
	}

	public static String getContentTypeFromFileString(String path) {
		String type = null;
		if (path != null) {
			type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(
					MimeTypeMap.getFileExtensionFromUrl((Uri.parse(path)
							.toString())));
		}

		return type;
	}

	public static String getFileNameFromUrl(String path) {
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

	public static String readableFileSize(long size) {
		if (size <= 0)
			return "0";
		final String[] units = new String[] { "B", "KB", "MB", "GB", "TB" };
		int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
		return new DecimalFormat("#,##0.#").format(size
				/ Math.pow(1024, digitGroups))
				+ " " + units[digitGroups];
	}
}
