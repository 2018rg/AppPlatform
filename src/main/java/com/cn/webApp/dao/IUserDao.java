package com.cn.webApp.dao;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.User;

public interface IUserDao {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    List<User> selectBySelective(User record);
    /**
     * 分页查询
     */
    List<User> queryUserPage(Map<String,Object> params);
    
    /**
	 * 查询符合条件的总数
	 */
	 long queryUserCount(User user);
	 
	 //根据  Customerunitcode 查询管理员用户
	 User selectByCustomerunitcode(String customerunitcode);
}