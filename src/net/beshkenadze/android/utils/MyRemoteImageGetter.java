package net.beshkenadze.android.utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.text.Html.ImageGetter;
import android.view.View;

public class MyRemoteImageGetter implements ImageGetter {
	private Context c;
	private View container;
	private Resources res;
	private Download downloader;
	private File imageFile = null;
	private ImageDrawable imageDrawable = null;

	public class ImageDrawable extends BitmapDrawable {
		public ImageDrawable(Resources res) {
			super(res);
		}
		protected Drawable drawable;

		@Override
		public void draw(Canvas canvas) {
			if (drawable != null) {
				drawable.draw(canvas);
			}
		}
	}

	/***
	 * Construct the URLImageParser which will execute AsyncTask and refresh the
	 * container
	 * 
	 * @param t
	 * @param c
	 */
	public MyRemoteImageGetter(View t, Context c) {
		this.setContext(c);
		this.container = t;
		this.res = c.getResources();
		setDownloader(new Download(c));
	}

	public Drawable getDrawable(final String source) {
		setImageDrawable(new ImageDrawable(res));
//		final Handler updateHandler = new Handler();
//		final Runnable callback = new Runnable() {
//			@Override
//			public void run() {
//				MyRemoteImageGetter.this.container.invalidate();
//			}
//		};
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				setImageFile(getDownloader().get(source));
//				getImageDrawable().drawable = Drawable.createFromPath(getImageFile().getPath());
//				updateHandler.post(callback);
//			}
//		}).start();
//		ImageDrawable urlDrawable = new ImageDrawable(res);
		//
		// // get the actual source
		 ImageGetterAsyncTask asyncTask = new
		 ImageGetterAsyncTask(getImageDrawable());
		//
		 asyncTask.execute(source);

		// return reference to URLDrawable where I will change with actual image
		// from
		// the src tag
		return getImageDrawable();
	}

	public Download getDownloader() {
		return downloader;
	}

	public void setDownloader(Download downloader) {
		this.downloader = downloader;
	}

	public File getImageFile() {
		return imageFile;
	}

	public void setImageFile(File loadImage) {
		this.imageFile = loadImage;
	}

	public ImageDrawable getImageDrawable() {
		return imageDrawable;
	}

	public void setImageDrawable(ImageDrawable imageDrawable) {
		this.imageDrawable = imageDrawable;
	}

	public Context getContext() {
		return c;
	}

	public void setContext(Context c) {
		this.c = c;
	}

	public View getContainer() {
		return container;
	}

	public void setContainer(View container) {
		this.container = container;
	}

	public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
		ImageDrawable urlDrawable;

		public ImageGetterAsyncTask(ImageDrawable d) {
			this.urlDrawable = d;
		}

		@Override
		protected Drawable doInBackground(String... params) {
			String source = params[0];
			return fetchDrawable(source);
		}

		@Override
		protected void onPostExecute(Drawable result) {
			// set the correct bound according to the result from HTTP call
			urlDrawable.setBounds(0, 0, 0 + result.getIntrinsicWidth(),
					0 + result.getIntrinsicHeight());

			// change the reference of the current drawable to the result
			// from the HTTP call
			urlDrawable.drawable = result;

			// redraw the image by invalidating the container
			MyRemoteImageGetter.this.container.invalidate();
		}

		/***
		 * Get the Drawable from URL
		 * 
		 * @param urlString
		 * @return
		 */
		public Drawable fetchDrawable(String urlString) {
			try {
				InputStream is = fetch(urlString);
				Drawable drawable = Drawable.createFromStream(is, "src");
				drawable.setBounds(0, 0, 0 + drawable.getIntrinsicWidth(),
						0 + drawable.getIntrinsicHeight());
				return drawable;
			} catch (Exception e) {
				return null;
			}
		}

		private InputStream fetch(String urlString)
				throws MalformedURLException, IOException {
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet request = new HttpGet(urlString);
			HttpResponse response = httpClient.execute(request);
			return response.getEntity().getContent();
		}
	}

}
