package org.patent.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 账户实体类
 * @author Administrator
 *
 */

public class AcountEntity implements Serializable{
	 private static final long serialVersionUID = 1L;
	 
	 private Long acountId;//账户id
	 private String acountName;//账户名称
	 private String nickName;//昵称
	 private String password;//密码
	 private String phone;//手机号码
	 private String imgUrl;//头像地址
	 private Date lastLongTime;//最后一次创建的时间
	 private Date createTime;//创建时间
	public Long getAcountId() {
		return acountId;
	}
	public void setAcountId(Long acountId) {
		this.acountId = acountId;
	}
	public String getAcountName() {
		return acountName;
	}
	public void setAcountName(String acountName) {
		this.acountName = acountName;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getImgUrl() {
		return imgUrl;
	}
	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
	public Date getLastLongTime() {
		return lastLongTime;
	}
	public void setLastLongTime(Date lastLongTime) {
		this.lastLongTime = lastLongTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	 
	
}
