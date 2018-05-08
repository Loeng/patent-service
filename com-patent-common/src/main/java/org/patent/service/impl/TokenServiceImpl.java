package org.patent.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.patent.context.TokenHolder;
import org.patent.dao.AcountDao;
import org.patent.dao.TokenDao;
import org.patent.entity.AcountEntity;
import org.patent.entity.TokenEntity;
import org.patent.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("tokenService")
public class TokenServiceImpl implements TokenService{

	@Autowired
	TokenDao tokendao;
	@Autowired
	AcountDao acountDao;
	//过期时间设置为一星期
	private final static int EXPIR = 3600*24*7;
	
	@Override
	public TokenEntity queryByAcountId(Long acountId) {
		return tokendao.queryTokenByAcountId(acountId);
	}

	@Override
	public TokenEntity queryByToken(String token) {
		return tokendao.queryTokenByToken(token);
	}

	@Override
	public void saveToken(TokenEntity token) {
		tokendao.save(token);
	}

	@Override
	public void updateToken(TokenEntity tokenEntity) {
		tokendao.update(tokenEntity);
	}

	@Override
	public Map<String, Object> createToken(long acountId) {
		//生成随机码
		String token = UUID.randomUUID().toString();
		Date now = new Date();
		Date expireTime = new Date(now.getTime()+ EXPIR*1000);//毫秒
		
		//是否生成果token
		TokenEntity tokenEntity = tokendao.queryTokenByAcountId(acountId);
		if (tokenEntity == null) {
			tokenEntity = new TokenEntity();
			tokenEntity.setAcountId(acountId);
			tokenEntity.setToken(token);
			tokenEntity.setExpireTime(expireTime);
			tokenEntity.setUpdateTime(now);
			//保存新生成的token
			saveToken(tokenEntity);
		}else {
			TokenHolder.custom().remove(tokenEntity.getToken());
			tokenEntity.setToken(token);
			tokenEntity.setUpdateTime(now);
			tokenEntity.setExpireTime(expireTime);
			//更新token
			updateToken(tokenEntity);
		}
		//把新生成的token保存在缓存中
		Map<String, Object> map = new HashMap<>();
		map.put("token", token);
		map.put("expir", EXPIR);
		TokenHolder.custom().set(tokenEntity, EXPIR);
		return map;
	}

	@Override
	public void updateTokenExpireTimeByAcountName(String acountName, String date) {
		AcountEntity acountEntity = acountDao.queryByAcountName(acountName);
		TokenEntity tokenEntity = tokendao.queryTokenByAcountId(acountEntity.getAcountId());
		if (tokenEntity != null) {
			//使token失效
			TokenHolder.custom().remove(tokenEntity.getToken());
		}
	}

}
