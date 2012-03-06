package net.beshkenadze.android.utils;

public interface OnRequestListener {
	public void onLoadError(String url);
	public void onLoad(String url, Object data);
}
