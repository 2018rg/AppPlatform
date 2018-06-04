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

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.webApp.common.HttpUtils;
import com.cn.webApp.common.MD5Util;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.common.page.Page;
import com.cn.webApp.controller.base.BaseController;
import com.cn.webApp.controller.base.Msg;
import com.cn.webApp.model.Legalperson;
import com.cn.webApp.model.ServerApp;
import com.cn.webApp.model.User;
import com.cn.webApp.propertiesConfig.DBProperties;
import com.cn.webApp.service.LegalpersonService;
import com.cn.webApp.service.ServerAppService;

/**
 * 解析菜单url
 * @author rg
 * 下午1:32:28
 */
@Controller
@Scope("prototype")
@RequestMapping(value = "/resolveurl")
@SuppressWarnings("all")
public class ResolveUrlController {

	Logger log = Logger.getLogger(this.getClass());

	/**
	 * 获得客户下所有有效用户信息
	 */
	@RequestMapping("jump")
	@ResponseBody
	public Msg jump(HttpServletRequest request, Msg msg) {

		User user=HttpUtils.getUser(request);
		String empcode = user.getEmpcode();
		String jumpUrl = "";
		String ecode = "";
		String key = "";
		String mp = request.getParameter("url");
		String app = mp.split("\\$")[0];
		String m = mp.split("\\$")[1];
		JSONObject jsonObject;
		try {
			if (app.equals("BT")) {
				jumpUrl = DBProperties.getProperty("bt.jump.url");
				;
				ecode = DBProperties.getProperty("bt.ecode");
				key = DBProperties.getProperty("bt.key");
				;
			}
			Date from = new Date(0);
			Date now = new Date();
			long seconds = Math.round((now.getTime() - from.getTime() + 8 * 3600 * 1000) / 1000);

			String auid_src = user.getEmpcode() + seconds + key;
			String auid = MD5Util.md5(auid_src).toUpperCase();

			// 构建URL
			StringBuffer url = new StringBuffer(jumpUrl);

			if (app.equals("BT")) {
				url.append("?").append("cityschoolcode=").append(ecode);
				url.append("&").append("key=").append(key);
				url.append("&").append("username=").append(empcode);
				url.append("&").append("timestamp=").append(seconds);
				url.append("&").append("auid=").append(auid);
				url.append("&").append("menuid=").append(m);
			}
			jsonObject = new JSONObject();
			jsonObject.put("url", url.toString());
			msg.setData(jsonObject);
			msg.setSuccess(true);
		} catch (Exception e) {
			e.printStackTrace();
			msg.setData("解析url异常");
			msg.setSuccess(false);
		}
		return msg;
	}
}
