package com.cn.webApp.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cn.webApp.common.MD5Util;
import com.cn.webApp.common.StringUtil;
import com.cn.webApp.dao.IUserDao;
import com.cn.webApp.model.User;
import com.cn.webApp.service.UserService;
@Service("userservice")
@Transactional
public class UserServiceImpl implements UserService {
private IUserDao iuserdao;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(User record) {
        return 0;
    }

    @Override
    public int insertSelective(User record) {
        return 0;
    }

    @Override
    public User selectByPrimaryKey(String id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }
}
