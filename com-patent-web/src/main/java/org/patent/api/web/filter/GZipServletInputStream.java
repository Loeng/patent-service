package org.patent.api.web.filter;


import java.io.IOException;
import java.util.zip.GZIPInputStream;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;

public class GZipServletInputStream extends ServletInputStream {

  private GZIPInputStream input;
  private boolean finished;

  public GZipServletInputStream(GZIPInputStream input) {
    this.input = input;

  }

  @Override
  public boolean isFinished() {
    return finished;
  }

  @Override
  public boolean isReady() {
    return true;
  }

  @Override
  public void setReadListener(ReadListener readListener) {

  }

  @Override
  public int read() throws IOException {
    int b = input.read();
    if (b == -1) {
      this.finished = true;
    }
    return b;
  }

  @Override
  public void close() throws IOException {
    this.input.close();
    super.close();
  }
}
