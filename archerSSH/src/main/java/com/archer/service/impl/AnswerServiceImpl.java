package com.archer.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.stereotype.Service;

import com.archer.core.service.impl.BaseServiceImpl;
import com.archer.core.utils.QueryHelper;
import com.archer.dao.AnswerDao;
import com.archer.dao.QuestionnaireDao;
import com.archer.model.Answer;
import com.archer.model.Questionnaire;
import com.archer.model.User;
import com.archer.service.AnswerService;
import com.archer.utils.CreateDataFile;
@Service("answerService")
public class AnswerServiceImpl extends BaseServiceImpl<Answer> implements AnswerService{
	private AnswerDao answerDao;
	@Resource
	private QuestionnaireDao questionnaireDao;
	@Resource
	public void setAnswerDao(AnswerDao answerDao) {
		super.setBaseDao(answerDao);
		this.answerDao = answerDao;
	}

	@Override
	public void collectAnswer(List<String> ans,Questionnaire q) {
		answerDao.collectAnswer(ans, q);
	}

	@Override
	public List getAnswerDataByQid(User u, Questionnaire q, Answer ans) {
		Integer type = null;
		if(q!=null&&ans.getQid()>0) {
			JSONArray ja=JSONArray.fromObject(q.getQuestionnaire_content());
			if(ans.getQid()<ja.size()+1) {
				type=(Integer) ((JSONObject)ja.get(ans.getQid()-1)).get("类型");
			}
			else {
				return null;
			}
		}else {
			return null;
		}
		List l=answerDao.getAnswerListByQid(q, ans);
		List ret=new ArrayList<Object>();
		ret.add(type);
		ret.add(l);
		return ret;
	}
	@Override
	public List getAnswerGroupByQid(User u, Questionnaire q, Answer ans) {
		List l=answerDao.getAnswerGroupByQid(q, ans);
		return l;
	}

	
}
