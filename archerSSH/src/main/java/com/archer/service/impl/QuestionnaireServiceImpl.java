package com.archer.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.archer.core.service.impl.BaseServiceImpl;
import com.archer.core.utils.PageResult;
import com.archer.core.utils.QueryHelper;
import com.archer.dao.AnswerDao;
import com.archer.dao.QuestionnaireDao;
import com.archer.dao.UserDao;
import com.archer.model.Questionnaire;
import com.archer.model.User;
import com.archer.service.QuestionnaireService;
@Service("questionnaireService")
public class QuestionnaireServiceImpl extends BaseServiceImpl<Questionnaire> implements QuestionnaireService{
	private QuestionnaireDao questionnaireDao;
	@Resource
	public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
		super.setBaseDao(questionnaireDao);
		this.questionnaireDao = questionnaireDao;
	}
	@Override
	public void addQuestionnaire(Questionnaire q) {
		q.setStatus(0);//新添加，处于编辑状态
		questionnaireDao.save(q);
	}
	@Override
	public boolean changeStatus(Questionnaire q) {
		Questionnaire q_db=questionnaireDao.findObjectById(q.getId());
		if(q!=null&&q_db!=null&&q.getUser().equals(q_db.getUser())) {
			if(q_db.getStatus()!=2){//状态码加1
				if(q_db.getStatus()<2){//加1
					q_db.setStatus(q_db.getStatus()+1);
					questionnaireDao.update(q_db);
				}else{//回收站状态3修改为2
					q_db.setStatus(2);
					questionnaireDao.update(q_db);
				}
				return true;
			}else{//由收集完毕变为继续收集2->1
				q_db.setStatus(1);
				return true;
			}
		}else {
			return false;
		}
	}
	@Override
	public boolean deleteQues(Questionnaire q, String isRecycle) {
		Questionnaire q_db=null;
		if(q!=null)q_db=questionnaireDao.findObjectById(q.getId());
		if(q!=null&&q_db!=null&&q_db.getUser().equals(q.getUser())) {
			if(isRecycle!=null){
				if(isRecycle.equals("true")){//设置状态码3
					q_db.setStatus(3);
					questionnaireDao.update(q_db);
					return true;
				}
				if(isRecycle.equals("false")){//直接删除
					questionnaireDao.delete(q_db.getId());
					return true;
				}
				return false;
			}else{
				return false;
			}
		}else {
			return false;
		}
	}
	@Override
	public Questionnaire getQuestionnaire(Questionnaire q){
		Questionnaire q_db= questionnaireDao.findObjectById(q.getId());
		if(q!=null&&q_db!=null&&q_db.getUser().equals(q.getUser())){
			return q_db;
		}
		return null;
	}
	
	@Override
	public Questionnaire getpublishQuestionnaire(Questionnaire q) {
		Questionnaire q_db=questionnaireDao.findObjectById(q.getId());
		if(q!=null&&q_db!=null&&q_db.getStatus()==1) {
			return q_db;
		}
		return null;
	}
	@Override
	public PageResult getQuestionnaireList(User u, int pageNo, int pageSize,int status) {
		QueryHelper qh=new QueryHelper(Questionnaire.class, "q");
		qh.addCondition("q.user.username=?", u.getUsername());
		qh.addCondition("q.status=?", status);
		qh.addOrderByProperty("q.id", QueryHelper.ORDER_BY_ASC);
		PageResult pr=questionnaireDao.getPageResult(qh, pageNo, pageSize);
		return pr;
	}
}
