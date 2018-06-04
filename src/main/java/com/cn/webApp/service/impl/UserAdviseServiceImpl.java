package com.cn.webApp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.IUserAdviseDao;
import com.cn.webApp.model.UserAdvise;
import com.cn.webApp.service.UserAdviseService;

@Service("useradviseservice")
public class UserAdviseServiceImpl implements UserAdviseService {
	@Resource
	private IUserAdviseDao iuseradvisedao;

	@Override
	public int deleteByPrimaryKey(String id) {
		return iuseradvisedao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(UserAdvise record) {
		return iuseradvisedao.insert(record);
	}

	@Override
	public int insertSelective(UserAdvise record) {
		return iuseradvisedao.insertSelective(record);
	}

	@Override
	public UserAdvise selectByPrimaryKey(String id) {
		return iuseradvisedao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(UserAdvise record) {
		return iuseradvisedao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(UserAdvise record) {
		return iuseradvisedao.updateByPrimaryKey(record);
	}

	@Override
	public List<UserAdvise> queryUserAdvisePage(Map<String, Object> map) {
		return iuseradvisedao.queryUserAdvisePage(map);
	}

	@Override
	public long queryUserAdviseCount(UserAdvise record) {
		return iuseradvisedao.queryUserAdviseCount(record);
	}

}
