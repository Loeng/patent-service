package org.easemob.server.comm;

import io.swagger.client.model.User;

public class IMUser extends User{
	private String nickName;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public IMUser nickName(String nickName) {
		this.nickName = nickName;
		return this;
	}
}
