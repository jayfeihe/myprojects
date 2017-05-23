package com.greenkoo.sys.dao;

import java.util.List;

import com.greenkoo.sys.model.SysIndustry;

public interface SysIndustryDao {

	SysIndustry queryByIndustryId(int industryId);
	
	List<SysIndustry> queryList();
}
