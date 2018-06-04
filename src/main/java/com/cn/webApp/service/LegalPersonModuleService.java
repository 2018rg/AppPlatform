package com.cn.webApp.service;

import java.util.List;

import com.cn.webApp.model.LegalPersonModule;

public interface LegalPersonModuleService {
	int deleteByPrimaryKey(String id);

	int insert(LegalPersonModule record);

	int insertSelective(LegalPersonModule record);

	LegalPersonModule selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(LegalPersonModule record);

	int updateByPrimaryKey(LegalPersonModule record);
    /**
     * 根据customerunitcode查找LegalPersonModule
     * @param customerunitcode
     * @return
     */
	List<LegalPersonModule> getCheckModule(String customerunitcode);
	/**
	 * 根据appmodelid查找LegalPersonModule
	 * @param appmodelid
	 * @return
	 */
	List<LegalPersonModule> queryByMoudleId(String appmodelid);
	/**
	 * 根据customerunitcode 删除
	 * @param customerunitcode
	 * @return
	 */
	int deleteByCusCode(String customerunitcode);
}
