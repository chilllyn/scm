package com.scm.service;

import java.util.ArrayList;

public class AuthService {
	//根据servlet路径和user modeluri判断其是否具有权限
	public boolean authCheck(String path,ArrayList<String> modelUri) {
		for(String s:modelUri) {
			if(path.startsWith(s)) {
				return true;
			}
		}
		return false;
	}
}
