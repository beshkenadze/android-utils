package net.beshkenadze.android.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import android.content.Context;
import android.os.AsyncTask;

public class AsyncDownload extends AsyncTask<URL, Integer, File> {
	private Context context;
	public String[] mimetypes = { "jpg", "jpeg", "png", "xml" };
	private onLoadListener listener;
	public AsyncDownload(Context c, onLoadListener listener) {
		context = c;
		this.listener = listener;
	}

	@Override
	protected File doInBackground(URL... urls) {
		if (urls.length > 0) {
			return this.downloadFile(urls[0]);
		} else {
			return null;
		}
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

	protected void onProgressUpdate(Integer... progress) {
		Debug.i("progress:" + progress[0]);
	}
	protected void onPostExecute(File file) {
		if(file != null) {
			listener.onLoad((Object) file);
		}else{
			listener.onLoadError("Can't download");
		}
	}

	public void get(URL url) {
		execute(url);
	}

}
