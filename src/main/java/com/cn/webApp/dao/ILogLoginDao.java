package com.cn.webApp.dao;

import java.util.List;

import com.cn.webApp.model.LogLogin;

public interface ILogLoginDao {
    int insert(LogLogin record);

    int insertSelective(LogLogin record);
    
    List<LogLogin> selectByUserId(String id);
}