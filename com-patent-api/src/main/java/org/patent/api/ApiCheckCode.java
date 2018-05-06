package org.patent.api;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.patent.annotation.IgnoreAuth;
import org.patent.entity.SmsEntity;
import org.patent.service.SmsService;
import org.patent.utils.ApiRRException;
import org.patent.utils.ApiResult;
import org.patent.utils.ApiResultCode;
import org.patent.utils.RegexUtils;
import org.patent.utils.SendSmsUtils;
import org.patent.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短信验证码接口
 * @author Administrator
 *
 */

@RestController
@RequestMapping("/api")
public class ApiCheckCode {

	@Autowired
	private SmsService smsService;
	
	@IgnoreAuth
	@RequestMapping("/sendCheackCode")
	public ApiResult sendCheckCode(HttpServletRequest request) {
		String mobile = request.getParameter("mobile");
		Assert.isBlank(mobile, ApiResultCode.PHONE_IS_ERROR,ApiResultCode.PHONE_IS_ERROR_CODE);
		if (!RegexUtils.checkMobile(mobile)) {
			throw new ApiRRException(ApiResultCode.PHONE_IS_ERROR, ApiResultCode.PHONE_IS_ERROR_CODE);
		}
		
		//发送短信验证码，并且保存
		HashMap<String, Object> smsResult = SendSmsUtils.sendCheckCode(mobile);
		if ((boolean) smsResult.get("status")) {
			//保存短信验证码，有效时间为60秒，并且返回给客户端
			SmsEntity smsEntity = smsService.createSmsCode(mobile, smsResult.get("smsCode").toString());
			HashMap<String, Object> result = new HashMap<>();
			result.put("code", smsEntity.getCode());
			return ApiResult.R().setResult(result);
		}else {
			throw new ApiRRException(ApiResultCode.SEND_SMS_ERROR, ApiResultCode.SEND_SMS_ERROR_CODE);
		}
	}
	
}
