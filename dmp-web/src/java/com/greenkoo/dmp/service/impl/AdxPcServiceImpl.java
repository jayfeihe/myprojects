package com.greenkoo.dmp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenkoo.dmp.dao.IAdxPcDao;
import com.greenkoo.dmp.model.AdxPc;
import com.greenkoo.dmp.service.IAdxPcService;
import com.greenkoo.dmp.sys.model.Pager;

@Service("adxPcService")
public class AdxPcServiceImpl implements IAdxPcService {

	@Autowired
	private IAdxPcDao dao;
	
	@Override
	public List<AdxPc> findAll() {
		return dao.findAll(AdxPc.class);
	}

	@Override
	public Pager<AdxPc> pageList() {
		return dao.pageList(AdxPc.class);
	}
}
