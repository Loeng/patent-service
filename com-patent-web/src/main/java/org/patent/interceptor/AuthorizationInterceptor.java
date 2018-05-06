package org.patent.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.patent.annotation.IgnoreAuth;
import org.patent.entity.TokenEntity;
import org.patent.utils.ApiResultCode;
import org.patent.validator.Assert;
import org.patent.interceptor.RRException;
import org.patent.context.TokenHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 拦截器
 * 
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

    // 获取当前API请求对应执行的方法名称
    IgnoreAuth ignoreAnnotion = null;
    if (handler instanceof HandlerMethod) {
      HandlerMethod hd = (HandlerMethod) handler;
      ignoreAnnotion = hd.getMethodAnnotation(IgnoreAuth.class);

      // 如果没有@IgnoreToken注解,则验证token
      if (null == ignoreAnnotion) {
        // 从header中获取token,如果header中不存在token,则从参数中获取token,并验证token是否有效
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
          token = request.getParameter("token");
        }
        Assert.isBlank(token, ApiResultCode.TOKEN_IS_EMPTY_MSG,ApiResultCode.TOKEN_IS_EMPTY);
        TokenEntity tokenEntity = TokenHolder.custom().get(token);
        if (tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
          // token失效
          TokenHolder.custom().remove(token);
          throw new RRException(ApiResultCode.TOEKN_IS_ERROR, ApiResultCode.TOEKN_IS_ERROR_CODE);
        }
        return true;
      } else {
        return true;
      }
    }
    
    return false;
  }
}
