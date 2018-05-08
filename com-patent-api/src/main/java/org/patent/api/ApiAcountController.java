package org.patent.api;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.patent.annotation.IgnoreAuth;
import org.patent.annotation.NotifyCustomer;
import org.patent.entity.AcountEntity;
import org.patent.entity.SmsEntity;
import org.patent.service.AcountService;
import org.patent.service.SmsService;
import org.patent.service.TokenService;
import org.patent.utils.ApiRRException;
import org.patent.utils.ApiResult;
import org.patent.utils.ApiResultCode;
import org.patent.utils.DateUtil;
import org.patent.utils.RRException;
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
	@Autowired
	private TokenService tokenService;

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
	@RequestMapping("/acountRegister")
	public ApiResult registerAcount(HttpServletRequest request) {

		//获取参数，判断参数是否合法
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		String code = request.getParameter("code");

		Assert.isBlank(mobile, ApiResultCode.ACCOUNT_IS_EMPTY, ApiResultCode.ACCOUNT_IS_EMPTY_CODE);
		Assert.isBlank(password, ApiResultCode.PASSWORD_IS_EMPTY, ApiResultCode.PASSWORD_IS_EMPTY_CODE);
		Assert.isBlank(code, ApiResultCode.CHECKCODE_IS_EMPTY, ApiResultCode.CHECKCODE_IS_EMPTY_CODE);
		if (!RegexUtils.checkMobile(mobile)) {
			throw new ApiRRException(ApiResultCode.PHONE_IS_ERROR, ApiResultCode.PHONE_IS_ERROR_CODE);
		}

		//获取当前验证码，是否有效
		SmsEntity smsEntity = smsService.queryByMobile(mobile);
		Assert.isNull(smsEntity, ApiResultCode.CHECKCODE_IS_ERROR, ApiResultCode.CHECKCODE_INPUT_ERROR_CODE);
		if (smsEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
			//短信验证码已经过期
			throw new ApiRRException(ApiResultCode.CHECKCODE_IS_INNALID, ApiResultCode.CHECKCODE_IS_INNALID_CODE);
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



	/**
	 * 账户登录
	 * @param request
	 * @return 登录成功后返回数据
	 */
	@IgnoreAuth
	@RequestMapping("/acountLogin")
	public ApiResult acountLogin(HttpServletRequest request) {
		//获取参数，判断参数是否合法
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");

		Assert.isBlank(mobile, ApiResultCode.ACCOUNT_IS_EMPTY, ApiResultCode.ACCOUNT_IS_EMPTY_CODE);
		Assert.isBlank(password, ApiResultCode.PASSWORD_IS_EMPTY , ApiResultCode.PASSWORD_IS_EMPTY_CODE);

		//判断账号是否存在
		AcountEntity acountEntity = acountService.queryByMobile(mobile);
		Assert.isNull(acountEntity, ApiResultCode.ACCOUNT_NOT_REGISTER, ApiResultCode.ACCOUNT_NOT_REGISTER_CODE);

		//判断账号密码是否正确，正确则登录成功，并生成通信令牌，返回数据。
		if (acountEntity.getPassword().equals(new Sha256Hash(password).toHex())) {
			Map<String, Object> tokenMap = tokenService.createToken(acountEntity.getAcountId());
			HashMap<String, Object> resultMap = new HashMap<>();
			resultMap.put("token", tokenMap.get("token"));
			resultMap.put("acountId", acountEntity.getAcountId());
			resultMap.put("acountName", acountEntity.getAcountName());
			resultMap.put("nickName", acountEntity.getNickName());
			resultMap.put("phone", acountEntity.getPhone());
			resultMap.put("imgUrl", acountEntity.getImgUrl());
			return ApiResult.R().setResult(resultMap);
		}else {
			throw new ApiRRException(ApiResultCode.PASSWORD_IS_ERROR, ApiResultCode.PASSWORD_IS_ERROR_CODE);
		}
	}

	/**
	 * 检验账号是否已经注册
	 * @param request
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping("checkAcount")
	public ApiResult checkAcount(HttpServletRequest request) {
		String mobile = request.getParameter("mobile");
		Assert.isBlank(mobile, ApiResultCode.ACCOUNT_IS_EMPTY, ApiResultCode.ACCOUNT_IS_EMPTY_CODE);
		Assert.isNull(acountService.queryByMobile(mobile), ApiResultCode.ACCOUNT_NOT_REGISTER, ApiResultCode.ACCOUNT_NOT_REGISTER_CODE);;
		return ApiResult.R();
	}


	/**
	 * 找回密码
	 * @param request
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping("/findPwdBack")
	public ApiResult findPasswordBack(HttpServletRequest request) {
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

		AcountEntity acountEntity = acountService.queryByMobile(mobile);
		Assert.isNull(acountEntity, ApiResultCode.ACCOUNT_NOT_REGISTER, ApiResultCode.ACCOUNT_NOT_REGISTER_CODE);
		//获取当前验证码，是否有效
		SmsEntity smsEntity = smsService.queryByMobile(mobile);
		Assert.isNull(smsEntity, ApiResultCode.CHECKCODE_IS_ERROR, ApiResultCode.CHECKCODE_INPUT_ERROR_CODE);
		if (smsEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
			//短信验证码已经过期
			throw new ApiRRException(ApiResultCode.CHECKCODE_IS_INNALID, ApiResultCode.CHECKCODE_IS_INNALID_CODE);
		}

		if (smsEntity.getCode().equals(code)) {
			acountEntity.setPassword(password);
			acountService.update(acountEntity);
			logger.info("修改密码成功，账户名:{};接口名：{}", new Object[] {mobile, "findPwdBack"});
			return ApiResult.R();
		}else {
			throw new ApiRRException(ApiResultCode.CHECKCODE_IS_ERROR, ApiResultCode.CHECKCODE_IS_ERROR_CODE);
		}
	}

	/**
	 * 更新账户信息
	 * @param request
	 * @return
	 */
	@RequestMapping("/updateAcount")
	public ApiResult updateAcountInfo(HttpServletRequest request) {
		String acountId = request.getParameter("acountId");
		String nickName = request.getParameter("nickName");
		String phone = request.getParameter("phone");
		String imgUrl = request.getParameter("imgUrl");

		Assert.isBlank(acountId, ApiResultCode.ACCOUNT_IS_EMPTY, ApiResultCode.ACCOUNT_IS_EMPTY_CODE);
		AcountEntity acountEntity = acountService.queryObject(Long.valueOf(acountId));
		Assert.isNull(acountEntity, ApiResultCode.ACCOUNT_NOT_REGISTER, ApiResultCode.ACCOUNT_NOT_REGISTER_CODE);

		if (StringUtils.isNotBlank(imgUrl)) {
			acountEntity.setImgUrl(imgUrl);
		}

		if (StringUtils.isNotBlank(nickName)) {
			acountEntity.setNickName(nickName);
		}

		if (StringUtils.isNotBlank(phone)) {
			if (!RegexUtils.checkMobile(phone)) {
				throw new RRException(ApiResultCode.PHONE_IS_ERROR);
			}
			acountEntity.setPhone(phone);
		}

		return ApiResult.R().setResult(acountEntity);
	}

	@RequestMapping("/resetPassword")
	public ApiResult resetPassword(HttpServletRequest request) {
		//获取参数，判断参数是否合法
		String mobile = request.getParameter("mobile");
		String password = request.getParameter("password");
		String newPassword = request.getParameter("newPassword");
		String code = request.getParameter("code");

		Assert.isBlank(mobile, ApiResultCode.ACCOUNT_IS_EMPTY, ApiResultCode.ACCOUNT_IS_EMPTY_CODE);getClass();
		Assert.isBlank(password, ApiResultCode.PASSWORD_IS_EMPTY, ApiResultCode.PASSWORD_IS_EMPTY_CODE);
		Assert.isBlank(code, ApiResultCode.CHECKCODE_IS_EMPTY, ApiResultCode.CHECKCODE_IS_EMPTY_CODE);
		Assert.isBlank(newPassword, ApiResultCode.NEWPASSWORD_IS_EMPTY, ApiResultCode.NEWPASSWORD_IS_EMPTY_CODE);
		Assert.isNotEquals(password, newPassword, ApiResultCode.OLD_NEW_PASSWORD_IS_SAME, ApiResultCode.OLD_NEW_PASSWORD_IS_SAME_CODE);

		AcountEntity acountEntity = acountService.queryByMobile(mobile);
		Assert.isNull(acountEntity, ApiResultCode.ACCOUNT_NOT_REGISTER, ApiResultCode.ACCOUNT_NOT_REGISTER_CODE);
		//获取当前验证码，是否有效
		SmsEntity smsEntity = smsService.queryByMobile(mobile);
		Assert.isNull(smsEntity, ApiResultCode.CHECKCODE_IS_ERROR, ApiResultCode.CHECKCODE_INPUT_ERROR_CODE);
		if (smsEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
			//短信验证码已经过期
			throw new ApiRRException(ApiResultCode.CHECKCODE_IS_INNALID, ApiResultCode.CHECKCODE_IS_INNALID_CODE);
		}

		//判断用户提交的短息验证码是否正确，如果正确，注册用户信息并且返回客户端数据
		if (smsEntity.getCode().equals(code)) {
			acountEntity.setPassword(newPassword);
			acountService.update(acountEntity);
			HashMap<String, Object> hashMap = new HashMap<>();
			hashMap.put("acountId", acountEntity.getAcountId());
			hashMap.put("acountName", acountEntity.getAcountName());
			logger.info("app:{}注册,接口名:{}",new Object[] {mobile,"resetPassword"});
			return ApiResult.R().setResult(hashMap);
		}else {
			throw new ApiRRException(ApiResultCode.CHECKCODE_IS_ERROR, ApiResultCode.CHECKCODE_IS_ERROR_CODE);
		}
	}

}
