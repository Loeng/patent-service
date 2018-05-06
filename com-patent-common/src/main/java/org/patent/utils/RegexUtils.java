package org.patent.utils;


import java.util.regex.Pattern;

/**
 * 正则表达式验证工具类
 * 
 */
public class RegexUtils {

  /**
   * 验证Email
   * @param email 邮箱
   * @return 验证成功返回true，验证失败返回false
   */
  public static boolean checkEmail(String email) {
    String regex = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";
    return Pattern.matches(regex, email);
  }

  /**
   * 验证身份证号码
   * 
   * @param idCard 15位和18位居民身份证号码
   * @return 验证成功返回true，验证失败返回false
   */
  public static boolean checkIdCard(String idCard) {
    String regex = "[1-9]\\d{13,16}[a-zA-Z0-9]{1}";
    return Pattern.matches(regex, idCard);
  }

  /**
   * 验证手机号码
   * 
   * @param mobile
   * @return 验证成功返回true，验证失败返回false
   */
  public static boolean checkMobile(String mobile) {
	String regex = "^[1][3-9]\\d{9}$|^([6|9])\\d{7}$|^[0][9]\\d{8}$|^[6]([8|6])\\d{5}$";
    return Pattern.matches(regex, mobile);
  }
  
  public static void main(String[] args) {
    System.out.println(checkMobile("+8618764960759"));
  }

  /**
   * 验证中文
   * 
   * @param chinese 中文字符
   * @return 验证成功返回true，验证失败返回false
   */
  public static boolean checkChinese(String chinese) {
    String regex = "^[\u4E00-\u9FA5]+$";
    return Pattern.matches(regex, chinese);
  }
  
  /**
   * 验证中文英文和下划线
   * 
   * @param chinese 中文字符英文字符和下划线空格数字
   * @return 验证成功返回true，验证失败返回false
   */
  public static boolean checkChEn(String ch_en){
    String regex = "^[\u4e00-\u9fa5_a-zA-Z0-9\u0020]+$";
    return Pattern.matches(regex, ch_en);
  }

  /**
   * 验证日期 年月日
   * 
   * @param birthday 日期，格式：1992-09-03，或1992.09.03
   * @return 验证成功返回true，验证失败返回false
   */
  public static boolean checkBirthday(String birthday) {
    String regex = "[1-9]{4}([-./])\\d{1,2}\\1\\d{1,2}";
    return Pattern.matches(regex, birthday);
  }

  /**
   * 验证URL地址
   * 
   * @param url
   * @return 验证成功返回true，验证失败返回false
   */
  public static boolean checkURL(String url) {
    String regex = "[a-zA-z]+://[^\\s]*";
    return Pattern.matches(regex, url);
  }

  /**
   * 匹配中国邮政编码
   * 
   * @param postcode 邮政编码
   * @return 验证成功返回true，验证失败返回false
   */
  public static boolean checkPostcode(String postcode) {
    String regex = "[1-9]\\d{5}";
    return Pattern.matches(regex, postcode);
  }

}
