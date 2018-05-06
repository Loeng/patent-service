package org.patent.shiro;


import java.io.Serializable;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.com.patent.redis.JedisHolder;

public class SessionRedisDao extends MemorySessionDAO {

  private static final Logger LOGGER = LoggerFactory.getLogger(SessionRedisDao.class);

  @Override
  protected Serializable doCreate(Session session) {
    Serializable id = super.doCreate(session);
    JedisHolder.custom().T().setObject(id.toString(), session);
    return id;
  }

  @Override
  protected Session doReadSession(Serializable sessionId) {
    Session session = super.doReadSession(sessionId);
    if (session == null) {
      session = (Session) JedisHolder.custom().T().getObject(sessionId.toString());
      if (session != null) {
        storeSession(sessionId, session);
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("store a login session��[{}] to local finished!", session.getId().toString());
        }
      }
    }
    return session;
  }

  @Override
  public void delete(Session session) {
    super.delete(session);
    JedisHolder.custom().T().delObject(session.getId().toString());
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("delete a session��[{}] to redis finished!", session.getId().toString());
    }
  }
}
