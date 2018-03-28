package com.archer.servlet.template;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.archer.dao.TemplateDao;
import com.archer.model.Questionnaire;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetTemplate
 */
@WebServlet("/getTemplate")
public class GetTemplate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTemplate() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/json");
		JSONObject json=new JSONObject();
		
		String name=request.getParameter("name");
		Questionnaire q=TemplateDao.getTemplate(name);
		if(q==null){
			json.put("status", false);
		}else{
			json.put("status", true);
			json.put("name", name);
			json.put("content", q.getQuestionnaire_content());
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
