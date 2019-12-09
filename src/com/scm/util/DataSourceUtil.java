package com.scm.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DataSourceUtil {
private static DataSource dataSource;
	
	static {
		try {
			// 初始化上下文环境的对象
			InitialContext ctx = new InitialContext();
			// 根据资源的路径名去查找资源对象
			dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/scm");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 从连接池中获取连接对象
	 * @return
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		return dataSource.getConnection();
	}
	/**
	 * 关闭连接
	 * @param conn
	 * @param st
	 * @param rs
	 */
	public static void close(Connection conn,Statement st, ResultSet rs) {
		try {
			if(rs != null) {
				rs.close();
			}
			if(st != null) {
				st.close();
			}
			if(conn != null) {
				conn.close(); // 把连接对象归还给连接池
			}
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
