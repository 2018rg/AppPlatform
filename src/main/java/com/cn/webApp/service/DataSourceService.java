package com.cn.webApp.service;

import java.util.List;

import com.cn.webApp.model.DataSource;
/**
 * 
 * @author rg
 * 上午10:19:42
 */
public interface DataSourceService {

	int deleteByPrimaryKey(String id);

	int insert(DataSource record);

	int insertSelective(DataSource record);

	DataSource selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(DataSource record);

	int updateByPrimaryKey(DataSource record);
	/**
	 * 根据appid查询DataSource集合
	 * @param record
	 * @return
	 */
	List<DataSource> queryAllResource(String appid);
	/**
	 * 查询DataSource集合
	 * @return
	 */
	List<DataSource> findAll();
	
	void saveOrUpdate(DataSource datasource);
	/**
	 * 根据appid和customerunitcode查询DataSource集合
	 * @param record
	 * @return
	 */
	List<DataSource> queryByAppid(DataSource record);
	/**
	 * 根据customerunitcode更新customerunitcode为null
	 * @param customerunitcode
	 * @return
	 */
	int updateCusCodeEffective(String customerunitcode);

}
