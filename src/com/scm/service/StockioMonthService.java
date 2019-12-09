package com.scm.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.dao.StockSearchDao;
import com.scm.dao.StockioMonthDao;
import com.scm.model.Product;
import com.scm.model.StockRecord;
import com.scm.util.DBUtils;
import com.scm.util.DataSourceUtil;

public class StockioMonthService {
	private int num=2;
	/**
	 * 月份分页查询,跳转
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> search(String month,int toPage,int stockType1,int stockType2) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			int orderNum=0;
			int numTotal=0;
			double priceTotal=0;
			ArrayList<StockRecord> srs=new ArrayList<StockRecord>();
			int totalPage=1;
			StockioMonthDao smd=new StockioMonthDao(conn);
			orderNum=smd.orderNum(month, stockType1, stockType2);
			numTotal=smd.numTotal(month, stockType1, stockType2);
			priceTotal=smd.priceTotal(month, stockType1, stockType2);
			srs=smd.getSrs(month, toPage, num, stockType1, stockType2);
			totalPage=smd.totalPage(month, num, stockType1, stockType2);
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
