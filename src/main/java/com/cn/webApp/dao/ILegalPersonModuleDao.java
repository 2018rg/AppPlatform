package com.cn.webApp.dao;

import java.util.List;

import com.cn.webApp.model.LegalPersonModule;

public interface ILegalPersonModuleDao {
    int deleteByPrimaryKey(String id);

    int insert(LegalPersonModule record);

    int insertSelective(LegalPersonModule record);

    LegalPersonModule selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(LegalPersonModule record);

    int updateByPrimaryKey(LegalPersonModule record);
    
    List<LegalPersonModule> getCheckModule(String customerunitcode);
    
    List<LegalPersonModule> queryByMoudleId(String appmodelid);
    
    int deleteByCusCode(String customerunitcode);
}