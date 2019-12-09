package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.scm.model.Poitem;
import com.scm.model.Product;
import com.scm.model.Soitem;
import com.scm.util.DBUtils;
import com.scm.util.DataSourceUtil;

public class ProductDao {
	private Connection conn;

	public ProductDao(Connection conn) {
		this.conn = conn;
	}

	public ProductDao() {}
	
	/**
	 * 库存查询总页数
	 * @param num
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public int getStockSearchTotalPage(int num,Product p) throws SQLException{
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
				pstat.setObject(4, "9999999999");
			}else {
				pstat.setObject(4, p.getMax());
			}
			rs=pstat.executeQuery();
			while(rs.next()) {
				count+=1;
			}
			return (int) Math.ceil(count*1.0/num);
		}finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 库存查询获取指定查询条件指定页的表格数据
	 * @param page
	 * @param num
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Product> getStockSearchProducts(int page,int num,Product p) throws SQLException{
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
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 更新产品表库存和采购在途数
	 * @param poid
	 * @return
	 * @throws SQLException
	 */
	public boolean updateProductPo(long poid) throws SQLException{
		PreparedStatement pstat=null;
		try {
			PoitemDao pid=new PoitemDao(conn);
			ArrayList<Poitem> poitems=pid.getPoitems(poid);
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
			DataSourceUtil.close(null, pstat, null);
		}
	}
	/**
	 * 更新产品表库存和销售待发数
	 * @param poid
	 * @return
	 * @throws SQLException
	 */
	public boolean updateProductSo(long soid) throws SQLException{
		PreparedStatement pstat=null;
		try {
			SoitemDao sid=new SoitemDao(conn);
			ArrayList<Soitem> soitems=sid.getSoitems(soid);
			pstat=conn.prepareStatement("update product set num=num-?,soNum=soNum-? where productCode=? ");
			for(int i=0;i<soitems.size();i++) {
				pstat.setInt(1, soitems.get(i).getNum());
				pstat.setInt(2, soitems.get(i).getNum());
				pstat.setObject(3, soitems.get(i).getProductCode());
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
			DataSourceUtil.close(null, pstat, null);
		}
	}
	/**
	 * 判断是否可以删除
	 * @param categoryId
	 * @return
	 * @throws SQLException
	 */
	public boolean canDelete(int categoryId) throws SQLException {
		PreparedStatement pstat=null;
		ResultSet rs=null;
		int count=0;
		
		try {
			conn=DBUtils.getConnection();
			pstat=conn.prepareStatement("select count(*) from product where categoryId=? ");
			pstat.setInt(1, categoryId);
			rs=pstat.executeQuery();
			if(rs.next()) {
				count=rs.getInt(1);
			}
			if(count==0) {
				return true;
			}
			return false;
		} finally {
			DataSourceUtil.close(rs, pstat, conn);
		}
	}
	/**
	 * 删除
	 * @param productCode
	 * @return
	 * @throws SQLException
	 */
	public boolean delete(String productCode) throws SQLException{
		PreparedStatement pstat=null;
		int num=0;
		
		try {
			pstat=conn.prepareStatement("delete from product where productCode=? and PONum=0 and SONum=0 ");
			pstat.setObject(1, productCode);
			num=pstat.executeUpdate();
			if(num==0) {
				return false;
			}
			return true;
		} finally {
			DataSourceUtil.close(null, pstat, null);
		}
	}
	/**
	 * 修改
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public boolean update(HashMap<String, String> p) throws SQLException{
		PreparedStatement pstat=null;
		int num=0;
		
		try {
			pstat=conn.prepareStatement("update product set "
					+ " categoryId=?,name=?,unitName=?,price=?,remark=? "
					+ " where productCode=? ");
			pstat.setObject(1, p.get("categoryId"));
			pstat.setObject(2, p.get("name"));
			pstat.setObject(3, p.get("unitName"));
			pstat.setObject(4, p.get("price"));
			pstat.setObject(5, p.get("remark"));
			pstat.setObject(6, p.get("productCode"));
			num=pstat.executeUpdate();
			if(num==1) {
				return true;
			}
			return false;
		} finally {
			DataSourceUtil.close(null, pstat, null);
		}
	}
	/**
	 * 新增
	 * @param p
	 * @return
	 * @throws SQLException
	 */
	public boolean add(HashMap<String, String> p) throws SQLException{
		PreparedStatement pstat=null;
		int num=0;
		
		try {
			pstat=conn.prepareStatement("insert into product(productCode,categoryId,name,unitName,price,createDate,remark) "
					+ " values(?,?,?,?,?,?,?) ");
			pstat.setString(1, p.get("productCode"));
			pstat.setObject(2, p.get("categoryId"));
			pstat.setObject(3, p.get("name"));
			pstat.setObject(4, p.get("unitName"));
			pstat.setObject(5, p.get("price"));
			pstat.setObject(6, p.get("createDate"));
			pstat.setObject(7, p.get("remark"));
			num=pstat.executeUpdate();
			if(num==1) {
				return true;
			}
			return false;
		} finally {
			DataSourceUtil.close(null, pstat, null);
		}
	}
	/**
	 * 判断新增产品编码是否可用
	 * @param productCode
	 * @return
	 * @throws SQLException
	 */
	public boolean checkProductCode(String productCode) throws SQLException{
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select * from product where productCode=? ");
			pstat.setString(1, productCode);
			rs=pstat.executeQuery();
			if(rs.next()) {
				return false;
			}
			return true;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	
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
			pstat=conn.prepareStatement("select product.*,category.name categoryName "
					+ " from product inner join category "
					+ " on product.categoryid=category.categoryid "
					+ " and productCode regexp ? "
					+ " and product.name regexp ? "
					+ " and category.name regexp ? ; ");
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
			if(p.getCategoryName()==null||("").equals(p.getCategoryName())) {
				pstat.setString(3, ".*");
			}else {
				pstat.setString(3, p.getCategoryName());
			}
			rs=pstat.executeQuery();
			while(rs.next()) {
				count+=1;
			}
			return (int) Math.ceil(count*1.0/num);
		}finally {
			DataSourceUtil.close(rs, pstat, null);
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
			pstat=conn.prepareStatement("select product.*,category.name categoryName "
					+ " from product inner join category "
					+ " on product.categoryid=category.categoryid "
					+ " and productCode regexp ? "
					+ " and product.name regexp ? "
					+ " and category.name regexp ? "
					+ " limit ?,?; ");
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
			if(p.getCategoryName()==null||("").equals(p.getCategoryName())) {
				pstat.setString(3, ".*");
			}else {
				pstat.setString(3, p.getCategoryName());
			}
			pstat.setInt(4, (page-1)*num);
			pstat.setInt(5, num);
			rs=pstat.executeQuery();
			while(rs.next()) {
				String productCode=rs.getString("productCode");
				int categoryId=rs.getInt("categoryId");
				String name=rs.getString("name");
				String unitName=rs.getString("unitName");
				String categoryName=rs.getString("categoryName");
				String createDate=rs.getString("createDate");
				double price=rs.getDouble("price");
				String remark=rs.getString("remark");
				products.add(new Product(productCode, categoryId, categoryName, name, unitName, price, createDate, remark));
			}
			return products;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 获取月度库存报表数据
	 * @param month
	 * @param page
	 * @param num
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Product> getPs(String month,int page,int num) throws SQLException{
		ArrayList<Product> ps=new ArrayList<Product>();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select distinct s.productcode,p.name,t1.ponum,t2.sonum,p.num \r\n" + 
					" from stockrecord s,product p, \r\n" + 
					" (select productcode,sum(stocknum) ponum \r\n" + 
					" from stockrecord \r\n" + 
					" where (stocktype=1 or stocktype=3) \r\n" + 
					" and stocktime regexp ? \r\n" + 
					" group by productcode) t1, \r\n" + 
					" (select productcode,sum(stocknum) sonum \r\n" + 
					" from stockrecord \r\n" + 
					" where (stocktype=2 or stocktype=4) \r\n" + 
					" and stocktime regexp ? \r\n" + 
					" group by productcode) t2\r\n" + 
					" where s.productcode=p.productcode \r\n" + 
					" and s.productcode=t1.productcode \r\n" + 
					" and s.productcode=t2.productcode \r\n" + 
					" and stocktime regexp ? \r\n" + 
					" limit ?,? ; ");
			pstat.setObject(1, month);
			pstat.setObject(2, month);
			pstat.setObject(3, month);
			pstat.setObject(4, (page-1)*num);
			pstat.setObject(5, num);
			rs=pstat.executeQuery();
			while(rs.next()) {
				String productCode=rs.getString("productCode");
				String name=rs.getString("name");
				int poNum=rs.getInt("poNum");
				int soNum=rs.getInt("soNum");
				int num1=rs.getInt("num");
				ps.add(new Product(productCode, name, num1, poNum, soNum));
			}
			return ps;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
	/**
	 * 月度库存报表总页数
	 * @param month
	 * @param num
	 * @return
	 * @throws SQLException
	 */
	public int totalPage(String month,int num) throws SQLException{
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select distinct s.productcode,p.name,t1.ponum,t2.sonum,p.num \r\n" + 
					" from stockrecord s,product p, \r\n" + 
					" (select productcode,sum(stocknum) ponum \r\n" + 
					" from stockrecord \r\n" + 
					" where (stocktype=1 or stocktype=3) \r\n" + 
					" and stocktime regexp ? \r\n" + 
					" group by productcode) t1, \r\n" + 
					" (select productcode,sum(stocknum) sonum \r\n" + 
					" from stockrecord \r\n" + 
					" where (stocktype=2 or stocktype=4) \r\n" + 
					" and stocktime regexp ? \r\n" + 
					" group by productcode) t2\r\n" + 
					" where s.productcode=p.productcode \r\n" + 
					" and s.productcode=t1.productcode \r\n" + 
					" and s.productcode=t2.productcode \r\n" + 
					" and stocktime regexp ? ; ");
			pstat.setObject(1, month);
			pstat.setObject(2, month);
			pstat.setObject(3, month);
			rs=pstat.executeQuery();
			rs.last();
			return (int) Math.ceil(rs.getRow()*1.0/num) ;
		} finally {
			DataSourceUtil.close(rs, pstat, null);
		}
	}
}
