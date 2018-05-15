package org.patent.api;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
		String id = request.getParameter("id");
		String acountId = request.getParameter("acountId");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String reply = request.getParameter("reply");
		String state = request.getParameter("state");

		Assert.isBlank(acountId,ApiResultCode.ACCOUNT_IS_EMPTY, ApiResultCode.ACOUNT_INPUT_ERROR_CODE);
		Assert.isBlank(content, ApiResultCode.OPINION_IS_EMPTY_MSG, ApiResultCode.OPINION_IS_EMPTY_CODE);

		OpinionsEntity opinionsEntity = new OpinionsEntity();
		opinionsEntity.setAcountId(Long.valueOf(acountId));
		opinionsEntity.setContent(content);
		if (StringUtils.isNotBlank(id)) {
			opinionsEntity.setId(Long.valueOf(id));
		}
		if (StringUtils.isNotBlank(title)) {
			opinionsEntity.setTitle(title);
		}
		if (StringUtils.isNotBlank(reply)) {
			opinionsEntity.setTitle(reply);
		}
		if (StringUtils.isNotBlank(state)) {
			opinionsEntity.setTitle(state);
		}else {
			opinionsEntity.setState("0");
		}
		opinionService.insertOrUpdateOpinions(opinionsEntity);
		logger.info("写入/更新意见反馈信息成功,账户id:{};接口名:{}", new Object[] {acountId, "insertOrUpdateOpinions"});
		return ApiResult.R();
	}




}
