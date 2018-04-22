package org.patent.http;


import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpMessage;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class HttpClientApiImpl implements HttpClientApi {

	private CloseableHttpClient client;

	public HttpClientApiImpl(CloseableHttpClient client) {
		this.client = client;
	}

	private void addHeader(HttpMessage message, Map<String, String> headers) {
		if (headers == null) {
			return;
		}
		for (Map.Entry<String, String> h : headers.entrySet()) {
			message.addHeader(h.getKey(), h.getValue());
		}
	}

	private void addParameter(StringBuilder url,Map<String, String> parameters) {
		String para = "?";
		for (String str : parameters.keySet()) {
			try {
				if (StringUtils.isNotEmpty(parameters.get(str))) {
					para += str + "=" + URLEncoder.encode(parameters.get(str), "utf-8") + "&";
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		
		url = url.append( null == parameters ? "" : para.substring(0, para.length() - 1) );
	}
	
	private String processResponse(HttpUriRequest message) throws IOException {
		CloseableHttpResponse response = this.client.execute(message);
		return EntityUtils.toString(response.getEntity(), Charset.forName("utf-8"));
	}

	@Override
	public String get(String url, Map<String, String> headers) throws IOException {
		return this.get(url, headers, -1);
	}

	@Override
	public String get(String url, Map<String, String> headers, int timeout) throws IOException {
		HttpGet getMethod = new HttpGet(url);
		addHeader(getMethod, headers);
		try {
			getMethod.setConfig(RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(timeout).build());
			return processResponse(getMethod);
		} finally {
			getMethod.releaseConnection();
		}
	}

	@Override
	public String post(String url, Map<String, String> headers, Map<String, String> body) throws IOException {
		return this.post(url, headers, body, -1);
	}

	@Override
	public String post(String url, Map<String, String> headers, Map<String, String> body, int timeout)
			throws IOException {
		HttpPost postMethod = new HttpPost(url);
		addHeader(postMethod, headers);
		try {
			postMethod.setConfig(RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(timeout).build());
			if (body != null) {
				List<NameValuePair> list = new LinkedList<>();
				for (Map.Entry<String, String> entry : body.entrySet()) {
					BasicNameValuePair bn = new BasicNameValuePair(entry.getKey(), entry.getValue());
					list.add(bn);
				}
				postMethod.setEntity(new UrlEncodedFormEntity(list));
			}
			return processResponse(postMethod);

		} finally {
			postMethod.releaseConnection();
		}
	}

	@Override
	public String postJson(String url, Map<String, String> headers, String jsonBody) throws IOException {
		return this.postJson(url, headers, jsonBody, -1);
	}

	@Override
	public String postJson(String url, Map<String, String> headers, String jsonBody, int timeout) throws IOException {
		HttpPost postMethod = new HttpPost(url);
		addHeader(postMethod, headers);
		try {
			postMethod.setConfig(RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(timeout).build());
			if (jsonBody != null) {
				postMethod.setEntity(new StringEntity(jsonBody,
						ContentType.create(ContentType.APPLICATION_JSON.getMimeType(), Charset.forName("utf-8"))));
			}
			return processResponse(postMethod);
		} finally {
			postMethod.releaseConnection();
		}
	}

	@Override
	public String get(String url, Map<String, String> headers,Map<String, String> params) throws IOException {
		return this.get(url, headers,params,-1);
	}

	@Override
	public String get(String url, Map<String, String> headers,Map<String, String> params, int timeout) throws IOException {
		StringBuilder urlBuilder = new StringBuilder(url);
		addParameter(urlBuilder,params);
		HttpGet getMethod = new HttpGet(urlBuilder.toString());
		addHeader(getMethod, headers);
		try {
			getMethod.setConfig(RequestConfig.custom().setConnectTimeout(10000).setSocketTimeout(timeout).build());
			return processResponse(getMethod);
		} finally {
			getMethod.releaseConnection();
		}
	}
	
}
