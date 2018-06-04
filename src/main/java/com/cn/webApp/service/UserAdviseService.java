package com.cn.webApp.service;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.UserAdvise;

public interface UserAdviseService {

	int deleteByPrimaryKey(String id);

	int insert(UserAdvise record);

	int insertSelective(UserAdvise record);

	UserAdvise selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(UserAdvise record);

	int updateByPrimaryKey(UserAdvise record);
    /**
     * 待条件的分页查询
     * @param map
     * @return
     */
	List<UserAdvise> queryUserAdvisePage(Map<String, Object> map);
    /**
     * 根据条件查询总数
     * @param record
     * @return
     */
	long queryUserAdviseCount(UserAdvise record);

}
