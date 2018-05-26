package org.patent.api;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.patent.entity.IdeaEntity;
import org.patent.service.IdeaPushService;
import org.patent.utils.ApiResult;
import org.patent.utils.ApiResultCode;
import org.patent.validator.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 需求发布的相关接口
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/api")
public class IdeaPushController {
	private static final Logger logger = LoggerFactory.getLogger(IdeaPushController.class);

	@Autowired
	IdeaPushService ideaPushService;
	
	/**
	 * 插入一条信发布的需求
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertNewIdeas")
	public ApiResult insertIdeaPush(HttpServletRequest request) {
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		String acountId = request.getParameter("acountId");
		String ideaImgUrl = request.getParameter("ideaImageUrl");
		String fileUrl = request.getParameter("fileUrl");
		
		Assert.isBlank(acountId, ApiResultCode.ACCOUNTID_IS_EMPTY, ApiResultCode.ADMINID_IS_EMPTY_CODE);
		Assert.isBlank(title, ApiResultCode.ACCOUNTID_IS_EMPTY, ApiResultCode.ADMINID_IS_EMPTY_CODE);
		Assert.isBlank(content, ApiResultCode.ACCOUNTID_IS_EMPTY, ApiResultCode.ACOUNT_INPUT_ERROR_CODE);
		
		IdeaEntity ideaEntity = new IdeaEntity();
		ideaEntity.setAcountId(Long.valueOf(acountId));
		ideaEntity.setTitle(title);
		ideaEntity.setContent(content);
		if (StringUtils.isNotBlank(fileUrl)) {
			ideaEntity.setIdeaImage(ideaImgUrl);
		}
		if (StringUtils.isNotBlank(fileUrl)) {
			ideaEntity.setIdeaFile(fileUrl);
		}
		ideaEntity.setCreateTime(new Date());
		ideaPushService.insertNewIdea(ideaEntity);
		
		logger.info("插入需求信息成功,账户id:{};接口名:{}", new Object[] {acountId, "insertNewIdeas"});
		return ApiResult.R();
	}

}
