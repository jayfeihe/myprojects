package com.hikootech.mqcash.service;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.UserQhzxVerify;

public interface UserQhzxVerifyService {

public void addQhzxVerify(UserQhzxVerify userQhzxVerify);
	
	public List<UserQhzxVerify> queryCountByInfo(Map<String,Object> queryMap);
}
