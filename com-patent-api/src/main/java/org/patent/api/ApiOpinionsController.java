package org.patent.api;

import javax.servlet.http.HttpServletRequest;

import org.patent.entity.OpinionsEntity;
import org.patent.service.OpinionService;
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
public class ApiOpinionsController {
	private static final Logger logger = LoggerFactory.getLogger(ApiOpinionsController.class);

	@Autowired
	private OpinionService opinionService;

	@RequestMapping("/writeOrUpdateOption")
	public ApiResult insertOpisions(HttpServletRequest request) {
		String acountId = request.getParameter("acountId");
		String content = request.getParameter("content");

		Assert.isBlank(acountId,ApiResultCode.ACCOUNT_IS_EMPTY, ApiResultCode.ACOUNT_INPUT_ERROR_CODE);
		Assert.isBlank(content, ApiResultCode.OPINION_IS_EMPTY_MSG, ApiResultCode.OPINION_IS_EMPTY_CODE);

		OpinionsEntity opinionsEntity = new OpinionsEntity();
		opinionsEntity.setAcountId(Long.valueOf(acountId));
		opinionsEntity.setContent(content);
		opinionService.insertOrUpdateOpinions(opinionsEntity);
		logger.info("写入/更新意见反馈信息成功,账户id:{};接口名:{}", new Object[] {acountId, "insertOrUpdateOpinions"});
		return ApiResult.R();
	}




}
