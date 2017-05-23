package com.greenkoo.sys.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenkoo.sys.dao.SysMenuDao;
import com.greenkoo.sys.model.SysMenu;
import com.greenkoo.sys.service.ISysMenuService;

@Service("sysMenuService")
public class SysMenuServiceImpl implements ISysMenuService {

	@Autowired
	private SysMenuDao dao;
	
	@Transactional
	@Override
	public void add(SysMenu menu) throws Exception {
		this.dao.add(menu);
	}
	
	@Transactional
	@Override
	public int update(SysMenu menu) throws Exception {
		return this.dao.update(menu);
	}
	
	@Override
	public List<SysMenu> queryList() {
		return this.dao.queryList();
	}

	@Override
	public List<SysMenu> queryMenuByLevel(int level, Integer status) {
		return this.dao.queryMenuByLevel(level, status);
	}
}
