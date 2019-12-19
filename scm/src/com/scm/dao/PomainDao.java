package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.model.PO;
import com.scm.util.DataSourceUtil;

public class PomainDao {
	private Connection conn;

	public PomainDao(Connection conn) {
		this.conn = conn;
	}
	
	public PomainDao() {}
	/**
	 * 更新采购单主表
	 * @param poid
	 * @param userAccount
	 * @return
	 * @throws SQLException
	 */
	public boolean update(long poid,String userAccount) throws SQLException{
		PreparedStatement pstat=null;
		try {
			pstat=conn.prepareStatement("update pomain set status=2,stockTime=now(),stockUser=? where poid=? ");
			pstat.setString(1, userAccount);
			pstat.setLong(2, poid);
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
	public PO getPo(long poid) throws SQLException{
		PO po=new PO();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select poid,createTime,tipFee,productTotal,POtotal,payType,prePayFee,t1.status,venderName,scmuser.name userName \r\n " + 
					" from \r\n" + 
					" (select poid,p.account userAccount,createTime,tipFee,productTotal,POtotal,payType,prePayFee,status,vender.name venderName  \r\n " + 
					" from pomain p inner join vender \r\n " + 
					" on p.venderCode=vender.venderCode and poid=?) t1 inner join scmuser \r\n " + 
					" on t1.userAccount=scmuser.account ; ");
			pstat.setLong(1, poid);
			rs=pstat.executeQuery();
			if(rs.next()) {
				String venderName=rs.getString("venderName");
				String userName=rs.getString("userName");
				String createTime=rs.getString("createTime");
				double tipFee=rs.getDouble("tipFee");
				double productTotal=rs.getDouble("productTotal");
				double poTotal=rs.getDouble("POTotal");
				int payType1=rs.getInt("payType");
				double prePayFee=rs.getDouble("prePayFee");
				int status=rs.getInt("status");
				po=new PO(poid, venderName, userName, createTime, tipFee, productTotal, poTotal, payType1, prePayFee, status);
			}
			return po;
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
			pstat=conn.prepareStatement("select poid,createTime,tipFee,productTotal,POtotal,payType,prePayFee,t1.status,venderName,scmuser.name userName \r\n " + 
					" from \r\n " + 
					" (select poid,p.account userAccount,createTime,tipFee,productTotal,POtotal,payType,prePayFee,status,vender.name venderName  \r\n " + 
					" from pomain p inner join vender \r\n " + 
					" on p.venderCode=vender.venderCode and payType regexp ? and status regexp ?) t1 inner join scmuser \r\n " + 
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
	 * 获取入库登记表格数据
	 * @param page
	 * @param num
	 * @param payType
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<PO> getPos(int page,int num,int payType) throws SQLException{
		PreparedStatement pstat=null;
		ResultSet rs=null;
		ArrayList<PO> pos=new ArrayList<PO>();
		try {
			pstat=conn.prepareStatement("select poid,createTime,tipFee,productTotal,POtotal,payType,prePayFee,t1.status,venderName,scmuser.name userName \r\n " + 
					" from \r\n " + 
					" (select poid,p.account userAccount,createTime,tipFee,productTotal,POtotal,payType,prePayFee,status,vender.name venderName  \r\n " + 
					" from pomain p inner join vender \r\n " + 
					" on p.venderCode=vender.venderCode and payType regexp ? and status regexp ?) t1 inner join scmuser \r\n " + 
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
				long poid=rs.getLong("poid");
				String venderName=rs.getString("venderName");
				String userName=rs.getString("userName");
				String createTime=rs.getString("createTime");
				double tipFee=rs.getDouble("tipFee");
				double productTotal=rs.getDouble("productTotal");
				double poTotal=rs.getDouble("POTotal");
				int payType1=rs.getInt("payType");
				double prePayFee=rs.getDouble("prePayFee");
				int status=rs.getInt("status");
				pos.add(new PO(poid, venderName, userName, createTime, tipFee, productTotal, poTotal, payType1, prePayFee, status));
			}
			return pos;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
}
