package com.scm.model;

import java.io.Serializable;

public class PO implements Serializable{
	//采购单编号  long
	private long poid;
	//供应商名称  String
	private String venderName;
	//用户名称	String
	private String userName;
	//创建时间	String
	private String createTime;
	//附加费用	double
	private double tipFee;
	//产品总价	double 
	private double productTotal;
	//总价格	double
	private double poTotal;
	//付款方式编号 int
	private int payType;
	//最低预付款 double
	private double prePayFee;
	//处理状态	int
	private int status;
	public PO() {}
	
	public PO(long poid, String venderName, String userName, String createTime, double tipFee, double productTotal,
			double poTotal, int payType, double prePayFee, int status) {
		super();
		this.poid = poid;
		this.venderName = venderName;
		this.userName = userName;
		this.createTime = createTime;
		this.tipFee = tipFee;
		this.productTotal = productTotal;
		this.poTotal = poTotal;
		this.payType = payType;
		this.prePayFee = prePayFee;
		this.status = status;
	}
	
	public double getPoTotal() {
		return poTotal;
	}

	public void setPoTotal(double poTotal) {
		this.poTotal = poTotal;
	}

	public long getPoid() {
		return poid;
	}
	public void setPoid(long poid) {
		this.poid = poid;
	}
	public String getVenderName() {
		return venderName;
	}
	public void setVenderName(String venderName) {
		this.venderName = venderName;
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
