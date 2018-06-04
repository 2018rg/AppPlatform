package com.cn.webApp.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.webApp.common.StringUtil;
import com.cn.webApp.dao.ILegalpersonDao;
import com.cn.webApp.model.DataSource;
import com.cn.webApp.model.LegalPersonAuthority;
import com.cn.webApp.model.LegalPersonModule;
import com.cn.webApp.model.Legalperson;
import com.cn.webApp.model.Role;
import com.cn.webApp.model.RoleGroup;
import com.cn.webApp.model.User;
import com.cn.webApp.service.DataSourceService;
import com.cn.webApp.service.LegalPersonAuthorityService;
import com.cn.webApp.service.LegalPersonModuleService;
import com.cn.webApp.service.LegalpersonService;
import com.cn.webApp.service.RoleGroupService;
import com.cn.webApp.service.RoleService;
import com.cn.webApp.service.UserService;

@Service("legalpersonservice")
@Transactional
public class LegalpersonServiceImpl implements LegalpersonService {

	@Resource
	private ILegalpersonDao ilegalpersondao;

	@Resource
	private UserService userservice;

	@Resource
	private DataSourceService datasourceservice;

	@Resource
	private LegalPersonModuleService legalpersonmoduleservice;

	@Resource
	private LegalPersonAuthorityService legalpersonauthorityservice;
	
	@Resource
	private RoleService roleservice;
	
	@Resource
	private RoleGroupService rolegroupservice;

	@Override
	public int deleteByPrimaryKey(String id) {
		return ilegalpersondao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Legalperson record) {
		return ilegalpersondao.insert(record);
	}

	@Override
	public int insertSelective(Legalperson record) {
		return ilegalpersondao.insertSelective(record);
	}

	@Override
	public Legalperson selectByPrimaryKey(String id) {
		return ilegalpersondao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Legalperson record) {
		return ilegalpersondao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Legalperson record) {
		return ilegalpersondao.updateByPrimaryKey(record);
	}

	@Override
	public Legalperson selectByCustomerunitcode(String id) {
		return ilegalpersondao.selectByCustomerunitcode(id);
	}

	@Override
	public List<Legalperson> selectByAppId(String appId) {
		return ilegalpersondao.selectByAppId(appId);
	}

	@Override
	public List<Map<String, Object>> selectBySelectivePage(Map<String, Object> params) {
		return ilegalpersondao.selectBySelectivePage(params);
	}

	@Override
	public Long queryLegalpersonCount(Legalperson record) {
		return ilegalpersondao.queryLegalpersonCount(record);
	}

	@Override
	public int deleteByCustomerunitcode(String customerunitcode) {
		return ilegalpersondao.deleteByCustomerunitcode(customerunitcode);
	}

	@Override
	public Boolean updateForm(User user, Legalperson legalperson, String limitip, String oldEmpcode) {
		Boolean flag = true;
		try {
			userservice.updateUser(user, limitip, oldEmpcode);
			updateByPrimaryKeySelective(legalperson);
		} catch (Exception e) {
			e.printStackTrace();
			flag = false;
		}
		return flag;
	}

	@Override
	public Boolean savePermit(List<DataSource> baseDatasourceLsit, List<LegalPersonModule> legalPersonModuleLsit,
			Date end_time) {
		Boolean flag=true;
		try {
			String cusCode = legalPersonModuleLsit.get(0).getCustomerunitcode();
			// // 更新数据源所属
			// 先全部都置为 0
			datasourceservice.updateCusCodeEffective(cusCode);
			// 再把当前选中的置为 1
			for (Iterator<DataSource> iterator = baseDatasourceLsit.iterator(); iterator.hasNext();) {
				DataSource basedatasource = iterator.next();
				DataSource parameters = new DataSource();
				parameters.setId(basedatasource.getId());
				parameters.setEffective(new BigDecimal(1));
				parameters.setCustomerunitcode(basedatasource.getCustomerunitcode());
				datasourceservice.updateByPrimaryKeySelective(parameters);
			}
			// //更新模块和法人对应关系
			// //先删除
			legalpersonmoduleservice.deleteByCusCode(cusCode);
			// //再新增
			//String modules = "";
			//String apps = "";
			for (Iterator<LegalPersonModule> iterator = legalPersonModuleLsit.iterator(); iterator.hasNext();) {
				LegalPersonModule legalpersonmodule = iterator.next();
				legalpersonmoduleservice.insertSelective(legalpersonmodule);
				//modules += legalpersonmodule.getAppmodelid() + ",";
				//apps += legalpersonmodule.getAppid() + ",";
			}
			// 保存授权时间
			LegalPersonAuthority lpa = legalpersonauthorityservice.getPermitDate(cusCode);
			LegalPersonAuthority legalpersonauthority = new LegalPersonAuthority();
			legalpersonauthority.setCustomerunitcode(cusCode);
			legalpersonauthority.setEndTime(end_time);
			if (lpa != null) {
				legalpersonauthority.setId(lpa.getId());
				legalpersonauthorityservice.updateByPrimaryKeySelective(legalpersonauthority);
			} else {
				legalpersonauthority.setId(StringUtil.getUUID());
				legalpersonauthorityservice.insertSelective(legalpersonauthority);
			}
			// 更新法人为已授权
			updateStatus(cusCode);
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public int updateStatus(String customerunitcode) {
		return ilegalpersondao.updateStatus(customerunitcode);
	}
    /**
     * 生成custcode
     */
	@Override
	public String selectNextCustCode() {
		return ilegalpersondao.selectNextCustCode();
	}

	@Override
	public void saveLegalPerson(Legalperson legalperson, User user, String limitip) {
		//保存租户
		insertSelective(legalperson);
		//初始化租户对应的用户的角色和角色组
		Role role = new Role();
		role.setCustomerunitcode(legalperson.getCustomerunitcode());
		role.setRolename("系统管理员");
		role.setRolestate(1);
		role.setId(StringUtil.getUUID());
		roleservice.insert(role);
		String role_uid = role.getId();
		
		RoleGroup rolegroup = new RoleGroup();
		rolegroup.setCustomerunitcode(legalperson.getCustomerunitcode());
		rolegroup.setGroupname("系统管理员组");
		rolegroup.setRoleids(role_uid);
		rolegroup.setGroupstate("1");
		rolegroup.setId(StringUtil.getUUID());
		rolegroupservice.insert(rolegroup);
		user.setRoleid(rolegroup.getId());
		//角色类型：0 单个角色 1 角色组'
		user.setRoletype("1");
		userservice.saveUser(user,limitip);
	}

	@Override
	public List<Legalperson> selectAll() {
		return ilegalpersondao.selectAll();
	}
}
