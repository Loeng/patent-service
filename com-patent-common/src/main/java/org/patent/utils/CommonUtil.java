package org.patent.utils;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 通用工具类
 * 
 *
 */
public class CommonUtil {

  // 地球半径
  private static final double R = 6371;
  private static final double DEGREE = Math.PI / 180;

  // 时间的格式类型
  public static final String DATE_TYPE_YMD = "yyyy-MM-dd";
  public static final String DATE_TYPE_HMS = "HH:mm:ss";
  public static final String DATE_TYPE_HM = "HH:mm";
  public static final String DATE_TYPE_YMD_HMS = "yyyy-MM-dd HH:mm:ss";

  // 电子围栏警告
  public static final String LOCATION_IN = "已回到 ";
  public static final String LOCATION_OUT = "已离开 ";
  public static final String FENCE_RANGE = "!";
  public static final Double FENCE_POINT_RADIUS = 80.0;

  public static final String METHOD_PARAMETER = "MethodParameter";
  public static final String METHOD_PARAMETER_TWO = "MethodParameterTwo";

  public static final String SHARE_KEY = "share_key";


  /**
   * 计算两经纬度之间的距离
   * 
   * @param lng 目标的经度
   * @param lat 目标的纬度
   * @param fenceLng 围栏的经度
   * @param fenceLat 围栏的纬度
   * @return 返回距离，单位米
   */
  public static Double getDistance(Double lng, Double lat, Double fenceLng, Double fenceLat) {
    Double lat1 = DEGREE * lat;
    Double lat2 = DEGREE * fenceLat;// 维度

    try {
      return Double.valueOf(new DecimalFormat("0.00").format(Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2)
          * Math.cos((fenceLng - lng) * DEGREE))
          * R * 1000));
    } catch (Exception e) {
      return 0.0;
    }
  }

  /**
   * 格式化String类型的时间格式，并返回Date类型的时间
   * 
   * @param date
   * @param fromartType
   * @return
   */
  public static Date formatStringToDate(String date, String fromartType) {
    try {
      return new SimpleDateFormat(fromartType).parse(date);
    } catch (ParseException e) {
      throw new ApiRRException(ApiResultCode.DATA_FORMART_ERROR, ApiResultCode.DATA_FORMART_ERROR_CODE);
    }
  }

  /**
   * 格式化Date类型的时间格式，返回String类型的时间
   * 
   * @param date
   * @param fromartType
   * @return
   */
  public static String formatDateToString(Date date, String fromartType) {
    try {
      return new SimpleDateFormat(fromartType).format(date);
    } catch (Exception e) {
      throw new ApiRRException(ApiResultCode.DATA_FORMART_ERROR, ApiResultCode.DATA_FORMART_ERROR_CODE);
    }
  }

  /**
   * 判断是否有新的升级包
   * 
   * @param appVersion APP当前版本
   * @param serverVersion 服务器最新版本
   * @return
   */
  public static boolean checkVersion(String appVersion, String serverVersion) {
    String[] appVersionArray = appVersion.split("\\.");
    String[] serverVersionArray = serverVersion.split("\\.");
    if (Integer.valueOf(appVersionArray[0]) < Integer.valueOf(serverVersionArray[0])) {
      return true;
    } else if (Integer.valueOf(appVersionArray[0]) == Integer.valueOf(serverVersionArray[0])) {
      if (Integer.valueOf(appVersionArray[1]) < Integer.valueOf(serverVersionArray[1])) {
        return true;
      } else if (Integer.valueOf(appVersionArray[1]) == Integer.valueOf(serverVersionArray[1])) {
        if (Integer.valueOf(appVersionArray[2]) < Integer.valueOf(serverVersionArray[2])) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * 判断是否有新的升级包
   * 
   * @param AppVersionCode APP当前内部版本
   * @param serverVersionCode 服务器最新内部版本
   * @return
   */
  public static boolean checkVersionCode(int AppVersionCode, int serverVersionCode) {
    if (serverVersionCode > AppVersionCode) {
      return true;
    } else {
      return false;
    }
  }

}

