package com.cn.webApp.service;

import com.cn.webApp.model.LegalPersonAuthority;

public interface LegalPersonAuthorityService {

	int deleteByPrimaryKey(String id);

	int insert(LegalPersonAuthority record);

	int insertSelective(LegalPersonAuthority record);

	LegalPersonAuthority selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(LegalPersonAuthority record);

	int updateByPrimaryKey(LegalPersonAuthority record);
    /**
     * 根据customerunitcode查询法人授权
     * @param customerunitcode
     * @return
     */
	LegalPersonAuthority getPermitDate(String customerunitcode);

}
