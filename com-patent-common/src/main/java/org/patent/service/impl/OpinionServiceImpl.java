package org.patent.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.patent.dao.OpinionsDao;
import org.patent.entity.OpinionsEntity;
import org.patent.service.OpinionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("opinionService")
public class OpinionServiceImpl implements OpinionService {

  @Autowired
  private OpinionsDao opinionsDao;

  @Override
  public void insertOrUpdateOpinions(OpinionsEntity opinionsEntity) {
    // 根据实体有无id，判断是写入还是更新
    if (opinionsEntity.getId() != 0 && StringUtils.isNotBlank(String.valueOf(opinionsEntity.getId()))) {
      opinionsDao.update(opinionsEntity);
    } else {
      opinionsEntity.setCreateTime(new Date());
      opinionsDao.save(opinionsEntity);
    }
  }

  @Override
  public List<OpinionsEntity> queryList(Map<String, Object> map) {
    return opinionsDao.queryList(map);
  }

  @Override
  public int queryTotal(Map<String, Object> map) {
//    return opinionsDao.queryTotal(map);
	  return 0;
  }

}

