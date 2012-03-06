package net.beshkenadze.android.utils;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class MyWindowActivity implements OnClickListener {
	private Context mContext;
	private Class<?> mClass;

	public MyWindowActivity(Context applicationContext,
			Class<?> aClass) {
		mContext = applicationContext;
		mClass = aClass;
	}

	public MyWindowActivity(Context applicationContext, Intent i) {
		mContext = applicationContext;
	}

	public static void openWindow(Context c, Intent i) {
		c.startActivity(i);
	}

	public static void openWindow(Context c, Class<?> aClass) {
		c.startActivity(new Intent(c,aClass));
	}

	@Override
	public void onClick(View v) {
		openWindow(mContext, mClass);
	}

	public void openWindow() {
		openWindow(mContext, mClass);
	}
}
