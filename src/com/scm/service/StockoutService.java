package com.scm.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.dao.StockoutDao;
import com.scm.model.SO;
import com.scm.model.Soitem;
import com.scm.util.DBUtils;
import com.scm.util.DataSourceUtil;

public class StockoutService {
	private int num=2;
	/**
	 * 出库
	 * @param soid
	 * @param userAccount
	 * @param nowPage
	 * @param payType0
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> out(long soid,String userAccount,int nowPage,int payType0) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		int status=0;
		int totalPage=1;
		ArrayList<SO> sos=new ArrayList<SO>();
		ArrayList<Object> result=new ArrayList<Object>();
		try {
			conn.setAutoCommit(false);
			StockoutDao sd=new StockoutDao(conn);
			if(sd.update(soid, userAccount)&&sd.insertStockRecord(soid, userAccount)&&sd.updateProduct(soid)) {
				conn.commit();
				totalPage=sd.getTotalPage(num, payType0);
				if(totalPage>=nowPage) {
					sos=sd.getSos(nowPage, num, payType0);
				}else {
					sos=sd.getSos(totalPage, num, payType0);
				}
				status=1;
				conn.commit();
			}else {
				conn.rollback();
				status=0;
			}
			result.add(sos);
			result.add(totalPage);
			result.add(status);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
	/**
	 * 销售单明细页
	 * @param soid
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> detail(long soid) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			SO so=new SO();
			ArrayList<Soitem> soitems=new ArrayList<Soitem>();
			StockoutDao sd=new StockoutDao(conn);
			so=sd.getSo(soid);
			soitems=sd.getSoitems(soid);
			result.add(so);
			result.add(soitems);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
	/**
	 * 根据付款方式显示
	 * @param payType0
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> tab(int payType0) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<SO> sos=new ArrayList<SO>();
			int totalPage=1;
			StockoutDao sd=new StockoutDao(conn);
			sos=sd.getSos(1, num, payType0);
			totalPage=sd.getTotalPage(num, payType0);
			result.add(sos);
			result.add(totalPage);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
	/**
	 * 分页跳转
	 * @param payType0
	 * @param toPage
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> to(int payType0,int toPage) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<SO> pos=new ArrayList<SO>();
			StockoutDao sd=new StockoutDao(conn);
			pos=sd.getSos(toPage, num, payType0);
			result.add(pos);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
	/**
	 * 从菜单栏第一次进入出库登记
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> first() throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<SO> sos=new ArrayList<SO>();
			int totalPage=1;
			StockoutDao sd=new StockoutDao(conn);
			//查询第一页
			sos=sd.getSos(1, num, 0);
			//查询总页数
			totalPage=sd.getTotalPage(num, 0);
			result.add(sos);
			result.add(totalPage);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
}
