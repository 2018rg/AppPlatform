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
	
	public List<User> selectBySelective(User record);
	
	public List<User> queryUserPage(Map<String,Object> params);
	
	long queryUserCount(User user);
	
	User selectByCustomerunitcode(String customerunitcode);
	
	Boolean saveUser(User user,String limitip);
	
	void delUser(String userid);
	
	void updateUser(User user,String limitip,String oldEmpcode);
	
	void changePassword(User user, String oldPwd, String newPwd);

}
