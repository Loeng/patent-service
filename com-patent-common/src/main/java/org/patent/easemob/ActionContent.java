package org.patent.easemob;

import io.swagger.client.model.MsgContent;

public class ActionContent extends MsgContent {
	private String action;

	MsgContent action(String action) {
		this.action = action;
		return this;
	}
	
	public String getAction(){
		return this.action;
	}
}