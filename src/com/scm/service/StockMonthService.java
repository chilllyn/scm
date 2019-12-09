package com.scm.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.dao.ProductDao;
import com.scm.model.Product;
import com.scm.util.DataSourceUtil;

public class StockMonthService {
	private int num=2;
	/**
	 * 月份分页查询,跳转
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> search(String month,int toPage) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<Product> ps=new ArrayList<Product>();
			int totalPage=1;
			ProductDao pd=new ProductDao(conn);
			ps=pd.getPs(month, toPage, num);
			totalPage=pd.totalPage(month, num);
			result.add(ps);
			result.add(totalPage);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
	}
}
