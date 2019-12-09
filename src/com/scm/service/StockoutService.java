package com.scm.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.dao.ProductDao;
import com.scm.dao.SoitemDao;
import com.scm.dao.SomainDao;
import com.scm.dao.StockRecordDao;
import com.scm.model.SO;
import com.scm.model.Soitem;
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
			SomainDao smd=new SomainDao(conn);
			StockRecordDao srd=new StockRecordDao(conn);
			ProductDao pd=new ProductDao(conn);
			if(smd.update(soid, userAccount)&&srd.insertStockRecordSo(soid, userAccount)&&pd.updateProductSo(soid)) {
				conn.commit();
				totalPage=smd.getTotalPage(num, payType0);
				if(totalPage>=nowPage) {
					sos=smd.getSos(nowPage, num, payType0);
				}else {
					sos=smd.getSos(totalPage, num, payType0);
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
			DataSourceUtil.close(null, null, conn);
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
			SomainDao smd=new SomainDao(conn);
			SoitemDao sid=new SoitemDao(conn);
			so=smd.getSo(soid);
			soitems=sid.getSoitems(soid);
			result.add(so);
			result.add(soitems);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
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
			SomainDao smd=new SomainDao(conn);
			sos=smd.getSos(1, num, payType0);
			totalPage=smd.getTotalPage(num, payType0);
			result.add(sos);
			result.add(totalPage);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
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
			SomainDao smd=new SomainDao(conn);
			pos=smd.getSos(toPage, num, payType0);
			result.add(pos);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
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
			SomainDao smd=new SomainDao(conn);
			//查询第一页
			sos=smd.getSos(1, num, 0);
			//查询总页数
			totalPage=smd.getTotalPage(num, 0);
			result.add(sos);
			result.add(totalPage);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
	}
}
