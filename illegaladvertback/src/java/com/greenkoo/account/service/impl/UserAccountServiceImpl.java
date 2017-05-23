package com.greenkoo.account.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenkoo.account.dao.UserAccountDao;
import com.greenkoo.account.model.UserAccount;
import com.greenkoo.account.model.form.UserAccountVo;
import com.greenkoo.account.service.IUserAccountService;

@Service("userAccountService")
public class UserAccountServiceImpl implements IUserAccountService {

	@Autowired
	private UserAccountDao dao;
	
	@Transactional
	@Override
	public void add(UserAccount account) throws Exception {
		this.dao.add(account);
	}

	@Transactional
	@Override
	public int update(UserAccount account) throws Exception {
		return this.dao.update(account);
	}

	@Override
	public UserAccount queryById(String id) {
		return this.dao.queryById(id);
	}

	@Override
	public UserAccount queryByAccount(String account) {
		return this.dao.queryByAccount(account);
	}

	@Override
	public int queryCount(Map<String, Object> paramMap) {
		return this.dao.queryCount(paramMap);
	}

	@Override
	public List<UserAccountVo> queryList(Map<String, Object> paramMap) {
		return this.dao.queryList(paramMap);
	}
}
