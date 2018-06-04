package com.cn.webApp.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cn.webApp.common.ExcelUtils;
import com.cn.webApp.common.HttpUtils;
import com.cn.webApp.controller.base.Msg;
import com.cn.webApp.model.AppRedit;
import com.cn.webApp.model.Menu;
import com.cn.webApp.model.RoleMenu;
import com.cn.webApp.model.User;
import com.cn.webApp.service.AppReditService;
import com.cn.webApp.service.MenuService;
import com.cn.webApp.service.RoleMenuService;
import com.cn.webApp.service.RoleService;
import com.cn.webApp.service.Win8UIService;
import com.cn.webApp.tree.model.Tree;
import com.alibaba.fastjson.JSONArray;

@Controller
@Scope("prototype")
@RequestMapping(value = "/menuManage")
@SuppressWarnings("all")
public class MenuManageController {

	@Resource
	private Win8UIService win8uiService;

	@Resource
	private RoleService roleservice;

	@Resource
	private RoleMenuService rolemenuservice;

	@Resource
	private AppReditService appreditservcie;

	@Resource
	private MenuService menuservice;

	/**
	 * 系统默认菜单设置 列表
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "indexUI")
	public ModelAndView indexUI(ModelMap modelMap) {
		List<Map<String, Object>> list = appreditservcie.AppMessage();
		modelMap.put("pageView1", list);
		return new ModelAndView("foundation/menuMang/pagelet/v1.0/indexUI", modelMap);
	}

	/**
	 * app的树状菜单展示
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "AppMenuTree")
	@ResponseBody
	public List<Map<String, Object>> AppMenuTree(ModelMap modelMap, HttpServletRequest request) {
		String appid = request.getParameter("appid");
		List<Map<String, Object>> list = menuservice.queryAppMenuByAppId(appid);
		return list;
	}

	/**
	 * 系统菜单维护 设置提交
	 * 
	 * @param modelMap
	 * @return msg @throws
	 */
	@RequestMapping(value = "AppMenuInstallCommit")
	public Msg appMenuInstallCommit(ModelMap modelMap, HttpServletRequest request) {
		Msg msg = new Msg();
		int applength = Integer.parseInt(request.getParameter("nodelength"));
		List<Menu> menuLsit = new ArrayList<Menu>();
		if (applength > 0) {
			for (int i = 0; i < applength; i++) {
				String nodeid = request.getParameter("nodearray[" + i + "][nodeid]");
				if (nodeid.startsWith("n")) {
					Menu obj = new Menu();
					obj.setMenuid(nodeid.substring(1));
					obj.setMenuname((request.getParameter("nodearray[" + i + "][nodename]")).trim());
					obj.setSortid(i);
					obj.setIsvisible(1);
					menuLsit.add(obj);
				}
			}
		}
		try {
			menuservice.updateMenuTree(menuLsit);
		} catch (Exception e) {
			e.printStackTrace();
			msg.setMsg("保存异常，请重试！");
			msg.setSuccess(false);
			return msg;
		}
		msg.setSuccess(true);
		msg.setMsg("保存成功！");
		return msg;

	}

	/**
	 * 角色 包--树形数据加载
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "customRoleTree", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<Tree> customRoleTree(ModelMap modelMap, HttpServletRequest request) {
		/*
		 * LegalPerson legalPerson = CookieUtil.getObj(request2,
		 * "legalPersonId", LegalPerson.class); String cuscode =
		 * legalPerson.getCustomerunitcode();
		 */
		User user = HttpUtils.getUser(request);
		String cuscode = user.getCustomerunitcode();
		List<Tree> tree = win8uiService.showRoleTree(cuscode);
		String roleid = request.getParameter("roleid");
		if (StringUtils.isNotBlank(roleid)) { // 编辑时，否则是新增时
			List<RoleMenu> lpa = rolemenuservice.queryMenuByRoleId(roleid);
			if (lpa != null && !lpa.isEmpty()) {
				for (int i = 0; i < tree.size(); i++) {
					for (int j = 0; j < lpa.size(); j++) {
						if (lpa.get(j).getMenuid().equals(tree.get(i).getId())) {
							tree.get(i).setChecked(true);
							break;
						}
					}
				}
			}
		}
		return tree;
	}

	/**
	 * 上传应用菜单
	 * 
	 * @param file
	 * @return
	 */
	@RequestMapping(value = "/uploadmenu", method = RequestMethod.POST)
	@ResponseBody
	public Msg uploadMenuConfig(@RequestParam("file") MultipartFile file) {
		Msg msg = new Msg();
		try {
			JSONArray menuArray = ExcelUtils.parseMenus(file.getInputStream());
			menuservice.uploadMenu(menuArray);
			msg.setSuccess(true);
			msg.setMsg("菜单添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			msg.setSuccess(false);
			msg.setMsg("菜单添加失败");
		}
		return msg;
	}
}
