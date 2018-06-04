package com.cn.webApp.service;

import java.util.List;

import com.cn.webApp.model.Bank;

public interface BankService {

	int insert(Bank record);

	int insertSelective(Bank record);
    /**
     * 所有Bank集合
     * @return
     */
	List<Bank> findAll();

}
