package com.cn.webApp.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.cn.webApp.dao.IDataSourceDao;
import com.cn.webApp.model.DataSource;
import com.cn.webApp.service.DataSourceService;
/**
 * 
 * @author rg
 * 上午10:20:17
 */
@Service("datasourceservice")
public class DataSourceServiceImpl implements DataSourceService {
	
	@Resource
	private IDataSourceDao idatasourcedao;

	@Override
	public int deleteByPrimaryKey(String id) {
		return idatasourcedao.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(DataSource record) {
		return idatasourcedao.insert(record);
	}

	@Override
	public int insertSelective(DataSource record) {
		return idatasourcedao.insertSelective(record);
	}

	@Override
	public DataSource selectByPrimaryKey(String id) {
		return idatasourcedao.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(DataSource record) {
		return idatasourcedao.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(DataSource record) {
		return idatasourcedao.updateByPrimaryKey(record);
	}

	@Override
	public List<DataSource> queryAllResource(String appid) {
		return idatasourcedao.queryAllResource(appid);
	}

	@Override
	public List<DataSource> findAll() {
		return idatasourcedao.findAll();
	}

	@Override
	public void saveOrUpdate(DataSource datasource) {
		DataSource record=idatasourcedao.selectByPrimaryKey(datasource.getId());
		if (record != null) {
			idatasourcedao.updateByPrimaryKey(datasource);
		}else{
			idatasourcedao.insertSelective(datasource);
		}
		
	}

	@Override
	public List<DataSource> queryByAppid(DataSource record) {
		return idatasourcedao.queryByAppid(record);
	}

	@Override
	public int updateCusCodeEffective(String customerunitcode) {
		return idatasourcedao.updateCusCodeEffective(customerunitcode);
	}

}
