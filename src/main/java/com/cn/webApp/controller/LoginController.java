package com.cn.webApp.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.webApp.common.HttpUtils;
import com.cn.webApp.common.MD5Util;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.common.ValidateCode;
import com.cn.webApp.controller.base.BaseController;
import com.cn.webApp.model.Legalperson;
import com.cn.webApp.model.LogLogin;
import com.cn.webApp.model.User;
import com.cn.webApp.service.LegalpersonService;
import com.cn.webApp.service.LogLoginService;
import com.cn.webApp.service.UserService;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {
	@Resource
	private UserService userserviceimpl;
	@Resource
	private LogLoginService logloginservice;
	@Resource
	private LegalpersonService legalpersonservice;

	Logger log = Logger.getLogger(this.getClass());

	// 登录验证
	@RequestMapping(value = "/login")
	public ModelAndView login(ModelMap modelMap, User user, HttpServletRequest request) {
		User sessionuser = HttpUtils.getUser(request);;
		if (sessionuser != null) {
			return new ModelAndView("foundation/win8/pagelet/v2.0/indexUI", modelMap);
		}
		String view = "foundation/login/index";
		try {
			user.setEmppwd(MD5Util.md5(user.getEmppwd()));
			List<User> list = userserviceimpl.selectBySelective(user);
			Legalperson legalperson = legalpersonservice.selectByCustomerunitcode(user.getCustomerunitcode());
			String code = request.getParameter("code");
			String realCode = (String) request.getSession().getAttribute("code");
			if (legalperson.getStatus() != 1) {
				if (list.size() == 1) {
					if (realCode.trim().equals(code.trim())) {
						request.getSession().setAttribute("user", list.get(0));
						LogLogin loglogin = new LogLogin();
						loglogin.setId(StringUtil.getUUID());
						loglogin.setUserId(list.get(0).getId());
						loglogin.setCustomerunitcode(list.get(0).getCustomerunitcode());
						loglogin.setLogType(0);
						loglogin.setLogLimitType(0);
						loglogin.setIp(HttpUtils.getIpAddr(request));
						loglogin.setOccurTime(new Date());
						logloginservice.insert(loglogin);
						view = "foundation/win8/pagelet/v2.0/indexUI";
					} else {
						modelMap.put("msg", "验证码输入错误");

					}
				} else {
					modelMap.put("msg", "账号或密码输入错误");
				}
			} else {
				modelMap.put("msg", "该租户未授权");
			}

		} catch (Exception e) {
			modelMap.put("msg", "登陆失败");
			log.error(e);
		}

		return new ModelAndView(view, modelMap);
	}

	// 主页面
	@RequestMapping("/index")
	public ModelAndView index(HttpServletRequest request) {
		User user = HttpUtils.getUser(request);;
		ModelAndView mv = new ModelAndView();
		if (user != null) {
			mv.setViewName("redirect:login");
		} else {
			mv.setViewName("foundation/login/index");
		}
		return mv;
	}

	// 验证码
	@RequestMapping("/img")
	public void img(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ValidateCode.outputImg(request, response);
	}

	// 用户退出
	@RequestMapping("/logout")
	public ModelAndView logout(HttpServletRequest request) {
		User user = HttpUtils.getUser(request);;
		if (user != null) {
			request.getSession().removeAttribute("user");
		}
		return new ModelAndView("redirect:index", null);
	}
}
