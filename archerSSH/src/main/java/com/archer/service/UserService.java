package com.archer.service;

import com.archer.core.service.BaseService;
import com.archer.model.User;

public interface UserService extends BaseService<User>{
	public long getSum();
	public boolean existUser();
}
