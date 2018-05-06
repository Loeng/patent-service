package org.patent.listener;


import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.com.patent.redis.JedisHolder;

public class RedisSessionListener implements SessionListener {
  private static final Logger LOGGER = LoggerFactory.getLogger(RedisSessionListener.class);

  @Override
  public void onExpiration(Session session) {
    String sessionId = session.getId().toString();
    JedisHolder.custom().T().delObject(sessionId);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("expiration  a user :{}", sessionId);
    }

  }

  @Override
  public void onStart(Session session) {
    // do nothing,on sessionDao do it

  }

  @Override
  public void onStop(Session session) {
    // do nothing,on sessionDao do it

  }
}
