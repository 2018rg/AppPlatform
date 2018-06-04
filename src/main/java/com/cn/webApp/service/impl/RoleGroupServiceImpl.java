package com.cn.webApp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.IRoleGroupDao;
import com.cn.webApp.model.RoleGroup;
import com.cn.webApp.service.RoleGroupService;
@Service("rolegroupservice")
public class RoleGroupServiceImpl implements RoleGroupService {
	@Resource
	private IRoleGroupDao irolegroupdao;

	@Override
	public int deleteByPrimaryKey(String id) {
		return irolegroupdao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(RoleGroup record) {
		return irolegroupdao.insert(record);
	}

	@Override
	public int insertSelective(RoleGroup record) {
		return irolegroupdao.insertSelective(record);
	}

	@Override
	public RoleGroup selectByPrimaryKey(String id) {
		return irolegroupdao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(RoleGroup record) {
		return irolegroupdao.updateByPrimaryKey(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(RoleGroup record) {
		return irolegroupdao.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(RoleGroup record) {
		return irolegroupdao.updateByPrimaryKey(record);
	}

	@Override
	public List<RoleGroup> queryRoleGroupByCustomerCode(String customerunitcode) {
		
		return irolegroupdao.queryRoleGroupByCustomerCode(customerunitcode);
	}

	@Override
	public List<Map<String, Object>> queryRoleGroups(Map<String, Object> parameters) {
		return irolegroupdao.queryRoleGroups(parameters);
	}

	@Override
	public Long queryCount(String customerunitcode) {
		return irolegroupdao.queryCount(customerunitcode);
	}

	@Override
	public List<RoleGroup> verifyRoleGroupByName(RoleGroup record) {
		return irolegroupdao.verifyRoleGroupByName(record);
	}

	@Override
	public List<RoleGroup> selectByRoleids(String appid) {
		return irolegroupdao.selectByRoleids(appid);
	}

}
