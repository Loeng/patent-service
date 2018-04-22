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

  // 能推送给设备喜马拉雅能的最大故事数量
  public static final Integer HIMALAYAN_MAX_STORY_NUM = 20;
  // 能推送给设备的所有故事总大小限定
  public static final Integer HIMALAYAN_MAX_SIZE_NUM = 60 * 1024 * 1024;


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

  /**
   * 判断经纬度输入是否正确
   * 
   * @param lng 经度 -180~180
   * @param lat 纬度 -90~90
   */
  public static void checkLngLat(Double lng, Double lat) {
    if (lng == null || lng <= -180 || lng >= 180) {
      throw new ApiRRException(ApiResultCode.FENCE_LNG_LAT_ERROR_MSG, ApiResultCode.FENCE_LNG_LAT_ERROR_CODE);
    }
    if (lat == null || lat <= -90 || lat >= 90) {
      throw new ApiRRException(ApiResultCode.FENCE_LNG_LAT_ERROR_MSG, ApiResultCode.FENCE_LNG_LAT_ERROR_CODE);
    }
  }


  /**
   * 计算 bearing (正北方向夹角)，可作速度方向
   * 
   * @param lat_a 纬度1
   * @param lng_a 经度1
   * @param lat_b 纬度2
   * @param lng_b 经度2
   * @return
   */
  private static double getAngle1(double lng_a, double lat_a, double lng_b, double lat_b) {

    double y = Math.sin(lng_b - lng_a) * Math.cos(lat_b);
    double x = Math.cos(lat_a) * Math.sin(lat_b) - Math.sin(lat_a) * Math.cos(lat_b) * Math.cos(lng_b - lng_a);
    double brng = Math.atan2(y, x);

    brng = Math.toDegrees(brng);
    if (brng < 0)
      brng = brng + 360;
    return brng;

  }

  /**
   * 计算 bearing (正北方向夹角)，可作速度方向（航向角）
   * 
   * @param lat_a 纬度1
   * @param lng_a 经度1
   * @param lat_b 纬度2
   * @param lng_b 经度2
   * @return
   */
  public static double getAngle(double lng_b, double lat_b, double lng_a, double lat_a) {
    double brng = Math.atan((lng_b - lng_a) * Math.cos(lat_b) / (lat_b - lat_a));
    brng = Math.toDegrees(brng);
    double dLo = lng_b - lng_a;
    double dLa = lat_b - lat_a;
    if (dLo > 0 && dLa > 0) {
      brng = brng + 360;
    } else if (dLa < 0) {
      brng = brng + 180.;
    }
    return brng;

  }

  public static void main(String[] args) {
    System.out.println(getAngle(113.9447561, 22.5456443, 113.9437207, 22.5391461));
    System.out.println(getAngle1(113.9447561, 22.5456443, 113.9437207, 22.5391461));
    System.out.println(getAngle(110, 23.5456443, 113.9447561, 22.5391461));
    System.out.println(getAngle1(110, 23.5456443, 113.9447561, 22.5391461));
  }

}

