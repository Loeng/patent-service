package org.patent.api;

import javax.servlet.http.HttpServletRequest;

import org.patent.service.AppversionService;
import org.patent.utils.ApiResult;
import org.patent.utils.ApiResultCode;
import org.patent.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiVersionController {

	@Autowired
	AppversionService appversionService;
	
	/**
	 * 检查版本号是否为最新
	 * @param request
	 * @return
	 */
	@RequestMapping("/cheakVersion")
	public ApiResult checkVersion(HttpServletRequest request) {
		String versioncode = request.getParameter("versionCode");
		Assert.isBlank(versioncode, ApiResultCode.VERSION_IS_EMPTY, ApiResultCode.VERSION_INPUT_ERROR_CODE);
		return ApiResult.R().setResult(appversionService.checkAndroidVersion(versioncode));
	}
}
