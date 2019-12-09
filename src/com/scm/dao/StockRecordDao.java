package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.model.Poitem;
import com.scm.model.Soitem;
import com.scm.model.StockRecord;
import com.scm.util.DataSourceUtil;

public class StockRecordDao {
	private Connection conn;

	public StockRecordDao(Connection conn) {
		this.conn = conn;
	}
	
	public StockRecordDao() {}
	/**
	 * 库存变更总页数
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
			DataSourceUtil.close(rs, pstat, null);
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
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 获取总页数
	 * @param month
	 * @param num
	 * @return
	 * @throws SQLException
	 */
	public int totalPage(String month,int num,int stockType1,int stockType2) throws SQLException{
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select ifnull(ordercode,\"\") ordercode,stocktime,s.productcode,p.name productname,stocknum,stocknum*price totalprice \r\n" + 
					" from stockrecord s,product p \r\n" + 
					" where s.productcode=p.productcode \r\n" + 
					" and (stocktype=? or stocktype=?) \r\n" + 
					" and stocktime regexp ? ");
			pstat.setObject(1, stockType1);
			pstat.setObject(2, stockType2);
			pstat.setObject(3, month);
			rs=pstat.executeQuery();
			rs.last();
			return (int) Math.ceil(rs.getRow()*1.0/num) ;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 获取指定月份，指定页码，入库 出库库存记录
	 * @param month
	 * @param page
	 * @param num
	 * @param stockType1
	 * @param stockType2
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<StockRecord> getSrs(String month,int page,int num,int stockType1,int stockType2) throws SQLException{
		ArrayList<StockRecord> srs=new ArrayList<StockRecord>();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select ifnull(ordercode,\"\") ordercode,stocktime,s.productcode,p.name productname,stocknum,stocknum*price totalprice \r\n" + 
					" from stockrecord s,product p \r\n" + 
					" where s.productcode=p.productcode \r\n" + 
					" and (stocktype=? or stocktype=?) \r\n" + 
					" and stocktime regexp ? \r\n" + 
					" limit ?,? ;");
			pstat.setObject(1, stockType1);
			pstat.setObject(2, stockType2);
			pstat.setObject(3, month);
			pstat.setObject(4, (page-1)*num);
			pstat.setObject(5, num);
			rs=pstat.executeQuery();
			while(rs.next()) {
				String orderCode=rs.getString("orderCode");
				String stockTime=rs.getString("stockTime");
				String productCode=rs.getString("productCode");
				String productName=rs.getString("productName");
				int stockNum=rs.getInt("stockNum");
				double totalPrice=rs.getDouble("totalPrice");
				srs.add(new StockRecord(productCode, orderCode, stockNum, stockTime, productName, totalPrice));
			}
			return srs;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 获取入库，出库总价
	 * @param month
	 * @param stockType1
	 * @param stockType2
	 * @return
	 * @throws SQLException
	 */
	public double priceTotal(String month,int stockType1,int stockType2) throws SQLException{
		double priceTotal=0;
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select sum(stocknum*price) from stockrecord s,product p \r\n" + 
					" where s.productcode=p.productcode \r\n" + 
					" and (stocktype=? or stocktype=?) \r\n" + 
					" and stocktime regexp ? ;");
			pstat.setObject(1, stockType1);
			pstat.setObject(2, stockType2);
			pstat.setObject(3, month);
			rs=pstat.executeQuery();
			if(rs.next()) {
				priceTotal=rs.getInt(1);
			}
			return priceTotal;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 获取某月入库 出库总数量
	 * @param month
	 * @param stockType1
	 * @param stockType2
	 * @return
	 * @throws SQLException
	 */
	public int numTotal(String month,int stockType1,int stockType2) throws SQLException{
		int numTotal=0;
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select sum(stocknum) from stockrecord where (stocktype=? or stocktype=?) and stocktime regexp ? ; " );
			pstat.setObject(1, stockType1);
			pstat.setObject(2, stockType2);
			pstat.setObject(3, month);
			rs=pstat.executeQuery();
			if(rs.next()) {
				numTotal=rs.getInt(1);
			}
			return numTotal;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 获得入库出库单据数
	 * @param month
	 * @param stockType1
	 * @param stockType2
	 * @return
	 * @throws SQLException
	 */
	public int orderNum(String month,int stockType1,int stockType2) throws SQLException{
		int orderNum=0;
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select count(*) from stockrecord where (stocktype=? or stocktype=?) and stocktime regexp ? ; " );
			pstat.setObject(1, stockType1);
			pstat.setObject(2, stockType2);
			pstat.setObject(3, month);
			rs=pstat.executeQuery();
			if(rs.next()) {
				orderNum=rs.getInt(1);
			}
			return orderNum;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 向库存记录表插入入库记录
	 * @param poid
	 * @param userAccount
	 * @return
	 * @throws SQLException
	 */
	public boolean insertStockRecordPo(long poid,String userAccount) throws SQLException{
		PreparedStatement pstat=null;
		try {
			PoitemDao pid=new PoitemDao(conn);
			ArrayList<Poitem> poitems=pid.getPoitems(poid);
			pstat=conn.prepareStatement("insert into stockRecord(productCode,orderCode,stockNum,stockType,stockTime,createUser) values(?,?,?,?,now(),?) ");
			for(int i=0;i<poitems.size();i++) {
				pstat.setObject(1, poitems.get(i).getProductCode());
				pstat.setObject(2, poid);
				pstat.setObject(3, poitems.get(i).getNum());
				pstat.setObject(4, 1);
				pstat.setObject(5, userAccount);
				pstat.addBatch();
			}
			int[] results=pstat.executeBatch();
			for(int i=0;i<results.length;i++) {
				if(results[i]==0) {
					return false;
				}
			}
			return true;
		} finally {
			DataSourceUtil.close(null, pstat, null);
		}
	}
	/**
	 * 向库存记录表插入出库记录
	 * @param poid
	 * @param userAccount
	 * @return
	 * @throws SQLException
	 */
	public boolean insertStockRecordSo(long soid,String userAccount) throws SQLException{
		PreparedStatement pstat=null;
		try {
			SoitemDao sid=new SoitemDao(conn);
			ArrayList<Soitem> soitems=sid.getSoitems(soid);
			pstat=conn.prepareStatement("insert into stockRecord(productCode,orderCode,stockNum,stockType,stockTime,createUser) values(?,?,?,?,now(),?) ");
			for(int i=0;i<soitems.size();i++) {
				pstat.setObject(1, soitems.get(i).getProductCode());
				pstat.setObject(2, soid);
				pstat.setObject(3, -soitems.get(i).getNum());
				pstat.setObject(4, 2);
				pstat.setObject(5, userAccount);
				pstat.addBatch();
			}
			int[] results=pstat.executeBatch();
			for(int i=0;i<results.length;i++) {
				if(results[i]==0) {
					return false;
				}
			}
			return true;
		} finally {
			DataSourceUtil.close(null, pstat, null);
		}
	}
}
