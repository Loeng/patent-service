package org.patent.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.patent.entity.AppversionEntity;

public interface AppversionService {
	/**
	   * 根据版本id查询版本信息
	   * 
	   * @param id
	   * @return
	   */
	  AppversionEntity queryObject(Long id);

	  /**
	   * 分页查询版本信息集合
	   * 
	   * @param map
	   * @return
	   */
	  List<AppversionEntity> queryList(Map<String, Object> map);

	  /**
	   * 查询版本数据量
	   * 
	   * @param map
	   * @return
	   */
	  int queryTotal(Map<String, Object> map);

	  /**
	   * 保存版本信息
	   * 
	   * @param appversion
	   */
	  void save(AppversionEntity appversion);

	  /**
	   * 更新版本信息
	   * 
	   * @param appversion
	   */
	  void update(AppversionEntity appversion);

	  /**
	   * 根据版本id，删除版本信息
	   * 
	   * @param id
	   */
	  void delete(Long id);

	  /**
	   * 根据版本id，批量删除版本信息
	   * 
	   * @param ids
	   */
	  void deleteBatch(Long[] ids);
	  
	  /**
	   * 检查Android是否有更新版本
	   * 
	   * @param version
	   * @param versionCode
	   */
	  HashMap<String, Object> checkAndroidVersion(String versionCode);
}
