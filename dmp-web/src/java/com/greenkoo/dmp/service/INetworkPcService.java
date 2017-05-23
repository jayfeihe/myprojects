package com.greenkoo.dmp.service;

import java.util.List;

import com.greenkoo.dmp.model.NetworkPc;
import com.greenkoo.dmp.sys.model.Pager;

public interface INetworkPcService {

	public List<NetworkPc> findAll();
	
	public Pager<NetworkPc> pageList();
}
