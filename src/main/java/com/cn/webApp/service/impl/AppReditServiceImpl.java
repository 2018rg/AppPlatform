package com.cn.webApp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.IAppReditDao;
import com.cn.webApp.model.AppRedit;
import com.cn.webApp.service.AppReditService;

@Service("appreditservice")
public class AppReditServiceImpl implements AppReditService {

	@Resource
	private IAppReditDao iappreditdao;

	@Override
	public int deleteByPrimaryKey(String appid) {
		return iappreditdao.deleteByPrimaryKey(appid);
	}

	@Override
	public int insert(AppRedit record) {
		return iappreditdao.insert(record);
	}

	@Override
	public int insertSelective(AppRedit record) {
		return iappreditdao.insertSelective(record);
	}

	@Override
	public AppRedit selectByPrimaryKey(String appid) {
		return iappreditdao.selectByPrimaryKey(appid);
	}

	@Override
	public int updateByPrimaryKeySelective(AppRedit record) {
		return iappreditdao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKeyWithBLOBs(AppRedit record) {
		return iappreditdao.updateByPrimaryKeyWithBLOBs(record);
	}

	@Override
	public int updateByPrimaryKey(AppRedit record) {
		return iappreditdao.updateByPrimaryKey(record);
	}

	/**
	 * 获取被授权的应用
	 */
	@Override
	public List<AppRedit> selectByBusinessAppredit() {
		return iappreditdao.selectByBusinessAppredit();
	}

	@Override
	public AppRedit findByAppId(String appId) {
		List<AppRedit> list = iappreditdao.findByAppId(appId);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	@Override
	public void saveOrUpdate(AppRedit record) {
		AppRedit appredit=iappreditdao.selectByPrimaryKey(record.getId());
		if (appredit != null) {
			iappreditdao.updateByPrimaryKey(record);
		}else{
			iappreditdao.insertSelective(record);
		}
		
	}

	@Override
	public List<Map<String, Object>> AppMessage() {
		return iappreditdao.AppMessage();
	}
	//查询所有应用包
	@Override
	public List<AppRedit> findAll_nobase() {
		return iappreditdao.findAll_nobase();
	}

	@Override
	public List<AppRedit> findAll() {
		return iappreditdao.findAll();
	}

	@Override
	public AppRedit getAppreditByCustcode(String customerunitcode) {
		return iappreditdao.getAppreditByCustcode(customerunitcode);
	}

	@Override
	public List<AppRedit> findStandardBusinessAppredit(String appid) {
		return iappreditdao.findStandardBusinessAppredit(appid);
	}

	@Override
	public List<AppRedit> getAppreditByCustcode2(String customerunitcode) {
		return iappreditdao.getAppreditByCustcode2(customerunitcode);
	}

}
