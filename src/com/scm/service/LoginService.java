package com.scm.service;

import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.dao.UserDao;
import com.scm.model.User;

public class LoginService {
	/**
	 * 获取登录用户
	 * @param account
	 * @param password
	 * @return
	 * @throws SQLException
	 */
	public User getUser(String account,String password) throws SQLException{
		UserDao ud=new UserDao();
		User su=ud.getUser(account, password);
		ArrayList<String> modelUri=ud.getModelUri(account, password);
		if(su==null) {
			return su;
		}
		User u=new User(account, password, su.getName(), su.getStatus(), modelUri);
		return u;
	}
}
