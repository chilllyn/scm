package com.scm.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBUtils {
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() throws SQLException {
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/scm?serverTimezone=CTT&"
				+ "useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true", "root", "123456");
	}
	
	public static void close(ResultSet rs,Statement pstat,Connection conn) {
		try {
			if(rs!=null) rs.close();
			if(pstat!=null) pstat.close();
			if(conn!=null) conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
