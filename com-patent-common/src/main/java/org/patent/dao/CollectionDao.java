package org.patent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.patent.entity.CollectionEntity;

public interface CollectionDao extends BaseDao<CollectionEntity>{
	
	/**
	 * 通过收藏者的账号查询收藏的对象
	 * @param collector
	 * @return
	 */
	List<CollectionEntity> queryCollection(@Param("collector") String collector);

	void deleteCotions(Long id);

	/**
	 * 取消收藏
	 * @param collectionEntity
	 */
	void cancleCollections(CollectionEntity collectionEntity);

	/**
	 * 查询单个
	 * @param collectionEntity
	 * @return
	 */
	int queryCollectionOneByOne(CollectionEntity collectionEntity);

}
