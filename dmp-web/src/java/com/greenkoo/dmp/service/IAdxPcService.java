package com.greenkoo.dmp.service;

import java.util.List;

import com.greenkoo.dmp.model.AdxPc;
import com.greenkoo.dmp.sys.model.Pager;

public interface IAdxPcService {

	public List<AdxPc> findAll();
	
	public Pager<AdxPc> pageList();
}
