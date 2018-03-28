package com.archer.servlet.Questionnaire;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.archer.Utils.IOUtils;
import com.archer.dao.DataDao;

/**
 * Servlet implementation class GetDataPic
 */
@WebServlet("/user/getData")
public class GetData extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetData() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stubresponse.setDateHeader("Expires", -1);
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");
		String username=(String) request.getSession().getAttribute("username");
		try{
			String questionnaire_id=request.getParameter("id");
			int q_id=Integer.parseInt(request.getParameter("q_id"));
			DataDao d=new DataDao(username,questionnaire_id, q_id); 
			if(d.type==3){
				response.setContentType("application/octet-stream");
				response.addHeader("Content-Disposition","attachment; filename="+new String(("µÚ"+q_id+"Ìâ.xls").getBytes("utf-8"),"iso8859-1"));
			}
			else
				response.setContentType("image/jpeg");
			IOUtils.InStreamToOutStream(d.getData(), response.getOutputStream());
			d.in.close();
		}catch(Exception e){
			e.printStackTrace();
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
