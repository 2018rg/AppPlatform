package com.cn.webApp.dao;

import java.util.List;

import com.cn.webApp.model.Authorize;



public interface IAuthorizeDao {
	
    int deleteByPrimaryKey(String id);

    int insert(Authorize record);

    int insertSelective(Authorize record);

    Authorize selectByPrimaryKey(String id);
    
    List<Authorize> selectBySelective(Authorize record);

    int updateByPrimaryKeySelective(Authorize record);

    int updateByPrimaryKey(Authorize record);
    
    // 获得所有Authorize
	List<Authorize> getAll();
}