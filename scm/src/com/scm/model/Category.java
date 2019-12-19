package com.scm.model;

import java.io.Serializable;

public class Category implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int categoryId;
	private String name;
	private String remark;
	
	public Category(int categoryId, String name) {
		super();
		this.categoryId = categoryId;
		this.name = name;
	}
	public Category() {
		super();
	}
	public Category(int categoryId, String name, String remark) {
		super();
		this.categoryId = categoryId;
		this.name = name;
		this.remark = remark;
	}
	public int getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
