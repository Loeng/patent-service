package org.patent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.patent.entity.IdeaEntity;

public interface IdeaDao extends BaseDao<IdeaEntity>{

	void inserNewIdea(IdeaEntity ideaEntity);

	void updateIdeas(IdeaEntity ideaEntity);

	List<IdeaEntity> queryMyPushIdeas(@Param("acountId") long acountId);

	List<IdeaEntity> queryAllIdeas();

	void deleteByAcountId(@Param("ideaId") String acountId);

}
