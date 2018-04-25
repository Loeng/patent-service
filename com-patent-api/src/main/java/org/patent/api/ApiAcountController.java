package org.patent.api;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.patent.annotation.IgnoreAuth;
import org.patent.annotation.NotifyCustomer;
import org.patent.entity.AcountEntity;
import org.patent.entity.SmsEntity;
import org.patent.service.AcountService;
import org.patent.service.SmsService;
import org.patent.utils.ApiRRException;
import org.patent.utils.ApiResult;
import org.patent.utils.ApiResultCode;
import org.patent.utils.RegexUtils;
import org.patent.validator.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 账户相关的接口
 * @author Administrator
 *
 */
@RestController  //相当于@controller和@respoesty的结合
@RequestMapping("/api")
public class ApiAcountController {
	private static final Logger logger = LoggerFactory.getLogger(ApiAcountController.class);
	
	//@Autowired是用在JavaBean中的注解，通过byType形式，用来给指定的字段或方法注入所需的外部资源。
	@Autowired  
	private AcountService acountService;
	@Autowired
	private SmsService smsService;
	
	
	
	  /**
	   * 账户注册
	   * 
	   * @param request
	   * @param mobile 11位手机号码
	   * @param password 账户密码
	   * @param code 短信验证吗
	   * @return ApiResult
	   */
	@IgnoreAuth   //忽略token验证
	@NotifyCustomer(handler = "apiAcountHandler",method = "registerAcount")  //自定义注解
	@RequestMapping("/registerAcount")
	public ApiResult registerAcount(HttpServletRequest request) {
		
		//获取参数，判断参数是否合法
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		String code = request.getParameter("code");
		
		Assert.isBlank(mobile, ApiResultCode.ACCOUNT_IS_EMPTY, ApiResultCode.ACCOUNT_IS_EMPTY_CODE);getClass();
		Assert.isBlank(password, ApiResultCode.PASSWORD_IS_EMPTY, ApiResultCode.PASSWORD_IS_EMPTY_CODE);
		Assert.isBlank(code, ApiResultCode.CHECKCODE_IS_EMPTY, ApiResultCode.CHECKCODE_IS_EMPTY_CODE);
		if (RegexUtils.checkMobile(mobile)) {
			throw new ApiRRException(ApiResultCode.PHONE_IS_ERROR, ApiResultCode.PHONE_IS_ERROR_CODE);
		}
		
		//获取当前验证码，是否有效
		SmsEntity smsEntity = smsService.queryByMobile(mobile);
		Assert.isNull(smsEntity, ApiResultCode.CHECKCODE_IS_ERROR, ApiResultCode.CHECKCODE_INPUT_ERROR_CODE);
		if (smsEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
			//短信验证码已经过期
			throw new ApiRRException(ApiResultCode.CHECKCODE_IS_EMPTY, ApiResultCode.CHECKCODE_IS_INNALID_CODE);
		}
		
		//判断用户提交的短息验证码是否正确，如果正确，注册用户信息并且返回客户端数据
		if (smsEntity.getCode().equals(code)) {
			AcountEntity acountEntity = acountService.registerAcount(mobile, password);
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("acountId", acountEntity.getAcountId());
			hashMap.put("acountName", acountEntity.getAcountName());
			hashMap.put("password", acountEntity.getPassword());
			logger.info("app:{}注册,接口名:{}",new Object[] {mobile,"registerAcount"});
			return ApiResult.R().setResult(hashMap);
		}else {
			throw new ApiRRException(ApiResultCode.CHECKCODE_IS_ERROR, ApiResultCode.CHECKCODE_IS_ERROR_CODE);
		}
	}
	
	
}
