package org.patent.dao;

import org.patent.entity.IdeaEntity;

public interface IdeaDao extends BaseDao<IdeaEntity>{

	void inserNewIdea(IdeaEntity ideaEntity);

}
