/**  
 * @Title: RoleController.java
 * @Package cn..function.platform.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaochongfei 
 * @date 2014-3-28 下午03:03:03
 * @version V1.0  
 */
package com.cn.webApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import com.cn.webApp.common.HttpUtils;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.common.page.Page;
import com.cn.webApp.controller.base.BaseController;
import com.cn.webApp.controller.base.Msg;
import com.cn.webApp.model.Role;
import com.cn.webApp.model.RoleGroup;
import com.cn.webApp.model.RoleMenu;
import com.cn.webApp.model.User;
import com.cn.webApp.service.RoleGroupService;
import com.cn.webApp.service.RoleMenuService;
import com.cn.webApp.service.RoleService;

/**
 * 
 * @author rg 上午9:50:56
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/role")
@SuppressWarnings("all")
public class RoleController extends BaseController {
	@Resource
	private RoleService roleservice;

	@Resource
	private RoleGroupService rolegroupservice;

	@Resource
	private RoleMenuService rolemenuservice;

	/**
	 * 
	 * @Title: userListGrid @Description: 角色列表 @param @param
	 *         modelMap @param @return 设定文件 @return ModelAndView 返回类型 @throws
	 */
	@RequestMapping(value = "roleList")
	public ModelAndView roleList(ModelMap modelMap) {

		return new ModelAndView("foundation/platform/pagelet/v1.0/roleManage/roleList", modelMap);
	}

	/**
	 * 
	 * @Description: 角色列表 @param @param modelMap @param @return @return
	 *               ModelAndView @throws
	 */
	@RequestMapping(value = "roleListGrid")
	public ModelAndView roleListGrid(ModelMap modelMap, HttpServletRequest request, String page, String rows) {
		User user =HttpUtils.getUser(request);
		Map<String, Object> params = new HashMap<String, Object>();
		// 添加查询条件
		params.put("customerunitcode", user.getCustomerunitcode());
		// 获取总条数
		Role parameters = new Role();
		parameters.setCustomerunitcode(user.getCustomerunitcode());
		long totalCount = roleservice.queryRolesCounts(parameters);

		// 当前页
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		// 每页显示条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		// 设置分页对象
		Page pageObject = executePage(request, totalCount, intPage, number);
		pageObject.setJsMethod("reloadRoleList");
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
		List<Role> RoleList = roleservice.queryPageRoles(params);
		modelMap.put("pageView", RoleList);
		modelMap.put("total", totalCount);
		return new ModelAndView("foundation/platform/pagelet/v1.0/roleManage/roleList_grid", modelMap);
	}

	/**
	 * 
	 * @Description: 添加界面 @param @param modelMap @param @return @return
	 *               String @throws
	 */
	@RequestMapping(value = "addRoleUI")
	public String addUserUI(ModelMap modelMap) {

		return "foundation/platform/pagelet/v1.0/roleManage/addRoleUI";
	}

	/**
	 * 
	 * @Description: 新增角色 @param @return @return Msg @throws
	 */
	@RequestMapping(value = "addRole", method = RequestMethod.POST)
	@ResponseBody
	public Msg add(Role role, HttpServletRequest request) {
		Msg msg = new Msg();
		User user = HttpUtils.getUser(request);;
		/*
		 * LegalPerson legalPerson=CookieUtil.getObj(request2, "legalPersonId",
		 * LegalPerson.class); getJsonObject().put("customerunitcode",
		 * legalPerson.getCustomerunitcode()); Role role =
		 * JSONTools.JSONToBean(getJsonObject(), Role.class);
		 */
		role.setCustomerunitcode(user.getCustomerunitcode());
		role.setId(StringUtil.getUUID());
		Role parameters = new Role();
		parameters.setCustomerunitcode(user.getCustomerunitcode());
		parameters.setRolename(role.getRolename());
		if (roleservice.selectBySelective(parameters).size() > 0) {
			msg.setSuccess(false);
			msg.setMsg("此角色名已经存在！");
			return msg;
		}
		// // 需要封装放入个 menuid 数组 , moudelid数组 供修改时候用
		int menulength = Integer.parseInt(request.getParameter("menulength"));
		List<RoleMenu> rolemenuLsit = new ArrayList<RoleMenu>();
		if (menulength > 0) {
			for (int i = 0; i < menulength; i++) {
				RoleMenu obj = new RoleMenu();
				obj.setId(StringUtil.getUUID());
				obj.setRoleid(role.getId());
				obj.setModuleid(request.getParameter("menuarray[" + i + "][moudleid]"));
				obj.setMenuid(request.getParameter("menuarray[" + i + "][menuid]"));
				obj.setCustomerunitcode(user.getCustomerunitcode());
				rolemenuLsit.add(obj);
			}
		}
		if (roleservice.addRole(role, rolemenuLsit)) {
			msg.setSuccess(true);
			msg.setMsg("新增成功！");
		} else {
			msg.setSuccess(false);
			msg.setMsg("新增异常！");
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
	@RequestMapping(value = "editRoleUI")
	public String editRoleUI(ModelMap modelMap, HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		Role role = roleservice.selectByPrimaryKey(uuid);
		modelMap.put("role", role);
		return "foundation/platform/pagelet/v1.0/roleManage/editRoleUI";
	}

	/**
	 * 
	 * @Description: 编辑角色 @param @return @return Msg @throws
	 */
	@RequestMapping(value = "editRole", method = RequestMethod.POST)
	@ResponseBody
	public Msg editRole(Role role, HttpServletRequest request) {
		Msg msg = new Msg();
		/*
		 * // 王鹏:删除并新增（把从seesion获取改为从redis读登录信息） LegalPerson legalPerson =
		 * CookieUtil.getObj(request2, "legalPersonId", LegalPerson.class);
		 * getJsonObject().put("customerunitcode",
		 * legalPerson.getCustomerunitcode()); Role role =
		 * JSONTools.JSONToBean(getJsonObject(), Role.class);
		 * 
		 * // 王鹏:删除并新增（把从seesion获取改为从redis读登录信息） User sessionUser =
		 * CookieUtil.getObj(request2, "userId", User.class);
		 */
		User sessionUser = HttpUtils.getUser(request);;
		role.setCustomerunitcode(sessionUser.getCustomerunitcode());
		if (1 == sessionUser.getIsadmin()) { // 是租户管理员
		} else {
			String rolegroup_uid = sessionUser.getRoleid();
			RoleGroup rolegroup = rolegroupservice.selectByPrimaryKey(rolegroup_uid);
			String role_uid = rolegroup.getRoleids();
			if (role.getId().equals(role_uid)) {
				msg.setSuccess(false);
				msg.setMsg("系统管理员角色不可以修改！");
				return msg;
			}
		}
        Role parameters=new Role();
        parameters.setRolename(role.getRolename());
        int j=roleservice.selectBySelective(parameters).size();
		if (j>1) {
			msg.setSuccess(false);
			msg.setMsg("此角色名已经存在！");
			return msg;
		}
		// 需要封装放入个 menuid 数组 , moudelid数组 供修改时候用
		int menulength = Integer.parseInt(request.getParameter("menulength"));
		List<RoleMenu> rolemenuLsit = new ArrayList<RoleMenu>();
		if (menulength > 0) {
			for (int i = 0; i < menulength; i++) {
				RoleMenu obj = new RoleMenu();
				obj.setRoleid(role.getId());
				obj.setModuleid(request.getParameter("menuarray[" + i + "][moudleid]"));
				obj.setMenuid(request.getParameter("menuarray[" + i + "][menuid]"));
				obj.setCustomerunitcode(sessionUser.getCustomerunitcode());
				obj.setId(StringUtil.getUUID());
				rolemenuLsit.add(obj);
			}
		}
		if (roleservice.editRole(role, rolemenuLsit)) {
			msg.setSuccess(true);
			msg.setMsg("修改成功！");
		}
		return msg;
	}

	/**
	 * 
	 * @Description: 删除角色 @param @return @return Msg @throws
	 */
	@RequestMapping(value = "deleteRole", method = RequestMethod.POST)
	@ResponseBody
	public Msg deleteRole(HttpServletRequest request) {
		Msg msg = new Msg();
		String uuid = request.getParameter("uuid");
		if (uuid == null || uuid.trim().equals("")) {
			msg.setMsg("删除失败！");
			msg.setSuccess(false);
			return msg;
		}
		// 查询是否有角色组在用
		if (rolegroupservice.selectByRoleids(uuid).size() > 0) {
			int i=rolegroupservice.selectByRoleids(uuid).size();
			msg.setMsg("有角色组在使用此角色！");
			msg.setSuccess(false);
			return msg;
		}
		// 查询是否有关联菜单
		if (rolemenuservice.queryMenuByRoleId(uuid).size() > 0) {
			msg.setMsg("有菜单在使用此角色！");
			msg.setSuccess(false);
			return msg;
		}
		// 删除用户
		if (1 == roleservice.deleteByPrimaryKey(uuid)) {
			msg.setSuccess(true);
			msg.setMsg("删除成功！");
		}
		return msg;
	}

}
