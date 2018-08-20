/**
 * @Title:
 * @Package cn..function.platform.controller
 * @Description: TODO(用一句话描述该文件做什么)
 * @author gaochongfei
 * @date 2014-3-28 下午03:03:03
 * @version V1.0
 */
package com.cn.webApp.controller;

import com.cn.webApp.service.UserService;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * 
 * @author rg 下午1:53:02
 */

@Controller
@Scope("prototype")
@RequestMapping(value = "/user")
@SuppressWarnings("all")
public class UserController {
	Logger log=Logger.getLogger(this.getClass());
	@Resource
	private UserService userservice;

}
