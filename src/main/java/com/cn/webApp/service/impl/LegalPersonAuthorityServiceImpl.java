package com.cn.webApp.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cn.webApp.dao.ILegalPersonAuthorityDao;
import com.cn.webApp.model.LegalPersonAuthority;
import com.cn.webApp.service.LegalPersonAuthorityService;

@Service("legalpersonauthorityservice")
public class LegalPersonAuthorityServiceImpl implements LegalPersonAuthorityService {
    @Resource
	private ILegalPersonAuthorityDao ilegalpersonauthoritydao;
	@Override
	public int deleteByPrimaryKey(String id) {
		return ilegalpersonauthoritydao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(LegalPersonAuthority record) {
		return ilegalpersonauthoritydao.insert(record);
	}

	@Override
	public int insertSelective(LegalPersonAuthority record) {
		return ilegalpersonauthoritydao.insertSelective(record);
	}

	@Override
	public LegalPersonAuthority selectByPrimaryKey(String id) {
		return ilegalpersonauthoritydao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(LegalPersonAuthority record) {
		return ilegalpersonauthoritydao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LegalPersonAuthority record) {
		return ilegalpersonauthoritydao.updateByPrimaryKey(record);
	}

	@Override
	public LegalPersonAuthority getPermitDate(String customerunitcode) {
		return ilegalpersonauthoritydao.getPermitDate(customerunitcode);
	}

}
