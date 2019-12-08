package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.model.User;
import com.scm.util.DBUtils;

public class UserDao {
	/**
	 * 获取用户模块路径
	 * @param account
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<String> getModelUri(String account,String password) throws SQLException{
		ArrayList<String> modelUri=new ArrayList<String>();
		Connection conn=null;
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			conn=DBUtils.getConnection();
			pstat=conn.prepareStatement("select modeluri from scmuser,usermodel,systemmodel \r\n" + 
					"where usermodel.modelcode=systemmodel.modelcode and scmuser.account=usermodel.account \r\n" + 
					"and scmuser.account=? and password=?;");
			pstat.setString(1, account);
			pstat.setString(2, password);
			rs=pstat.executeQuery();
			while(rs.next()) {
				modelUri.add(rs.getString(1));
			}
			return modelUri;
		} finally {
			DBUtils.close(rs, pstat, conn);
		}
	}
	/**
	 * 根据账号密码获取用户姓名，锁定状态
	 * @param account
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public User getUser(String account,String password) throws SQLException{
		User user=null;
		Connection conn=null;
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			conn=DBUtils.getConnection();
			pstat=conn.prepareStatement("select name,status from scmuser where account=? and password=?;");
			pstat.setString(1, account);
			pstat.setString(2, password);
			rs=pstat.executeQuery();
			if(rs.next()) {
				user=new User(account, password, rs.getString("name"), rs.getInt("status"));
			}
			return user;
		} finally {
			DBUtils.close(rs, pstat, conn);
		}
	}
}
