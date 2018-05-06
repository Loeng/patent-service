package org.patent.utils;



import org.patent.entity.SysUserEntity;
import org.com.patent.redis.JedisHolder;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 * 
 */
public class ShiroUtils {

  public static Session getSession() {
    return SecurityUtils.getSubject().getSession();
  }

  public static Subject getSubject() {
    return SecurityUtils.getSubject();
  }

  public static SysUserEntity getUserEntity() {
    return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
  }

  public static Long getUserId() {
    return getUserEntity().getUserId();
  }

  public static void setSessionAttribute(Object key, Object value) {
    getSession().setAttribute(key, value);
  }

  public static Object getSessionAttribute(Object key) {
    return getSession().getAttribute(key);
  }

  public static boolean isLogin() {
    return SecurityUtils.getSubject().getPrincipal() != null;
  }

  public static void logout() {
    SecurityUtils.getSubject().logout();
  }

  public static void logoutAndClearRedis() {
    SysUserEntity user = getUserEntity();
    if (user != null) {
      try {
        String userName = user.getUsername();
        String key = userName + "__authority";
        // 删除redis缓存
        JedisHolder.custom().T().delObject(userName);
        JedisHolder.custom().T().delObject(key);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    SecurityUtils.getSubject().logout();
  }

}

