package net.beshkenadze.android.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

public class Asset {
	static public AssetFileDescriptor getAssetFD(Context c, String filename) {
		try {
			AssetFileDescriptor afd = c.getAssets().openFd(filename);
			return afd;
		} catch (IOException e) {
		}
		return null;
	}
	static public InputStream getAsset(Context c, String filename) {
		AssetManager mngr = c.getAssets();
		InputStream inputStream = null;
		try {
			inputStream = mngr.open(filename);
			if (inputStream.available() > 0) {
				return inputStream;
			}
		} catch (IOException e) {
			Debug.e("Error:" + e);
		}
		return inputStream;
	}

	static public String getAssetToString(Context c, String filename) {
		InputStream inputStream = getAsset(c, filename);
		String line = null;
		if (inputStream != null) {
			BufferedReader r = new BufferedReader(new InputStreamReader(
					inputStream));
			StringBuilder total = new StringBuilder();
			try {
				while ((line = r.readLine()) != null) {
					total.append(line);
					total.append("\n");
				}
			} catch (IOException e) {
				Debug.e("Error:" + e);
			}
			try {
				inputStream.close();
			} catch (IOException e) {
			}
			return total.toString();
		}else{
			Debug.e("Empty inputStream");
		}

		return line;
	}
	static public int[] getDisplaySize(Context context) {
		Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		int height = display.getHeight();
		int sizes[]= {width, height};
		return sizes;
	}
	static public float getDisplayDencity(Context context) {
		DisplayMetrics dm = context.getResources().getDisplayMetrics();
		return dm.density;
	}
}
