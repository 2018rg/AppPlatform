package com.cn.webApp.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.dao.IMenuDao;
import com.cn.webApp.model.Menu;
import com.cn.webApp.model.RoleGroup;
import com.cn.webApp.model.User;
import com.cn.webApp.service.MenuService;
import com.cn.webApp.service.RoleGroupService;
import com.cn.webApp.service.UserService;

@Service("menuservice")
@Transactional
@SuppressWarnings("all")
public class MenuServiceImpl implements MenuService {

	@Resource
	private IMenuDao imenudao;
	@Resource
	private UserService userserviceimpl;
	@Resource
	private RoleGroupService rolegroupservice;

	@Override
	public int deleteByPrimaryKey(String menuid) {

		return imenudao.deleteByPrimaryKey(menuid);
	}

	@Override
	public int insert(Menu record) {
		return imenudao.insert(record);
	}

	@Override
	public int insertSelective(Menu record) {
		return imenudao.insertSelective(record);
	}

	@Override
	public Menu selectByPrimaryKey(String menuid) {
		return imenudao.selectByPrimaryKey(menuid);
	}

	@Override
	public int updateByPrimaryKeySelective(Menu record) {
		return imenudao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Menu record) {
		return imenudao.updateByPrimaryKey(record);
	}

	@Override
	public List<Menu> selectAllMenu() {
		return imenudao.selectAllMenu();
	}

	@Override
	public List<Menu> selectByCunstomerUnitCode(String CunstomerUnitCode) {
		return imenudao.selectByCunstomerUnitCode(CunstomerUnitCode);
	}

	@Override
	public List<Menu> selectByRoles(String []roleIds) {
		return imenudao.selectByRoles(roleIds);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Menu> findAllUserMenu(String empCode, String code) {
		List<Menu> menus = Collections.EMPTY_LIST;
		User record = new User();
		record.setEmpcode(empCode);
		record.setCustomerunitcode(code);
		List<User> list = userserviceimpl.selectBySelective(record);
		if (list.size() == 1) {
			if (list.get(0).getIsmaster() == 1) {// 运营管理员，显示所有菜单
				menus = selectAllMenu();
			} else if (list.get(0).getIsadmin() == 1) {// 租户管理员，显示租户被授权的所有菜单
				menus = selectByCunstomerUnitCode(list.get(0).getCustomerunitcode());
			} else {
				// 普通用户，根据角色授权加载
				String roleGroupId = list.get(0).getRoleid();
				RoleGroup group = rolegroupservice.selectByPrimaryKey(roleGroupId);
				if(group.getGroupstate().trim().equals("1")){
				String roleIds = group.getRoleids();
				String[] roles = roleIds.split(",");
				menus = selectByRoles(roles);
				}else{
					menus=null;
				}
			}
		}
		return menus;
	}

	@Override
	public List<Menu> selectByModuleId(String CunstomerUnitCode) {
		return imenudao.selectByModuleId(CunstomerUnitCode);
	}

	@Override
	public List<Menu> userRecommendMenu(String userid) {
		return imenudao.userRecommendMenu(userid);
	}

	@Override
	public List<Menu> custcodeRecommendMenu(String CunstomerUnitCode) {
		return imenudao.custcodeRecommendMenu(CunstomerUnitCode);
	}

	/**
	 * app所有菜单的树状列表
	 */
	@Override
	public List<Map<String, Object>> queryAppMenuByAppId(String appid) {
		return imenudao.queryAppMenuByAppId(appid);
	}

	/**
	 * 更新app的菜单tree
	 */
	@Override
	public void updateMenuTree(List<Menu> list) {
		for (Menu menu : list) {
			imenudao.updateByPrimaryKeySelective(menu);
		}
	}

	/**
	 * 保存上传的菜单
	 */
	@Override
	public void uploadMenu(JSONArray menuArray) {
		List<Menu> result = new ArrayList<Menu>();
		int len = menuArray.size();
		JSONObject jsonObj;
		for (int i = 0; i < len; i++) {
			jsonObj = menuArray.getJSONObject(i);
			Menu menu = jsonObj.parseObject(jsonObj.toJSONString(), Menu.class);
			Menu oldMenu = imenudao.selectByPrimaryKey(menu.getMenuid());
			if (oldMenu != null) {
				imenudao.updateByPrimaryKeySelective(menu);
			} else {
				menu.setId(StringUtil.getUUID());
				imenudao.insert(menu);
			}

		}

	}
}
