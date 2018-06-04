package com.cn.webApp.controller;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.cn.webApp.cache.Cache;
import com.cn.webApp.common.HttpUtils;
import com.cn.webApp.common.ResourcePackageUtils;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.common.page.Page;
import com.cn.webApp.controller.base.BaseController;
import com.cn.webApp.controller.base.Msg;
import com.cn.webApp.model.AppRedit;
import com.cn.webApp.model.AppResource;
import com.cn.webApp.model.AppResourcePackage;
import com.cn.webApp.model.LegalPersonModule;
import com.cn.webApp.model.LegalPersonResourcePackage;
import com.cn.webApp.model.Legalperson;
import com.cn.webApp.model.ServerApp;
import com.cn.webApp.model.User;
import com.cn.webApp.service.AppResourcePackageService;
import com.cn.webApp.service.AppResourceService;
import com.cn.webApp.service.LegalPersonResourcePackageService;
import com.cn.webApp.service.LegalpersonService;
import com.cn.webApp.tree.model.Tree;

import sun.util.logging.resources.logging;

/**
 * app资源管理 下午1:51:10
 * 
 * @author rg
 */
@Controller
@RequestMapping(value = "resourcepackageorize")
public class AppResourcePackageAuthorizeController extends BaseController {

	@Resource
	private AppResourcePackageService appresourcepackageservice;

	@Resource
	private LegalpersonService legalpersonservice;

	@Resource
	private LegalPersonResourcePackageService legalpersonresourcepackageservice;
	
	@Resource
	Cache cache;
	Logger log = Logger.getLogger(this.getClass());

	/**
	 * 租户信息包含已被授权资源包信息
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "LegalPersonList")
	public String LegalPersonList(ModelMap modelMap) {

		return "foundation/AppResourcePackageAuthorize/pagelet/v1.0/LegalPersonList";

	}

	/**
	 * 
	 * @param modelMap
	 * @param request
	 * @param legalpersonparams
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value = "legalpersonlist_grid")
	public ModelAndView LegalPersonList_grid(ModelMap modelMap, HttpServletRequest request,
			Legalperson legalpersonparams, String page, String rows) {
		Map<String, Object> params = new HashMap<String, Object>();
		// 添加查询条件
		if (legalpersonparams.getCustomername() != null && !legalpersonparams.getCustomername().trim().equals("")) {
			params.put("customername", legalpersonparams.getCustomername());
		}
		if (legalpersonparams.getCustomerunitcode() != null
				&& !legalpersonparams.getCustomerunitcode().trim().equals("")) {
			params.put("customerunitcode", legalpersonparams.getCustomerunitcode());
		}

		if (legalpersonparams.getCustomername() != null && legalpersonparams.getCustomername().trim().equals("")) {
			legalpersonparams.setCustomername(null);
		}
		if (legalpersonparams.getCustomerunitcode() != null
				&& legalpersonparams.getCustomerunitcode().trim().equals("")) {
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
		pageObject.setJsMethod("reloadLegalPersonList");
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
		List<Map<String, Object>> resourcepackagelist = legalpersonresourcepackageservice.queryList();
		modelMap.put("resourcepackagelist", resourcepackagelist);
		return new ModelAndView("foundation/AppResourcePackageAuthorize/pagelet/v1.0/LegalPersonList_grid", modelMap);
	}

	@RequestMapping(value = "permitUI")
	public ModelAndView permitUI(ModelMap modelMap, String code) {
		modelMap.put("customerunitcode", code);
		return new ModelAndView("foundation/AppResourcePackageAuthorize/pagelet/v1.0/permitUI", modelMap);
	}

	/**
	 * 资源包--树形数据加载
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "customTree", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<Tree> customTree(ModelMap modelMap, HttpServletRequest request) {
		List<Tree> tree = null;
		try {
			tree = appresourcepackageservice.CreatTree();
			String code = request.getParameter("code");
			List<LegalPersonResourcePackage> lpr = legalpersonresourcepackageservice.queryByCode(code);

			if (lpr != null && !lpr.isEmpty()) {
				for (int i = 0; i < tree.size(); i++) {
					for (int j = 0; j < lpr.size(); j++) {
						if (lpr.get(j).getResourcepackageid().equals(tree.get(i).getId())) {
							tree.get(i).setChecked(true);
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error(e);
		}
		return tree;
	}

	/**
	 * 资源包授权提交
	 * 
	 * @param modelMap
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "permitCommit")
	public Msg permitCommit(ModelMap modelMap, HttpServletRequest request) {
		Msg msg = new Msg();
		String code = request.getParameter("customerunitcode");
		int moudlelength = Integer.parseInt(request.getParameter("moudlelength"));
		String ids[] = new String[moudlelength];
		if (moudlelength > 0) {
			for (int i = 0; i < moudlelength; i++) {
				String resourcepackageid = request.getParameter("moudlearray[" + i + "][id]");
				ids[i] = resourcepackageid;
			}
		}
		try {
			legalpersonresourcepackageservice.updateByArray(code, ids);
			msg.setMsg("授权成功！");
			msg.setSuccess(true);
		} catch (Exception e) {
			msg.setMsg("授权失败！");
			msg.setSuccess(false);
			log.error(e);
		}
		return msg;
	}
}
