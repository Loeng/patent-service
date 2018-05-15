package org.patent.entity;

import java.io.Serializable;
import java.util.Date;

public class AppversionEntity implements Serializable{
	  private static final long serialVersionUID = 1L;

	  // 自增主键
	  private Long id;
	  // 内部版本号
	  private int versionCode;
	  // 外部版本号
	  private String version;
	  // 版本更新时间
	  private Date updateTime;
	  // 版本更新内容
	  private String desc;
	  // 版本下载地址
	  private String url;
	  // 创建时间
	  private Date createTime;
	  // 升级包状态 0-禁用 1-启用
	  private int status;
	  // 升级包类型 0可选 1强制
	  private int type;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public int getVersionCode() {
		return versionCode;
	}
	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	  
}
