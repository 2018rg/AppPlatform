package com.cn.webApp.service;

import java.util.List;

import com.cn.webApp.model.RoleMenu;

public interface RoleMenuService {

	int deleteByPrimaryKey(String id);

	int insert(RoleMenu record);

	int insertSelective(RoleMenu record);

	RoleMenu selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(RoleMenu record);

	int updateByPrimaryKey(RoleMenu record);

	List<RoleMenu> queryMenuByRoleId(String appid);

	int deleteByRoleId(String appid);

}
