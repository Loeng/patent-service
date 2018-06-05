package org.patent.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.patent.annotation.IgnoreAuth;
import org.patent.entity.AcountEntity;
import org.patent.entity.CollectionEntity;
import org.patent.service.AcountService;
import org.patent.service.AddCollectionService;
import org.patent.utils.ApiResult;
import org.patent.utils.ApiResultCode;
import org.patent.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ApiInsertCollectionController {
	
	@Autowired
	AddCollectionService addCollectionService;
	
	@Autowired
	AcountService acountService;
	
	/**
	 * 添加我的收藏
	 * @param request
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping("/insertCollection")
	public ApiResult insertCollections(HttpServletRequest request) {
		String collectible = request.getParameter("collectible");
		String collector = request.getParameter("collector");
		Assert.isBlank(collectible, ApiResultCode.COLLECTION_IS_EMPTY,ApiResultCode.COLLECTION_IS_EMPTY_code);
		
		CollectionEntity collectionEntity = new CollectionEntity();
		collectionEntity.setCollectibleAcount(collectible);
		collectionEntity.setCollectorAcount(collector);
		addCollectionService.insertCollection(collectionEntity);
		return ApiResult.R();                                                             
	}

	
	/**
	 * 取消收藏单个
	 * @param request
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping("/cancleCollection")
	public ApiResult cancleCollection(HttpServletRequest request) {
		String collectible = request.getParameter("collectible");
		String collector = request.getParameter("collector");
		Assert.isBlank(collectible, ApiResultCode.COLLECTION_IS_EMPTY,ApiResultCode.COLLECTION_IS_EMPTY_code);
		
		CollectionEntity collectionEntity = new CollectionEntity();
		collectionEntity.setCollectibleAcount(collectible);
		collectionEntity.setCollectorAcount(collector);
		addCollectionService.cancleCollection(collectionEntity);
		return ApiResult.R();                                                             
	}
	
	/**
	 * 查询收藏单个
	 * @param request
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping("/queryCollections")
	public ApiResult queryCollection(HttpServletRequest request) {
		String collectible = request.getParameter("collectibleAcount");
		String collector = request.getParameter("collectorAcount");
		Assert.isBlank(collectible, ApiResultCode.COLLECTION_IS_EMPTY,ApiResultCode.COLLECTION_IS_EMPTY_code);
		
		CollectionEntity collectionEntity = new CollectionEntity();
		collectionEntity.setCollectibleAcount(collectible);
		collectionEntity.setCollectorAcount(collector);
		HashMap<String, Object> hashMap = new HashMap<>();
		int result = addCollectionService.queryCollectionsOneByOne(collectionEntity);
		if (result == 0) {
			hashMap.put("isCollected", false);
		}else {
			hashMap.put("isCollected", true);
		}
		return ApiResult.R().setResult(hashMap);                                                             
	}

	
	/**
	 * 查询我的全部收藏
	 * @param request
	 * @return
	 */
	@IgnoreAuth
	@RequestMapping("/queryCollectionsOfMine")
	public ApiResult queryAllMyCollections(HttpServletRequest request) {
		String acountName = request.getParameter("acountName");
		System.out.println("acountName---------->"+acountName);
		Assert.isBlank(acountName, ApiResultCode.ACCOUNTNAME_IS_EMPTY, ApiResultCode.ACCOUNTNAME_IS_EMPTY_CODE);
		
		List<CollectionEntity> collectionsList = addCollectionService.queryMyCollections(acountName);
		List<AcountEntity> acountList = new ArrayList<>();
		HashMap<String, Object> resultMap = new HashMap<>();
		List<HashMap<String, Object>> hashList= new ArrayList<>();
		for(int i = 0;i<collectionsList.size();i++) {
			System.out.println("被收藏的账号："+collectionsList.get(i).getCollectibleAcount());
			AcountEntity acountEntity2 = new AcountEntity();
			acountEntity2 = acountService.queryByAcountName(collectionsList.get(i).getCollectibleAcount());
			acountList.add(acountEntity2);
		}
		if (acountList != null && acountList.size() > 0) {
			for(int i = 0;i<acountList.size();i++) {
				AcountEntity acountEntity2 = acountList.get(i);
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
		return ApiResult.R().setResult(resultMap);
	}


}
