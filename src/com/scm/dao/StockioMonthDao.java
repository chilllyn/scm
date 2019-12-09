package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.model.StockRecord;
import com.scm.util.DBUtils;

public class StockioMonthDao {
	private Connection conn;

	public StockioMonthDao(Connection conn) {
		this.conn = conn;
	}
	
	public StockioMonthDao() {}
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
			DBUtils.close(rs, pstat, null);
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
			DBUtils.close(rs, pstat, null);
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
			DBUtils.close(rs, pstat, null);
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
			DBUtils.close(rs, pstat, null);
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
			DBUtils.close(rs, pstat, null);
		}
	}
}
