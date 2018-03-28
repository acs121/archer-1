package com.archer.servlet.Questionnaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.archer.dao.QuestionnaireDao;
import com.archer.model.Questionnaire;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class ChangeStatus
 */
@WebServlet("/user/changeStatus")
public class ChangeStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/json");
		String questionnaire_id=request.getParameter("id");
		String username=(String) request.getSession().getAttribute("username");
		JSONObject json=new JSONObject();
		
		Questionnaire q=QuestionnaireDao.getQuestionnaireByIdAndUser(username, questionnaire_id);
		if(q!=null&&q.getStatus()!=2){//状态码加1
			if(q.getStatus()<2){//加1
				QuestionnaireDao.updateStatus(username, questionnaire_id, q.getStatus()+1);
			}else{//修改为2
				QuestionnaireDao.updateStatus(username, questionnaire_id, 2);
			}
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
