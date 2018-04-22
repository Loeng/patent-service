package org.patent.http;


import java.io.IOException;
import java.util.Map;

public interface HttpClientApi {

	public String get(String url, Map<String, String> headers) throws IOException;

	public String get(String url, Map<String, String> headers, int timeout) throws IOException;

	public String post(String url, Map<String, String> headers, Map<String, String> body) throws IOException;

	public String post(String url, Map<String, String> headers, Map<String, String> body, int timeout) throws IOException;

	public String postJson(String url, Map<String, String> headers, String jsonBody) throws IOException;

	public String postJson(String url, Map<String, String> headers, String jsonBody, int timeout) throws IOException;

	public String get(String url, Map<String, String> headers, Map<String, String> params) throws IOException;

	public String get(String url, Map<String, String> headers, Map<String, String> params, int timeout) throws IOException;	

}
