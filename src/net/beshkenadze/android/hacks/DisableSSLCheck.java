package net.beshkenadze.android.hacks;

import java.security.KeyStore;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpVersion;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;

public class DisableSSLCheck {
	// always verify the host - dont check for certificate
	public final static HostnameVerifier DO_NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};

	private static TrustManager[] getTrustCerts() {
		return new TrustManager[] { new X509TrustManager() {
			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return new java.security.cert.X509Certificate[] {};
			}

			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException {
			}

			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException {
			}
		} };

	}

	public static DefaultHttpClient getNewHttpClient() {
		// Create a trust manager that does not validate certificate chains
		TrustManager[] trustAllCerts = getTrustCerts();
		// Install the all-trusting trust manager
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore
					.getDefaultType());
			trustStore.load(null, null);
			
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			SSLSocketFactory sf = new MySSLSocketFactory(trustStore);
			
			
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

			SchemeRegistry registry = new SchemeRegistry();
			registry.register(new Scheme("http", PlainSocketFactory
					.getSocketFactory(), 80));
			registry.register(new Scheme("https", sf, 443));
			
			ClientConnectionManager ccm = new ThreadSafeClientConnManager(params, registry);
			return new DefaultHttpClient(ccm, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new DefaultHttpClient();
	}
}
