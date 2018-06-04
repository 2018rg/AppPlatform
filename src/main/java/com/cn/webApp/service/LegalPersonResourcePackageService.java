package com.cn.webApp.service;

import java.util.List;
import java.util.Map;

import com.cn.webApp.model.LegalPersonResourcePackage;

public interface LegalPersonResourcePackageService {
	int deleteByPrimaryKey(String id);

	int insert(LegalPersonResourcePackage record);

	int insertSelective(LegalPersonResourcePackage record);

	LegalPersonResourcePackage selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(LegalPersonResourcePackage record);

	int updateByPrimaryKey(LegalPersonResourcePackage record);
    /**
     * 查询租户对应授权资源包信息
     * @return
     */
	List<Map<String, Object>> queryList();
	/**
	 * 根据租户号查询当前租户的租户-资源包对应关系
	 * @param code
	 * @return
	 */
	List<LegalPersonResourcePackage> queryByCode(String code);
	/**
	 * 根据租户号删除当前租户的租户-资源包对应关系
	 * @param code
	 * @return
	 */
	int delByCode(String code);
	/**
	 * 更新当前租户的 租户-资源包对应关系表
	 * 先删除，后添加
	 * @param code
	 * @param ids
	 */
	void updateByArray(String code,String[] ids);
}
