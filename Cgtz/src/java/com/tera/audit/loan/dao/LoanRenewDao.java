package com.tera.audit.loan.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.loan.model.form.LoanRenewQBean;

public interface LoanRenewDao {

	public List<LoanRenewQBean> queryList(Map<String, Object> map);
}
