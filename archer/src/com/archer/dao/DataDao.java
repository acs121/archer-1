package com.archer.dao;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.archer.Utils.CreateDataFile;
import com.archer.model.Answer;

/**
 * 根据anserdao和Questionnairedao得到数据结果
 * @author guokunjin
 *
 */
public class DataDao {
		public String questionnaire_id;
		public int q_id;
		public int type;
		public InputStream in;
		public DataDao(String username,String questionnaire_id,int q_id){
			this.questionnaire_id=questionnaire_id;
			this.q_id=q_id;
			this.type=QuestionnaireDao.getQuestionType(username,questionnaire_id, q_id);
		}
		public InputStream getData(){
			List<Answer> l=AnswerDao.getAnswerList(questionnaire_id, q_id);
			switch(type){
			case 1://单选
			case 2://多选
				in=CreateDataFile.createChioceDataPic(getAnsMap(l), q_id, 1);
				break;
			case 3://文字题
				in=CreateDataFile.createTextPOI(l);
				break;
			default:
				break;
			}
			return in;
		}
		
		private Map<Integer, Object> getAnsMap(List<Answer> list){
			Map<Integer, Object> m=new HashMap<Integer,Object>();
			for(Answer a:list){
				for(char c:a.getSt().toCharArray()){
					if(m.get(c-'0')==null){
						m.put(c-'0', 1);
					}else{
						m.put(c-'0', (int)m.get(c-'0')+1);
					}
				}
			}
			return m;
		}
		
		
}
