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
 * �û�ע��(������֤��˶�)
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
			json.put("CodeIsOK", "false");//��֤�����
			out.print(json.toString());
			return;
		}else{
			json.put("CodeIsOK", "true");//��֤����ȷ
		}
		
		if(user.getUsername()==null||UserDao.findUserByUserName(user.getUsername())!=null){//����û����Ƿ����
				json.put("IsRegist", "false");//�û����Ѵ���,���û���Ϊ�գ�����ע��
				out.print(json.toString());
				return;
		}else{
			json.put("IsRegist", "true");//�û��������ڣ���ע��
		}
		
		if(user.getPassword()==null||user.getNickname()==null){//����û���д��Ϣ�Ƿ�����
			json.put("status", "false");//ע��ʧ�ܣ���Ϣ������
			out.print(json.toString());
		}else{
			UserDao.addUser(user);
			json.put("status", "true");//ע��ɹ�
			out.print(json.toString());
		}
		
		
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(req, resp);
	}
		
}
