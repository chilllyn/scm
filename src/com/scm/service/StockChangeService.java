package com.scm.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.dao.StockChangeDao;
import com.scm.dao.StockinDao;
import com.scm.model.PO;
import com.scm.model.StockRecord;
import com.scm.util.DBUtils;

public class StockChangeService {
	private int num=2;
	/**
	 * 根据库存变化类型查询
	 * @param stockStatus
	 * @param productCode
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> tab(int stockStatus,String productCode) throws SQLException{
		Connection conn=DBUtils.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<StockRecord> srs=new ArrayList<StockRecord>();
			int totalPage=1;
			StockChangeDao scd=new StockChangeDao(conn);
			srs=scd.getSrs(1, num, productCode, stockStatus);
			totalPage=scd.getTotalPage(num, productCode, stockStatus);
			result.add(srs);
			result.add(totalPage);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
	/**
	 * 分页跳转
	 * @param stockStatus
	 * @param toPage
	 * @param productCode
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> to(int stockStatus,int toPage,String productCode) throws SQLException{
		Connection conn=DBUtils.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<StockRecord> srs=new ArrayList<StockRecord>();
			StockChangeDao scd=new StockChangeDao(conn);
			srs=scd.getSrs(toPage, num, productCode, stockStatus);
			result.add(srs);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
	/**
	 * 点击产品编号第一次进入库存变更
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> first(String productCode) throws SQLException{
		Connection conn=DBUtils.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<StockRecord> srs=new ArrayList<StockRecord>();
			int totalPage=1;
			StockChangeDao scd=new StockChangeDao(conn);
			//查询第一页
			srs=scd.getSrs(1, num, productCode, 2);
			//查询总页数
			totalPage=scd.getTotalPage(num, productCode, 2);
			result.add(srs);
			result.add(totalPage);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
}
