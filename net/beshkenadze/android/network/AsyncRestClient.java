package net.beshkenadze.android.network;

import android.os.AsyncTask;
import android.util.Log;
import net.beshkenadze.android.utils.Debug;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class AsyncRestClient extends AsyncTask<Void, Void, Object> {

	private String url;
	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;
	private int responseCode;
	private String message;

	private String response;
	private Boolean encode = true;
	private HttpUriRequest request;
	private OnRequestListener listener;

	public AsyncRestClient(String url, OnRequestListener listener) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
		this.listener = listener;
	}

	public String getResponse() {
		return response;
	}

	public String getErrorMessage() {
		return message;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void addParam(String name, String value) {
		params.add(new BasicNameValuePair(name, value));
	}

	public void addHeader(String name, String value) {
		headers.add(new BasicNameValuePair(name, value));
	}

	public Object prepare() {
		String combinedParams = "";
		if (!params.isEmpty()) {
			combinedParams += "?";
			for (NameValuePair p : params) {
				String paramString;
				try {
					paramString = p.getName()
							+ "="
							+ (encode ? URLEncoder
									.encode(p.getValue(), "UTF-8") : p
									.getValue());
				} catch (UnsupportedEncodingException e) {
					paramString = p.getName() + "=" + p.getValue();
					e.printStackTrace();
				}
				if (combinedParams.length() > 1) {
					combinedParams += "&" + paramString;
				} else {
					combinedParams += paramString;
				}
			}
		}
		Log.i("APP", (url + combinedParams) + "");
		return combinedParams;
	}

	public void postRequest() {
		postRequest(null);
	}

	public void postRequest(String raw) {
		request = new HttpPost(url);

		if (raw != null) {
			StringEntity se;
			try {
				se = new StringEntity(raw);
				((HttpPost) request).setEntity(se);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}

		} else {
			List<NameValuePair> nvps = new ArrayList<NameValuePair>();

			if (!params.isEmpty()) {
				for (NameValuePair p : params) {
					nvps.add(new BasicNameValuePair(p.getName(), p.getValue()));
				}
			}

			try {
				((HttpPost) request).setEntity(new UrlEncodedFormEntity(nvps,
						HTTP.UTF_8));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}

		// add headers
		for (NameValuePair h : headers) {
			request.addHeader(h.getName(), h.getValue());
		}

		execute();
	}

	public void getRequest() {
		Object combinedParams = prepare();
		request = new HttpGet(url + combinedParams);

		// add headers
		for (NameValuePair h : headers) {
			request.addHeader(h.getName(), h.getValue());
		}
		// return executeRequest(request, url);
		Debug.i(request.toString());
		execute();
	}

	private boolean cancelled;

	private String executeRequest(HttpUriRequest request, String url) {
		cancelled = false;

		while (!cancelled) {
			HttpClient client = new DefaultHttpClient();

			HttpResponse httpResponse;

			try {
				httpResponse = client.execute(request);
				responseCode = httpResponse.getStatusLine().getStatusCode();
				message = httpResponse.getStatusLine().getReasonPhrase();

				HttpEntity entity = httpResponse.getEntity();

				if (entity != null) {
					InputStream instream = entity.getContent();
					response = convertStreamToString(instream);

					// Closing the input stream will trigger connection release
					instream.close();
					return response;
				}

			} catch (ClientProtocolException e) {
				client.getConnectionManager().shutdown();
				e.printStackTrace();
			} catch (IOException e) {
				client.getConnectionManager().shutdown();
				return null;
				//e.printStackTrace();
			}

			// TODO сделать обарботку ошибки
		}

		return "";
	}

	private static String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	public void setEncode(boolean encode) {
		this.encode = encode;
	}

	@Override
	protected Object doInBackground(Void... params) {
		return executeRequest(request, url);
	}

	@Override
	protected void onPostExecute(Object result) {
		if (result == null) {
			listener.onLoadError(url);
		} else {
			listener.onLoad(url, (String) result);
		}
		super.onPostExecute(result);
	}
}