package com.scm.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.dao.CategoryDao;
import com.scm.dao.ProductDao;
import com.scm.model.Category;
import com.scm.util.DataSourceUtil;

public class CategoryService {
	//每页显示条数
	private int num=2;
	/**
	 * 第一次进入产品分类页面
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> first() throws SQLException{
		ArrayList<Object> result=new ArrayList<Object>();
		int totalPage=1;
		int nowPage=1;
		ArrayList<Category> categories=new ArrayList<Category>();
		categories=getCategories(num, null, "", "");
		totalPage=getTotalPage(num, "", "");
		result.add(categories);
		result.add(totalPage);
		result.add(nowPage);
		return result;
	}
	/**
	 * 删除产品类别
	 * @param categoryId
	 * @param nowPage
	 * @return
	 * @throws SQLException 
	 * @throws NumberFormatException 
	 */
	public ArrayList<Object> delete(String categoryId,String nowPage,String searchId,String searchName) throws NumberFormatException, SQLException {
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			int status=0;
			int totalPage=0;
			ArrayList<Category> getCategories=new ArrayList<Category>();
			CategoryDao cd=new CategoryDao();
			ProductDao pd=new ProductDao(conn);
			if(pd.canDelete(Integer.parseInt(categoryId))) {
				if(cd.delete(Integer.parseInt(categoryId))) {
					totalPage=getTotalPage(num, searchId, searchName);
					if(totalPage>=Integer.parseInt(nowPage)) {
						getCategories=getCategories(num, nowPage, searchId, searchName);
						status=2;
					}else {
						getCategories=getCategories(num, totalPage+"", searchId, searchName);
						status=3;
					}
				}else {
					status=1;
				}
			}else {
				status=0;
			}
			result.add(getCategories);
			result.add(totalPage);
			result.add(status);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
		
	}
	/**
	 * 分页跳转
	 * @param categoryId
	 * @param name
	 * @param toPage
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> to(String categoryId,String name,String toPage) throws SQLException{
		ArrayList<Object> result=new ArrayList<Object>();
		ArrayList<Category> getCategories=new ArrayList<Category>();
		int totalPage=1;
		getCategories=getCategories(num, toPage, categoryId, name);
		totalPage=getTotalPage(num, categoryId, name);
		result.add(getCategories);
		result.add(totalPage);
		return result;
	}
	/**
	 * 模糊查询
	 * @param categoryId
	 * @param name
	 * @return
	 * @throws SQLException 
	 */
	public ArrayList<Object> search(String categoryId,String name) throws SQLException{
		ArrayList<Object> result=new ArrayList<Object>();
		ArrayList<Category> getCategories=new ArrayList<Category>();
		int totalPage=1;
		getCategories=getCategories(num, "1", categoryId, name);
		totalPage=getTotalPage(num, categoryId, name);
		result.add(getCategories);
		result.add(totalPage);
		return result;
	}
	/**
	 * 获取页面显示的产品分类集合
	 * @param num 
	 * @param page
	 * @param categoryId
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Category> getCategories(int num,String page,String categoryId,String name) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Category> categories=new ArrayList<Category>();
			if(page==null||("").equals(page)) {
				//查询首页
				categories=new CategoryDao(conn).getCategories(num, 1,"","");
			}else {
				//查询指定页数
				categories=new CategoryDao(conn).getCategories(num, Integer.parseInt(page),categoryId,name);
			}
			return categories;
		} finally {
			DataSourceUtil.close(null, null, conn);
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
	public int getTotalPage(int num,String categoryId,String name) throws SQLException {
		Connection conn=DataSourceUtil.getConnection();
		try {
			return new CategoryDao(conn).getTotalPage(num, categoryId, name);
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
	}
	/**
	 * 获取当前页数
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public int getNowPage(String page) throws SQLException {
		int nowPage=1;
		if(page==null||("").equals(page)) {
			nowPage=1;
		}else {
			nowPage=Integer.parseInt(page);
		}
		return nowPage;
	}
	/**
	 * 新增
	 * @param name
	 * @param remark
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> add(String name,String remark) throws SQLException {
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<Category> getCategories=new ArrayList<Category>();
			int totalPage=1;
			int num=2;
			int status=0;
			CategoryDao cd=new CategoryDao(conn);
			if(cd.checkName(name)) {
				if(cd.insertCategory(name, remark)) {
					totalPage=getTotalPage(num, "", "");
					getCategories=getCategories(num, totalPage+"", "", "");
					status=2;
				}else {
					status=1;
				}
			}else {
				status=0;
			}
			result.add(getCategories);
			result.add(totalPage);
			result.add(status);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
	}
	/**
	 * 修改
	 * @param name
	 * @param remark
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> update(String categoryId,String name,String addCategoryId,String addName,String remark,String nowPage) throws SQLException {
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<Category> getCategories=new ArrayList<Category>();
			int num=2;
			int status=0;
			CategoryDao cd=new CategoryDao(conn);
			if(cd.checkUpdateName(Integer.parseInt(addCategoryId), addName)) {
				if(cd.update(Integer.parseInt(addCategoryId), addName, remark)) {
					getCategories=getCategories(num, nowPage, categoryId, name);
					status=2;
				}else {
					status=1;
				}
			}else {
				status=0;
			}
			result.add(getCategories);
			result.add(status);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
	}
	
	
	/**
	 * 新增验证名字是否重复
	 * @param addName
	 * @return
	 * @throws SQLException
	 */
	public boolean checkName(String addName) throws SQLException {
		Connection conn=DataSourceUtil.getConnection();
		try {
			return new CategoryDao(conn).checkName(addName);
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
	}
}
