package org.patent.api;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.patent.entity.CollectionEntity;
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
	
	/**
	 * 添加我的收藏
	 * @param request
	 * @return
	 */
	@RequestMapping("/insertCollection")
	public ApiResult insertCollections(HttpServletRequest request) {
		String collectible = request.getParameter("collectible");
		String collector = request.getParameter("collector");
		Assert.isBlank(collectible, ApiResultCode.COLLECTION_IS_EMPTY,ApiResultCode.COLLECTION_IS_EMPTY_code);
		
		CollectionEntity collectionEntity = new CollectionEntity();
		collectionEntity.setCollectible(collectible);
		collectionEntity.setCollector(collector);
		addCollectionService.insertCollection(collectionEntity);
		return ApiResult.R();                                                             
	}

	
	/**
	 * 取消收藏单个
	 * @param request
	 * @return
	 */
	@RequestMapping("/cancleCollection")
	public ApiResult cancleCollection(HttpServletRequest request) {
		String collectible = request.getParameter("collectible");
		String collector = request.getParameter("collector");
		Assert.isBlank(collectible, ApiResultCode.COLLECTION_IS_EMPTY,ApiResultCode.COLLECTION_IS_EMPTY_code);
		
		CollectionEntity collectionEntity = new CollectionEntity();
		collectionEntity.setCollectible(collectible);
		collectionEntity.setCollector(collector);
		addCollectionService.cancleCollection(collectionEntity);
		return ApiResult.R();                                                             
	}
	
	/**
	 * 查询收藏单个
	 * @param request
	 * @return
	 */
	@RequestMapping("/queryCollections")
	public ApiResult queryCollection(HttpServletRequest request) {
		String collectible = request.getParameter("collectible");
		String collector = request.getParameter("collector");
		Assert.isBlank(collectible, ApiResultCode.COLLECTION_IS_EMPTY,ApiResultCode.COLLECTION_IS_EMPTY_code);
		
		CollectionEntity collectionEntity = new CollectionEntity();
		collectionEntity.setCollectible(collectible);
		collectionEntity.setCollector(collector);
		HashMap<String, Object> hashMap = new HashMap<>();
		int result = addCollectionService.queryCollectionsOneByOne(collectionEntity);
		if (result == 0) {
			hashMap.put("isCollected", false);
		}else {
			hashMap.put("isCollected", true);
		}
		return ApiResult.R().setResult(hashMap);                                                             
	}
}
