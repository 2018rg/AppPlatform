package com.cn.webApp.service;

import java.util.List;

import com.cn.webApp.model.AppResourcePackage;
import com.cn.webApp.tree.model.Tree;

public interface AppResourcePackageService {

	int deleteByPrimaryKey(String id) throws Exception;

	int insert(AppResourcePackage record) throws Exception;

	int insertSelective(AppResourcePackage record) throws Exception;

	AppResourcePackage selectByPrimaryKey(String id) throws Exception;

	int updateByPrimaryKeySelective(AppResourcePackage record) throws Exception;

	int updateByPrimaryKey(AppResourcePackage record) throws Exception;
    /**
     * 查询所有
     * @return
     */
	List<AppResourcePackage> queryAll() throws Exception;
	/**
	 * 查询符合resourcepackagename的集合
	 * @param name
	 * @return
	 */
	List<AppResourcePackage> queryByName(String name) throws Exception;
	
	/**
	 * 创建资源包的Tree
	 * @return
	 */
	List<Tree> CreatTree() throws Exception;
}
