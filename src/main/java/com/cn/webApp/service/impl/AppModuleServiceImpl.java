package com.cn.webApp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cn.webApp.common.StringUtil;
import com.cn.webApp.dao.IAppModuleDao;
import com.cn.webApp.model.AppModule;
import com.cn.webApp.service.AppModuleService;

@Service("appmoduleservice")
public class AppModuleServiceImpl implements AppModuleService {
	@Resource
	private IAppModuleDao iappmoduledao;

	/**
	 * 根据主键删除AppModule
	 */
	@Override
	public int deleteByPrimaryKey(String moduleid) {
		return iappmoduledao.deleteByPrimaryKey(moduleid);
	}

	/**
	 * 新增AppModule，所有字段不为空
	 */
	@Override
	public int insert(AppModule record) {
		return iappmoduledao.insert(record);
	}

	/**
	 * 新增AppModule，字段不为空的
	 */
	@Override
	public int insertSelective(AppModule record) {
		return iappmoduledao.insertSelective(record);
	}

	/**
	 * 根据主键查询AppModule，
	 */
	@Override
	public AppModule selectByPrimaryKey(String moduleid) {
		return iappmoduledao.selectByPrimaryKey(moduleid);
	}

	/**
	 * 根据主键去更新AppModule字段不为空的
	 */
	@Override
	public int updateByPrimaryKeySelective(AppModule record) {
		return iappmoduledao.updateByPrimaryKeySelective(record);
	}

	/**
	* 
	*/
	@Override
	public int updateByPrimaryKeyWithBLOBs(AppModule record) {
		return iappmoduledao.updateByPrimaryKeyWithBLOBs(record);
	}

	/**
	 * 根据主键更新AppModule，所有字段
	 */
	@Override
	public int updateByPrimaryKey(AppModule record) {
		return iappmoduledao.updateByPrimaryKey(record);
	}

	/**
	 * 获得被授权的模块信息
	 */
	@Override
	public List<AppModule> selectByBusinessAppredit() {
		return iappmoduledao.selectByBusinessAppredit();
	}

	/**
	 * 更新或新增AppModule
	 */
	@Override
	@Transactional(propagation = Propagation.SUPPORTS)
	public void saveOrUpdate(AppModule record) {
		AppModule appmodule = iappmoduledao.selectByPrimaryKey(record.getModuleid());
		if (appmodule != null) {
			iappmoduledao.updateByPrimaryKey(record);
		} else {
			record.setId(StringUtil.getUUID());
			iappmoduledao.insertSelective(record);
		}
	}

	/**
	 * 根据appid获取对应模块的信息以及当前已接入数
	 */
	@Override
	public List<Map<String, Object>> queryByAppid(String appid) {
		return iappmoduledao.queryByAppid(appid);
	}

	/**
	 * 根据base_appmodule查询 base_app_module中所有模块的信息
	 */
	@Override
	public List<AppModule> findModuleByStandard(String appid) {
		return iappmoduledao.findModuleByStandard(appid);
	}

	/**
	 * 根据customerunitcode查询当前租户被授权的模块信息
	 */
	@Override
	public List<AppModule> findBaseModule(String customerunitcode) {
		return iappmoduledao.findBaseModule(customerunitcode);
	}

}
