package com.archer.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archer.core.service.impl.BaseServiceImpl;
import com.archer.dao.UserDao;
import com.archer.model.User;
import com.archer.service.UserService;
@Service("userService")
public class UserServiceimpl extends BaseServiceImpl<User> implements UserService{
	private UserDao userDao;
	@Resource
	public void setUserDao(UserDao userDao) {
		super.setBaseDao(userDao);
		this.userDao = userDao;
	}
	@Override
	public long getSum() {
		return userDao.getSum();
	}
	@Override
	public boolean existUser() {
		return false;
	}
}
