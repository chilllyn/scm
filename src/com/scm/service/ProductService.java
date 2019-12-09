package com.scm.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.scm.dao.CategoryDao;
import com.scm.dao.ProductDao;
import com.scm.model.Category;
import com.scm.model.Product;
import com.scm.util.DataSourceUtil;

public class ProductService {
	//每页显示条数
	private int num=2;
	/**
	 * 删除
	 * @param productCode
	 * @param p
	 * @param nowPage
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> delete(String productCode,Product p,String nowPage) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<Product> products=new ArrayList<Product>();
			int totalPage=1;
			int status=0;
			ProductDao pd=new ProductDao(conn);
			if(pd.delete(productCode)) {
				totalPage=pd.getTotalPage(num, p);
				if(totalPage>=Integer.parseInt(nowPage)) {
					products=pd.getProducts(Integer.parseInt(nowPage), num, p);
					status=2;
				}else {
					products=pd.getProducts(totalPage, num, p);
					status=1;
				}
			}else {
				status=0;
			}
			result.add(products);
			result.add(totalPage);
			result.add(status);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
	}
	/**
	 * 修改
	 * @param p
	 * @param p1
	 * @param nowPage
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> update(HashMap<String, String> p,Product p1,String nowPage) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<Product> products=new ArrayList<Product>();
			int status=0;
			ProductDao pd=new ProductDao(conn);
			if(p.get("name").matches("^[\\w\\u4E00-\\u9FA5]{1,100}$")) {
				if(p.get("price").matches("^\\d$|^[1-9]\\d+$|^\\d+\\.\\d+$")) {
					if(p.get("unitName").matches("^[\\w\\u4E00-\\u9FA5]{1,5}$")) {
						if(pd.update(p)) {
							products=pd.getProducts(Integer.parseInt(nowPage), num, p1);
							status=4;
						}else {
							status=3;
						}
					}else {
						status=2;
					}
				}else {
					status=1;
				}
			}else {
				status=0;
			}
			result.add(products);
			result.add(status);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
	}
	/**
	 * 新增
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> add(HashMap<String, String> p) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<Product> products=new ArrayList<Product>();
			int totalPage=1;
			int status=0;
			ProductDao pd=new ProductDao(conn);
			boolean flag=pd.checkProductCode(p.get("productCode"));
			if(p.get("productCode").matches("^[0-9a-zA-Z]{4,20}$")&&flag) {
				if(p.get("name").matches("^[\\w\\u4E00-\\u9FA5]{1,100}$")) {
					if(p.get("price").matches("^\\d$|^[1-9]\\d+$|^\\d+\\.\\d+$")) {
						if(p.get("unitName").matches("^[\\w\\u4E00-\\u9FA5]{1,5}$")) {
							if(pd.add(p)) {
								totalPage=pd.getTotalPage(num, new Product("", "", ""));
								products=pd.getProducts(1, num, new Product("", "", ""));
								status=5;
							}else {
								status=4;
							}
						}else {
							status=3;
						}
					}else {
						status=2;
					}
				}else {
					status=1;
				}
			}else {
				status=0;
			}
			result.add(products);
			result.add(totalPage);
			result.add(status);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
	}
	/**
	 * 分页跳转
	 * @param p
	 * @param toPage
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> to(Product p,int toPage) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<Product> products=new ArrayList<Product>();
			ProductDao pd=new ProductDao(conn);
			products=pd.getProducts(toPage, num, p);
			int totalPage=pd.getTotalPage(num, p);
			result.add(products);
			result.add(totalPage);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
	}
	/**
	 * 模糊查询
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> search(Product p) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<Product> products=new ArrayList<Product>();
			int totalPage=1;
			ProductDao pd=new ProductDao(conn);
			products=pd.getProducts(1, num, p);
			totalPage=pd.getTotalPage(num, p);
			result.add(products);
			result.add(totalPage);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
		
	}
	/**
	 * 从菜单栏第一次进入页面
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> first() throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<Product> products=new ArrayList<Product>();
			int totalPage=1;
			ArrayList<Category> categories=new ArrayList<Category>();
			ProductDao pd=new ProductDao(conn);
			CategoryDao cd=new CategoryDao(conn);
			//查询第一页
			products=pd.getProducts(1, num, new Product("", "", ""));
			//查询总页数
			totalPage=pd.getTotalPage(num, new Product("", "", ""));
			//查询所有产品类别
			categories=cd.getCategories();
			result.add(products);
			result.add(totalPage);
			result.add(categories);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
	}
}
