package org.easemob.server.api.impl;

import org.easemob.server.api.ChatMessageAPI;
import org.easemob.server.comm.EasemobAPI;
import org.easemob.server.comm.OrgInfo;
import org.easemob.server.comm.ResponseHandler;
import org.easemob.server.comm.TokenUtil;

import io.swagger.client.ApiException;
import io.swagger.client.api.ChatHistoryApi;

public class EasemobChatMessage implements ChatMessageAPI{
	
	private ResponseHandler responseHnadler = new ResponseHandler();
	private ChatHistoryApi api = new ChatHistoryApi();

	@Override
	public Object exportChatMessages(Long limit, String cursor, String query) {
		return responseHnadler.handle(new EasemobAPI() {
			
			@Override
			public Object invokeEasemobAPI() throws ApiException {
                return api.orgNameAppNameChatmessagesGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),query,limit+"",cursor);
			}
		});
	}

}
