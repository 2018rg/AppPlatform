package com.cn.webApp.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.webApp.common.MD5Util;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.common.page.Page;
import com.cn.webApp.controller.base.BaseController;
import com.cn.webApp.controller.base.Msg;
import com.cn.webApp.model.AppModule;
import com.cn.webApp.model.AppRedit;
import com.cn.webApp.model.Bank;
import com.cn.webApp.model.DataSource;
import com.cn.webApp.model.LegalPersonAuthority;
import com.cn.webApp.model.LegalPersonModule;
import com.cn.webApp.model.Legalperson;
import com.cn.webApp.model.LoginlimitIp;
import com.cn.webApp.model.User;
import com.cn.webApp.service.AppModuleService;
import com.cn.webApp.service.AppReditService;
import com.cn.webApp.service.BankService;
import com.cn.webApp.service.DataSourceService;
import com.cn.webApp.service.LegalPersonAuthorityService;
import com.cn.webApp.service.LegalPersonModuleService;
import com.cn.webApp.service.LegalpersonService;
import com.cn.webApp.service.LoginlimitIpService;
import com.cn.webApp.service.LoginlimitTypeService;
import com.cn.webApp.service.UserService;


/**
 * @Description:客户管理授权
 * @author rg 上午11:51:31
 */
@Controller
@RequestMapping(value = "/legelPerson")
public class LegelPersonController extends BaseController {

	@Resource
	private LegalpersonService legalpersonservice;

	@Resource
	private UserService userservice;

	@Resource
	private BankService bankservice;

	@Resource
	private LoginlimitIpService loginlimitipservice;

	@Resource
	private LoginlimitTypeService loginlimittypeservice;

	@Resource
	private AppReditService appreditservice;

	@Resource
	private LegalPersonAuthorityService legalpersonauthorityservice;

	@Resource
	private DataSourceService datasourceservice;

	@Resource
	private LegalPersonModuleService legalpersonmoduleservice;

	@Resource
	private AppModuleService appmoduleservice;

	/**
	 * 管理与授权 首頁
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "manageAndPermitUI")
	public String manageAndPermitUI(ModelMap modelMap) {

		return "foundation/legelPerson/pagelet/v1.0/manageAndPermitUI";

	}

	/**
	 * 查詢 @param modelMap @return ModelAndView @throws
	 */
	@RequestMapping(value = "queryLegalPerson")
	public ModelAndView queryLegalPerson(ModelMap modelMap, HttpServletRequest request, Legalperson legalpersonparams,
			String page, String rows) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 添加查询条件
		if (legalpersonparams.getCustomername() != null && !legalpersonparams.getCustomername().trim().equals("")) {
			params.put("customername", legalpersonparams.getCustomername());
		}
		if (legalpersonparams.getCustomerunitcode() != null
				&& !legalpersonparams.getCustomerunitcode().trim().equals("")) {
			params.put("customerunitcode", legalpersonparams.getCustomerunitcode());
		}

		if (legalpersonparams.getCustomername().trim().equals("")) {
			legalpersonparams.setCustomername(null);
		}
		if (legalpersonparams.getCustomerunitcode().trim().equals("")) {
			legalpersonparams.setCustomerunitcode(null);
		}

		// 设置全局的查询条件（分页查询用）
		params.put("isdelete", 0);
		// 查询总数用
		legalpersonparams.setIsdelete(0);
		// 获取总条数
		long totalCount = legalpersonservice.queryLegalpersonCount(legalpersonparams);
		// 当前页
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		// 每页显示条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		// 设置分页对象
		Page pageObject = executePage(request, totalCount, intPage, number);
		pageObject.setJsMethod("queryLegalPerson");
		// 如排序
		if (pageObject.isSort()) {
			params.put("orderName", pageObject.getSortName());
			params.put("descAsc", pageObject.getSortState());
		} else {
			// 没有进行排序,默认排序方式
			params.put("orderName", "customerunitcode");
			params.put("descAsc", "asc");
		}
		// 每页的开始记录 第一页为1 第二页为number +1
		int start = (intPage - 1) * number;

		params.put("startIndex", start);
		params.put("endIndex", number);
		// 查询结果集合
		List<Map<String, Object>> legalpersons = legalpersonservice.selectBySelectivePage(params);
		// total键 存放总记录数，必须的
		modelMap.put("total", totalCount);
		modelMap.put("legalpersons", legalpersons);
		return new ModelAndView("foundation/legelPerson/pagelet/v1.0/manageAndPermitUI_Grid", modelMap);
	}

	/**
	 * 授权 @param modelMap @return ModelAndView @throws
	 */
	@RequestMapping(value = "permitUI")
	public ModelAndView permitUI(ModelMap modelMap, HttpServletRequest request) {
		String code = request.getParameter("code");
		// 查询所有应用包
		List<AppRedit> applist = appreditservice.findAll_nobase();
		if ("08600000000".equals(code)) { // 特殊处理 admin 以外过滤 基础应用包 050100
			applist = appreditservice.findAll();
		}
		// 查询当前租户的 授权包
		AppRedit appredit = appreditservice.getAppreditByCustcode(code);
		if (null != appredit) {
			modelMap.put("cus_appid", appredit.getAppid());
		}
		modelMap.put("customerunitcode", code);
		modelMap.put("appPackage", applist);
		return new ModelAndView("foundation/legelPerson/pagelet/v1.0/permitUI", modelMap);
	}


	/**
	 * 授权提交 @param modelMap @return msg @throws
	 */
	@RequestMapping(value = "permitCommit")
	public Msg permitCommit(ModelMap modelMap, HttpServletRequest request) {
		Msg msg = new Msg();
		String customerunitcode = request.getParameter("customerunitcode");
		if ("08600000000".equals(customerunitcode)) { // 特殊处理 admin 初始化数据授权基础应用包
														// 050100 不支持再授权

		}
		List<LegalPersonModule> ylist = legalpersonmoduleservice.getCheckModule(customerunitcode);
		String limitdt = request.getParameter("limitdt");
		Date end_time = new Date();
		try {
			end_time = StringUtil.stringReverseDate(limitdt, "yyyy-MM-dd");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		int applength = Integer.parseInt(request.getParameter("applength"));
		List<DataSource> baseDatasourceLsit = new ArrayList<DataSource>();
		List<LegalPersonModule> legalPersonModuleLsit = new ArrayList<LegalPersonModule>();
		if (applength > 0) {
			for (int i = 0; i < applength; i++) {
				String mappid = request.getParameter("apparray[" + i + "][appid]");
				String mdataid = request.getParameter("apparray[" + i + "][dataid]");
				// 判断包的接入数
				AppRedit appredit = appreditservice.findByAppId(mappid); // getAppreditByCustcode(customerunitcode);
				List<Legalperson> list = new ArrayList<Legalperson>();
				list = legalpersonservice.selectByAppId(mappid);
				if (ylist != null && ylist.size() > 0) {
					String yappid = (String) ylist.get(0).getAppid();
					if (yappid.equals(request.getParameter("moudlearray[0][appid]"))) { // 还是原来的appid没变
						if (list != null && appredit.getApplimitnum()  == list.size()) {
							msg.setMsg("当前应用包的接入数已满！");
							msg.setSuccess(false);
							return msg;
						}
					} else {
						if (list != null && appredit.getApplimitnum() == list.size()) {
							msg.setMsg("当前应用包的接入数已满！");
							msg.setSuccess(false);
							return msg;
						}
					}
				} else {
					if (list != null && appredit.getApplimitnum() == list.size()) {
						msg.setMsg("当前应用包的接入数已满！");
						msg.setSuccess(false);
						return msg;
					}
				}

				DataSource obj = new DataSource();
				obj.setAppid(mappid);
				obj.setId(mdataid);
				obj.setCustomerunitcode(customerunitcode);
				baseDatasourceLsit.add(obj);
			}
		}
		int moudlelength = Integer.parseInt(request.getParameter("moudlelength"));
		if (moudlelength > 0) {
			for (int i = 0; i < moudlelength; i++) {
				String m_appid = request.getParameter("moudlearray[" + i + "][appid]");
				String m_moudleid = request.getParameter("moudlearray[" + i + "][moudleid]");
				// 判断包 -- 模块的接入数
				AppModule imodule = appmoduleservice.selectByPrimaryKey(m_moudleid);
				List<LegalPersonModule> ilist = legalpersonmoduleservice.queryByMoudleId(m_moudleid);
				if (ylist != null && ylist.size() > 0) {
					if (isContainModule(ylist, m_moudleid)) {
						if (ilist != null && imodule.getLimitnum() == ilist.size()) {
							msg.setMsg("当前[" + imodule.getModulename() + "]模块的接入数已满！");
							msg.setSuccess(false);
							return msg;
						}
					} else {
						if (ilist != null && imodule.getLimitnum() == ilist.size()) {
							msg.setMsg("当前[" + imodule.getModulename() + "]模块的接入数已满！");
							msg.setSuccess(false);
							return msg;
						}
					}
				} else {
					if (ilist != null && imodule.getLimitnum() == ilist.size()) {
						msg.setMsg("当前[" + imodule.getModulename() + "]模块的接入数已满！");
						msg.setSuccess(false);
						return msg;
					}
				}
				LegalPersonModule obj = new LegalPersonModule();
				obj.setId(StringUtil.getUUID());
				obj.setAppid(m_appid);
				obj.setAppmodelid(m_moudleid);
				obj.setSortid(i);
				obj.setCustomerunitcode(customerunitcode);
				legalPersonModuleLsit.add(obj);
			}
		}
		// 保存
		if (legalpersonservice.savePermit(baseDatasourceLsit, legalPersonModuleLsit, end_time)) {
			msg.setSuccess(true);
			msg.setMsg("授权提交成功！");
		}
		return msg;
	}

	private boolean isContainModule(List<LegalPersonModule> ylist, String m_moudleid) {
		for (LegalPersonModule lp : ylist) {
			if (m_moudleid.equals(lp.getAppmodelid())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 加载授权时间 @param modelMap @return ModelAndView @throws
	 */
	@RequestMapping(value = "loadPermitDate")
	public Msg loadPermitDate(ModelMap modelMap, HttpServletRequest request) {
		Msg msg = new Msg();
		String code = request.getParameter("code");
		LegalPersonAuthority lpa = legalpersonauthorityservice.getPermitDate(code);
		if (null != lpa) {
			String data = StringUtil.dateReverseString(lpa.getEndTime(), "yyyy-MM-dd");
			msg.setData(data);
		} else {
			String data = StringUtil.dateReverseString(new Date(), "yyyy-MM-dd");
			msg.setData(data);
		}
		msg.setMsg("加载授权时间成功！");
		return msg;
	}

	/**
	 * 加载数据源 @param modelMap @return ModelAndView @throws
	 */

	@RequestMapping(value = "loadDatasource")
	public Msg loadDatasource(ModelMap modelMap, HttpServletRequest request) {
		Msg msg = new Msg();
		DataSource parameters = new DataSource();
		parameters.setAppid(request.getParameter("appid"));
		parameters.setCustomerunitcode(request.getParameter("code"));
		List<DataSource> list = datasourceservice.queryByAppid(parameters);
		msg.setData(list);
		msg.setMsg("加载数据源成功！");
		return msg;
	}

	/**
	 * 更新界面
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(value = "editUI")
	public String editUI(ModelMap modelMap, HttpServletRequest request) {
		String code = request.getParameter("customerunitcode");
		Legalperson legalperson = legalpersonservice.selectByCustomerunitcode(code);
		User user = userservice.selectByCustomerunitcode(code);
		List<Bank> banks = bankservice.findAll();
		String ips = "";
		if (user.getLoglimit().intValue() == 1) {// IP限制
			List<LoginlimitIp> list = loginlimitipservice.selectByUser(user);
			for (LoginlimitIp l : list) {
				ips += l.getLimitip() + ";";
			}
		}
		modelMap.put("limitip", ips);
		modelMap.put("eduser", user);
		modelMap.put("legalperson", legalperson);
		modelMap.put("banks", banks);
		modelMap.put("loginlimitlist", loginlimittypeservice.findAll());
		return "foundation/legelPerson/pagelet/v1.0/manageAndPermitEditUI";
	}

	/**
	 * 详情界面
	 * 
	 * @param request
	 * @param
	 * @return
	 */
	@RequestMapping(value = "detailUI")
	public String detailUI(ModelMap modelMap, HttpServletRequest request) {
		String code = request.getParameter("customerunitcode");
		Legalperson legalperson = legalpersonservice.selectByCustomerunitcode(code);
		User user = userservice.selectByCustomerunitcode(code);
		List<Bank> banks = bankservice.findAll();
		String ips = "";
		if (user.getLoglimit().intValue() == 1) {// IP限制
			List<LoginlimitIp> list = loginlimitipservice.selectByUser(user);
			for (LoginlimitIp l : list) {
				ips += l.getLimitip() + ";";
			}
		}
		modelMap.put("limitip", ips);
		modelMap.put("eduser", user);
		modelMap.put("legalperson", legalperson);
		modelMap.put("banks", banks);
		modelMap.put("loginlimitlist", loginlimittypeservice.findAll());
		return "foundation/legelPerson/pagelet/v1.0/detailUI";
	}

	/**
	 * 修改 @param modelMap @return Msg @throws
	 */
	@RequestMapping(value = "edit")
	@ResponseBody
	public Msg manageAndPermitEdit(HttpServletRequest request, User user, Legalperson legalperson) {
		Msg msg = new Msg();
		// 获取前台页面legalperson字段值
		String telephone = request.getParameter("legalper_telephone");
		String email = request.getParameter("legalper_email");
		String legalpersonid = request.getParameter("legalpersonid");
		Date date = StringUtil.getNowDate(null);
		legalperson.setOpdt(date);
		legalperson.setId(legalpersonid);
		legalperson.setEmail(email);
		legalperson.setTelephone(telephone);
		// 获取前台页面user字段值
		String id = request.getParameter("userid");
		user.setId(id);
		user.setTelephone(request.getParameter("user_telephone"));
		user.setEmail(request.getParameter("user_email"));
		user.setOpdt(new Date());
		if ("08600000000".equals(legalperson.getCustomerunitcode())) { // 特殊处理
			user.setEmpcode("admin");
			user.setEmpstate(new Long(1));
		}
		if (user.getEmppwd().trim().equals("")) {
			user.setEmppwd(null);
		} else {
			user.setEmppwd(MD5Util.md5(user.getEmppwd()));
		}
		// 获取limitip
		String limitip = request.getParameter("limitip");
		// 获取old user
		User olduser = userservice.selectByPrimaryKey(id);
		// 更新legalperson、user、limitip
		User parameter = new User();
		parameter.setEmpcode(user.getEmpcode().trim());
		parameter.setCustomerunitcode(user.getCustomerunitcode());
		List<User> list = userservice.selectBySelective(parameter);
		if (list.size() > 1) {
			msg.setSuccess(false);
			msg.setMsg("此用户名已经存在！");
		} else {
			if (legalpersonservice.updateForm(user, legalperson, limitip, olduser.getEmpcode())) {
				msg.setSuccess(true);
				msg.setMsg("修改成功！");
				return msg;
			} else {
				msg.setSuccess(false);
				msg.setMsg("修改失败！");
			}
		}
		return msg;
	}

	/**
	 * 删除 @param modelMap
	 * 
	 * @return Msg @throws
	 */
	@RequestMapping(value = "del", method = RequestMethod.POST)
	@ResponseBody
	public Msg manageAndPermitDel(ModelMap modelMap, HttpServletRequest request) {
		Msg msg = new Msg();
		String customerunitcode = request.getParameter("customerunitcode");
		int i = legalpersonservice.deleteByCustomerunitcode(customerunitcode);
		//查询当前租户对应的系统管理员并删除
		User user=userservice.selectByCustomerunitcode(customerunitcode);
		int j=userservice.deleteByPrimaryKey(user.getId());
		if (i == 1&&j==1) {
			msg.setSuccess(true);
			msg.setMsg("删除成功！");
		} else {
			msg.setSuccess(false);
			msg.setMsg("删除异常！");
		}
		return msg;
	}

	/**
	 * 添加界面
	 *
	 * @return
	 */
	@RequestMapping(value = "addUI")
	public String addUI(ModelMap modelMap) {
		List<Bank> banks = bankservice.findAll();
		modelMap.put("banks", banks);
		modelMap.put("loginlimitlist", loginlimittypeservice.findAll());
		return "foundation/legelPerson/pagelet/v1.0/manageAndPermitAddUI";
	}

	/**
	 * 新增
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	public Msg manageAndPermitAdd(ModelMap modelMap, HttpServletRequest request, Legalperson legalperson, User user) {
		/*
		 * getJsonObject().put("telephone",""+getJsonObject().get(
		 * "legalper_telephone"));
		 * getJsonObject().put("email",""+getJsonObject().get("legalper_email"))
		 * ; Legalperson legalperson = JSONTools.JSONToBean(getJsonObject(),
		 * LegalPerson.class);
		 */
		Msg msg = new Msg();
		legalperson.setTelephone(request.getParameter("legalper_telephone"));
		legalperson.setEmail(request.getParameter("legalper_email"));

		String seq = legalpersonservice.selectNextCustCode();
		String customerunitcode = null;
		if (seq == null || seq.trim().equals("")) {
			customerunitcode = "08600000000";
		} else {
			customerunitcode = "0"+seq;
		}
		legalperson.setCustomerunitcode(customerunitcode);
		Date date = StringUtil.getNowDate(null);
		legalperson.setId(StringUtil.getUUID());
		legalperson.setOpdt(date);
		legalperson.setOpendt(date);
		legalperson.setIsbalance(0);
		legalperson.setIsdelete(0);

		/*
		 * getJsonObject().put("telephone",""+getJsonObject().get(
		 * "user_telephone"));
		 * getJsonObject().put("email",""+getJsonObject().get("user_email"));
		 * User user = JSONTools.JSONToBean(getJsonObject(), User.class);
		 */
		user.setTelephone(request.getParameter("user_telephone"));
		user.setEmail(request.getParameter("user_email"));
		user.setCustomerunitcode(customerunitcode);
		user.setIsdelete(new Long(0));
		user.setOpdt(new Date());
		user.setIsadmin(1);
		user.setIsmaster(0);
		user.setEmpstate(new Long(1));
		user.setId(StringUtil.getUUID());
		user.setEmppwd(MD5Util.md5(user.getEmppwd()));
		// LoginLimitIP loginLimitIP = JSONTools.JSONToBean(getJsonObject(),
		// LoginLimitIP.class);
		String limitip = request.getParameter("limitip");
		legalpersonservice.saveLegalPerson(legalperson, user, limitip);
		msg.setSuccess(true);
		msg.setMsg("新增成功！");
		return msg;
	}
}