package com.hikootech.mqcash.dao;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.UserQhzxVerify;

public interface UserQhzxVerifyDAO {

	public void addQhzxVerify(UserQhzxVerify userQhzxVerify);
	
	public List<UserQhzxVerify> queryCountByInfo(Map<String,Object> queryMap);
}
