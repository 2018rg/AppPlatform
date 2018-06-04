package com.cn.webApp.dao;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.Legalperson;

public interface ILegalpersonDao {
    int deleteByPrimaryKey(String id);

    int insert(Legalperson record);

    int insertSelective(Legalperson record);

    Legalperson selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Legalperson record);

    int updateByPrimaryKey(Legalperson record);
    
    Legalperson selectByCustomerunitcode(String id);
    
    List<Legalperson> selectByAppId(String appId);
    
    //分页查询
    List<Map<String, Object>> selectBySelectivePage(Map<String,Object> params);
    
    
    //根据条件查询总数
    Long queryLegalpersonCount(Legalperson record);
    
    int deleteByCustomerunitcode(String customerunitcode);
    
    int updateStatus(String customerunitcode);
    
    String selectNextCustCode();
    
    //查询所有的租户信息
    List<Legalperson> selectAll();
}