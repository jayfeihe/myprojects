package com.greenkoo.dmp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenkoo.dmp.dao.INetworkAppDao;
import com.greenkoo.dmp.model.NetworkApp;
import com.greenkoo.dmp.service.INetworkAppService;
import com.greenkoo.dmp.sys.model.Pager;

@Service("networkAppService")
public class NetworkAppServiceImpl implements INetworkAppService {

	@Autowired
	private INetworkAppDao dao;
	
	@Override
	public List<NetworkApp> findAll() {
		return dao.findAll(NetworkApp.class);
	}

	@Override
	public Pager<NetworkApp> pageList() {
		return dao.pageList(NetworkApp.class);
	}
}
