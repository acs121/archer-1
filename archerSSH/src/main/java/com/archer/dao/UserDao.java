package com.archer.dao;


import com.archer.core.dao.BaseDao;
import com.archer.model.User;

public interface UserDao extends BaseDao<User>{
	public long getSum();
}
