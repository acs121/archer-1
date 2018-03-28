package com.archer.dao;

import java.util.List;

import com.archer.core.dao.BaseDao;
import com.archer.model.Answer;
import com.archer.model.Questionnaire;

public interface AnswerDao extends BaseDao<Answer>{
	public void removeFromQuestionnaire(Questionnaire q);
	public void collectAnswer(List<String> ans,Questionnaire q);
	public List getAnswerListByQid(Questionnaire q,Answer ans);
}
