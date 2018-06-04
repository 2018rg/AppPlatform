package com.cn.webApp.dao;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.AppModule;

public interface IAppModuleDao {
    int deleteByPrimaryKey(String moduleid);

    int insert(AppModule record);

    int insertSelective(AppModule record);

    AppModule selectByPrimaryKey(String moduleid);

    int updateByPrimaryKeySelective(AppModule record);

    int updateByPrimaryKeyWithBLOBs(AppModule record);

    int updateByPrimaryKey(AppModule record);
    //获得被授权的模块信息
    List<AppModule> selectByBusinessAppredit();
    
    List<Map<String ,Object>>queryByAppid(String appid);
    
    List<AppModule> findModuleByStandard(String appid);
    
    List<AppModule> findBaseModule(String customerunitcode);
}