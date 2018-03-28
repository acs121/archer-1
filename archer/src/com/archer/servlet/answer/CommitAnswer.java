package com.archer.servlet.answer;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.archer.Utils.QuestionnaireUtils;
import com.archer.dao.AnswerDao;
import com.archer.dao.QuestionnaireDao;
import com.archer.model.Questionnaire;

/**
 * Servlet implementation class CommitAnwser
 */
@WebServlet("/commitAnswer")
public class CommitAnswer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String questionnaire_id=request.getParameter("id");
		String answer_content=request.getParameter("answer_list");
		JSONObject json=new JSONObject();
		Questionnaire q=QuestionnaireDao.getQuestionnaire(questionnaire_id);
		if(q!=null&&q.getStatus()==1){//只限在问卷状态码为1时提交
			List<String> ans= QuestionnaireUtils.cheakAnswerFormatIsRight(q.getQuestionnaire_content(), answer_content);
			if(ans!=null){
				json.put("status", true);
				AnswerDao.addAnswer(q.getQuestionnaire_id(), ans);;
			}else{
				json.put("status", false);
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
