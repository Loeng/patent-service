package org.patent.dao;

import org.apache.ibatis.annotations.Param;
import org.patent.entity.TokenEntity;

public interface TokenDao extends BaseDao<TokenEntity>{

	/**
	 * 
	 * @return token的实体类
	 */
	TokenEntity queryTokenByAcountId(@Param("acountId") long acountId);
	
	/**
	 * 通过token查询信息
	 * @param token
	 * @return
	 */
	TokenEntity queryTokenByToken(@Param("token") String token);
	
}
