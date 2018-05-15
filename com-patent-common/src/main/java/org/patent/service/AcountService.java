package org.patent.service;

import java.util.List;

import org.patent.entity.AcountEntity;

import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

/**
 * 账号接口类
 * @author Administrator
 *
 */

public interface AcountService {

	/**
	 * 根据账号id查询账号信息
	 * @param acountId
	 * @return
	 */
	AcountEntity queryObject(Long acountId);
	
	/**
	 * 更新密码
	 * @param acountEntity
	 * @return
	 */
	int update(AcountEntity acountEntity);
	
	/**
	 * 通过手机号查询账号信息
	 * @param mobile
	 * @return
	 */
	AcountEntity queryByMobile(String mobile);
	
	/**
	 * 根据账号类型查询相反账号的信息
	 * @param acountType
	 * @return
	 */
	List<AcountEntity> queryByAcountType(int acountType);
	
	/**
	 * 根据条件查询账号列表
	 * @param map 查询条件
	 * @return
	 */
	List<AcountEntity> queryList(Map<String, Object> map);
	
	/**
	 * 根据条件查询账户记录总数
	 * @param map 查询条件
	 * @return
	 */
	int queryTotal(Map<String, Object> map);
	
	/**
	 * 保存账户信息
	 */
	Long save(AcountEntity acount);
	
	/**
	 * 账号注册
	 * @param acountName 账号
	 * @param password 密码
	 * @return
	 */
	AcountEntity registerAcount(String acountName,String password);
	
	/**
	 * 账号
	 * @param acount
	 */
	void updateAcountInfo(AcountEntity acount);

	/**
	 * 根据id删除账户，根据手机号删除id
	 * @param id 账号id
 	 * @param mobile 手机号码
	 */
	void delectAcountByAcountIdOrMobile(Long id,String mobile);
	
}
