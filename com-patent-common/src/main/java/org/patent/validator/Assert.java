package org.patent.validator;


import org.patent.utils.ApiRRException;
import org.apache.commons.lang.StringUtils;

/**
 * API接口数据校验工具
 * 
 */
public abstract class Assert {

  /**
   * 判断字符串参数是否为空
   * 
   * @param str 验证参数
   * @param message 错误信息
   */
  public static void isBlank(String str, String message, int errorCode) {
    if (StringUtils.isBlank(str)) {
      throw new ApiRRException(message, errorCode);
    }
  }
  
  /**
   * 判断字符串参数是否为不为空空
   * 
   * @param str 验证参数
   * @param message 错误信息
   */
  public static void isNotBlank(String str, String message, int errorCode) {
    if (StringUtils.isNotBlank(str)) {
      throw new ApiRRException(message, errorCode);
    }
  }

  /**
   * 判断Object参数是否为空
   * 
   * @param object 验证参数
   * @param message 错误信息
   */
  public static void isNull(Object object, String message, int errorCode) {
    if (object == null) {
      throw new ApiRRException(message, errorCode);
    }
  }

  /**
   * 判断Object参数是否不为空
   * 
   * @param object 验证参数
   * @param message 错误信息
   */
  public static void isNotNull(Object object, String message, int errorCode) {
    if (object != null) {
      throw new ApiRRException(message, errorCode);
    }
  }

  /**
   * 判断字符串参数是否为11位有效数字
   * 
   * @param str 验证参数
   * @param message 错误信息
   */
  public static void isTelNum(String str, String message, int errorCode) {
    if (str.length() != 11 && str.length() != 8) {
      throw new ApiRRException(message, errorCode);
    }
  }

  /**
   * 判断IMEI格式是否为15位有效数字
   * 
   * @param imei
   * @param message
   * @param code
   */
  public static void checkImei(String imei, String message, int errorCode) {
    if (imei.length() != 15) {
      throw new ApiRRException(message, errorCode);
    }
  }


  /**
   * 判断两个字符串是否相等
   * 
   * @param str
   * @param str2
   * @param message
   */
  public static void isEquals(String str, String str2, String message, int errorCode) {
    if (str.equals(str2)) {
      throw new ApiRRException(message, errorCode);
    }
  }

  /**
   * 判断两个字符串是否不相等
   * 
   * @param str
   * @param str2
   * @param message
   */
  public static void isNotEquals(String str, String str2, String message, int errorCode) {
    if (!str.equals(str2)) {
      throw new ApiRRException(message, errorCode);
    }
  }
}
