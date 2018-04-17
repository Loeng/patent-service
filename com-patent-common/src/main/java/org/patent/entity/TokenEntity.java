package org.patent.entity;


import java.io.Serializable;
import java.util.Date;



/**
 * 用户Token
 * 
 */
public class TokenEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  // 用户ID
  private Long acountId;
  // token
  private String token;
  // 过期时间
  private Date expireTime;
  // 更新时间
  private Date updateTime;

  /**
   * 设置：用户ID
   */
  public void setAcountId(Long acountId) {
    this.acountId = acountId;
  }

  /**
   * 获取：用户ID
   */
  public Long getAcountId() {
    return acountId;
  }

  /**
   * 设置：token
   */
  public void setToken(String token) {
    this.token = token;
  }

  /**
   * 获取：token
   */
  public String getToken() {
    return token;
  }

  /**
   * 设置：过期时间
   */
  public void setExpireTime(Date expireTime) {
    this.expireTime = expireTime;
  }

  /**
   * 获取：过期时间
   */
  public Date getExpireTime() {
    return expireTime;
  }

  /**
   * 设置：更新时间
   */
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }

  /**
   * 获取：更新时间
   */
  public Date getUpdateTime() {
    return updateTime;
  }
}
