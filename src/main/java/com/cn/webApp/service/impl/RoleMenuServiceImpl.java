package com.cn.webApp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.IRoleMenuDao;
import com.cn.webApp.model.RoleMenu;
import com.cn.webApp.service.RoleMenuService;
@Service("rolemenuservice")
public class RoleMenuServiceImpl implements RoleMenuService {
    @Resource
	private IRoleMenuDao irolemenudao;
	@Override
	public int deleteByPrimaryKey(String id) {
		return irolemenudao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(RoleMenu record) {
		return irolemenudao.insert(record);
	}

	@Override
	public int insertSelective(RoleMenu record) {
		return irolemenudao.insertSelective(record);
	}

	@Override
	public RoleMenu selectByPrimaryKey(String id) {
		return irolemenudao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RoleMenu record) {
		return irolemenudao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(RoleMenu record) {
		return irolemenudao.updateByPrimaryKey(record);
	}

	@Override
	public List<RoleMenu> queryMenuByRoleId(String appid) {
		return irolemenudao.queryMenuByRoleId(appid);
	}

	@Override
	public int deleteByRoleId(String appid) {
		return irolemenudao.deleteByRoleId(appid);
	}

}
