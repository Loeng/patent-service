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
import org.patent.oss.OSSFactory;
import org.patent.service.AcountService;
import org.patent.service.SmsService;
import org.patent.service.TokenService;
import org.patent.utils.ApiRRException;
import org.patent.utils.ApiResult;
import org.patent.utils.ApiResultCode;
import org.patent.utils.RRException;
import org.patent.utils.RegexUtils;
import org.patent.validator.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;



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
		String acountType = request.getParameter("acountType");

		Assert.isBlank(mobile, ApiResultCode.ACCOUNT_IS_EMPTY, ApiResultCode.ACCOUNT_IS_EMPTY_CODE);
		Assert.isBlank(password, ApiResultCode.PASSWORD_IS_EMPTY, ApiResultCode.PASSWORD_IS_EMPTY_CODE);
		Assert.isBlank(code, ApiResultCode.CHECKCODE_IS_EMPTY, ApiResultCode.CHECKCODE_IS_EMPTY_CODE);
		if (!RegexUtils.checkMobile(mobile)) {
			throw new ApiRRException(ApiResultCode.PHONE_IS_ERROR, ApiResultCode.PHONE_IS_ERROR_CODE);
		}

		//获取当前验证码，是否有效
		SmsEntity smsEntity = smsService.queryByMobile(mobile);
		Assert.isNull(smsEntity, ApiResultCode.CHECKCODE_IS_ERROR, ApiResultCode.CHECKCODE_IS_ERROR_CODE);
		if (smsEntity.getExpireTime().getTime() < System.currentTimeMillis()) {
			//短信验证码已经过期
			throw new ApiRRException(ApiResultCode.CHECKCODE_IS_INNALID, ApiResultCode.CHECKCODE_IS_INNALID_CODE);
		}

		//判断用户提交的短息验证码是否正确，如果正确，注册用户信息并且返回客户端数据
		if (smsEntity.getCode().equals(code)) {
			AcountEntity acountEntity = new AcountEntity(); 
			HashMap<String, Object> hashMap = new HashMap<>();
			acountService.registerAcount(mobile, password,acountType);
			if (acountType.equals("1")) {//普通用户注册
				hashMap.put("acountType", "1");
			}else {
				//专业用户注册
				hashMap.put("acountType", "2");
			}

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
			resultMap.put("acountType", acountEntity.getAcountType());
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
	@RequestMapping("/checkAcount")
	public ApiResult checkAcount(HttpServletRequest request) {
		String mobile = request.getParameter("mobile");
		Assert.isBlank(mobile, ApiResultCode.ACCOUNT_IS_EMPTY, ApiResultCode.ACCOUNT_IS_EMPTY_CODE);
		Assert.isNull(acountService.queryByMobile(mobile), ApiResultCode.ACCOUNT_NOT_REGISTER, ApiResultCode.ACCOUNT_NOT_REGISTER_CODE);
		System.out.println("验证证号："+mobile);
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
		if (!RegexUtils.checkMobile(mobile)) {
			throw new ApiRRException(ApiResultCode.PHONE_IS_ERROR, ApiResultCode.PHONE_IS_ERROR_CODE);
		}

		AcountEntity acountEntity = acountService.queryByMobile(mobile);
		Assert.isNull(acountEntity, ApiResultCode.ACCOUNT_NOT_REGISTER, ApiResultCode.ACCOUNT_NOT_REGISTER_CODE);
		//获取当前验证码，是否有效
		SmsEntity smsEntity = smsService.queryByMobile(mobile);
		Assert.isNull(smsEntity, ApiResultCode.CHECKCODE_IS_ERROR, ApiResultCode.CHECKCODE_IS_ERROR_CODE);
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
		String nick = request.getParameter("nick");
		String job = request.getParameter("job");
		String skill = request.getParameter("skill");
		String experience = request.getParameter("experience");
		String information = request.getParameter("information");
		String acountName = request.getParameter("acountName");

		Assert.isBlank(acountName, ApiResultCode.ACCOUNT_IS_EMPTY, ApiResultCode.ACCOUNT_IS_EMPTY_CODE);

		AcountEntity acountEntity = new AcountEntity();
		acountEntity.setAcountName(acountName);
		acountEntity.setNickName(nick);
		acountEntity.setJob(job);
		acountEntity.setGoodAt(skill);
		acountEntity.setWorkExprience(experience);
		acountEntity.setInfomation(information);
		acountService.updateProfessInformation(acountEntity);
		return ApiResult.R().setResult(acountEntity);
	}

	/**
	 * 重置密码
	 * @param request
	 * @return
	 */
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
		Assert.isNull(smsEntity, ApiResultCode.CHECKCODE_IS_ERROR, ApiResultCode.CHECKCODE_IS_ERROR_CODE);
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


	@RequestMapping("/updateHeadImage")
	public ApiResult updateHeadImage(@RequestParam("File") CommonsMultipartFile[] fileFiles,
			HttpServletRequest request) {
		String acountId = request.getParameter("acountId");	
		System.out.println("666666666");
		Assert.isBlank(acountId, ApiResultCode.ACCOUNTID_IS_EMPTY, ApiResultCode.ADMINID_IS_EMPTY_CODE);
		if (fileFiles == null) {
			throw new ApiRRException(ApiResultCode.FILE_IS_EMPTY, ApiResultCode.FILE_INPUT_ERROR_CODE);
		}
		StringBuffer sBuffer = new StringBuffer();
		AcountEntity acountEntity = new AcountEntity();
		acountEntity.setAcountId(Long.valueOf(acountId));
		String suffix = fileFiles[0]
				.getOriginalFilename()
				.substring(fileFiles[0]
						.getOriginalFilename()
						.lastIndexOf("."));
		if (".GIF".endsWith(suffix.toUpperCase()) || ".PNG".equals(suffix.toUpperCase()) || ".JPG".equals(suffix.toUpperCase())
				|| ".BMP".equals(suffix.toUpperCase())) {
			int randmonNumber = (int) (Math.random()*10000);
			String url = OSSFactory.build().upload(fileFiles[0].getBytes(),"image"+randmonNumber+suffix);
			System.out.println("url-------->"+url);
			sBuffer.append(url);
		}else {
			throw new ApiRRException(ApiResultCode.FILE_FORMAT_ERROR, ApiResultCode.FILE_FORMAT_ERROR_CODE);
		}
		acountEntity.setImgUrl(sBuffer.toString());
		acountService.updateHeadImage(acountEntity);
		return ApiResult.R();
	}

	@RequestMapping("/querySkillByAcountname")
	public ApiResult querySkillByAcountName(HttpServletRequest request) {
		String acountName = request.getParameter("acountName");
		
		Assert.isBlank(acountName, ApiResultCode.ACCOUNT_IS_EMPTY, ApiResultCode.ACCOUNT_IS_EMPTY_CODE);
		
		AcountEntity acountEntity = acountService.queryByAcountName(acountName);
		HashMap<String, Object> hashMap = new HashMap<>();
		hashMap.put("nickName",acountEntity.getNickName());
		hashMap.put("job",acountEntity.getJob());
		hashMap.put("goodAt",acountEntity.getGoodAt());
		hashMap.put("workExprience",acountEntity.getWorkExprience());
		hashMap.put("infomation",acountEntity.getInfomation());
		return ApiResult.R().setResult(hashMap);
	}

}
