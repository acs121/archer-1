package com.archer.dao;

import java.util.ArrayList;
import java.util.List;

import com.archer.Utils.JDBCUtils;
import com.archer.model.Questionnaire;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class TemplateDao {
	public static Questionnaire getTemplate(String name){
		String sql="SELECT * FROM template WHERE name=?";
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Questionnaire q=null;
		try{
			conn = JDBCUtils.getConn();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			ps.setString(1,name);
			rs=(ResultSet) ps.executeQuery();
			while(rs.next()){
					q=new Questionnaire();//将该条记录加入列表中
					q.setQuestionnaire_id("");
					q.setStatus(0);
					q.setQuestionnaire_name(rs.getString("name"));
					q.setQuestionnaire_content(rs.getString("content"));
					q.setWriteURL("");
					q.setVersion(0);
					q.setUsername("");
			}
			return q;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.close(rs, ps, conn);
		}
		
	}
		
	public static List getTemplateNameList(){
		String sql="SELECT * FROM template";
		Connection conn=null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Questionnaire q=null;
		List<String> l=new ArrayList<String>();
		try{
			conn = JDBCUtils.getConn();
			ps = (PreparedStatement) conn.prepareStatement(sql);
			rs=(ResultSet) ps.executeQuery();
			while(rs.next()){
				l.add(rs.getString("name"));
			}
			return l;
		}catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}finally{
			JDBCUtils.close(rs, ps, conn);
		}
		
	}
}
