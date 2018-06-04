package com.cn.webApp.dao;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.LegalPersonResourcePackage;

public interface ILegalPersonResourcePackageDao {
	int deleteByPrimaryKey(String id);

	int insert(LegalPersonResourcePackage record);

	int insertSelective(LegalPersonResourcePackage record);

	LegalPersonResourcePackage selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(LegalPersonResourcePackage record);

	int updateByPrimaryKey(LegalPersonResourcePackage record);

	List<Map<String, Object>> queryList();
	
	List<LegalPersonResourcePackage> queryByCode(String code);
	
	int delByCode(String code);
}