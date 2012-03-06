package net.beshkenadze.android.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import net.beshkenadze.android.utils.ImageLoader.OnImageLoadListener;

public class Utils {
	public static String md5(String s) {
		try {
			// Create MD5 Hash
			MessageDigest digest = java.security.MessageDigest
					.getInstance("MD5");
			digest.update(s.getBytes());
			byte messageDigest[] = digest.digest();

			// Create Hex String
			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++)
				hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
			return hexString.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	 public static String fileExtention(String filename) {
	        String filenameArray[] = filename.split("\\.");
	        String extension = filenameArray[filenameArray.length-1];
	        return extension;
	    }
//	public static void downloadImageInFrameView(final Context context, final ImageView imageView, String src) {
//		try {
//			new AsyncDownload(context, new onLoadListener() {
//				public void onLoadError(String error) {
//					
//				}
//				
//				public void onLoad(Object data) {
//					File image = (File) data;
//					Bitmap myBitmapImage = BitmapFactory.decodeFile(image.getAbsolutePath());
//					Animation fadeIn = AnimationUtils.loadAnimation(context, R.anim.fade_in);
//					imageView.startAnimation(fadeIn);
//					imageView.setImageDrawable(new BitmapDrawable(Utils.framePhoto(context, myBitmapImage, R.drawable.ring)));
//				}
//			}).get(new URL(src));
//		} catch (MalformedURLException e) {
//			e.printStackTrace();
//		}
//	}
	public static void downloadImageInView(Activity activity, final ImageView imageView, String src) {
		downloadImageInView(activity, imageView, src, null);
	}
	public static void downloadImageInView(Activity activity, final ImageView imageView, String src, OnImageLoadListener listener) {
		ImageLoader imageLoader = new ImageLoader(activity.getApplicationContext());
		imageLoader.DisplayImage(src, imageView, false);
		if(listener != null) {
			imageLoader.setOnImageLoadListener(listener);
		}
	}
	public static void downloadImageInBackgroundView(Activity activity, final ImageView imageView, String src) {
		Utils.downloadImageInBackgroundView(activity, imageView, src, null);
	}
	public static void downloadImageInBackgroundView(Activity activity, final ImageView imageView, String src, OnImageLoadListener listener) {
		ImageLoader imageLoader = new ImageLoader(activity.getApplicationContext());
		imageLoader.DisplayImage(src, imageView, true);
		imageLoader.setOnImageLoadListener(listener);
	}
	public static Bitmap framePhoto(Context contenxt, Bitmap photo, int resource_frame_id) {
		final Resources r = contenxt.getResources();
		final Drawable frame = r.getDrawable(resource_frame_id);

		Bitmap bitmapFrame = ((BitmapDrawable)frame).getBitmap();
		
		final int width = bitmapFrame.getWidth();
		final int height = bitmapFrame.getHeight();

		frame.setBounds(0, 0, width, height);

		final Rect padding = new Rect();
		frame.getPadding(padding);

		final Rect source = new Rect(0, 0, photo.getWidth(), photo.getHeight());
		final Rect destination = new Rect(padding.left, padding.top, width
				- padding.right, height - padding.bottom);

		final int d = Math.max(width, height);
		final Bitmap b = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
		final Canvas c = new Canvas(b);

		c.translate((d - width) / 2.0f, (d - height) / 2.0f);
		frame.draw(c);
		c.drawBitmap(photo, source, destination, new Paint(
				Paint.FILTER_BITMAP_FLAG));

		return b;
	}
	public static void CopyStream(InputStream is, OutputStream os)
    {
        final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
    }
}
