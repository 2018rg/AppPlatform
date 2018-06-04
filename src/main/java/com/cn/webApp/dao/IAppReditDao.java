package com.cn.webApp.dao;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.AppRedit;

public interface IAppReditDao {
    int deleteByPrimaryKey(String appid);

    int insert(AppRedit record);

    int insertSelective(AppRedit record);

    AppRedit selectByPrimaryKey(String appid);

    int updateByPrimaryKeySelective(AppRedit record);

    int updateByPrimaryKeyWithBLOBs(AppRedit record);

    int updateByPrimaryKey(AppRedit record);
    //获取授权的应用
    List<AppRedit> selectByBusinessAppredit();
    
    List<AppRedit> findByAppId(String appId);
    
    List<Map<String,Object>> AppMessage();
    
    List<AppRedit> findAll_nobase();
    
    List<AppRedit> findAll();
    
    AppRedit getAppreditByCustcode(String customerunitcode);
    
    List<AppRedit> findStandardBusinessAppredit(String appid);
    
    List<AppRedit> getAppreditByCustcode2(String customerunitcode);
}