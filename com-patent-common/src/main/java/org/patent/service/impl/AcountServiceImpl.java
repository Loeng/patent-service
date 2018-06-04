package org.patent.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.shiro.crypto.hash.Sha256Hash;
import org.patent.dao.AcountDao;
import org.patent.entity.AcountEntity;
import org.patent.entity.IdeaEntity;
import org.patent.service.AcountService;
import org.patent.utils.ApiRRException;
import org.patent.utils.ApiResultCode;
import org.patent.utils.IMUtil;
import org.patent.validator.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sun.xml.internal.xsom.impl.scd.Iterators.Map;

/**
 * 账户接口实现类
 * @author Administrator
 *
 */

@Service("acountService")
public class AcountServiceImpl implements AcountService{
	
	@Autowired
	AcountDao acountDao;

	@Override
	public AcountEntity queryObject(Long acountId) {
		// TODO Auto-generated method stub
		return acountDao.queryObject(acountId);
	}

	@Override
	public List<AcountEntity> queryList(String keyword) {
		return acountDao.queryListByKeywords(keyword);
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
	public AcountEntity registerAcount(String acountName, String password,String acountType) {
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
		acountEntity.setAcountType(Integer.parseInt(acountType));
		acountDao.save(acountEntity); //保存账号
		return acountEntity;
	}

	@Override
	public void updateAcountInfo(AcountEntity acount) {
		acountDao.update(acount);
	}

	@Override
	public void delectAcountByAcountIdOrMobile(Long id, String mobile) {
		acountDao.delete(id);
		IMUtil.deleteIMUser(mobile);
	}

	@Override
	public AcountEntity queryByMobile(String mobile) {
		return acountDao.queryByAcountName(mobile);
	}
	
	

	@Transactional
	@Override
	public int update(AcountEntity acountEntity) {
		//修改密码的时候也需要把环信的密码一起修改
		
		if (IMUtil.modifyIMUserPasswordWithAdminToken(acountEntity.getAcountName(), acountEntity.getPassword()) == null) {
			throw new ApiRRException(ApiResultCode.CHANGE_IM_PASSWORD_FAILUE, ApiResultCode.CHANGE_IM_PASSWORD_FAILUE_CODE);
		}
		acountEntity.setPassword(new Sha256Hash(acountEntity.getPassword()).toHex());
		return acountDao.update(acountEntity);
	}

	@Override
	public List<AcountEntity> queryByAcountType(int acountType) {
		
		return acountDao.queryByAcountType(acountType);
	}

	@Override
	public void updateHeadImage(AcountEntity acountEntity) {
		acountDao.updateHeadImage(acountEntity);
	}

	@Override
	public AcountEntity queryByAcountName(String collectible) {
		return acountDao.queryProfessByAcountName(collectible);
	}

	@Override
	public void updateProfessInformation(AcountEntity acountEntity) {
		acountDao.updateSkillsInformation(acountEntity);
	}

	@Override
	public AcountEntity queryByAcountId(long acountId) {
		return acountDao.queryByAcuntId(acountId);
	}

}
