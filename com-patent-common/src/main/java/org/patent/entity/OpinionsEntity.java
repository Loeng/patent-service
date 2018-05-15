package org.patent.entity;

import java.io.Serializable;
import java.util.Date;

public class OpinionsEntity implements Serializable{
	private static final long serialVersionUID = 1L;

	private long id;
	private long acountId;
	private String title;
	private String content;
	private String reply; //回复内容
	private String state;//处理状态
	private Date createTime;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAcountId() {
		return acountId;
	}
	public void setAcountId(long acountId) {
		this.acountId = acountId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReply() {
		return reply;
	}
	public void setReply(String reply) {
		this.reply = reply;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
