/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.beshkenadze.android.utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.CommonDataKinds.Photo;

public class MyContactsUtils {
	/**
	 * Opens an InputStream for the person's photo and returns the photo as a
	 * Bitmap. If the person's photo isn't present returns null.
	 * 
	 * @param aggCursor
	 *            the Cursor pointing to the data record containing the photo.
	 * @param bitmapColumnIndex
	 *            the column index where the photo Uri is stored.
	 * @param options
	 *            the decoding options, can be set to null
	 * @return the photo Bitmap
	 */
	public static Bitmap loadContactPhoto(Cursor cursor, int bitmapColumnIndex,
			BitmapFactory.Options options) {
		if (cursor == null) {
			return null;
		}

		byte[] data = cursor.getBlob(bitmapColumnIndex);
		return BitmapFactory.decodeByteArray(data, 0, data.length, options);
	}

	public static Bitmap loadContactPhoto(Context c, Uri selectedUri,
			BitmapFactory.Options options) {
		Uri contactUri = null;
		if (Contacts.CONTENT_ITEM_TYPE.equals(c.getContentResolver().getType(
				selectedUri))) {
			// TODO we should have a "photo" directory under the lookup URI
			// itself
			contactUri = Contacts.lookupContact(c.getContentResolver(),
					selectedUri);
		} else {

			Cursor cursor = c.getContentResolver().query(selectedUri,
					new String[] { Data.CONTACT_ID }, null, null, null);
			try {
				if (cursor != null && cursor.moveToFirst()) {
					final long contactId = cursor.getLong(0);
					contactUri = ContentUris.withAppendedId(
							Contacts.CONTENT_URI, contactId);
				}
			} finally {
				if (cursor != null)
					cursor.close();
			}
		}

		Cursor cursor = null;
		Bitmap bm = null;
		if (contactUri != null) {
			try {
				Uri photoUri = Uri.withAppendedPath(contactUri,
						Contacts.Photo.CONTENT_DIRECTORY);
				cursor = c.getContentResolver().query(photoUri,
						new String[] { Photo.PHOTO }, null, null, null);
				if (cursor != null && cursor.moveToFirst()) {
					bm = loadContactPhoto(cursor, 0, options);
				}
			} finally {
				if (cursor != null) {
					cursor.close();
				}
			}
		}

//		if (bm == null) {
//			bm = BitmapFactory.decodeResource(c.getResources(), R.drawable.icon);
//		}

		return bm;
	}

	/**
	 * Loads a placeholder photo.
	 * 
	 * @param placeholderImageResource
	 *            the resource to use for the placeholder image
	 * @param context
	 *            the Context
	 * @param options
	 *            the decoding options, can be set to null
	 * @return the placeholder Bitmap.
	 */
	public static Bitmap loadPlaceholderPhoto(int placeholderImageResource,
			Context context, BitmapFactory.Options options) {
		if (placeholderImageResource == 0) {
			return null;
		}
		return BitmapFactory.decodeResource(context.getResources(),
				placeholderImageResource, options);
	}

	public static Bitmap loadContactPhoto(Context context, long photoId,
			BitmapFactory.Options options) {
		Cursor photoCursor = null;
		Bitmap photoBm = null;

		try {
			photoCursor = context.getContentResolver().query(
					ContentUris.withAppendedId(Data.CONTENT_URI, photoId),
					new String[] { Photo.PHOTO }, null, null, null);

			if (photoCursor.moveToFirst() && !photoCursor.isNull(0)) {
				byte[] photoData = photoCursor.getBlob(0);
				photoBm = BitmapFactory.decodeByteArray(photoData, 0,
						photoData.length, options);
			}
		} finally {
			if (photoCursor != null) {
				photoCursor.close();
			}
		}

		return photoBm;
	}

//	public static Bitmap framePhoto(Context contenxt, Bitmap photo) {
//		final Resources r = contenxt.getResources();
//		final Drawable frame = r.getDrawable(R.drawable.quickcontact_badge);
//
//		final int width = r
//				.getDimensionPixelSize(R.dimen.contact_shortcut_frame_width);
//		final int height = r
//				.getDimensionPixelSize(R.dimen.contact_shortcut_frame_height);
//
//		frame.setBounds(0, 0, width, height);
//
//		final Rect padding = new Rect();
//		frame.getPadding(padding);
//
//		final Rect source = new Rect(0, 0, photo.getWidth(), photo.getHeight());
//		final Rect destination = new Rect(padding.left, padding.top, width
//				- padding.right, height - padding.bottom);
//
//		final int d = Math.max(width, height);
//		final Bitmap b = Bitmap.createBitmap(d, d, Bitmap.Config.ARGB_8888);
//		final Canvas c = new Canvas(b);
//
//		c.translate((d - width) / 2.0f, (d - height) / 2.0f);
//		frame.draw(c);
//		c.drawBitmap(photo, source, destination, new Paint(
//				Paint.FILTER_BITMAP_FLAG));
//
//		return b;
//	}
}
