package com.greenkoo.company.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.greenkoo.company.dao.UserCompanyDao;
import com.greenkoo.company.model.UserCompany;
import com.greenkoo.company.model.form.UserCompanyVo;
import com.greenkoo.company.service.IUserCompanyService;

@Service("userCompanyService")
public class UserCompanyServiceImpl implements IUserCompanyService {

	@Autowired
	private UserCompanyDao dao;
	
	@Transactional
	@Override
	public void add(UserCompany company) throws Exception {
		this.dao.add(company);
	}

	@Transactional
	@Override
	public int update(UserCompany company) throws Exception {
		return this.dao.update(company);
	}

	@Transactional
	@Override
	public void updateStatus(UserCompany company) throws Exception {
		this.dao.updateStatus(company);
	}

	@Override
	public UserCompany queryByCompanyId(String companyId) {
		return this.dao.queryByCompanyId(companyId);
	}

	@Override
	public UserCompany queryByCompanyUrlAndType(String companyUrl, String type) {
		return this.dao.queryByCompanyUrlAndType(companyUrl, type);
	}

	@Override
	public int queryCount(Map<String, Object> paramMap) {
		return this.dao.queryCount(paramMap);
	}

	@Override
	public List<UserCompanyVo> queryList(Map<String, Object> paramMap) {
		return this.dao.queryList(paramMap);
	}
}
