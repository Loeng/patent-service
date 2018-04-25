package org.patent.service.impl;

import java.util.Date;

import org.patent.dao.SmsDao;
import org.patent.entity.SmsEntity;
import org.patent.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 短信验证码接口实现类
 * @author Administrator
 *
 */
@Service("smsService")
public class SmsServiceImpl implements SmsService{

	@Autowired
	private SmsDao smsDao;
	
	//短信验证码码的过期时间 120秒
	private static final int EXPIRE = 120;
	
	@Override
	public SmsEntity queryByMobile(String mobile) {
		// TODO Auto-generated method stub
		return smsDao.queryByMobile(mobile);
	}

	@Override
	public void save(SmsEntity sms) {
		// TODO Auto-generated method stub
		smsDao.save(sms);
	}

	@Override
	public void update(SmsEntity sms) {
		// TODO Auto-generated method stub
		smsDao.update(sms);
	}

	@Override
	public SmsEntity createSmsCode(String mobile, String code) {
		// TODO Auto-generated method stub
		Date now = new Date();
		Date expire = new Date(now.getTime()+EXPIRE*1000);//设置过期时间
		SmsEntity smsEntity = queryByMobile(mobile);
		//判断是否生成过验证码
		if (smsEntity == null) {
			smsEntity = new SmsEntity();
			smsEntity.setMobile(mobile);
			smsEntity.setCode(code);
			smsEntity.setUpdateTime(now);
			smsEntity.setExpireTime(expire);
			save(smsEntity);
		}else {
			smsEntity.setCode(code);
			smsEntity.setUpdateTime(now);
			smsEntity.setExpireTime(expire);
			update(smsEntity);
		}
		return smsEntity;
	}

}
