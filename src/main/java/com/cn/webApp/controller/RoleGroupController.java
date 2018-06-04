/**  
 * @Title: RoleGroupController.java
 * @Package cn..function.platform.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaochongfei 
 * @date 2014-3-28 下午03:03:03
 * @version V1.0  
 */
package com.cn.webApp.controller;

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
import com.cn.webApp.model.User;
import com.cn.webApp.service.RoleGroupService;
import com.cn.webApp.service.RoleService;
import com.cn.webApp.service.UserService;

/**
 * 
 * @author rg 上午9:14:20
 */

@Controller
@Scope("prototype")
@RequestMapping(value = "/roleGroup")
@SuppressWarnings("all")
public class RoleGroupController extends BaseController {
	@Resource
	private RoleGroupService rolegroupservice;

	@Resource
	private UserService userservice;

	@Resource
	private RoleService roleservice;

	/**
	 * 
	 * @Title: userListGrid @Description: 角色组列表 @param @param
	 *         modelMap @param @return 设定文件 @return ModelAndView 返回类型 @throws
	 */
	@RequestMapping(value = "roleGroupListGrid")
	public ModelAndView roleGroupListGrid(ModelMap modelMap, HttpServletRequest request, String page, String rows) {
		User user = HttpUtils.getUser(request);;
		Map<String, Object> params = new HashMap<String, Object>();
		// 添加查询条件
		params.put("customerunitcode", user.getCustomerunitcode());
		// 获取总条数
		long totalCount = rolegroupservice.queryCount(user.getCustomerunitcode());

		// 当前页
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		// 每页显示条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		// 设置分页对象
		Page pageObject = executePage(request, totalCount, intPage, number);
		pageObject.setJsMethod("reloadRoleGroupList");
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
		List<Map<String, Object>> RoleGroupList = rolegroupservice.queryRoleGroups(params);
		modelMap.put("pageView", RoleGroupList);
		modelMap.put("total", totalCount);
		return new ModelAndView("foundation/platform/pagelet/v1.0/roleGroupManage/roleGroupList_grid", modelMap);
	}

	@RequestMapping(value = "roleGroupList")
	public ModelAndView roleGroupList(ModelMap modelMap) {
		return new ModelAndView("foundation/platform/pagelet/v1.0/roleGroupManage/roleGroupList", modelMap);
	}

	/**
	 * 添加界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "addRoleGroupUI")
	public String addRoleGroupUI(ModelMap modelMap, HttpServletRequest request) {
		User user = HttpUtils.getUser(request);;
		// 获取角色
		List<Role> page = roleservice.queryRoleByCustomerCode(user.getCustomerunitcode());
		modelMap.put("page", page);
		return "foundation/platform/pagelet/v1.0/roleGroupManage/addRoleGroupUI";
	}
	/**
	 * 
	 * @Description: 新增角色组 @param @return @return Msg @throws
	 */

	/**
	 * 
	 * @Description: 新增角色组 @param @return @return Msg @throws
	 */
	@RequestMapping(value = "addRoleGroup", method = RequestMethod.POST)
	@ResponseBody
	public Msg add(RoleGroup rolegroup, HttpServletRequest request) {
		Msg msg = new Msg();
		User user = HttpUtils.getUser(request);;
		rolegroup.setCustomerunitcode(user.getCustomerunitcode());
		rolegroup.setId(StringUtil.getUUID());
		RoleGroup parameters = new RoleGroup();
		parameters.setCustomerunitcode(user.getCustomerunitcode());
		parameters.setGroupname(rolegroup.getGroupname());
		List<RoleGroup> list = rolegroupservice.verifyRoleGroupByName(parameters);
		if (list.size() < 1) {
			msg.setSuccess(false);
			msg.setMsg("此角色组名已经存在！");
		}
		if (1 == rolegroupservice.insert(rolegroup)) {
			msg.setSuccess(true);
			msg.setMsg("新增成功！");
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
	@RequestMapping(value = "editRoleGroupUI")
	public String editRoleGroupUI(ModelMap modelMap, HttpServletRequest request) {
		User user = HttpUtils.getUser(request);;
		List<Role> page = roleservice.queryRoleByCustomerCode(user.getCustomerunitcode());
		modelMap.put("page", page);
		String uuid = request.getParameter("uuid");
		RoleGroup rolegroup = rolegroupservice.selectByPrimaryKey(uuid);
		modelMap.put("roleGroup", rolegroup);
		String[] roles = rolegroup.getRoleids().split(",");
		modelMap.put("roleArry", roles);
		User parameters = new User();
		parameters.setRoleid(uuid);
		parameters.setIsdelete(new Long(0));
		List<User> list = userservice.selectBySelective(parameters);
		modelMap.put("userpage", list);
		return "foundation/platform/pagelet/v1.0/roleGroupManage/editRoleGroupUI";
	}

	/**
	 * 
	 * @Description: 更新角色组 @param @return @return Msg @throws
	 */

	@RequestMapping(value = "editRoleGroup", method = RequestMethod.POST)
	@ResponseBody
	public Msg editRoleGroup(HttpServletRequest request, RoleGroup rolegroup) {
		Msg msg = new Msg();
		User user = HttpUtils.getUser(request);;
		User adminuser = userservice.selectByCustomerunitcode(user.getCustomerunitcode());
		String role_uid = adminuser.getRoleid();

		if (rolegroup.getId().equals(role_uid)) {
			msg.setSuccess(false);
			msg.setMsg("系统管理员组不可以修改！");
		}

		if (!rolegroup.getGroupname().trim()
				.equals(rolegroupservice.selectByPrimaryKey(rolegroup.getId()).getGroupname())) {
			msg.setSuccess(false);
			msg.setMsg("此角色组名已经存在！");
		}
		if (1 == rolegroupservice.updateByPrimaryKeyWithBLOBs(rolegroup)) {
			msg.setSuccess(true);
			msg.setMsg("更新成功！");
		} else {
			msg.setSuccess(false);
			msg.setMsg("更新异常！");
		}
		return msg;
	}

	/**
	 * 
	 * @Description:删除 @param @return @return Msg @throws
	 */
	@RequestMapping(value = "deleteRoleGroup", method = RequestMethod.POST)
	@ResponseBody
	public Msg deleteRoleGroup(HttpServletRequest request) {
		Msg msg = new Msg();
		String uuid = request.getParameter("uuid");
		if (uuid == null) {
			msg.setMsg("删除失败！");
			msg.setSuccess(false);
		}
		User parameters = new User();
		parameters.setRoleid(uuid);
		parameters.setIsdelete(new Long(0));
		List<User> list = userservice.selectBySelective(parameters);
		if (list.size() != 0) {
			msg.setMsg("有用户在使用此角色组！");
			msg.setSuccess(false);
		} else {
			// 删除用户组
			rolegroupservice.deleteByPrimaryKey(uuid);
			msg.setSuccess(true);
			msg.setMsg("删除成功！");
		}
		return msg;
	}

}
