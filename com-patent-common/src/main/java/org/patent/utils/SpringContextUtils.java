package org.patent.utils;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Spring Context 工具类
 * 
 */

@Service
public class SpringContextUtils implements ApplicationContextAware,DisposableBean{
  public static ApplicationContext applicationContext;

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    SpringContextUtils.applicationContext = applicationContext;
  }

  @Override
  public void destroy() throws Exception {
    SpringContextUtils.clearApplicationContext();
  }
  
  public static ApplicationContext getApplicationContext() {
      return applicationContext;
  }
  
  public static void clearApplicationContext(){
    applicationContext = null;
  }
  public static Object getBean(String name) {
    return applicationContext.getBean(name);
  }

  public static <T> T getBean(String name, Class<T> requiredType) {
    return applicationContext.getBean(name, requiredType);
  }

  public static boolean containsBean(String name) {
    return applicationContext.containsBean(name);
  }

  public static boolean isSingleton(String name) {
    return applicationContext.isSingleton(name);
  }

  public static Class<? extends Object> getType(String name) {
    return applicationContext.getType(name);
  }

}
