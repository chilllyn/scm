package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.model.Category;
import com.scm.util.DBUtils;
import com.scm.util.DataSourceUtil;

public class CategoryDao {
	private Connection conn;

	public CategoryDao(Connection conn) {
		this.conn = conn;
	}

	public CategoryDao() {}
	/**
	 * 获取所有产品类别编号 名称
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Category> getCategories() throws SQLException{
		ArrayList<Category> categories=new ArrayList<Category>();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select categoryId,name from category ");
			rs=pstat.executeQuery();
			while(rs.next()) {
				categories.add(new Category(rs.getInt(1), rs.getString(2)));
			}
			return categories;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 删除
	 * @param categoryId
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(int categoryId) throws SQLException{
		PreparedStatement pstat=null;
		int count=0;
		
		try {
			conn=DBUtils.getConnection();
			pstat=conn.prepareStatement("delete from category where categoryId=? ");
			pstat.setInt(1, categoryId);
			count=pstat.executeUpdate();
			if(count==1) {
				return true;
			}
			return false;
		} finally {
			DataSourceUtil.close(null, pstat, null);
		}
	}
	
	/**
	 * 根据每页显示条数和第几页查询,包含模糊查询
	 * @param num	每页显示条数
	 * @param page	当前页数
	 * @param categoryId	模糊分类序列号
	 * @param name	模糊分类名
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Category> getCategories(int num,int page,String categoryId,String name) throws SQLException{
		ArrayList<Category> categories=new ArrayList<Category>();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			conn=DBUtils.getConnection();
			pstat=conn.prepareStatement("select * from category where categoryId regexp ? and "
					+ " name regexp ? limit ?,? ");
			int start=(page-1)*num;
			if(categoryId.equals("")) {
				pstat.setString(1, "\\d*");
			}else {
				pstat.setString(1, categoryId);
			}
			if(name.equals("")) {
				pstat.setString(2, "\\w*");
			}else {
				pstat.setString(2, name);
			}
			pstat.setInt(3, start);
			pstat.setInt(4, num);
			rs=pstat.executeQuery();
			while(rs.next()) {
				categories.add(new Category(rs.getInt("categoryId"), rs.getString("name"), rs.getString("remark")==null?"":rs.getString("remark")));
			}
			return categories;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 获取总页数
	 * @param num
	 * @param categoryId
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public int getTotalPage(int num,String categoryId,String name) throws SQLException{
		PreparedStatement pstat=null;
		ResultSet rs=null;
		int totalPage=1;
		
		try {
			conn=DBUtils.getConnection();
			pstat=conn.prepareStatement("select ceil(count(*)/?) from category where categoryId regexp ? and "
					+ " name regexp ? ");
			pstat.setInt(1, num);
			if(categoryId==null||("").equals(categoryId)) {
				pstat.setString(2, "\\d*");
			}else {
				pstat.setString(2, categoryId);
			}
			if(name==null||("").equals(name)) {
				pstat.setString(3, "\\w*");
			}else {
				pstat.setString(3, name);
			}
			rs=pstat.executeQuery();
			if(rs.next()) {
				totalPage=rs.getInt(1);
			}
			return totalPage;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 新增
	 * @param name
	 * @param remark
	 * @throws SQLException
	 */
	public boolean insertCategory(String name,String remark) throws SQLException {
		PreparedStatement pstat=null;
		
		try {
			conn=DBUtils.getConnection();
			pstat=conn.prepareStatement("insert into category(name,remark) values(?,?)");
			pstat.setString(1, name);
			pstat.setString(2, remark);
			if(pstat.executeUpdate()==1) {
				return true;
			}
			return false;
		} finally {
			DataSourceUtil.close(null, pstat, null);
		}
	}
	/**
	 * 验证新增类名是否重复
	 * @param addName
	 * @return
	 * @throws SQLException
	 */
	public boolean checkName(String addName) throws SQLException {
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			conn=DBUtils.getConnection();
			pstat=conn.prepareStatement("select * from category where name=? ");
			pstat.setString(1, addName);
			rs=pstat.executeQuery();
			if(rs.next()) {
				return false;
			}else {
				return true;
			}
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 修改验证产品类名
	 * @param categoryId
	 * @param addName
	 * @return
	 * @throws SQLException
	 */
	public boolean checkUpdateName(int categoryId,String addName) throws SQLException {
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			conn=DBUtils.getConnection();
			pstat=conn.prepareStatement("select * from category where name=? and categoryId!=? ");
			pstat.setString(1, addName);
			pstat.setInt(2, categoryId);
			rs=pstat.executeQuery();
			if(rs.next()) {
				return false;
			}else {
				return true;
			}
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 修改
	 * @param categoryId
	 * @param name
	 * @param remark
	 * @return
	 * @throws SQLException
	 */
	public boolean update(int categoryId,String name,String remark) throws SQLException {
		PreparedStatement pstat=null;
		
		try {
			conn=DBUtils.getConnection();
			pstat=conn.prepareStatement("update category set name=?,remark=? where categoryId=? ");
			pstat.setString(1, name);
			pstat.setString(2, remark);
			pstat.setInt(3, categoryId);
			if(pstat.executeUpdate()==1) {
				return true;
			}
			return false;
		} finally {
			DataSourceUtil.close(null, pstat, null);
		}
	}
}
