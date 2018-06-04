package com.cn.webApp.controller;

import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com.cn.webApp.common.Constant;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.controller.base.Msg;
import com.cn.webApp.model.AppRedit;
import com.cn.webApp.model.DataSource;
import com.cn.webApp.model.Legalperson;
import com.cn.webApp.service.AppModuleService;
import com.cn.webApp.service.AppReditService;
import com.cn.webApp.service.DataSourceService;
import com.cn.webApp.service.LegalpersonService;

/**
 * 资源管理
 * @author rg
 * 上午11:52:36
 */
@Controller
@RequestMapping(value = "resourceManage")
public class DataSourceController {

	@Resource
	private LegalpersonService legalpersonservice;

	@Resource
	private AppReditService appreditservice;

	@Resource
	private AppModuleService appmoduleservice;

	@Resource
	private DataSourceService datasourceservice;

	/**
	 * 
	 * @param modelMap
	 * @return ModelAndView @throws
	 */
	@RequestMapping(value = "resourceManageUI")
	public ModelAndView indexUI(ModelMap modelMap) {
		// 系统平台
		String appid = Constant.BASE_APPID;
		AppRedit rapp = appreditservice.findByAppId(appid);
		// 系统平台授权客户
		List<Legalperson> cuslist = legalpersonservice.selectByAppId(appid);
		// 标准包列表
		List<Map<String, Object>> applist = appreditservice.AppMessage();
		modelMap.put("apppage", applist);
		modelMap.put("rootapp", rapp);
		modelMap.put("cuslist", cuslist);
		return new ModelAndView("foundation/resourceManage/pagelet/v1.0/resourceManageUI", modelMap);
	}

	/**
	 * 客户详情界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "cusdetailUI")
	public String cusdetailUI(ModelMap modelMap, HttpServletRequest request) {
		String appid = request.getParameter("appid");
		List<Legalperson> cuslist = legalpersonservice.selectByAppId(appid);
		modelMap.put("cuslist", cuslist);
		return "foundation/resourceManage/pagelet/v1.0/cusdetailUI";
	}

	/**
	 * 模块详情界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */

	@RequestMapping(value = "moduledetailUI")
	public ModelAndView moduledetailUI(ModelMap modelMap, HttpServletRequest request) {
		String appid = request.getParameter("appid");
		List<Map<String, Object>> modulelist = appmoduleservice.queryByAppid(appid);
		modelMap.put("modulelist", modulelist);
		return new ModelAndView("foundation/resourceManage/pagelet/v1.0/moduledetailUI");
	}

	/*
	*//**
		 * 资源展示界面
		 * 
		 * @param request
		 * @param response
		 * @return
		 */
	@RequestMapping(value = "resourceList")
	public String resourceList(ModelMap modelMap, HttpServletRequest request) {
		String appid = request.getParameter("appid");
		List<DataSource> datasourcelist = datasourceservice.queryAllResource(appid);
		modelMap.put("datasourcelist", datasourcelist);
		modelMap.put("appid", appid);
		return "foundation/resourceManage/pagelet/v1.0/resourceList";
	}

	/**
	 * 资源更新界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "editUI")
	public String resourceEditUI(ModelMap modelMap, HttpServletRequest request) {
		String appid = request.getParameter("id");
		DataSource datasource = datasourceservice.selectByPrimaryKey(appid);
		modelMap.put("datasource", datasource);
		return "foundation/resourceManage/pagelet/v1.0/resourceEdit";
	}

	/**
	 * 资源新增界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "addUI")
	public String resourceAddUI(ModelMap modelMap, HttpServletRequest request) {
		modelMap.put("appid", request.getParameter("appid"));
		return "foundation/resourceManage/pagelet/v1.0/resourceAdd";
	}

	/**
	 * 保存 @param modelMap @return Msg @throws
	 */
	@RequestMapping(value = "save", method = RequestMethod.POST)
	public Msg save(ModelMap modelMap, HttpServletRequest request, DataSource basedatasource) {
		Msg msg = new Msg();
		List<DataSource> allResourcelist = datasourceservice.findAll();// all
		String id = request.getParameter("datasourceid");
		basedatasource.setEffective(new BigDecimal(0)); // 当前是否生效 1 生效 0 未生效
		if (!StringUtils.isBlank(id)) { // 修改
			DataSource baseDatasource2 = datasourceservice.selectByPrimaryKey(id);
			if (!StringUtils.isBlank(baseDatasource2.getCustomerunitcode())) {
				msg.setSuccess(false);
				msg.setMsg("记录已授权，不能修改！");
				return msg;
			}
			basedatasource.setId(id);
			basedatasource.setEffective(baseDatasource2.getEffective());
			// 判断重复
			for (DataSource all : allResourcelist) {
				if (all.getName().trim().equals(basedatasource.getName().trim()) && !all.getId().equals(id)) {
					msg.setSuccess(false);
					msg.setMsg("本地数据库名称已存在，请重新命名！");
					return msg;
				}
				if ((basedatasource.getDsModel().indexOf("instance") != -1)
						&& all.getDsHost().trim().equals(basedatasource.getDsHost().trim())
						&& all.getDsName().trim().equals(basedatasource.getDsName().trim())
						&& all.getDsPort().trim().equals(basedatasource.getDsPort().trim())
						&& all.getDsType().equals(basedatasource.getDsType()) && !all.getId().equals(id)) {
					msg.setSuccess(false);
					msg.setMsg("单实例下资源已存在！");
					return msg;
				}
				if (all.getDsHost().trim().equals(basedatasource.getDsHost().trim())
						&& all.getDsName().trim().equals(basedatasource.getDsName().trim())
						&& all.getDsPort().trim().equals(basedatasource.getDsPort().trim())
						&& all.getDsType().equals(basedatasource.getDsType())
						&& all.getDsUser().trim().equals(basedatasource.getDsUser().trim())
						&& !all.getId().equals(id)) {
					msg.setSuccess(false);
					msg.setMsg("资源已存在！");
					return msg;

				}
			}
		} else {// 新增
			basedatasource.setId(StringUtil.getUUID());
			// 判断重复
			for (DataSource all : allResourcelist) {
				if (all.getName().trim().equals(basedatasource.getName().trim())) {
					msg.setSuccess(false);
					msg.setMsg("本地数据库名称已存在，请重新命名！");
					return msg;
				}
				if ((basedatasource.getDsModel().indexOf("instance") != -1)
						&& all.getDsHost().trim().equals(basedatasource.getDsHost().trim())
						&& all.getDsName().trim().equals(basedatasource.getDsName().trim())
						&& all.getDsPort().trim().equals(basedatasource.getDsPort().trim())
						&& all.getDsType().equals(basedatasource.getDsType())) {
					msg.setSuccess(false);
					msg.setMsg("单实例下资源已存在！");
					return msg;
				}
				if (all.getDsHost().trim().equals(basedatasource.getDsHost().trim())
						&& all.getDsName().trim().equals(basedatasource.getDsName().trim())
						&& all.getDsPort().trim().equals(basedatasource.getDsPort().trim())
						&& all.getDsType().equals(basedatasource.getDsType())
						&& all.getDsUser().trim().equals(basedatasource.getDsUser().trim())) {
					msg.setSuccess(false);
					msg.setMsg("资源已存在！");
					return msg;
				}
			}
		}
		// 测试连接
		if (!getConnection(basedatasource)) {
			msg.setSuccess(false);
			msg.setMsg("尝试连接数据源失败！请检查配置信息");
			return msg;
		}
		Date date = StringUtil.getNowDate(null);
		basedatasource.setLastUpdateTime(date);
		if (basedatasource.getAddTime() == null) {
			basedatasource.setAddTime(date);
		}
		datasourceservice.saveOrUpdate(basedatasource);
		msg.setSuccess(true);
		msg.setMsg("保存成功！");

		return msg;
	}

	/**
	 * 删除 @param modelMap @return Msg @throws
	 */
	@RequestMapping(value = "del", method = RequestMethod.POST)
	public Msg resourceDel(ModelMap modelMap, HttpServletRequest request) {
		Msg msg = new Msg();
		String id = request.getParameter("id");
		DataSource datasource = datasourceservice.selectByPrimaryKey(id);
		if (!StringUtils.isBlank(datasource.getCustomerunitcode())) {
			msg.setSuccess(false);
			msg.setMsg("记录已授权，不能删除！");
		}
		datasourceservice.deleteByPrimaryKey(id);
		msg.setSuccess(true);
		msg.setMsg("删除成功");
		return msg;

	}

	private boolean getConnection(DataSource basedatasource) {
		boolean falg = false;
		java.sql.Connection conn = null;
		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@" + basedatasource.getDsHost() + ":" + basedatasource.getDsPort() + ":"
				+ basedatasource.getDsName();
		if ("mysql".equals(basedatasource.getDsType())) {
			driver = "com.mysql.jdbc.Driver";
			url = "jdbc:mysql://" + basedatasource.getDsHost() + ":" + basedatasource.getDsPort() + "/"
					+ basedatasource.getDsName();
		}
		try {
			Class.forName(driver);
			conn = DriverManager.getConnection(url, basedatasource.getDsUser(), basedatasource.getDsPwd());
			if (conn != null)
				falg = true;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return falg;
	}
}
