/**
 * @Title:
 * @Package cn..function.platform.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author rg
 * @date 2016-7-14 下午03:03:03
 * @version V1.0
 */
package com.cn.webApp.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.webApp.common.HttpUtils;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.common.page.Page;
import com.cn.webApp.controller.base.BaseController;
import com.cn.webApp.controller.base.Msg;
import com.cn.webApp.model.Legalperson;
import com.cn.webApp.model.ServerApp;
import com.cn.webApp.model.User;
import com.cn.webApp.service.LegalpersonService;
import com.cn.webApp.service.ServerAppService;

/**
 * 
 * @author rg 下午1:53:02
 */

@Controller
@Scope("prototype")
@RequestMapping(value = "/serverApp")
@SuppressWarnings("all")
public class ServerAppController extends BaseController {

	@Resource
	private ServerAppService serverAppService;

	@Resource
	private LegalpersonService legalpersonservice;

	@RequestMapping(value = "serverAppList")
	public ModelAndView serverAppList(ModelMap modelMap) {
		return new ModelAndView("foundation/serverAppManage/pagelet/v1.0/serverAppList", modelMap);
	}

	/**
	 * 列表展示
	 *
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "serverAppListGrid")
	public ModelAndView serverAppListGrid(ModelMap modelMap, HttpServletRequest request, ServerApp serverAppparams,
			String page, String rows) {
		User user = HttpUtils.getUser(request);
		;
		Map<String, Object> params = new HashMap<String, Object>();
		// 添加查询条件
		// ... params.put("name","jack");...
		// 获取总条数
		long totalCount = serverAppService.queryServerAppCount(serverAppparams);

		// 当前页
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		// 每页显示条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		// 设置分页对象
		Page pageObject = executePage(request, totalCount, intPage, number);
		pageObject.setJsMethod("reloadServerAppList");
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
		List<ServerApp> serverApps = serverAppService.queryServerAppPage(params);
		modelMap.put("total", totalCount);// total键 存放总记录数，必须的
		modelMap.put("rows", serverApps);
		return new ModelAndView("foundation/serverAppManage/pagelet/v1.0/serverAppList_grid", modelMap);
	}

	/**
	 * 添加界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "addServerAppUI")
	public String addServerAppUI(ModelMap modelMap, HttpServletRequest request) {
		// 查询租户结果集合
		List<Legalperson> legalpersonList = legalpersonservice.selectAll();
		modelMap.put("legalpersonList", legalpersonList);
		return "foundation/serverAppManage/pagelet/v1.0/addServerAppUI";
	}

	/**
	 * 
	 * @Description: 新增 @param @return @return Msg @throws
	 */
	@RequestMapping(value = "addServerApp", method = RequestMethod.POST)
	@ResponseBody
	public Msg addServerApp(ServerApp serverApp, HttpServletRequest request) {
		Msg msg = new Msg();
		User loginuser = HttpUtils.getUser(request);
		;
		serverApp.setOperator(loginuser.getEmpcode().trim());
		serverApp.setCtTime(new Date());
		serverApp.setId(StringUtil.getUUID());
		if (1 == serverAppService.insertSelective(serverApp)) {
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
	@RequestMapping(value = "editServerAppUI")
	public String editServerAppUI(ModelMap modelMap, HttpServletRequest request) {
		// 查询租户结果集合
		List<Legalperson> legalpersonList = legalpersonservice.selectAll();
		modelMap.put("legalpersonList", legalpersonList);
		// 根据主键查询server_app信息
		User user = HttpUtils.getUser(request);
		;
		String uuid = request.getParameter("uuid");
		ServerApp serverApp = serverAppService.selectByPrimaryKey(uuid);
		modelMap.put("serverApp", serverApp);
		return "foundation/serverAppManage/pagelet/v1.0/editServerAppUI";
	}

	/**
	 * 
	 * @Description: 更新@param @return @return Msg @throws
	 */

	@RequestMapping(value = "editServerApp", method = RequestMethod.POST)
	@ResponseBody
	public Msg editServerApp(HttpServletRequest request, ServerApp serverApp) {
		Msg msg = new Msg();
		User loginuser = HttpUtils.getUser(request);
		;
		serverApp.setOperator(loginuser.getEmpcode().trim());
		serverApp.setCtTime(new Date());
		try {
			serverAppService.updateByPrimaryKeySelective(serverApp);
			msg.setSuccess(true);
			msg.setMsg("更新成功！");
		} catch (Exception e) {
			msg.setSuccess(false);
			msg.setMsg("更新异常！");
		}
		return msg;
	}

	/**
	 * 
	 * @Description:删除 @param @return @return Msg @throws
	 */
	@RequestMapping(value = "deleteServerApp", method = RequestMethod.POST)
	@ResponseBody
	public Msg deleteServerApp(HttpServletRequest request) {
		Msg msg = new Msg();
		String uuid = request.getParameter("uuid");
		if (uuid == null) {
			msg.setMsg("删除失败！");
			msg.setSuccess(false);
		}
		// 删除用户组
		serverAppService.deleteByPrimaryKey(uuid);
		msg.setSuccess(true);
		msg.setMsg("删除成功！");
		return msg;
	}
}
