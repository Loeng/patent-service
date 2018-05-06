package org.patent.controller;



import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.subject.support.DelegatingSubject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import org.com.patent.redis.JedisHolder;
import org.patent.utils.R;
import org.patent.utils.RRException;
import org.patent.utils.ShiroUtils;

/**
 * 登录相关
 * 
 */
@Controller
public class SysLoginController {

  /**
   * 登录
   */
  @ResponseBody
  @RequestMapping(value = "/sys/login", method = RequestMethod.POST)
  public R login(String username, String password) throws IOException {
    try {
      Subject subject = ShiroUtils.getSubject();
      password = new Sha256Hash(password).toHex();
      UsernamePasswordToken token = new UsernamePasswordToken(username, password);
      subject.login(token);
      Session session = (Session) JedisHolder.custom().T().getObject(subject.getSession(false).getId().toString());
      session.setAttribute("login", true);
      Session old = subject.getSession(false);
      Iterator<Object> it = old.getAttributeKeys().iterator();
      while (it.hasNext()) {
        // 封装公共信息到redis里面
        Object key = it.next();
        Object value = old.getAttribute(key);
        if (value instanceof Serializable) {
          if (DefaultSubjectContext.PRINCIPALS_SESSION_KEY.equals(key)) {
            List<PrincipalCollection> runAsPrincipals = new LinkedList<PrincipalCollection>();
            runAsPrincipals.add(subject.getPrincipals());
            session.setAttribute(DelegatingSubject.class.getName() + ".RUN_AS_PRINCIPALS_SESSION_KEY", runAsPrincipals);
          } else {
            session.setAttribute(key, value);
          }
        }
      }
      JedisHolder.custom().T().setObject(session.getId().toString(), session);
    } catch (UnknownAccountException e) {
      throw new RRException(e.getMessage());
    } catch (IncorrectCredentialsException e) {
      throw new RRException(e.getMessage());
    } catch (LockedAccountException e) {
      throw new RRException(e.getMessage());
    } catch (AuthenticationException e) {
      throw new RRException("账户验证失败");
    }
    return R.ok();
  }

  /**
   * 退出
   */
  @RequestMapping(value = "logout", method = RequestMethod.GET)
  public String logout() {
    ShiroUtils.getSubject().logout();
    return "redirect:login.html";
  }

}

