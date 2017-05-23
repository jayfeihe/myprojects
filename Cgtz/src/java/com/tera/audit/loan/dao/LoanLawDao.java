package com.tera.audit.loan.dao;

import java.util.List;
import java.util.Map;

import com.tera.audit.loan.model.form.LoanLawQBean;

public interface LoanLawDao {

	public List<LoanLawQBean> queryListByLoanId(Map<String, Object> map);
}
