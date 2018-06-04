package com.cn.webApp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.webApp.dao.IServerAppDao;
import com.cn.webApp.model.ServerApp;
import com.cn.webApp.service.ServerAppService;

@Service("ServerAppService")
@Transactional
public class ServerAppServiceImpl implements ServerAppService {

	@Resource
	private IServerAppDao iServerAppDao;

	@Override
	public int deleteByPrimaryKey(String id) {
		return iServerAppDao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ServerApp record) {
		return iServerAppDao.insert(record);
	}

	@Override
	public int insertSelective(ServerApp record) {
		return iServerAppDao.insertSelective(record);
	}

	@Override
	public ServerApp selectByPrimaryKey(String id) {
		return iServerAppDao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(ServerApp record) {
		return iServerAppDao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(ServerApp record) {
		return iServerAppDao.updateByPrimaryKey(record);
	}
	
	@Override
	public List<ServerApp> selectBySelective(ServerApp record){
		return iServerAppDao.selectBySelective(record);
	}
	
	@Override
	public List<ServerApp> queryServerAppPage(Map<String, Object> params) {
		return iServerAppDao.queryServerAppPage(params);
	}

	@Override
	public long queryServerAppCount(ServerApp record) {
		return iServerAppDao.queryServerAppCount(record);
	}

}
