package com.cn.webApp.service;

import java.util.List;

import com.cn.webApp.model.Menu;
import com.cn.webApp.model.MenuRecommend;

public interface MenuRecommentService {
	
	int deleteByPrimaryKey(String id);

	int insert(MenuRecommend record);

	int insertSelective(MenuRecommend record);

	MenuRecommend selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(MenuRecommend record);

	int updateByPrimaryKey(MenuRecommend record);

	List<MenuRecommend> selectByUserId(String id);

	List<MenuRecommend> selectByCustomerunitcode(String id);
	
	List<Menu> getMenuByRecommend(String userid,String cuscode);
}
