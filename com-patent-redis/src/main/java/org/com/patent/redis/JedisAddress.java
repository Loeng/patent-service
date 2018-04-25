package org.com.patent.redis;


/**
 * Hello world!
 *
 */
public class JedisAddress {
  private String hostName;
  private int port;

  public JedisAddress(String hostName, int port) {
    super();
    this.hostName = hostName;
    this.port = port;
  }

  public String getHostName() {
    return hostName;
  }

  public void setHostName(String hostName) {
    this.hostName = hostName;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  @Override
  public String toString() {
    return this.hostName + ":" + this.port;
  }

}
