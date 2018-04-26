package org.easemob.server.api.impl;

import org.easemob.server.api.AuthTokenAPI;
import org.easemob.server.comm.TokenUtil;

public class EasemobAuthToken implements AuthTokenAPI{

	@Override
	public Object getAuthToken() {
		return TokenUtil.getAccessToken();
	}

}
