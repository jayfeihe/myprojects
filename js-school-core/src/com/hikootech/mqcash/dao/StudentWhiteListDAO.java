package com.hikootech.mqcash.dao;

import java.util.List;
import java.util.Map;

import com.hikootech.mqcash.po.StudentWhiteList;

public interface StudentWhiteListDAO {
	
	public List<StudentWhiteList> queryStudentWhiteList(Map<String, String> paramMap);

}
