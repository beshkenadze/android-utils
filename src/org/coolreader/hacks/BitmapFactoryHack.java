package org.coolreader.hacks;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import android.graphics.Bitmap;

public class BitmapFactoryHack {
	private VMRuntimeHack mRuntime;
	private final boolean mUseHack;

	private Set<Bitmap> allocatedBitmaps = new HashSet<Bitmap>();
	private Set<Bitmap> hackedBitmaps = new HashSet<Bitmap>();

	public BitmapFactoryHack(boolean useHack) {
		mUseHack = useHack;
	}

	public BitmapFactoryHack(VMRuntimeHack runtime) {
		mUseHack = true;
		mRuntime = runtime;
	}

	public Bitmap alloc(Bitmap bmp) {
		if (bmp != null) {
			if (mUseHack) {
				mRuntime.trackFree(bmp.getRowBytes() * bmp.getHeight());
				hackedBitmaps.add(bmp);
			}
			allocatedBitmaps.add(bmp);
		}
		return bmp;
	}

	public Bitmap alloc(int dx, int dy) {
		Bitmap bmp = Bitmap.createBitmap(dx, dy, Bitmap.Config.RGB_565);
		if (mUseHack) {
			mRuntime.trackFree(bmp.getRowBytes() * bmp.getHeight());
			hackedBitmaps.add(bmp);
		}
		allocatedBitmaps.add(bmp);
		return bmp;
	}

	public void free(Bitmap bmp) {
		bmp.recycle();
		if (hackedBitmaps.contains(bmp)) {
			mRuntime.trackAlloc(bmp.getRowBytes() * bmp.getHeight());
			hackedBitmaps.remove(bmp);
		}
		allocatedBitmaps.remove(bmp);
	}

	public void freeAll() {
		for (Bitmap bmp : new LinkedList<Bitmap>(allocatedBitmaps))
			free(bmp);
	}
}
