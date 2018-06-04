package com.cn.webApp.dao;

import java.util.List;

import com.cn.webApp.model.LoginlimitIp;
import com.cn.webApp.model.User;

public interface ILoginlimitIpDao {
	
    int deleteByPrimaryKey(String id);

    int insert(LoginlimitIp record);

    int insertSelective(LoginlimitIp record);

    LoginlimitIp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LoginlimitIp record);

    int updateByPrimaryKey(LoginlimitIp record);
    
    List<LoginlimitIp> selectByUser(User user);
    
    int deleteByEmpCode(String empcode);
}