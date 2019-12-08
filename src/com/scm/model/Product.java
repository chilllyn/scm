package com.scm.model;

import java.io.Serializable;

public class Product implements Serializable {
	private String productCode;
	private int categoryId;
	private String categoryName;
	private String name;
	private String unitName;
	private double price;
	private String createDate;
	private String remark;
	private int num;
	private int poNum;
	private int soNum;
	private String min;
	private String max;
	
	public Product(String productCode, String name, int num, int poNum, int soNum) {
		super();
		this.productCode = productCode;
		this.name = name;
		this.num = num;
		this.poNum = poNum;
		this.soNum = soNum;
	}

	public Product(String productCode, String name, String min, String max) {
		super();
		this.productCode = productCode;
		this.name = name;
		this.min = min;
		this.max = max;
	}


	public Product(String productCode, String categoryName, String name) {
		super();
		this.productCode = productCode;
		this.categoryName = categoryName;
		this.name = name;
	}
	
	public Product(String productCode, int categoryId, String categoryName, String name, String unitName, double price,
			String createDate, String remark) {
		super();
		this.productCode = productCode;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.name = name;
		this.unitName = unitName;
		this.price = price;
		this.createDate = createDate;
		this.remark = remark;
	}

	public Product() {
	}
	public Product(String productCode, int categoryId, String categoryName, String name, String unitName, double price,
			String createDate, String remark, int num, int poNum, int soNum) {
		this.productCode = productCode;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.name = name;
		this.unitName = unitName;
		this.price = price;
		this.createDate = createDate;
		this.remark = remark;
		this.num = num;
		this.poNum = poNum;
		this.soNum = soNum;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUnitName() {
		return unitName;
	}
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public int getPoNum() {
		return poNum;
	}
	public void setPoNum(int poNum) {
		this.poNum = poNum;
	}
	public int getSoNum() {
		return soNum;
	}
	public void setSoNum(int soNum) {
		this.soNum = soNum;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	
	
}
