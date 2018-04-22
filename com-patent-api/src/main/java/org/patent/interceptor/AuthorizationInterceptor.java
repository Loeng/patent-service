package org.patent.interceptor;


import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import org.patent.annotation.IgnoreAuth;
import org.patent.annotation.NotifyCustomer;
import org.patent.context.TokenHolder;
//import org.patent.entity.NotifyCustomerMsg;
import org.patent.entity.SysLogEntity;
import org.patent.entity.TokenEntity;
import org.patent.utils.ApiRRException;
import org.patent.utils.ApiResultCode;
import org.patent.utils.CommonUtil;
//import io.watch.utils.IPUtils;

/**
 * 权限(Token)验证
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {  //请求拦截器

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    
    // 通过AOP切面验证当前接口是否需要token验证
    IgnoreAuth annotation;
    
    // API接口请求日志信息实体
    SysLogEntity log = new SysLogEntity();
    log.setCreateDate(new Date());
//    log.setIp(IPUtils.getIpAddr(request));
    
    try {
      
      // 获取当前API请求对应执行的方法名称
      if (handler instanceof HandlerMethod) { //作用是判断其左边对象是否为其右边类的实例，返回boolean类型的数据
        HandlerMethod hd = (HandlerMethod) handler;
        annotation = hd.getMethodAnnotation(IgnoreAuth.class);
        log.setMethod(hd.getBean().getClass().getName() + "." + hd.getMethod().getName());
      } else {
        log.setOperation("api/not-method");
        log.setMethod(handler.getClass().getName());
        return true;
      }
      
      // 如果有@IgnoreAuth注解,则不验证token,无验证的API请求,APP传递mobile作为userName参数
      if (annotation != null) {
        String usrName = request.getParameter("mobile");
        if (StringUtils.isBlank(usrName)) {
//          usrName = request.getParameter("imei");
        	System.out.println("mobile参数为空");
        }
        log.setUsername(usrName);
        log.setOperation("api/not-auth");
        return true;
      }
      
      // 从header中获取token,如果header中不存在token,则从参数中获取token
      String token = request.getHeader("token");
      if (StringUtils.isBlank(token)) {
        token = request.getParameter("token");
      }

      // token为空
      if (StringUtils.isBlank(token)) {
        log.setOperation("api/token-empty");
        throw new ApiRRException(ApiResultCode.TOEKN_IS_ERROR, ApiResultCode.TOEKN_IS_ERROR_CODE);
      }
      
      // 查询token信息,存在token验证的请求,使用账户对应的accountId作为操作用户的标记
      TokenEntity tokenEntity = TokenHolder.custom().get(token);
      if (tokenEntity == null) {
        log.setOperation("api/token-invalid");
        log.setUsername(token);
      } else {
        log.setOperation("api/token-ok");
        log.setUsername(tokenEntity.getAcountId().toString());
      }
      if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
        // token失效
        TokenHolder.custom().remove(token);
        throw new ApiRRException(ApiResultCode.TOEKN_IS_ERROR, ApiResultCode.TOEKN_IS_ERROR_CODE);
      }
      return true;
    } finally {
      
      // 获取接口传递的参数
      Enumeration<String> keys = request.getParameterNames();
      Map<String, Object> reqParma = new HashMap<String, Object>();
      if (keys != null) {
        while (keys.hasMoreElements()) {
          String key = keys.nextElement();
          reqParma.put(key, request.getParameter(key));
        }
      }
      if(reqParma.toString().length() < 2500){
        log.setParams(reqParma.toString());
      }
      
      // 保存日志信息
//      LocationIndexHolder.custom().addSysLog(log);
    }
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    if (handler instanceof HandlerMethod) {
      Throwable hasError = (Throwable) request.getAttribute(DispatcherServlet.EXCEPTION_ATTRIBUTE);
      NotifyCustomer customer = ((HandlerMethod) handler).getMethodAnnotation(NotifyCustomer.class);
      if (customer != null && hasError == null) {
        Map<String, String> reqParma = new HashMap<String, String>();
        Enumeration<String> keys = request.getParameterNames();
        String MethodParameter = (String) request.getAttribute("MethodParameter");
        String MethodParameterTwo = (String) request.getAttribute(CommonUtil.METHOD_PARAMETER_TWO);
        
        // 对于结果是String[]类型的暂时不支持,有需要在处理
        if (keys != null) {
          while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            reqParma.put(key, request.getParameter(key));
          }
        }
        if (null != MethodParameter) {
          reqParma.put("MethodParameter", MethodParameter);
        }
        if (null != MethodParameterTwo) {
          reqParma.put("MethodParameterTwo", MethodParameterTwo);
        }
        
        // 分类句柄处理通知
//        LocationIndexHolder.custom().addMsg(new NotifyCustomerMsg(reqParma, customer.handler(), customer.method()));
      }
    }
  }

}
