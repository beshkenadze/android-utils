package net.beshkenadze.android.utils;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

public class MyDisplay {
	private Point mSize;

	public MyDisplay(Context context) {
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		// TODO Hack For API 10
		try {
			if (android.os.Build.VERSION.SDK_INT > 10) {
				display.getSize(size);
				setDisplaySize(size);
			} else {
				size.set(display.getWidth(), display.getHeight());
				setDisplaySize(size);
			}
		} catch (Exception e) {
			// TODO HACK
			size.set(800, 480);
			setDisplaySize(size);
		}

	}

	public int getDisplayWidth() {
		return getDisplaySize().x;
	}

	public int getDisplayHeight() {
		return getDisplaySize().y;

	}

	private Point getDisplaySize() {
		return mSize;
	}

	private void setDisplaySize(Point size) {
		mSize = size;
	}
}
