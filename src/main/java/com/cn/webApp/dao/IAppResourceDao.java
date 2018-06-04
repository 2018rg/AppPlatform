package com.cn.webApp.dao;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.AppResource;

public interface IAppResourceDao {
	int deleteByPrimaryKey(String id);

	int insert(AppResource record);

	int insertSelective(AppResource record);

	AppResource selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(AppResource record);

	int updateByPrimaryKey(AppResource record);
    /**
     * 查询全部Resource
     * @return
     */
	List<AppResource> queryAll();
	/**
	 * 分页查询
	 * @param map
	 * @return
	 */
	List<AppResource> queryAppResourcePage(Map<String,Object> map);
	/**
	 * 查询总数
	 * @return
	 */
	Long queryAppResourceCount(AppResource record);
	/**
	 * 条件查询list
	 * @param record
	 * @return
	 */
	List<AppResource> queryBySelective(AppResource record);
}