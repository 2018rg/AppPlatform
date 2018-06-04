package com.cn.webApp.service;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.AppRedit;

public interface AppReditService {
	/**
	 * 删除
	 * @param appid
	 * @return
	 */
	int deleteByPrimaryKey(String appid);
    /**
     * 新增 所有字段
     * @param record
     * @return
     */
	int insert(AppRedit record);
    /**
     * 新增 字段不为空的
     * @param record
     * @return
     */
	int insertSelective(AppRedit record);
    /**
     * 根据主键查询
     * @param appid
     * @return
     */
	AppRedit selectByPrimaryKey(String appid);
    /**
     * 更新 字段不为空的
     * @param record
     * @return
     */
	int updateByPrimaryKeySelective(AppRedit record);

	int updateByPrimaryKeyWithBLOBs(AppRedit record);
    /**
     * 更新 所有字段，无论是否为空
     * @param record
     * @return
     */
	int updateByPrimaryKey(AppRedit record);

	/**
	 * 获取授权的应用
	 * @return
	 */
	List<AppRedit> selectByBusinessAppredit();
	
	/**
	 * 根据appid查询
	 * @param appId
	 * @return
	 */
	AppRedit findByAppId(String appId);
	/**
	 * 新增或更新
	 * @param record
	 */
	void saveOrUpdate(AppRedit record);
	/**
	 * 查询注册的app相关信息和最大接入数、当前已接入数、模块总数
	 * @return
	 */
	List<Map<String,Object>> AppMessage();
	/**
	 *  查询不是基础平台的标准包app
	 * @return
	 */
	List<AppRedit> findAll_nobase();
	/**
	 * 所有标准包app
	 * @return
	 */
	List<AppRedit> findAll();
	/**
	 * 根据租户号获取租户被授权的应用
	 * @param customerunitcode
	 * @return
	 */
	AppRedit getAppreditByCustcode(String customerunitcode);
	/**
	 * 根据授权验证查询所有应用包
	 * @param appid
	 * @return
	 */
	List<AppRedit> findStandardBusinessAppredit(String appid);
	/**
	 * 根据租户号获取租户被授权的应用
	 * @param customerunitcode
	 * @return
	 */
    List<AppRedit> getAppreditByCustcode2(String customerunitcode);

}
