/**
 *
 */
package com.cn.webApp.service;

import java.util.List;

import com.cn.webApp.model.AppModule;
import com.cn.webApp.model.AppRedit;
import com.cn.webApp.model.Menu;
import com.cn.webApp.model.User;
import com.cn.webApp.tree.model.Tree;



/**
 *
 * @Description :
 * @author : ocean
 * @date : 2014-5-15 上午09:49:18
 * @email : zhangjunfang0505@163.com
 * @Copyright :  zhengzhou
 */
public interface Win8UIService {

	List<AppModule> findAllModule(User user);

	List<Menu> findAllMenu();

	List<Object> showTree();

	List<Tree> showCusTree(String appid);
	
	List<Tree> showRoleTree(String cuscode);

	List<AppRedit> findStandardBusinessAppredit(String appid);

	List<Menu> findAllMenu(User user);

	List<AppRedit> findStandardBusinessAppredit_role(String cuscode);

}
