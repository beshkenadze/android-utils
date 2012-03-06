package net.beshkenadze.android.utils;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.view.View;
import android.view.View.OnClickListener;

public class Picker implements OnClickListener {
	public static final int PICK_CONTACT = 1;
	public static final int PICK_IMAGE = 2;
	private Activity mActivity;
	private int mReqCode;

	public Picker(Activity a, int requestCode) {
		mActivity = a;
		mReqCode = requestCode;
	}

	public void onClick(View v) {
		switch (mReqCode) {
		case PICK_CONTACT:
			contact(mActivity);
			break;
		case PICK_IMAGE:
			image(mActivity);
			break;
		}
		
	}

	public static void contact(Activity a) {
		String mMimeType = ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE;
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
		intent.setType(mMimeType);
		a.startActivityForResult(intent, PICK_CONTACT);
	}

	public static void image(Activity a) {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		a.startActivityForResult(intent, PICK_IMAGE);
	}
}
