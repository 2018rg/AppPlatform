/**
 *
 */
package com.cn.webApp.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cn.webApp.model.AppModule;
import com.cn.webApp.model.AppRedit;
import com.cn.webApp.model.Menu;
import com.cn.webApp.model.User;
import com.cn.webApp.service.AppModuleService;
import com.cn.webApp.service.AppReditService;
import com.cn.webApp.service.MenuService;
import com.cn.webApp.service.Win8UIService;
import com.cn.webApp.tree.model.Tree;




/**
 * @author ocean
 * @date : 2014-4-22 上午11:14:57
 * @email : zhangjunfang0505@163.com
 * @Copyright :  zhengzhou
 */
@Service("win8uiService")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Transactional()
public class Win8UIServiceImpl implements Win8UIService, Serializable {

	private static final long serialVersionUID = 681398034096834898L;
	
	@Resource
	private AppReditService appreditservice;
	
	@Resource
	private AppModuleService appmodulservice;
	
	@Resource
    private MenuService menuservice;
	
	@Override
	public List<AppModule> findAllModule(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Menu> findAllMenu() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Object> showTree() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Tree> showCusTree(String appid) {
		List<Tree> trees = new ArrayList<Tree>(500);
		List<AppRedit> appredits = findStandardBusinessAppredit(appid);
		changBusinessAppreditToTree(appredits, trees);
		List<AppModule> modules = appmodulservice.findModuleByStandard(appid);
		changModuleToTree(modules, trees);
		return trees;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<Tree> showRoleTree(String cuscode) {
		List<Tree> trees = new ArrayList<Tree>(500);
	    List<AppRedit> appredits = findStandardBusinessAppredit_role(cuscode);
		if(appredits==null||appredits.isEmpty()){
			return trees;
		}
		changBusinessAppreditToTree(appredits, trees);
		List<AppModule> modules = appmodulservice.findBaseModule(cuscode);
		if(modules==null||modules.isEmpty()){
			return trees;
		}
		changModuleToTree(modules, trees);
		List<Menu> menus = menuservice.selectByModuleId(cuscode);
		changMenuToTree(menus, trees);
		return trees;
	}

	@Override
	public List<AppRedit> findStandardBusinessAppredit(String appid) {
		
		return appreditservice.findStandardBusinessAppredit(appid);
	}

	@Override
	public List<Menu> findAllMenu(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppRedit> findStandardBusinessAppredit_role(String cuscode) {
		List<AppRedit> appredits=appreditservice.getAppreditByCustcode2(cuscode);
		return appredits;
	}
	
	/**
	 * @param findStandardBusinessAppredit
	 * @param trees
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	private void changBusinessAppreditToTree(List<AppRedit> findStandardBusinessAppredit,
			List<Tree> trees) {
		if (null == findStandardBusinessAppredit
				|| findStandardBusinessAppredit.size() == 0) {
			return;
		} else {
			Tree subTree = null;
			for (AppRedit appredit : findStandardBusinessAppredit) {
				subTree = new Tree(appredit.getAppid(),
						"0", appredit.getAppname(), "",
						null);
				trees.add(subTree);
			}
		}
	}
	/**
	 * @param modules
	 * @param trees
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
			private void changModuleToTree(List<AppModule> modules, List<Tree> trees) {
		if (null == modules || modules.size() == 0) {
			return;
		} else {

			Tree subTree = null;
			for (AppModule module : modules) {
				subTree = new Tree(module.getModuleid(), module.getAppid(),
						module.getModulename(), "", null);
				trees.add(subTree);
			}
		}
	}

	/**
	 * @param menus
	 * @param trees
	 */
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	private void changMenuToTree(List<Menu> menus, List<Tree> trees) {
		if (null == menus || menus.size() == 0) {
			return;
		} else {
			Tree subTree = null;
			for (Menu menu : menus) {
				if(StringUtils.isBlank(menu.getParentmenuid())){
					subTree = new Tree(menu.getMenuid(), menu.getModuleid(),
							menu.getMenuname(), "", null);
				}else{
					subTree = new Tree(menu.getMenuid(), menu.getParentmenuid(),
							menu.getMenuname(), "", null);
				}
				trees.add(subTree);
			}
		}

	}
}
