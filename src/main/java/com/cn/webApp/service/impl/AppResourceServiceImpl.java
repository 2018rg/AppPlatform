package com.cn.webApp.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.IAppResourceDao;
import com.cn.webApp.model.AppResource;
import com.cn.webApp.service.AppResourceService;

@Service("appresourceservice")
public class AppResourceServiceImpl implements AppResourceService {

	@javax.annotation.Resource
	private IAppResourceDao iappresourcedao;

	@Override
	public int deleteByPrimaryKey(String id) {
		return iappresourcedao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(AppResource record) {
		return iappresourcedao.insert(record);
	}

	@Override
	public int insertSelective(AppResource record) {
		return iappresourcedao.insertSelective(record);
	}

	@Override
	public AppResource selectByPrimaryKey(String id) {
		return iappresourcedao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(AppResource record) {
		return iappresourcedao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(AppResource record) {
		return iappresourcedao.updateByPrimaryKey(record);
	}

	@Override
	public List<AppResource> queryAll() {
		return iappresourcedao.queryAll();
	}

	@Override
	public List<AppResource> queryAppResourcePage(Map<String, Object> map) {
		return iappresourcedao.queryAppResourcePage(map);
	}

	@Override
	public Long queryAppResourceCount(AppResource record) {
		return iappresourcedao.queryAppResourceCount(record);
	}

	@Override
	public List<AppResource> queryBySelective(AppResource record) {
		return iappresourcedao.queryBySelective(record);
	}

}
