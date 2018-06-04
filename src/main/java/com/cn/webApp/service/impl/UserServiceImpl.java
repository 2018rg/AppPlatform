package com.cn.webApp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.webApp.common.MD5Util;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.dao.IUserDao;
import com.cn.webApp.model.LoginlimitIp;
import com.cn.webApp.model.User;
import com.cn.webApp.service.LoginlimitIpService;
import com.cn.webApp.service.UserService;

import ca.ex.BaseException;

@Service("userservice")
@Transactional
public class UserServiceImpl implements UserService {

	@Resource
	private IUserDao iuserdao;

	@Resource
	private LoginlimitIpService loginlimitipservice;

	@Override
	public int deleteByPrimaryKey(String id) {
		return iuserdao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(User record) {
		return iuserdao.insert(record);
	}

	@Override
	public int insertSelective(User record) {
		return iuserdao.insertSelective(record);
	}

	@Override
	public User selectByPrimaryKey(String id) {
		return iuserdao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(User record) {
		return iuserdao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(User record) {
		return iuserdao.updateByPrimaryKey(record);
	}

	@Override
	public List<User> selectBySelective(User record) {
		return iuserdao.selectBySelective(record);
	}

	@Override
	public List<User> queryUserPage(Map<String, Object> params) {
		return iuserdao.queryUserPage(params);
	}

	@Override
	public long queryUserCount(User user) {
		return iuserdao.queryUserCount(user);
	}

	// 根据 Customerunitcode 查询管理员用户
	@Override
	public User selectByCustomerunitcode(String customerunitcode) {
		return iuserdao.selectByCustomerunitcode(customerunitcode);
	}

	// 保存user和ip限制
	@Override
	public Boolean saveUser(User user, String limitip) {
		Boolean flag = true;

		try {
			insertSelective(user);
			if (user.getLoglimit().intValue() == 1 && limitip != null && !limitip.equals("")) {
				String[] ips = limitip.split(";");
				for (String ip : ips) {
					LoginlimitIp loginlimitip = new LoginlimitIp();
					loginlimitip.setLimitip(ip);
					loginlimitip.setCustomerunitcode(user.getCustomerunitcode());
					loginlimitip.setEmpcode(user.getEmpcode());
					loginlimitip.setId(StringUtil.getUUID());
				    loginlimitipservice.insert(loginlimitip);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public void delUser(String userid) {
		User user = selectByPrimaryKey(userid);
		loginlimitipservice.deleteByEmpCode(user.getEmpcode());
		deleteByPrimaryKey(userid);
	}

	@Override
	public void updateUser(User user, String limitip, String oldEmpcode) {
		// 更新user对象
		updateByPrimaryKeySelective(user);
		// 删除原有的ip限制
		loginlimitipservice.deleteByEmpCode(oldEmpcode);
		// 添加新的ip限制
		if (user.getLoglimit().intValue() == 1 && limitip != null && !limitip.equals("")) {
			String[] ips = limitip.split(";");
			for (String ip : ips) {
				LoginlimitIp loginlimitip = new LoginlimitIp();
				loginlimitip.setLimitip(ip);
				loginlimitip.setCustomerunitcode(user.getCustomerunitcode());
				loginlimitip.setEmpcode(user.getEmpcode());
				loginlimitip.setId(StringUtil.getUUID());
				loginlimitipservice.insert(loginlimitip);
			}
		}

	}

	@Override
	public void changePassword(User user, String oldPwd, String newPwd) {
		if (user.getEmppwd().trim().equals(MD5Util.md5(oldPwd))) {
			user.setEmppwd(MD5Util.md5(newPwd));
			updateByPrimaryKeySelective(user);
		} else {
			throw new BaseException("原始密码不正确");
		}
		;
	}

}
