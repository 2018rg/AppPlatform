package com.cn.webApp.service;

import java.util.List;

import com.cn.webApp.model.LogLogin;

public interface LogLoginService {
	
	int insert(LogLogin record);

	int insertSelective(LogLogin record);

	List<LogLogin> selectByUserId(String id);
}
