package com.scm.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.dao.ProductDao;
import com.scm.dao.StockSearchDao;
import com.scm.model.Category;
import com.scm.model.Product;
import com.scm.util.DBUtils;
import com.scm.util.DataSourceUtil;

public class StockSearchService {
	private int num=2;
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
			int status=0;
			boolean flag=true;
			String min=p.getMin();
			String max=p.getMax();
			if("".equals(min)){
				min="0";
			}
			if("".equals(max)){
				max="999999999";
			}
			if(!min.matches("^\\d$|^[1-9]\\d+$")) {
				status=0;
				flag=false;
			}
			if(!max.matches("^\\d$|^[1-9]\\d+$")) {
				status=0;
				flag=false;
			}
			if(flag) {
				StockSearchDao ssd=new StockSearchDao(conn);
				products=ssd.getProducts(1, num, p);
				totalPage=ssd.getTotalPage(num, p);
				status=1;
			}
			result.add(products);
			result.add(totalPage);
			result.add(status);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
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
			StockSearchDao ssd=new StockSearchDao(conn);
			products=ssd.getProducts(toPage, num, p);
			result.add(products);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
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
			StockSearchDao ssd=new StockSearchDao(conn);
			//查询第一页
			products=ssd.getProducts(1, num, new Product("", "", "", ""));
			//查询总页数
			totalPage=ssd.getTotalPage(num, new Product("", "", "", ""));
			result.add(products);
			result.add(totalPage);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
}
