package com.greenkoo.dmp.service;

import java.util.List;

import com.greenkoo.dmp.model.NetworkApp;
import com.greenkoo.dmp.sys.model.Pager;

public interface INetworkAppService {

	public List<NetworkApp> findAll();
	
	public Pager<NetworkApp> pageList();
}
