package net.beshkenadze.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

public class MyNetworkUtils {
	static public int CONNECT_TYPE_3G = 0;
	static public int CONNECT_TYPE_WIFI = 1;

	static public boolean isOnline(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		}
		return false;
	}

	static public int getType(Context c) {
		ConnectivityManager cm = (ConnectivityManager) c
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		// mobile
		State mobile = cm.getNetworkInfo(0).getState();
		// wifi
		State wifi = cm.getNetworkInfo(1).getState();
		if (mobile == NetworkInfo.State.CONNECTED
				|| mobile == NetworkInfo.State.CONNECTING) {
			return CONNECT_TYPE_3G;
		} else if (wifi == NetworkInfo.State.CONNECTED
				|| wifi == NetworkInfo.State.CONNECTING) {
			return CONNECT_TYPE_WIFI;
		}
		return -1;
	}

	static public boolean is3g(Context c) {
		return getType(c) == CONNECT_TYPE_3G;
	}

	static public boolean isWifi(Context c) {
		return getType(c) == CONNECT_TYPE_WIFI;
	}

	static public String getOperatorName(Context c) {
		TelephonyManager tm = (TelephonyManager) c.getSystemService(Context.TELEPHONY_SERVICE);
		return tm.getNetworkOperatorName();
	}
}
