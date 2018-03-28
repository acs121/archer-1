package com.archer.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.archer.Utils.IOUtils;
import com.archer.Utils.ValiImg;


/**
 * 生成验证码
 * @author guokunjin
 *
 */
@WebServlet("/codeImg")
public class CodeValiImg  extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		response.setContentType("image/jpeg");
		String type=request.getParameter("type");
		ValiImg code=null;
		if(type!=null){//验证码类型：目前只用于两处①登录②注册
			code=new ValiImg();//生成验证码图片
			if(type.equals("loginCode")){
				request.getSession().setAttribute(type, code.getAuthCode());//写入生成验证码的值用于判断用户是否输入正确
			}
			if(type.equals("registCode")){
				request.getSession().setAttribute(type, code.getAuthCode());
			}
		IOUtils.InStreamToOutStream(code.getIn(), response.getOutputStream());//将验证码图片写入响应中
		}
		if(code!=null)code.getIn().close();//关闭流
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}