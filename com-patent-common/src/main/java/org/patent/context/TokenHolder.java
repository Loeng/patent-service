package org.patent.context;


import org.patent.entity.TokenEntity;

/**
 * 通信令牌控制类
 * 
 *
 */
public class TokenHolder {

  private static TokenHolder instance = new TokenHolder();
  // tokne过期时间
  private final static int EXPIRE_TIME = 3600 * 24 * 7;

  private TokenHolder() {

  }

  /**
   * 实例化
   * 
   * @return
   */
  public static TokenHolder custom() {
    return instance;
  }

//  public TokenEntity get(String key) {
////    TokenEntity token = (TokenEntity) JedisHolder.custom().T().getObject(key);
//    return token;
//  }

  public void set(TokenEntity token) {
    this.set(token, EXPIRE_TIME);
  }

  public void set(TokenEntity token, int seconds) {
//    JedisHolder.custom().T().setObject(token.getToken(), token, seconds);
  }

  public TokenEntity remove(String key) {
//    JedisHolder.custom().T().delObject(key);
    return null;
  }
}
