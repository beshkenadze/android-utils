package net.beshkenadze.android.network;

import java.io.File;

import net.beshkenadze.android.utils.Debug;
import net.beshkenadze.android.utils.Filesystem;
import net.beshkenadze.android.utils.Utils;

import android.content.Context;


public class Cache {
	private File cacheDir;

	public Cache(Context c) {
		cacheDir = Cache.getDir(c);
		Debug.i("Cache Dir:" + cacheDir);
	}
	public static File getFile(Context c, String url, String ext) {
		String filename = Utils.md5(url);
		if(filename.length() <= 0) return null;
		File tmpDirExt = new File(Cache.getDir(c)+"/"+ext);
		if(tmpDirExt.exists() == false) {
			tmpDirExt.mkdirs();
		}
		File file = new File(tmpDirExt.toString()+"/"+filename+"."+ext);
		return file;
	}
	public static File getDir(Context c) {
		File dir = null;
		if(Filesystem.isExternalMemoryAvailble()) {
			String tmpCacheDir = Filesystem.getExternalDir().toString();
			tmpCacheDir += "/Android/data/com.mobileup.pizza/cache";
			dir = new File(tmpCacheDir);
			if(dir.exists()==false) {
				dir.mkdirs();
			}
		}else{
			dir = c.getCacheDir();
		}
		return dir;
	}
}
