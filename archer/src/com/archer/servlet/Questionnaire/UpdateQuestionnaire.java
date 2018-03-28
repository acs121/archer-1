package com.archer.servlet.Questionnaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.archer.Utils.QuestionnaireUtils;
import com.archer.dao.QuestionnaireDao;
import com.archer.model.Questionnaire;

/**
 * 更新问卷表
 * Servlet implementation class UpdateQuestionnaire
 */
@WebServlet("/user/updateQuestionnaire")
public class UpdateQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/json");
		Questionnaire q=new Questionnaire();
		q.setUsername((String) request.getSession().getAttribute("username"));
		q.setQuestionnaire_name(request.getParameter("title"));
		q.setQuestionnaire_content(request.getParameter("content"));
		q.setQuestionnaire_id(request.getParameter("id"));
		//验证content是否合法
		if(QuestionnaireUtils.ContentFormatIsRight(q.getQuestionnaire_content())&&q.getQuestionnaire_name()!=null&&q.getQuestionnaire_id()!=null){//content合法
			if(QuestionnaireDao.updateQuestionnaire(q)){//操作成功
				response.getWriter().print("{'status':true}");
			}else{//问卷ID不正确
				response.getWriter().print("{'status':false}");
			}
		}else{
			response.getWriter().print("{'status':false}");
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
