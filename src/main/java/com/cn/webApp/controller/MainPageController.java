/**
 *
 */
package com.cn.webApp.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import com.cn.webApp.model.LogLogin;
import com.cn.webApp.model.Menu;
import com.cn.webApp.common.HttpUtils;
import com.cn.webApp.model.Legalperson;
import com.cn.webApp.model.User;
import com.cn.webApp.service.LegalpersonService;
import com.cn.webApp.service.LogLoginService;
import com.cn.webApp.service.MenuRecommentService;

/**
 * 
 * 主页，welcome页
 * @author wtj
 *
 */

@Controller
@RequestMapping(value = "/main")
public class MainPageController {
	
  Logger log = Logger.getLogger(this.getClass());
    @Resource
	private LegalpersonService legalpersonservice;
    @Resource
   	private LogLoginService logloginservice ;
    @Resource
	private MenuRecommentService menurecommentservice;
 
	@RequestMapping(value = "/welcome")
	public ModelAndView loginUI(ModelMap modelMap, HttpServletRequest request,
			HttpServletResponse res) {
		
		//获得用户信息
		User userInfo=HttpUtils.getUser(request);
		modelMap.put("userInfo", userInfo);
		
		//客户信息
		Legalperson person = legalpersonservice.selectByCustomerunitcode(userInfo.getCustomerunitcode());
		modelMap.put("person", person);
		
		//登录日志.
		List<LogLogin> logs = logloginservice.selectByUserId(userInfo.getId());
		modelMap.put("logs", logs);
		
		//推荐功能
		List<Menu> recommendMenus = menurecommentservice.getMenuByRecommend(userInfo.getId(), userInfo.getCustomerunitcode());
		modelMap.put("menus", recommendMenus);
		
		//流程信息
    	/*Map<String,Object> para=new HashMap<String,Object>();
		para.put("customerunitcode", UserContext.getCurrentUser().getCustomerUnitCode());
		PageContext.setPagesize(Integer.MAX_VALUE);
		
		RoleGroup rolegroup = roleGroupService.get(userInfo.getRoleid());
		if(rolegroup !=null && StringUtils.isNotBlank(rolegroup.getRoleids())){
			String[] r = rolegroup.getRoleids().split(",");
			String roleids = "" ;
			for(String role_id : r){
				roleids += "'"+role_id+"',";
			}
			para.put("roleids", roleids.substring(0,roleids.length()-1));
		}
		List<Process> proessses=(List<Process>) processService.getPageList(para).getItems();*/
		modelMap.put("proessses", null);

		
		return new ModelAndView(("foundation/main/welcome"), modelMap);
	}
	
	 
 
}
