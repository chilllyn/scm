package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.model.StockRecord;
import com.scm.util.DBUtils;

public class StockinMonthDao {
	private Connection conn;

	public StockinMonthDao(Connection conn) {
		this.conn = conn;
	}
	
	public StockinMonthDao() {}
	/**
	 * 获取总页数
	 * @param month
	 * @param num
	 * @return
	 * @throws SQLException
	 */
	public int totalPage(String month,int num) throws SQLException{
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select ifnull(ordercode,\"\") ordercode,stocktime,s.productcode,p.name productname,stocknum,stocknum*price totalprice \r\n" + 
					" from stockrecord s,product p \r\n" + 
					" where s.productcode=p.productcode \r\n" + 
					" and (stocktype=1 or stocktype=3) \r\n" + 
					" and stocktime regexp ? ");
			pstat.setObject(1, month);
			rs=pstat.executeQuery();
			rs.last();
			return (int) Math.ceil(rs.getRow()*1.0/num) ;
		} finally {
			DBUtils.close(rs, pstat, null);
		}
	}
	/**
	 * 获取指定月份，指定页码，入库库存记录
	 * @param month
	 * @param page
	 * @param num
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<StockRecord> getSrs(String month,int page,int num) throws SQLException{
		ArrayList<StockRecord> srs=new ArrayList<StockRecord>();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select ifnull(ordercode,\"\") ordercode,stocktime,s.productcode,p.name productname,stocknum,stocknum*price totalprice \r\n" + 
					" from stockrecord s,product p \r\n" + 
					" where s.productcode=p.productcode \r\n" + 
					" and (stocktype=1 or stocktype=3) \r\n" + 
					" and stocktime regexp ? \r\n" + 
					" limit ?,? ;");
			pstat.setObject(1, month);
			pstat.setObject(2, (page-1)*num);
			pstat.setObject(3, num);
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
	 * 获取入库总价
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public double priceTotal(String month) throws SQLException{
		double priceTotal=0;
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select sum(stocknum*price) from stockrecord s,product p \r\n" + 
					" where s.productcode=p.productcode \r\n" + 
					" and (stocktype=1 or stocktype=3) \r\n" + 
					" and stocktime regexp ? ;");
			pstat.setObject(1, month);
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
	 * 获取某月入库商品总数量
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public int numTotal(String month) throws SQLException{
		int numTotal=0;
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select sum(stocknum) from stockrecord where (stocktype=1 or stocktype=3) and stocktime regexp ? ; " );
			pstat.setObject(1, month);
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
	 * 获取指定月份入库单据数
	 * @param month
	 * @return
	 * @throws SQLException
	 */
	public int orderNum(String month) throws SQLException{
		int orderNum=0;
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select count(*) from stockrecord where (stocktype=1 or stocktype=3) and stocktime regexp ? ; " );
			pstat.setObject(1, month);
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
