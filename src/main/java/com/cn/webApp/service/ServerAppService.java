package com.cn.webApp.service;
import java.util.List;
import java.util.Map;

import com.cn.webApp.model.ServerApp;

public interface ServerAppService {

	public int deleteByPrimaryKey(String id); 

	public int insert(ServerApp record);

	public int insertSelective(ServerApp record);

	public ServerApp selectByPrimaryKey(String id);

	public int updateByPrimaryKeySelective(ServerApp record); 

	public int updateByPrimaryKey(ServerApp record); 
	
	public List<ServerApp> selectBySelective(ServerApp record);
	
	public List<ServerApp> queryServerAppPage(Map<String,Object> params);
	
	long queryServerAppCount(ServerApp user);
	
}
