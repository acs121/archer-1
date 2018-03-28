package com.archer.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.archer.Utils.JDBCUtils;
import com.archer.model.Answer;
import com.archer.model.Questionnaire;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class AnswerDao {
	
	public static List<Answer> getAnswerList(String questionnaire_id,int q_id){
		String sql="SELECT answer_content asw FROM answer_list WHERE questionnaire_id=? AND question_id=?";
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Questionnaire q=null;
		List<Answer> a=new ArrayList<Answer>();
		int count=0;
		try{
			conn = JDBCUtils.getConn();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, questionnaire_id);
			ps.setInt(2, q_id);
			rs=(ResultSet) ps.executeQuery();
			while(rs.next()){
				count++;
				a.add(new Answer(count+"",rs.getString("asw")));
			}
			return a;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.close(rs, ps, conn);
		}
	}
	
	
	/**
	 * 提交答卷
	 * @param questionnaire_id
	 * @param answers
	 */
	public static void addAnswer(String questionnaire_id,List<String> answers){
		String date=new Date().toLocaleString();
		String sql="INSERT INTO answer_list(questionnaire_id,question_id,answer_content,answer_postdata) VALUES(?,?,?,?)";
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		String s=null;
		try{
			conn = JDBCUtils.getConn();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, questionnaire_id);
			ps.setString(4, date);
			for(int i=0;i<answers.size();i++){
				s=answers.get(i);
				ps.setInt(2, i+1);
				ps.setString(3, s);
				ps.executeUpdate();
			}
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.close(rs, ps, conn);
		}
	}
	
	/**
	 * 删除指定问卷的所有答卷
	 * @param questionnaire_id
	 */
	public static void deleteAnswer(String questionnaire_id){
		String sql="DELETE FROM answer_list WHERE questionnaire_id=?";
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			conn = JDBCUtils.getConn();
			ps=(PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1, questionnaire_id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JDBCUtils.close(rs, ps, conn);
		}
		
		
	}
	
	
}
