package com.archer.servlet.Questionnaire;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.archer.dao.QuestionnaireDao;
import com.archer.model.Questionnaire;

import net.sf.json.JSONArray;

/**
 * 根据用户名查询数据库得出制作的问卷列表
 * 访问url:/user/getQuestionnaireList
 * 参数:isDelete
 * Servlet implementation class GetQuestionnaireList
 */
@WebServlet("/user/getQuestionnaireList")
public class GetQuestionnaireList extends HttpServlet {
	private static final long serialVersionUID = 1L;
      

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		boolean isDelete=false;
		String username=(String) request.getSession().getAttribute("username");
		if(request.getParameter("isDelete")!=null&&request.getParameter("isDelete").equals("true")){
			isDelete=true;
		}
		List<Questionnaire> list=QuestionnaireDao.getQuestionnaireList(username,isDelete);
		JSONArray json=JSONArray.fromObject(list);
		response.getWriter().print(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
