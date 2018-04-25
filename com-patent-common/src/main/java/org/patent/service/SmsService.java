package org.patent.service;

import org.patent.entity.SmsEntity;

/**
 * 短信服务类
 * @author Administrator
 *
 */
public interface SmsService {

	/**
	 * 根据手机号查询短信验证信息
	 */
	SmsEntity queryByMobile(String mobile);
	
	/**
	 * 保存短信验证码信息
	 */
	void save(SmsEntity sms);
	
	/**
	 * 更新短信验证码
	 */
	void update(SmsEntity sms);
	
	/**
	 * 生成短信验证码
	 */
	SmsEntity createSmsCode(String mobile,String code);
	
}
