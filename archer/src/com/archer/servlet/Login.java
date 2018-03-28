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

/**
 * 
 * 用户登录（含有验证码核对）
 * Servlet implementation class LoginAction
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		response.setContentType("text/plain");
		PrintWriter out=response.getWriter();
		JSONObject  json=new JSONObject();
		User user=new User();
		Map<String, String[]> param=request.getParameterMap();
		try {
			BeanUtils.populate(user, param);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//检查验证码是否正确
		if(param.isEmpty()?true:!param.get("loginCode")[0].equalsIgnoreCase((String) request.getSession().getAttribute("loginCode"))){
			json.put("CodeIsOK", "false");//验证码错误
			out.print(json.toString());
			return;
		}else{
			json.put("CodeIsOK", "true");//验证码正确
		}
		if(UserDao.findUserByUserName(user.getUsername())==null){//检查用户名是否存在
			json.put("UserIsOK", "false");//用户名不存在
			out.print(json.toString());
			return;
		}else{
			json.put("UserIsOK", "true");//用户名存在
		}
		user=UserDao.findUserByUNandPSW(user.getUsername(), user.getPassword());
		if(user == null){//检测用户密码是否正确
			json.put("status", "false");//密码不正确，登录失败
			out.print(json.toString());
		}else{
			json.put("status", "true");//登陆成功，在session里面写入用户和昵称
			out.print(json.toString());
			request.getSession().setAttribute("username", user.getUsername());
			request.getSession().setAttribute("nickname", user.getNickname());
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
