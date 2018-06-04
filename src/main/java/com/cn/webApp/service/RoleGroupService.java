package com.cn.webApp.service;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.RoleGroup;

public interface RoleGroupService {
	
	int deleteByPrimaryKey(String id);

    int insert(RoleGroup record);

    int insertSelective(RoleGroup record);

    RoleGroup selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(RoleGroup record);

    int updateByPrimaryKeyWithBLOBs(RoleGroup record);

    int updateByPrimaryKey(RoleGroup record);
    
    List<RoleGroup> queryRoleGroupByCustomerCode(String customerunitcode);
    
    List<Map<String,Object>> queryRoleGroups(Map<String,Object> parameters);
    
    Long queryCount(String customerunitcode);
    
    List<RoleGroup> verifyRoleGroupByName(RoleGroup record);
    
    List<RoleGroup> selectByRoleids(String appid);

}
