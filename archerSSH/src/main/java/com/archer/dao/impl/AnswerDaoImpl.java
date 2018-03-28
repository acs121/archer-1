package com.archer.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Component;

import com.archer.core.impl.BaseDaoImpl;
import com.archer.dao.AnswerDao;
import com.archer.model.Answer;
import com.archer.model.Questionnaire;
@Component("answerDao")
public class AnswerDaoImpl extends BaseDaoImpl<Answer> implements AnswerDao{

	@Override
	public void removeFromQuestionnaire(Questionnaire q) {
		currentSession().createQuery("delete from Answer ans where ans.qusetionnaire=");
	}

	@Override
	public void collectAnswer(List<String> ans, Questionnaire q) {
		Answer a=null;
		for(int i=0;i<ans.size();i++) {
			a=new Answer();
			a.setQuestionnaire(q);
			a.setQid(i+1);
			a.setSt(ans.get(i));
			save(a);
			if (i+1%20==0){
				currentSession().flush();
				currentSession().clear();
		    }
		}
	}

	@Override
	public List getAnswerListByQid(Questionnaire q, Answer ans) {
		Query query=currentSession().createQuery("select st from Answer ans where ans.qid=? AND ans.questionnaire.id=?");
		query.setParameter(0, ans.getQid());
		query.setParameter(1, q.getId());
		return query.list();
	}
	
}
