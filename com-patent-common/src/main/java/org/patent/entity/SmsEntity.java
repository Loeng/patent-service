package org.patent.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 短信验证码实体类
 * @author Administrator
 *
 */


public class SmsEntity implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;//用户id
	private String mobile;//手机号码
	private String code;//短信验证码
	private Date expireTime;//过期时间
	private Date updateTime;//更新时间
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}


}
