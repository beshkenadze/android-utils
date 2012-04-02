package net.beshkenadze.android.utils;

import android.os.AsyncTask;

public class AsyncRestClient extends AsyncTask<Void, Void, Object> {
	private OnRequestListener mListener;
	private RestClient mRestClient;
	private String mUrl;

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

	@Override
	protected Object doInBackground(Void... params) {
		return mRestClient.executeRequest();
	}

	@Override
	protected void onPostExecute(Object result) {
		if (result == null) {
			mListener.onLoadError(mUrl);
		} else {
			mListener.onLoad(mUrl, (String) result);
		}
		super.onPostExecute(result);
	}
}