package org.patent.service.impl;

import java.util.List;

import org.patent.dao.IdeaDao;
import org.patent.entity.IdeaEntity;
import org.patent.service.IdeaPushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ideaPushService")
public class IdeaPushServiceImpl implements IdeaPushService{

	@Autowired
	IdeaDao ideaDao;

	@Override
	public void insertNewIdea(IdeaEntity ideaEntity) {
		ideaDao.inserNewIdea(ideaEntity);
	}

	@Override
	public void updateIdeasList(IdeaEntity ideaEntity) {
		ideaDao.updateIdeas(ideaEntity);
	}

	@Override
	public List<IdeaEntity> queryMyIdeaPushed(String acountName) {
		long acountId = Long.parseLong(acountName);
		return ideaDao.queryMyPushIdeas(acountId);
	}

	@Override
	public List<IdeaEntity> queryAllIdeas() {
		return ideaDao.queryAllIdeas();
	}


}
