package com.cn.webApp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.IAppSoureDao;
import com.cn.webApp.model.AppSoure;
import com.cn.webApp.service.AppSoureService;
@Service("appsoureservice")
public class AppSoureServiceImpl implements AppSoureService{
	
	@Resource
    private IAppSoureDao iappsouredao;
	@Override
	public int deleteByPrimaryKey(String appcode) {
		return iappsouredao.deleteByPrimaryKey(appcode);
	}

	@Override
	public int insert(AppSoure record) {
		return iappsouredao.insert(record);
	}

	@Override
	public int insertSelective(AppSoure record) {
		return iappsouredao.insertSelective(record);
	}

	@Override
	public AppSoure selectByPrimaryKey(String appcode) {
		return iappsouredao.selectByPrimaryKey(appcode);
	}

	@Override
	public int updateByPrimaryKeySelective(AppSoure record) {
		return iappsouredao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(AppSoure record) {
		return iappsouredao.updateByPrimaryKey(record);
	}

	@Override
	public List<AppSoure> findAll() {
		return iappsouredao.findAll();
	}

}
