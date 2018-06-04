package com.cn.webApp.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cn.webApp.model.DataSource;
import com.cn.webApp.model.LegalPersonModule;
import com.cn.webApp.model.Legalperson;
import com.cn.webApp.model.User;

public interface LegalpersonService {
	int deleteByPrimaryKey(String id);

	int insert(Legalperson record);

	int insertSelective(Legalperson record);

	Legalperson selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Legalperson record);

	int updateByPrimaryKey(Legalperson record);
    /**
     * 根据CUSTMERUNITCODE查询
     * @param id
     * @return
     */
	Legalperson selectByCustomerunitcode(String id);

	List<Legalperson> selectByAppId(String appId);

	/**
	 *  根据条件分页查询
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> selectBySelectivePage(Map<String, Object> params);

	/**
	 *  根据条件查询总数
	 * @param record
	 * @return
	 */
	Long queryLegalpersonCount(Legalperson record);

	int deleteByCustomerunitcode(String customerunitcode);

	Boolean updateForm(User user, Legalperson legalperson, String limitip, String oldEmpcode);

	/**
	 * 保存授权
	 * 
	 * @param baseDatasourceLsit
	 * @param legalPersonModuleLsit
	 *            void @throws
	 */
	Boolean savePermit(List<DataSource> baseDatasourceLsit, List<LegalPersonModule> legalPersonModuleLsit,
			Date end_time);

	int updateStatus(String customerunitcode);

	String selectNextCustCode();

	void saveLegalPerson(Legalperson legalperson, User user, String limitip);

	/**
	 *  查询所有租户信息
	 * @return
	 */
	List<Legalperson> selectAll();
}
