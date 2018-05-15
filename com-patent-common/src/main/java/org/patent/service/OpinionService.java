package org.patent.service;


import java.util.List;
import java.util.Map;
import org.patent.entity.OpinionsEntity;

/**
 * 意见反馈接口类
 * 
 *
 */
public interface OpinionService {

  /**
   * 写入/更新意见反馈
   * 
   * @param opinionsEntity
   */
  public void insertOrUpdateOpinions(OpinionsEntity opinionsEntity);

  /**
   * 后台系统查询APP一键反馈信息
   * 
   * @param map
   * @return
   */
  List<OpinionsEntity> queryList(Map<String, Object> map);

  /**
   * 后台系统查询APP一键反馈信息总数
   * 
   * @param map
   * @return
   */
  int queryTotal(Map<String, Object> map);
}
