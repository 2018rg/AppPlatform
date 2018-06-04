package com.cn.webApp.service.impl;

import com.cn.webApp.common.StringUtil;
import com.cn.webApp.dao.ILegalPersonResourcePackageDao;
import com.cn.webApp.model.LegalPersonResourcePackage;
import com.cn.webApp.service.LegalPersonResourcePackageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("legalpersonresourcepackageservice")
@Transactional
public class LegalPersonResourcePackageServiceImpl implements LegalPersonResourcePackageService {
    
	@Resource
	private ILegalPersonResourcePackageDao dao;
	@Override
	public int deleteByPrimaryKey(String id) {
		return dao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(LegalPersonResourcePackage record) {
		return dao.insert(record);
	}

	@Override
	public int insertSelective(LegalPersonResourcePackage record) {
		return dao.insertSelective(record);
	}

	@Override
	public LegalPersonResourcePackage selectByPrimaryKey(String id) {
		return dao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(LegalPersonResourcePackage record) {
		return dao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LegalPersonResourcePackage record) {
		return dao.updateByPrimaryKey(record);
	}

	@Override
	public List<Map<String, Object>> queryList() {
		return dao.queryList();
	}

	@Override
	public List<LegalPersonResourcePackage> queryByCode(String code) {
		return dao.queryByCode(code);
	}

	@Override
	public int delByCode(String code) {
		return dao.delByCode(code);
	}

	@Override
	public void updateByArray(String code, String[] ids) {
		delByCode(code);
		for(String s:ids){
			LegalPersonResourcePackage record=new LegalPersonResourcePackage();
			record.setId(StringUtil.getUUID());
			record.setCustomerunitcode(code);
			record.setResourcepackageid(s);
			insert(record);
		}
	}

}
