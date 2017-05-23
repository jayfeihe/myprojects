package com.greenkoo.company.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.greenkoo.company.dao.UserCompanyDao;
import com.greenkoo.company.model.UserCompany;
import com.greenkoo.company.service.IUserCompanyService;

@Service("userCompanyService")
public class UserCompanyServiceImpl implements IUserCompanyService {

	@Autowired
	private UserCompanyDao dao;
	
	@Override
	public UserCompany queryByCompanyId(String companyId) {
		return this.dao.queryByCompanyId(companyId);
	}
}
