package com.archer.service;

import com.archer.core.service.BaseService;
import com.archer.core.utils.PageResult;
import com.archer.core.utils.QueryHelper;
import com.archer.model.Questionnaire;
import com.archer.model.User;

public interface QuestionnaireService extends BaseService<Questionnaire>{
	public void addQuestionnaire(Questionnaire q);
	/**
	 * 更改问卷状态<br>
	 * 状态说明：0-未发布，1-发布，2-收集，3-已删除（回收站）<br>
	 * 0->1 1->2 2->1
	 * @param q --id -user
	 * @return
	 */
	public boolean changeStatus(Questionnaire q);
	/**
	 * 删除/回收问卷<br>
	 * @param q --id -user
	 * @param isRecycle 是否回收“true”/"false"
	 * @return
	 */
	public boolean deleteQues(Questionnaire q,String isRecycle);
	/**
	 * 获取已发布的单个具体问卷
	 * @param q -id
	 * @return	Questionnaire（status=1）
	 */
	public Questionnaire getpublishQuestionnaire(Questionnaire q);
	/**
	 * 获取用户自己的单张问卷
	 * @param q -id -user
	 * @return
	 */
	public Questionnaire getQuestionnaire(Questionnaire q);
	/**
	 * 获取用户问卷信息
	 * @param u -username 用户的主键属性
	 * @param pageNo -获取的页码
	 * @param pageSize -每页最大条数
	 * @param status -问卷状态码（0-未发布的问卷，1-发布的问卷（可填写状态）<br>，2-收集状态，3-回收站状态）
	 * @return
	 */
	public PageResult getQuestionnaireList(User u, int pageNo, int pageSize,int status);
}
