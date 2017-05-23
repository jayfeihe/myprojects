package com.tera.audit.loan.service;

import java.util.List;
import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.model.form.LoanLawQBean;

public interface ILoanLawService {

	List<LoanLawQBean> queryListByLoanId(Map<String, Object> map);
	
	PageList<LoanLawQBean> queryPageList(Map<String, Object> params);
}