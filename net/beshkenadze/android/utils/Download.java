package net.beshkenadze.android.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


import android.content.Context;

public class Download{
	private Context context;
	public String[] mimetypes = { "jpg", "jpeg", "png", "xml" };
	public Download(Context c) {
		context = c;
	}

	private File downloadFile(URL url) {
		String ext = Utils.fileExtention(url.toString()).toLowerCase();
		Boolean access = false;
		for (String type : mimetypes) {
            if (ext.contains(type)) {
            	access = true;
            }
        }
		if(!access) return null;
		File cacheFile = Cache.getFile(context, url.toString(), ext);
		if (cacheFile.length() <= 0) {
			try {
				BufferedInputStream in = new BufferedInputStream(
						url.openStream());
				FileOutputStream out = new FileOutputStream(cacheFile);
				BufferedOutputStream bout = new BufferedOutputStream(out, 1024);
				byte[] data = new byte[1024];
				int x = 0;
				while ((x = in.read(data, 0, 1024)) >= 0) {
					bout.write(data, 0, x);
				}
				out.flush();
				bout.flush();
				out.close();
				bout.close();
				in.close();
			} catch (IOException e) {
				//listener.onLoadError(e.);
			}
		}else{
			Debug.i("File in cache!!!");
		}
		return cacheFile;
	}


	public File get(String url) {
		try {
			return downloadFile(new URL(url));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
