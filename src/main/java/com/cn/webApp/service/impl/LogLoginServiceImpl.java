package com.cn.webApp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.ILogLoginDao;
import com.cn.webApp.model.LogLogin;
import com.cn.webApp.service.LogLoginService;

@Service("logloginservice")
public class LogLoginServiceImpl implements LogLoginService{
    @Resource
	private ILogLoginDao iloglogindao;
	@Override
	public int insert(LogLogin record) {
		return iloglogindao.insert(record);
	}

	@Override
	public int insertSelective(LogLogin record) {
		return iloglogindao.insertSelective(record);
	}

	@Override
	public List<LogLogin> selectByUserId(String id) {
		return iloglogindao.selectByUserId(id);
	}

}
