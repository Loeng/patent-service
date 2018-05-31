package org.patent.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.patent.entity.AcountEntity;


public interface AcountDao extends BaseDao<AcountEntity>{
	/**
	 * 
	 * @param mobile
	 * @return
	 */
	AcountEntity queryByAcountName(@Param("mobile") String mobile);

	/**
	 * 根据用户名修改用户信息
	 * @param acount
	 * @return
	 */
	int updateByAcountName(AcountEntity acount);
	
	/**
	 * 
	 * @param acountType
	 * @return
	 */
	List<AcountEntity> queryByAcountType(@Param("acountType") int acountType);

	/**
	 * 根据关键词查询专家用户
	 * @param map
	 * @return
	 */
	List<AcountEntity> queryListByKeywords(@Param("keyword") String keyword);

	void updateHeadImage(AcountEntity acountEntity);
	
	
}
