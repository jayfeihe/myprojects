package com.tera.audit.loan.service;

import java.util.Map;

import com.github.miemiedev.mybatis.paginator.domain.PageList;
import com.tera.audit.loan.model.form.AppFormBean;
import com.tera.audit.loan.model.form.LoanBaseJsonMsg;
import com.tera.audit.loan.model.form.LoanRenewQBean;

public interface ILoanRenewService {

	PageList<LoanRenewQBean> queryPageList(Map<String, Object> params);

	LoanBaseJsonMsg loanRenewProcess(AppFormBean formBean, String loginId, String orgId) throws Exception;

}