package org.patent.service.impl;

import java.util.List;

import org.patent.dao.CollectionDao;
import org.patent.entity.CollectionEntity;
import org.patent.service.AddCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("addCollectionService")
public class AddCollectionServiceImpl implements AddCollectionService{

	@Autowired
	CollectionDao collectionDao;
	
	@Override
	public void insertCollection(CollectionEntity collectionEntity) {
		collectionDao.save(collectionEntity);
	}

	@Override
	public void delectCollection(Long id) {
		collectionDao.deleteCotions(id);
	}

	@Override
	public List<CollectionEntity> queryCollections(CollectionEntity collectionEntity) {
		return collectionDao.queryCollection(collectionEntity.getCollectibleAcount());
	}

	@Override
	public void cancleCollection(CollectionEntity collectionEntity) {
		collectionDao.cancleCollections(collectionEntity);
	}

	@Override
	public int queryCollectionsOneByOne(CollectionEntity collectionEntity) {
		return collectionDao.queryCollectionOneByOne(collectionEntity);
	}

	@Override
	public List<CollectionEntity> queryMyCollections(String acountName) {
		return collectionDao.queryAllCollections(acountName);
	}

}
