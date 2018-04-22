package org.easemob.server.comm;


import io.swagger.client.ApiException;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 环信返回响应信息封装类
 * 
 */
public class ResponseHandler {
  private static final Logger logger = LoggerFactory.getLogger(ResponseHandler.class);

  /**
   * 
   * 400 （错误请求）服务器不理解请求的语法。 401 （未授权）请求要求身份验证。对于需要token的接口，服务器可能返回此响应。 403
   * （禁止）服务器拒绝请求。对于群组/聊天室服务，表示本次调用不符合群组/聊天室操作的正确逻辑，例如调用添加成员接口，添加已经在群组里的用户，或者移除聊天室中不存在的成员等操作。 404
   * （未找到）服务器找不到请求的接口。 408 （请求超时）服务器等候请求时发生超时。 413 （请求体过大）请求体超过了5kb，拆成更小的请求体重试即可。 429
   * （服务不可用）请求接口超过调用频率限制，即接口被限流。 500 （服务器内部错误）服务器遇到错误，无法完成请求。 501
   * （尚未实施）服务器不具备完成请求的功能。例如，服务器无法识别请求方法时可能会返回此代码。 502 （错误网关）服务器作为网关或代理，从上游服务器收到无效响应。 503
   * （服务不可用）请求接口超过调用频率限制，即接口被限流。 504 （网关超时）服务器作为网关或代理，但是没有及时从上游服务器收到请求。
   * 
   * @param easemobAPI
   * @return
   */
  public Object handle(EasemobAPI easemobAPI) {
    Object result = null;
    try {
      result = easemobAPI.invokeEasemobAPI();
    } catch (ApiException e) {
      if (e.getCode() == 401) {
        logger.info("The current token is invalid, re-generating token for you and calling it again");
        TokenUtil.initTokenByProp();
        try {
          result = easemobAPI.invokeEasemobAPI();
        } catch (ApiException e1) {
          logger.error(e1.getMessage());
        }
        return result;
      }
      if (e.getCode() == 429) {
        logger.warn("The api call is too frequent");
      }
      if (e.getCode() >= 500) {
        logger.info("The server connection failed and is being reconnected");
        result = retry(easemobAPI);
        if (result != null) {
          return result;
        }
        System.out.println(e);
        logger.error("The server may be faulty. Please try again later");
      }
      logger.error("error_code:{} error_msg:{} error_desc:{}", e.getCode(), e.getMessage(), e.getResponseBody());
    }
    return result;
  }

  public Object retry(EasemobAPI easemobAPI) {
    Object result = null;
    long time = 5;
    for (int i = 0; i < 3; i++) {
      try {
        TimeUnit.SECONDS.sleep(time);
        logger.info("Reconnection is in progress..." + i);
        result = easemobAPI.invokeEasemobAPI();
        if (result != null) {
          return result;
        }
      } catch (ApiException e1) {
        time *= 3;
      } catch (InterruptedException e1) {
        logger.error(e1.getMessage());
      }
    }
    return result;
  }
}
