package org.patent.dao;

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
	
	
}
