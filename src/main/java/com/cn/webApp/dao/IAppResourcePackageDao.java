package com.cn.webApp.dao;

import java.util.List;

import com.cn.webApp.model.AppResourcePackage;

public interface IAppResourcePackageDao {
	int deleteByPrimaryKey(String id);

	int insert(AppResourcePackage record);

	int insertSelective(AppResourcePackage record);

	AppResourcePackage selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(AppResourcePackage record);

	int updateByPrimaryKey(AppResourcePackage record);

	List<AppResourcePackage> queryAll();
	
	List<AppResourcePackage> queryByName(String name);
}