package com.scm.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.dao.PoitemDao;
import com.scm.dao.PomainDao;
import com.scm.dao.ProductDao;
import com.scm.dao.StockRecordDao;
import com.scm.model.PO;
import com.scm.model.Poitem;
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
			PomainDao pmd=new PomainDao(conn);
			StockRecordDao srd=new StockRecordDao(conn);
			ProductDao pd=new ProductDao(conn);
			if(pmd.update(poid, userAccount)&&srd.insertStockRecordPo(poid, userAccount)&&pd.updateProductPo(poid)) {
				conn.commit();
				totalPage=pmd.getTotalPage(num, payType0);
				if(totalPage>=nowPage) {
					pos=pmd.getPos(nowPage, num, payType0);
				}else {
					pos=pmd.getPos(totalPage, num, payType0);
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
			DataSourceUtil.close(null, null, conn);
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
			PomainDao pmd=new PomainDao(conn);
			PoitemDao pid=new PoitemDao(conn);
			po=pmd.getPo(poid);
			poitems=pid.getPoitems(poid);
			result.add(po);
			result.add(poitems);
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
			ArrayList<PO> pos=new ArrayList<PO>();
			int totalPage=1;
			PomainDao pmd=new PomainDao(conn);
			pos=pmd.getPos(1, num, payType0);
			totalPage=pmd.getTotalPage(num, payType0);
			result.add(pos);
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
			ArrayList<PO> pos=new ArrayList<PO>();
			PomainDao pmd=new PomainDao(conn);
			pos=pmd.getPos(toPage, num, payType0);
			result.add(pos);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
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
			PomainDao pmd=new PomainDao(conn);
			//查询第一页
			pos=pmd.getPos(1, num, 0);
			//查询总页数
			totalPage=pmd.getTotalPage(num, 0);
			result.add(pos);
			result.add(totalPage);
			return result;
		} finally {
			DataSourceUtil.close(null, null, conn);
		}
	}
}
