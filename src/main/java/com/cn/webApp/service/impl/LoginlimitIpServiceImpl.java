package com.cn.webApp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.ILoginlimitIpDao;
import com.cn.webApp.model.LoginlimitIp;
import com.cn.webApp.model.User;
import com.cn.webApp.service.LoginlimitIpService;

@Service("loginlimitservice")
public class LoginlimitIpServiceImpl implements LoginlimitIpService {
    
	@Resource
	private ILoginlimitIpDao iloginlimitdao;
	@Override
	public int deleteByPrimaryKey(String id) {
		return iloginlimitdao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(LoginlimitIp record) {
		return iloginlimitdao.insert(record);
	}

	@Override
	public int insertSelective(LoginlimitIp record) {
		return iloginlimitdao.insertSelective(record);
	}

	@Override
	public LoginlimitIp selectByPrimaryKey(String id) {
		return iloginlimitdao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(LoginlimitIp record) {
		return iloginlimitdao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LoginlimitIp record) {
		return iloginlimitdao.updateByPrimaryKey(record);
	}

	@Override
	public List<LoginlimitIp> selectByUser(User user) {
		return iloginlimitdao.selectByUser(user);
	}

	@Override
	public int deleteByEmpCode(String empcode) {
		return iloginlimitdao.deleteByEmpCode(empcode);
	}

}
