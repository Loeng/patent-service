package org.patent.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.patent.dao.AppversionDao;
import org.patent.entity.AppversionEntity;
import org.patent.service.AppversionService;
import org.patent.utils.CommonUtil;
import org.patent.utils.Query;

@Service("appversionService")
public class AppversionServiceImpl implements AppversionService {
  @Autowired
  private AppversionDao appversionDao;

  @Override
  public AppversionEntity queryObject(Long id) {
    return appversionDao.queryObject(id);
  }

  @Override
  public List<AppversionEntity> queryList(Map<String, Object> map) {
    try {
      return appversionDao.queryList(map);
    } catch (Exception e) {
    }
    return null;
  }

  @Override
  public int queryTotal(Map<String, Object> map) {
//    return appversionDao.queryTotal(map);
	  return 0;
  }

  @Override
  public void save(AppversionEntity appversion) {
    appversion.setCreateTime(new Date());
    appversionDao.save(appversion);
  }

  @Override
  public void update(AppversionEntity appversion) {
    appversion.setUpdateTime(new Date());
    appversionDao.update(appversion);
  }

  @Override
  public void delete(Long id) {
    appversionDao.delete(id);
  }

  @Override
  public void deleteBatch(Long[] ids) {
//    appversionDao.deleteBatch(ids);
  }

  @Override
  public HashMap<String, Object> checkAndroidVersion(String versionCode) {
    // 查询出最新的版本信息，并比较传入版本号与最新版本号的大小
    Query query = new Query(1, 1, "id", "desc");
    // 查看开启的升级包
    query.put("status", 1);
    List<AppversionEntity> appVersionList = this.queryList(query);
    HashMap<String, Object> result = new HashMap<String, Object>();
    if (null != appVersionList && appVersionList.size() > 0) {
      AppversionEntity appVersionEntity = appVersionList.get(0);
      if (CommonUtil.checkVersionCode(Integer.parseInt(versionCode), appVersionEntity.getVersionCode())) {
        result.put("hasNewVesion", true);
        result.put("versionCode", appVersionEntity.getVersionCode());
        result.put("version", appVersionEntity.getVersion());
        result.put("type", appVersionEntity.getType());
        result.put("desc", appVersionEntity.getDesc());
        result.put("url", appVersionEntity.getUrl());
        result.put("updateTime", appVersionEntity.getUpdateTime());
      } else {
        result.put("hasNewVesion", false);
      }
    } else {
      result.put("hasNewVesion", false);
    }
    return result;
  }

}
