package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.scm.model.PO;
import com.scm.model.Poitem;
import com.scm.util.DBUtils;

public class StockinDao {
	private Connection conn;

	public StockinDao(Connection conn) {
		this.conn = conn;
	}
	
	public StockinDao() {}
	/**
	 * 更新产品表库存和采购在途数
	 * @param poid
	 * @return
	 * @throws SQLException
	 */
	public boolean updateProduct(long poid) throws SQLException{
		PreparedStatement pstat=null;
		try {
			ArrayList<Poitem> poitems=getPoitems(poid);
			pstat=conn.prepareStatement("update product set num=num+?,poNum=poNum-? where productCode=? ");
			for(int i=0;i<poitems.size();i++) {
				pstat.setInt(1, poitems.get(i).getNum());
				pstat.setInt(2, poitems.get(i).getNum());
				pstat.setObject(3, poitems.get(i).getProductCode());
				pstat.addBatch();
			}
			int[] r=pstat.executeBatch();
			for(int i=0;i<r.length;i++) {
				if(r[i]==0) {
					return false;
				}
			}
			return true;
		} finally {
			DBUtils.close(null, pstat, null);
		}
	}
	/**
	 * 向库存记录表插入记录
	 * @param poid
	 * @param userAccount
	 * @return
	 * @throws SQLException
	 */
	public boolean insertStockRecord(long poid,String userAccount) throws SQLException{
		PreparedStatement pstat=null;
		try {
			ArrayList<Poitem> poitems=getPoitems(poid);
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
			DBUtils.close(null, pstat, null);
		}
	}
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
			DBUtils.close(null, pstat, null);
		}
	}
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
			DBUtils.close(rs, pstat, null);
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
			DBUtils.close(rs, pstat, null);
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
			DBUtils.close(rs, pstat, null);
		}
	}
	/**
	 * 获取指定条件的表格数据
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
			DBUtils.close(rs, pstat, null);
		}
	}
}
