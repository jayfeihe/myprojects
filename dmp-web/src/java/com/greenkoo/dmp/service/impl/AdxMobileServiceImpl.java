package com.greenkoo.dmp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenkoo.dmp.dao.IAdxMobileDao;
import com.greenkoo.dmp.model.AdxMobile;
import com.greenkoo.dmp.service.IAdxMobileService;
import com.greenkoo.dmp.sys.model.Pager;

@Service("adxMobileService")
public class AdxMobileServiceImpl implements IAdxMobileService {

	@Autowired
	private IAdxMobileDao dao;
	
	@Override
	public List<AdxMobile> findAll() {
		return dao.findAll(AdxMobile.class);
	}

	@Override
	public Pager<AdxMobile> pageList() {
		return dao.pageList(AdxMobile.class);
	}
}
