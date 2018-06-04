package com.cn.webApp.service;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.cn.webApp.model.Menu;

public interface MenuService {

	int deleteByPrimaryKey(String menuid);

	int insert(Menu record);

	int insertSelective(Menu record);

	Menu selectByPrimaryKey(String menuid);

	int updateByPrimaryKeySelective(Menu record);

	int updateByPrimaryKey(Menu record);

	List<Menu> selectAllMenu();

	List<Menu> selectByCunstomerUnitCode(String CunstomerUnitCode);

	List<Menu> selectByRoles(String[] roleIds);
	
	List<Menu> findAllUserMenu(String empCode,String code);
	
	List<Menu> selectByModuleId(String CunstomerUnitCode);
	//用户推荐菜单
    List<Menu> userRecommendMenu(String userid);
    //系统/租户推荐菜单
    List<Menu> custcodeRecommendMenu(String CunstomerUnitCode);
    
    List<Map<String,Object>> queryAppMenuByAppId(String appid);
    
    void updateMenuTree(List<Menu> list);
    
    /**
	 * 上传菜单
	 * @param moduleArray 模块信息
	 * @param menuArray 菜单信息
	 * @return
	 */
	void uploadMenu(JSONArray menuArray);

}
