package com.greenkoo.dmp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenkoo.dmp.dao.INetworkPcDao;
import com.greenkoo.dmp.model.NetworkPc;
import com.greenkoo.dmp.service.INetworkPcService;
import com.greenkoo.dmp.sys.model.Pager;

@Service("networkPcService")
public class NetworkPcServiceImpl implements INetworkPcService {

	@Autowired
	private INetworkPcDao dao;
	
	@Override
	public List<NetworkPc> findAll() {
		return dao.findAll(NetworkPc.class);
	}

	@Override
	public Pager<NetworkPc> pageList() {
		return dao.pageList(NetworkPc.class);
	}
}
