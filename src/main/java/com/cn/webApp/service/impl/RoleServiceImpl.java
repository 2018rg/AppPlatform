package com.cn.webApp.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.IRoleDao;
import com.cn.webApp.model.Role;
import com.cn.webApp.model.RoleMenu;
import com.cn.webApp.service.RoleMenuService;
import com.cn.webApp.service.RoleService;


@Service("roleservice")
public class RoleServiceImpl implements RoleService {
	@Resource
	private IRoleDao iroledao;

	@Resource
	private RoleMenuService rolemenuservice;

	@Override
	public int deleteByPrimaryKey(String id) {
		return iroledao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Role record) {
		return iroledao.insert(record);
	}

	@Override
	public int insertSelective(Role record) {
		return iroledao.insertSelective(record);
	}

	@Override
	public Role selectByPrimaryKey(String id) {
		return iroledao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Role record) {
		return iroledao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Role record) {
		return iroledao.updateByPrimaryKey(record);
	}

	@Override
	public List<Role> queryRoleByCustomerCode(String customerunitcode) {
		return iroledao.queryRoleByCustomerCode(customerunitcode);
	}

	@Override
	public List<Role> queryPageRoles(Map<String, Object> map) {
		return iroledao.queryPageRoles(map);
	}

	@Override
	public Long queryRolesCounts(Role record) {
		return iroledao.queryRolesCounts(record);
	}

	@Override
	public List<Role> selectBySelective(Role record) {
		return iroledao.selectBySelective(record);
	}

	/**
	 * 保存新增的角色和角色菜单
	 */
	@Override
	public Boolean addRole(Role record, List<RoleMenu> rolemenuLsit) {
		Boolean flag = true;
		try {
			insert(record);
			// 新增角色对应权限菜单
			for (Iterator<RoleMenu> ite = rolemenuLsit.iterator(); ite.hasNext();) {
				RoleMenu rolemenu = ite.next();
				rolemenuservice.insert(rolemenu);
			}
		} catch (Exception e) {
			flag = false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public Boolean editRole(Role role, List<RoleMenu> rolemenuLsit) {
		Boolean flag=true;
		try {
			// 更新角色
			updateByPrimaryKeySelective(role);
			String role_uid = role.getId();
			// 先删除
			rolemenuservice.deleteByRoleId(role_uid);
			// 新增角色对应权限菜单 
			for(Iterator<RoleMenu> ite= rolemenuLsit.iterator();ite.hasNext();){
				RoleMenu rolemenu = ite.next();
				rolemenuservice.insert(rolemenu);
			}
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

}
