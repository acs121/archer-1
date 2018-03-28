package com.archer.Utils;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.Connection;

public class JDBCUtils {
	static String driver;
	static String url;
	static String user;
	static String password;
	private JDBCUtils(){}
	static{
		Properties p=new Properties();
		try {
			p.load(JDBCUtils.class.getClassLoader().getResourceAsStream("jdbc.properties"));
			driver =p.getProperty("driver");
			url=p.getProperty("url");
			user=p.getProperty("user");
			password=p.getProperty("password");
			Class.forName(driver);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	static public Connection getConn() throws SQLException{
		return (Connection) DriverManager.getConnection(url, user, password);
	}
	
	public static void close(ResultSet rs, Statement stat,Connection conn){
		if(rs!=null){
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				rs = null;
			}
		}
		if(stat!=null){
			try {
				stat.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				stat = null;
			}
		}
		if(conn!=null){
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				conn = null;
			}
		}
	}
	
}
