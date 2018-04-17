package org.patent.entity;


import java.util.Map;

public class NotifyCustomerMsg {

  private String handler;
  private String type;
  private Map<String, String> parameters;

  public NotifyCustomerMsg(Map<String, String> parameters, String handler, String type) {
    this.handler = handler;
    this.parameters = parameters;
    this.type = type;
  }

  public String getHandlerName() {
    return handler;
  }

  public String getType() {
    return this.type;
  }

  public String getValue(String key) {
    return parameters.get(key);
  }
}
