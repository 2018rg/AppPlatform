package com.cn.webApp.dao;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.Menu;

public interface IMenuDao {
    int deleteByPrimaryKey(String menuid);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(String menuid);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);
    
    List<Menu> selectAllMenu();
    
    List<Menu> selectByCunstomerUnitCode(String CunstomerUnitCode);
    
    List<Menu> selectByRoles(String[]roleIds);
    
    List<Menu> selectByModuleId(String CunstomerUnitCode);
    
    List<Menu> userRecommendMenu(String userid);
    
    List<Menu> custcodeRecommendMenu(String CunstomerUnitCode);
    
    List<Map<String,Object>> queryAppMenuByAppId(String appid);
}