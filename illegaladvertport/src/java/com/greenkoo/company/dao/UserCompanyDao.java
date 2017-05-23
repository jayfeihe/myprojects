package com.greenkoo.company.dao;

import com.greenkoo.company.model.UserCompany;

public interface UserCompanyDao {

	UserCompany queryByCompanyId(String companyId);
}
