package org.patent.service;

import java.util.List;

import org.patent.entity.CollectionEntity;

/**
 * 添加收藏接口
 * @author Administrator
 *
 */
public interface AddCollectionService {
	
	/**
	 *插入收藏者信息
	 * @param collectionEntity
	 */
	public void insertCollection(CollectionEntity collectionEntity);
	
	/**
	 *取消收藏
	 * @param collectionEntity
	 */
	public void cancleCollection(CollectionEntity collectionEntity);
	
	
	/**
	 * 根据id删除收藏关系
	 * @param id
	 */
	public void delectCollection(Long id);
	
	/**
	 * 根据收藏者的账号查询所有收藏的对象
	 * @param collectionEntity
	 * @return
	 */
	public List<CollectionEntity> queryCollections(CollectionEntity collectionEntity);
	
	/**
	 * 查询收藏对象单个
	 * @param collectionEntity
	 * @return
	 */
	public int queryCollectionsOneByOne(CollectionEntity collectionEntity);

}
