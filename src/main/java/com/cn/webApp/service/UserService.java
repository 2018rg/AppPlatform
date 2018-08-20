package com.cn.webApp.service;
import java.util.List;
import java.util.Map;

import com.cn.webApp.model.User;

public interface UserService {

	public int deleteByPrimaryKey(String id); 

	public int insert(User record);

	public int insertSelective(User record);

	public User selectByPrimaryKey(String id);

	public int updateByPrimaryKeySelective(User record); 

	public int updateByPrimaryKey(User record); 
}
