package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.model.PO;
import com.scm.model.StockRecord;
import com.scm.util.DBUtils;

public class StockChangeDao {
	private Connection conn;

	public StockChangeDao(Connection conn) {
		this.conn = conn;
	}
	
	public StockChangeDao() {}
	
	/**
	 * 总页数
	 * @param num
	 * @param payType
	 * @return
	 * @throws SQLException
	 */
	public int getTotalPage(int num,String productCode,int stockStatus) throws SQLException{
		PreparedStatement pstat=null;
		ResultSet rs=null;
		int count=0;
		try {
			pstat=conn.prepareStatement("select stockTime,ifnull(orderCode,\"\") orderCode,scmuser.name createUser,stockNum,stockType " + 
					" from stockrecord,scmuser \r\n" + 
					" where stockrecord.createUser=scmuser.account \r\n" + 
					" and productCode=? \r\n" + 
					" and (stockType=? or stockType=? or stockType=? or stockType=?) \r\n");
			pstat.setObject(1, productCode);
			if(stockStatus==0) {
				pstat.setObject(2, 1);
				pstat.setObject(3, 1);
				pstat.setObject(4, 3);
				pstat.setObject(5, 3);
			}else if(stockStatus==1) {
				pstat.setObject(2, 2);
				pstat.setObject(3, 2);
				pstat.setObject(4, 4);
				pstat.setObject(5, 4);
			}else if(stockStatus==2) {
				pstat.setObject(2, 1);
				pstat.setObject(3, 2);
				pstat.setObject(4, 3);
				pstat.setObject(5, 4);
			}
			rs=pstat.executeQuery();
			while(rs.next()) {
				count++;
			}
			return (int) Math.ceil(count*1.0/num);
		} finally {
			DBUtils.close(rs, pstat, null);
		}
	}
	/**
	 * 获取指定条件的库存变更记录
	 * @param page
	 * @param num
	 * @param productCode
	 * @param stockStatus
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<StockRecord> getSrs(int page,int num,String productCode,int stockStatus) throws SQLException{
		PreparedStatement pstat=null;
		ResultSet rs=null;
		ArrayList<StockRecord> srs=new ArrayList<StockRecord>();
		try {
			pstat=conn.prepareStatement("select stockTime,ifnull(orderCode,\"\") orderCode,scmuser.name createUser,stockNum,stockType " + 
					" from stockrecord,scmuser \r\n" + 
					" where stockrecord.createUser=scmuser.account \r\n" + 
					" and productCode=? \r\n" + 
					" and (stockType=? or stockType=? or stockType=? or stockType=?) \r\n" + 
					" limit ?,?;");
			pstat.setObject(1, productCode);
			if(stockStatus==0) {
				pstat.setObject(2, 1);
				pstat.setObject(3, 1);
				pstat.setObject(4, 3);
				pstat.setObject(5, 3);
			}else if(stockStatus==1) {
				pstat.setObject(2, 2);
				pstat.setObject(3, 2);
				pstat.setObject(4, 4);
				pstat.setObject(5, 4);
			}else if(stockStatus==2) {
				pstat.setObject(2, 1);
				pstat.setObject(3, 2);
				pstat.setObject(4, 3);
				pstat.setObject(5, 4);
			}
			pstat.setInt(6, (page-1)*num);
			pstat.setInt(7, num);
			rs=pstat.executeQuery();
			while(rs.next()) {
				String stockTime=rs.getString("stockTime");
				String orderCode=rs.getString("orderCode");
				String createUser=rs.getString("createUser");
				int stockNum=rs.getInt("stockNum");
				int stockType=rs.getInt("stockType");
				srs.add(new StockRecord(orderCode, stockNum, stockType, stockTime, createUser));
			}
			return srs;
		} finally {
			DBUtils.close(rs, pstat, null);
		}
	}
}
