package com.archer.servlet.Questionnaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.archer.dao.AnswerDao;
import com.archer.dao.QuestionnaireDao;
import net.sf.json.JSONObject;;

/**
 * É¾³ýÎÊ¾í±í
 * Servlet implementation class UpdateQuestionnaire
 */
@WebServlet("/user/deleteQuestionnaire")
public class DeleteQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/json");	
		JSONObject  json =new JSONObject();
		String username=(String)request.getSession().getAttribute("username");
		String questionnaire_id=request.getParameter("id");
		String isRecycle=request.getParameter("isRecycle");
		if(isRecycle!=null){
			if(isRecycle.equals("true")){
				QuestionnaireDao.recycleQuestionnaire(username, questionnaire_id);//ÉèÖÃ×´Ì¬Âë3
				json.put("status", true);
			}
			if(isRecycle.equals("false")){//Ö±½ÓÉ¾³ý
				if(QuestionnaireDao.deleteQuestionnaire(username,questionnaire_id )==true){
					AnswerDao.deleteAnswer(questionnaire_id);
					json.put("status", true);
				}else{
					json.put("status", false);
				}
			}
		}else{
			json.put("status", false);
		}
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
