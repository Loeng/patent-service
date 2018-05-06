package org.patent.shiro;


import org.patent.entity.SysUserEntity;
import org.patent.service.SysUserService;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 认证
 * 
 */
public class UserRealm extends AuthorizingRealm {

  @Autowired
  private SysUserService sysUserService;


  /**
   * 授权(验证权限时调用)
   */
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    // 获取当前用户已授权的菜单
    SysUserEntity user = (SysUserEntity) principals.getPrimaryPrincipal();
    Long userId = user.getUserId();
    List<String> permsList = null;
    permsList = sysUserService.queryAllPerms(userId);
    // 用户权限列表
    Set<String> permsSet = new HashSet<String>();
    for (String perms : permsList) {
      if (StringUtils.isBlank(perms)) {
        continue;
      }
      permsSet.addAll(Arrays.asList(perms.trim().split(",")));
    }
    // 缓存当前用户的权限信息
    SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
    info.setStringPermissions(permsSet);
    return info;
  }

  /**
   * 认证(登录时调用)
   */
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
    String username = (String) token.getPrincipal();
    String password = new String((char[]) token.getCredentials());
    // 查询用户信息
    SysUserEntity user = sysUserService.queryByUserName(username);
    // 账号不存在
    if (user == null) {
      throw new UnknownAccountException("账号或密码不正确");
    }
    // 密码错误
    if (!password.equals(user.getPassword())) {
      throw new IncorrectCredentialsException("账号或密码不正确");
    }
    // 账号锁定
    if (user.getStatus() == 0) {
      throw new LockedAccountException("账号已被锁定,请联系管理员");
    }
    SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
    return info;
  }

}
