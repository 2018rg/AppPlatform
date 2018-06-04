package com.cn.webApp.dao;

import java.util.List;

import com.cn.webApp.model.Bank;

public interface IBankDao {
    int insert(Bank record);

    int insertSelective(Bank record);
    
    List<Bank> findAll();
}