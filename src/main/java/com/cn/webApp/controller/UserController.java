/**
 * @Title:
 * @Package cn..function.platform.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaochongfei
 * @date 2014-3-28 下午03:03:03
 * @version V1.0
 */
package com.cn.webApp.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cn.webApp.common.HttpUtils;
import com.cn.webApp.common.MD5Util;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.common.page.Page;
import com.cn.webApp.controller.base.BaseController;
import com.cn.webApp.controller.base.Msg;
import com.cn.webApp.model.LoginlimitIp;
import com.cn.webApp.model.LoginlimitType;
import com.cn.webApp.model.RoleGroup;
import com.cn.webApp.model.User;
import com.cn.webApp.service.LoginlimitIpService;
import com.cn.webApp.service.LoginlimitTypeService;
import com.cn.webApp.service.RoleGroupService;
import com.cn.webApp.service.UserService;

/**
 * 
 * @author rg 下午1:53:02
 */

@Controller
@Scope("prototype")
@RequestMapping(value = "/user")
@SuppressWarnings("all")
public class UserController extends BaseController {
	
	Logger log=Logger.getLogger(this.getClass());

	@Resource
	private RoleGroupService rolegroupservice;

	@Resource
	private UserService userservice;

	@Resource
	private LoginlimitTypeService loginlimittypeservice;

	@Resource
	private LoginlimitIpService loginlimitipservice;

	/**
	 * 获取角色组
	 *
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "userList")
	public ModelAndView userList(ModelMap modelMap, HttpServletRequest request) {
		User user = HttpUtils.getUser(request);;
		List<RoleGroup> RoleGroupList = rolegroupservice.queryRoleGroupByCustomerCode(user.getCustomerunitcode());
		modelMap.put("RoleGroupList", RoleGroupList);
		return new ModelAndView("foundation/platform/pagelet/v1.0/userManage/userList", modelMap);
	}

	/**
	 * 用户展示
	 *
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "userListGrid")
	public ModelAndView userListGrid(ModelMap modelMap, HttpServletRequest request, User userparams, String page,
			String rows) {
		User user = HttpUtils.getUser(request);;
		Map<String, Object> params = new HashMap<String, Object>();
		// 添加查询条件
		// ... params.put("name","jack");...
		if (userparams.getEmpcode() != null && !userparams.getEmpcode().trim().equals("")) {
			params.put("empcode", userparams.getEmpcode());
		}
		if (userparams.getRoleid() != null && !userparams.getRoleid().trim().equals("")
				&& !userparams.getRoleid().trim().equals("-1")) {
			params.put("roleid", userparams.getRoleid());
		}
		if (userparams.getEmpstate() != null) {
			params.put("empstate", userparams.getEmpstate());
		}
		if (userparams.getRoleid().equals("-1")) {
			userparams.setRoleid(null);
		}
		if (userparams.getEmpcode().trim().equals("")) {
			userparams.setEmpcode(null);
		}
		// 设置全局的查询条件（分页查询用）
		params.put("isdelete", 0);
		params.put("customerunitcode", user.getCustomerunitcode());
		// 查询总数用
		userparams.setCustomerunitcode(user.getCustomerunitcode());
		userparams.setIsdelete(new Long(0));
		// 获取总条数
		long totalCount = userservice.queryUserCount(userparams);

		// 当前页
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		// 每页显示条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		// 设置分页对象
		Page pageObject = executePage(request, totalCount, intPage, number);
		pageObject.setJsMethod("reloadUserList");
		// 如排序
		if (pageObject.isSort()) {
			params.put("orderName", pageObject.getSortName());
			params.put("descAsc", pageObject.getSortState());
		} else {
			// 没有进行排序,默认排序方式
			params.put("orderName", "id");
			params.put("descAsc", "asc");
		}
		// 每页的开始记录 第一页为1 第二页为number +1
		int start = (intPage - 1) * number;

		params.put("startIndex", start);
		params.put("endIndex", number);

		// 查询结果集合
		List<User> users = userservice.queryUserPage(params);
		modelMap.put("total", totalCount);// total键 存放总记录数，必须的
		modelMap.put("rows", users);
		modelMap.put("isadmin", user.getIsadmin());
		return new ModelAndView("foundation/platform/pagelet/v1.0/userManage/userList_grid", modelMap);
	}

	/**
	 * 添加界面
	 *
	 * @return
	 */
	@RequestMapping(value = "addUserUI")
	public String addUserUI(ModelMap modelMap, HttpServletRequest request) {
		User user = HttpUtils.getUser(request);;
		List<RoleGroup> RoleGroupList = rolegroupservice.queryRoleGroupByCustomerCode(user.getCustomerunitcode());
		String role_uid = user.getRoleid();
		List<LoginlimitType> list = loginlimittypeservice.findAll();
		modelMap.put("role_uid", user.getRoleid());
		modelMap.put("loginlimitlist", list);
		modelMap.put("customerunitcode", user.getCustomerunitcode());
		modelMap.put("RoleGroupList", RoleGroupList);
		return "foundation/platform/pagelet/v1.0/userManage/addUserUI";
	}

	/**
	 *
	 * 密码修改UI
	 * 
	 * @return
	 */
	@RequestMapping(value = "toChangePW")
	public String toChangePWUI(ModelMap modelMap) {
		return "foundation/platform/pagelet/v1.0/userManage/changePWUI";
	}

	/**
	 * 密码修改
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "changePW")
	public String changePW(ModelMap modelMap, HttpServletRequest request) {
		User user = HttpUtils.getUser(request);;
		String oldPWD = request.getParameter("oldPWD");
		String newPWD = request.getParameter("newPWD");
		String reNew = request.getParameter("reNew");
		if (StringUtils.isBlank(oldPWD) || StringUtils.isBlank(newPWD) || StringUtils.isBlank(reNew)) {
			modelMap.put("error", "请填写所有信息");
		} else if (!newPWD.equals(reNew)) {
			modelMap.put("error", "新密码两次填写不匹配");
		} else {
			try {
				userservice.changePassword(user, oldPWD, newPWD);
				modelMap.put("msg", "密码修改成功");
			} catch (Exception e) {
				modelMap.put("error", e.getMessage());
			}
		}

		return "foundation/platform/pagelet/v1.0/userManage/changePWUI";
	}
	/**
	 *
	 * @Description: 获取用户角色 @param @return @return Msg @throws
	 */
	/*
	 * @RequestMapping(value = "getRole", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Msg getRole() { return doExpAssert(new
	 * AssertObject() {
	 * 
	 * @Override public void AssertMethod(Msg msg) {
	 * //王鹏:删除并新增（把从seesion获取改为从redis读登录信息） LegalPerson
	 * legalPerson=CookieUtil.getObj(request2, "legalPersonId",
	 * LegalPerson.class); //(LegalPerson) request.getSession() //
	 * .getAttribute(Constant.SESSIONCUSTOMER); JSONObject jsonObject = new
	 * JSONObject(); setJsonObject(jsonObject);
	 * getJsonObject().put("customerunitcode",
	 * legalPerson.getCustomerunitcode()); // 获取角色 Page empRole =
	 * roleService.queryRoleByCustomerCode( getJsonObject(), null);
	 * msg.setData(empRole); } }); }
	 * 
	 *//**
		 *
		 * @Description: 获取用户角色组 @param @return @return Msg @throws
		 */

	/*
	 * @RequestMapping(value = "getRoleGroup", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Msg getRoleGroup() { return doExpAssert(new
	 * AssertObject() {
	 * 
	 * @Override public void AssertMethod(Msg msg) {
	 * //王鹏:删除并新增（把从seesion获取改为从redis读登录信息） LegalPerson
	 * legalPerson=CookieUtil.getObj(request2, "legalPersonId",
	 * LegalPerson.class); //(LegalPerson) request.getSession() //
	 * .getAttribute(Constant.SESSIONCUSTOMER); JSONObject jsonObject = new
	 * JSONObject(); setJsonObject(jsonObject);
	 * getJsonObject().put("customerunitcode",
	 * legalPerson.getCustomerunitcode()); // 获取角色组 Page roleGroup =
	 * roleGroupService.queryRoleGroupByCustomerCode( getJsonObject(), null);
	 * msg.setData(roleGroup); } }); }
	 * 
	 *//**
		 * 保存信息
		 *
		 * @param
		 * @return
		 */
	@RequestMapping(value = "addUser", method = RequestMethod.POST)
	@ResponseBody
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public Msg add(User user, HttpServletRequest request) {
		Msg msg = new Msg();
		String limitip = request.getParameter("limitip");
		User loginuser = HttpUtils.getUser(request);;
		User parameter = new User();
		parameter.setEmpcode(user.getEmpcode().trim());
		parameter.setCustomerunitcode(loginuser.getCustomerunitcode());
		List<User> list = userservice.selectBySelective(parameter);
		if (list.size() != 0) {
			msg.setSuccess(false);
			msg.setMsg("此用户名已经存在！");
		} else {
			// 保存user
			user.setEmppwd(MD5Util.md5(user.getEmppwd()));
			user.setIsdelete(new Long(0));
			user.setOpdt(new Date());
			user.setId(StringUtil.getUUID());
			if (userservice.saveUser(user, limitip)) {
				
				log.info(user.getName());
				msg.setMsg("新增成功！");
				msg.setSuccess(true);
			}
		}
		return msg;

	}

	/**
	 * 更新界面
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "editUserUI")
	public String editUserUI(ModelMap modelMap, HttpServletRequest request) {

		User adminuser = HttpUtils.getUser(request);;

		String empuuid = request.getParameter("uuid");
		User user = userservice.selectByPrimaryKey(empuuid);
		String ips = "";
		if (user.getLoglimit().intValue() == 1) {// IP限制
			List<LoginlimitIp> list = loginlimitipservice.selectByUser(user);
			for (LoginlimitIp l : list) {
				ips += l.getLimitip() + ";";
			}
		}
		modelMap.put("limitip", ips);
		modelMap.put("loginlimitlist", loginlimittypeservice.findAll());
		modelMap.put("eduser", user);
		// 角色组
		List<RoleGroup> RoleGroupList = rolegroupservice.queryRoleGroupByCustomerCode(user.getCustomerunitcode());

		String role_uid = adminuser.getRoleid();
		modelMap.put("isadmin", 0);
		if (empuuid.equals(adminuser.getId())) {
			modelMap.put("isadmin", 1); // 当前租户管理员
		}
		modelMap.put("role_uid", role_uid);
		modelMap.put("pageView", RoleGroupList);
		return "foundation/platform/pagelet/v1.0/userManage/editUserUI";
	}

	/**
	 * 更新信息
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateUser", method = RequestMethod.POST)
	@ResponseBody
	public Msg updateUser(User user, HttpServletRequest request) {
		Msg msg = new Msg();
		User adminuser = HttpUtils.getUser(request);;
		String limitip = request.getParameter("limitip");
		User user2 = userservice.selectByPrimaryKey(user.getId());
		if ("08600000000".equals(adminuser.getCustomerunitcode()) && "admin".equals(user2.getEmpcode())) { // 特殊处理
																											// 08600000000
			user.setEmpcode("admin");
			user.setEmpstate(new Long(1));
		}
		if (user.getEmppwd().trim().equals("")) {
			user.setEmppwd(null);
		} else {
			user.setEmppwd(MD5Util.md5(user.getEmppwd()));
		}
		// "系统管理员角色不可以修改！";
		if (adminuser.getId().equals(user.getId())) {
			String rolegroup_uid = adminuser.getRoleid();
			user.setRoleid(rolegroup_uid);
		}
		User parameter = new User();
		parameter.setEmpcode(user.getEmpcode().trim());
		List<User> list = userservice.selectBySelective(parameter);
		if (list.size() > 1) {
			msg.setSuccess(false);
			msg.setMsg("此用户名已经存在！");
		} else {
			user.setOpdt(new Date());
			userservice.updateUser(user, limitip, user2.getEmpcode());
			;
			msg.setSuccess(true);
			msg.setMsg("更新成功！");
		}
		return msg;
	}

	/**
	 * 删除用户
	 *
	 * @return
	 */
	@RequestMapping(value = "deleteUser")
	@ResponseBody
	public Msg deleteUser(HttpServletRequest request) {
		Msg msg = new Msg();
		String userid = request.getParameter("uuid");
		User adminuser = HttpUtils.getUser(request);;
		if (adminuser.getId().equals(userid)) {
			msg.setSuccess(false);
			msg.setData("系统管理员不可以修改！");
		} else {
			// 删除用户
			userservice.delUser(userid);
			msg.setSuccess(true);
			msg.setMsg("删除成功！");
		}
		return msg;
	}
}
