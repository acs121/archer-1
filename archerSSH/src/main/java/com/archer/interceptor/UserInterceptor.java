package com.archer.interceptor;

import com.archer.model.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class UserInterceptor extends MethodFilterInterceptor {

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		User u=(User) ActionContext.getContext().getSession().get("user");
		if(u==null) {
			return "index";
		}else {
			return invocation.invoke();
		}
	}

}
