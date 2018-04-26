package org.easemob.server.api.impl;

import java.io.File;

import org.easemob.server.api.FileAPI;
import org.easemob.server.comm.EasemobAPI;
import org.easemob.server.comm.OrgInfo;
import org.easemob.server.comm.ResponseHandler;
import org.easemob.server.comm.TokenUtil;

import io.swagger.client.ApiException;
import io.swagger.client.api.UploadAndDownloadFilesApi;

public class EasemobFile implements FileAPI{
	
	private ResponseHandler responseHnadler = new ResponseHandler();
	private UploadAndDownloadFilesApi api = new UploadAndDownloadFilesApi();

	@Override
	public Object uploadFile(Object file) {
		return responseHnadler.handle(new EasemobAPI() {
			
			@Override
			public Object invokeEasemobAPI() throws ApiException {
				return api.orgNameAppNameChatfilesPost(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, TokenUtil.getAccessToken(), (File)file, true);
			}
		});
	}

	@Override
	public Object downloadFile(String fileUUID, String shareScreect, Boolean isThumbnail) {
		return responseHnadler.handle(new EasemobAPI() {
			
			@Override
			public Object invokeEasemobAPI() throws ApiException {
				return api.orgNameAppNameChatfilesUuidGet(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, TokenUtil.getAccessToken(), fileUUID, shareScreect, isThumbnail);
			}
		});
	}

}
