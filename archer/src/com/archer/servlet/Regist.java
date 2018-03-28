package com.archer.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.archer.dao.UserDao;
import com.archer.model.User;

import net.sf.json.JSONObject;

@WebServlet("/regist")
/**
 * 用户注册(含有验证码核对)
 * @author guokunjin
 *
 */
public class Regist extends HttpServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setContentType("text/plain");
		PrintWriter out=resp.getWriter();
		JSONObject  json=new JSONObject();
		User user=new User();
		Map<String, String[]> param=req.getParameterMap();
		try {
			BeanUtils.populate(user, param);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(param.isEmpty()?true:!param.get("registCode")[0].equalsIgnoreCase((String) req.getSession().getAttribute("registCode"))){
			json.put("CodeIsOK", "false");//验证码错误
			out.print(json.toString());
			return;
		}else{
			json.put("CodeIsOK", "true");//验证码正确
		}
		
		if(user.getUsername()==null||UserDao.findUserByUserName(user.getUsername())!=null){//检查用户名是否存在
				json.put("IsRegist", "false");//用户名已存在,或用户名为空，不可注册
				out.print(json.toString());
				return;
		}else{
			json.put("IsRegist", "true");//用户名不存在，可注册
		}
		
		if(user.getPassword()==null||user.getNickname()==null){//检测用户填写信息是否完善
			json.put("status", "false");//注册失败，信息不完整
			out.print(json.toString());
		}else{
			UserDao.addUser(user);
			json.put("status", "true");//注册成功
			out.print(json.toString());
		}
		
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
		
}
