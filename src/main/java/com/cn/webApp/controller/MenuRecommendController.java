
package com.cn.webApp.controller;

import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import net.sf.json.JSONArray;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.webApp.common.HttpUtils;
import com.cn.webApp.controller.base.Msg;
import com.cn.webApp.model.Menu;
import com.cn.webApp.model.User;
import com.cn.webApp.service.MenuRecommentService;


/**
 * 推荐菜单
 * 
 * @author rg 上午11:41:18
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/recmommendMenu")
public class MenuRecommendController {

	@Resource
	private MenuRecommentService menurecommentservice;
	/**
	 * 系统推荐菜单设置入口
	 * 
	 * @param modelMap
	 *            map模型
	 * @return ModelAndView
	 */
	/*
	 * @RequestMapping(value = "system") public ModelAndView system(ModelMap
	 * modelMap) { List<Module> list =
	 * moduleService.findLegalModule(UserContext.getCurrentUser().
	 * getCustomerUnitCode()); modelMap.put("moduleList", list); return
	 * toView(getUrl("platform.menuManage.recommend.system"), modelMap); }
	 * 
	 *//**
		 * 个人推荐菜单设置入口
		 * 
		 * @param modelMap
		 *            map模型
		 * @return ModelAndView
		 */
	/*
	 * @RequestMapping(value = "person") public ModelAndView person(ModelMap
	 * modelMap) { List<Module> list =
	 * moduleService.findLegalModule(UserContext.getCurrentUser().
	 * getCustomerUnitCode()); modelMap.put("moduleList", list); return
	 * toView(getUrl("platform.menuManage.recommend.person"), modelMap); }
	 * 
	 *//**
		 * 系统推荐菜单设置保存
		 * 
		 * @return Msg
		 */
	/*
	 * @RequestMapping(value = "saveSystem", method = RequestMethod.POST)
	 * 
	 * @ResponseBody public Msg saveSystem(final HttpServletRequest request) {
	 * return doExpAssert(new AssertObject() {
	 * 
	 * @Override public void AssertMethod(Msg msg) { Assert.isTrue(null !=
	 * getJsonObject(), "参数传入失败！"); String moduleid =
	 * getJsonObject().getString("module"); String[] menuids=
	 * getJsonObject().getString("menuids").split(","); UserEntity user =
	 * UserContext.getCurrentUser(); //先执行清洗操作
	 * recommendService.delByCustomerOrUser(user.getCustomerUnitCode(),
	 * null,"1",moduleid);
	 * 
	 * //保存 MenuRecommend recommend; int i=0; for(String menuid:menuids){
	 * if(StringUtils.isNotBlank(menuid)){ recommend=new MenuRecommend();
	 * recommend.setCustomerunitcode(user.getCustomerUnitCode());
	 * recommend.setEdit_date(Calendar.getInstance().getTime());
	 * recommend.setMenu_id(menuid); recommend.setModule_id(moduleid);
	 * recommend.setSortid(String.valueOf(i)); recommend.setType("1");
	 * recommend.setUser_id(user.getId()); i++;
	 * recommendService.saveOrUpdate(recommend); } } msg.setMsg("保存成功！"); } });
	 * }
	 * 
	 *//**
		 * 个人推荐菜单设置保存
		 * 
		 * @return Msg
		 *//*
		 * @RequestMapping(value = "savePerson", method = RequestMethod.POST)
		 * 
		 * @ResponseBody public Msg savePerson(final HttpServletRequest request)
		 * { return doExpAssert(new AssertObject() {
		 * 
		 * @Override public void AssertMethod(Msg msg) { Assert.isTrue(null !=
		 * getJsonObject(), "参数传入失败！"); String moduleid =
		 * getJsonObject().getString("module"); String[] menuids=
		 * getJsonObject().getString("menuids").split(","); UserEntity user =
		 * UserContext.getCurrentUser(); //先执行清洗操作
		 * recommendService.delByCustomerOrUser(null,user.getId(),"2",moduleid);
		 * 
		 * //保存 MenuRecommend recommend; int i=0; for(String menuid:menuids){
		 * if(StringUtils.isNotBlank(menuid)){ recommend=new MenuRecommend();
		 * recommend.setCustomerunitcode(user.getCustomerUnitCode());
		 * recommend.setEdit_date(Calendar.getInstance().getTime());
		 * recommend.setMenu_id(menuid); recommend.setModule_id(moduleid);
		 * recommend.setSortid(String.valueOf(i)); recommend.setType("2");
		 * recommend.setUser_id(user.getId()); i++;
		 * recommendService.saveOrUpdate(recommend); } } msg.setMsg("保存成功！"); }
		 * }); }
		 */
	/**
	 * ajax获取系统推荐菜单列表
	 * 
	 * @return Msg
	 */
	/*
	 * @RequestMapping(value =
	 * "ajaxGetSysRecommendMenu",method=RequestMethod.POST)
	 * 
	 * @ResponseBody public Msg ajaxGetSysRecommendMenu() { return
	 * doExpAssert(new AssertObject() {
	 * 
	 * @Override public void AssertMethod(Msg msg) { Assert.isTrue(null !=
	 * getJsonObject() && getJsonObject().has("module") &&
	 * (!StringUtils.isBlank(getJsonObject().getString("module"))), "传入参数失败！");
	 * String moduleid=getJsonObject().getString("module");
	 * moduleid=moduleService.getModule(moduleid).getModuleid(); UserEntity user
	 * = UserContext.getCurrentUser();
	 * PageContext.setPagesize(Integer.MAX_VALUE); List<MenuRecommend>
	 * listRecommend=recommendService.getSystemMenuRecommend(user.
	 * getCustomerUnitCode()); List<Tree>
	 * list=recommendService.getTreeByRecommend(listRecommend,
	 * user.getCustomerUnitCode(), null,moduleid);
	 * msg.setData(JSONArray.fromObject(list).toString()); } }); }
	 * 
	 *//**
		 * ajax获取个人推荐菜单列表
		 * 
		 * @return Msg
		 *//*
		 * @RequestMapping(value =
		 * "ajaxGetPersonRecommendMenu",method=RequestMethod.POST)
		 * 
		 * @ResponseBody public Msg ajaxGetPersonRecommendMenu() { return
		 * doExpAssert(new AssertObject() {
		 * 
		 * @Override public void AssertMethod(Msg msg) { Assert.isTrue(null !=
		 * getJsonObject() && getJsonObject().has("module") &&
		 * (!StringUtils.isBlank(getJsonObject().getString("module"))),
		 * "传入参数失败！"); String moduleid=getJsonObject().getString("module");
		 * moduleid=moduleService.getModule(moduleid).getModuleid(); UserEntity
		 * user = UserContext.getCurrentUser();
		 * PageContext.setPagesize(Integer.MAX_VALUE); List<MenuRecommend>
		 * listRecommend=recommendService.getPersonMenuRecommend(user.getId());
		 * List<Tree> list=recommendService.getTreeByRecommend(listRecommend,
		 * null, user.getId(),moduleid);
		 * msg.setData(JSONArray.fromObject(list).toString()); } }); }
		 */

	/**
	 * ajax获取推荐菜单列表(顶部导航用)
	 * 
	 * @return Msg
	 */
	@RequestMapping(value = "ajaxGetMenuByRecommend", method = RequestMethod.POST)
	@ResponseBody
	public Msg ajaxGetMenuByRecommend(HttpServletRequest request) {
		Msg msg = new Msg();
		User user = HttpUtils.getUser(request);;
		List<Menu> recommendMenus = menurecommentservice.getMenuByRecommend(user.getId(), user.getCustomerunitcode());
		if (recommendMenus != null && recommendMenus.size() > 0) {
			msg.setData(JSONArray.fromObject(recommendMenus).toString());
			msg.setSuccess(true);
		} else {
			msg.setData("");
			msg.setSuccess(false);
		}
		return msg;
	}
}
