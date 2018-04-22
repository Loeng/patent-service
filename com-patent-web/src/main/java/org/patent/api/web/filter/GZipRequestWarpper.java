package org.patent.api.web.filter;

import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class GZipRequestWarpper extends HttpServletRequestWrapper {
  private HttpServletRequest request;

  public GZipRequestWarpper(HttpServletRequest request) {
    super(request);
    this.request = request;
  }

  @Override
  public ServletInputStream getInputStream() throws IOException {
    ServletInputStream stream = request.getInputStream();
    String contentEncoding = request.getHeader("Content-Encoding");
    if (null != contentEncoding && contentEncoding.toLowerCase().indexOf("gzip") != -1) {
      return new GZipServletInputStream(new GZIPInputStream(stream));
    }
    return stream;
  }

}