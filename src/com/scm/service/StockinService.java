package com.scm.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.dao.StockinDao;
import com.scm.model.PO;
import com.scm.model.Poitem;
import com.scm.util.DBUtils;
import com.scm.util.DataSourceUtil;

public class StockinService {
	private int num=2;
	/**
	 * 入库
	 * @param poid
	 * @param userAccount
	 * @param nowPage
	 * @param payType0
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> in(long poid,String userAccount,int nowPage,int payType0) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		int status=0;
		int totalPage=1;
		ArrayList<PO> pos=new ArrayList<PO>();
		ArrayList<Object> result=new ArrayList<Object>();
		try {
			conn.setAutoCommit(false);
			StockinDao sd=new StockinDao(conn);
			if(sd.update(poid, userAccount)&&sd.insertStockRecord(poid, userAccount)&&sd.updateProduct(poid)) {
				conn.commit();
				totalPage=sd.getTotalPage(num, payType0);
				if(totalPage>=nowPage) {
					pos=sd.getPos(nowPage, num, payType0);
				}else {
					pos=sd.getPos(totalPage, num, payType0);
				}
				status=1;
				conn.commit();
			}else {
				conn.rollback();
				status=0;
			}
			result.add(pos);
			result.add(totalPage);
			result.add(status);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
	/**
	 * 采购单明细页
	 * @param poid
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> detail(long poid) throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			PO po=new PO();
			ArrayList<Poitem> poitems=new ArrayList<Poitem>();
			StockinDao sd=new StockinDao(conn);
			po=sd.getPo(poid);
			poitems=sd.getPoitems(poid);
			result.add(po);
			result.add(poitems);
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
			ArrayList<PO> pos=new ArrayList<PO>();
			int totalPage=1;
			StockinDao sd=new StockinDao(conn);
			pos=sd.getPos(1, num, payType0);
			totalPage=sd.getTotalPage(num, payType0);
			result.add(pos);
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
			ArrayList<PO> pos=new ArrayList<PO>();
			StockinDao sd=new StockinDao(conn);
			pos=sd.getPos(toPage, num, payType0);
			result.add(pos);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
	/**
	 * 从菜单栏第一次进入入库登记
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Object> first() throws SQLException{
		Connection conn=DataSourceUtil.getConnection();
		try {
			ArrayList<Object> result=new ArrayList<Object>();
			ArrayList<PO> pos=new ArrayList<PO>();
			int totalPage=1;
			StockinDao pd=new StockinDao(conn);
			//查询第一页
			pos=pd.getPos(1, num, 0);
			//查询总页数
			totalPage=pd.getTotalPage(num, 0);
			result.add(pos);
			result.add(totalPage);
			return result;
		} finally {
			DBUtils.close(null, null, conn);
		}
	}
}
