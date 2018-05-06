package org.patent.utils;

import java.security.MessageDigest;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendSmsUtils {
	private static final Logger logger = LoggerFactory.getLogger(SendSmsUtils.class);

	// 互亿短信平台发送验证码，发送请求接口地址
	private static String Url = "http://106.ihuyi.cn/webservice/sms.php?method=Submit";
	//账户名称
	private static String account = "C79123436";
	// 验证码账户密码
	private static String password = "973d8a51e0c958ccf37db7828553faaa";
	// 16进制字符吗
	private final static String[] hexDigits = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

	/**
	 * 字符转换成16进制
	 * 
	 * @param b
	 * @return
	 */
	private static String byteToHexString(byte b) {
		int n = b;
		if (n < 0) {
			n = 256 + n;
		}
		int d1 = n / 16;
		int d2 = n % 16;
		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * 转换字节数组为16进制字串
	 * 
	 * @param b 字节数组
	 * @return 16进制字串
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	/**
	 * 加密原始密码，加密方式MD5
	 * 
	 * @param origin
	 * @return
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception ex) {
		}
		return resultString;
	}

	/**
	 * 发送短信
	 * @param mobile
	 * @return HashMap：status 发送短信状态 true/false， 
	 * smsId 发送短信Id ，
	 * smsCode 发送的短信验证码 ，
	 * smsMsg 平台返回的提示信息，
	 * smsContent 发送的短信内容
	 */
	public static HashMap<String, Object> sendCheckCode(String mobile){

		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(Url);
		client.getParams().setContentCharset("GBK");
		method.setRequestHeader("ContentType", "application/x-www-form-urlencoded;charset=GBK");
		//生成六位随机验证码
		int mobile_code = (int) ((Math.random() * 9 + 1) * 100000);
		String content = new String("您的验证码是：" + mobile_code + "。请不要把验证码泄露给其他人。");

		NameValuePair[] data = {new NameValuePair("account", account),
				new NameValuePair("password", password),
				new NameValuePair("mobile", mobile),
				new NameValuePair("content", content),};
		method.setRequestBody(data);
		HashMap<String, Object> smsMap = new HashMap<>();
		try {
			client.executeMethod(method);
			String SubmitResult = method.getResponseBodyAsString();
			// 发送回调内容
			Document doc = DocumentHelper.parseText(SubmitResult);
			Element root = doc.getRootElement();
			// 回调状态编码
			String statuCode = root.elementText("code");
			// 回调提示信息
			String smsMsg = root.elementText("msg");
			// 回调发送短信id
			String smsId = root.elementText("smsid");

			if ("2".equals(statuCode)) {
				smsMap.put("status", true);
				smsMap.put("smsId", smsId);
				smsMap.put("smsCode", mobile_code);
				smsMap.put("smsMsg", smsMsg);
				smsMap.put("smsContent", content);
			} else {
				smsMap.put("status", false);
				smsMap.put("smsId", smsId);
				smsMap.put("smsCode", mobile_code);
				smsMap.put("smsMsg", smsMsg);
				smsMap.put("smsContent", content);
			}
			logger.info("发送短信验证" + mobile + ";时间：" + CommonUtil.formatDateToString(new Date(), CommonUtil.DATE_TYPE_YMD_HMS));
		} catch (Exception e) {
			// TODO: handle exception
		}
		return smsMap;
	}
}
