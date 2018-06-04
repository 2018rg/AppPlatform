package com.cn.webApp.service;

import java.util.List;

import com.cn.webApp.model.AppSoure;

public interface AppSoureService {

	int deleteByPrimaryKey(String appcode);

	int insert(AppSoure record);

	int insertSelective(AppSoure record);

	AppSoure selectByPrimaryKey(String appcode);

	int updateByPrimaryKeySelective(AppSoure record);

	int updateByPrimaryKey(AppSoure record);

	/**
	 * 所有AppSoure集合
	 * @return
	 */
	List<AppSoure> findAll();

}
