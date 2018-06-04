package com.cn.webApp.service;

import com.cn.webApp.model.BaseAppModule;

public interface BaseAppModuleService {

	int deleteByPrimaryKey(String id);

	int deleteByAppId(String appId);

	int insert(BaseAppModule record);

	int insertSelective(BaseAppModule record);

	BaseAppModule selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(BaseAppModule record);

	int updateByPrimaryKey(BaseAppModule record);

}
