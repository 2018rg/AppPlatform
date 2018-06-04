package com.cn.webApp.controller.base;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


import com.cn.webApp.common.page.Page;
/**   
 *Title:     
 *Description:     
 *Copyright: Copyright (c) 2011   
 *Company:http://liuzidong.iteye.com/    
 *Makedate:2011-5-23 下午03:31:03   
 * @author liuzidong   
 * @version 1.0   
 * @since 1.0    
 *   
 */
public class BaseController {
	@Resource
	HttpServletRequest request;
	
	/**
	 * oracel的三层分页语句	
	 * 子类在展现数据前,进行分页计算!
	 * @param querySql  查询的SQL语句,未进行分页
	 * @param totalCount 根据查询SQL获取的总条数
	 * @param columnNameDescOrAsc 列名+排序方式 : ID DESC or ASC
	 */
	protected Page executePage(HttpServletRequest request,Long totalCount,int intPage,int number){
		Page page=new Page();
		//总数(同时也设置了总页数)
		page.setTotalCount(totalCount);
		//当前页
		page.setCurrentPage(new Long(intPage));
		//每页多少条
		page.setEveryPage(new Long(number));
		
		request.getSession().setAttribute("page", page);
		return page;
	}
}
