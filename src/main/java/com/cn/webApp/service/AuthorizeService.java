package com.cn.webApp.service;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cn.webApp.model.AppModule;
import com.cn.webApp.model.AppRedit;
import com.cn.webApp.model.Authorize;

public interface AuthorizeService {

	int deleteByPrimaryKey(String id);

	int insert(Authorize record);

	/**
	 * 保存软件授权，保存时，会自动计算CRC校验码，存入库中，防篡改
	 * 
	 * @param authorize
	 */
	int insertSelective(Authorize record);

	Authorize selectByPrimaryKey(String id);

	int updateByPrimaryKeySelective(Authorize record);

	int updateByPrimaryKey(Authorize record);

	/**
	 * 应用授权
	 */
	public void appGrant(InputStream in, String machineCode,HttpServletRequest request);

	void saveOrUpdate(Authorize paramT, AppRedit app, AppModule module);
	
	void saveOrUpdate(Authorize paramT);

	/**
	 * 获得所有软件授权，或根据CRC校验码，将被篡改的数据之间过滤掉
	 * 
	 * @return
	 */
	List<Authorize> getAll();

	Authorize selectBySelective(Authorize record);

}
