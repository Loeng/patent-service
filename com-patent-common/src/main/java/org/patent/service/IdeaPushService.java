package org.patent.service;

import java.util.List;

import org.patent.entity.IdeaEntity;

public interface IdeaPushService {
	
	/**
	 * 插入新的发布需求
	 * @param ideaEntity
	 */
	public void insertNewIdea(IdeaEntity ideaEntity);
	
	public void updateIdeasList(IdeaEntity ideaEntity);

	public List<IdeaEntity> queryMyIdeaPushed(String acountName);

	public List<IdeaEntity> queryAllIdeas();

	public void delectByAcountId(String acountId);


}
