package org.patent.utils;


import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期处理
 * 
 */
public class DateUtils {

  /** 时间格式(yyyy-MM-dd) */
  public final static String DATE_PATTERN = "yyyy-MM-dd";

  /** 时间格式(yyyy-MM-dd HH:mm:ss) */
  public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

  /**
   * 时间格式化
   * 
   * @param date
   * @return
   */
  public static String format(Date date) {
    return format(date, DATE_PATTERN);
  }

  /**
   * 
   * @param date
   * @param pattern
   * @return String
   */
  public static String format(Date date, String pattern) {
    if (date != null) {
      SimpleDateFormat df = new SimpleDateFormat(pattern);
      return df.format(date);
    }
    return null;
  }

  /**
   * 
   * @return
   */
  public static Date formatShortDate(Date date, String pattern) {
    if (date != null) {
      SimpleDateFormat df = new SimpleDateFormat(pattern);
      ParsePosition pos = new ParsePosition(0);
      Date shortDate = df.parse(df.format(date), pos);
      return shortDate;
    }
    return null;
  }

}
