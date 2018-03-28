package com.archer.dao.impl;


import org.springframework.stereotype.Component;

import com.archer.core.impl.BaseDaoImpl;
import com.archer.dao.UserDao;
import com.archer.model.User;
@Component("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao{

	@Override
	public long getSum() {
		return (Long) currentSession().createQuery("select count(*) from User").uniqueResult();
	}
	
	
}
