package com.archer.servlet.Questionnaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.archer.Utils.QuestionnaireUtils;
import com.archer.dao.QuestionnaireDao;
import com.archer.model.Questionnaire;
/**
 * 创建问卷
 * @author guokunjin
 *
 */
@WebServlet("/user/createQuestionnaire")
public class CreateQuestionnaire extends HttpServlet {
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
		JSONObject json=new JSONObject();
		//验证content是否合法
		if(QuestionnaireUtils.ContentFormatIsRight(q.getQuestionnaire_content())&&q.getQuestionnaire_name()!=null){//content合法
			QuestionnaireDao.createQuestionnaire(q);
			json.put("status", true);
		}else{
			json.put("status", false);
		}
		response.getWriter().print(json.toString());
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
