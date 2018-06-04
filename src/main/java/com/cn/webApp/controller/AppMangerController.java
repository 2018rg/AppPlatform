/**
*
*/
package com.cn.webApp.controller;

import java.io.InputStream;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.webApp.common.uploadUtil;
import com.cn.webApp.controller.base.Msg;
import com.cn.webApp.model.AppRedit;
import com.cn.webApp.model.LegalPersonModule;
import com.cn.webApp.service.AppModuleService;
import com.cn.webApp.service.AppReditService;
import com.cn.webApp.service.AuthorizeService;
import com.cn.webApp.service.LegalPersonModuleService;
import com.cn.webApp.service.Win8UIService;
import com.cn.webApp.tree.model.Tree;

import ca.ex.BaseException;
import ca.helper.SoftGrantMachineCodeGenerator;


/**
 * 
 * @author rg
 *
 */
@Controller
@RequestMapping(value = "/app")
public class AppMangerController {

	Logger log = Logger.getLogger(this.getClass());

	@Resource
	private AppReditService appreditservice;
	@Resource
	private AppModuleService appmoduleservice;
	@Resource
	private AuthorizeService authorizeservice;
	@Resource
	private Win8UIService win8uiService;
	@Resource
	private LegalPersonModuleService legalpersonmoduleservice;

	/**
	 * 应用注册视图
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "appUI", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView appUI(ModelMap modelMap) {
		// 获得当前被授权的应用
		List<AppRedit> apps = appreditservice.selectByBusinessAppredit();
		modelMap.addAttribute("businessAppreditList", apps);
		modelMap.addAttribute("moduleList", appmoduleservice.selectByBusinessAppredit());
		return new ModelAndView("foundation/platform/pagelet/v1.0/paltformManger/app", modelMap);
	}

	/**
	 * 文件上传控制
	 * 
	 * @param request
	 * 
	 */
	@RequestMapping(value = "uploadFile", method = RequestMethod.POST)
	public Msg uploadFile(HttpServletRequest request, HttpServletResponse response) {
		Msg msg = new Msg();
		InputStream in = null;
		try {
			in = uploadUtil.mvcUpLoad(request);
			// 生成机器特征码
			String machineCode = SoftGrantMachineCodeGenerator.generateMachineCode().toUpperCase();
			System.out.println("===机器特征码 ===" + machineCode);
			authorizeservice.appGrant(in, machineCode,request);
			msg.setSuccess(true);
			msg.setMsg("授权成功，请按F5刷新页面，重新加载菜单");
		} catch (Exception e) {
			msg.setSuccess(false);
			msg.setMsg("授权失败，请重试");
			if (e instanceof BaseException) {
				msg.setSuccess(false);
				msg.setMsg(e.getMessage());
			}
		} finally {
			try {
				in.close();
			} catch (Exception e) {
				log.error(e);
			}
		}
		return msg;
	}

	/**
	 * 自定义应用包--树形数据加载
	 * 
	 * @param modelMap
	 * @return
	 */
	@RequestMapping(value = "customTree", method = { RequestMethod.POST, RequestMethod.GET })
	@ResponseBody
	public List<Tree> customTree(ModelMap modelMap,HttpServletRequest request) {
		String appid =request.getParameter("appid");
		List<Tree> tree = win8uiService.showCusTree(appid);
		String code = request.getParameter("code");
		AppRedit appredit = appreditservice.getAppreditByCustcode(code);
		if (null == appredit || !appid.equals(appredit.getAppid())) { // 不是当前租户
			return tree;
		}
		List<LegalPersonModule> lpa = legalpersonmoduleservice.getCheckModule(code);
		if (lpa != null && !lpa.isEmpty()) {
			for (int i = 0; i < tree.size(); i++) {
				for (int j = 0; j < lpa.size(); j++) {
					// if (lpa.get(j).get("APPID").equals(tree.get(i).getpId())
					// && lpa.get(j).get("APPMODELID")
					if (lpa.get(j).getAppmodelid().equals(tree.get(i).getId())) {
						tree.get(i).setChecked(true);
						break;
					}
				}
			}
		}
		return tree;
	}
}