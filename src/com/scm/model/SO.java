package com.scm.model;

import java.io.Serializable;

public class SO implements Serializable{
	//销售单编号  long
	private long soid;
	//客户名称  String
	private String customerName;
	//用户名称	String
	private String userName;
	//创建时间	String
	private String createTime;
	//附加费用	double
	private double tipFee;
	//产品总价	double 
	private double productTotal;
	//总价格	double
	private double soTotal;
	//付款方式编号 int
	private int payType;
	//最低预付款 double
	private double prePayFee;
	//处理状态	int
	private int status;
	public SO() {}
	
	public SO(long soid, String customerName, String userName, String createTime, double tipFee, double productTotal,
			double soTotal, int payType, double prePayFee, int status) {
		super();
		this.soid = soid;
		this.customerName = customerName;
		this.userName = userName;
		this.createTime = createTime;
		this.tipFee = tipFee;
		this.productTotal = productTotal;
		this.soTotal = soTotal;
		this.payType = payType;
		this.prePayFee = prePayFee;
		this.status = status;
	}
	
	public long getSoid() {
		return soid;
	}

	public void setSoid(long soid) {
		this.soid = soid;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getSoTotal() {
		return soTotal;
	}

	public void setSoTotal(double soTotal) {
		this.soTotal = soTotal;
	}

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public double getTipFee() {
		return tipFee;
	}
	public void setTipFee(double tipFee) {
		this.tipFee = tipFee;
	}
	public double getProductTotal() {
		return productTotal;
	}
	public void setProductTotal(double productTotal) {
		this.productTotal = productTotal;
	}
	public int getPayType() {
		return payType;
	}
	public void setPayType(int payType) {
		this.payType = payType;
	}
	public double getPrePayFee() {
		return prePayFee;
	}
	public void setPrePayFee(double prePayFee) {
		this.prePayFee = prePayFee;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
}
