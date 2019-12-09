package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.model.Soitem;
import com.scm.util.DataSourceUtil;

public class SoitemDao {
	private Connection conn;

	public SoitemDao(Connection conn) {
		this.conn = conn;
	}
	
	public SoitemDao() {}
	/**
	 * 根据采购单编号获取采购单明细
	 * @param poid
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Soitem> getSoitems(long soid) throws SQLException{
		ArrayList<Soitem> soitems=new ArrayList<Soitem>();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select s.productCode,name,s.unitName,s.num,unitPrice,itemPrice \r\n " + 
					" from soitem s,product \r\n " + 
					" where s.productCode=product.productCode and soid=? ; ");
			pstat.setLong(1, soid);
			rs=pstat.executeQuery();
			while(rs.next()) {
				String productCode=rs.getString("productCode");
				String name=rs.getString("name");
				String unitName=rs.getString("unitName");
				int num=rs.getInt("num");
				double unitPrice=rs.getDouble("unitPrice");
				double itemPrice=rs.getDouble("itemPrice");
				soitems.add(new Soitem(productCode, name, unitName, num, unitPrice, itemPrice));
			}
			return soitems;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
}
