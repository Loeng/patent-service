package org.patent.service;

import java.util.Map;

import org.patent.entity.TokenEntity;

/**
 * token接口
 * @author Administrator
 *
 */

public interface TokenService {

	/**
	 * 根据账户id查询token
	 * @param acountId
	 * @return
	 */
	TokenEntity queryByAcountId(Long acountId);
	
	/**
	 * 根据token查询token的详细信息
	 * @param token
	 * @return
	 */
	TokenEntity queryByToken(String token);
	
	/**
	 * 保存token
	 * @param token
	 */
	void saveToken(TokenEntity token);
	
	/**
	 * 更新token信息
	 * @param tokenEntity
	 */
	void updateToken(TokenEntity tokenEntity);
	
	/**
	 * 生成token
	 * @param acountId
	 * @return
	 */
	Map<String, Object> createToken(long acountId);
	
	/**
	 * 更新token的过期时间
	 * @param acountName
	 * @param date
	 */
	void updateTokenExpireTimeByAcountName(String acountName,String date);
}
