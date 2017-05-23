package com.hikootech.mqcash.dao;

import java.util.Map;

import com.hikootech.mqcash.po.UserInstalment;

public interface UserInstalmentDAO {
	
	public UserInstalment queryUserInstalmentById(String instalmentId) throws Exception;
	
	public Long updateInstalmentStatus(Map<String, Object> paramMap) throws Exception;
	
}
