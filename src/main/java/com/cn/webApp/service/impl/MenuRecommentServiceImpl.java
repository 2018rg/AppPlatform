package com.cn.webApp.service.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.cn.webApp.dao.IMenuRecommendDao;
import com.cn.webApp.model.Menu;
import com.cn.webApp.model.MenuRecommend;
import com.cn.webApp.service.MenuRecommentService;
import com.cn.webApp.service.MenuService;

@Service("menurecommentserviceimpl")
public class MenuRecommentServiceImpl implements MenuRecommentService {
	@Resource
	private IMenuRecommendDao imenurecommenddao;
	@Resource
	private MenuService menuservice;

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public int deleteByPrimaryKey(String id) {
		return imenurecommenddao.deleteByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public int insert(MenuRecommend record) {
		return imenurecommenddao.insert(record);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public int insertSelective(MenuRecommend record) {
		return imenurecommenddao.insertSelective(record);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public MenuRecommend selectByPrimaryKey(String id) {
		return imenurecommenddao.selectByPrimaryKey(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public int updateByPrimaryKeySelective(MenuRecommend record) {
		return imenurecommenddao.updateByPrimaryKeySelective(record);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public int updateByPrimaryKey(MenuRecommend record) {
		return imenurecommenddao.updateByPrimaryKey(record);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<MenuRecommend> selectByUserId(String id) {
		return imenurecommenddao.selectByUserId(id);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
	public List<MenuRecommend> selectByCustomerunitcode(String id) {
		return imenurecommenddao.selectByCustomerunitcode(id);
	}

	@Override
	public List<Menu> getMenuByRecommend(String userid, String cuscode) {
		// 如果没有找到个人的，就找系统全局默认的
		List<Menu> list = menuservice.userRecommendMenu(userid);
		if (list == null || list.size() == 0) {
			list = menuservice.custcodeRecommendMenu(cuscode);
		}
		return list;
	}

}
