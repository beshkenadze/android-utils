package net.beshkenadze.android.utils;

import net.beshkenadze.android.hacks.DisableSSLCheck;
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
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class RestClient {

	private String url;
	private ArrayList<NameValuePair> params;
	private ArrayList<NameValuePair> headers;
	private int responseCode;
	private String message;
	private Boolean mCheckSsl = false;

	private String response;
	private Boolean encode = true;
	private HttpUriRequest request;
	private int mTimeoutConnection = 20000;
	private HttpResponse httpResponse;
	
	public RestClient(String url) {
		this.url = url;
		params = new ArrayList<NameValuePair>();
		headers = new ArrayList<NameValuePair>();
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
		// Log.i("APP", (url + combinedParams) + "");
		return combinedParams;
	}

	public String postRequest() {
		return postRequest(null);
	}

	public void doPreparePostRequest(String raw) {
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
	}

	public String postRequest(String raw) {
		doPreparePostRequest(raw);
		return getRequestString();
	}

	public void doPrepareGetRequest() {
		Object combinedParams = prepare();
		request = new HttpGet(url + combinedParams);

		// add headers
		for (NameValuePair h : headers) {
			request.addHeader(h.getName(), h.getValue());
		}
	}

	public String getRequest() {
		doPrepareGetRequest();
		return getRequestString();
	}

	private boolean cancelled;

	private String getRequestString() {
		return executeRequest();
	}
	public String exportRequestString() {
		String paramsString = "?";
		for (NameValuePair nameValuePair : getParams()) {
			paramsString += nameValuePair.getName()+"="+nameValuePair.getValue()+"&";
		}
		return url + paramsString;
	}
	public String executeRequest() {
		cancelled = false;

		while (!cancelled) {

			
			HttpParams httpParameters = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParameters, getTimeoutConnection());
			HttpClient client = new DefaultHttpClient(httpParameters);
			if (!mCheckSsl) {
				client = DisableSSLCheck.getNewHttpClient(httpParameters);
			}
			
			try {
				httpResponse = client.execute(request);
				responseCode = httpResponse.getStatusLine().getStatusCode();
				message = httpResponse.getStatusLine().getReasonPhrase();

				HttpEntity entity = httpResponse.getEntity();

				if (entity != null) {
					response = EntityUtils.toString(entity);
					return response;
				} else {
					return null;
				}

			} catch (ClientProtocolException e) {
				client.getConnectionManager().shutdown();
				e.printStackTrace();
				return null;
			} catch (IOException e) {
				client.getConnectionManager().shutdown();
				e.printStackTrace();
				return null;
			}

		}

		return null;
	}

	public void setEncode(boolean encode) {
		this.encode = encode;
	}

	public void disableSslCheck(Boolean check) {
		mCheckSsl = check;
	}

	public int getTimeoutConnection() {
		return mTimeoutConnection;
	}

	public void setTimeoutConnection(int mTimeoutConnection) {
		this.mTimeoutConnection = mTimeoutConnection;
	}
	public ArrayList<NameValuePair> getParams() {
		return params;
	}

	public void setParams(ArrayList<NameValuePair> params) {
		this.params = params;
	}

	public HttpResponse getHttpResponse() {
		return httpResponse;
	}

	public void setHttpResponse(HttpResponse httpResponse) {
		this.httpResponse = httpResponse;
	}
}