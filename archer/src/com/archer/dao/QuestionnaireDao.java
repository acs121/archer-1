package com.archer.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.archer.Utils.JDBCUtils;
import com.archer.model.Questionnaire;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class QuestionnaireDao {
	//ҵ�����
	/**
	 * ��ȡ�ʾ��Ӧ��ŵ���������
	 * @return int
	 */
	public static int getQuestionType(String username,String questionnaire_id,int q_id){
		Questionnaire q=getQuestionnaireByIdAndUser(username, questionnaire_id);
		JSONArray ja=JSONArray.fromObject(q.getQuestionnaire_content());
		return (int) ((JSONObject)ja.get(q_id-1)).get("����");
	}
	
	/**
	 * �������ʾ����
	 * q�����в���(username,question_name,questionnaire_content)
	 * @param q
	 */
	public static void createQuestionnaire(Questionnaire q){
		q.setStatus(0);//��ʼ��Ϊδ����״̬
		q.setVersion(0);//������ʼ�汾��Ϊ0
		q.setQuestionnaire_id(q.getUsername()+(findQuestionnaireMaxID(q.getUsername(), q.getQuestionnaire_id())+1));
		q.setWriteURL("xxxx");////��δʵ�֣���ʱĬ�ϴ���
		addQuestionnaire(q);
	}
	
	/**
	 * ��ȡ�ʾ����Ϣ       
	 * �ṩ�û������ʾ�ID
	 * @param username
	 * @param questionnaire_id
	 * @return Questionnaire
	 */
	public static Questionnaire getQuestionnaireByIdAndUser(String username,String questionnaire_id){
		Questionnaire q=getQuestionnaireById(questionnaire_id);//�Ȼ�ȡ�ʾ���Ϣ
		if(q.getUsername().equals(username)){//�û�����Ӧ�����ظ��ʾ���Ϣ
			return q;
		}else{//�û�������Ӧ������null
			return null;
		}
		
	}
	
/**
 * ������д�ʾ��ύ�𰸣��˶Դ���Ƿ��׼
 * @param questionnaire_id
 * @return
 */
	public static Questionnaire getQuestionnaire(String questionnaire_id){
		return getQuestionnaireById(questionnaire_id);
		
	}
	/**
	 * �����ʾ����     �������ǣ����һ����¼���汾������1��
	 * q�����ʼ������4��������
	 * username��questionnaire_id��questionnaire_name��questionnaire_content
	 * @param q
	 * @return
	 */
	public static boolean updateQuestionnaire(Questionnaire q){
		int versionID=findQuestionnaireVersionTotal(q.getQuestionnaire_id());//��ȡ��ID���ʾ��������޸Ĵ���
		if(versionID==0){//���ݿ��л�δ���ڸ��ļ�
			return false;
		}
		q.setStatus(0);
		q.setVersion(versionID);//�������°汾��
		q.setWriteURL("xxxxx");
		Questionnaire Q=getQuestionnaireById(q.getQuestionnaire_id());// 
		if(q.getUsername().equals(Q.getUsername())&&Q.getStatus()==0){//�û�����Ӧ,����δ����״̬�����ظ��ʾ���Ϣ
			addQuestionnaire(q);
			return true;
		}else{//�û�������Ӧ������null
			return false;
		}
	}
	/**
	 * ��ȡ�û��������ʾ��б����°汾��<br>
	 * �ṩ�������û���,��ȡ����--�Ƿ�Ϊ����վ���ʾ�
	 * @param username
	 * @return
	 */
	public static List<Questionnaire> getQuestionnaireList(String username,boolean isDelete){
		return getQuestionnaireListByusername(username,isDelete);
	}
	
	/**
	 * ɾ���ʾ�
	 * @param username
	 * @param questionnaire_id
	 */
	public static boolean deleteQuestionnaire(String username,String questionnaire_id){
		return deleteQuestionnaire_P(username, questionnaire_id);
	}
	
	public static boolean recycleQuestionnaire(String username,String questionnaire_id){
		return updateValue(username, questionnaire_id, 3);//״̬��3��ʾ����վ״̬
	}
	
	public static boolean updateStatus(String username,String questionnaire_id,int status){
		return updateValue(username, questionnaire_id, status);//״̬��3��ʾ����վ״̬
	}
	//�����ǻ�������
	/**
	 * �����ʾ�������״ֵ̬��status��
	 * @param username
	 * @param questionnaire_id
	 * @param value
	 * @return
	 */
	private static boolean updateValue(String username,String questionnaire_id,int value){
		String sql="UPDATE questionnaire_list SET status=? WHERE username=? AND questionnaire_id=?";
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Questionnaire q=null;
		boolean b=false;
		try{
			conn = JDBCUtils.getConn();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setInt(1,value );
			ps.setString(2,username);
			ps.setString(3,questionnaire_id);
			ps.executeUpdate();
			b=true;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.close(rs, ps, conn);
		}
		return b;
	}
	
	
	/**
	 * ɾ���ʾ�
	 * @param Username
	 * @param questionnaire_id
	 * @return
	 */
	private static boolean deleteQuestionnaire_P( String username,String questionnaire_id){
		String sql="DELETE FROM questionnaire_list WHERE username=? AND questionnaire_id=?";
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Questionnaire q=null;
		boolean b=false;
		try{
			conn = JDBCUtils.getConn();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1,username);
			ps.setString(2,questionnaire_id);
			ps.executeUpdate();
			b=true;
			return b;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.close(rs, ps, conn);
		}
		
	} 
	
	/**
	 * ��ȡ�û����ʾ��б����°汾��<br>
	 * 
	 * @param username
	 * @param isDelete   �Ƿ�Ϊ�����ʾ�Y-����״̬��Ϊ3���ʾ���   N-����״̬��Ϊ0-2�ļ���
	 * 
	 * @return
	 */
	private static List<Questionnaire> getQuestionnaireListByusername(String username,boolean isDelete){
		String sql="SELECT * FROM questionnaire_list WHERE username=? ORDER BY questionnaire_id,version DESC";
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Questionnaire q=null;
		List<Questionnaire> qList=new ArrayList<Questionnaire>();
		String questionnaire_id="";
		try{
			conn = JDBCUtils.getConn();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1,username);
			rs=(ResultSet) ps.executeQuery();
			while(rs.next()){
				if(!questionnaire_id.equals(rs.getString("questionnaire_id"))&&isDelete?rs.getInt("status")==3:rs.getInt("status")<3){
					questionnaire_id=rs.getString("questionnaire_id");//���¼�¼ֵ
					q=new Questionnaire();//��������¼�����б���
					q.setQuestionnaire_id(questionnaire_id);
					q.setStatus(rs.getInt("status"));
					q.setQuestionnaire_name(rs.getString("questionnaire_name"));
					q.setQuestionnaire_content(rs.getString("questionnaire_content"));
					q.setWriteURL(rs.getString("writeURL"));
					q.setVersion(rs.getInt("version"));
					q.setUsername(rs.getString("username"));
					qList.add(q);
				}
			}
			return qList;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.close(rs, ps, conn);
		}
		
	}
	
	
	/**
	 * ��ȡquestionnaire_id��Ӧ���ʾ���Ϣ(���汾)
	 * @param questionnaire_id
	 * @return
	 */
	private static Questionnaire getQuestionnaireById(String questionnaire_id){
		String sql="SELECT * FROM questionnaire_list WHERE questionnaire_id=? AND version IN (SELECT MAX(version) FROM questionnaire_list WHERE questionnaire_id=?)";
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Questionnaire q=null;
		try{
			conn = JDBCUtils.getConn();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1,questionnaire_id);
			ps.setString(2,questionnaire_id);
			rs=(ResultSet) ps.executeQuery();
			while(rs.next()){
				q=new Questionnaire();
				q.setQuestionnaire_id(questionnaire_id);
				q.setStatus(rs.getInt("status"));
				q.setQuestionnaire_name(rs.getString("questionnaire_name"));
				q.setQuestionnaire_content(rs.getString("questionnaire_content"));
				q.setWriteURL(rs.getString("writeURL"));
				q.setVersion(rs.getInt("version"));
				q.setUsername(rs.getString("username"));
			}
			return q;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.close(rs, ps, conn);
		}
		
	}
	
	
	
	/**
	 * ����ʾ�
	 * @throws SQLException 
	 */
		private static void addQuestionnaire(Questionnaire q){
			String sql="INSERT INTO questionnaire_list(username,questionnaire_id,questionnaire_name,questionnaire_content,writeURL,version,status) VALUES(?,?,?,?,?,?,?)";
			Connection conn=null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				conn = JDBCUtils.getConn();
				ps = (PreparedStatement) conn.prepareStatement(sql);
				ps.setString(1,q.getUsername() );
				ps.setString(2,q.getQuestionnaire_id());
				ps.setString(3,q.getQuestionnaire_name() );
				ps.setString(4,q.getQuestionnaire_content() );
				ps.setString(5,q.getWriteURL() );
				ps.setInt(6,q.getVersion() );
				ps.setInt(7,q.getStatus() );
				ps.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}finally{
				JDBCUtils.close(rs, ps, conn);
			}
		}
		
		/**
		 * ��ѯ�û������ʾ�����ID�������½��ʾ��ID����
		 * @param username
		 * @param Questionnaire_id
		 * @return
		 */
		private static int findQuestionnaireMaxID(String username ,String Questionnaire_id){
			//String sql="SELECT SUBSTR(questionnaire_id,LENGTH(username)+1) id FROM questionnaire_list WHERE username=? GROUP BY questionnaire_id ";
			String sql="SELECT questionnaire_id id FROM questionnaire_list WHERE username=? GROUP BY questionnaire_id ";
			Connection conn=null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			String ID=null;
			int idMax=0;//Ĭ�ϣ����ݿ��в�ѯ�޽��
			int id=0;
			try{
				conn = JDBCUtils.getConn();
				ps = (PreparedStatement) conn.prepareStatement(sql);
				ps.setString(1,username);
				rs=(ResultSet) ps.executeQuery();
				while(rs.next()){
					id=Integer.parseInt(rs.getString("id").substring(username.length()));
					if(id>idMax){
						idMax=id;
					}
				}
				return idMax;
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}finally{
				JDBCUtils.close(rs, ps, conn);
			}
		}
		
		/**
		 * ��ѯ�û������ʾ�������
		 * @param username
		 * @return
		 */
		private static int findUserQuestionnaireTotal(String username){
			String sql="SELECT COUNT(*) count FROM (SELECT questionnaire_id FROM questionnaire_list WHERE username=? GROUP BY questionnaire_id ) a";
			Connection conn=null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				conn = JDBCUtils.getConn();
				ps = (PreparedStatement) conn.prepareStatement(sql);
				ps.setString(1,username);
				rs=(ResultSet) ps.executeQuery();
				rs.next();
				return rs.getInt("count");
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}finally{
				JDBCUtils.close(rs, ps, conn);
			}
		}
		
		
		/**
		 * ��ѯ�ʾ�汾����
		 */
		private static int findQuestionnaireVersionTotal(String questionnaire_id){
			String sql="SELECT COUNT(questionnaire_id) count FROM questionnaire_list WHERE questionnaire_id=?";
			Connection conn=null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				conn = JDBCUtils.getConn();
				ps = (PreparedStatement) conn.prepareStatement(sql);
				ps.setString(1,questionnaire_id);
				rs=(ResultSet) ps.executeQuery();
				rs.next();
				return rs.getInt("count");
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}finally{
				JDBCUtils.close(rs, ps, conn);
			}
			
		}
		
		
		
		
}
