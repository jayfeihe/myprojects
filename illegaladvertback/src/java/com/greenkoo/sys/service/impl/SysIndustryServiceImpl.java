package com.greenkoo.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenkoo.sys.dao.SysIndustryDao;
import com.greenkoo.sys.model.SysIndustry;
import com.greenkoo.sys.service.ISysIndustryService;

@Service("sysIndustryService")
public class SysIndustryServiceImpl implements ISysIndustryService {

	@Autowired
	private SysIndustryDao dao;
	
	@Override
	public SysIndustry queryByIndustryId(int industryId) {
		return dao.queryByIndustryId(industryId);
	}

	@Override
	public List<SysIndustry> queryList() {
		return dao.queryList();
	}
}
