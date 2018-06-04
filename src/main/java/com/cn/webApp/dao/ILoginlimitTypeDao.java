package com.cn.webApp.dao;

import java.util.List;

import com.cn.webApp.model.LoginlimitType;

public interface ILoginlimitTypeDao {
    int deleteByPrimaryKey(Integer typeid);

    int insert(LoginlimitType record);

    int insertSelective(LoginlimitType record);

    LoginlimitType selectByPrimaryKey(Integer typeid);

    int updateByPrimaryKeySelective(LoginlimitType record);

    int updateByPrimaryKey(LoginlimitType record);
    
    List<LoginlimitType> findAll();
}