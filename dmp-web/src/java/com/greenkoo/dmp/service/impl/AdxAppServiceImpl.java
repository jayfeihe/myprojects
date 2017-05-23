package com.greenkoo.dmp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenkoo.dmp.dao.IAdxAppDao;
import com.greenkoo.dmp.model.AdxApp;
import com.greenkoo.dmp.service.IAdxAppService;
import com.greenkoo.dmp.sys.model.Pager;

@Service("adxAppService")
public class AdxAppServiceImpl implements IAdxAppService {

	@Autowired
	private IAdxAppDao dao;
	
	@Override
	public List<AdxApp> findAll() {
		return dao.findAll(AdxApp.class);
	}

	@Override
	public Pager<AdxApp> pageList() {
		return dao.pageList(AdxApp.class);
	}
}
