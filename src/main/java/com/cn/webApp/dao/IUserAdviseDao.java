package com.cn.webApp.dao;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.UserAdvise;

public interface IUserAdviseDao {
	int deleteByPrimaryKey(String id);

	int insert(UserAdvise record);

	int insertSelective(UserAdvise record);

	UserAdvise selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(UserAdvise record);

	int updateByPrimaryKey(UserAdvise record);

	List<UserAdvise> queryUserAdvisePage(Map<String, Object> map);

	long queryUserAdviseCount(UserAdvise record);
}