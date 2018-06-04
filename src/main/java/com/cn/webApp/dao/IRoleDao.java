package com.cn.webApp.dao;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.Role;

public interface IRoleDao {
    int deleteByPrimaryKey(String id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    List<Role> queryRoleByCustomerCode(String customerunitcode);
    
    List<Role>  queryPageRoles(Map<String,Object> map);
    
    Long queryRolesCounts(Role record);
    
	List<Role> selectBySelective(Role record);
}