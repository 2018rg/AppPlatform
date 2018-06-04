package com.cn.webApp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.ILegalPersonModuleDao;
import com.cn.webApp.model.LegalPersonModule;
import com.cn.webApp.service.LegalPersonModuleService;
@Service("legalpersonmoduleservice")
public class LegalPersonModuleServiceImpl implements LegalPersonModuleService {
   
	@Resource
	private ILegalPersonModuleDao ilegalpersonmoduledao;
	
	@Override
	public int deleteByPrimaryKey(String id) {
		return ilegalpersonmoduledao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(LegalPersonModule record) {
		return ilegalpersonmoduledao.insert(record);
	}

	@Override
	public int insertSelective(LegalPersonModule record) {
		return ilegalpersonmoduledao.insertSelective(record);
	}

	@Override
	public LegalPersonModule selectByPrimaryKey(String id) {
		return ilegalpersonmoduledao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(LegalPersonModule record) {
		return ilegalpersonmoduledao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LegalPersonModule record) {
		return ilegalpersonmoduledao.updateByPrimaryKey(record);
	}

	@Override
	public List<LegalPersonModule> getCheckModule(String customerunitcode) {
		return ilegalpersonmoduledao.getCheckModule(customerunitcode);
	}

	@Override
	public List<LegalPersonModule> queryByMoudleId(String appmodelid) {
		return ilegalpersonmoduledao.queryByMoudleId(appmodelid);
	}

	@Override
	public int deleteByCusCode(String customerunitcode) {
		return ilegalpersonmoduledao.deleteByCusCode(customerunitcode);
	}

}
