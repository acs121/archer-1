package com.archer.service;

import java.util.List;

import com.archer.core.service.BaseService;
import com.archer.model.Answer;
import com.archer.model.Questionnaire;
import com.archer.model.User;

public interface AnswerService extends BaseService<Answer>{
	/**
	 * 提交用户填写答卷
	 * @param ans
	 * @param q
	 */
	public void collectAnswer(List<String> ans,Questionnaire q);
	
	/**
	 * 
	 * @param u
	 * @param q
	 * @param ans
	 * @return list.get(0)--(int)type list.get(1)-List ansList
	 */
	public List getAnswerDataByQid(User u,Questionnaire q,Answer ans) ;

	/**
	 * @param u
	 * @param q
	 * @param ans
	 * @return
	 */
	List getAnswerGroupByQid(User u, Questionnaire q, Answer ans);
	
}
