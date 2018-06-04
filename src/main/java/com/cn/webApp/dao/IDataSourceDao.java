package com.cn.webApp.dao;

import java.util.List;

import com.cn.webApp.model.DataSource;

public interface IDataSourceDao {
    int deleteByPrimaryKey(String id);

    int insert(DataSource record);

    int insertSelective(DataSource record);

    DataSource selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(DataSource record);

    int updateByPrimaryKey(DataSource record);
    
    List<DataSource> queryAllResource(String appid);
    
    List<DataSource> findAll();
    
    List<DataSource> queryByAppid(DataSource record);
    
    int updateCusCodeEffective(String customerunitcode);
}