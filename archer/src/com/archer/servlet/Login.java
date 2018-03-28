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
 * �û���¼��������֤��˶ԣ�
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
		//�����֤���Ƿ���ȷ
		if(param.isEmpty()?true:!param.get("loginCode")[0].equalsIgnoreCase((String) request.getSession().getAttribute("loginCode"))){
			json.put("CodeIsOK", "false");//��֤�����
			out.print(json.toString());
			return;
		}else{
			json.put("CodeIsOK", "true");//��֤����ȷ
		}
		if(UserDao.findUserByUserName(user.getUsername())==null){//����û����Ƿ����
			json.put("UserIsOK", "false");//�û���������
			out.print(json.toString());
			return;
		}else{
			json.put("UserIsOK", "true");//�û�������
		}
		user=UserDao.findUserByUNandPSW(user.getUsername(), user.getPassword());
		if(user == null){//����û������Ƿ���ȷ
			json.put("status", "false");//���벻��ȷ����¼ʧ��
			out.print(json.toString());
		}else{
			json.put("status", "true");//��½�ɹ�����session����д���û����ǳ�
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
