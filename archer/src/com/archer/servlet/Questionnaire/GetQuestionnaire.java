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
 * ��ȡ�ʾ���Ϣ
 * Servlet implementation class GetQuestionnaire
 */
@WebServlet("/getQuestionnaire")
public class GetQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/json");
		JSONObject json=new JSONObject();
		String id=request.getParameter("id");
		if(id!=null&&!id.equals("")){
			Questionnaire q=QuestionnaireDao.getQuestionnaire(id);
			if(q==null){
				json.put("status", false);
			}else{
				json.put("status", true);
				json.put("�ʾ�id", q.getQuestionnaire_id());
				json.put("�ʾ���", q.getQuestionnaire_name());
				json.put("����", q.getQuestionnaire_content());
				json.put("q_status", q.getStatus());
			}
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
