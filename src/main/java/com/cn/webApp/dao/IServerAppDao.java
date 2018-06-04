package com.cn.webApp.dao;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.ServerApp;

public interface IServerAppDao {
    int deleteByPrimaryKey(String id);

    int insert(ServerApp record);

    int insertSelective(ServerApp record);

    ServerApp selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(ServerApp record);

    int updateByPrimaryKey(ServerApp record);
    
    List<ServerApp> selectBySelective(ServerApp record);
    
    /**
     * 分页查询
     */
    List<ServerApp> queryServerAppPage(Map<String,Object> params);
    
    /**
	 * 查询符合条件的总数
	 */
	long queryServerAppCount(ServerApp record);
}