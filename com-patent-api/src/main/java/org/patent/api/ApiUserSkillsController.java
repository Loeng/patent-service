package org.patent.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.patent.annotation.IgnoreAuth;
import org.patent.entity.AcountEntity;
import org.patent.entity.IdeaEntity;
import org.patent.service.AcountService;
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
 * 专业用户技能更新界面
 * @author Administrator
 *
 */

@RestController
@RequestMapping("/api")
public class ApiUserSkillsController {

	private static final Logger logger = LoggerFactory.getLogger(ApiUserSkillsController.class);


	@Autowired
	AcountService acountService;
	
	@Autowired
	IdeaPushService ideaPushService;

	/**
	 * 根据账户类型查询相反类型用户的信息
	 * @param request
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping("/insertOrUpdateUserSkills")
	public ApiResult insertOrUpdateSkills(HttpServletRequest request) {
		String acountName = request.getParameter("acountName");
		Assert.isBlank(acountName, ApiResultCode.ACCOUNT_IS_EMPTY,ApiResultCode.ACCOUNT_IS_EMPTY_CODE);

		AcountEntity acountEntity = acountService.queryByMobile(acountName);
		List<AcountEntity> resutList = new ArrayList<>();
		if (acountEntity.getAcountType() == 1) {//如果是普通用户，那么就查询所有专业用户的信息
			resutList= acountService.queryByAcountType(2);
		}else {//如果是专业用户，就查询普通用户的信息
//			resutList = acountService.queryByAcountType(1);
		}
		List<IdeaEntity> ideaLists = ideaPushService.queryAllIdeas();
		
		HashMap<String, Object> resultMap = new HashMap<>();
		List<HashMap<String, Object>> hashList= new ArrayList<>();
		if (resutList != null && resutList.size()>0) {
			for(int i = 0;i<resutList.size();i++) {
				AcountEntity acountEntity2 = resutList.get(i);
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("imgUrl", acountEntity2.getImgUrl());
				hashMap.put("nickName", acountEntity2.getNickName());
				hashMap.put("job",acountEntity2.getJob());
				hashMap.put("workExprience",acountEntity2.getWorkExprience());
				hashMap.put("goodAt", acountEntity2.getGoodAt());
				hashMap.put("infomation", acountEntity2.getInfomation());
				hashMap.put("acountName", acountEntity2.getAcountName());
				hashList.add(hashMap);
			}
		}
		resultMap.put("userInfoList", hashList);
		logger.info("app:{}用户,接口名:{}",new Object[] {acountName,"insertOrUpdateSkills"});
		return ApiResult.R().setResult(resultMap);
	}

}
