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
	//业务操作
	/**
	 * 获取问卷对应题号的问题类型
	 * @return int
	 */
	public static int getQuestionType(String username,String questionnaire_id,int q_id){
		Questionnaire q=getQuestionnaireByIdAndUser(username, questionnaire_id);
		JSONArray ja=JSONArray.fromObject(q.getQuestionnaire_content());
		return (int) ((JSONObject)ja.get(q_id-1)).get("类型");
	}
	
	/**
	 * 创建新问卷操作
	 * q中已有参数(username,question_name,questionnaire_content)
	 * @param q
	 */
	public static void createQuestionnaire(Questionnaire q){
		q.setStatus(0);//初始化为未发布状态
		q.setVersion(0);//设置起始版本号为0
		q.setQuestionnaire_id(q.getUsername()+(findQuestionnaireMaxID(q.getUsername(), q.getQuestionnaire_id())+1));
		q.setWriteURL("xxxx");////还未实现，暂时默认处理
		addQuestionnaire(q);
	}
	
	/**
	 * 获取问卷的信息       
	 * 提供用户名和问卷ID
	 * @param username
	 * @param questionnaire_id
	 * @return Questionnaire
	 */
	public static Questionnaire getQuestionnaireByIdAndUser(String username,String questionnaire_id){
		Questionnaire q=getQuestionnaireById(questionnaire_id);//先获取问卷信息
		if(q.getUsername().equals(username)){//用户名对应，返回该问卷信息
			return q;
		}else{//用户名不对应，返回null
			return null;
		}
		
	}
	
/**
 * 用于填写问卷，提交答案，核对答卷是否标准
 * @param questionnaire_id
 * @return
 */
	public static Questionnaire getQuestionnaire(String questionnaire_id){
		return getQuestionnaireById(questionnaire_id);
		
	}
	/**
	 * 更新问卷操作     （不覆盖，添加一条记录，版本号增加1）
	 * q必须初始化以下4个参数：
	 * username、questionnaire_id、questionnaire_name、questionnaire_content
	 * @param q
	 * @return
	 */
	public static boolean updateQuestionnaire(Questionnaire q){
		int versionID=findQuestionnaireVersionTotal(q.getQuestionnaire_id());//获取该ID的问卷数，即修改次数
		if(versionID==0){//数据库中还未存在该文件
			return false;
		}
		q.setStatus(0);
		q.setVersion(versionID);//设置最新版本号
		q.setWriteURL("xxxxx");
		Questionnaire Q=getQuestionnaireById(q.getQuestionnaire_id());// 
		if(q.getUsername().equals(Q.getUsername())&&Q.getStatus()==0){//用户名对应,且是未发布状态，返回该问卷信息
			addQuestionnaire(q);
			return true;
		}else{//用户名不对应，返回null
			return false;
		}
	}
	/**
	 * 获取用户的所有问卷列表（最新版本）<br>
	 * 提供参数：用户名,获取类型--是否为回收站的问卷
	 * @param username
	 * @return
	 */
	public static List<Questionnaire> getQuestionnaireList(String username,boolean isDelete){
		return getQuestionnaireListByusername(username,isDelete);
	}
	
	/**
	 * 删除问卷
	 * @param username
	 * @param questionnaire_id
	 */
	public static boolean deleteQuestionnaire(String username,String questionnaire_id){
		return deleteQuestionnaire_P(username, questionnaire_id);
	}
	
	public static boolean recycleQuestionnaire(String username,String questionnaire_id){
		return updateValue(username, questionnaire_id, 3);//状态码3表示回收站状态
	}
	
	public static boolean updateStatus(String username,String questionnaire_id,int status){
		return updateValue(username, questionnaire_id, status);//状态码3表示回收站状态
	}
	//下面是基础操作
	/**
	 * 更新问卷表里面的状态值（status）
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
	 * 删除问卷
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
	 * 获取用户的问卷列表（最新版本）<br>
	 * 
	 * @param username
	 * @param isDelete   是否为回收问卷，Y-返回状态码为3的问卷集合   N-返回状态码为0-2的集合
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
					questionnaire_id=rs.getString("questionnaire_id");//更新记录值
					q=new Questionnaire();//将该条记录加入列表中
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
	 * 获取questionnaire_id对应的问卷信息(最大版本)
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
	 * 添加问卷
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
		 * 查询用户创建问卷的最大ID，用于新建问卷的ID命名
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
			int idMax=0;//默认，数据库中查询无结果
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
		 * 查询用户制作问卷俺的总数
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
		 * 查询问卷版本总数
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
