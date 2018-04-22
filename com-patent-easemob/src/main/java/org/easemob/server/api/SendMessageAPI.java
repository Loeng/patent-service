package org.easemob.server.api;


/**
 * 环信发送聊天消息接口
 * 
 */
public interface SendMessageAPI {

  /**
   * 
   * @param payload
   * @return
   */
  Object sendMessage(Object payload);
}
