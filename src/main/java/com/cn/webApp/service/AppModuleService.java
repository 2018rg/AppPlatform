package com.cn.webApp.service;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.AppModule;

public interface AppModuleService {
	/**
	 * 根据主键删除
	 * 
	 * @param moduleid
	 * @return
	 */
	int deleteByPrimaryKey(String moduleid);

	/**
	 * 新增AppModule所有字段
	 * @param record
	 * @return
	 */
	int insert(AppModule record);
    /**
     * 新增 AppModule 字段不为空的
     * @param record
     * @return
     */
	int insertSelective(AppModule record);
    /**
     * 根据主键查询
     * @param moduleid
     * @return
     */
	AppModule selectByPrimaryKey(String moduleid);
	
	/**
	 * 更新AppModule 字段不为空的
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(AppModule record);

	int updateByPrimaryKeyWithBLOBs(AppModule record);
    /**
     * 更新AppModule 所有字段，无论是否为空
     * @param record
     * @return
     */
	int updateByPrimaryKey(AppModule record);

	/**
	 * 获得被授权的模块信息
	 * 
	 * @return
	 */
	List<AppModule> selectByBusinessAppredit();
   /**
    * 更新或新增AppModule
    * @param record
    */
	void saveOrUpdate(AppModule record);
    /**
     * 根据appid获取对应模块的信息以及当前已接入数
     * @param appid
     * @return
     */
	List<Map<String, Object>> queryByAppid(String appid);
    /**
     * 根据base_appmodule查询 base_app_module中所有模块的信息
     * @param appid
     * @return
     */
	List<AppModule> findModuleByStandard(String appid);
    /**
     * 根据customerunitcode查询当前租户被授权的模块信息
     * @param customerunitcode
     * @return
     */
	List<AppModule> findBaseModule(String customerunitcode);

}
