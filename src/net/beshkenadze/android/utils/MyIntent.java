package net.beshkenadze.android.utils;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.MimeTypeMap;

public class MyIntent {
	public static Intent getChooserIntent(Context c, File file, String title) {
		final Intent intent = new Intent(Intent.ACTION_VIEW);
		final Uri data = Uri.fromFile(file);
		final String type = MimeTypeMap.getSingleton()
				.getMimeTypeFromExtension(
						MimeTypeMap.getFileExtensionFromUrl((Uri.fromFile(file)
								.toString())));
		intent.setDataAndType(data, type);
		return Intent.createChooser(intent, title);
	}
}
