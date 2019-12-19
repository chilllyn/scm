package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.model.SO;
import com.scm.util.DataSourceUtil;

public class SomainDao {
	private Connection conn;

	public SomainDao(Connection conn) {
		this.conn = conn;
	}
	
	public SomainDao() {}
	/**
	 * 更新销售单主表
	 * @param soid
	 * @param userAccount
	 * @return
	 * @throws SQLException
	 */
	public boolean update(long soid,String userAccount) throws SQLException{
		PreparedStatement pstat=null;
		try {
			pstat=conn.prepareStatement("update somain set status=2,stockTime=now(),stockUser=? where soid=? ");
			pstat.setString(1, userAccount);
			pstat.setLong(2, soid);
			if(pstat.executeUpdate()==1) {
				return true;
			}
			return false;
		} finally {
			DataSourceUtil.close(null, pstat, null);
		}
	}
	/**
	 * 根据采购单编号获得采购单主信息
	 * @param poid
	 * @return
	 * @throws SQLException
	 */
	public SO getSo(long soid) throws SQLException{
		SO so=new SO();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select soid,createTime,tipFee,productTotal,SOtotal,payType,prePayFee,t1.status,customerName,scmuser.name userName \r\n " + 
					" from \r\n" + 
					" (select soid,s.account userAccount,createTime,tipFee,productTotal,SOtotal,payType,prePayFee,status,customer.name customerName  \r\n " + 
					" from somain s inner join customer \r\n " + 
					" on s.customerCode=customer.customerCode and soid=?) t1 inner join scmuser \r\n " + 
					" on t1.userAccount=scmuser.account ; ");
			pstat.setLong(1, soid);
			rs=pstat.executeQuery();
			if(rs.next()) {
				String customerName=rs.getString("customerName");
				String userName=rs.getString("userName");
				String createTime=rs.getString("createTime");
				double tipFee=rs.getDouble("tipFee");
				double productTotal=rs.getDouble("productTotal");
				double soTotal=rs.getDouble("SOTotal");
				int payType1=rs.getInt("payType");
				double prePayFee=rs.getDouble("prePayFee");
				int status=rs.getInt("status");
				so=new SO(soid, customerName, userName, createTime, tipFee, productTotal, soTotal, payType1, prePayFee, status);
			}
			return so;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 总页数
	 * @param num
	 * @param payType
	 * @return
	 * @throws SQLException
	 */
	public int getTotalPage(int num,int payType) throws SQLException{
		PreparedStatement pstat=null;
		ResultSet rs=null;
		int count=0;
		try {
			pstat=conn.prepareStatement("select soid,createTime,tipFee,productTotal,SOtotal,payType,prePayFee,t1.status,customerName,scmuser.name userName \r\n " + 
					" from \r\n " + 
					" (select soid,s.account userAccount,createTime,tipFee,productTotal,SOtotal,payType,prePayFee,status,customer.name customerName  \r\n " + 
					" from somain s inner join customer \r\n " + 
					" on s.customerCode=customer.customerCode and payType regexp ? and status regexp ?) t1 inner join scmuser \r\n " + 
					" on t1.userAccount=scmuser.account \r\n " + 
					" ;");
			if(payType==1) {
				pstat.setObject(1, 1);
				pstat.setObject(2, 1);
			}else if(payType==2) {
				pstat.setObject(1, 2);
				pstat.setObject(2, 3);
			}else if(payType==3) {
				pstat.setObject(1, 3);
				pstat.setObject(2, 5);
			}else {
				pstat.setObject(1, "^[1-3]$");
				pstat.setObject(2, "^[135]$");
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
	 * 出库登记得到销售单数据
	 * @param page
	 * @param num
	 * @param payType
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<SO> getSos(int page,int num,int payType) throws SQLException{
		PreparedStatement pstat=null;
		ResultSet rs=null;
		ArrayList<SO> sos=new ArrayList<SO>();
		try {
			pstat=conn.prepareStatement("select soid,createTime,tipFee,productTotal,SOtotal,payType,prePayFee,t1.status,customerName,scmuser.name userName \r\n " + 
					" from \r\n " + 
					" (select soid,s.account userAccount,createTime,tipFee,productTotal,SOtotal,payType,prePayFee,status,customer.name customerName  \r\n " + 
					" from somain s inner join customer \r\n " + 
					" on s.customerCode=customer.customerCode and payType regexp ? and status regexp ?) t1 inner join scmuser \r\n " + 
					" on t1.userAccount=scmuser.account \r\n " + 
					" limit ?,? ;");
			if(payType==1) {
				pstat.setObject(1, 1);
				pstat.setObject(2, 1);
			}else if(payType==2) {
				pstat.setObject(1, 2);
				pstat.setObject(2, 3);
			}else if(payType==3) {
				pstat.setObject(1, 3);
				pstat.setObject(2, 5);
			}else {
				pstat.setObject(1, "^[1-3]$");
				pstat.setObject(2, "^[135]$");
			}
			pstat.setInt(3, (page-1)*num);
			pstat.setInt(4, num);
			rs=pstat.executeQuery();
			while(rs.next()) {
				long soid=rs.getLong("soid");
				String customerName=rs.getString("customerName");
				String userName=rs.getString("userName");
				String createTime=rs.getString("createTime");
				double tipFee=rs.getDouble("tipFee");
				double productTotal=rs.getDouble("productTotal");
				double soTotal=rs.getDouble("SOTotal");
				int payType1=rs.getInt("payType");
				double prePayFee=rs.getDouble("prePayFee");
				int status=rs.getInt("status");
				sos.add(new SO(soid, customerName, userName, createTime, tipFee, productTotal, soTotal, payType1, prePayFee, status));
			}
			return sos;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
}
