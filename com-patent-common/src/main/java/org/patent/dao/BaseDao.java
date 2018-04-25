package org.patent.dao;

import java.util.List;

import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

/**
 * 基础的dao，还需要在xml中配置相应的sql语句
 * @author Administrator
 *
 * @param <T>
 */

public interface BaseDao<T> {
	/**
	 * 通用的新增
	 * @param t
	 * @return
	 */
	int save(T t);
	
	/*
	 * 条件新增
	 */
	int save(Map<String, Object> map);
	
	 /**
	   * 通用修改
	   * 
	   * @param t
	   * @return
	   */
	  int update(T t);
	
	/**
	 * 通用的修改
	 * @param map
	 * @return
	 */
	int update(Map<String, Object> map);
	
	/**
	 * 通用删除
	 * @param id
	 * @return
	 */
	int delete(Object id);
	
	/**
	 * 通用条件删除
	 * @param map
	 * @return
	 */
	int delete(Map<String, Object> map);

	/**
	 * 通用查询
	 * @param id
	 * @return
	 */
	T queryObject(Object id);
	
	/**
	 * 通用条件查询
	 * @param map
	 * @return
	 */
	 List<T> queryList(Map<String, Object> map);
	 
	 /**
	  * 通用条件查询，返回多条结果
	  * @param id
	  * @return
	  */
	 List<T> queryList(Object id);
	 
	 /**
	   * 通用条件记录总数条件查询
	   * 
	   * @param map
	   * @return
	   */
	  int queryTotal(Map<String, Object> map);

	  /**
	   * 通用记录总数查询
	   * 
	   * @return
	   */
	  int queryTotal();
}
