package com.cn.webApp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.IBankDao;
import com.cn.webApp.model.Bank;
import com.cn.webApp.service.BankService;

@Service("bankservice")
public class BankServiceImpl implements BankService {
	@Resource
    private IBankDao ibankdao;
	
	@Override
	public int insert(Bank record) {
		return ibankdao.insert(record);
	}

	@Override
	public int insertSelective(Bank record) {
		return ibankdao.insertSelective(record);
	}

	@Override
	public List<Bank> findAll() {
		return ibankdao.findAll();
	}

}
