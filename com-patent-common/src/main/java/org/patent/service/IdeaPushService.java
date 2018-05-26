package org.patent.service;

import org.patent.entity.IdeaEntity;

public interface IdeaPushService {
	
	/**
	 * 插入新的发布需求
	 * @param ideaEntity
	 */
	public void insertNewIdea(IdeaEntity ideaEntity);

}
