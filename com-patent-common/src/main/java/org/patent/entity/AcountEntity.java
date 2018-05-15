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
	 private int acountType;//账号类型
	 private String job;//职业
	 private String workExprience;//工作经验
	 private String goodAt;//擅长领域
	 
	 public String getWorkExprience() {
		return workExprience;
	}
	public void setWorkExprience(String workExprience) {
		this.workExprience = workExprience;
	}
	public int getAcountType() {
		return acountType;
	}
	public void setAcountType(int acountType) {
		this.acountType = acountType;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public String getGoodAt() {
		return goodAt;
	}
	public void setGoodAt(String goodAt) {
		this.goodAt = goodAt;
	}
	public String getInfomation() {
		return infomation;
	}
	public void setInfomation(String infomation) {
		this.infomation = infomation;
	}
	private String infomation;//简介
	 
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
