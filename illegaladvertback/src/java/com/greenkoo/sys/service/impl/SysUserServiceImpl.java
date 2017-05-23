package com.greenkoo.sys.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenkoo.sys.dao.SysUserDao;
import com.greenkoo.sys.model.SysUser;
import com.greenkoo.sys.service.ISysUserService;

@Service("sysUserService")
public class SysUserServiceImpl implements ISysUserService {

	@Autowired
	private SysUserDao dao;
	
	@Transactional
	@Override
	public void add(SysUser user) throws Exception {
		this.dao.add(user);
	}

	@Transactional
	@Override
	public int updatePwd(SysUser user) throws Exception {
		return this.dao.updatePwd(user);
	}

	@Override
	public SysUser queryByUserId(String userId) {
		return this.dao.queryByUserId(userId);
	}

	@Override
	public int queryCount(Map<String, Object> paramMap) {
		return this.dao.queryCount(paramMap);
	}

	@Override
	public List<SysUser> queryList(Map<String, Object> paramMap) {
		return this.dao.queryList(paramMap);
	}

	@Override
	public SysUser queryByAccountAndPwd(String account, String pwd) {
		return this.dao.queryByAccountAndPwd(account, pwd);
	}
}
