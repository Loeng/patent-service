package org.patent.shiro;


import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IgnoreNullSessionSecurityManager extends DefaultWebSecurityManager {
  private static final Logger LOGGER = LoggerFactory.getLogger(IgnoreNullSessionSecurityManager.class);

  @Override
  protected SubjectContext resolveSession(SubjectContext context) {
    if (context.resolveSession() != null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("Context already contains a session.  Returning.");
      }
      return context;
    }
    try {
      Session session = resolveContextSession(context);
      if (session != null) {
        context.setSession(session);
      }
    } catch (InvalidSessionException e) {
      if (LOGGER.isDebugEnabled()) {
        // LOGGER.debug("Resolved SubjectContext context session is invalid.  Ignoring and creating an anonymous "
        // + "(session-less) Subject instance.", e);
      }
    }
    return context;
  }

}
