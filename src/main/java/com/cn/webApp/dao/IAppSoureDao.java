package com.cn.webApp.dao;

import java.util.List;

import com.cn.webApp.model.AppSoure;

public interface IAppSoureDao {
    int deleteByPrimaryKey(String appcode);

    int insert(AppSoure record);

    int insertSelective(AppSoure record);

    AppSoure selectByPrimaryKey(String appcode);

    int updateByPrimaryKeySelective(AppSoure record);

    int updateByPrimaryKey(AppSoure record);
    //查找全部
    List<AppSoure> findAll();
}