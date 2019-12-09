package com.scm.model;

import java.io.Serializable;

public class StockRecord implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int stockId;
	private String productCode;
	private String orderCode;
	private int stockNum;
	private int stockType;
	private String stockTime;
	private String createUser;
	private String productName;
	private double totalPrice;
	public StockRecord() {
		super();
	}
	
	public StockRecord(String productCode, String orderCode, int stockNum, String stockTime, String productName,
			double totalPrice) {
		super();
		this.productCode = productCode;
		this.orderCode = orderCode;
		this.stockNum = stockNum;
		this.stockTime = stockTime;
		this.productName = productName;
		this.totalPrice = totalPrice;
	}

	public StockRecord(String orderCode, int stockNum, int stockType, String stockTime, String createUser) {
		super();
		this.orderCode = orderCode;
		this.stockNum = stockNum;
		this.stockType = stockType;
		this.stockTime = stockTime;
		this.createUser = createUser;
	}

	public StockRecord(int stockId, String productCode, String orderCode, int stockNum, int stockType,
			String stockTime, String createUser) {
		super();
		this.stockId = stockId;
		this.productCode = productCode;
		this.orderCode = orderCode;
		this.stockNum = stockNum;
		this.stockType = stockType;
		this.stockTime = stockTime;
		this.createUser = createUser;
	}
	public int getStockId() {
		return stockId;
	}
	public void setStockId(int stockId) {
		this.stockId = stockId;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getOrderCode() {
		return orderCode;
	}
	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}
	public int getStockNum() {
		return stockNum;
	}
	public void setStockNum(int stockNum) {
		this.stockNum = stockNum;
	}
	public int getStockType() {
		return stockType;
	}
	public void setStockType(int stockType) {
		this.stockType = stockType;
	}

	public String getStockTime() {
		return stockTime;
	}

	public void setStockTime(String stockTime) {
		this.stockTime = stockTime;
	}

	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	
	
}
