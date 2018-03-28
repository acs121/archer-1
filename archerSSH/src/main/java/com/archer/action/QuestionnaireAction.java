package com.archer.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.SessionAware;

import com.archer.core.utils.PageResult;
import com.archer.model.Questionnaire;
import com.archer.model.User;
import com.archer.service.QuestionnaireService;
import com.archer.utils.QuestionnaireUtils;

/**
 * 前提：已登录<br>
 * 问卷处理（添加、修改、删除、查询）
 * @author guokunjin
 *
 */
public class QuestionnaireAction implements SessionAware{
	private Map<String,Object> session;
	@Resource
	private QuestionnaireService questionnaireService;//业务逻辑处理
	private Map<String,Object> jsonData;//backData
	
	private Questionnaire q;
	private String isRecycle;//deleteQuestionnaire() Need
	private boolean isDelete;//getQuestionnaireList() Need
	private int pageNo;//getQuestionnaireList() Need 页码
	private int pageSize;//getQuestionnaireList()Need 页大小
	
	
	/**
	 * 创建问卷<br>
	 * 参数需要：q.questionnaire_name、q.questionnaire_content<br>
	 * 
	 */
	public String createQuestionnaire() {
		jsonData=new HashMap<String, Object>();
		if(q!=null&&q.getQuestionnaire_name()!=null&&q.getQuestionnaire_content()!=null) {
			if(QuestionnaireUtils.ContentFormatIsRight(q.getQuestionnaire_content())){//content合法
				q.setUser((User) session.get("user"));
				questionnaireService.addQuestionnaire(q);
				jsonData.put("status", true);
			}else {
				jsonData.put("status", false);
			}
		}else {
			jsonData.put("status", false);
		}
		return "json";
	}
	/**
	 * 删除问卷
	 * params:q.id--问卷ID  isRecycle--是否回收
	 * @return
	 */
	public String deleteQuestionnaire() {
		jsonData=new HashMap<String, Object>();
		User user=(User) session.get("user");
		q.setUser(user);
		if(questionnaireService.deleteQues(q, isRecycle)) {
			jsonData.put("status", true);
		}else {
			jsonData.put("status", false);
		}
		return "json";
	}
	/**
	 * 更新问卷信息
	 * params：q.id q.questionnaire_name q.questionnaire_content
	 * @return
	 */
	public String updateQuestionnaire() {
		jsonData=new HashMap<String, Object>();
		User user=(User) session.get("user");
		Questionnaire q_db=null;
		q.setUser(user);
		if(q!=null&&q.getQuestionnaire_name()!=null&&q.getQuestionnaire_content()!=null) {
			q_db=questionnaireService.getQuestionnaire(q);
			if(q_db!=null&&q_db.getStatus()==0) {
				q_db.setQuestionnaire_content(q.getQuestionnaire_content());
				q_db.setQuestionnaire_name(q.getQuestionnaire_name());
				questionnaireService.update(q_db);
				jsonData.put("status", true);
				return "json";
			}
		}
		System.out.println(q.getId()+q.getQuestionnaire_content()+q.getQuestionnaire_name()+q_db.getStatus());
		jsonData.put("status", false);
		return "json";
	}
	/**
	 * 修改问卷状态
	 * params:q.id --问卷id
	 * @return
	 */
	public String changeStatus() {
		jsonData=new HashMap<String, Object>();
		User u=(User) session.get("user");
		q.setUser(u);
		if(questionnaireService.changeStatus(q)) {
			jsonData.put("status", true);
		}else {
			jsonData.put("status", false);
		}
		return "json";
	}
	/***
	 * 获取单张详细问卷
	 * params：q.id--问卷id
	 * @return
	 */
	public String getQuestionnaire() {
		jsonData=new HashMap<String, Object>();
		User user=(User) session.get("user");
		if(q!=null) {
			q.setUser(user);
			q=questionnaireService.getQuestionnaire(q);
		}
		if(q==null){
			jsonData.put("status", false);
		}else{
			jsonData.put("status", true);
			jsonData.put("问卷id", q.getId());
			jsonData.put("问卷名", q.getQuestionnaire_name());
			jsonData.put("内容", q.getQuestionnaire_content());
			jsonData.put("q_status", q.getStatus());
		}
		return "json";
	}
	/**
	 * 用于填写人获取发布中的问卷
	 * pram：q.id
	 * @return
	 */
	public String getPublishQues() {
		jsonData=new HashMap<String, Object>();
		if(q!=null)q=questionnaireService.getpublishQuestionnaire(q);
		if(q==null){
			jsonData.put("status", false);
		}else{
			jsonData.put("status", true);
			jsonData.put("问卷id", q.getId());
			jsonData.put("问卷名", q.getQuestionnaire_name());
			jsonData.put("内容", q.getQuestionnaire_content());
		}
		return "json";
	}
	
	/***
	 * 获取问卷列表
	 * params: q.status问卷状态码,pageNo页码,pageSize页大小
	 * @return
	 */
	public String getQuestionnaireList() {
		jsonData=new HashMap<String, Object>();
		User u=(User) session.get("user");
		if(q.getStatus()<=3&&q.getStatus()>=0) {
			PageResult pr=questionnaireService.getQuestionnaireList(u, pageNo, pageSize, q.getStatus());
			jsonData.put("status", true);
			jsonData.put("totalCount", pr.getTotalCount());
			jsonData.put("pageNo", pr.getPageNo());
			jsonData.put("pageSize", pr.getPageSize());
			jsonData.put("totalPageCount",pr.getTotalPageCount());
			jsonData.put("items", pr.getItems());
		}else {
			jsonData.put("status", false);
		}
		return "json";
	}
	
	




	
	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session=arg0;
	}



	public Map<String, Object> getJson() {
		return jsonData;
	}
	public Questionnaire getQ() {
		return q;
	}
	public void setQ(Questionnaire q) {
		this.q = q;
	}
	public String getIsRecycle() {
		return isRecycle;
	}
	public void setIsRecycle(String isRecycle) {
		this.isRecycle = isRecycle;
	}
	public Map<String, Object> getJsonData() {
		return jsonData;
	}
	public boolean isDelete() {
		return isDelete;
	}
	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}
	public int getPageNo() {
		return pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

}
