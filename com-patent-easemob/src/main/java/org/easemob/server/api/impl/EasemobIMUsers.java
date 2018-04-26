package org.easemob.server.api.impl;

import org.easemob.server.api.IMUserAPI;
import org.easemob.server.comm.EasemobAPI;
import org.easemob.server.comm.OrgInfo;
import org.easemob.server.comm.ResponseHandler;
import org.easemob.server.comm.TokenUtil;


import io.swagger.client.ApiException;
import io.swagger.client.api.UsersApi;
import io.swagger.client.model.NewPassword;
import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;

public class EasemobIMUsers implements IMUserAPI{
	private UsersApi api = new UsersApi();
	private ResponseHandler responseHandler = new ResponseHandler();
	

	@Override
	public Object createNewIMUserSingle(Object payload) {
		return responseHandler.handle(new EasemobAPI() {
			
			@Override
			public Object invokeEasemobAPI() throws ApiException {
				return api.orgNameAppNameUsersPost(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, (RegisterUsers)payload, TokenUtil.getAccessToken());
			}
		});
	}

	@Override
	public Object createNewIMUserBatch(Object payload) {
		return responseHandler.handle(new EasemobAPI() {
			@Override
			public Object invokeEasemobAPI() throws ApiException {
				return api.orgNameAppNameUsersPost(OrgInfo.ORG_NAME,OrgInfo.APP_NAME, (RegisterUsers) payload,TokenUtil.getAccessToken());
			}
		});
	}

	@Override
	public Object getIMUserByUserName(String userName) {
		return responseHandler.handle(new EasemobAPI() {
			@Override
			public Object invokeEasemobAPI() throws ApiException {
				return api.orgNameAppNameUsersUsernameGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName);
		}
		});
	}

	@Override
	public Object getIMUsersBatch(Long limit, String cursor) {
		return responseHandler.handle(new EasemobAPI() {
			@Override
			public Object invokeEasemobAPI() throws ApiException {
				return api.orgNameAppNameUsersGet(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),limit+"",cursor);
			}
		});
	}

	@Override
	public Object deleteIMUserByUserName(String userName) {
		return responseHandler.handle(new EasemobAPI() {
			@Override
			public Object invokeEasemobAPI() throws ApiException {
				return api.orgNameAppNameUsersUsernameDelete(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),userName);
			}
		});
	}

	@Override
	public Object deleteIMUserBatch(Long limit, String cursor) {
		return responseHandler.handle(new EasemobAPI() {
			@Override
			public Object invokeEasemobAPI() throws ApiException {
				return api.orgNameAppNameUsersDelete(OrgInfo.ORG_NAME,OrgInfo.APP_NAME,TokenUtil.getAccessToken(),limit+"",cursor);
			}
		});
	}

	@Override
	public Object modifyIMUserPasswordWithAdminToken(String userName, Object payload) {
		 return responseHandler.handle(new EasemobAPI() {
		      @Override
		      public Object invokeEasemobAPI() throws ApiException {
		        return api.orgNameAppNameUsersUsernamePasswordPut(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, userName, (NewPassword) payload,
		            TokenUtil.getAccessToken());
		      }
		    });
	}

	@Override
	public Object modifyIMUserNickNameWithAdminToken(String userName, Object payload) {
		   return responseHandler.handle(new EasemobAPI() {
			      @Override
			      public Object invokeEasemobAPI() throws ApiException {
			        return api.orgNameAppNameUsersUsernamePut(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, userName, (Nickname) payload, TokenUtil.getAccessToken());
			      }
			    });
	}

	@Override
	public Object addFriendSingle(String userName, String friendName) {
		 return responseHandler.handle(new EasemobAPI() {
		      @Override
		      public Object invokeEasemobAPI() throws ApiException {
		        return api.orgNameAppNameUsersOwnerUsernameContactsUsersFriendUsernamePost(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, TokenUtil.getAccessToken(),
		            userName, friendName);
		      }
		    });
	}

	@Override
	public Object deleteFriendSingle(String userName, String friendName) {
		return responseHandler.handle(new EasemobAPI() {
		      @Override
		      public Object invokeEasemobAPI() throws ApiException {
		        return api.orgNameAppNameUsersOwnerUsernameContactsUsersFriendUsernameDelete(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, TokenUtil.getAccessToken(),
		            userName, friendName);
		      }
		    });
	}

	@Override
	public Object getFriends(String userName) {
		  return responseHandler.handle(new EasemobAPI() {
		      @Override
		      public Object invokeEasemobAPI() throws ApiException {
		        return api.orgNameAppNameUsersOwnerUsernameContactsUsersGet(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, TokenUtil.getAccessToken(), userName);
		      }
		    });
	}

	@Override
	public Object getIMUserStatus(String userName) {
		   return responseHandler.handle(new EasemobAPI() {
			      @Override
			      public Object invokeEasemobAPI() throws ApiException {
			        return api.orgNameAppNameUsersUsernameStatusGet(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, TokenUtil.getAccessToken(), userName);
			      }
			    });
	}

	@Override
	public Object getOfflineMsgCount(String userName) {
		 return responseHandler.handle(new EasemobAPI() {
		      @Override
		      public Object invokeEasemobAPI() throws ApiException {
		        return api.orgNameAppNameUsersOwnerUsernameOfflineMsgCountGet(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, TokenUtil.getAccessToken(), userName);
		      }
		    });
	}

	@Override
	public Object getSpecifiedOfflineMsgStatus(String userName, String msgId) {
		  return responseHandler.handle(new EasemobAPI() {
		      @Override
		      public Object invokeEasemobAPI() throws ApiException {
		        return api.orgNameAppNameUsersUsernameOfflineMsgStatusMsgIdGet(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, TokenUtil.getAccessToken(), userName,
		            msgId);
		      }
		    });
	}

	@Override
	public Object deactivateIMUser(String userName) {
		  return responseHandler.handle(new EasemobAPI() {
		      @Override
		      public Object invokeEasemobAPI() throws ApiException {
		        return api.orgNameAppNameUsersUsernameDeactivatePost(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, TokenUtil.getAccessToken(), userName);
		      }
		    });
	}

	@Override
	public Object activateIMUser(String userName) {
		 return responseHandler.handle(new EasemobAPI() {
		      @Override
		      public Object invokeEasemobAPI() throws ApiException {
		        return api.orgNameAppNameUsersUsernameDeactivatePost(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, TokenUtil.getAccessToken(), userName);
		      }
		    });
	}

	@Override
	public Object disconnectIMUser(String userName) {
		 return responseHandler.handle(new EasemobAPI() {
		      @Override
		      public Object invokeEasemobAPI() throws ApiException {
		        return api.orgNameAppNameUsersUsernameDisconnectGet(OrgInfo.ORG_NAME, OrgInfo.APP_NAME, TokenUtil.getAccessToken(), userName);
		      }
		    });
	}

}
