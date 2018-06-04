package com.cn.webApp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.cn.webApp.common.page.Page;
import com.cn.webApp.controller.base.BaseController;
import com.cn.webApp.controller.base.Msg;
import com.cn.webApp.model.UserAdvise;
import com.cn.webApp.service.UserAdviseService;

/**
 * 用户建议
 * 
 * @author rg 上午11:26:39
 */
@Controller
@RequestMapping(value = "userAdvise")
public class UserAdviseController extends BaseController {
	@Resource
	private UserAdviseService useradviseservice;

	Logger log = Logger.getLogger(this.getClass());

	@RequestMapping(value = "UserAdviseList")
	public ModelAndView UserAdviseList(ModelMap modelMap) {
		return new ModelAndView("foundation/UserAdviseManage/pagelet/v1.0/UserAdviseList", modelMap);
	}

	/**
	 * 列表展示
	 *
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "UserAdviseListGrid")
	public ModelAndView UserAdviseListGrid(ModelMap modelMap, HttpServletRequest request, String page, String rows) {
		;
		Map<String, Object> params = new HashMap<String, Object>();
		// 添加查询条件

		// 获取总条数
		long totalCount = useradviseservice.queryUserAdviseCount(new UserAdvise());

		// 当前页
		int intPage = Integer.parseInt((page == null || page == "0") ? "1" : page);
		// 每页显示条数
		int number = Integer.parseInt((rows == null || rows == "0") ? "10" : rows);
		// 设置分页对象
		Page pageObject = executePage(request, totalCount, intPage, number);
		pageObject.setJsMethod("reloadUserAdviseList");
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
		List<UserAdvise> resourcelist = useradviseservice.queryUserAdvisePage(params);
		modelMap.put("total", totalCount);// total键 存放总记录数，必须的
		modelMap.put("rows", resourcelist);
		return new ModelAndView("foundation/UserAdviseManage/pagelet/v1.0/UserAdviseList_grid", modelMap);
	}

	/**
	 * 查看用户建议界面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "showUserAdviseUI", method = RequestMethod.POST)
	public String showUserAdviseUI(ModelMap modelMap, HttpServletRequest request) {
		String uuid = request.getParameter("uuid");
		UserAdvise useradvise = null;
		try {
			useradvise = useradviseservice.selectByPrimaryKey(uuid);
		} catch (Exception e) {
			log.error(e);
		}
		modelMap.put("useradvise", useradvise);
		return "foundation/UserAdviseManage/pagelet/v1.0/ShowUserAdviseUI";
	}

	/**
	 * 删除
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "deleteServerApp", method = RequestMethod.POST)
	@ResponseBody
	public Msg deleteServerApp(HttpServletRequest request) {
		Msg msg = new Msg();
		try {
			String uuid = request.getParameter("uuid");
			useradviseservice.deleteByPrimaryKey(uuid);
			msg.setSuccess(true);
			msg.setMsg("删除成功！");
		} catch (Exception e) {
			msg.setMsg("删除失败！");
			msg.setSuccess(false);
			log.error(e);
		}
		return msg;
	}
}
