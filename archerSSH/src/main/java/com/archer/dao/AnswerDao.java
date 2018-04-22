package com.archer.dao;

import java.util.List;

import com.archer.core.dao.BaseDao;
import com.archer.model.Answer;
import com.archer.model.Questionnaire;

public interface AnswerDao extends BaseDao<Answer>{
	/**
	 * 删除问卷的答卷信息
	 * @param q
	 */
	public void removeFromQuestionnaire(Questionnaire q);
	/**
	 * 将一个问卷的所有答题信息写入数据库
	 * @param ans
	 * @param q
	 */
	public void collectAnswer(List<String> ans,Questionnaire q);
	public List getAnswerListByQid(Questionnaire q,Answer ans);
	/***
	 * 分组统计答题数量--弃用
	 * @param q
	 * @param ans
	 * @return
	 */
	@Deprecated
	public List getAnswerGroupByQid(Questionnaire q, Answer ans);
}
