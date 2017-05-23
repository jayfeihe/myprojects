package com.greenkoo.company.service;

import com.greenkoo.company.model.UserCompany;

public interface IUserCompanyService {

	UserCompany queryByCompanyId(String companyId);
}
