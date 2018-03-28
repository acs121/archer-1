package com.archer.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;

import com.archer.model.Answer;
import com.archer.model.Questionnaire;
import com.archer.model.User;
import com.archer.service.AnswerService;
import com.archer.service.QuestionnaireService;
import com.archer.utils.CreateDataFile;
import com.archer.utils.QuestionnaireUtils;

public class AnswerAction implements SessionAware{
	@Resource
	private AnswerService answerService;
	@Resource
	private QuestionnaireService questionnaireService;
	private Map<String,Object> session;
	private Map<String,Object> jsonData;
	private Questionnaire q;
	private Answer ans;
	private String answer_list;//commit() Need
	private int type=1;//getAnswerDataByQid() Need
	private InputStream source;
	private String filename;
	
	/**
	 * 获取答卷的单题数据统计
	 * params:q.id|ans.qid|type(统计图类型 2-圆饼 1-柱状)
	 * @return
	 */
	public String getAnswerDataByQid() {
		User u=(User) session.get("user");
		if(q!=null&&ans!=null) { 
			q=questionnaireService.findObjectById(q.getId());
			if(q!=null&&q.getUser().equals(u)) {
				List type_ansList=answerService.getAnswerDataByQid(u, q,ans);
				switch(type_ansList==null?4:(int)type_ansList.get(0)){
					case 1://单选
					case 2://多选
						source=CreateDataFile.createChioceDataPic(QuestionnaireUtils.getAnsMap((List<String>) type_ansList.get(1)), ans.getQid(), (type<1||type>2)?1:type);
						return "png";
					case 3://文字题
					try {
						filename=new String(("第"+ans.getQid()+"题答题信息.xls").getBytes("utf-8"),"iso8859-1");
					} catch (UnsupportedEncodingException e) {
						
					}
						source=CreateDataFile.createTextPOI( (List<String>) type_ansList.get(1));
						return "xlsx";
				}
			}
		}
		return "json";
	}

	/***
	 * 提交答卷
	 * params:q.id、answer_list
	 * @return
	 */
	public String commit() {
		jsonData=new HashMap<String, Object>();
		if(q!=null&&answer_list!=null) {
			q=questionnaireService.findObjectById(q.getId());
			if(q!=null&&q.getStatus()==1) {//可提交状态
				List<String> ans= QuestionnaireUtils.cheakAnswerFormatIsRight(q.getQuestionnaire_content(), answer_list);
				if(ans!=null){
					answerService.collectAnswer(ans,q);
					jsonData.put("status", true);
				}else{
					jsonData.put("status", false);
				}
			}else {
				jsonData.put("status", false);
			}
		}else {
			jsonData.put("status", false);
		}
		return "json";
	}
	
	
	
	
	public Map<String, Object> getJsonData() {
		return jsonData;
	}
	public void setJsonData(Map<String, Object> jsonData) {
		this.jsonData = jsonData;
	}


	public Questionnaire getQ() {
		return q;
	}


	public String getAnswer_list() {
		return answer_list;
	}


	public void setQ(Questionnaire q) {
		this.q = q;
	}


	public void setAnswer_list(String answer_list) {
		this.answer_list = answer_list;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session=arg0;
	}

	public Answer getAns() {
		return ans;
	}

	public void setAns(Answer ans) {
		this.ans = ans;
	}

	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}

	public String getFilename() {
		return filename;
	}

	public InputStream getSource() {
		return source;
	}
}
