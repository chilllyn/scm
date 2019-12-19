package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.model.Poitem;
import com.scm.util.DataSourceUtil;

public class PoitemDao {
	private Connection conn;

	public PoitemDao(Connection conn) {
		this.conn = conn;
	}
	
	public PoitemDao() {}
	/**
	 * 根据采购单编号获取采购单明细
	 * @param poid
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Poitem> getPoitems(long poid) throws SQLException{
		ArrayList<Poitem> poitems=new ArrayList<Poitem>();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select p.productCode,name,p.unitName,p.num,unitPrice,itemPrice \r\n " + 
					" from poitem p,product \r\n " + 
					" where p.productCode=product.productCode and poid=? ; ");
			pstat.setLong(1, poid);
			rs=pstat.executeQuery();
			while(rs.next()) {
				String productCode=rs.getString("productCode");
				String name=rs.getString("name");
				String unitName=rs.getString("unitName");
				int num=rs.getInt("num");
				double unitPrice=rs.getDouble("unitPrice");
				double itemPrice=rs.getDouble("itemPrice");
				poitems.add(new Poitem(productCode, name, unitName, num, unitPrice, itemPrice));
			}
			return poitems;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
}
