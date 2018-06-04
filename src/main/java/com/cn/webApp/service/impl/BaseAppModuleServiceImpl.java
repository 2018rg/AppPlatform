package com.cn.webApp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.IBaseAppModuleDao;
import com.cn.webApp.model.BaseAppModule;
import com.cn.webApp.service.BaseAppModuleService;
@Service("baseappmoduleservice")
public class BaseAppModuleServiceImpl implements BaseAppModuleService {
    @Resource
    private IBaseAppModuleDao ibaseappmoduledao;
    
	@Override
	public int deleteByPrimaryKey(String id) {
		return ibaseappmoduledao.deleteByPrimaryKey(id);
	}

	@Override
	public int deleteByAppId(String appId) {
		return ibaseappmoduledao.deleteByAppId(appId);
	}

	@Override
	public int insert(BaseAppModule record) {
		return ibaseappmoduledao.insert(record);
	}

	@Override
	public int insertSelective(BaseAppModule record) {
		return ibaseappmoduledao.insertSelective(record);
	}

	@Override
	public BaseAppModule selectByPrimaryKey(String id) {
		return ibaseappmoduledao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(BaseAppModule record) {
		return ibaseappmoduledao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(BaseAppModule record) {
		return ibaseappmoduledao.updateByPrimaryKey(record);
	}

}
