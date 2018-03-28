package com.archer.Utils;


import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QuestionnaireUtils {
	
	/**
	 * 判断问卷内容 格式是否正确<br>
	 * content正确示范：<br>
	 * "[{'题目':String,'类型':Integer(1-2),'选项':[String,String,...]},{'题目':String,'类型':3},...]";
	 * @param content
	 * @return
	 */
	public static boolean ContentFormatIsRight(String content){
		boolean b=true;//记录格式是否正确
		JSONArray jsonA=null;
		JSONObject json=null;
		JSONArray choose=null;
		int type;
		try{
			jsonA=JSONArray.fromObject(content);
			for(Object o:jsonA){
				json=(JSONObject) o;
				if(json.get("题目").getClass()==String.class&&json.get("类型").getClass()==Integer.class){
					type=(Integer) json.get("类型");
					if(type>=1&&type<=3){//符合
						if(type==3){//文字题
							
						}else{//选择题
							choose=(JSONArray) json.get("选项");
							for(Object c:choose){
								if(c.getClass()!=String.class){
									throw new Exception("false");
								}
							}
						}
					}else{
						throw new Exception("false");
					}
				}else{
					throw new Exception("false");
				}
			}
		}catch (Exception e) {
			b=false;
		}
		return b;
		
	}
	
	
	/**
	 * 核对答卷是否与问卷吻合
	 * @param questionnaire_content [{'题目':String,'类型':Integer(1-2),'选项':[String,String,...]},{'题目':String,'类型':3},...]
	 * @param answer_content ["A","BC","文字",...]
	 * @return
	 */
	public static List<String> cheakAnswerFormatIsRight(String questionnaire_content,String answer_content){
		boolean b=true;
		List<String> value=null;
		try{
			int type;
			JSONObject json=null;//题
			int chooseCount;//选项数
			int v;
			JSONArray q_content=JSONArray.fromObject(questionnaire_content);
			JSONArray a_content=JSONArray.fromObject(answer_content);
			
			value=JSONArray.toList(a_content);//选项值串
			
			if(q_content.size()==a_content.size()){
				for(int i=0;i<q_content.size();i++){
					json=(JSONObject) q_content.get(i);
					if((Integer)json.get("类型")==1){//单选
						chooseCount=((JSONArray)json.get("选项")).size();
						if(value.get(i).length()==1){
							v=Integer.parseInt(value.get(i));
							if(v<=0||v>chooseCount){
								throw new Exception("false");
							}
						}else{
							throw new Exception("false");
						}
					}
					if((Integer)json.get("类型")==2){//多选

						chooseCount=((JSONArray)json.get("选项")).size();
						for(char c:value.get(i).toCharArray()){
							v=c-'0';
							if(v<=0||v>chooseCount){
								throw new Exception("false");
							}
						}
					}
				}
			}else{
				throw new Exception("false");
			}
			
		}catch (Exception e) {
			b=false;
			e.printStackTrace();
		}
		if(b){
			return value;
		}else{
			return null;
		}
	}
}
