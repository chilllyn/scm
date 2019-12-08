package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.model.Product;
import com.scm.util.DBUtils;

public class StockSearchDao {
	private Connection conn;

	public StockSearchDao(Connection conn) {
		this.conn = conn;
	}

	public StockSearchDao() {}
	/**
	 * 总页数
	 * @param num
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public int getTotalPage(int num,Product p) throws SQLException{
		PreparedStatement pstat=null;
		ResultSet rs=null;
		int count=0;
		try {
			pstat=conn.prepareStatement("select productCode,name,num,poNum,soNum from product \r\n" + 
					"where productCode regexp ? \r\n" + 
					"and name regexp ? \r\n" + 
					"and num>=? \r\n" + 
					"and num<=? ; ");
			if(p.getProductCode()==null||("").equals(p.getProductCode())) {
				pstat.setString(1, ".*");
			}else {
				pstat.setString(1, p.getProductCode());
			}
			if(p.getName()==null||("").equals(p.getName())) {
				pstat.setString(2, ".*");
			}else {
				pstat.setString(2, p.getName());
			}
			if(("").equals(p.getMin())) {
				pstat.setObject(3, 0);
			}else {
				pstat.setObject(3, p.getMin());
			}
			if(p.getMax()==null||("").equals(p.getMax())) {
				pstat.setObject(4, "9999999999999");
			}else {
				pstat.setObject(4, p.getMax());
			}
			rs=pstat.executeQuery();
			while(rs.next()) {
				count+=1;
			}
			return (int) Math.ceil(count*1.0/num);
		}finally {
			DBUtils.close(rs, pstat, null);
		}
	}
	/**
	 * 获取指定查询条件指定页的表格数据
	 * @param page
	 * @param num
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Product> getProducts(int page,int num,Product p) throws SQLException{
		PreparedStatement pstat=null;
		ResultSet rs=null;
		ArrayList<Product> products=new ArrayList<Product>();
		try {
			pstat=conn.prepareStatement("select productCode,name,num,poNum,soNum from product \r\n" + 
					"where productCode regexp ? \r\n" + 
					"and name regexp ? \r\n" + 
					"and num>=? \r\n" + 
					"and num<=? \r\n" + 
					"limit ?,?; ");
			if(p.getProductCode()==null||("").equals(p.getProductCode())) {
				pstat.setString(1, ".*");
			}else {
				pstat.setString(1, p.getProductCode());
			}
			if(p.getName()==null||("").equals(p.getName())) {
				pstat.setString(2, ".*");
			}else {
				pstat.setString(2, p.getName());
			}
			if(p.getMin()==null||("").equals(p.getMin())) {
				pstat.setObject(3, 0);
			}else {
				pstat.setObject(3, p.getMin());
			}
			if(p.getMax()==null||("").equals(p.getMax())) {
				pstat.setObject(4, "9999999999");
			}else {
				pstat.setObject(4, p.getMax());
			}
			pstat.setInt(5, (page-1)*num);
			pstat.setInt(6, num);
			rs=pstat.executeQuery();
			while(rs.next()) {
				String productCode=rs.getString("productCode");
				String name=rs.getString("name");
				int num1=rs.getInt("num");
				int poNum=rs.getInt("poNum");
				int soNum=rs.getInt("soNum");
				products.add(new Product(productCode, name, num1, poNum, soNum));
			}
			return products;
		} finally {
			DBUtils.close(rs, pstat, null);
		}
	}
}
