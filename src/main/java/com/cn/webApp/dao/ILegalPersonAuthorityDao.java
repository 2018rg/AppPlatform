package com.cn.webApp.dao;

import com.cn.webApp.model.LegalPersonAuthority;

public interface ILegalPersonAuthorityDao {
    int deleteByPrimaryKey(String id);

    int insert(LegalPersonAuthority record);

    int insertSelective(LegalPersonAuthority record);

    LegalPersonAuthority selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LegalPersonAuthority record);

    int updateByPrimaryKey(LegalPersonAuthority record);
    
    LegalPersonAuthority getPermitDate(String customerunitcode);
}