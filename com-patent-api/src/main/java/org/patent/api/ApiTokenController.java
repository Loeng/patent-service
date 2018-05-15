package org.patent.api;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.patent.annotation.IgnoreAuth;
import org.patent.entity.TokenEntity;
import org.patent.service.TokenService;
import org.patent.utils.ApiResult;
import org.patent.utils.ApiResultCode;
import org.patent.validator.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class ApiTokenController {
	private static final Logger logger = LoggerFactory.getLogger(ApiTokenController.class);

	@Autowired
	private TokenService tokenService;
	
	
	/**
	 * 检验通信令牌是否有效
	 * @param request
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping("/verificationToken")
	public ApiResult verificationToken(HttpServletRequest request) {
		String token = request.getHeader("token");
		if (token == null) {
			token = request.getParameter("token");
		}
		
		Assert.isBlank(token, ApiResultCode.TOKEN_IS_EMPTY_MSG, ApiResultCode.TOKEN_IS_EMPTY);
		TokenEntity tokenEntity = tokenService.queryByToken(token);
		if (tokenEntity == null || tokenEntity.getExpireTime().getTime()<System.currentTimeMillis()) {
			HashMap<String, Object> result = new HashMap<>();
			result.put("verification", false);//token失效
			return ApiResult.R().setResult(result);
		}else {
			HashMap<String, Object> result = new HashMap<>();
			result.put("verification", true);
			logger.info("接口名:{}",new Object[] {"insertOrUpdateSkills"});
			return ApiResult.R().setResult(result);
		}
	}
}
