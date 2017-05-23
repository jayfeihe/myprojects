package com.greenkoo.dmp.service;

import java.util.List;

import com.greenkoo.dmp.model.AdxMobile;
import com.greenkoo.dmp.sys.model.Pager;

public interface IAdxMobileService {

	public List<AdxMobile> findAll();
	
	public Pager<AdxMobile> pageList();
}
