package net.beshkenadze.android.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class MyContentProviderUtils {
	public static String getNameString(Context context, Uri uri) {
		String scheme = uri.getScheme();
		String fileName = null;
		if (scheme.equals("file")) {
			fileName = uri.getLastPathSegment();
		} else if (scheme.equals("content")) {
			String[] proj = { MediaStore.Images.Media.TITLE };
			Cursor cursor = context.getContentResolver().query(uri, proj, null,
					null, null);
			if (cursor != null && cursor.getCount() != 0) {
				int columnIndex = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.TITLE);
				cursor.moveToFirst();
				fileName = cursor.getString(columnIndex);
			}
		}
		return fileName;
	}
	public static String getMimeTypeString(Context context, Uri uri) {
		String scheme = uri.getScheme();
		String fileName = null;
		if (scheme.equals("content")) {
			String[] proj = { MediaStore.Images.Media.MIME_TYPE };
			Cursor cursor = context.getContentResolver().query(uri, proj, null,
					null, null);
			if (cursor != null && cursor.getCount() != 0) {
				int columnIndex = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.MIME_TYPE);
				cursor.moveToFirst();
				fileName = cursor.getString(columnIndex);
			}
		}
		return fileName;
	}
	
	public static String getRealPathFromURI(Context context, Uri uri) {
		String scheme = uri.getScheme();
		String fileName = null;
		if (scheme.equals("content")) {
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = context.getContentResolver().query(uri, proj, null,
					null, null);
			if (cursor != null && cursor.getCount() != 0) {
				int columnIndex = cursor
						.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				fileName = cursor.getString(columnIndex);
			}
		}
		return fileName;
    }
}
