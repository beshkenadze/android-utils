package net.beshkenadze.android.utils;

import android.app.ProgressDialog;
import android.os.AsyncTask;

public class AsyncRestClient extends AsyncTask<Void, Void, Object> {
	private OnRequestListener mListener;
	private RestClient mRestClient;
	private String mUrl;
	private ProgressDialog mDialog;

	public AsyncRestClient(String url, OnRequestListener listener) {
		mListener = listener;
		mUrl = url;
		mRestClient = new RestClient(url);
	}

	public String getResponse() {
		return mRestClient.getResponse();
	}

	public String getErrorMessage() {
		return mRestClient.getErrorMessage();
	}

	public int getResponseCode() {
		return mRestClient.getResponseCode();
	}

	public void addParam(String name, String value) {
		mRestClient.addParam(name, value);
	}

	public void addHeader(String name, String value) {
		mRestClient.addHeader(name, value);
	}

	public void postRequest() {
		mRestClient.doPreparePostRequest(null);
		execute();
	}

	public void postRequest(String raw) {
		mRestClient.doPreparePostRequest(raw);
		execute();
	}

	public void getRequest() {
		mRestClient.doPrepareGetRequest();
		execute();
	}
	public void disableSslCheck(Boolean check) {
		mRestClient.disableSslCheck(check);
	}
	@Override
	protected Object doInBackground(Void... params) {
		return mRestClient.executeRequest();
	}
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
		if(mDialog != null) {
			mDialog.show();
		}
	}
	@Override
	protected void onPostExecute(Object result) {
		if (result == null) {
			mListener.onLoadError(mUrl);
		} else {
			mListener.onLoad(mUrl, (String) result);
		}
		if(mDialog != null) {
			mDialog.cancel();
		}
		super.onPostExecute(result);
	}
}