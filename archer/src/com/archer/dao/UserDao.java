package com.archer.dao;

import com.archer.Utils.JDBCUtils;
import com.archer.model.User;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.ResultSet;

public class UserDao {
		public static void addUser(User user){
			String sql = "insert into user_list values (?,?,?,?)";
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				conn = JDBCUtils.getConn();
				ps = (PreparedStatement) conn.prepareStatement(sql);
				ps.setString(1, user.getUsername());
				ps.setString(2, user.getPassword());
				ps.setString(3, user.getNickname());
				ps.setInt(4, 0);
				ps.executeUpdate();
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}finally{
				JDBCUtils.close(rs, ps, conn);
			}
		}
		/**
		 *暂时未实现该功能
		 */
		public static void updataUserPwd(String pwd){
			
		}

		public static User findUserByUserName(String username) {
			String sql = "select * from user_list where username=?";
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				conn = JDBCUtils.getConn();
				ps = (PreparedStatement) conn.prepareStatement(sql);
				ps.setString(1, username);
				rs = (ResultSet) ps.executeQuery();
				if(rs.next()){
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setNickname(rs.getString("nickname"));
					return user;
				}else{
					return null;
				}
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}finally{
				JDBCUtils.close(rs, ps, conn);
			}
		}
		public static User findUserByUNandPSW(String username, String password) {
			String sql = "select * from user_list where username=? and password=?";
			Connection conn = null;
			PreparedStatement ps = null;
			ResultSet rs = null;
			try{
				conn = JDBCUtils.getConn();
				ps = (PreparedStatement) conn.prepareStatement(sql);
				ps.setString(1, username );
				ps.setString(2, password);
				rs = (ResultSet) ps.executeQuery();
				if(rs.next()){
					User user = new User();
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setNickname(rs.getString("nickname"));
					return user;
				}else{
					return null;
				}
			}catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}finally{
				JDBCUtils.close(rs, ps, conn);
			}
		}
		
}
