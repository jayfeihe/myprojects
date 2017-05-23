package com.greenkoo.dmp.service;

import java.util.List;

import com.greenkoo.dmp.model.AdxApp;
import com.greenkoo.dmp.sys.model.Pager;

public interface IAdxAppService {

	public List<AdxApp> findAll();
	
	public Pager<AdxApp> pageList();
}
