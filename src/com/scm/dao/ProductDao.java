package com.scm.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import com.scm.model.Category;
import com.scm.model.Product;
import com.scm.util.DBUtils;

public class ProductDao {
	private Connection conn;

	public ProductDao(Connection conn) {
		this.conn = conn;
	}

	public ProductDao() {}
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
			DBUtils.close(null, pstat, null);
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
			DBUtils.close(null, pstat, null);
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
			DBUtils.close(null, pstat, null);
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
			DBUtils.close(rs, pstat, null);
		}
	}
	/**
	 * 获取所有产品类别编号 名称
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Category> getCategories() throws SQLException{
		ArrayList<Category> categories=new ArrayList<Category>();
		PreparedStatement pstat=null;
		ResultSet rs=null;
		
		try {
			pstat=conn.prepareStatement("select categoryId,name from category ");
			rs=pstat.executeQuery();
			while(rs.next()) {
				categories.add(new Category(rs.getInt(1), rs.getString(2)));
			}
			return categories;
		} finally {
			DBUtils.close(rs, pstat, null);
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
			DBUtils.close(rs, pstat, null);
		}
	}
}
