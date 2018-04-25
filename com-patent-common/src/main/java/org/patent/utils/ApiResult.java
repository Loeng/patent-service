package org.patent.utils;


import java.util.HashMap;

/**
 * API返回数据
 * 
 */
public class ApiResult extends HashMap<String, Object> {
  private static final long serialVersionUID = 1L;

  public ApiResult() {
    put("resultCode", ApiResultCode.SUCCESS_CODE);
    put("resultMsg", ApiResultCode.SUCCESS_MSG);
    put("errorCode", 0);
    put("time", System.currentTimeMillis());
    put("result", new Object());
  }

  public static ApiResult R() {
    return new ApiResult();
  }

  public ApiResult setResultCode(int resultCode) {
    super.put("resultCode", resultCode);
    return this;
  }

  public ApiResult setResultMsg(String resultMsg) {
    super.put("resultMsg", resultMsg);
    return this;
  }

  public ApiResult setResult(Object result) {
    if (null != result) {
      super.put("result", result);
    }
    return this;
  }

  public ApiResult put(String key, Object value) {
    super.put(key, value);
    return this;
  }

  public ApiResult setErrorCode(Object errorCode) {
    super.put("errorCode", errorCode);
    return this;
  }
}
