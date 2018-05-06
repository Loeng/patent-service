package org.patent.shiro;


import java.util.Iterator;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.web.filter.authc.PassThruAuthenticationFilter;

import org.com.patent.redis.JedisHolder;
import org.patent.utils.ShiroUtils;

public class ShareAuthenFilter extends PassThruAuthenticationFilter {

  private SessionRedisDao sessionDao;

  public void setSessionDao(SessionRedisDao sessionDao) {
    this.sessionDao = sessionDao;
  }

  @Override
  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
    Session session = sessionDao.doReadSession(ShiroUtils.getSession().getId());
    if (session == null) {
      return false;
    }
    Boolean login = (Boolean) session.getAttribute("login");
    if (login == null) {
      Session rediSession = (Session) JedisHolder.custom().T().getObject(session.getId().toString());
      login = (Boolean) rediSession.getAttribute("login");
      if (login != null && login) {
        Iterator<Object> it = rediSession.getAttributeKeys().iterator();
        while (it.hasNext()) {
          Object key = it.next();
          session.setAttribute(key, rediSession.getAttribute(key));
        }
        return true;
      } else {
        return false;
      }
    }
    return login;
  }
}
