package org.patent.service;

import org.patent.entity.SysOssEntity;

import java.util.List;
import java.util.Map;

/**
 * 文件上传
 */
public interface SysOssService {

  /**
   * 查询附件信息
   * 
   * @param id 附件ID
   * @return
   */
  SysOssEntity queryObject(Long id);

  /**
   * 查询附件信息列表
   * 
   * @param map 查询条件
   * @return
   */
  List<SysOssEntity> queryList(Map<String, Object> map);

  /**
   * 查询附件信息记录总数
   * 
   * @param map 查询条件
   * @return
   */
  int queryTotal(Map<String, Object> map);

  /**
   * 保存附件信息
   * 
   * @param sysOss 附件信息实体
   */
  void save(SysOssEntity sysOss);

  /**
   * 更新附件信息
   * 
   * @param sysOss 附件信息实体
   */
  void update(SysOssEntity sysOss);

  /**
   * 删除附件信息
   * 
   * @param id 附件ID
   */
  void delete(Long id);

  /**
   * 批量删除附件信息
   * 
   * @param ids 附件ID数组
   */
  void deleteBatch(Long[] ids);

}

