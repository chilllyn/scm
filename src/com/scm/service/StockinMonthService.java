package com.scm.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.dao.StockSearchDao;
import com.scm.dao.StockinMonthDao;
import com.scm.model.Product;
import com.scm.model.StockRecord;
import com.scm.util.DBUtils;

public class StockinMonthService {
	private int num=2;
	/**
	 * 月份分页查询,跳转
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> search(String month,int toPage) throws SQLException{
		Connection conn=DBUtils.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			int orderNum=0;
			int numTotal=0;
			double priceTotal=0;
			ArrayList<StockRecord> srs=new ArrayList<StockRecord>();
			int totalPage=1;
			StockinMonthDao smd=new StockinMonthDao(conn);
			orderNum=smd.orderNum(month);
			numTotal=smd.numTotal(month);
			priceTotal=smd.priceTotal(month);
			srs=smd.getSrs(month, toPage, num);
			totalPage=smd.totalPage(month, num);
			result.add(orderNum);
			result.add(numTotal);
			result.add(priceTotal);
			result.add(srs);
			result.add(totalPage);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
}
