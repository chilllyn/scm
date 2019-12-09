package com.scm.model;

import java.io.Serializable;

public class Soitem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productCode;
	private String name;
	private String unitName;
	private int num;
	private double unitPrice;
	private double itemPrice;
	public Soitem() {
		super();
	}
	public Soitem(String productCode, String name, String unitName, int num, double unitPrice, double itemPrice) {
		super();
		this.productCode = productCode;
		this.name = name;
		this.unitName = unitName;
		this.num = num;
		this.unitPrice = unitPrice;
		this.itemPrice = itemPrice;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
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
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}
	public double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(double itemPrice) {
		this.itemPrice = itemPrice;
	}
}
