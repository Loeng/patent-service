package org.patent.http;


import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.httpclient.HttpClientError;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("deprecation")
public class HttpClientHolder {
	private static final Logger LOGGER = LoggerFactory.getLogger(HttpClientHolder.class);
	private static HttpClientHolder instance = new HttpClientHolder();
	private CloseableHttpClient client;
	private AtomicBoolean status = new AtomicBoolean(false);
	private HttpClientApi clentApi;

	private HttpClientHolder() {

	}

	public static HttpClientHolder custom() {
		return instance;
	}

	public void init() {
		if (status.compareAndSet(false, true)) {
			HttpClientBuilder builder = HttpClientBuilder.create();
			builder.setMaxConnPerRoute(20);
			builder.setMaxConnTotal(200);
			builder.setDefaultRequestConfig(RequestConfig.custom().setConnectTimeout(10000).build());
			//PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
			//builder.setConnectionManager(connectionManager);
			builder.setSSLSocketFactory(new SSLSocketFactory(createEasySSLContext(), new X509HostnameVerifier() {
				@Override
				public void verify(String host, SSLSocket ssl) throws IOException {
				}
				@Override
				public void verify(String host, X509Certificate cert) throws SSLException {
				}
				@Override
				public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
				}
				@Override
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			}));
			this.client = builder.build();
			this.clentApi = new HttpClientApiImpl(this.client);
		}
	}

	private SSLContext createEasySSLContext() {
		try {
			SSLContext context = SSLContext.getInstance("SSL");
			context.init(null, new TrustManager[] { new X509TrustManager() {
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}

				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}
			} }, null);
			return context;
		} catch (Exception e) {
			throw new HttpClientError(e.toString());
		}
	}

	public void dispose() {
		if (status.compareAndSet(true, false)) {
			if (client != null) {
				try {
					client.close();
				} catch (IOException e) {
					LOGGER.error("error:", e);
				}
			}
		}
	}

	public HttpClientApi api() {
		return this.clentApi;
	}
}

