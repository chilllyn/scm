package com.scm.model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	private String account;
	private String password;
	private String name;
	private String createDate;
	private int status;
	private ArrayList<Integer> modelCode;
	private ArrayList<String> modelUri;
	
	public User(String account, String password, String name, int status) {
		super();
		this.account = account;
		this.password = password;
		this.name = name;
		this.status = status;
	}
	public User(String account, String password, String name, int status, ArrayList<String> modelUri) {
		super();
		this.account = account;
		this.password = password;
		this.name = name;
		this.status = status;
		this.modelUri = modelUri;
	}
	public User() {}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public ArrayList<Integer> getModelCode() {
		return modelCode;
	}
	public void setModelCode(ArrayList<Integer> modelCode) {
		this.modelCode = modelCode;
	}
	public ArrayList<String> getModelUri() {
		return modelUri;
	}
	public void setModelUri(ArrayList<String> modelUri) {
		this.modelUri = modelUri;
	}
	
}
