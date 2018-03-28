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
 * ������֤��
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
		if(type!=null){//��֤�����ͣ�Ŀǰֻ���������ٵ�¼��ע��
			code=new ValiImg();//������֤��ͼƬ
			if(type.equals("loginCode")){
				request.getSession().setAttribute(type, code.getAuthCode());//д��������֤���ֵ�����ж��û��Ƿ�������ȷ
			}
			if(type.equals("registCode")){
				request.getSession().setAttribute(type, code.getAuthCode());
			}
		IOUtils.InStreamToOutStream(code.getIn(), response.getOutputStream());//����֤��ͼƬд����Ӧ��
		}
		if(code!=null)code.getIn().close();//�ر���
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}