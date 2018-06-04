package com.cn.webApp.service.impl;

import com.cn.webApp.cache.Cache;
import com.cn.webApp.dao.IAppResourcePackageDao;
import com.cn.webApp.model.AppResourcePackage;
import com.cn.webApp.service.AppResourcePackageService;
import com.cn.webApp.tree.model.Tree;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("appresourcepackageservice")
@Transactional
public class AppResourcePackageServiceImpl implements AppResourcePackageService {

	@Resource
	private IAppResourcePackageDao iappresourcepackagedao;
	
	@Resource
	Cache cache;

	@Override
	public int deleteByPrimaryKey(String id) throws Exception{
		int result=iappresourcepackagedao.deleteByPrimaryKey(id);
		if(result>0){
			cache.delete("app_resourcepackage_"+id);
		}
		return result;
	}

	@Override
	public int insert(AppResourcePackage record) throws Exception{
		int result = iappresourcepackagedao.insert(record);
		if(result > 0){
			cache.set("app_resourcepackage_"+record.getId(), record.toString());
		}
		return result;
	}

	@Override
	public int insertSelective(AppResourcePackage record) throws Exception{
		int result = iappresourcepackagedao.insertSelective(record);
		if(result > 0){
			cache.set("app_resourcepackage_"+record.getId(), record.toString());
		}
		return result;
	}

	@Override
	public AppResourcePackage selectByPrimaryKey(String id) throws Exception{
		AppResourcePackage record= iappresourcepackagedao.selectByPrimaryKey(id);
		if(record!=null){
			cache.set("app_resourcepackage_"+record.getId(), record.toString());
		}
		return record;
	}

	@Override
	public int updateByPrimaryKeySelective(AppResourcePackage record) throws Exception{
		int result= iappresourcepackagedao.updateByPrimaryKeySelective(record);
		AppResourcePackage newrecord=iappresourcepackagedao.selectByPrimaryKey(record.getId());
		if(result>0&&newrecord!=null){
			cache.set("app_resourcepackage_"+record.getId(), newrecord.toString());
		}
		return result;
	}

	@Override
	public int updateByPrimaryKey(AppResourcePackage record) throws Exception{
		int result= iappresourcepackagedao.updateByPrimaryKey(record);
		if(result>0){
			cache.set("app_resourcepackage_"+record.getId(), record.toString());
		}
		return result;
	}

	@Override
	public List<AppResourcePackage> queryAll() throws Exception{
		return iappresourcepackagedao.queryAll();
	}

	@Override
	public List<AppResourcePackage> queryByName(String name) throws Exception {
		return iappresourcepackagedao.queryByName(name);
	}

	@Override
	public List<Tree> CreatTree() throws Exception {
		List<Tree> trees = new ArrayList<Tree>(500);
		List<AppResourcePackage> list=queryAll();
		Tree roottree=new Tree("1","0","资源包","",null);
		trees.add(roottree);
		Tree subTree = null;
		for (AppResourcePackage a : list) {
			subTree = new Tree(a.getId(), "1",a.getResourcepackagename(), "", null);
			trees.add(subTree);
		}
		return trees;
	}

	

}
