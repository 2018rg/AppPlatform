/**
 *
 */
package com.cn.webApp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.webApp.common.HttpUtils;
import com.cn.webApp.model.AppModule;
import com.cn.webApp.model.Menu;
import com.cn.webApp.model.User;
import com.cn.webApp.service.AppModuleService;
import com.cn.webApp.service.AppReditService;
import com.cn.webApp.service.MenuService;


/**
 * 
 * 主页菜单初始化对应
 * @author wtj
 *
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/index")
public class IndexMenuController {
    @Resource
	private MenuService menuservice;
    @Resource
    private AppReditService appreditservice;
    @Resource
    private AppModuleService appmoduleservice;
 	/**
	 * 获得模块信息以及根节点菜单信息返回
	 * @return JSON字符串
	 */
	@RequestMapping(value = "loadMenuTree")
	@ResponseBody
	public String loadMenuTreeRoot(HttpServletRequest request,HttpServletResponse response) {
		JSONArray result = new JSONArray();
		response.setContentType("text/xml;charset=utf-8");  
//		//获得当前被授权的应用
		//List<AppRedit> apps = appreditservice.selectByBusinessAppredit();

		//获得被授权的模块信息
		List<AppModule> modules =appmoduleservice.selectByBusinessAppredit();
//		List<Module> modules =moduleService.findAllModule();
		// 目前是获得所有菜单，后期要根据当前用户的权限决定是否显示菜单
		
		//获得当前用户有权限的菜单
		User user=HttpUtils.getUser(request);
		List<Menu> menus = menuservice.findAllUserMenu(user.getEmpcode(),user.getCustomerunitcode());
	
		
		/**
		 * 将菜单与模块，放入map中，用于生成菜单树，根据key获得子节点的所有元素
		 */
		Map<String, List<Menu>> moduleMenuMap = new HashMap<String, List<Menu>>();
		Map<String, List<Menu>> menuParentMap = new HashMap<String, List<Menu>>();
		for (Menu menu : menus) {
			if(user.getIsmaster() == 1 && !menu.getModuleid().startsWith("0501")){//非系统菜单，不显示
				continue;
			}
			if(StringUtils.isBlank(menu.getParentmenuid() )){//根节点
				List<Menu> menuList = moduleMenuMap.get(menu.getModuleid());
				if(menuList == null){
					menuList = new ArrayList<Menu>();
				}
				menuList.add(menu);
				moduleMenuMap.put(menu.getModuleid(), menuList);
			}else{//非根节点
				List<Menu> menuList = menuParentMap.get(menu.getParentmenuid());
				if(menuList == null){
					menuList = new ArrayList<Menu>();
				}
				menuList.add(menu);
				menuParentMap.put(menu.getParentmenuid(), menuList);
			}
		}
		
		/**
		 * 生成菜单树json
		 */
		for (AppModule module : modules) {
			//模块
			JSONObject moduleJson = createMenuObj(module.getId(), "0", module.getModulename(),"","","");
			
			//获得一级菜单信息
			JSONArray rootMenusJson = new JSONArray();
			List<Menu> rootMenus = moduleMenuMap.get(module.getModuleid());
			if(rootMenus == null){
				continue;
			}
			for (Menu rootMenu : rootMenus) {
				JSONObject rootMenuJson = createMenuObj(rootMenu.getId(),
						module.getId(), rootMenu.getMenuname(),"",
						rootMenu.getSmallicon(), rootMenu.getLargeicon());
				 
				//获得子菜单
				List<Menu> sonMenus = menuParentMap.get(rootMenu.getMenuid());
				if(sonMenus == null || sonMenus.size() == 0){
					continue;
				}
				
				rootMenuJson.put("children", getSonMenu(menuParentMap, rootMenu.getMenuid()));
				rootMenusJson.add(rootMenuJson);
			}
			
			moduleJson.put("children", rootMenusJson);
			result.add(moduleJson);
		}
 		return result.toString();
	}
	
	/**
	 * 获得子菜单元素
	 * @param menuMap
	 * @param parentId
	 * @return
	 */
	private JSONArray getSonMenu(Map<String, List<Menu>> menuMap,String parentId){
		JSONArray result = new JSONArray();
		List<Menu> menus = menuMap.get(parentId);
		if(menus != null && menus.size()>0){
			for (Menu menu : menus) {
				JSONObject menuJson =  createMenuObj(menu.getId(),
						parentId, menu.getMenuname(),menu.getNavlink(),
						menu.getSmallicon(), menu.getLargeicon());
				
				/**
				 * 尝试获得对应子菜单
				 */
				List<Menu> sons = menuMap.get(menu.getMenuid());
				if(sons != null && sons.size()>0){
					menuJson =  createMenuObj(menu.getId(),
							parentId, menu.getMenuname(),menu.getNavlink(),
							"", "");
					menuJson.put("children", getSonMenu(menuMap, menu.getMenuid()));
				}
				
				result.add(menuJson);
			}
		}
		return result;
	}
	
	
	/**
	 * 创建菜单项，JSONObject
	 * @param id
	 * @param pid
	 * @param name
	 * @param smallPic
	 * @param bigPic
	 * @return
	 */
	private JSONObject createMenuObj(String id,String pid,String name,String navLink,String smallPic,String bigPic){
		return createMenuObj(id, pid, name, navLink, smallPic, bigPic, "", "");
	}
	
	
	private JSONObject createMenuObj(String id,String pid,String name,String navLink,String smallPic,String bigPic,String openIcon,String closeIcon){
		JSONObject moduleJson = new JSONObject();
		moduleJson.put("moduleId", id);
		moduleJson.put("id", id);
		moduleJson.put("pid", pid);
		moduleJson.put("pModuleId",pid);
		moduleJson.put("moduleName", name);
		moduleJson.put("name", name);
		moduleJson.put("depth", 10);
		moduleJson.put("smallPic",smallPic);
		if(StringUtils.isNotBlank(smallPic)){
			moduleJson.put("icon",smallPic);
		}
		if(StringUtils.isNotBlank(openIcon)){
			moduleJson.put("iconOpen",openIcon);
		}
		if(StringUtils.isNotBlank(closeIcon)){
			moduleJson.put("iconClose",closeIcon);
		}
		moduleJson.put("bigPic", bigPic);
		moduleJson.put("file", navLink);
		return moduleJson;
	}

}
