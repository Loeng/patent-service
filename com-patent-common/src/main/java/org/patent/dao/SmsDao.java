package org.patent.dao;

import org.apache.ibatis.annotations.Param;
import org.patent.entity.SmsEntity;

/**
 * 短信验证码，数据访问接口
 * @author Administrator
 *1:一个接口可以继承多个接口
 *2：一个类可以实现多个接口
 *3：一个类值可以继承一个类
 */

public interface SmsDao extends BaseDao<SmsEntity>{

	/**
	 * 使用@param注解将参数映射到xml中
	 * @param mobile
	 * @return
	 */
	SmsEntity queryByMobile(@Param("mobile") String mobile);
}
