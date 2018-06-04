package com.cn.webApp.common;

import javax.servlet.http.HttpServletRequest;

import com.cn.webApp.model.User;

/**
 * 
 * @author wtj
 * 
 */
public class HttpUtils {

	/**
	 * 获得客户端IP
	 * 
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}
   /**
    * 获取session中当前登录对象
    * 
    * @param request
    * @return
    */
	public static User getUser(HttpServletRequest request) {
		User user=(User)request.getSession().getAttribute("user");
		if (user != null) {
			return user;
		}
		return null;
	}

}
