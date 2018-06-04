package com.cn.webApp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.ILoginlimitTypeDao;
import com.cn.webApp.model.LoginlimitType;
import com.cn.webApp.service.LoginlimitTypeService;
@Service("loginlimittypeservice")
public class LoginlimitTypeServiceImpl implements LoginlimitTypeService {
	
   @Resource
   private ILoginlimitTypeDao iloginlimittypedao;
	@Override
	public int deleteByPrimaryKey(Integer typeid) {
		return iloginlimittypedao.deleteByPrimaryKey(typeid);
	}

	@Override
	public int insert(LoginlimitType record) {
		return iloginlimittypedao.insert(record);
	}

	@Override
	public int insertSelective(LoginlimitType record) {
		return iloginlimittypedao.insertSelective(record);
	}

	@Override
	public LoginlimitType selectByPrimaryKey(Integer typeid) {
		return iloginlimittypedao.selectByPrimaryKey(typeid);
	}

	@Override
	public int updateByPrimaryKeySelective(LoginlimitType record) {
		return iloginlimittypedao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LoginlimitType record) {
		return iloginlimittypedao.updateByPrimaryKey(record);
	}

	@Override
	public List<LoginlimitType> findAll() {
		return iloginlimittypedao.findAll();
	}

}
