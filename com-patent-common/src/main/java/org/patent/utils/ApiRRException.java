package org.patent.utils;


/**
 * 自定义API异常
 */
public class ApiRRException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private String resultMsg = ApiResultCode.FAILUE_MSG;
  private int resultCode = ApiResultCode.FAILUE_CODE;
  private int errorCode = 0;
  private Object result = new Object();
  private long time = System.currentTimeMillis();

  /*
   * public ApiRRException(String resultMsg) { super(resultMsg); this.resultMsg = resultMsg; }
   */

  public ApiRRException(String resultMsg, Throwable e) {
    super(resultMsg, e);
    this.resultMsg = resultMsg;
  }

  public ApiRRException(String resultMsg, int errorCode) {
    super(resultMsg);
    this.resultMsg = resultMsg;
    this.errorCode = errorCode;
  }

  public ApiRRException(String resultMsg, int resultCode, Throwable e) {
    super(resultMsg, e);
    this.resultMsg = resultMsg;
    this.resultCode = resultCode;
  }

  public ApiRRException(String resultMsg, int resultCode, Object result) {
    super(resultMsg);
    this.resultMsg = resultMsg;
    this.resultCode = resultCode;
    this.result = result;
  }

  public ApiRRException(String resultMsg, int resultCode, Object result, Throwable e) {
    super(resultMsg, e);
    this.resultMsg = resultMsg;
    this.resultCode = resultCode;
    this.result = result;
  }

  public String getResultMsg() {
    return resultMsg;
  }

  public void setResultMsg(String resultMsg) {
    this.resultMsg = resultMsg;
  }

  public int getResultCode() {
    return resultCode;
  }

  public void setResultCode(int resultCode) {
    this.resultCode = resultCode;
  }

  public Object getResult() {
    return result;
  }

  public void setResult(Object result) {
    this.result = result;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public int getErrorCode() {
    return errorCode;
  }

  public void setErrorCode(int errorCode) {
    this.errorCode = errorCode;
  }
}
