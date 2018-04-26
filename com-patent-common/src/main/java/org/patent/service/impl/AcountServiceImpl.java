package org.patent.service.impl;

import java.util.Date;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.patent.dao.AcountDao;
import org.patent.entity.AcountEntity;
import org.patent.service.AcountService;
import org.patent.utils.ApiResultCode;
import org.patent.utils.IMUtil;
import org.patent.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.sun.tools.javac.util.List;
import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

/**
 * 账户接口实现类
 * @author Administrator
 *
 */

public class AcountServiceImpl implements AcountService{
	
	@Autowired
	AcountDao acountDao;

	@Override
	public AcountEntity queryObject(Long acountId) {
		// TODO Auto-generated method stub
		return acountDao.queryObject(acountId);
	}

	@Override
	public List<AcountEntity> queryList(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int queryTotal(Map<String, Object> map) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Long save(AcountEntity acount) {
		// TODO Auto-generated method stub
		acount.setPassword(new Sha256Hash(acount.getPassword()).toHex());
		acount.setCreateTime(new Date());
		return acount.getAcountId();
	}

	@Override
	public AcountEntity registerAcount(String acountName, String password) {
		//判断账户是否已经注册
		Assert.isNotNull(acountDao.queryByAcountName(acountName), ApiResultCode.ACCOUNT_IS_REGISTER,ApiResultCode.ACCOUNT_IS_REGISTER_CODE);
		
		//去环信创建账号
		Object imObject = null;
		try {
			imObject = IMUtil.createIMUser(acountName, password);
		} catch (Exception e) {
			//如果环信报异常，就去删除这条数据
			IMUtil.deleteIMUser(acountName);
		}
		if (imObject == null) {
			//如果环信创建账户失败，去删除这条数据
			IMUtil.deleteIMUser(acountName);
		}
		
		//在自己的服务器创建账号
		AcountEntity acountEntity = new AcountEntity();
		acountEntity.setAcountName(acountName);
		acountEntity.setPassword(new Sha256Hash(password).toHex());
		acountEntity.setPhone(acountName);
		acountEntity.setCreateTime(new Date());
		acountDao.save(acountEntity); //保存账号
		return acountEntity;
	}

	@Override
	public void updateAcountInfoByAcountName(AcountEntity acount) {
		acountDao.updateByAcountName(acount);
	}

	@Override
	public void delectAcountByAcountIdOrMobile(Long id, String mobile) {
		acountDao.delete(id);
		IMUtil.deleteIMUser(mobile);
	}

}
