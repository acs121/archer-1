package com.archer.Utils;


import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class QuestionnaireUtils {
	
	/**
	 * �ж��ʾ����� ��ʽ�Ƿ���ȷ<br>
	 * content��ȷʾ����<br>
	 * "[{'��Ŀ':String,'����':Integer(1-2),'ѡ��':[String,String,...]},{'��Ŀ':String,'����':3},...]";
	 * @param content
	 * @return
	 */
	public static boolean ContentFormatIsRight(String content){
		boolean b=true;//��¼��ʽ�Ƿ���ȷ
		JSONArray jsonA=null;
		JSONObject json=null;
		JSONArray choose=null;
		int type;
		try{
			jsonA=JSONArray.fromObject(content);
			for(Object o:jsonA){
				json=(JSONObject) o;
				if(json.get("��Ŀ").getClass()==String.class&&json.get("����").getClass()==Integer.class){
					type=(Integer) json.get("����");
					if(type>=1&&type<=3){//����
						if(type==3){//������
							
						}else{//ѡ����
							choose=(JSONArray) json.get("ѡ��");
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
	 * �˶Դ���Ƿ����ʾ��Ǻ�
	 * @param questionnaire_content [{'��Ŀ':String,'����':Integer(1-2),'ѡ��':[String,String,...]},{'��Ŀ':String,'����':3},...]
	 * @param answer_content ["A","BC","����",...]
	 * @return
	 */
	public static List<String> cheakAnswerFormatIsRight(String questionnaire_content,String answer_content){
		boolean b=true;
		List<String> value=null;
		try{
			int type;
			JSONObject json=null;//��
			int chooseCount;//ѡ����
			int v;
			JSONArray q_content=JSONArray.fromObject(questionnaire_content);
			JSONArray a_content=JSONArray.fromObject(answer_content);
			
			value=JSONArray.toList(a_content);//ѡ��ֵ��
			
			if(q_content.size()==a_content.size()){
				for(int i=0;i<q_content.size();i++){
					json=(JSONObject) q_content.get(i);
					if((Integer)json.get("����")==1){//��ѡ
						chooseCount=((JSONArray)json.get("ѡ��")).size();
						if(value.get(i).length()==1){
							v=Integer.parseInt(value.get(i));
							if(v<=0||v>chooseCount){
								throw new Exception("false");
							}
						}else{
							throw new Exception("false");
						}
					}
					if((Integer)json.get("����")==2){//��ѡ

						chooseCount=((JSONArray)json.get("ѡ��")).size();
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
