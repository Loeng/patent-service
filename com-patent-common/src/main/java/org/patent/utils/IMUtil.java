package org.patent.utils;


import org.easemob.server.api.impl.EasemobIMUsers;
import org.easemob.server.api.impl.EasemobSendMessage;
import org.easemob.server.comm.IMUser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import io.swagger.client.model.Msg;
import io.swagger.client.model.MsgContent;
import io.swagger.client.model.NewPassword;
import io.swagger.client.model.MsgContent.TypeEnum;
import io.swagger.client.model.Nickname;
import io.swagger.client.model.RegisterUsers;
import io.swagger.client.model.UserName;

/**
 * 环信消息处理的工具类
 * @author Administrator
 *
 */

public class IMUtil {

	//发送消息对象
	private static EasemobSendMessage easemobSendMessage = new EasemobSendMessage();

	//用户对象
	private static EasemobIMUsers easemobIMUsers = new EasemobIMUsers();

	//环信用户注册，默认消息昵称
	public static final String REGISTER_NICK = "hello guys~";
	//环信离线推送消息通知标题
	public static final String TITLE_SEMASGE = "推送消息";

	/**
	 * 在环信创建新用户
	 * @param username
	 * @param password
	 * @return
	 */
	public static Object createIMUser(String username,String password) {
		RegisterUsers users = new RegisterUsers();
		users.add(new IMUser().nickName(REGISTER_NICK).username(username).password(password));
		return easemobIMUsers.createNewIMUserSingle(users);
	}	

	/**
	 * 修改环信用户的密码
	 * @param userName
	 * @param password
	 * @return 实体类
	 */
	public static Object modifyIMUserPasswordWithAdminToken(String userName, String password) {
		return easemobIMUsers.modifyIMUserPasswordWithAdminToken(userName, new NewPassword().newpassword(password));
	}

	/**
	 * 在环信上通过用户名删除一个用户
	 * @param username
	 * @return
	 */
	public static final Object deleteIMUser(String username) {
		return easemobIMUsers.deleteIMUserByUserName(username);
	}


	/**
	 * 
	 * @param fromUser  发送者
	 * @param acountName  接受者
	 * @param msg 消息内容
	 * @param type 消息类型，比如：文本 MsgContent.TypeEnum.TXT，图片 MsgContent.TypeEnum.IMG
	 * @return
	 */
	public static Object sendMsgToAcount(String fromUser,String acountName,String msg,TypeEnum type){
		Msg myMsg = new Msg();//构造消息体
		MsgContent msgContent = new MsgContent(); 
		msgContent.type(type).msg(msg); //构造消息内容
		UserName userName = new UserName();
		userName.add(acountName);//设置目标用户
		myMsg.from(fromUser).target(userName).targetType("users").msg(msgContent);
		return easemobSendMessage.sendMessage(myMsg);//发送消息
	}

	/**
	 * 通过用户名修改环信昵称
	 * @param username 
	 * @param nickname
	 * @return
	 */
	public static Object modifyIMUserNickNameByUserName(String username,String nickname) {
		Nickname nickname2 = new Nickname();
		nickname2.setNickname(nickname);
		return easemobIMUsers.modifyIMUserNickNameWithAdminToken(username, nickname2);
	}

	/**
	 * 获取用户是否在线
	 * @param username
	 * @return 1：表示在线 0：表示不在线
	 */
	public static String getIMUserStatus(String username) {
		Object result = easemobIMUsers.getIMUserStatus(username);
		JSONObject jsonObject = JSON.parseObject(result.toString());
		JSONObject jsonObject2 = JSON.parseObject(jsonObject.get("data").toString());
		if (jsonObject2.get(username).equals("online")) {
			return "1";
		}
		return "0";
	}

}
